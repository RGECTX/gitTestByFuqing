package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.daointerface.org.IOrganizationDAO;
import com.greathack.homlin.exception.BusinessException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.AuxDagl;
import com.greathack.homlin.pojo.auxiliary.AuxDaglImportVo;
import com.greathack.homlin.pojo.auxiliary.AuxDaglSearchCriteria;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxDaglService;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;
import com.greathack.homlin.serviceinterface.dict.IDictService;
import com.greathack.homlin.serviceinterface.org.IOrganizationService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.homlin.utils.DateUtils;
import com.greathack.homlin.utils.IdCardNumberVerify;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuxDaglService implements IAuxDaglService {

    @Autowired
    private IDictService dictService;
    @Autowired
    private IDictDataService dictDataService;
    @Autowired
    private IOrganizationService organizationService;

    private static Logger logger = LoggerFactory.getLogger(AuxDaglService.class);
    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");
    private static IOrganizationDAO organizationDAO = (IOrganizationDAO) DAOFactory.createDAO("IOrganizationDAO");


    @Override
    public List<AuxDagl> search(AuxDaglSearchCriteria criteria) {
        List<AuxDagl> auxZljdList = new ArrayList<AuxDagl>();
        if (criteria == null) {
            return auxZljdList;
        }
        return auxDaglDAO.search(criteria);
    }

    @Override
    public long getSearchResultCount(AuxDaglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        System.out.println(criteria.toString());
        String count = "0";
        count = Long.toString(auxDaglDAO.getSearchResultCount(criteria));

        return Long.parseLong(count);
    }

    @Override
    public List<AuxDagl> findIdcard(String idcard) {
        return auxDaglDAO.findIdcard(idcard);
    }

    @Override
    public void updateFjlb(AuxDagl auxDagl) {
        auxDaglDAO.updateFjlb(auxDagl);
    }

    @Override
    public void updateDwjs(AuxDagl auxDagl) {
        auxDaglDAO.updateDwjs(auxDagl);
    }


    @Override
    public AuxDagl add(AuxDagl auxDagl) throws ServiceException {
        try {
            //????????????
            int sexByCertId = IdCardNumberVerify.getSexByCertId(auxDagl.getIdcard());
            auxDagl.setXb(String.valueOf(sexByCertId));
            auxDagl.setCreateTime(Common.getCurrentTime());
            auxDagl.setDaId(IdCreator.createId("daId"));
            List<Organization> org = organizationDAO.findOrgName(auxDagl.getOrgId());//???????????????????????????
            if (org.size() != 0) {
                auxDagl.setOrgName(org.get(0).getOrgName());
            }

            //??????????????????
            String fjbh = "FJ" + auxDagl.getFjbh();
            auxDagl.setFjbh(fjbh);
            auxDaglDAO.add(auxDagl);
            return auxDagl;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, e.getErrMsg());
        }
    }

    @Override
    public void delete(Long auxDaglId) {
        auxDaglDAO.delete(auxDaglId);
    }

    @Override
    public void update(AuxDagl auxDagl) {
        //????????????
        /*auxDagl.setXb(String.valueOf(IdCardNumberVerify.getSexByCertId(auxDagl.getIdcard())));*/
        //??????????????????
        /*String fjbh = "FJ" + auxDagl.getFjbh();
        auxDagl.setFjbh(fjbh);*/

        List<Organization> org = organizationDAO.findOrgName(auxDagl.getOrgId());//???????????????????????????
        if (org.size() != 0) {
            auxDagl.setOrgName(org.get(0).getOrgName());
        }
        auxDaglDAO.update(auxDagl);

    }

    @Override
    public AuxDagl get(Long auxDaglId) {
        return auxDaglDAO.findById(auxDaglId);
    }

    @Override
    public List<AuxDagl> findByExample(AuxDagl auxDagl) {
        return auxDaglDAO.findByExample(auxDagl);
    }

    @Override
    public List<AuxDagl> findAll() {
        return auxDaglDAO.findAll();
    }


    public void updateState(AuxDagl auxDagl) {
        auxDaglDAO.updateState(auxDagl);
    }

    @Override
    public String importJbxx(List<AuxDaglImportVo> auxDaglImportVoList, Map<String, Object> params) {
        if (auxDaglImportVoList == null || auxDaglImportVoList.size() == 0) {
            throw new BusinessException("???????????????????????????????????????");
        }

        List<Dict> dictList = dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String, List<DictData>> dictMap = new HashMap<>();
        for (Dict dict : dictList) {
            List<DictData> dictDataList = dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode() + "", dictDataList);
        }

        Long createBy = Long.parseLong(params.get("createBy").toString());
        String isJbxxCover = params.get("isJbxxCover").toString();//0????????????????????????????????????  1?????????????????????????????????

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (AuxDaglImportVo auxDaglImportVo : auxDaglImportVoList) {
            try {
                String auxState = getDictLabel(auxDaglImportVo.getState(), "AUX_DASTATE", "", dictMap);
                if (StringUtils.isEmpty(auxDaglImportVo.getXm())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "????????? " + auxDaglImportVo.getXm() + " ??????????????????");
                    continue;
                } else if (StringUtils.isEmpty(auxDaglImportVo.getIdcard())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "????????? " + auxDaglImportVo.getXm() + " ????????????????????????");
                    continue;
                }/*else if(StringUtils.isEmpty(amJbxxImportVo.getOrgCode())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "????????? " + amJbxxImportVo.getXm()+ " ????????????????????????");
                    continue;
                }*//*else if(StringUtils.isEmpty(auxState)){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "????????? " + auxDaglImportVo.getXm()+ " ?????????????????????????????????");
                    continue;
                }*/

                Organization organization = organizationDAO.findByOrgCode(auxDaglImportVo.getOrgCode());
                if (organization == null) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "????????? " + auxDaglImportVo.getXm() + " ??????????????????");
                    continue;
                }
                // ?????????????????????
                AuxDagl auxDagl = new AuxDagl();
                //?????????????????????????????????????????????????????????????????????????????????
                List<AuxDagl> oldAuxDagl = auxDaglDAO.findIdcard(auxDaglImportVo.getIdcard());

                if (oldAuxDagl.size() == 0) {
                    //???????????????????????????????????? ??????????????????
                    //????????????
                    auxDagl.setCreateTime(Common.getCurrentTime());
                    auxDagl.setCreateBy(createBy);
                    auxDagl.setDaId(IdCreator.createId("AuxDagl"));
                    auxDagl.setIdcard(auxDaglImportVo.getIdcard());//????????????
                } else {
                    auxDagl.setCreateTime(Common.getCurrentTime());
                    auxDagl.setCreateBy(createBy);
                    auxDagl.setDaId(oldAuxDagl.get(0).getDaId());
                    auxDagl.setIdcard(auxDaglImportVo.getIdcard());//????????????
                }
                Long daId = auxDagl.getDaId();

                auxDagl.setXm(auxDaglImportVo.getXm());//??????
                auxDagl.setXb(getDictLabel(auxDaglImportVo.getXb(), "SEX", "", dictMap));//??????
                auxDagl.setMz(getDictLabel(auxDaglImportVo.getMz(), "MZ", "", dictMap));//??????
                auxDagl.setJgszss(auxDaglImportVo.getJgszss());//??????
                auxDagl.setJz(getDictLabel(auxDaglImportVo.getJz(), "DRIVER_CARD", "", dictMap));//??????
                if ("???".equals(auxDaglImportVo.getFby())) {
                    auxDagl.setFby("1");//?????????
                } else if ("???".equals(auxDaglImportVo.getFby())) {
                    auxDagl.setFby("0");//?????????
                }

                auxDagl.setHyzk(getDictLabel(auxDaglImportVo.getHyzk(), "HYZK", "", dictMap));//????????????
                auxDagl.setZzmm(getDictLabel(auxDaglImportVo.getZzmm(), "AM_ZZMM", "", dictMap));//????????????
                auxDagl.setXl(getDictLabel(auxDaglImportVo.getXl(), "AM_XL", "", dictMap));//??????
                auxDagl.setByys(auxDaglImportVo.getByys());//????????????
                auxDagl.setZy(auxDaglImportVo.getZy());//??????
                if (auxDaglImportVo.getByrq() != null) {
                    String byrq = auxDaglImportVo.getByrq();
                    byrq = DateUtils.formateDateStrTo8(byrq);
                    if (byrq != null) {
                        auxDagl.setByrq(byrq);//????????????
                    }
                }

                auxDagl.setAihao(auxDaglImportVo.getAihao());//??????
                auxDagl.setTechang(auxDaglImportVo.getTechang());//????????????
                auxDagl.setXjtzz(auxDaglImportVo.getXjtzz());//???????????????
                auxDagl.setLxfs(auxDaglImportVo.getLxfs());//????????????

                auxDagl.setFjbh(auxDaglImportVo.getFjbh());//????????????
                auxDagl.setHjdz(auxDaglImportVo.getHjdz());//????????????
                if ("???".equals(auxDaglImportVo.getIsdy())) {
                    auxDagl.setIsdy("1");//????????????
                } else if ("???".equals(auxDaglImportVo.getIsdy())) {
                    auxDagl.setIsdy("0");//????????????
                }
                auxDagl.setRzrq(auxDaglImportVo.getRzrq());//????????????
                auxDagl.setJtzz(auxDaglImportVo.getJtzz());//????????????
                if ("???".equals(auxDaglImportVo.getIsbx())) {
                    auxDagl.setIsbx("1");//????????????
                } else if ("???".equals(auxDaglImportVo.getIsbx())) {
                    auxDagl.setIsbx("0");//????????????
                }
                /*auxDagl.setHjdlx(getDictLabel(auxDaglImportVo.getHjdlx(),"AM_HJDLX","",dictMap));//???????????????*/

                /*jbxx.setRylb(getDictLabel(amJbxxImportVo.getRylb(),"AM_RYLB","",dictMap));//????????????*/
                /*auxDagl.setState(getDictLabel(auxDaglImportVo.getState(),"AUX_DASTATE","",dictMap));//??????????????????*/
                auxDagl.setState("1");//??????????????????
                auxDagl.setFjType(getDictLabel(auxDaglImportVo.getFjType(), "AUX_FJLB", "", dictMap));//????????????

                auxDagl.setRemark(auxDaglImportVo.getRemark());
                auxDagl.setOrgId(organization.getOrgId());//????????????ID
                List<Organization> orgName = organizationService.findOrgName(organization.getOrgId());
                auxDagl.setOrgName(orgName.get(0).getOrgName());//???????????????


                if (oldAuxDagl.size() == 0) {
                    auxDaglDAO.add(auxDagl);
                    //??????????????????
                    /*hrJbxxService.updateByAmJbxx(a);*/
                } else {
                    if ("1".equals(isJbxxCover)) {
                        //??????
                        auxDaglDAO.update(auxDagl);

                    } else if ("2".equals(isJbxxCover)) {
                        /*auxDaglDAO.delete(oldAuxDagl.get(0).getDaId());*/
                        auxDaglDAO.updatePart(auxDagl);
                    }
                }


                if (oldAuxDagl.size() == 0) {
                    successNum++;
                    successMsg.append("<br/>" + successNum + "????????? " + auxDaglImportVo.getXm() + " ????????????");
                } else {
                    if ("1".equals(isJbxxCover) || "2".equals(isJbxxCover)) {
                        successNum++;
                        successMsg.append("<br/>" + successNum + "????????? " + auxDaglImportVo.getXm() + " ????????????");
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "????????? " + auxDaglImportVo.getXm() + " ???????????????";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "??????????????? " + successNum + " ??????????????????????????? " + failureNum + " ??????????????????????????????????????????");
            return failureMsg.toString();
            //throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "????????????????????????????????????????????? " + successNum + " ?????????????????????");
        }
        return successMsg.toString();
    }

    @Override
    public List<AuxDaglImportVo> searchExport(AuxDaglSearchCriteria criteria) {
        List<AuxDaglImportVo> auxDaglImportVoList = new ArrayList<AuxDaglImportVo>();
        if (criteria == null) {
            return auxDaglImportVoList;
        }
        return auxDaglDAO.searchExport(criteria);
    }

    @Override
    public Integer findByOrgId(Long orgId, String fjType) {
        return auxDaglDAO.findByOrgId(orgId, fjType);
    }

    @Override
    public void addZl(AuxDagl auxDagl) {

        //????????????
        int sexByCertId = IdCardNumberVerify.getSexByCertId(auxDagl.getIdcard());
        auxDagl.setXb(String.valueOf(sexByCertId));
        auxDagl.setCreateTime(Common.getCurrentTime());
        try {
            auxDagl.setDaId(IdCreator.createId("daId"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        //??????????????????
        String fjbh = "FJ" + auxDagl.getFjbh();
        auxDagl.setFjbh(fjbh);
        auxDaglDAO.add(auxDagl);

    }

    @Override
    public void updateYfgz(AuxDagl auxDagl) {
        auxDaglDAO.updateYfgz(auxDagl);
    }

    @Override
    public List<AuxDagl> rytj2(AuxDaglSearchCriteria criteria) {
        return auxDaglDAO.rytj2(criteria);
    }

    @Override
    public List<AuxDagl> rytj(AuxDaglSearchCriteria criteria) {
        return auxDaglDAO.rytj(criteria);
    }

    @Override
    public long getSearchResultCount2(AuxDaglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        System.out.println(criteria.toString());
        String count = "0";

        try {
            count = Long.toString(auxDaglDAO.getSearchResultCount2(criteria));
        }catch (Exception e){
            return 0;
        }
        return Long.parseLong(count);
    }

    @Override
    public List<AuxDagl> rytj4(AuxDaglSearchCriteria criteria) {
        return auxDaglDAO.rytj4(criteria);
    }

    @Override
    public List<AuxDagl> rytj3(AuxDaglSearchCriteria criteria) {
        return auxDaglDAO.rytj3(criteria);
    }

    @Override
    public long getSearchResultCount3(AuxDaglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        System.out.println(criteria.toString());
        String count = "0";

        try {
            count = Long.toString(auxDaglDAO.getSearchResultCount3(criteria));
        }catch (Exception e){
            return 0;
        }
        return Long.parseLong(count);
    }

    @Override
    public long getSearchResultCount4(AuxDaglSearchCriteria criteria) {
        if (criteria == null) {
            return 0;
        }
        System.out.println(criteria.toString());
        String count = "0";

        try {
            count = Long.toString(auxDaglDAO.getSearchResultCount4(criteria));
        }catch (Exception e){
            return 0;
        }
        return Long.parseLong(count);
    }

    @Override
    public List<AuxDagl> rytj7(AuxDaglSearchCriteria criteria) {
        return auxDaglDAO.rytj7(criteria);
    }

    @Override
    public List<AuxDagl> rytj5(AuxDaglSearchCriteria criteria) {
        return auxDaglDAO.rytj5(criteria);
    }

    /*@Override
    public List<AuxDagl> rytj2() {
        return auxDaglDAO.rytj2();
    }

    @Override
    public List<AuxDagl> rytj() {
        return auxDaglDAO.rytj();
    }*/


    /**
     * ????????????????????????????????????code
     *
     * @param value        ?????????????????????????????????
     * @param type         ?????????AM_STATE
     * @param defaultLabel ??????1
     * @param dictMap
     * @return
     */
    public static String getDictLabel(String value, String type, String defaultLabel, Map<String, List<DictData>> dictMap) {
        List<DictData> dictDataList = dictMap.get(type);
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (DictData dictData : dictDataList) {
                if (value.equals(dictData.getDataName())) {
                    return dictData.getDataCode();
                }
            }
        }
        return defaultLabel;
    }


}
