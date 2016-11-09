package com.greattone.greattone.Listener;

import org.json.JSONObject;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class BaseUiListener implements IUiListener {

	@Override
	public void onComplete(Object response) {
        if (null == response) {
        	//"返回为空", "登录失败"
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
         	//"返回为空", "登录失败"
            return;
        }
        //登录成功
		doComplete((JSONObject)response);
	}

	protected void doComplete(JSONObject values) {
	
	}

	@Override
	public void onError(UiError e) {
//		LogUtil.e("onError==>code:" + e.errorCode + ", msg:"
//				+ e.errorMessage + ", detail:" + e.errorDetail);
	}

	@Override
	public void onCancel() {
//		LogUtil.e("onCancel==>");
	}
}
