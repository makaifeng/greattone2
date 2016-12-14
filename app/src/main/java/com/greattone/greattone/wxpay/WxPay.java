package com.greattone.greattone.wxpay;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.greattone.greattone.dialog.MyProgressDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class WxPay {
	// appid
	// 请同时修改 androidmanifest.xml里面，.PayActivityd里的属性<data
	// android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
	public static final String APP_ID = "wx1ca72dd2170d5611";

	// 商户号
	public static final String MCH_ID = "1283498201";

	// API密钥，在商户平台设置
	public static final String API_KEY = "2KhrcaGRnZWbNp3LET3MtTq2tMNrMs4W";// "373587e75669bd197bb62c5167d06e8f";

	private static Map<String, String> resultunifiedorder;
	/**
	 * 生成支付参数
	 * 
	 * @param wxUnifiedorder
	 */
	private static PayReq genPayReq(WxUnifiedorder wxUnifiedorder) {
		PayReq req = new PayReq();
		req.appId = APP_ID;// appid
		req.partnerId = MCH_ID;// 商户号
//		req.prepayId = wxUnifiedorder.getPrepay_id();// 预支付交易会话ID
		req.prepayId = resultunifiedorder.get("prepay_id");// 预支付交易会话ID
		req.packageValue = "Sign=WXPay";// 扩展字段，暂填写固定值Sign=WXPay
		req.nonceStr = genNonceStr();// 随机字符串
//		req.nonceStr = wxUnifiedorder.getNonce_str();// 随机字符串
		req.timeStamp = String.valueOf(genTimeStamp());// 时间戳

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);// 签名

		// show.setText(sb.toString());

		// Log.e("orion", signParams.toString());
		return req;
	}

	/**
	 * 发起支付
	 */
	public static void sendPayReq(Context context, String name,
			String contant, String price, String orderId) {
		resultunifiedorder=new HashMap<String, String>();
		resultunifiedorder.put("price", price);
		resultunifiedorder.put("orderId", orderId);
		resultunifiedorder.put("name", name);
		resultunifiedorder.put("contant", contant);
		GetPrepayIdTask getPrepayId = new GetPrepayIdTask(context);
		getPrepayId.execute();
//		PayReq req = genPayReq(wxUnifiedorder);
//		IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
//		msgApi.registerApp(APP_ID);
//		msgApi.sendReq(req);
//		MyProgressDialog.Cancel();
	}

	private static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(API_KEY);

		// this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase(Locale.CHINA);
		// Log.e("orion",appSign);
		return appSign;
	}

	private static class GetPrepayIdTask extends
			AsyncTask<Void, Void, Map<String, String>> {
		Context context;

		public GetPrepayIdTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			MyProgressDialog.show(context);
		}

		@Override
		protected void onPostExecute(Map<String, String> result) {
			// if (dialog != null) {
			// dialog.dismiss();
			// }
//			 sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
//			show.setText(sb.toString());

			resultunifiedorder = result;
			PayReq req = genPayReq(null);
			IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
			msgApi.registerApp(APP_ID);
			msgApi.sendReq(req);
			MyProgressDialog.Cancel();
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String, String> doInBackground(Void... params) {

			String url = String
					.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion", entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String, String> xml = decodeXml(content);

			return xml;
		}
	}
	   //
		private static String genProductArgs() {
			StringBuffer xml = new StringBuffer();

			try {
				String	nonceStr = genNonceStr();


				xml.append("</xml>");
	            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
				packageParams.add(new BasicNameValuePair("appid", APP_ID));
				packageParams.add(new BasicNameValuePair("body", "weixin"));
//				packageParams.add(new BasicNameValuePair("body",resultunifiedorder.get("name")));//内容
//				packageParams.add(new BasicNameValuePair("notify_url", ""));//异步回调接口
//				packageParams.add(new BasicNameValuePair("out_trade_no",resultunifiedorder.get("orderId")));//订单号
//				packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
//				packageParams.add(new BasicNameValuePair("total_fee",resultunifiedorder.get("price")));//价格
				packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
				packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
				packageParams.add(new BasicNameValuePair("notify_url", "http://121.40.35.3/test"));
				packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
				packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
				packageParams.add(new BasicNameValuePair("total_fee", "1"));
				packageParams.add(new BasicNameValuePair("trade_type", "APP"));

				String sign = genPackageSign(packageParams);
				packageParams.add(new BasicNameValuePair("sign", sign));


			   String xmlstring =toXml(packageParams);

				return xmlstring;

			} catch (Exception e) {
//				Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
				return null;
			}
		}
		/**
			 生成签名
		 */
		
		private static String genPackageSign(List<NameValuePair> params) {
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < params.size(); i++) {
				sb.append(params.get(i).getName());
				sb.append('=');
				sb.append(params.get(i).getValue());
				sb.append('&');
			}
			sb.append("key=");
			sb.append(API_KEY);
			
			
			String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase(Locale.CHINA);
			Log.e("orion",packageSign);
			return packageSign;
		}
		private static String toXml(List<NameValuePair> params) {
			StringBuilder sb = new StringBuilder();
			sb.append("<xml>");
			for (int i = 0; i < params.size(); i++) {
				sb.append("<"+params.get(i).getName()+">");


				sb.append(params.get(i).getValue());
				sb.append("</"+params.get(i).getName()+">");
			}
			sb.append("</xml>");

			Log.e("orion",sb.toString());
			return sb.toString();
		}
		public static Map<String,String> decodeXml(String content) {

			try {
				Map<String, String> xml = new HashMap<String, String>();
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(new StringReader(content));
				int event = parser.getEventType();
				while (event != XmlPullParser.END_DOCUMENT) {

					String nodeName=parser.getName();
					switch (event) {
						case XmlPullParser.START_DOCUMENT:

							break;
						case XmlPullParser.START_TAG:

							if("xml".equals(nodeName)==false){
								//实例化student对象
								xml.put(nodeName,parser.nextText());
							}
							break;
						case XmlPullParser.END_TAG:
							break;
					}
					event = parser.next();
				}

				return xml;
			} catch (Exception e) {
				Log.e("orion",e.toString());
			}
			return null;

		}


		private static String genNonceStr() {
			Random random = new Random();
			return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
		}
		private static String genOutTradNo() {
			Random random = new Random();
			return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
		}

}
