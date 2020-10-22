package com.kingkiller.exception;

/**
 * 自定义业务异常类
 * @author kingkiller
 */
public class CustomerServiceException extends BaseException {

    /**
     * 错误码与Throwable错误信息构造方法
     * @param errorCode 错误码
     * @param cause Throwable错误信息
     */
    public CustomerServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * 错误码构造方法
     * @param errorCode 错误码
     */
    public CustomerServiceException(ErrorCode errorCode) {
        super(errorCode);
    }


    /**
     * 全参构造方法
     * @param errorCode 错误码
     * @param extraMsg 错误信息
     */
    public CustomerServiceException(ErrorCode errorCode, String extraMsg) {
        super(errorCode, extraMsg);
    }



}
