package com.jeesite.modules.cs.rongmo;

import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调用容联接口 的clint 对象
 * @author tangchao
 */
public class RongMoClint {

    private static final String ACCOUNT = "N00000045304";//替换为您的账户
    private static final String SECRET = "d58a8fd0-61c9-11ea-9ea0-515667970fcd";//替换为您的api密码
    private static final String HOST = "https://apis.7moor.com";

    public static final String OUTBOUND_PATH = "/v20160818/call/dialout/";

    public  static RongMoOutboundResult outbound(RongMoOutboundParameter parameter){
        Gson gson = new Gson();
        String jsonStr = post(OUTBOUND_PATH,gson.toJson(parameter));
            return gson.fromJson(jsonStr,RongMoOutboundResult.class);
    }


    public static String post(String interfacePath,String jsonStr){
        String time = getDateTime();
        String sig = md5(ACCOUNT + SECRET + time);
        //查询坐席状态接口
        String url = HOST + interfacePath + ACCOUNT + "?sig=" + sig;
        String auth = base64(ACCOUNT + ":" + time);
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient client = builder.build();
        HttpPost post = new HttpPost(url);
        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type","application/json;charset=utf-8");
        post.addHeader("Authorization",auth);
        StringEntity requestEntity;
        //根据需要发送的数据做相应替换
        requestEntity = new StringEntity(jsonStr,"UTF-8");
        post.setEntity(requestEntity);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String resultJson = EntityUtils.toString(entity,"utf8");
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String md5 (String text) {
        return DigestUtils.md5Hex(text).toUpperCase();
    }


    public static String base64 (String text) {
        byte[] b = text.getBytes();
        Base64 base64 = new Base64();
        b = base64.encode(b);
        String s = new String(b);
        return s;
    }



    public static String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

}
