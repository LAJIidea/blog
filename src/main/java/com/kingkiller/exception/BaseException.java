package com.kingkiller.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常类
 * @author kingkiller
 */
@Slf4j
public class BaseException extends RuntimeException{

    /**
     * 错误码
     */
    @Getter
    @Setter
    private ErrorCode errorCode;

    /**
     * 除本身信息外额外的补充信息
     */
    @Getter
    @Setter
    private String extraMsg;

    /**
     * 提供错误码和Throwable错误信息构造方法
     * @param errorCode 错误码
     * @param cause Throwable错误原因
     */
    public BaseException(ErrorCode errorCode, Throwable cause){
        this(errorCode,cause.toString());
    }

    /**
     * 错误码构造方法
     * @param errorCode 错误码
     */
    public BaseException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }


    /**
     * 全参构造方法
     * @param errorCode 错误码
     * @param extraMsg 错误描述
     */
    public BaseException(ErrorCode errorCode, String extraMsg){
        this.errorCode = errorCode;
        this.extraMsg = extraMsg;
    }

}
