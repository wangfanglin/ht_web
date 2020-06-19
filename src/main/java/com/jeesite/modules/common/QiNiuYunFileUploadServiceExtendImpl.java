package com.jeesite.modules.common;

import com.google.gson.Gson;
import com.jeesite.common.io.FileUtils;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.support.FileUploadServiceExtendSupport;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


@Service
public class QiNiuYunFileUploadServiceExtendImpl extends FileUploadServiceExtendSupport {


    /**
     * 上传文件，首次上传文件都调用（保存到文件实体表之前调用）
     * @param fileEntity 文件实体信息
     * @param fileEntity.getFileRealPath() 文件实际磁盘路径
     * @exception 支持抛出 throw ServiceException("文件不符合要求") v4.1.5
     */
    @Override
    public void uploadFile(FileEntity fileEntity){
        super.uploadFile(fileEntity);
        String key = fileEntity.getFilePath()+fileEntity.getFileId()+"."+fileEntity.getFileExtension();
        // 当前上传的文件
        String fileName = fileEntity.getFileRealPath();
        upload(fileName,key);

    }

    /**
     * 保存上传文件信息，每次上传都调用（保存文件和用户关系数据之前调用）
     * @param fileUpload 文件上传信息，包括文件实体
     * @exception 支持抛出 throw ServiceException("文件不符合要求") v4.1.5
     */
    @Override
    public void saveUploadFile(FileUpload fileUpload){
        // 当前上传的文件
        String fileName = fileUpload.getFileEntity().getFileRealPath();
    }

    /**
     * 获取文件下载的URL地址
     * @param fileUpload 文件上传的信息，包括文件实体
     * @return 无文件下载地址，则返回null，方便后续处理
     */
    @Override
    public String getFileUrl(FileUpload fileUpload){
        FileEntity fileEntity = fileUpload.getFileEntity();
        return "http://ht2imagespace.hollardchina.com.cn/"+fileEntity.getFilePath()+fileEntity.getFileId()+"."+fileEntity.getFileExtension();
    }

    /**
     * 下载文件到浏览器
     * @param fileUpload 文件上传的信息
     * @param request 请求对象，可能断点续传用
     * @param response 响应对象，输出文件流使用
     * @return 如果不是文件流数据，也可返回文件的URL地址进行跳转，如果文件不存在返回404字符串
     */
    @Override
    public String downFile(FileUpload fileUpload, HttpServletRequest request, HttpServletResponse response){
        FileEntity fileEntity = fileUpload.getFileEntity();
        File file = new File(fileEntity.getFileRealPath());
        if (file.exists()){
            FileUtils.downFile(file, request, response, fileUpload.getFileName());
            return null;
        }
        return "404";
    }


    public void upload(String fileName,String key){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.huabei());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "C4HIK20bxi2SKawJfcBgaNSghdWPpBYAClrPLWt7";
        String secretKey = "FFnFBciC-sytnfbvCK0-xX4AWsgqK3uWfFvWck7C";
        String bucket = "ht2imagespace";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = fileName;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = fileName;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            ex.printStackTrace();
        }
    }
}
