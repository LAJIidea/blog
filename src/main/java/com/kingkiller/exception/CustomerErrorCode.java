package com.kingkiller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerErrorCode implements ErrorCode {

    /**
     * 系统内部异常
     */
    SYSTEM_INNER_ERROR("00000","系统内部异常"),

    /**
     * 用户信息不存在
     */
    USER_NO_IS_NULL("00001", "用户不存在"),

    /**
     * 用户未登录
     */
    LOGIN_IS_ERROR("00002","请先登录"),

    /**
     * 上传文件不能为空
     */
    FILE_IS_NULL("00003","文件为空"),

    /**
     * 上传文件类型不正确
     */
    FILE_INDEX_ERROR("00004","文件类型不匹配"),

    /**
     * 下载文件异常
     */
    DOWNLOAD_FILE_ERROR("00005", "下载文件异常"),
    ;

    private String code;

    private String desc;

}
