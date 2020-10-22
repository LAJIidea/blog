package com.kingkiller.util;


import java.util.UUID;

/**
 * 生成自定义ID公共方法
 * @author kingkiller
 */
public class GenIdUtil {

    private static final String FORMAT_ID = "type%sID%s";

    /**
     * 生成自定义随机ID
     * @param type ID类型
     * @return id
     */
    public static String createId(String type){
        String replace = FORMAT_ID.replace("type", type);
        return String.format(replace, TimeUtil.getCurrent(), UUID.randomUUID().toString());
    }

}
