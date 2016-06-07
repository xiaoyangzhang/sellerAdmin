package com.yimayhd.sellerAdmin.base.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi on 16/6/7.
 */
public class AbstractBaseService extends AbstractService {

    protected static final Logger log = LoggerFactory.getLogger(AbstractBaseService.class);

    /**
     * Service层日志
     */
    public static final Logger serviceLog = LoggerFactory.getLogger("serviceLogger");

    /**
     * error日志
     */
    public static final Logger errorLog =LoggerFactory.getLogger("serviceErrorLogger");


    protected void writeInfoLog4Service(String message) {
        if (serviceLog.isInfoEnabled()) {
            serviceLog.info(message);
        }
    }

    protected void writeErrorLog4Service(String message) {
        if (errorLog.isErrorEnabled()) {
            errorLog.error(message);
        }
    }

    protected void writeErrorLog4Service(String message,Throwable e){
        if (errorLog.isErrorEnabled()) {
            errorLog.error(message,e);
        }
    }
}
