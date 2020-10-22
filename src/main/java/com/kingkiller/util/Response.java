package com.kingkiller.util;


/**
 * 自定义返回结果类
 */
public class Response<T> {

    private String message;

    private String code;

    private T data;

    public Response(){

    }

    public Response(String code, String message){
        this.code = code;
        this.message = message;
    }

    public Response(T data) {
        this.data = data;
        this.code = "11111";
        this.message = "SUCCESS";
    }

}
