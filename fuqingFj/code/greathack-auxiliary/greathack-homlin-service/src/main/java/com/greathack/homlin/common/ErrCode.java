package com.greathack.homlin.common;

public class ErrCode {

    public final static int UNKNOWN_ERROR = 500;//未知错误
    public final static int INVALID_PARAMS = 400001;//无效参数
    public final static int MENU_NOT_EXIST = 400010;//无效参数

    public final static int JBXX_ID_IS_REQUIRE = 400026;//基本信息ID必填

    public static final int UPLOAD_FAIL = 400053;//上传失败

    public static final int CLASS_NOT_FOUND = 400026;//类不存在

    public static final int SCHEDULE_TASK_NOT_EXIST = 400027;//定时任务不存在

    public static final int JYXLGL_TRAINID_IS_REQUIRED = 400030;//培训预约id必填

    public static final int JYXLGL_TRAIN_NOT_EXIST = 400030;//培训预约不存在

    public static final int JYXLGL_TRAIN_RECORDID_IS_REQUIRED = 400030;//培训预约记录id必填

    public static final int JYXLGL_TRAIN_RECORD_USERID_EXIST = 400030;//培训预约记录人员已存在

    public static final int JYXLGL_TRAIN_RESERVE_ABORT = 400030;//培训预约截止报名

    public static final int JYXLGL_TRAIN_RESERVENUM_HAVELIMIT = 400030;//培训预约人数已上限

    public static final int JYXLGL_ASSESSID_IS_REQUIRED = 400030;//考核预约id必填

    public static final int JYXLGL_ASSESS_NOT_EXIST = 400030;//考核预约不存在

    public static final int JYXLGL_ASSESS_RECORDID_IS_REQUIRED = 400030;//考核预约记录id必填

    public static final int JYXLGL_ASSESS_RECORD_NOT_EXIST = 400030;//考核预约记录不存在

    public static final int JYXLGL_ASSESS_RECORD_USERID_EXIST = 400030;//考核预约记录人员已存在

    public static final int JYXLGL_ASSESS_RESERVE_ABORT = 400030;//考核预约截止报名

    public static final int JYXLGL_ASSESS_RESERVENUM_HAVELIMIT = 400030;//考核预约人数已上限

    public static final int JYXLGL_ASSESS_PROJECTTYPE_NOT_EXIST = 400030;//考核项目类别不存在

    public static final int JYXLGL_ASSESS_PROJECTTYPE_IS_REQUIRED = 400030;//考核项目类别id必填

    public static final int JYXLGL_TRAINGYMID_IS_REQUIRED = 400030;//训练馆预约id必填

    public static final int JYXLGL_TRAIN_GYM_NOT_EXIST = 400030;//训练馆预约不存在

    public static final int JYXLGL_TRAIN_GYM_RECORDID_IS_REQUIRED = 400030;//训练馆预约记录id必填

    public static final int JYXLGL_TRAIN_GYM_RECORD_USERID_EXIST = 400030;//训练馆预约记录人员已存在

    public static final int JYXLGL_TRAIN_GYM_RECORD_NOT_EXIST = 400030;//训练馆预约记录不存在

    public static final int JYXLGL_TRAIN_GYM_RESERVENUM_HAVELIMIT = 400030;//训练馆预约人数已上限

    public static final int JYXLGL_TRAIN_GYM_RESERVE_ABORT = 400030;//训练馆预约截止报名

    public static final int JYXLGL_TRAIN_GYM_COUNT_USERID_EXIST = 400030;//训练馆预约次数人员已存在

    public static final int JYXLGL_TRAIN_GYM_COUNTID_IS_REQUIRED = 400030;//训练馆预约次数id必填

    public static final int JYXLGL_TRAIN_GYM_COUNT_NOT_EXIST = 400030;//训练馆预约次数不存在

    public static final int JYXLGL_TRAIN_GYM_COUNT_STATE_DISABLE = 400030;//训练馆预约次数状态禁用

    public static final int JYXLGL_TRAIN_GYM_COUNT_SURPLUSCOUNT_DEFICIENCY = 400030;//训练馆预约次数剩余次数不足

    public static final int JYXLGL_INSTRUCTORID_IS_REQUIRED = 400030;//教官资料id必填

    public static final int JYXLGL_INSTRUCTOR_NOT_EXIST = 400030;//教官资料不存在

    public static final int JYXLGL_SETID_IS_REQUIRED = 400030;//考核成绩设置id必填

    public static final int JYXLGL_RESULT_SET_NOT_EXIST = 400030;//考核成绩设置不存在

    public static final int JYXLGL_RESULT_SET_IN_USE = 400030;//考核成绩设置已被使用

    public static final int JYXLGL_ENTRYID_IS_REQUIRED = 400030;//考核成绩录入id必填

    public static final int JYXLGL_RESULT_ENTRY_NOT_EXIST = 400030;//考核成绩录入不存在

    public static final int JYXLGL_RESULT_ENTRY_USERID_EXIST = 400030;//考核成绩录入人员已存在

    public static final int JYXLGL_ARCHIVESID_IS_REQUIRED = 400030;//训练档案id必填

    public static final int JYXLGL_TRAIN_ARCHIVES_NOT_EXIST = 400030;//训练档案不存在

    public static final int JYXLGL_NOTICEID_IS_REQUIRED = 400030;//通知通告id必填

    public static final int JYXLGL_NOTICE_NOT_EXIST = 400030;//通知通告不存在

    public static final int JYXLGL_DATAID_IS_REQUIRED = 400030;//培训资料id必填

    public static final int JYXLGL_TRAINDATA_NOT_EXIST = 400030;//培训资料不存在

    public static final int HMD_NOT_EXIST = 400030;//黑名单信息不存在

    public static final int ZLJD_NOT_EXIST = 400030;//招录建档信息不存在

    public static final int DAGL_NOT_EXIST = 400030;//档案管理信息不存在

    public static final int JYGL_NOT_EXIST = 400030;//减员管理信息不存在

    public static final int JYGL_PROGRESS_STARTED = 400030;//减员管理流程已经开始

    public static final int JYGL_PROGRESS_COMPLETED = 400030;//减员管理流程已经结束

    public static final int ZLJD_PROGRESS_STARTED = 400030;//招录建档流程已经开始

    public static final int ZLJD_PROGRESS_COMPLETED = 400030;//招录建档流程已经结束

    public static final int HMD_FORBID_ADD_ZLJD = 400030;//黑名单禁止添加招录建档

    public static final int KQXX_NOT_EXIST = 400030;//考勤信息不存在

    public static final int APPLICATION_FOR_LEAVE_NOT_EXIST = 400030;//请假申请信息不存在

    public static final int THERE_ARE_THE_SAME_DATA_IN_RECORD = 400030;//档案管理已存在相同的登记信息

    public static final int DAGL_DATAID_IS_REQUIRED = 400030;//档案管理ID必填

    public static final int SALARY_NOT_EXIST = 400030;//工资管理信息不存在

    public static final int OCCUPATIONAL_INJURY_NOT_EXIST = 400030;//工伤信息不存在

    public static final int APP_CODE_IS_REQUIRE = 400030;//appCode必填

    public final static int INSTANCE_ID_IS_REQUIRE=400030;//主体ID必填

    public final static int AUX_NDKH_ID_IS_REQUIRE=400030;//年度考核ID必填

    public final static int AUX_NDKH_NOT_EXIST=400030;//年度考核不存在

    public final static int AUX_YDKH_ID_IS_REQUIRE=400030;//月度考核ID必填

    public final static int AUX_YDKH_NOT_EXIST=400030;//月度考核不存在

    public final static int BZGL_PROGRESS_STARTED=400030;//被装管理已经开始

    public final static int BZGL_PROGRESS_COMPLETED=400030;//被装管理已经结束

    public final static int AUX_QSGX_ID_IS_REQUIRE=400030;//亲属关系ID必填

    public final static int AUX_QSGX_NOT_EXIST=400030;//亲属关系不存在

    public final static int AMPAYROLLID_IS_REQUIRED=400030;//辅警工资记录id必填

    public final static int AMPAYROLL_NOT_EXIST=400030;//辅警工资记录id必填

    public final static int AMPAYROLL_EXISTED=400030;//辅警工资记录id必填

    public final static int AUX_SJQK_ID_IS_REQUIRE=400030;//授奖情况信息id必填

    public final static int AUX_SJQK_NOT_EXIST=400030;//授奖情况不存在

    public static final int AUX_NOTICEID_IS_REQUIRED = 400030;//通知通告id必填

    public static final int AUX_NOTICE_NOT_EXIST = 400030;//通知通告不存在

}
