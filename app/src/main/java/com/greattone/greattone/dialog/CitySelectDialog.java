package com.greattone.greattone.dialog;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.util.CityUtil;
import com.greattone.greattone.wheel.ArrayWheelViewAdapter;
import com.greattone.greattone.wheel.OnWheelChangedListener;
import com.greattone.greattone.wheel.WheelView;

public class CitySelectDialog extends Dialog {
	private OnSelectCityListener clickSureListener;
	private View view;
	private WheelView wheelView_province;
	private WheelView wheelView_city;
	private WheelView wheelView_district;
	List<String> provinceList;
	List<String> cityList;
	List<String> districtList;
	int districtPosition = 0;
	int cityPosition = 0;
	int provincePosition = 0;
	String province;
	String city;
	String district;
	boolean isfrist=true;
	private TextView tv_sure;
	private TextView tv_cancle;

	public CitySelectDialog(Context context, String province, String city,
			String district) {
		super(context, R.style.ActionSheetDialogStyle1);
		this.province = province;
		this.city = city;
		this.district = district;
	}
	public CitySelectDialog(Context context) {
		super(context, R.style.ActionSheetDialogStyle1);
	}

	@SuppressLint("InflateParams")
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		this.view = getLayoutInflater().inflate(R.layout.city_select, null);
		wheelView_province = (WheelView) view
				.findViewById(R.id.wheelView_province);
		wheelView_city = (WheelView) view.findViewById(R.id.wheelView_city);
		wheelView_district = (WheelView) view
				.findViewById(R.id.wheelView_district);
		tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
		tv_sure.setOnClickListener(lis);
		tv_cancle.setOnClickListener(lis);

		setContentView(view);
		// 省
		provinceList = CityUtil.getProvince();
		wheelView_province.setViewAdapter(new ArrayWheelViewAdapter(
				getContext(), provinceList));
		if (province != null) {
			for (int i = 0; i < provinceList.size(); i++) {
				if (provinceList.get(i).equals(province)) {
					wheelView_district.setCurrentItem(i);
				}
			}
		}
		wheelView_province.addChangingListener(onWheelChangedListener);
		wheelView_city.addChangingListener(onWheelChangedListener);
		wheelView_district.addChangingListener(onWheelChangedListener);
		// 市
		updateCities(isfrist);
		// 区
		updateAreas(isfrist);
		isfrist=false;
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas(boolean isFrist) {
		int pCurrent = wheelView_city.getCurrentItem();
		city = cityList.get(pCurrent);
		districtList = CityUtil.getDistrict(province,city);
//		districtList.remove("默认");
		district = districtList.get(0);
		if (districtList == null) {
			districtList = new ArrayList<String>();
		}
		wheelView_district.setViewAdapter(new ArrayWheelViewAdapter(
				getContext(), districtList));
		if (isFrist) {
			if (district != null) {
				for (int i = 0; i < districtList.size(); i++) {
					if (districtList.get(i).equals(district)) {
						wheelView_district.setCurrentItem(i);
					}
				}
			}
		}
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities(boolean isFrist) {
		int pCurrent = wheelView_province.getCurrentItem();
		province = provinceList.get(pCurrent);
		cityList = CityUtil.getCity(provinceList.get(pCurrent));
//		cityList.remove("默认");
		if (cityList == null) {
			cityList = new ArrayList<String>();
		}
		wheelView_city.setViewAdapter(new ArrayWheelViewAdapter(getContext(),
				cityList));
		if (isFrist) {
			if (city != null) {
				for (int i = 0; i < cityList.size(); i++) {
					if (cityList.get(i).equals(city)) {
						wheelView_district.setCurrentItem(i);
					}
				}
			}
		}
//		int index;
//		if (province != null) {
//			if ((index = provinceList.indexOf(province)) != -1) {
//				wheelView_province.setCurrentItem(index);
//			}
//		}
		// updateAreas();

	}

	@Override
	public void onWindowAttributesChanged(LayoutParams params) {
		params.gravity = Gravity.BOTTOM;
		params.width = LayoutParams.MATCH_PARENT;
		super.onWindowAttributesChanged(params);
	}

	OnWheelChangedListener onWheelChangedListener = new OnWheelChangedListener() {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			switch (wheel.getId()) {
			case R.id.wheelView_province:
				wheelView_city.setCurrentItem(0);
				updateCities(isfrist);
				updateAreas(isfrist);
				break;
			case R.id.wheelView_city:
				wheelView_district.setCurrentItem(0);
				updateAreas(isfrist);
				break;
			case R.id.wheelView_district:
				districtPosition = newValue;
				district = districtList.get(newValue);
				break;

			default:
				break;
			}
		}
	};
	// OnWheelViewListener onWheelViewListener = new OnWheelViewListener() {
	//
	// @Override
	// public void onSelected(View v, int selectedIndex, String item) {
	// switch (v.getId()) {
	// case R.id.wheelView_province:
	// province = item;
	// provincePosition = selectedIndex;
	// cityList = CityUtil.getCity(provinceList.get(provincePosition));
	// districtList = CityUtil.getDistrict(
	// provinceList.get(provincePosition),
	// cityList.get(cityPosition));
	// wheelView_city.setItems(cityList);
	// wheelView_district.setItems(districtList);
	// break;
	// case R.id.wheelView_city:
	// city = item;
	// cityPosition = selectedIndex;
	// districtList = CityUtil.getDistrict(
	// provinceList.get(provincePosition),
	// cityList.get(cityPosition));
	// wheelView_district.setItems(districtList);
	// break;
	// case R.id.wheelView_district:
	// district = item;
	// districtPosition = selectedIndex;
	// break;
	//
	// default:
	// break;
	// }
	// }
	// };

	View.OnClickListener lis = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_sure:
				if (clickSureListener != null) {
					clickSureListener.ClickSure(province, city, district);
				}
				break;

			default:
				break;
			}
			cancel();
		}
	};


	public void setonClickSureListener(OnSelectCityListener clickSureListener) {
		this.clickSureListener = clickSureListener;
	}
}
