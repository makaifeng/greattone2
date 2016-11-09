package com.greattone.greattone.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.VolleyError;
import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.adapter.StartPagerAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.entity.Filter;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.proxy.LoginProxy;
import com.greattone.greattone.util.ActivityUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.LocationUtil;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class StartActivity extends BaseActivity {
	boolean isLotation;
	boolean isGetMsg;
	boolean isLoadImage;
	boolean isToMain;

	int num;
	final int MixNum = 1;
	private ImageView image;
	private List<ImageData> imageUrlList=new ArrayList<ImageData>();
	private ViewPager viewPager;
Handler handler=new Handler();
//private TextView tv_time;
//private JumpingBeans jumpingBeans1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!isTaskRoot()) { 
			finish(); return; 
			}
		setContentView(R.layout.activity_start);
		initView();
		LanguageUtil.init(context);//获取手机语言
			preferences.edit().putString("version", ActivityUtil.getVersionName(context)).commit();
			getAdvList(3);
			LocationUtil.start(this, myLocationListener);// 开始定位
			getFillter();
	}

private void initViewPager(Bitmap bm) {

	// viewPager
	StartPagerAdapter adapter=new StartPagerAdapter(context, imageUrlList,bm);
	adapter.setOnBtnItemClickListener(btnItemClickListener);//点击图片
	viewPager.setAdapter(adapter);
	viewPager.setCurrentItem(0);
	viewPager.addOnPageChangeListener(new OnPageChangeListener() {

		@Override
		/**
		 * 页面切换后调用 
		 * position  新的页面位置
		 */
		public void onPageSelected(int position) {
			time=3;
			currentItem=position;
			handler.removeCallbacks(runnable);
			handler.post(runnable);
			if (position==imageUrlList.size()-1) {
				isLoadImage=true;
			}
		}
//		OnClickListener lis=new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				ToMain();
//			}
//		};

		@Override
		/**
		 * 页面正在滑动的时候，回调
		 */
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
//			if (position==resIds.length-1) {
//				nextPage();
//			}
		}

		@Override
		/**
		 * 当页面状态发生变化的时候，回调
		 */
		public void onPageScrollStateChanged(int state) {
		}
	});
	}
/**
 * Viewpager的图片点击
 */
OnBtnItemClickListener btnItemClickListener= new OnBtnItemClickListener() {
	
	@Override
	public void onItemClick(View v, int position) {
		if (position==imageUrlList.size()-1) {
			handler.removeCallbacks(runnable);
			ToMain();
		}else {
			currentItem=viewPager.getCurrentItem()+1;
			viewPager.setCurrentItem(currentItem);
			time=3;
//			tv_time.setText("将于"+time+"秒后跳转");
//			jumpingBeans1 = JumpingBeans.with(tv_time)
//					 .makeTextJump(0, tv_time.length())
//		             .setIsWave(true)
//		             .setLoopDuration(800)
//		             .build();
		}
	}
};

private void initView() {
	image=(ImageView)findViewById(R.id.iv_frist);
//	tv_time=(TextView)findViewById(R.id.tv_time);
	viewPager = (ViewPager ) findViewById(R.id.viewpager);
	}

/**会员筛选数据*/
	private void getFillter() {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("api", "extend/fillter");
		addRequest(HttpUtil.httpConnectionByPost(context, localHashMap,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						try {
							Data.filter_putong= JSON.parseObject(
									JSON.parseObject(message.getData()).getString(
											"putong_shenfen"), Filter.class);
							Data.filter_star = JSON.parseObject(
									JSON.parseObject(message.getData()).getString(
											"music_star"), Filter.class);
							Data.filter_teacher = JSON.parseObject(
									JSON.parseObject(message.getData()).getString(
											"music_teacher"), Filter.class);
							Data.filter_classroom = JSON.parseObject(
									JSON.parseObject(message.getData()).getString(
											"music_classroom"), Filter.class);
//							Data.haiXuanFilter = JSON.parseObject(
//									JSON.parseObject(message.getData()).getString(
//											"hxbm"), HaiXuanFilter.class);
						} catch (JSONException e) {
						}
						addNum();
					}

				}, null));
	}



	protected void addNum() {
		num++;
		if (num == MixNum) {
			isGetMsg = true;
			finishActivity();
		}
	}

	AMapLocationListener myLocationListener=new AMapLocationListener() {
		
		@Override
		public void onLocationChanged(AMapLocation location) {

			// map view 销毁后不在处理新接收的位置
			if (location != null) {
				Data.myLocation = location;
			} else {
				toast(getResources().getString(R.string.无法定位));
			}
			isLotation = true;
			finishActivity();
			LocationUtil.stop();
		}
	};

	/**
	 * 跳转到主页面
	 */
	private void ToMain() {
		if (!isToMain) {
			String name = preferences.getString("name", "");
			String password = preferences.getString("password", "");
			if (TextUtils.isEmpty(name)||TextUtils.isEmpty(password)) {
				Intent intent=new Intent(StartActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				isToMain=true;
				finishActivity();
			}else{
				new LoginProxy().Login(this, name, password,1);
			}
		}
	
	};
	private void finishActivity() {
		if (isLotation  && isGetMsg&&isLoadImage&&isToMain) {
			finish();
		}
	}
	int time=3;
	protected int currentItem;
	Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			if (time==0) {
				if (currentItem==imageUrlList.size()-1) {
					ToMain();
				}else {
					currentItem=viewPager.getCurrentItem()+1;
					viewPager.setCurrentItem(currentItem);
					time=5;
//					tv_time.setText("将于"+time+"秒后跳转");
//					jumpingBeans1 = JumpingBeans.with(tv_time)
//							 .makeTextJump(0, tv_time.length())
//				             .setIsWave(true)
//				             .setLoopDuration(800)
//				             .build();
				}
			}else{
				time--;
//			tv_time.setText("将于"+time+"秒后跳转");
//			jumpingBeans1 = JumpingBeans.with(tv_time)
//					 .makeTextJump(0, tv_time.length())
//		             .setIsWave(true)
//		             .setLoopDuration(1000)
//		             .build();
			handler.postDelayed(this, 1000);
			}
		}
	};
	/**
	 * 获取启动图或引导图
	 * 
	 * @return
	 */
	private void getAdvList( int classid) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "extend/getAdvList");
		map.put("classid", classid+"");
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {
					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&&message.getData().startsWith("[")) {
							imageUrlList=JSON.parseArray(message.getData(), ImageData.class);
							ImageLoaderUtil.getInstance().loadImage(imageUrlList.get(0).getPic(),new SimpleImageLoadingListener() {
								
								@Override
								public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
									initViewPager(arg2);
									handler.post(runnable);
									isLoadImage=true;
									StartActivity.this.image.setVisibility(View.GONE);
								}
								
								@Override
								public void onLoadingFailed(String imageUri,
										View view, FailReason failReason) {
									super.onLoadingFailed(imageUri, view, failReason);
									isLoadImage=true;
									ToMain();
								}
							});
						}else{
							isLoadImage=true;
							ToMain();
						}
//						addPointView();
					}

				}, new ErrorResponseListener() {
					
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						isLoadImage=true;
						ToMain();
					}
					
					@Override
					public void setErrorResponseHandle(VolleyError error) {
						isLoadImage=true;
						ToMain();
					}
				}));
	}
	@Override
	public void finish() {
//		if (jumpingBeans1!=null) {
//			jumpingBeans1.stopJumping();
//		}
		super.finish();
	}
}
