package com.system.bhouse.utils;


import android.util.Log;

import com.socks.library.KLog;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadUtils {

	public static String uploadFile(File file,String urlStr) {
		//异步加载，千万不能把网络请求放在UI主线程中，不然会发生异常android.os.NetworkOnMainThreadException
		String end = "\r\n";//回车换行
		String twoHyphens = "--";//参数分隔符ps:与boundary分割传入的参数
		String boundary = "***********";//分界线可以任意分配

		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);//不适用缓存
			httpURLConnection.setRequestMethod("POST");//post请求
			httpURLConnection.setRequestProperty("accept-encoding", "gzip,deflate");
			httpURLConnection.setRequestProperty("Connection", "keep-Alive");//一直保持链接状态
			httpURLConnection.setRequestProperty("Charset", "utf-8");//字符集编码为utf-8
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			//请求数据为多元性数据，这里只用分界线不用分隔符表示，必须严格按照这样写，不然服务器无法识别

			DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			//获取输出流
			String newName = "head.jpg";//临时文件名字。可任意改.我的服务器端存储的不是它，以为我用了全球唯一标识符（Guid）来命名的
//			dataOutputStream.writeBytes(twoHyphens + boundary + end);
//			dataOutputStream.writeBytes("Content-Disposition: form-data; " +
//					"name=\"MyHeadPicture\";filename=\"" +
//					newName + "\"" + end);
						/*注意，千万注意这的MyHeadPicture与浏览器端的<input type="file" name="MyHeadPicture"/>name对应的属性一致
						，记住不能少了回车换行结束标志*/
//			dataOutputStream.writeBytes(end);

			FileInputStream fStream = new FileInputStream(file);//获取本地文件输入流
				          /* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			//      StringBuffer sb = new StringBuffer();
				          /* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				//     	  sb.append(length);
				            /* 将资料写入DataOutputStream中 */
				dataOutputStream.write(buffer, 0, length);//将文件一字节流形式输入到输出流中
			}
//			dataOutputStream.writeBytes(end);
//			dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + end);
				          /* close streams */
			fStream.close();
			dataOutputStream.flush();
				          /* 取得Response内容 */
			int responseCode = httpURLConnection.getResponseCode();
			if(responseCode==200){
				Log.e("sjdlkfjsldk","responseCode :"+responseCode);
			}
			InputStream is = httpURLConnection.getInputStream();//服务器端响应

			String result = IOUtils.toString(is, "UTF-8");

//			String s1 = BHEncodeUtils.unGzip(is);

			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}

			System.err.println("-----------"+b.toString());
			dataOutputStream.close();
			return result;//返回响应内容
		} catch (IOException e) {
			e.printStackTrace();
//			L.d(e.getLocalizedMessage());
			KLog.e("上传图片的本地的异常:  "+e.getLocalizedMessage());
		}
		return "访问出错";
	}
}