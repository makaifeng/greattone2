package com.greattone.greattone.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.umeng.analytics.MobclickAgent;
public class BaseFragment extends Fragment {
	private  String mPageName; 
	public Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	/**
	 * 加入的volley网络请求的队列中
	 * 
	 * @param request
	 */
	public void addRequest(Request<?> request) {
		((BaseActivity) context).addRequest(request);
	}

	public void toast(String paramString) {
		((BaseActivity)context ).toast(paramString);
//		if ((paramString == null) || (paramString.equals("")))
//			return;
//		if (((BaseActivity)context ).toast== null){
//			this.toast = Toast.makeText(context, paramString,
//					Toast.LENGTH_SHORT);
//		}else {
//			this.toast=	((BaseActivity)context ).toast;
//			toast.cancel();
//			toast.setText(paramString);
//			this.toast.show();
//		}
	}
	@Override
	public void onPause() {
		super.onPause();
	
		 MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(context);
	}
@Override
public void onResume() {
	super.onResume();
	mPageName	=getClass().getSimpleName();
    MobclickAgent.onPageStart(mPageName);
	MobclickAgent.onResume(context);
}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}


}
