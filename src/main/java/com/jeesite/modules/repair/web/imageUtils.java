package com.jeesite.modules.repair.web;

import com.jeesite.common.lang.StringUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  图片工具类 
 * 		1、IoReadImage  方法：将图片用二进制流输出到页面提供显示
 *		2、base64StrToImage 方法 ：base64编码字符串转换为图片
 * @author 孙梦启
 *
 * @version v1.0
 *
 * @since 2018年12月8日
 */
public class imageUtils {
	/**
	 * 
	 * 功能描述：将图片用二进制流输出到页面提供显示
	 *
	 * @param imgPath
	 * @param request
	 * @param response
	 *
	 * @显示方式 : <img src="调用此方法的web层方法访问地址">  例如：src="http://localhost:9999/manage-admin/filemanager/prevViewByPath.action?imgPath=XXX/XXX/cf532711-6ba8-4259-8c30-1dbb13fe3a82.jpg"
	 *
	 * @author 孙梦启
	 *
	 * @since 2018年12月8日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static void IoReadImage(String imgPath, HttpServletRequest request, HttpServletResponse response) {
		File f = new File(imgPath);
		if (f.exists()) {
			FileInputStream fis=null;
			OutputStream out =null;
			try {
				fis = new FileInputStream(f);
				int i = fis.available(); // 得到文件大小  
				response.setContentType("image/*"); // 设置返回的文件类型  
				out = response.getOutputStream();
				byte[] bs = new byte[i];
				while ((fis.read(bs) != -1)) {
					out.write(bs);
				}
				out.flush();
				out.close();
				out = null;
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					out.flush();
					out.close();
					out = null;
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 
	 * 功能描述：base64编码字符串转换为图片
	 *
	 * @param Map<String,String>    imgStr:base64编码字符串     imgPath:图片路径 (包含图片名字以及图片后缀格式)
	 * @return Map<String,String>   result:success/error  msg:错误信息 imgPath:图片路径
	 *
	 * @author 孙梦启
	 *
	 * @since 2018年12月8日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
//	public static Map<String, String> base64StrToImage(Map<String, String> param) {
//		Map<String, String> result = Maps.newHashMap();
//
//		if (param == null) {
//			result.put("result", "ERROR");
//			result.put("msg", "错误，入参为空！");
//			return result;
//		}
//		String imgPath = param.get("imgPath");
//		if (StringUtils.isBlank(imgPath)) {
//			result.put("result", "ERROR");
//			result.put("msg", "错误，图片地址不能为空！");
//			return result;
//		}
//		String imgStr = param.get("imgStr");
//		if (StringUtils.isBlank(imgStr)) {
//			result.put("result", "ERROR");
//			result.put("msg", "错误，图片数据不能为空！");
//			return result;
//		} else {
//			int v = imgStr.indexOf("base64,");
//			if (v != -1) {
//				imgStr = imgStr.substring(v + 7, imgStr.length());
//			}
//		}
//
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			// 解密
//			byte[] b = decoder.decodeBuffer(imgStr);
//			// 处理数据
//			for (int i = 0; i < b.length; ++i) {
//				if (b[i] < 0) {
//					b[i] += 256;
//				}
//			}
//
//			// 文件夹不存在则自动创建
//			File tempFile = new File(imgPath);
//			if (!tempFile.getParentFile().exists()) {
//				tempFile.getParentFile().mkdirs();
//			}
//			OutputStream out = new FileOutputStream(tempFile);
//			out.write(b);
//			out.flush();
//			out.close();
//			result.put("result", "SUCCESS");
//			result.put("imgPath", imgPath);
//			result.put("imgStr", imgStr);
//			result.put("msg", "保存成功!");
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.put("result", "ERROR");
//			result.put("imgPath", imgPath);
//			result.put("msg", "保存失败！原因:" + e.getMessage());
//			return result;
//		}
//	}

	/**
	 * 
	 * 功能描述： 图片转base64位数组
	 *
	 * @param imgPath
	 * @return
	 *
	 * @author 孙梦启
	 *
	 * @since 2018年12月11日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String  imageToBase64Str( String imgPath){
		String base64Str="";
		if(StringUtils.isNotBlank(imgPath)){
		File f =null;
		FileInputStream fis =null;
		ByteArrayOutputStream out =null;
		try{
		f= new File(imgPath);
		fis=new FileInputStream(f);
		out = new ByteArrayOutputStream();
	    byte[] buffer = new byte[fis.available()];
	    int n = 0;
	    while ((n = fis.read(buffer)) != -1) {
	        out.write(buffer, 0, n);
	    }
	    BASE64Encoder encoder = new BASE64Encoder();
	    base64Str=encoder.encode(buffer);
		}catch (Exception e) {
			e.printStackTrace();
			base64Str="";
		}finally{
			try {
			if(out !=null){
			  out.close();
			}
			if(fis !=null){
				fis.close();
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		return base64Str;
	}
	/**
	 * 
	 * 功能描述：img 标签中显示base64位数据图片
	 *
	 * @param imgPath
	 * @return
	 *
	 * @author 孙梦启
	 *
	 * @since 2018年12月13日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String imageToBase64Html(String imgPath,String imgType){
		
		String base64Str="";
		if(imgPath.indexOf("http")==0){
			base64Str=imgURLBase64(imgPath,imgType);
		}else{
			base64Str=imageToBase64Str(imgPath);
		}
				
		int v = base64Str.indexOf("base64,");
		if (v != -1) {
			base64Str = base64Str.substring(v + 7, base64Str.length());
		}
		
		return "data:image/"+imgType+";base64,"+base64Str;
	}
	/**
	 * 
	 * 功能描述：网络图片转换成base64  压缩！
	 *
	 * @param imgURL
	 * @return
	 *
	 * @author 孙梦启
	 *
	 * @since 2019年2月22日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
//	public static String imgURLBase64(String imgURL,String fileType) {
//			ByteArrayOutputStream outPut = new ByteArrayOutputStream();
//		      byte[] data =null;
//		      HttpURLConnection conn=null;
//		      InputStream inStream =null;
//		      URL url =null;
//		        try { 
//		        	if(StringUtils.isBlank(fileType)){
//		        		fileType="jpg";
//		        	}
//		            // 创建URL  
//		            url = new URL(imgURL);  
//		            // 创建链接  
//		            conn= (HttpURLConnection) url.openConnection();  
//		            conn.setRequestMethod("GET");  
//		            conn.setConnectTimeout(10 * 1000); 
//		            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
//		          
//		            	return "fail:"+imgURL;//连接失败/链接失效/图片不存在
//		            }
//		            
//		            inStream = conn.getInputStream();
//		            
//		            data = new byte[inStream.available()];
//		            int len = -1;
//		            while ((len = inStream.read(data)) != -1) {
//		            outPut.write(data, 0, len);
//		            }
//		        inStream.close();  
//		        byte[] b=outPut.toByteArray();
//		        if(b.length<(600*1024)){
//			        BASE64Encoder encoder = new BASE64Encoder();
//					String base64Str=encoder.encode(b);
//			        return base64Str.replaceAll("\r|\n", "");  	
//			    }else{
//		        ByteArrayInputStream intputStream = new ByteArrayInputStream(b);
//		        Builder<? extends InputStream> builder=null;
//		        builder = Thumbnails.of(intputStream).scale(0.5f);
//		        byte[] byteArray=null;
//		      
//		          BufferedImage bufferedImage = builder.asBufferedImage();
//		          ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		          ImageIO.write(bufferedImage, fileType, baos);
//		          byteArray = baos.toByteArray();
//				BASE64Encoder encoder = new BASE64Encoder();
//				String base64Str=encoder.encode(byteArray);
//		        return base64Str.replaceAll("\r|\n", ""); 
//		        }
//		        } catch (Exception e) {
//		            e.printStackTrace();
//		        return "";
//		     }finally{
//		    try {
//		    	if(inStream !=null){
//				inStream.close();
//		    	}
//		    	if(outPut !=null){
//		    	outPut.flush();
//				outPut.close();
//		    	}
//		    	if(conn!=null){
//				conn.disconnect();
//		    	}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//		  }
//		}
	/**
	 * 
	 * 功能描述：网络图片转换成base64  压缩！
	 *
	 * @param imgURL
	 * @return
	 *
	 * @author 孙梦启
	 *
	 * @since 2019年2月22日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String imgURLBase64(String imgURL,String fileType) {
		URL url;
		String base64Str="";
		ByteArrayOutputStream baos =null;
		try {
		url = new URL(imgURL);
		BufferedImage bufImage= ImageIO.read(url);
		baos= new ByteArrayOutputStream();
		ImageIO.write(bufImage,fileType, baos);
		if(baos.size()>(2*1024*1024)){
		Builder<BufferedImage> builder = Thumbnails.of(bufImage).scale(0.5f);
		BufferedImage buf=builder.asBufferedImage();
		ImageIO.write(buf,fileType, baos);
		}
		byte[] bytes=baos.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();
		base64Str=encoder.encode(bytes).replaceAll("\r|\n", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
			if(baos !=null){
			   baos.flush();
			   baos.close();
			}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return base64Str;
	}
	/**
	 * 
	 * 功能描述：图片转换 base64 方法，可同时转换内部路径和外网路径
	 *
	 * @param imgURL 图片地址
	 * @param isHtml 是否是HTML格式
	 * @param fileType 图片类型
	 * @return
	 *
	 * @author 胡婷
	 *
	 * @since 2019年2月22日
	 *
	 * @update:[变更日期YYYY-MM-DD][更改人姓名][变更描述]
	 */
	public static String pubImageToBase64(String imgURL,boolean isHtml,String fileType){
		String base64Str="";
		if(!isHtml){ //非HTML格式
		if(imgURL.indexOf("http")==0){ //外网图片链接
			base64Str=imgURLBase64(imgURL,fileType);
		}else{
			base64Str=imageToBase64Str(imgURL);
		}
		}else{
			base64Str=imageToBase64Html(imgURL, fileType);
		}
		
		return base64Str;
	}
	public static void main(String[] args) throws Exception {  
//        //new一个URL对象  
////        URL url = new URL("http://apitest.dxtmobile.com/applyImage/20190304/d11e291183a943659afa2b46057dcf13.jpg");  
////        //打开链接  
////        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
////        //设置请求方式为"GET"  
////        conn.setRequestMethod("GET");  
////        //超时响应时间为5秒  
////        conn.setConnectTimeout(5 * 1000);  
////        //通过输入流获取图片数据  
////        InputStream inStream = conn.getInputStream();  
//        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
//      
//        //new一个文件对象用来保存图片，默认保存当前工程根目录  
//        File imageFile = new File("d://2222.jpg");  
//        //创建输出流  
//        FileOutputStream outStream = new FileOutputStream(imageFile);  
//        
//        String s=imgURLBase64("http://applyimage.hollardchina.com.cn/00f6da889c4b40cabaa005c6bc160171.jpg", "jpg");
//        
//        String FilePath = "d://2222.txt";
//		FileWriter fw = new FileWriter(FilePath);
//		BufferedWriter out2 = new BufferedWriter(fw);
//		out2.write("data:image/jpg;base64,"+s.replaceAll("\r|\n", ""));
//		out2.flush();
//		out2.close();
//        
//		String FilePath1 = "d://22222.txt";
//		FileWriter fw1 = new FileWriter(FilePath1);
//		BufferedWriter out3 = new BufferedWriter(fw1);
//		String encoderStr=URLEncoder.encode("data:image/jpg;base64,"+s,"UTF-8");
//		out3.write(encoderStr);
//		out3.flush();
//		out3.close();
//		
//		
//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] data =decoder.decodeBuffer(s);
////        for (int i = 0; i < data.length; ++i) {
////				if (data[i] < 0) {
////					data[i] += 256;
////				}
////			}
//	    //写入数据  
//        outStream.write(data);
//        //关闭输出流  
//        outStream.close();  
//     
//        System.out.println("success");
//		
		//String string=imgURLBase64("http://policyimage.hollardchina.com.cn/2019/04/17/7b66baa3386a40ab9a854d4f84d04436.", "jpg");
		//System.out.println(string);
		
		URL url= new URL("http://policyimage.hollardchina.com.cn/2019/04/17/7b66baa3386a40ab9a854d4f84d04436.");
		BufferedImage bufImage= ImageIO.read(url);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bufImage, "jpg", baos);
		byte[] bytes=baos.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Str=encoder.encode(bytes).replaceAll("\r|\n", "");
		
		 String FilePath = "d://2222.txt";
		 FileWriter fw = new FileWriter(FilePath);
		 BufferedWriter out2 = new BufferedWriter(fw);
		 out2.write("data:image/jpg;base64,"+base64Str);
		 out2.flush();
		 out2.close();
	    System.out.println(base64Str); 
    }


	/**
	 * 将网络图片转换成Base64编码字符串
	 *
	 * @param imgUrl 网络图片Url
	 * @return
	 */
	public static String getImgUrlToBase64(String imgUrl) {
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		byte[] buffer = null;
		try {
			// 创建URL
			URL url = new URL(imgUrl);
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			inputStream = conn.getInputStream();
			outputStream = new ByteArrayOutputStream();
			// 将内容读取内存中
			buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			buffer = outputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					// 关闭inputStream流
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					// 关闭outputStream流
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// 对字节数组Base64编码
		return new BASE64Encoder().encode(buffer);
	}
}
