package com.greattone.greattone.activity.haixuan_and_activitise;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HuodongWebAct extends BaseActivity {

	private WebView webview;
	private String urlPath;
	private String id;
	private int orientation;
	String mid = "22";
	String classid = "78";//会员活动 78

	// private boolean isFristIntent = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_web);
			getIntentData();
			MyProgressDialog.show(context);
			initView();
			startAction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**启动方法
 * @throws NoSuchMethodException 
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException */
	private void startAction() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String action=getIntent().getStringExtra("action");
		if (action==null) {
			return;
		}
		//通过反射启动方法
		Class<?> cls=getClass();
		 Method m1 = cls.getDeclaredMethod(action);
		 m1.invoke(this); 
	}

	private void getIntentData() {
		urlPath = getIntent().getStringExtra("urlPath");
		id = getIntent().getStringExtra("id");
		orientation = getIntent().getIntExtra("orientation", 0);
		if (orientation==1) {//橫屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		if (getIntent().getStringExtra("title")!=null) {
			setHead(getIntent().getStringExtra("title"), true, true);
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {


		webview = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		 // 设置可以访问文件
		 webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		//允许js弹出窗口
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);;
		// 加载需要显示的网页
//	 urlPath = "http://www.greattone.net/apple/html/index.html";
		webview.loadUrl(urlPath);
		webview.addJavascriptInterface(new JsInteration(this), "android");
		// 设置Web视图
		webview.setWebViewClient(new webViewClient());
		webview.setWebChromeClient(new webChromeClient() );
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_head_back:
				if (webview.canGoBack()) {
					webview.goBack(); // goBack()表示返回WebView的上一页面
				} else {
					finish();
				}
				break;

			default:
				break;
			}
		}
	};


	// Web视图
	private class webChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message, final  JsResult result) {
//			AlertDialog.Builder b2 = new AlertDialog.Builder(context)
//					.setTitle(R.string.title).setMessage(message)
//					.setPositiveButton("ok",
//							new AlertDialog.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									result.confirm();
//									// MyWebView.this.finish();
//								}
//							});
//			b2.setCancelable(false);
//			b2.create();
//			b2.show();
			return super.onJsAlert(view, url, message, result);
		}
	}
	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			MyProgressDialog.Cancel();
			super.onPageFinished(view, url);
		}
	}
	public class JsInteration {
		private Context mContext;

		public JsInteration(Context context) {
			this.mContext = context;
		}

		/**
		 * 在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
		 * 此方法名称一定要和js中showInfoFromJava方法一样
		 *
		 * @param json
		 */
		@JavascriptInterface
		public void back(String json) {
//			Toast.makeText(mContext, "来自js的信息:" + json.toString(), Toast.LENGTH_SHORT).show();
			try {
				JSONObject jsonObject = new JSONObject(json);

				Iterator<String> keyIter= jsonObject.keys();
				String key;
				String value ;
				Map<String, String> valueMap = new HashMap<String, String>();
				while (keyIter.hasNext()) {
					key = keyIter.next();
					value = jsonObject.getString(key);
					valueMap.put(key, value);
				}
				post2(valueMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**	报名*/
	protected void post2(	Map<String, String> map) {
		map.put("api", "post/ecms_bm");
		map.put("mid", mid);
		map.put("enews", "MAddInfo");
		map.put("bao_type","4");//活动
		map.put("hai_id", id);
		map.put("classid", classid);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new HttpUtil.ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
//						toast("已生成订单，去支付");
						if (message.getData()!=null&&message.getData().startsWith("{")) {
							com.alibaba.fastjson.JSONObject js = JSON.parseObject(message.getData());
							Intent intent = new Intent(context, PayActivity.class);
							intent.putExtra("name", js.getString("payname"));
							intent.putExtra("contant",js.getString("payname"));
							intent.putExtra("price", js.getString("price"));
							intent.putExtra("bitype", js.getString("bitype"));
							intent.putExtra("orderId",js.getString("orderid"));
							((Activity) context).startActivityForResult(intent, 3);
							finish();
						}
					}
				}, null));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 3&&resultCode==RESULT_OK){
			finish();
			setResult(RESULT_OK);
		}

	}
}
