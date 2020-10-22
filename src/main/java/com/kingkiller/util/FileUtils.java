package com.kingkiller.util;

import com.kingkiller.exception.CustomerErrorCode;
import com.kingkiller.exception.CustomerServiceException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class FileUtils {

    @Value("${bucket.secretId}")
    private String secretId;

    @Value("${bucket.secretKey}")
    private String secretKey;

    @Value("${bucket.url}")
    private String bucketUrl;

    @Value("${bucket.name}")
    private String bucketName;

    @Value("${bucket.region}")
    private String region;

    private static final String[] typeImg = {"gif","png","jpg"};

    /**
     * 上传到tomcat内置的服务器内
     * @param request 请求
     * @param path_deposit 路径
     * @param file 文件
     * @param isRandomName 是否为随即名
     * @return
     */
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
                        //String path = ResourceUtils.getURL("classpath:").getPath()+"static/picture";
                        //本地环境
                        String path = "D:/picture";
                        //服务器存放路径
                        //String path = "/temp";
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
                        fileSrc = path+"/"+origName;
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

    /**
     * 上传COS存储桶
     * @param file 文件
     * @return 文件存放路径
     */
    public  String uploadCosBucket(MultipartFile file, String source){
        File targetFile = null;
        try {
            // 判断上传文件是否为null
            if (file != null){
                // 获取文件原名称
                String originalFilename = file.getOriginalFilename();
                log.info("获取的文件名称 {}", originalFilename);
                // 判断文件类型
                String type= originalFilename.indexOf(".")!=-1?originalFilename.substring(originalFilename.lastIndexOf(".")+1, originalFilename.length()):null;
                boolean booIsType = false;
                if (type!=null) {
                    for (int i = 0; i < typeImg.length; i++) {
                        if (typeImg[i].equals(type.toLowerCase())) {
                            booIsType = true;
                        }
                    }
                }
                // 判断是否符合文件类型，符合进行上传
                if (booIsType){
                    // 系统临时存放文件路径
                    //String src = "/root/img/";
                    // Windows系统下测试用
                    String src = "C:\\Users\\kingkiller\\Desktop";
                    originalFilename = GenIdUtil.createId("FILE")+ originalFilename.substring(originalFilename.lastIndexOf("."));
                    targetFile = new File(src,originalFilename);

                    // 文件转换
                    file.transferTo(targetFile);
                    // 上传COS存储桶
                    BasicCOSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
                    // 设置bucket的区域
                    Region region = new Region("ap-beijing");
                    ClientConfig clientConfig = new ClientConfig(region);
                    // 生成cos客户端
                    COSClient cosClient = new COSClient(cred, clientConfig);
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, originalFilename, targetFile);
                    PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
                    log.info("上传COS存储桶结果 {}",putObjectResult);
                    targetFile.deleteOnExit();
                    return bucketUrl+originalFilename;
                }else {
                    throw new CustomerServiceException(CustomerErrorCode.FILE_INDEX_ERROR);
                }
            }else {
                throw new CustomerServiceException(CustomerErrorCode.FILE_IS_NULL);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomerServiceException(CustomerErrorCode.SYSTEM_INNER_ERROR,e.getMessage());
        }finally {
            // 删除临时文件
            targetFile.deleteOnExit();
        }
    }

    /**
     * multipartFile转化为File
     *
     * @param multipartFile
     * @return file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        File toFile = null;
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            toFile = new File(multipartFile.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    /**
     * 获取流文件
     *
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 流形式上传cos
     *
     * @param in       上传的文件流
     * @param filePath 下载预览时的文件名
     * @return 下载预览路径
     * @throws IOException 异常
     */
    public String upload(InputStream in, String filePath) throws IOException {

        log.info("secretId={}，secretKey={}，region={}", secretId, secretKey, region);
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3.生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        log.info("开始上传文件到腾讯云cos-----");
        // 设置文件上传大小
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(in.available());
        PutObjectRequest request = new PutObjectRequest(bucketName, filePath, in, metadata);
        request.setMetadata(metadata);
        PutObjectResult putObjectResult = cosClient.putObject(request);
        String eTag = putObjectResult.getETag();
        log.info("上传结束---返回etag = {}", eTag);
        cosClient.shutdown();
        return bucketUrl + filePath;
    }

    /**
     * COS SDK方式下载文件
     * @return 文件在服务器位置
     */
    public String downloadFile(String fileName){
        // COS存储桶设置
        BasicCOSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 设置bucket的区域
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        String src = "/root/img/";
        File downFile = new File(src);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
        return null;
    }

    /**
     * COS SDK流的方式下载文件
     * @return 文件流
     */
    public InputStream getFileInputStream(){
        return null;
    }

}
