package com.example.logintest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.logintest.utils.FormatUtils;
import com.example.logintest.utils.HttpUtils;

public class LoginActivity extends Activity implements OnClickListener {

	private Handler handler;
	private EditText username;
	private EditText password;
	private Button btn_login;
	private Button btn_register;
	private String url = "http://130.234.1.67/Test/login.php";
	private String url1 = "http://130.234.1.67/Test/json_array.php";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					try {
						String res = msg.getData().getString("res");
						JSONObject result = new JSONObject(res);
						int success = Integer.parseInt(result.getString("success"));
							Toast.makeText(LoginActivity.this, res + ":\n" +result.toString(), Toast.LENGTH_LONG).show();
							// TODO Auto-generated catch block
						if(success == 0){
							Intent intent = new Intent(LoginActivity.this, LoginActivity1.class);
							startActivity(intent);
						}else{
							Toast.makeText(LoginActivity.this, "输入的用户名或密码有错", Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				default:
					break;
				}
			}
		};

		Bundle bundle = this.getIntent().getExtras();

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		if(bundle != null){
			username.setText(bundle.getString("empNo"));
			password.setText(bundle.getString("pass"));
		}
	}

	public void onClick(View v){
		int id = v.getId();
		switch(id){
		//登陆按钮点击事件
		case R.id.btn_login:
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						JSONObject json = new JSONObject();
						json.put("UserName", username.getText().toString().trim());
						json.put("PassWord", password.getText().toString().trim());
						//						httpPostMethod(json);
						HttpUtils.httpPostMethod(url, json, handler);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.d("json", "解析JSON出错");
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			break;
		//注册按钮点击事件
		case R.id.btn_register:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
