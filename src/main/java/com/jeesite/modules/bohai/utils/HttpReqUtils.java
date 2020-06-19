package com.jeesite.modules.bohai.utils;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpReqUtils {
	protected static Logger logger = LoggerFactory.getLogger(HttpReqUtils.class);
	private static final String LOGIN_NAME="Hede";//用户名
	private static final String PASSWORD="hd123456";//密码
	public static void testPost() throws IOException {
		
		/**
		 * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
		 *  java.net.URL and //java.net.URLConnection
		 */
//		URL url = new URL("http://item.jd.com/539349.html");
		URL url = new URL("http://www.yzdsb.com.cn/");
		URLConnection connection = url.openConnection();
		/**
		 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
		 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
		 */
		connection.setDoOutput(true);
		/**
		 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
		 */
		OutputStreamWriter out = new OutputStreamWriter(connection
				.getOutputStream(), "8859_1");
		out.write("username=kevin&password=*********"); //post的关键所在！
		// remember to clean up
		out.flush();
		out.close();
		/**
		 * 这样就可以发送一个看起来象这样的POST： 
		 * POST /jobsearch/jobsearch.cgi HTTP 1.0 ACCEPT:
		 * text/plain Content-type: application/x-www-form-urlencoded
		 * Content-length: 99 username=bob password=someword
		 */
		// 一旦发送成功，用以下方法就可以得到服务器的回应：
		String sCurrentLine;
		String sTotalString;
		sCurrentLine = "";
		sTotalString = "";
		InputStream l_urlStream;
		l_urlStream = connection.getInputStream();
		// 传说中的三层包装阿！
		BufferedReader l_reader = new BufferedReader(new InputStreamReader(
				l_urlStream));
		while ((sCurrentLine = l_reader.readLine()) != null) {
			sTotalString += sCurrentLine + "/r/n";

		}
		System.out.println(sTotalString);
	}




	    /**
	     * 向指定URL发送GET方法的请求
	     * 
	     * @param url
	     *            发送请求的URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	     * @return URL 所代表远程资源的响应结果
	     */
	    public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
//	            for (String key : map.keySet()) {
//	                System.out.println(key + "--->" + map.get(key));
//	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream(),"gbk"));
	            String line;
	            while ((line = in.readLine()) != null) {
//	                result += changeCharset(line , "UTF-8" , "gbk" );
//	                result += new String(line.getBytes(), "utf-8");
	                result += line;//changeCharset(line , "utf-8" , "gbk" );
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }

	    /**
	     * 向指定 URL 发送POST方法的请求
	     * 
	     * @param url
	     *            发送请求的 URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	     * @return 所代表远程资源的响应结果
	     */
	    public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        System.out.println("消息发送结果： " + result);
	        return result;
	    }
	    
	    /**
	     * 向指定 URL 发送POST方法的请求
	     * 
	     * @param url
	     *            发送请求的 URL
	     * @param param
	     *            请求参数，json字符串
	     * @return 所代表远程资源的响应结果
	     */
	    public static String sendPostJson(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);	      
	            
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        System.out.println("消息发送结果： " + result);
	        return result;
	    }
	    
	    public static String changeCharset(String str, String oldCharset, String newCharset)
	    		   throws UnsupportedEncodingException {
	    		  if (str != null) {
	    		   //用旧的字符编码解码字符串。解码可能会出现异常。
	    		   byte[] bs = str.getBytes(oldCharset);
	    		   //用新的字符编码生成字符串
	    		   return new String(bs, newCharset);
	    		  }
	    		  return null;
	    		 }

		 /** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
		 public static final String US_ASCII = "US-ASCII";

		 /** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
		 public static final String ISO_8859_1 = "ISO-8859-1";

		 /** 8 位 UCS 转换格式 */
		 public static final String UTF_8 = "UTF-8";

		 /** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
		 public static final String UTF_16BE = "UTF-16BE";

		 /** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
		 public static final String UTF_16LE = "UTF-16LE";

		 /** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
		 public static final String UTF_16 = "UTF-16";

		 /** 中文超大字符集 */
		 public static final String GBK = "GBK";
		 
		 /**
		     * 向指定 URL 发送POST方法的请求
		     * 
		     * @param url
		     *            发送请求的 URL
		     * @param param
		     *            请求参数，json字符串
		     * @return 所代表远程资源的响应结果
		     */
		    public static String sendPostJson(String url, String param, String token) {
		        PrintWriter out = null;
		        BufferedReader in = null;
		        String result = "";
		        try {
		            URL realUrl = new URL(url);
		            // 打开和URL之间的连接
		            URLConnection conn = realUrl.openConnection();
		            // 设置通用的请求属性
		            conn.setRequestProperty("accept", "*/*");
		            conn.setRequestProperty("connection", "Keep-Alive");
		            conn.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		            conn.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
		           //渤海请求 需要在头部加token
		            conn.setRequestProperty("token", token);
		            // 发送POST请求必须设置如下两行
		            conn.setDoOutput(true);
		            conn.setDoInput(true);	      
		            
		            // 获取URLConnection对象对应的输出流
		            out = new PrintWriter(conn.getOutputStream());
		            // 发送请求参数
		            out.print(param);
		            // flush输出流的缓冲
		            out.flush();
		            // 定义BufferedReader输入流来读取URL的响应
		            in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
		            String line;
		            while ((line = in.readLine()) != null) {
		                result += line;
		            }
		        } catch (Exception e) {
		            System.out.println("发送 POST 请求出现异常！"+e);
		            e.printStackTrace();
		        }
		        //使用finally块来关闭输出流、输入流
		        finally{
		            try{
		                if(out!=null){
		                    out.close();
		                }
		                if(in!=null){
		                    in.close();
		                }
		            }
		            catch(IOException ex){
		                ex.printStackTrace();
		            }
		        }
		        System.out.println("消息发送结果： " + result);
		        return result;
		    }
		    
		    /**
			 * @param url
			 * @param authorization 鉴权信息
			 * @param body 请求body
			 * @return
			 * @author 
			 * @since 2018-1-18
			 */
			public static String post(String url, String authorization, String body){
				logger.info("请求捷信接口信息：" + body);
				CloseableHttpClient httpClient = null;
			    HttpPost httpPost = null;
			    String result = null;
			    try {
			    	String checkAuthorization = encode(LOGIN_NAME,PASSWORD);
			    	
			        httpClient = HttpClients.createDefault();
			        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			        httpPost = new HttpPost(url);
			        httpPost.setConfig(requestConfig);
			        httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
			        httpPost.setHeader("Signature",  authorization);
			        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		            httpPost.setHeader("Accept-Encoding", "application/json");
		            httpPost.setHeader("Authorization", checkAuthorization);
			        CloseableHttpResponse response = httpClient.execute(httpPost);
			        HttpEntity httpEntity = response.getEntity();
			        result = EntityUtils.toString(httpEntity,"utf-8");
			        logger.info("捷信接口返回信息：" + result);
			        return result;
			    } catch (ClientProtocolException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }finally{
			        try {
			            if(httpPost!=null){
			                httpPost.releaseConnection();
			            }
			            if(httpClient!=null){
			                httpClient.close();
			            }
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
				return null;
			}
			
			public static String get(String url, String authorization){
				logger.info("请求捷信接口：" + url);
				CloseableHttpClient httpClient = null;
				
			    HttpGet httpGet = null;
			    String result = null;
			    try {
			    	String checkAuthorization = encode(LOGIN_NAME,PASSWORD);
			    	
			        httpClient = HttpClients.createDefault();
			        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
			        httpGet = new HttpGet(url);
			        httpGet.setConfig(requestConfig);
			        httpGet.setHeader("Signature",  authorization);
			        httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
			        httpGet.setHeader("Accept-Encoding", "application/json");
			        httpGet.setHeader("Authorization", checkAuthorization);
			        CloseableHttpResponse response = httpClient.execute(httpGet);
			        HttpEntity httpEntity = response.getEntity();
			        result = EntityUtils.toString(httpEntity,"utf-8");
			        logger.info("捷信接口返回信息：" + result);
			        return result;
			    } catch (ClientProtocolException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }finally{
			        try {
			            if(httpGet!=null){
			            	httpGet.releaseConnection();
			            }
			            if(httpClient!=null){
			                httpClient.close();
			            }
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
			    }
				return null;
			}
			
			public static String encode(final String username, final String pwd) {
		        String authentication="";
		        try {
		        	final byte[] password = pwd.getBytes(ISO_8859_1);
		            final byte[] prefix = (username + ":").getBytes(ISO_8859_1);
		            final byte[] usernamePassword = new byte[prefix.length + password.length];
		            System.arraycopy(prefix, 0, usernamePassword, 0, prefix.length);
		            System.arraycopy(password, 0, usernamePassword, prefix.length, password.length);
		            authentication = "Basic " + new String(Base64.encode(usernamePassword), "ASCII");
		        } catch (UnsupportedEncodingException ex) {
		            throw new RuntimeException(ex);
		        }
		        return authentication;
		    }
    /**
     * 获取微信token值
     *
     * @return 微信token
     */
    public static String getWxToken (){
        try {
            String s = sendGet("http://testht.site.hollardchina.com.cn/wx-token-factory/api/account/HTXT/get", null);
            HashMap hashMap = JSON.parseObject(s,HashMap.class);
            return hashMap.get("data").toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}