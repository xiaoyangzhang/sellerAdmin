package com.yimayhd.sellerAdmin.constant;

/**
 * @author wenfeng zhang @ 10/21/14
 */
public enum ResponseStatus {

    SUCCESS(200, "执行成功！"),
    ERROR(500, "服务器未知错误！"),
    UNAUTHORIZED(401, "没有登录！"),
    FORBIDDEN(403, "没有权限！"),
    NOT_FOUND(404, "资源不存在！"),
    DATA_PARSE_ERROR(4001, "JSON解析错误！"),
    INVALID_DATA(4002, "数据校验错误，请检查输入！"),
    PARAM_ERROR(4003, "不支持该商户的操作");

    public Integer VALUE;
    public String MESSAGE;

    ResponseStatus(Integer value, String message) {
        this.VALUE = value;
        this.MESSAGE = message;
    }

}
