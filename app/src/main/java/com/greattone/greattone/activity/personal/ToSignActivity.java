package com.greattone.greattone.activity.personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.PayActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Sign;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

/**
 * 签约
 * 
 * @author makaifeng
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class ToSignActivity extends BaseActivity {
	private TextView tv_sign_up;
	private TextView tv_cost;
	private Sign  order;
private WebView webview;
private CheckBox cb_agreement;
private TextView tv_agreement;
private TextView textview1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_sign);
		initView();
		getData();
	}

	private void initView() {
		setHead("签约", true, true);
		textview1 =(TextView) findViewById(R.id.textview1);
		webview = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
//		webSettings.setJavaScriptEnabled(true);
//		 // 设置可以访问文件
//		 webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		String agreement = "《品牌会员认证协议》";
		if (Data.myinfo.getGroupid()==5) {
			textview1.setText("立刻充值\n成为认证品牌会员");
			agreement="《品牌会员认证协议》";
			// 加载需要显示的网页
			webview.loadUrl("http://m.greattone.net/app/ppqy.html");
		}else if (Data.myinfo.getGroupid()==4){
			textview1.setText("立刻充值\n成为认证琴行教室会员");
			agreement="《琴行教室认证协议》";
			// 加载需要显示的网页
			webview.loadUrl("http://m.greattone.net/app/qhqy.html");
		}
		// 设置Web视图
		webview.setWebViewClient(new webViewClient());
		cb_agreement =(CheckBox) findViewById(R.id.cb_agreement);
		cb_agreement.setOnCheckedChangeListener(checkedChangeListener);
		SpannableString spannableString=new SpannableString(agreement);
		spannableString.setSpan( new UnderlineSpan(), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_agreement =(TextView) findViewById(R.id.tv_agreement);
		tv_agreement.setText(spannableString);
		tv_agreement.setOnClickListener(clickListener);
		tv_cost =(TextView) findViewById(R.id.tv_cost);
		tv_sign_up =(TextView) findViewById(R.id.tv_sign_up);
		tv_sign_up.setOnClickListener(clickListener);
	}

	private void initViewData() {
		if (Data.myinfo.getGroupid()==5) {
			if (order != null) {
				tv_cost.setText("认证费用：￥" + order.getMoney() + "/年");
			}else if (Data.myinfo.getGroupid()==4){
				tv_cost.setVisibility(View.GONE);
			}else {
				tv_cost.setVisibility(View.GONE);
			}
		}
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_sign_up:
				if (cb_agreement.isChecked()) {
					if (Data.myinfo.getGroupid()==5) {
						getOrderData();
					}else if (Data.myinfo.getGroupid()==4){
						Intent intent = new Intent(context,
								SelectPackeageAct.class);
						intent.putExtra("title","《琴行教室认证协议》");
						intent.putExtra("urlPath", "http://m.greattone.net/app/qhqy-agreement.html");
						startActivity(intent);
					}
				}else{
					toast("请确认协议");
				}
				break;
			case R.id.tv_agreement:
				Intent intent = new Intent(context,
						WebActivity.class);
				if (Data.myinfo.getGroupid()==5) {
					intent.putExtra("title","《品牌会员认证协议》");
					intent.putExtra("urlPath", "http://m.greattone.net/app/ppqy-agreement.html");
				}else if (Data.myinfo.getGroupid()==4){
					intent.putExtra("title","《琴行教室认证协议》");
					intent.putExtra("urlPath", "http://m.greattone.net/app/qhqy-agreement.html");
				}
				startActivity(intent);
				break;
//			case R.id.ll_pay_menth:// 支付方式
//				List<String> mList = new ArrayList<String>();
//				String[] str = null;
//				str = getResources().getStringArray(R.array.pay_type);
//				// String[]
//				// str2=getResources().getStringArray(R.array.pay_type_tiaowan);
//				for (String string : str) {
//					mList.add(string);
//				}
//				// for (String string : str2) {
//				// mList.add(string);
//				// }
//				NormalPopuWindow popuWindow = new NormalPopuWindow(context, mList, ll_pay_menth);
//				popuWindow.setOnItemClickBack(new OnItemClickBack() {
//
//					@Override
//					public void OnClick(int position, String text) {
//						paystate.setText(text);
//						// payState=position;
//					}
//				});
//				popuWindow.show();
//				break;

			default:
				break;
			}
		}

	};
	OnCheckedChangeListener	checkedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
		}
	};

	/**
	 * 获取订单信息
	 */
	private void getOrderData() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getSignOrderData(context,null, new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null && message.getData().startsWith("{")) {
					Sign	order = JSON.parseObject(message.getData(), Sign.class);
					 Intent intent = new Intent(ToSignActivity.this,
							 PayActivity.class);
					 intent.putExtra("name",order.getShoptitle());
					 intent.putExtra("contant", order.getContent());
					 intent.putExtra("price",  order.getMoney());
//					 intent.putExtra("price",  "0.01");
					 intent.putExtra("orderId", "QY"+  order.getDdid());
					 intent.putExtra("bitype", "人民币");
					 startActivityForResult(intent, 1);
				}
				MyProgressDialog.Cancel();
			}
			
		}, null);
	}
	private void getData() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getSignData(context, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null && message.getData().startsWith("{")) {
					try {
						order = JSON.parseObject(JSON.parseObject(message.getData()).getString("order"), Sign.class);
//						sign = JSON.parseObject(JSON.parseObject(message.getData()).getString("sign"), Sign.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				initViewData();
				// num++;
				// MyProgressDialog.Cancel(num, 2);
				MyProgressDialog.Cancel();
			}

		}, null);
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
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0==1&&arg1==RESULT_OK) {
			finish();
		}
	}
}
