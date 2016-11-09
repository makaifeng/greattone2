package com.greattone.greattone.activity.map;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.PoiSearchAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.util.LocationUtil;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 选择位置
 * @author Administrator
 *
 */
public class SelectBdLocationActivity extends BaseActivity {
//	private int bdPage = 1;
	// private String searchText = "";
	private RelativeLayout rl_search;
	private AutoCompleteTextView keyWorldsView;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	private LatLng myLL;
	private PoiSearch poiSearch;
	private PoiSearchAdapter adapter;
	private List<PoiItem> poiList = new ArrayList<PoiItem>();
//	private SuggestionSearch mSuggestionSearch;
	ArrayAdapter<String> sugAdapter = null;
	boolean isFirstLoad=true;
	boolean isSearch;
	private TextView tv_address;

	protected int locType;
	protected double latitude;
	protected double longitude;
	protected float radius;
	protected String address;
	protected float direction;
	protected String province;
	protected String city;
	protected String district;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_bdlocation);
		initView();
		initLocation();
//		initPoiSearch();// 设置检索数据监听事件
		Handler handler = new Handler(new Handler.Callback() {
			public boolean handleMessage(Message message) {
				MyProgressDialog.Cancel();
				return false;
			}
		});
		handler.sendMessageDelayed(handler.obtainMessage(1), 5000L);
	}

	// private LocationClient mLocClient;

//	/** 设置检索数据监听事件 */
//	private void initPoiSearch() {
//		
//		poiSearch = PoiSearch.newInstance();
//		poiSearch.setOnGetPoiSearchResultListener(poiListener);
////		this.mSuggestionSearch = SuggestionSearch.newInstance();
////		this.mSuggestionSearch
////				.setOnGetSuggestionResultListener(getSuggestionResultListener);
//	}

//	OnGetSuggestionResultListener getSuggestionResultListener = new OnGetSuggestionResultListener() {
//
//		@Override
//		public void onGetSuggestionResult(SuggestionResult arg0) {
//			if ((arg0 == null) || (arg0.getAllSuggestions() == null))
//				return;
//			sugAdapter.clear();
//			Iterator<SuggestionResult.SuggestionInfo> localIterator = arg0
//					.getAllSuggestions().iterator();
//			if (localIterator.hasNext()) {
//				SuggestionResult.SuggestionInfo localSuggestionInfo = (SuggestionResult.SuggestionInfo) localIterator
//						.next();
//				if (localSuggestionInfo.key != null) {
//					sugAdapter.add(localSuggestionInfo.key);
//				}
//			} else {
//				sugAdapter.notifyDataSetChanged();
//			}
//		}
//	};
//	OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
//		// 获取POI检索结果
//		@Override
//		public void onGetPoiResult(PoiResult arg0) {
//			LogUtil.e("PoiResult=====>");
//			if (arg0.getAllPoi() != null) {
//				poiList.addAll(arg0.getAllPoi());
//			}
//			pull_to_refresh.onHeaderRefreshComplete();
//			pull_to_refresh.onFooterRefreshComplete();
//			adapter.notifyDataSetChanged();
//		}
//		
//		// 获取Place详情页检索结果
//		@Override
//		public void onGetPoiDetailResult(PoiDetailResult arg0) {
//			LogUtil.e("PoiDetailResult======>");
//		}
//	};
	 OnPoiSearchListener onPoiSearchListenter=new OnPoiSearchListener() {
			
			@Override
			public void onPoiSearched(PoiResult result, int arg1) {
				if (result.getPois()!= null) {
					poiList.addAll(result.getPois());
				}
				pull_to_refresh.onHeaderRefreshComplete();
				pull_to_refresh.onFooterRefreshComplete();
				adapter.notifyDataSetChanged();
				
			}
			
			@Override
			public void onPoiItemSearched(PoiItem arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		};

//	/** 开始定位 */
//	private void initLocation() {
//		LocationUtil.start(context, bdLocationListener);
//		MyProgressDialog.show(context);
//	}
	/** 开始定位 */
	private void initLocation() {
		LocationUtil.start(context, aMapLocationListener);
		MyProgressDialog.show(context);
	}

	// /**
	// * 通过百度搜索地址
	// *
	// * @param string
	// */
	// protected void searchAddressFromBaidu(String string, int page) {
	// if (string != null) {
	// if (string.length() == 0) {
	// string = "小区";
	// }
	// boolean b = poiSearch.searchNearby(new PoiNearbySearchOption()
	// .location(myLL).keyword(string).pageNum(page).radius(1000));
	// LogUtil.e("isserchback:" + b);
	// }
	// }
AMapLocationListener aMapLocationListener=new AMapLocationListener() {
	
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location == null)
			return;
		longitude = location.getLongitude();
		latitude = location.getLatitude();
		address = location.getAddress();
		province = location.getProvince();
		city = location.getCity();
		district = location.getDistrict();
		myLL = new LatLng(longitude, latitude);
		SetLocationInformation(city, district, myLL);
		LocationUtil.stop();
	}
};
//BDLocationListener bdLocationListener = new BDLocationListener() {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return;
//			locType = location.getLocType();
//			longitude = location.getLongitude();
//			latitude = location.getLatitude();
//			if (location.hasRadius())
//				radius = location.getRadius();
//			if (locType == BDLocation.TypeGpsLocation) {
//
//			} else if (locType == BDLocation.TypeNetWorkLocation) {
//				address = location.getAddrStr();// 获取反地理编码(文字描述的地址)
//
//			}
//			direction = location.getDirection();
//			province = location.getProvince();
//			city = location.getCity();
//			district = location.getDistrict();
//			myLL = new LatLng(longitude, latitude);
//			LogUtil.e("latitude======>"+latitude+"----longitude===>"+longitude);
//			SetLocationInformation(city, district, myLL);
//			LocationUtil.stop();
//		}
//	};

	private void initView() {
		setHead(getResources().getString(R.string.select_position), true, true);//选择位置
		tv_address = ((TextView) findViewById(R.id.tv_address));
		rl_search = ((RelativeLayout) findViewById(R.id.rl_search));
		rl_search.setOnClickListener(lis);
		keyWorldsView = ((AutoCompleteTextView) findViewById(R.id.searchkey));
		// listData.clear();
		this.pull_to_refresh = ((PullToRefreshView) findViewById(R.id.pull_to_refresh));
		// this.pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		this.pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		// this.pull_to_refresh.setHovered(false);
		this.lv_content = ((ListView) findViewById(R.id.lv_content));
		this.lv_content.setOnItemClickListener(listener);
		adapter = new PoiSearchAdapter(context, poiList);
		lv_content.setAdapter(adapter);
		sugAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_dropdown_item_1line);
		this.keyWorldsView.setAdapter(this.sugAdapter);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			poiList.clear();
			isSearch = true;
			searchButtonProcess(v);
			// poiList.clear();
			// bdPage = 1;
			// searchText = keyWorldsView.getText().toString().trim();
			// if (!TextUtils.isEmpty(searchText)) {
			// searchAddressFromBaidu(searchText, bdPage);
			// }
		}
	};
	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent();
			intent.putExtra("address",
					((PoiItem) adapter.getItem(position)).getAdName());
			setResult(RESULT_OK, intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
//			bdPage++;
			String str2 = keyWorldsView.getText().toString().trim();
			SetLocationInformation(address, str2, myLL);
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {

		}
	};

	public void searchButtonProcess(View v) {
		String str2 = this.keyWorldsView.getText().toString().trim();
		// if (("".equals(this.address)) || (this.address == null))
		// toast("定位城市失败！请检查设置");
		if (("".equals(str2)) || (str2 == null)) {
			toast("请输入搜索条件");
			return;
		}
		MyProgressDialog.show(context);
		SetLocationInformation(this.address, str2, myLL);
	}

	StringBuilder sb;
	private Query query;

//	private void SetLocationInformation(String address, String string,
//			LatLng latLng) {
//		if (this.isFirstLoad) {
//			if (!"".equals(this.address)) {
//				this.tv_address.setText(address);
//				LatLngBounds.Builder builder = new LatLngBounds.Builder()
//				.include(latLng);
//				boolean b = poiSearch.searchInBound(new PoiBoundSearchOption()
//				.bound(builder.build()).keyword("小区").pageCapacity(50)
//				.pageNum(bdPage));
//				LogUtil.i("poiSearch===>" + b);
//				sb = new StringBuilder();
//				this.isFirstLoad = false;
//				return;
//			}
//			toast("定位失败！请检查设置");
//		} else {
//			if (!"".equals(this.address)) {
//				this.tv_address.setText(address);
//				LatLngBounds.Builder builder1 = new LatLngBounds.Builder()
//				.include(latLng);
//				boolean b = poiSearch
//						.searchInBound(new PoiBoundSearchOption()
//						.bound(builder1.build())
//						.keyword(
//								this.keyWorldsView.getText().toString()
//								.trim()).pageCapacity(50)
//								.pageNum(this.bdPage));
//				LogUtil.i("poiSearch===>" + b);
//			} else {
//				toast("定位失败！请检查设置");
//			}
//		}
//	}
	private void SetLocationInformation(String address, String string,
			LatLng latLng) {
			if (!"".equals(this.address)) {
				this.tv_address.setText(address);
				query = new PoiSearch.Query("小区", "",city);
				query.setPageSize(20);// 设置每页最多返回多少条poiitem
				query.setPageNum(0);// 设置查第一页

				if (latLng != null) {
					poiSearch = new PoiSearch(this, query);
					poiSearch.setOnPoiSearchListener(onPoiSearchListenter);
					poiSearch.setBound(new SearchBound(new LatLonPoint(latitude, longitude), 5000, true));//
					poiSearch.searchPOIAsyn();// 异步搜索
				}
				return;
			}
			toast("定位失败！请检查设置");
	}


}