package com.kingkiller.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Constants {

    public static void printJson(HttpServletResponse response, Object obj){

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setContentType("text/json;charset=UTF-8");
        String temp="";

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        if (obj!=null){
            temp=gson.toJson(obj);
        }
        try{
            response.getWriter().print(temp);
            response.getWriter().flush();
            response.getWriter().close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
