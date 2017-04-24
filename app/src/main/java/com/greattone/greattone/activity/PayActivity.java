package com.greattone.greattone.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.alipay.AliPay;
import com.greattone.greattone.allpay.AllPay;
import com.greattone.greattone.data.HttpConstants2;
import com.greattone.greattone.data.PayOrder.PayBack;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.wxpay.WxPay;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * 支付
 * @author Administrator
 */
public class PayActivity extends BaseActivity {
	private String name;
	private String contant;
	private String price;
	private String orderId;
	private TextView paystate;
	private int payState=0;
	private LinearLayout ll_pay_menth;
	private String bitype;
	private TextView tv_hint;
String paytype;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		name=getIntent().getStringExtra("name");
		contant=getIntent().getStringExtra("contant");
		price=getIntent().getStringExtra("price");
		if (price.equals("0")) { setResult(RESULT_OK);finish();}
		orderId=getIntent().getStringExtra("orderId");
		bitype=getIntent().getStringExtra("bitype");
		bitype=bitype==null?"人民币":bitype;
		paytype=	getIntent().getStringExtra("type");
		setHead(getResources().getString(R.string.pay), true, true);
		
		tv_hint=	(TextView)findViewById(R.id.tv_hint);
		ll_pay_menth=	(LinearLayout)findViewById(R.id.ll_pay_menth);
				ll_pay_menth.setOnClickListener(lis);
		paystate= ((TextView) findViewById(R.id.activity_pay_paystate));
		
		findViewById(R.id.activity_pay_commit).setOnClickListener(lis);
		if (bitype.equals("人民币")) {
			tv_hint.setVisibility(View.GONE);
		}else{
			paystate.setText("欧付宝");
			tv_hint.setVisibility(View.VISIBLE);
			tv_hint.setText(""); 
		}
	}
	private OnClickListener lis=new OnClickListener() {
		

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_pay_commit://支付
				ToPay();
				break;
			case R.id.ll_pay_menth://支付方式
				List<String> mList=new ArrayList<String>();
				 String[] str = null;
				if (bitype.equals("人民币")) {
				str=getResources().getStringArray(R.array.pay_type);
				}else{
					str=getResources().getStringArray(R.array.pay_type_tiaowan);
				}
				for (String string : str) {
					mList.add(string);
				}
				NormalPopuWindow popuWindow=new NormalPopuWindow(context,mList, ll_pay_menth);
				popuWindow.setOnItemClickBack(new OnItemClickBack() {
					
					@Override
					public void OnClick(int position, String text) {
						paystate.setText(text);
						payState=position;
					}
				});
				popuWindow.show();
				break;

			default:
				break;
			}
		}
	};
	protected void ToPay() {
		MyProgressDialog.show(context);
		if (bitype.endsWith("人民币")) {
				if(	payState==0){//支付宝支付
					AliPay.pay(context, name, contant, price, orderId, payBack);
				}else if(	payState==1){//微信支付
					WxPay.sendPayReq(context,  name, contant, price, orderId);
				}else {//银联支付
				}
		}else{
			AllPay.pay(context,name, contant,price, orderId);
		}
	}
	PayBack payBack=new PayBack() {
		
		@Override
		public void PayBackOK(int payType) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("out_trade_no",orderId);
			String url="";
			if (paytype!=null&&paytype.equals("shangcheng")){//商城订单
				url=HttpConstants2.SERVER_URL+"/e/appdemo/zhuang2.php";
			}else url=HttpConstants2.SERVER_URL+"/e/appdemo/zhuang.php";


			addRequest(HttpUtil.httpConnectionByPost(context,url, map,
					new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							setResult(RESULT_OK);
							finish();
						}
					}, null));
		}
		
		@Override
		public void PayBackError(int payType) {
			MyProgressDialog.Cancel();
		}
	};
	@Override
	public void setBackClick() {
		super.setBackClick();
		setResult(RESULT_CANCELED);
	}
}
