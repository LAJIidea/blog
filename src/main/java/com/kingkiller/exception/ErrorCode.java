package com.kingkiller.exception;

/**
 * 错误类型包装类
 * @author kingkiller
 */
public interface ErrorCode {

    /**
     * 错误码
     * @return
     */
    String getCode();

    /**
     * 错误信息
     * @return
     */
    String getDesc();

}
