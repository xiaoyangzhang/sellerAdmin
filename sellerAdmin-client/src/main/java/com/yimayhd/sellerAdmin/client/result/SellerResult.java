package com.yimayhd.sellerAdmin.client.result; /**
 *
 */


/**
 * 返回参数模板
 * @param <T>
 */

public class SellerResult<T> extends SellerBaseResult  {

    public static final int CODE_FAILURE = -1;
    public static final int CODE_SUCCESS = 1;
    private static final long serialVersionUID = 4417975132147336761L;


    private final boolean success;
    private final int code;
    private final String msg;
    private final T resultObject;

    private String stringValue;

    public SellerResult(boolean isSuccess, int code, String msg, T resultObject){
        this.success = isSuccess;
        this.code = code;
        this.msg = msg;
        this.resultObject = resultObject;
    }


    public static <T> SellerResult<T> success(){
        return new SellerResult<T>(true,CODE_SUCCESS,"default success",null);
    }

    public static <T> SellerResult<T> success(T resultObject){
        return new SellerResult<T>(true,CODE_SUCCESS,"default success",resultObject);
    }

    public static <T> SellerResult<T> success(int code, String msg, T resultObject){
        return new SellerResult<T>(true,code,msg,resultObject);
    }

    public static <T> SellerResult<T> failure(){
        return new SellerResult<T>(false,CODE_FAILURE,"default failure",null);
    }

    public static <T> SellerResult<T> failure(String msg){
        return new SellerResult<T>(false,CODE_FAILURE,msg,null);
    }


    public static <T> SellerResult<T> failure(int code, String msg){
        return new SellerResult<T>(false,code,msg,null);
    }

    @Override
    public String toString(){
        String result = stringValue;
        if(result != null){
            return result;
        }
        result = new StringBuilder()
                .append('{')
                .append("\"success\":").append(success).append(',')
                .append("\"code\":").append(code).append(',')
                .append("\"msg\":\"").append(msg).append("\",")
                .append("\"resultObject\":").append(resultObject)
                .append('}')
                .toString();
        stringValue = result;
        return result;
    }

	/* -----------------------------getters&setters------------------------------- */


    public boolean hasData(){
        return resultObject!=null;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getResultObject() {
        return resultObject;
    }
}
