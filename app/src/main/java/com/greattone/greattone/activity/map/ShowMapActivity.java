package com.greattone.greattone.activity.map;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
/**
 * 显示地图（高德地图）
 * @author Administrator
 */
public class ShowMapActivity extends BaseActivity {
	// 缩放比例
	final float ZOOMT0_20 = 19;
	final float ZOOMT0_50 = 18;
	final float ZOOMT0_100 = 17;
	final float ZOOMT0_200 = 16;
	final float ZOOMT0_500 = 15;
	// 地图相关
	MapView mMapView;
//	private RelativeLayout rl_bmapView;
	private LatLng myLL;
//	private BitmapDescriptor mCurrentMarker;
//	private PoiSearch mPoiSearch;
	private GeocodeSearch geocoderSearch;
	private AMap aMap;
	private Marker geoMarker;

//    private static final int accuracyCircleFillColor = 0xAAFFFF88;
//    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
		String city = getIntent().getStringExtra("city");
		String address = getIntent().getStringExtra("address");
		initView();

//		initBaiduMap();
		initAMap();
		initSearch();
		startSearch(city, address);
//		startSearch("上海", "动物园");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		mMapView.onDestroy();
	}
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}
	private void initView() {
		setHead(getResources().getString(R.string.map), true, true);
	    // 地图初始化
//		rl_bmapView = (RelativeLayout) findViewById(R.id.rl_bmapView);

	}

	private void startSearch(String city, String address) {
		if (city!=null&&address!=null) {
//			mPoiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(address));
////			mSearch.geocode(new GeoCodeOption().city(city).address(address));
			
			
			// name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode 
			GeocodeQuery query = new GeocodeQuery(address, city); 
			geocoderSearch.getFromLocationNameAsyn(query); 
		}
	}
	/**
	 * 初始化高德地图
	 */
	private void initAMap() {
		if (aMap == null) {
			aMap = mMapView.getMap();
			geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		}

	}
//	/**
//	 * 初始化百度地图
//	 */
//	private void initBaiduMap() {
//		// 地图
//		BaiduMapOptions options = new BaiduMapOptions();
//		MapStatus mapStatus = new MapStatus.Builder().target(
//				new LatLng(31.217189, 121.410547)).build();
//		options.zoomControlsEnabled(true);// 设置是否显示缩放控件
//		options.scrollGesturesEnabled(true);// 设置是否允许拖拽手势
//		options.scaleControlEnabled(true);// 设置是否显示比例尺控件
//		options.mapStatus(mapStatus);// 设置地图初始化时的地图状态
//		mMapView = new MapView(context, options);
//		mBaiduMap = mMapView.getMap();
//		rl_bmapView.addView(mMapView);
//
//		// 缩放地图 缩放到 50m每个标尺
//		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(ZOOMT0_50);
//		mBaiduMap.animateMapStatus(u);
////		 mBaiduMap.setOnMapTouchListener(mapTouchListener);
//		  mBaiduMap = mMapView.getMap();
//	        // 开启定位图层
//	        mBaiduMap.setMyLocationEnabled(true);
//		     // 修改为自定义marker
//	        mCurrentMarker = BitmapDescriptorFactory
//	                .fromResource(R.drawable.icon_addresson);
//	        mBaiduMap
//	                .setMyLocationConfigeration(new MyLocationConfiguration(
//	                		LocationMode.NORMAL, true, mCurrentMarker,
//	                                        accuracyCircleFillColor, accuracyCircleStrokeColor));
//	}

	private void initSearch() {
//		// 初始化搜索模块，注册搜索事件监听
//		mPoiSearch = PoiSearch.newInstance();
//		mPoiSearch.setOnGetPoiSearchResultListener(this);
//		mSuggestionSearch = SuggestionSearch.newInstance();
//		mSuggestionSearch.setOnGetSuggestionResultListener(this);
//		mSearch = GeoCoder.newInstance();
//		mSearch.setOnGetGeoCodeResultListener(listener);
		
		
		
		geocoderSearch = new GeocodeSearch(this); 
		geocoderSearch.setOnGeocodeSearchListener(onGeocodeSearchListener); 
	}

//	/**
//	 * 显示位置
//	 * 
//	 * @param poiInfo
//	 */
//	private void showMyLocation(LatLng myLL) {
//		if (myLL!=null) {
////            MapStatus.Builder builder = new MapStatus.Builder();
////            builder.target(myLL).zoom(ZOOMT0_50);
////			mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
////            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//			// 构造定位数据
//			MyLocationData locData = new MyLocationData.Builder()
//			// .accuracy(Constants.location.getRadius())
//			// 此处设置开发者获取到的方向信息，顺时针0-360
////			.direction(0)
//			.latitude(myLL.latitude)
//			.longitude(myLL.longitude).build();
//			// 设置定位数据
//			mBaiduMap.setMyLocationData(locData);
//			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(myLL);
//			mBaiduMap.animateMapStatus(u);
//	
//		}
//
//	}
	private OnGeocodeSearchListener onGeocodeSearchListener=new OnGeocodeSearchListener() {
		
		@Override
		public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
			
		}
		
		@Override
		public void onGeocodeSearched(GeocodeResult result, int rCode) {
			if (rCode == 1000) {
				if (result != null && result.getGeocodeAddressList() != null
						&& result.getGeocodeAddressList().size() > 0) {
					GeocodeAddress address = result.getGeocodeAddressList().get(0);
					myLL=new LatLng(address.getLatLonPoint().getLatitude(), address
									.getLatLonPoint().getLongitude());
					aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
							new LatLng(address
									.getLatLonPoint().getLatitude(), address
									.getLatLonPoint().getLongitude()), ZOOMT0_500));
					geoMarker.setPosition(myLL);
					String addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
							+ address.getFormatAddress();
					toast(addressName);
				} else {
					toast("无法获取坐标");
				}

			} else {
				toast("无法获取坐标");
			}
		}
	};
//	OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
//		public void onGetGeoCodeResult(GeoCodeResult result) {
//			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//				// 没有检索到结果
//				if ( result.error== SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//					toast(getResources().getString(R.string.没有检索到结果));
//				}
////				myLL=new LatLng(121.410547, 31.217189);
//				myLL=new LatLng(0, 0);
//			}else {
//				// 获取地理编码结果
//				myLL=result.getLocation();
//			}
//			showMyLocation(myLL);
//		}
//
//		@Override
//		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//				// 没有找到检索结果
//			}
//			// 获取反向地理编码结果
//		}
//	};
//	@Override
//	public void onGetSuggestionResult(SuggestionResult arg0) {
//		
//	}
//
//	@Override
//	public void onGetPoiDetailResult(PoiDetailResult arg0) {
//			
//	}
//
//	@Override
//	public void onGetPoiResult(PoiResult result) {
//	    //获取POI检索结果
//		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//			// 没有检索到结果
//			if ( result.error== SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//				toast(getResources().getString(R.string.没有检索到结果));
//			}
////			myLL=new LatLng(121.410547, 31.217189);
//			myLL=new LatLng(0, 0);
//		}else {
//			// 获取地理编码结果
//			myLL=result.getAllPoi().get(0).location;
//		}
//		showMyLocation(myLL);
//	}
}
