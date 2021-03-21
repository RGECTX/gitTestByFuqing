package com.greathack.homlin.pojo.dict;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

public class DictSearchCriteria extends SearchCriteria {
    
    private String dictCode;

    /**
     * 字典名称字段
     */
    private static final int KW_FIELDS_DICT_NAME = 1;

    /**
     * 字典编码字段
     */
    private static final int KW_FIELDS_DICT_CODE = 2;

    /**
     * 备注字段
     */
    private static final int KW_FIELDS_REMARK = 4;

    /**
     * 备用1字段
     */
    private static final int KW_FIELDS_BAK1 = 8;

    /**
     * 备用2字段
     */
    private static final int KW_FIELDS_BAK2 = 16;


    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    @Override
    protected String getKwFieldName(int field) {
        switch (field) {
        case DictSearchCriteria.KW_FIELDS_DICT_NAME:
            return "dictName";
            
        case DictSearchCriteria.KW_FIELDS_DICT_CODE:
            return "dictCode";

        case DictSearchCriteria.KW_FIELDS_REMARK:
            return "remark";

        case DictSearchCriteria.KW_FIELDS_BAK1:
            return "bak1";

        case DictSearchCriteria.KW_FIELDS_BAK2:
            return "bak2";

        default:
            return "";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 32;
        int result = super.hashCode();
        result = prime * result + ((dictCode == null) ? 0 : HashUtils.fnv1_32_hash(dictCode));
        return result;
    }   

}
