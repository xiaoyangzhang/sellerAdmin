package com.yimayhd.sellerAdmin.exception;


import com.yimayhd.sellerAdmin.base.BaseException;

/**
 * @author liuhaiming
 */
public class NoticeException extends BaseException {

    private static final long serialVersionUID = 1172910353638992375L;

    public NoticeException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
