package com.greathack.homlin.pojo.auxiliary;

import com.greathack.homlin.pojo.system.SearchCriteria;
import com.greathack.utils.tools.HashUtils;

/**
 * @author huangmh
 * @Description
 * @date 2020-08-03 17:35:28
 */
public class AuxNoticeSearchCriteria extends SearchCriteria {

    /**
     * 通知标题
     */
    private static final int KW_FIELDS_NOTICETITLE = 1;

    /**
     * 通知内容
     */
    private static final int KW_FIELDS_NoticeTEXT = 2;

    /**
     * 备注字段
     */
    public static final int KW_FIELDS_REMARK = 4;

    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    protected String getKwFieldName(int field) {
        switch (field) {
            case AuxNoticeSearchCriteria.KW_FIELDS_NOTICETITLE:
                return "noticeTitle";
            case AuxNoticeSearchCriteria.KW_FIELDS_NoticeTEXT:
                return "noticeText";
            case AuxNoticeSearchCriteria.KW_FIELDS_REMARK:
                return "remark";
            default:
                return "";
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((state == null) ? 0 : HashUtils.fnv1_32_hash(state.toString()));
        return result;
    }

}
