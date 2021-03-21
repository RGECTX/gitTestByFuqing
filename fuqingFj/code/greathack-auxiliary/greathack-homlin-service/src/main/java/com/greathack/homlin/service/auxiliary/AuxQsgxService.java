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
            throw new BusinessException("导入亲属关系信息不能为空！");
        }

        List<Dict> dictList=dictService.getList(SystemConfig.getConfigData("DICT_APP_CODE"));
        Map<String,List<DictData>> dictMap=new HashMap<>();
        for(Dict dict:dictList){
            List<DictData> dictDataList=dictDataService.getList(dict.getDictId());
            dictMap.put(dict.getDictCode()+"",dictDataList);
        }

        Long createBy=Long.parseLong(params.get("createBy").toString());
        String isDelete=params.get("isDelete").toString();//0、直接新增  1、先删除子表信息


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
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + auxQsgxImportVo.getIdcard()+ " 身份证号不能为空");
                    continue;
                }

                Organization bjOrganization=organizationDAO.findByOrgCode(auxQsgxImportVo.getBjOrgCode());//本局工作单位编码


                if(!DateUtils.isDateString(auxQsgxImportVo.getBirthdayStr())){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + auxQsgxImportVo.getIdcard()+ " 出生日期格式错误");
                    continue;
                }



                // 验证人员基本信息是否存在
                List<AuxDagl> oldAuxDaglList = auxDaglDAO.findIdcard(auxQsgxImportVo.getIdcard());
                if(oldAuxDaglList.size()==0){
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、身份证号 " + auxQsgxImportVo.getIdcard()+ " 尚未有该人员的基本信息");
                    continue;
                }


                AuxQsgx qsgx =new AuxQsgx();
                qsgx.setCreateTime(Common.getCurrentTime());
                qsgx.setCreateBy(createBy);
                qsgx.setId(IdCreator.createId("AuxQsgx"));
                /*qsgx.setAppCode(SystemConfig.getConfigData("AUXILIARY_APP_CODE"));*/
                qsgx.setInstanceId(oldAuxDaglList.get(0).getDaId()+"");
                /*qsgx.setIdcard(auxQsgxImportVo.getIdcard());//身份证号*/

                qsgx.setQsname(auxQsgxImportVo.getQsname());//亲属姓名
                qsgx.setOwnIdcard(auxQsgxImportVo.getOwnIdcard());//亲属身份证号码
                qsgx.setKinshipTerm(getDictLabel(auxQsgxImportVo.getKinshipTerm(),"APPELLATION","",dictMap));//称谓
                if(auxQsgxImportVo.getBirthdayStr()!=null){
                    String birthday=auxQsgxImportVo.getBirthdayStr();
                    birthday = DateUtils.formateDateStrTo8(birthday);
                    if(birthday!=null){
                        qsgx.setBirthdayStr(birthday);//出生日期
                    }
                }
                qsgx.setWorkUnit(auxQsgxImportVo.getWorkUnit());//工作单位
                qsgx.setPost(auxQsgxImportVo.getPost());//职务
                qsgx.setNowStatus(getDictLabel(auxQsgxImportVo.getNowStatus(),"AM_REALTION_NOW_STATUS","",dictMap));//现状
                qsgx.setHealthState(getDictLabel(auxQsgxImportVo.getHealthState(),"AM_HEALTH_STATE","",dictMap));//健康状况
                qsgx.setEduLevel(getDictLabel(auxQsgxImportVo.getEduLevel(),"AM_XL","",dictMap));//学历
                qsgx.setHealthDetail(auxQsgxImportVo.getHealthDetail());//健康说明
                String kjysgbFlag=getDictLabel(auxQsgxImportVo.getKjysgbFlag(),"KJYSGB_FLAG","",dictMap);
                if(StringUtils.isNotEmpty(kjysgbFlag)){
                    qsgx.setKjysgbFlag(kjysgbFlag);//科级以上干部
                }
                String cgjFlag=auxQsgxImportVo.getCgjFlag();
                if(StringUtils.isNotEmpty(cgjFlag)){
                    if("无".equals(cgjFlag)){
                        qsgx.setCgjFlag("1");//出国境
                    }else if("有".equals(cgjFlag)){
                        qsgx.setCgjFlag("2");//出国境
                    }
                }
                String fzFlag=auxQsgxImportVo.getFzFlag();
                if(StringUtils.isNotEmpty(fzFlag)){
                    if("无".equals(fzFlag)){
                        qsgx.setFzFlag("1");//犯罪
                    }else if("有".equals(fzFlag)){
                        qsgx.setFzFlag("2");//犯罪
                    }
                }
                String sysjFlag=auxQsgxImportVo.getSysjFlag();
                if(StringUtils.isNotEmpty(sysjFlag)){
                    if("无".equals(sysjFlag)){
                        qsgx.setSysjFlag("1");//收押收教
                    }else if("有".equals(sysjFlag)){
                        qsgx.setSysjFlag("2");//收押收教
                    }
                }
                String rhflFlag=auxQsgxImportVo.getRhflFlag();
                if(StringUtils.isNotEmpty(rhflFlag)){
                    if("无".equals(rhflFlag)){
                        qsgx.setRhflFlag("1");//人户分离
                    }else if("有".equals(rhflFlag)){
                        qsgx.setRhflFlag("2");//人户分离
                    }
                }

                String bjgzFlag=getDictLabel(auxQsgxImportVo.getBjgzFlag(),"BJGZ_FLAG","",dictMap);
                if(StringUtils.isNotEmpty(bjgzFlag)){
                    qsgx.setBjgzFlag(bjgzFlag);//本局人员编制类别
                }
                if(bjOrganization!=null){//本局工作部门编码
                    qsgx.setBjOrgId(bjOrganization.getOrgId());
                }
                qsgx.setGzddSheng(auxQsgxImportVo.getGzddSheng());//工作地省
                qsgx.setGzddShi(auxQsgxImportVo.getGzddShi());//工作地市
                qsgx.setGzddXian(auxQsgxImportVo.getGzddXian());//工作地县
                qsgx.setGzddAddress(auxQsgxImportVo.getGzddAddress());//工作地详细地址
                String gzddssfw=getDictLabel(auxQsgxImportVo.getGzddssfw(),"DDSSFW","",dictMap);
                if(StringUtils.isNotEmpty(gzddssfw)){
                    qsgx.setGzddssfw(gzddssfw);//工作地点所属范围
                }
                String gzdwxz=getDictLabel(auxQsgxImportVo.getGzdwxz(),"GZDWXZ","",dictMap);
                if(StringUtils.isNotEmpty(gzdwxz)){
                    qsgx.setGzdwxz(gzdwxz);//工作单位性质
                }
                qsgx.setJzddSheng(auxQsgxImportVo.getJzddSheng());//居住地省
                qsgx.setJzddShi(auxQsgxImportVo.getJzddShi());//居住地市
                qsgx.setJzddXian(auxQsgxImportVo.getJzddXian());//居住地县
                qsgx.setJzddAddress(auxQsgxImportVo.getJzddAddress());//居住地详细地址
                String jzddssfw=getDictLabel(auxQsgxImportVo.getJzddssfw(),"DDSSFW","",dictMap);
                if(StringUtils.isNotEmpty(jzddssfw)){
                    qsgx.setJzddssfw(jzddssfw);//居住地点所属范围
                }
                qsgx.setOthers(auxQsgxImportVo.getOthers());//其他

                auxQsgxDAO.add(qsgx);

                if("1".equals(isDelete)){//先删除子表信息
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
                successMsg.append("<br/>" + successNum + "、 身份证号" + auxQsgxImportVo.getIdcard() + " 导入成功");


            }catch (Exception e){
                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "、身份证号 " + auxQsgxImportVo.getIdcard() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                //log.error(msg, e);
            }
        }
        if (failureNum > 0){
            failureMsg.insert(0, "很抱歉！共 "+successNum+" 条数据导入成功！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
            //throw new BusinessException(failureMsg.toString());
        }else{
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
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
     * 通过类型及字典中文名获得code
     * @param value  值如：在岗（正式辅警）
     * @param type 类型如AM_STATE
     * @param defaultLabel 如：1
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
