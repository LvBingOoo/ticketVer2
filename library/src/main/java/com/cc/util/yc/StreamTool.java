package com.cc.util.yc;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;

public class StreamTool {
	
	public static int responseValue=0;
	
	/**
	 * 从流中读取数据
	 * @param inStream
	 * @return
	 * @throws Exception 
	 */
	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = null;
		byte[] b = null;
		try {
			if (inStream == null) {
				return null;
			}
			outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			b = outStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			if (outStream != null) {
				outStream.close();
			}
			inStream = null;
			outStream = null;
		}
		return b;
	}
	
	/**
	 * POST请求并解析返回的XML
	 * @param con 上下文对象
	 * @param url 请求url
	 * @param data 请求信息map
	 * @return Object 结果集
	 */
	public static Object requestFromRemote(Context con, String url, Map<String, String> data/*, IParser baseHandler*/) {
		// 返回标识
		responseValue = 0;
		// 返回Object
		Object object = null;
		// 封装传送的数据
		ArrayList<BasicNameValuePair> postData = new ArrayList<BasicNameValuePair>();
		if(data!=null)
		for (Map.Entry<String, String> m : data.entrySet()) {
			postData.add(new BasicNameValuePair(m.getKey(), m.getValue()));
		
		}
		
		// 建立httpPost连接
		HttpPost httpPost = new HttpPost(url);
		
		BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 30000); 
        HttpConnectionParams.setSoTimeout(httpParams, 30000); 
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
		HttpResponse response = null;
		try{
			// 对请求的数据进行UTF-8转码
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData, HTTP.UTF_8);
			// 发送数据
			httpPost.setEntity(entity);
			// 获取响应对象
			response = httpClient.execute(httpPost);
			// 响应正常
//			System.out.println("state="+response.getStatusLine().getStatusCode());
			
			responseValue = 1;
			// 获取返回数据
			HttpEntity httpEntity = response.getEntity();
			StringBuilder builder = new StringBuilder(); 
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
			// 组合json字符串
			for (String s = bufferedReader.readLine(); s != null; s = bufferedReader.readLine()) { 
				builder.append(s);
			}
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				// 解析json返回数据bean
			 
				if(!builder.toString().contains("error_response")) {
					object =builder;// baseHandler.parse(builder.toString());
				} else {
				//	object = new ErrorHandler().parseJSON(builder.toString());
				}
			} else {
				responseValue = 2;
				httpPost.abort();
			}
			
			/*else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
			// 请求失败解析ErrorBean
			System.out.println("builder="+builder.toString());
			object = new ErrorHandler().parseJSON(builder.toString());
			} */
			
		} catch(Exception ex) {
			ex.printStackTrace();
			responseValue = 3;
		}
		
		// 返回数据集
		return object;
	}
}
