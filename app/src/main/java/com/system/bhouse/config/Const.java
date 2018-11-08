package com.system.bhouse.config;

/**
 * Created by Administrator on 2018-07-26.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.config
 */

public class Const {

    public final static String SAVE_STATUS = "保存";

    public final static String CHECK_STATUS = "审核";

    public final static String SUBMIT_STATUS = "提交";

    public final static String Completed_STATUS="已完成";

    public final static String Executing_STATUS="执行中";

    public final static String Undone_STATUS="未完成";

    /**
     * toplistView 常量
     */
    public static final String[] LETTERS=new String[]{"单据信息", "录入人信息", "审核人信息"};

    /**
     * 段落分配判断  4-- 对应 第一个 单据信息 分组
     */
    public static final String[] key= {"4", "2", "2"};

    public static final String Entry_is_empty = "分录为空,不能提交";

    public static final String[] SimpleDataString={"yyyy","MM","dd","HH","mm","ss"};
}
