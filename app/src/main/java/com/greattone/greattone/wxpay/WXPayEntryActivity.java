package com.greattone.greattone.wxpay;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.greattone.greattone.R;
import com.greattone.greattone.data.PayOrder;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        context=this;
    	api = WXAPIFactory.createWXAPI(this, WxPay.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		MyProgressDialog.Dismiss();
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode==0) {//成功支付
				PayOrder.payBack2.PayBackOK(PayOrder.WECHAT_PAY);
			}else if (resp.errCode==-2) {//用户取消
				PayOrder.payBack2.PayBackError(PayOrder.WECHAT_PAY);
			}else if (resp.errCode==-1) {//错误
				PayOrder.payBack2.PayBackError(PayOrder.WECHAT_PAY);
			}
			finish();
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
//			builder.show();
		}else {
			Toast.makeText(context, "支付失败！", Toast.LENGTH_SHORT).show();
		}
	}
}