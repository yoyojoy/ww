package com.cs.enums;

/**
 * Created by lucash on 2017/7/8.
 */
public enum ResponseCodeEnum {

    SUCCESS("操作成功", 10000),
    SYS_ERROR("系统异常", 10001),
    NULL_ID_ERROR("主键ID不能为空", 10002),
    NULL_VERSION_ERROR("版本号不能为空", 10003),
    NULL_USER_ID_ERROR("用户ID不能为空", 10004),

    /**
     * 放款业务异常代码 (20000)
     */
    LOAN_QUERY_ERROR("查询待更新数据失败", 20001),
    LOAN_PROJECT_QUERY_ERROR("查询放款数据失败", 20002),
    LOAN_EXCEL_QUERY_ERROR("查询导出数据失败", 20003),
    LOAN_PARSE_FILE_ERROR("解析导入文件失败", 20004),
    LOAN_IMPORT_FILE_FORMAT_ERROR("导入文件格式不正确", 20005),
    LOAN_READ_FILE_ERROR("读取导入文件失败", 20006),
    LOAN_IMPORT_FILE_ERROR("上传文件失败", 20007),
    LOAN_UPDATE_STATUS_ERROR("更新放款状态失败", 20008),
    LOAN_QUERY_BASE_INFO_ERROR("查询贷款项目基本信息失败", 20009),
    LOAN_BASE_STATUS_ERROR("索回数据状态不匹配", 20010),
    LOAN_RECOVER_CARD_ERROR("索回银行卡号不能为空", 20011),
    LOAN_RECOVER_AMOUNT_ERROR("索回金额不正确", 20012),
    LOAN_RECOVER_ERROR("索回请求失败", 20013),
    LOAN_RECOVER_INSERT_DATA_ERROR("索回请求成功, 入库失败", 20014),
    LOAN_RECOVER_ING_ERROR("索回正在进行中, 请等待", 20015),
    LOAN_RECOVER_SUCCESS_ERROR("索回成功, 请勿重复操作", 20016),

    /**
     * 借款人提现异常代码 (30000)
     */
    WITH_DRAW_QUERY_ERROR("查询提现数据失败", 30001),
    WITH_DRAW_QUERY_TARGET_ERROR("查询目标提现数据失败", 30002),
    WITH_DRAW_INCORRECT_TARGET_ERROR("目标提现数据异常", 30003),
    WITH_DRAW_UPDATE_TARGET_ERROR("目标提现数据异常", 30004),
    WITH_DRAW_AMOUNT_ERROR("目标提现数据异常", 30005),
    WITH_DRAW_DATA_ERROR("请选择需要提现的数据", 30006),
    WITH_DRAW_QUERY_BORROWER_DATA_ERROR("查询出借人信息失败", 30007),
    WITH_DRAW_BORROWER_DATA_ERROR("查询出借人信息不全", 30008),
    WITH_DRAW_BORROWER_BANK_DATA_ERROR("查询出借人银行卡信息不全", 30009),
    WITH_DRAW_ERROR("提现失败", 30010),

    /**
     * 平台用户管理异常代码 (40000)
     */
    PLATFORM_USER_QUERY_BASE_ERROR("查询平台用户列表失败", 40001),
    PLATFORM_USER_QUERY_DETAIL_ERROR("查询平台用户列表失败", 40002),
    PLATFORM_USER_QUERY_CARD_ERROR("查询平台用户银行卡信息失败", 40003),
    PLATFORM_USER_ID_NULL_ERROR("平台用户ID不能为空", 40004),
    PLATFORM_USER_CARD_NO_NULL_ERROR("平台用户银行卡号不能为空", 40005),
    PLATFORM_USER_CARD_EMPTY_ERROR("平台用户银行卡为空", 40006),
    PLATFORM_USER_INFO_NULL_ERROR("平台用户信息不存在", 40007),

    /**
     * 系统配置 (50000)
     */
    SYS_CONFIG_QUERY_BASE_ERROR("查询平台配置信息列表失败", 50001),
    SYS_CONFIG_QUERY_DETAIL_ERROR("查询平台配置信息详情失败", 50002),
    SESAME_CREDIT_QUERY_BASE_ERROR("查询平台授信额度定义列表失败", 50003),
    SESAME_CREDIT_QUERY_DETAIL_ERROR("查询平台授信额度定义详情失败", 50004),
    THIRD_INTERFACE_QUERY_BASE_ERROR("查询平台第三方接口配置列表失败", 50005),
    THIRD_INTERFACE_QUERY_DETAIL_ERROR("查询平台第三方接口配置详情失败", 50006),

    /**
     * 风控审核异常代码 (60000)
     */
    RISK_CONTROL_AUDIT_QUERY_LIST_ERROR("查询待审核列表失败", 60001),
    RISK_CONTROL_AUDIT_QUERY_DETAIL_ERROR("查询审核详情失败", 60002),
    RISK_CONTROL_AUDIT_UPDATE_ERROR("更新审核状态失败", 60003),
    RISK_CONTROL_AUDIT_DATA_ERROR("审核数据异常", 60004),
    RISK_CONTROL_AUDIT_BUSY_ERROR("该审核订单已占用", 60005),
    RISK_CONTROL_AUDIT_STATUS_ERROR("审核结果不能为空", 60006),

    /**
     * 产品模块异常代码 (70000)
     */
    PRODUCT_QUERY_LIST_ERROR("查询平台产品列表失败", 70001),

    /**
     * 出借人信息异常代码 (80000)
     */
    BORROWER_QUERY_LIST_ERROR("查询出借人信息列表失败", 80001),

    /**
     * 平台用户通讯录信息异常代码 (90000)
     */
    PLATUSER_CONTACT_QUERY_LIST_ERROR("查询平台用户通讯录失败", 90001),

    /**
     * 平台还款信息异常代码 (100000)
     */
    REPAYMENT_QUERY_LIST_ERROR("查询平台还款记录失败", 100001);

    private final String message;
    private final Integer code;

    ResponseCodeEnum(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public static String getMsg(Integer code) {
        for (ResponseCodeEnum responseCodeEnum : ResponseCodeEnum.values()) {
            if (responseCodeEnum.getCode().equals(code)) {
                return responseCodeEnum.getMessage();
            }
        }

        return null;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
