package com.greathack.homlin.service.auxiliary;

import com.greathack.homlin.common.Common;
import com.greathack.homlin.common.ErrCode;
import com.greathack.homlin.dao.factory.DAOFactory;
import com.greathack.homlin.daointerface.auxiliary.IAuxDaglDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxNdkhDAO;
import com.greathack.homlin.daointerface.auxiliary.IAuxQsgxDAO;
import com.greathack.homlin.daointerface.org.IOrganizationDAO;
import com.greathack.homlin.exception.BusinessException;
import com.greathack.homlin.exception.ServiceException;
import com.greathack.homlin.pojo.auxiliary.*;
import com.greathack.homlin.pojo.dict.Dict;
import com.greathack.homlin.pojo.dict.DictData;
import com.greathack.homlin.pojo.org.Organization;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxNdkhService;
import com.greathack.homlin.serviceinterface.auxiliary.IAuxQsgxService;
import com.greathack.homlin.serviceinterface.dict.IDictDataService;
import com.greathack.homlin.serviceinterface.dict.IDictService;
import com.greathack.homlin.system.IdCreator;
import com.greathack.homlin.system.SystemConfig;
import com.greathack.homlin.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuxQsgxService implements IAuxQsgxService {

    @Autowired
    private IDictService dictService;
    @Autowired
    private IDictDataService dictDataService;

    private static IAuxDaglDAO auxDaglDAO = (IAuxDaglDAO) DAOFactory.createDAO("IAuxDaglDAO");
    private static IAuxQsgxDAO auxQsgxDAO = (IAuxQsgxDAO) DAOFactory.createDAO("IAuxQsgxDAO");
    private static IOrganizationDAO organizationDAO = (IOrganizationDAO) DAOFactory.createDAO("IOrganizationDAO");


    @Override
    public AuxQsgx add(AuxQsgx auxQsgx) throws ServiceException {
        if (auxQsgx.getInstanceId() == null) {
            throw new ServiceException(ErrCode.INSTANCE_ID_IS_REQUIRE, "INSTANCE_ID_IS_REQUIRE");
        }
        try {
            auxQsgx.setCreateTime(Common.getCurrentTime());
            auxQsgx.setId(IdCreator.createId("auxQsgx"));
            auxQsgxDAO.add(auxQsgx);
            return auxQsgx;
        } catch (ServiceException e) {
            throw new ServiceException(ErrCode.UNKNOWN_ERROR, "UNKNOWN_ERROR");
        }
    }

    @Override
    public void delete(Long id) {
        auxQsgxDAO.delete(id);
    }

    @Override
    public void update(AuxQsgx auxQsgx) {
        auxQsgxDAO.update(auxQsgx);
    }

    @Override
    public AuxQsgx get(Long id) {
        return auxQsgxDAO.findById(id);
    }

    @Override
    public List<AuxQsgx> findByExample(AuxQsgx auxQsgx) {
        return auxQsgxDAO.findByExample(auxQsgx);
    }

    @Override
    public List<AuxQsgx> findAll() {
        return auxQsgxDAO.findAll();
    }

    @Override
    public String importAuxQsgx(List<AuxQsgxImportVo> auxQsgxImportVoList, Map<String, Object> params) {
        if (auxQsgxImportVoList==null || auxQsgxImportVoList.size() == 0)
        {
            throw new BusinessException("???????????????????????????????????????");
        }

        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String,List<DictData>> dictMap=new HashMap<>();
        for(Dict dict:dictList){
            List<DictData> dictDataList=dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode()+"",dictDataList);
        }

        Long createBy=Long.parseLong(params.get("createBy").toString());
        String isDelete=params.get("isDelete").toString();//0???????????????  1????????????????????????


        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        AuxQsgx auxQsgx=new AuxQsgx();
        auxQsgx.setCreateTime(Common.getCurrentTime());
        for (AuxQsgxImportVo auxQsgxImportVo : auxQsgxImportVoList){
            try
            {
                if(StringUtils.isEmpty(auxQsgxImportVo.getIdcard())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "??????????????? " + auxQsgxImportVo.getIdcard()+ " ????????????????????????");
                    continue;
                }

                Organization bjOrganization=organizationDAO.findByOrgCode(auxQsgxImportVo.getBjOrgCode());//????????????????????????


                if(!DateUtils.isDateString(auxQsgxImportVo.getBirthdayStr())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "??????????????? " + auxQsgxImportVo.getIdcard()+ " ????????????????????????");
                    continue;
                }



                // ????????????????????????????????????
                List<AuxDagl> oldAuxDaglList = auxDaglDAO.findIdcard(auxQsgxImportVo.getIdcard());
                if(oldAuxDaglList.size()==0){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "??????????????? " + auxQsgxImportVo.getIdcard()+ " ?????????????????????????????????");
                    continue;
                }


                AuxQsgx qsgx =new AuxQsgx();
                qsgx.setCreateTime(Common.getCurrentTime());
                qsgx.setCreateBy(createBy);
                qsgx.setId(IdCreator.createId("AuxQsgx"));
                /*qsgx.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));*/
                qsgx.setInstanceId(oldAuxDaglList.get(0).getDaId()+"");
                /*qsgx.setIdcard(auxQsgxImportVo.getIdcard());//????????????*/

                qsgx.setQsname(auxQsgxImportVo.getQsname());//????????????
                qsgx.setOwnIdcard(auxQsgxImportVo.getOwnIdcard());//?????????????????????
                qsgx.setKinshipTerm(getDictLabel(auxQsgxImportVo.getKinshipTerm(),"APPELLATION","",dictMap));//??????
                if(auxQsgxImportVo.getBirthdayStr()!=null){
                    String birthday=auxQsgxImportVo.getBirthdayStr();
                    birthday = DateUtils.formateDateStrTo8(birthday);
                    if(birthday!=null){
                        qsgx.setBirthdayStr(birthday);//????????????
                    }
                }
                qsgx.setWorkUnit(auxQsgxImportVo.getWorkUnit());//????????????
                qsgx.setPost(auxQsgxImportVo.getPost());//??????
                qsgx.setNowStatus(getDictLabel(auxQsgxImportVo.getNowStatus(),"AM_REALTION_NOW_STATUS","",dictMap));//??????
                qsgx.setHealthState(getDictLabel(auxQsgxImportVo.getHealthState(),"AM_HEALTH_STATE","",dictMap));//????????????
                qsgx.setEduLevel(getDictLabel(auxQsgxImportVo.getEduLevel(),"AM_XL","",dictMap));//??????
                qsgx.setHealthDetail(auxQsgxImportVo.getHealthDetail());//????????????
                String kjysgbFlag=getDictLabel(auxQsgxImportVo.getKjysgbFlag(),"KJYSGB_FLAG","",dictMap);
                if(StringUtils.isNotEmpty(kjysgbFlag)){
                    qsgx.setKjysgbFlag(kjysgbFlag);//??????????????????
                }
                String cgjFlag=auxQsgxImportVo.getCgjFlag();
                if(StringUtils.isNotEmpty(cgjFlag)){
                    if("???".equals(cgjFlag)){
                        qsgx.setCgjFlag("1");//?????????
                    }else if("???".equals(cgjFlag)){
                        qsgx.setCgjFlag("2");//?????????
                    }
                }
                String fzFlag=auxQsgxImportVo.getFzFlag();
                if(StringUtils.isNotEmpty(fzFlag)){
                    if("???".equals(fzFlag)){
                        qsgx.setFzFlag("1");//??????
                    }else if("???".equals(fzFlag)){
                        qsgx.setFzFlag("2");//??????
                    }
                }
                String sysjFlag=auxQsgxImportVo.getSysjFlag();
                if(StringUtils.isNotEmpty(sysjFlag)){
                    if("???".equals(sysjFlag)){
                        qsgx.setSysjFlag("1");//????????????
                    }else if("???".equals(sysjFlag)){
                        qsgx.setSysjFlag("2");//????????????
                    }
                }
                String rhflFlag=auxQsgxImportVo.getRhflFlag();
                if(StringUtils.isNotEmpty(rhflFlag)){
                    if("???".equals(rhflFlag)){
                        qsgx.setRhflFlag("1");//????????????
                    }else if("???".equals(rhflFlag)){
                        qsgx.setRhflFlag("2");//????????????
                    }
                }

                String bjgzFlag=getDictLabel(auxQsgxImportVo.getBjgzFlag(),"BJGZ_FLAG","",dictMap);
                if(StringUtils.isNotEmpty(bjgzFlag)){
                    qsgx.setBjgzFlag(bjgzFlag);//????????????????????????
                }
                if(bjOrganization!=null){//????????????????????????
                    qsgx.setBjOrgId(bjOrganization.getOrgId());
                }
                qsgx.setGzddSheng(auxQsgxImportVo.getGzddSheng());//????????????
                qsgx.setGzddShi(auxQsgxImportVo.getGzddShi());//????????????
                qsgx.setGzddXian(auxQsgxImportVo.getGzddXian());//????????????
                qsgx.setGzddAddress(auxQsgxImportVo.getGzddAddress());//?????????????????????
                String gzddssfw=getDictLabel(auxQsgxImportVo.getGzddssfw(),"DDSSFW","",dictMap);
                if(StringUtils.isNotEmpty(gzddssfw)){
                    qsgx.setGzddssfw(gzddssfw);//????????????????????????
                }
                String gzdwxz=getDictLabel(auxQsgxImportVo.getGzdwxz(),"GZDWXZ","",dictMap);
                if(StringUtils.isNotEmpty(gzdwxz)){
                    qsgx.setGzdwxz(gzdwxz);//??????????????????
                }
                qsgx.setJzddSheng(auxQsgxImportVo.getJzddSheng());//????????????
                qsgx.setJzddShi(auxQsgxImportVo.getJzddShi());//????????????
                qsgx.setJzddXian(auxQsgxImportVo.getJzddXian());//????????????
                qsgx.setJzddAddress(auxQsgxImportVo.getJzddAddress());//?????????????????????
                String jzddssfw=getDictLabel(auxQsgxImportVo.getJzddssfw(),"DDSSFW","",dictMap);
                if(StringUtils.isNotEmpty(jzddssfw)){
                    qsgx.setJzddssfw(jzddssfw);//????????????????????????
                }
                qsgx.setOthers(auxQsgxImportVo.getOthers());//??????

                auxQsgxDAO.add(qsgx);

                if("1".equals(isDelete)){//?????????????????????
                    auxQsgx.setInstanceId(oldAuxDaglList.get(0).getDaId()+"");
                    List<AuxQsgx> auxQsgxList=auxQsgxDAO.findByExample(auxQsgx);
                    if(auxQsgxList.size()>0){
                        for(AuxQsgx ax:auxQsgxList){
                            auxQsgxDAO.delete(ax.getId());
                        }
                    }
                    auxQsgxDAO.add(qsgx);
                }

                successNum++;
                successMsg.append("<br/>" + successNum + "??? ????????????" + auxQsgxImportVo.getIdcard() + " ????????????");


            }catch (Exception e){
                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "??????????????? " + auxQsgxImportVo.getIdcard() + " ???????????????";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0){
            failureMsg.insert(0, "??????????????? "+successNum+" ??????????????????????????? " + failureNum + " ??????????????????????????????????????????");
            return failureMsg.toString();
            //throw new BusinessException(failureMsg.toString());
        }else{
            successMsg.insert(0, "????????????????????????????????????????????? " + successNum + " ?????????????????????");
        }
        return successMsg.toString();
    }

    @Override
    public List<AuxQsgxImportVo> searchExport(AuxDaglSearchCriteria criteria) {
        List<AuxQsgxImportVo> auxQsgxImportVoList = new ArrayList<AuxQsgxImportVo>();
        if (criteria == null) {
            return auxQsgxImportVoList;
        }
        return auxQsgxDAO.searchExport(criteria);
    }

    /**
     *
     * ????????????????????????????????????code
     * @param value  ?????????????????????????????????
     * @param type ?????????AM_STATE
     * @param defaultLabel ??????1
     * @param dictMap
     * @return
     */
    public static String getDictLabel(String value, String type, String defaultLabel, Map<String,List<DictData>> dictMap){
        List<DictData> dictDataList=dictMap.get(type);
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
            for (DictData dictData : dictDataList){
                if (value.equals(dictData.getDataName())){
                    return dictData.getDataCode();
                }
            }
        }
        return defaultLabel;
    }
}
