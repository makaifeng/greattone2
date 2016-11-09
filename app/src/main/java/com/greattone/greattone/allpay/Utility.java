package com.greattone.greattone.allpay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.webkit.WebView;

class Utility {
	public static final String LOGTAG = "AllpayMobileSDK";
	public static final String ClientOrder = "Mobile/CreateClientOrder/V2";
	public static final String ServerOrder = "Mobile/CreateServerOrder";
	public static final String OTP = "Mobile/VerifyOtpCode";
	
	
	public static void webview_ClientPost(WebView webView, String url, Collection< Map.Entry<String, String>> postData){
		StringBuilder sb = new StringBuilder();
		
		sb.append("<html><head></head>");
		sb.append("<body onload='form1.submit()'>");
		sb.append(String.format("<form id='form1' action='%s' method='%s'>", url, "post"));
		for (Map.Entry<String, String> item : postData) {
			sb.append(String.format("<input name='%s' type='hidden' value='%s' />", item.getKey(), item.getValue()));
		}
		sb.append("</form></body></html>");
		
		Log.d(LOGTAG,String.format("Utility.webview_ClientPost -> %s", sb.toString()));
		
		webView.loadData(sb.toString(), "text/html", "UTF-8");
		
	}
	
	
	public static  Collection<Map.Entry<String, String>> combinePostData(Collection< Map.Entry<String, String>> col1, Collection< Map.Entry<String, String>> col2){
		Map<String, String> mapParams = new HashMap<String, String>();
		
		for (Map.Entry<String, String> item : col1) {
			mapParams.put(item.getKey(), item.getValue());
		}
		
		for (Map.Entry<String, String> item : col2) {
			mapParams.put(item.getKey(), item.getValue());
		}
		
		return mapParams.entrySet();
	}
	
		
}
