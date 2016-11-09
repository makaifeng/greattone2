package com.greattone.greattone.allpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.greattone.greattone.util.Textutil;


public class AllPay {
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public static void pay(final Context context,String name,String contant,String price,String orderId) {
		long time=System.currentTimeMillis();
		String aa=String.valueOf(time);
		String ss=aa.substring(aa.length()-8, aa.length());
		PAYMENTTYPE paymentType = null;
		Intent intent = new Intent(context, PaymentActivity.class);
		paymentType = PAYMENTTYPE.ALL;				
		//************** 沒PlatformID ******************
		CreateTrade oCreateTrade = new CreateTrade(
				Config.MerchantID_test,				//廠商編號 
				Config.AppCode_test, 				//App代碼
				orderId+ss, 		//廠商交易編號
				Config.getMerchantTradeDate(), 		//廠商交易時間
				Integer.valueOf(price), 			//交易金額
				Textutil.Sim2Tra(contant).toString(), 				//交易描述
					Textutil.Sim2Tra(name).toString(), 				//商品名稱
				paymentType, 						//預設付款方式
				ENVIRONMENT.OFFICIAL);				//介接環境 : STAGE為測試，OFFICIAL為正式
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, oCreateTrade);
		((Activity) context).startActivityForResult(intent, Config.REQUEST_CODE);
	}
}
