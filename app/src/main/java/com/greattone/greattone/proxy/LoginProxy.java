package com.greattone.greattone.proxy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.LoginActivity;
import com.greattone.greattone.activity.MainActivity;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.personal.DirectoryActivity2;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.activity.qa.MyQAActivity;
import com.greattone.greattone.data.Constants;
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

import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class LoginProxy {
	String type;int id;int classid;
	String username;
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
	public  void setData(String type,int id,int classid,String username){
		this.type=type;
		this.id=id;
		this.classid=classid;
		this.username=username;
	}
	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	private void toMain( BaseActivity activity,String name,String password) {
		if (type==null){
			activity.	preferences.edit().putString("name", name)
					.putString("password", password).commit();
			Intent intent=new Intent(activity, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			activity.startActivity(intent);
		}
		if (type.equals("chating")) {// 聊天信息
			Intent i = new Intent(context, MyChatActivity.class);
			i.putExtra("name", username);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		} else if (type.equals("QAAsk")
				|| type.equals("payend")) {// qa问答
			toQA(context, 1);
		} else if (type.equals("QABiaojia")) {// qa问答
			toQA(context, 2);
		} else if (type.equals("QAHd")) {// qa问答
			toQA(context, 3);
		} else if (type.equals("feed")) {// 关注
			Intent i = new Intent(context, DirectoryActivity2.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		} else if (type.equals("pinglun")) {// 评论
			Intent i = new Intent(context, PlazaMusicDetailsActivity.class);
				i.putExtra("id",id);
			 i.putExtra("classid",classid);
//							intent.putExtra("videourl", blogsList.get(position).getShipin());
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		}
		MyProgressDialog.Cancel();
		activity.finish();
	}
	/**
	 * qa问答
	 *
	 * @param context
	 * @param num
	 */
	private void toQA(Context context, int num) {
		SharedPreferences preferences= context.getSharedPreferences(Constants.PREFERENCES_NAME_USER,
				Context.MODE_PRIVATE);
		if (preferences.getInt("cked", 0)==1) {
			Intent i = new Intent(context, MyQAActivity.class);
			i.putExtra("num", num);// 我的问题
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		}else
			Toast.makeText(context.getApplicationContext(), "非会员无法使用该功能", Toast.LENGTH_LONG).show();
	}
}
