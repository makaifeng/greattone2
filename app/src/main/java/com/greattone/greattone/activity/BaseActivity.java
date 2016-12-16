package com.greattone.greattone.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greattone.greattone.Listener.ActivityBackListener;
import com.greattone.greattone.Listener.OnRequestPermissionsBackListener;
import com.greattone.greattone.R;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.util.ActiivtyStack;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

public class BaseActivity extends FragmentActivity {
	public	LinearLayout headlayout;
	public RequestQueue queue;
	public SharedPreferences preferences;
//	ImageLoader imageloader = ImageLoader.getInstance();
	// 屏幕宽高，密度
	public int screenWidth;
	public int screenHeight;
	public float density;


	private InputMethodManager manager;
	public Context context;
	private View head;
	private TextView title;
	private TextView tv_other;
	private ImageView back;
	public Toast toast;
	private ActivityBackListener activityBackListener;
	 private  String mPageName;
	private OnRequestPermissionsBackListener onRequestPermissionsBackListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		 ActiivtyStack.getScreenManager().pushActivity(this);  
//		 Data.activityErrorList.add(this);
		queue = Volley.newRequestQueue(this );//初始化网络请求 RequestQueue
		

        
		preferences = getSharedPreferences(Constants.PREFERENCES_NAME_USER,
				MODE_PRIVATE);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 获取屏幕宽高,密度
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		screenWidth = metric.widthPixels; // 屏幕宽度（像素）
		screenHeight = metric.heightPixels; // 屏幕高度（像素）
		density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）

		// options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.image_loading) // 设置图片在下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.image_empty)// 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.image_error) // 设置图片加载/解码过程中错误时候显示的图片
		// .cacheInMemory()// 设置下载的图片是否缓存在内存中
		// .cacheOnDisc()// 设置下载的图片是否缓存在SD卡中
		// .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
		// .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
		// // .decodingOptions(android.graphics.BitmapFactory.Options
		// // decodingOptions)//设置图片的解码配置
		// // .delayBeforeLoading(int delayInMillis)//int
		// // delayInMillis为你设置的下载前的延迟时间
		// // 设置图片加入缓存前，对bitmap进行设置
		// // .preProcessor(BitmapProcessor preProcessor)
		// // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
		// .displayer(new FadeInBitmapDisplayer(0))// 是否图片加载好后渐入的动画时间
		// .build();// 构建完成 \
		//

	}

	@SuppressLint("InflateParams")
	@Override
	public void setContentView(int layoutResID) {
		 headlayout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.head, null);
		head = headlayout.findViewById(R.id.rl_head_view);
		title = (TextView) headlayout.findViewById(R.id.tv_head_title);
		back = (ImageView) headlayout.findViewById(R.id.iv_head_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setBackClick();
				finish();
			}
		});
		head.setVisibility(View.GONE);
		View view = LayoutInflater.from(this).inflate(layoutResID, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		headlayout.addView(view);
		setContentView(headlayout);
	}

	/**
	 * 点击标题栏上的返回按钮
	 */
	public void setBackClick() {

	}

	/***
	 * 设置标题
	 * 
	 * @param name
	 *            标题名字
	 * @param isHeadVisibility
	 *            标题栏是否显示
	 * @param isBackVisibility
	 *            标题栏上的返回按钮是否显示
	 */
	public void setHead(CharSequence name,  Boolean isHeadVisibility,
			 boolean isBackVisibility) {
		title.setText(name);
		if (isHeadVisibility) {
			head.setVisibility(View.VISIBLE);
		} else {
			head.setVisibility(View.GONE);
		}
		if (isBackVisibility) {
			back.setVisibility(View.VISIBLE);
		} else {
			back.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 *
	 * @param name
	 * @param onClickListener
     */
	public void setOtherText(CharSequence name,OnClickListener onClickListener) {
		tv_other=(TextView) findViewById(R.id.tv_head_other);
		tv_other.setVisibility(View.VISIBLE);
//		tv_other.setTextSize(13);
		tv_other.setText(name);
		tv_other.setOnClickListener(onClickListener);
	}

	/**
	 * 加入的volley网络请求的队列中
	 * 
	 * @param request
	 */
	public void addRequest(Request<?> request) {
		if (request!=null) {
			request.setRetryPolicy(new DefaultRetryPolicy(3 * 60 * 1000, 1, 1.0f));
			request.setTag(this);
			if (queue!=null) {
				queue.add(request);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	
		 MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
		JPushInterface.onPause(context);
	}
@Override
protected void onResume() {
	super.onResume();
	mPageName	=getClass().getSimpleName();
    MobclickAgent.onPageStart(mPageName);
	MobclickAgent.onResume(this);
	JPushInterface.onResume(context);
}
	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		queue.cancelAll(this);
		// 退出时销毁定位
		// if (mLocClient != null) {
		// mLocClient.stop();
		// }
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			closeKeyBoard();
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 关闭小键盘
	 */
	public void closeKeyBoard() {
		if (getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/** Toast显示 */
	public void toast(String text) {
		if ((text == null) || (text.equals("")))
			return;
		if (this.toast == null) {
			this.toast = Toast.makeText(context.getApplicationContext(),
					text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
		}
		this.toast.show();
	}

	/**
	 * 监听onActivityResult返回
	 */
	public void setActivityBack(ActivityBackListener activityBackListener) {
		this.activityBackListener=activityBackListener;
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (activityBackListener!=null) {
			activityBackListener.activityBack(arg0, arg1, arg2);
		}
	}
	/**
	 * 监听onActivityResult返回
	 */
	public void setOnRequestPermissionsBack(OnRequestPermissionsBackListener onRequestPermissionsBackListener) {
		this.onRequestPermissionsBackListener=onRequestPermissionsBackListener;
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (onRequestPermissionsBackListener!=null)
			onRequestPermissionsBackListener.onRequestPermissionsBack(requestCode, permissions, grantResults);
	}

	@Override
	public void finish() {
		closeKeyBoard();
		MyProgressDialog.Cancel();
		 ActiivtyStack.getScreenManager().popActivity(this);  
		 super.finish();
	}
}
