package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class PaymentActivity extends Activity {
	public static final int RESULT_EXTRAS_NULL = 6001;
//	public static final int RESULT_EXTRAS_INVALID = 6002;
	public static final int RESULT_EXTRAS_CANCEL = 6003;
	public static final String EXTRA_PAYMENT = "MobileSDK.Payment";
	public static final String EXTRA_OPTIONAL = "ChoosePayment.Optional";
	public static final String EXTRA_OPTIONAL_CREDITTYPE = "ChoosePayment.Optional.CreditType";
	
	LinearLayout linearLayout;
	WebView webView1;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitle("AllpayMobileSDK");
		
		CreateTrade oCreateTrade = (CreateTrade)getIntent().getParcelableExtra(EXTRA_PAYMENT);
		
		if(oCreateTrade == null){
			Log.d(Utility.LOGTAG, "PaymentActivity -> EXTRA_PAYMENT is null");
			
			setResult(RESULT_EXTRAS_NULL);
			finish();
			return;
		}
		
		try {
			Log.d(Utility.LOGTAG, "PaymentActivity -> receive : " + oCreateTrade.getJSON());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Collection<Map.Entry<String, String>> postData = oCreateTrade.getPostData();
		
		if(oCreateTrade.getChoosePayment() == PAYMENTTYPE.ATM){			
			OptionalATM oOptionalATM = getIntent().getParcelableExtra(EXTRA_OPTIONAL);
			
			if(oOptionalATM != null){
				Log.d(Utility.LOGTAG, "PaymentActivity -> ChoosePayment ATM : oOptionalATM = " + oOptionalATM.getJSON());
				postData = Utility.combinePostData(postData, oOptionalATM.getPostData());
			}
		}else if(oCreateTrade.getChoosePayment() == PAYMENTTYPE.CVS){
			OptionalCVS oOptionalCVS = (OptionalCVS)getIntent().getParcelableExtra(EXTRA_OPTIONAL);
			
			if(oOptionalCVS != null){
				Log.d(Utility.LOGTAG, "PaymentActivity -> ChoosePayment CVS : OptionalCVS = " + oOptionalCVS.getJSON());
				postData = Utility.combinePostData(postData, oOptionalCVS.getPostData());
			}
		}else if(oCreateTrade.getChoosePayment() == PAYMENTTYPE.CREDIT){
			Log.d(Utility.LOGTAG, "PaymentActivity -> ChoosePayment Credit");
			CREDITTYPE CreditType = (CREDITTYPE)getIntent().getSerializableExtra(EXTRA_OPTIONAL_CREDITTYPE);
			
			if(CreditType != null){
				if(CreditType == CREDITTYPE.INSTALLMENT){
					OptionalCreditInstallment oCreditInstallment = (OptionalCreditInstallment)getIntent().getParcelableExtra(EXTRA_OPTIONAL);
					Log.d(Utility.LOGTAG, "PaymentActivity -> OptionalCreditInstallment = " + oCreditInstallment.getJSON());
					postData = Utility.combinePostData(postData, oCreditInstallment.getPostData(oCreateTrade.getTotalAmount()));
					
				}else if(CreditType == CREDITTYPE.PERIODAMOUNT){
					OptionalCreditPeriodAmount oPeriodAmount = (OptionalCreditPeriodAmount)getIntent().getParcelableExtra(EXTRA_OPTIONAL);
					Log.d(Utility.LOGTAG, "PaymentActivity -> OptionalCreditPeriodAmount = " + oPeriodAmount.getJSON());
					postData = Utility.combinePostData(postData, oPeriodAmount.getPostData());
					
				}
			}
						
		}
		
		
		linearLayout = new LinearLayout(PaymentActivity.this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		linearLayout.setLayoutParams(layoutParams);	
		
		webView1 = new WebView(PaymentActivity.this);
		LayoutParams layoutParams_webView = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		webView1.setLayoutParams(layoutParams_webView);
		webView1.getSettings().setJavaScriptEnabled(true);
		webView1.setWebViewClient(new WebViewClient());
		webView1.setWebChromeClient(new WebChromeClient());
		linearLayout.addView(webView1);
		
		setContentView(linearLayout);
		
		
		Utility.webview_ClientPost(webView1, oCreateTrade.getEnvironment().toString() + Utility.ClientOrder, postData);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_EXTRAS_CANCEL,null);
			finish();
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	
}
