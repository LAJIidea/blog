package com.kingkiller.util;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.UUID;


public class FileUtils {
    public static String uploadImage(HttpServletRequest request, String path_deposit, MultipartFile file,boolean isRandomName){
        try{
            String[] typeImg={"gif","png","jpg"};
            if (file!=null){
                String origName = file.getOriginalFilename();//文件原名称
                System.out.println("上传的文件原名称:"+origName);
                //判断文件类型
                String type=origName.indexOf(".")!=-1?origName.substring(origName.lastIndexOf(".")+1, origName.length()):null;
                if (type!=null){
                    boolean booIsType=false;
                    for (int i=0;i<typeImg.length;i++){
                        if (typeImg[i].equals(type.toLowerCase())){
                            booIsType = true;
                        }

                    }
                    //判断类型是否符合规定
                    if (booIsType){
                        //存放图片路径
                        String path = ResourceUtils.getURL("classpath:").getPath()+"static/picture";
                        System.out.println(path);
                        //组合名称
                        String fileSrc="";
                        //是否随机名称
                        if (isRandomName){
                            origName = TimeUtil.formateString(new Date(),"yyyy-MM-dd-")+ UUID.randomUUID().toString()+origName.substring(origName.lastIndexOf("."));
                        }
                        //判断是否存在目录
                        File targetFile = new File(path,origName);
                        if (!targetFile.exists()){
                            targetFile.mkdirs();//创建目录
                        }
                        //上传
                        file.transferTo(targetFile);
                        //完整路径
                        fileSrc = "/picture"+origName;
                        System.out.println("图片上传成功"+fileSrc);
                        return fileSrc;
                    }
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
