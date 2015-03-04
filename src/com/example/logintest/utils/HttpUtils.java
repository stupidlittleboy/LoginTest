package com.example.logintest.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpUtils {

	/**
	 * Post方式提交到服务器
	 * @param json	json数据
	 * @param handler	handler消息处理机制
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static void httpPostMethod(String url, JSONObject json, Handler handler)
			throws UnsupportedEncodingException, IOException,
			ClientProtocolException {
		
		HttpParams params = new BasicHttpParams();
		//设置连接超时时间
		HttpConnectionParams.setConnectionTimeout(params, 50000);
		HttpClient client = new DefaultHttpClient(params);
		
		//提交json数据到服务器
		HttpPost request = new HttpPost(url);
		StringEntity se = new StringEntity(json.toString(), "UTF-8");
		se.setContentEncoding("UTF-8");
		se.setContentType("application/json");
		request.setEntity(se);
		request.setHeader("json", json.toString());
		HttpResponse response = client.execute(request);
		
		//获取服务器返回的数据
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
			
			String res = EntityUtils.toString(response.getEntity(), "UTF-8");
			Log.d("httpResponse", res);
			Message msg = new Message();
			msg.what = 0;
			Bundle bundle = new Bundle();
			bundle.putString("res", res);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}
}
