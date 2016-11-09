package com.greattone.greattone.proxy;

import java.util.HashMap;
import java.util.Set;

import android.content.Intent;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.LoginActivity;
import com.greattone.greattone.activity.MainActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.LoginInfo;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.umeng.analytics.MobclickAgent;

public class LoginProxy {
	/**
	 * 登錄
	 * 
	 * @return
	 */
	public void Login(final BaseActivity activity,final String name,final String password,final int isstart ) {
		MyProgressDialog.show(activity);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "user/login");
		map.put("password", password);
		map.put("username", name);
			
		activity.addRequest(HttpUtil.httpConnectionByPost(activity, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						Data.user = JSON.parseObject(message.getData(),
								LoginInfo.class);
						   MobclickAgent.onProfileSignIn(Data.user.getUserid());
						JPushInterface.setAlias(activity,
								Data.user.getUsername(),
								new TagAliasCallback() {

									@Override
									public void gotResult(int arg0,
											String arg1, Set<String> arg2) {

									}
								});
						getUserMsg(activity, name, password);

					}

				}, new ErrorResponseListener() {
					
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						if (isstart==1) {
							Intent intent=new Intent(activity, LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							activity.startActivity(intent);
						}
					}
					
					@Override
					public void setErrorResponseHandle(VolleyError error) {
						if (isstart==1) {
							Intent intent=new Intent(activity, LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							activity.startActivity(intent);
						}
					}
				}));
	}
	/**
	 * 获取个人信息
	 * 
	 * @return
	 */
	private void getUserMsg( final BaseActivity activityt,final String name,final String password) {
		HttpProxyUtil.getUserInfo(activityt, null, null, new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						Data.myinfo = JSON.parseObject(message.getData(),
								UserInfo.class);
						toMain(activityt, name, password);
					}

				}, null);
	}
	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	private void toMain( BaseActivity activity,String name,String password) {
		activity.	preferences.edit().putString("name", name)
				.putString("password", password).commit();
		Intent intent=new Intent(activity, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		activity.startActivity(intent);
		MyProgressDialog.Cancel();
		activity.finish();
	}
}
