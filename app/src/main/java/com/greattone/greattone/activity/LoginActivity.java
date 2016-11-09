package com.greattone.greattone.activity;

import org.json.JSONObject;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.BaseUiListener;
import com.greattone.greattone.activity.personal.RegisterActivity;
import com.greattone.greattone.activity.personal.RevisedPasswordActivity;
import com.greattone.greattone.proxy.LoginProxy;
import com.greattone.greattone.util.LogUtil;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {
	private Context context;
	private EditText et_name;
	private EditText et_password;
	private Button btn_sign_in;
	private TextView tv_free_registration;
	private TextView tv_forget_password;
	private String name;
	private String password;
//	private int num;
//	private final int MixNum = 2;
	private Tencent mTencent;
private TextView tv_WX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		initview();
		initData();
	}

	/**
	 * 加载view
	 */
	private void initview() {
		// 账号
		et_name = (EditText) findViewById(R.id.et_name);
			
		// 密码
		et_password = (EditText) findViewById(R.id.et_password);
		// 登录
		btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
		btn_sign_in.setOnClickListener(lis);
		// 免费注册
		tv_free_registration = (TextView) findViewById(R.id.tv_free_registration);
		tv_free_registration.setOnClickListener(lis);
		// 忘记密码
		tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
		tv_forget_password.setOnClickListener(lis);
		//qq登录
		tv_WX = (TextView) findViewById(R.id.tv_WX);
		tv_WX.setOnClickListener(lis);

	}

	private void initData() {
		name = preferences.getString("name", "");
		password = preferences.getString("password", "");
		et_name.setText(name);
		et_password.setText(password);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == btn_sign_in) {// 登录
//				Intent intent = new Intent(context,MediaRecorderDemoActivity.class);
//			startActivityForResult(intent, 0);
				LoginIn();
				// Intent intent = new Intent(LoginActivity.this,
				// PayActivity.class);
				// intent.putExtra("name", "测试");
				// intent.putExtra("contant","测试");
				// intent.putExtra("price", "0.01");
				// intent.putExtra("orderId", System.currentTimeMillis()+"r");
				// startActivityForResult(intent, 3);
				// LinkedHashMap<String, String> map = new LinkedHashMap<String,
				// String>();
				// map.put("out_trade_no","2016030752505156");
				// addRequest(HttpUtil2.httpConnectionByPost(context,HttpConstants2.SERVER_URL+"/e/appdemo/zhuang.php",
				// map,
				// new ResponseListener() {
				//
				// @Override
				// public void setResponseHandle(Message2 message) {
				//
				// }
				// }, null));
//				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//				map.put("out_trade_no","2016041399989751");
//				addRequest(HttpUtil.httpConnectionByPost(context,HttpConstants2.SERVER_URL+"/e/appdemo/zhuang.php", map,
//						new ResponseListener() {
//
//							@Override
//							public void setResponseHandle(Message2 message) {
//							
//							}
//						}, null));
			} else if (v == tv_free_registration) {// 免费注册
				startActivityForResult(new Intent(context,
						RegisterActivity.class), 1);
//				IWXAPI api = WXAPIFactory.createWXAPI(context,com.greattone.greattone.data.Constants.WX_APPID, true);
//				api.registerApp(com.greattone.greattone.data.Constants.WX_APPID);
//			    final SendAuth.Req req = new SendAuth.Req();
//			    req.scope = "snsapi_userinfo";
//			    req.state = "wechat_sdk_demo_test";
//			    api.sendReq(req);
//				mTencent = Tencent.createInstance("1105386990", context.getApplicationContext());
//			    if (!mTencent.isSessionValid())
//		        {
//		            mTencent.login(LoginActivity.this, "all", loginListener);
//		        }
			} else if (v == tv_forget_password) {// 忘记密码
				startActivityForResult(new Intent(context,
						RevisedPasswordActivity.class), 1);
			} else if (v == tv_WX) {//qq登录
				IWXAPI api = WXAPIFactory.createWXAPI(context,com.greattone.greattone.data.Constants.WX_APPID, true);
				api.registerApp(com.greattone.greattone.data.Constants.WX_APPID);
			    final SendAuth.Req req = new SendAuth.Req();
			    req.scope = "snsapi_userinfo";
			    req.state = "wechat_sdk_demo_test";
			    api.sendReq(req);
			}
		}
	};


	/**
	 * 登录
	 */
	private void LoginIn() { 
		// 获取输入的姓名
		name = et_name.getText().toString().trim();
		// 获取输入的密码
		password = et_password.getText().toString().trim();
		// 判断输了的数据是否正确
		if (textCanUse()) {
			// Login();
			new LoginProxy().Login(this, name, password,0);
//			Login2();
		}

	}


//	/**
//	 * 登錄
//	 * 
//	 * @return
//	 */
//	private void Login2() {
//		MyProgressDialog.show(context);
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "user/login");
//		map.put("password", password);
//		map.put("username", name);
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						Data.user = JSON.parseObject(message.getData(),
//								LoginInfo.class);
//						   MobclickAgent.onProfileSignIn(Data.user.getUserid());
//						JPushInterface.setAlias(context,
//								Data.user.getUsername(),
//								new TagAliasCallback() {
//
//									@Override
//									public void gotResult(int arg0,
//											String arg1, Set<String> arg2) {
//
//									}
//								});
////						LogUtil.i("Registration ID="
////								+ JPushInterface.getRegistrationID(context));
////						addNum();
////						getFava();
//						getUserMsg();
//
//					}
//
//				}, null));
//	}

//	/** 收藏文件夹 */
//	private void getFava() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "fava/class");
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						Data.favaList = JSON.parseArray(message.getData(),
//								Fava.class);
//						addNum();
//					}
//
//				}, null));
//	}

//	/**
//	 * 获取个人信息
//	 * 
//	 * @return
//	 */
//	private void getUserMsg() {
//		HttpProxyUtil.getUserInfo(context, null, null, new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						Data.myinfo = JSON.parseObject(message.getData(),
//								UserInfo.class);
//						toMain();
//					}
//
//				}, null);
//	}

//	protected void addNum() {
//		num++;
//		if (num == MixNum) {
//			toMain();
//		}
//	}

//	/**
//	 * 跳转到主页面
//	 * 
//	 * @return
//	 */
//	private void toMain() {
//		preferences.edit().putString("name", name)
//				.putString("password", password).commit();
//		Intent intent=new Intent(context, MainActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//		startActivity(intent);
//		MyProgressDialog.Cancel();
//		finish();
//	}


	/**
	 * 判断输了的数据是否正确
	 * 
	 * @param name
	 * @param password
	 * @param password2
	 * @return
	 */
	boolean textCanUse() {
		// 判断姓名是否为空
		if (TextUtils.isEmpty(name)) {
			toast(getResources().getString(R.string.请输入手机号));
			return false;
			// 判断密码是否为空
		} else if (TextUtils.isEmpty(password)) {
			toast(getResources().getString(R.string.请输入密码));
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == Constants.REQUEST_LOGIN||	requestCode == Constants.REQUEST_APPBAR) {  
			  Tencent.onActivityResultData(requestCode,resultCode,data, loginListener);    
			  }
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				et_name.setText(data.getStringExtra("username"));
				et_password.setText(data.getStringExtra("password"));
			}
		}
	}

	@Override
	public void onBackPressed() {
		exit();
	}

	private long exitTime;

	/**
	 * 按2次返回退出
	 */
	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			// 弹窗显示文本"再按一次退出程序"
			toast(getResources().getString(R.string.click_again_from_exit));
			exitTime = System.currentTimeMillis();
		} else {
			finish();
			System.exit(0);// 关闭整个程序
		}
	}
	/**登陆回调*/
	IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    /**获取用户信息回调*/
    IUiListener infoListener = new BaseUiListener() {
    	@Override
    	protected void doComplete(JSONObject values) {
//    		LogUtil.e(values.toString());
    	}
    };
    /**获取用户信息*/
    private void updateUserInfo() {
		com.tencent.connect.UserInfo mInfo = new com.tencent.connect.UserInfo(context, mTencent.getQQToken());
		mInfo.getUserInfo(infoListener);
    }
    /**加载回调信息*/
	public  void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
}
