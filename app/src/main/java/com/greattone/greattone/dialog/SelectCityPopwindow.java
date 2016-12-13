package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.greattone.greattone.Listener.OnSelectCityListener;
import com.greattone.greattone.R;
import com.greattone.greattone.util.CityUtil;
import com.greattone.greattone.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 提示框
 */
public class SelectCityPopwindow {
	private static SelectCityPopwindow myHintPopupWindow;
	private static PopupWindow mPopupWindow;
	private Context context;
	private View popupView;
String province;
String city;
	List<String> provinceList=new ArrayList<String>();
	List<String> cityList=new ArrayList<String>();
	private ListView listView;
	private ListView subListView;
	private MyAdapter myAdapter;
	protected SubAdapter subAdapter;

	OnSelectCityListener onSelectCityListener;
	private SelectCityPopwindow(Context context) {
		this.context = context;
		init();
	}

	/**
	 * 单例构建
	 * 
	 * @param context
	 * @return
	 */
	public static SelectCityPopwindow build(Context context) {
		myHintPopupWindow = new SelectCityPopwindow(context);
		return myHintPopupWindow;
	}

	/*
	 * 选择城市
	 */
	public SelectCityPopwindow setOnSelectCityListener(OnSelectCityListener onSelectCityListener) {
		this.onSelectCityListener=onSelectCityListener;
		return myHintPopupWindow;
	}
	/*
	 * 显示界面
	 */
	public void show(View view) {
		mPopupWindow.showAsDropDown(view);
	}

	/**
	 * 
	 */
	@SuppressLint("InflateParams")
	void init() {
		popupView = LayoutInflater.from(context).inflate(
				R.layout.popupwondow_select_city, null);
		// 创建一个PopuWidow对象
		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				DisplayUtil.getScreenHight(context) / 2, true);
		mPopupWindow.setTouchable(true);
		// 设置允许在外点击消失
		mPopupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(130,
				255, 255, 255)));
		// mPopupWindow.getBackground().setAlpha(153);

		listView = (ListView) popupView.findViewById(R.id.listView);
		subListView = (ListView) popupView.findViewById(R.id.subListView);

		provinceList.add("全部");
		provinceList.addAll(CityUtil.getProvince());
		cityList.add("全部");
//		cityList = CityUtil.getCity(provinceList.get(0));
//		cityList.remove("默认");

		myAdapter = new MyAdapter(context, provinceList);
		listView.setAdapter(myAdapter);

		subAdapter = new SubAdapter(context, cityList);
		subListView.setAdapter(subAdapter);

		myAdapter.setSelectedPosition(0);
		myAdapter.notifyDataSetInvalidated();
		province=provinceList.get(0);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				province=provinceList.get(position);
				if (province.equals("全部")) {
					cityList=new ArrayList<String>();
					cityList.add("全部");
				}else {
					cityList = CityUtil.getCity(provinceList.get(position));
					cityList.remove("默认");
				}
				subAdapter = new SubAdapter(context, cityList);
				subListView.setAdapter(subAdapter);
				myAdapter.setSelectedPosition(position);
				myAdapter.notifyDataSetInvalidated();
			}
		});
		subListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				 city=cityList.get(position);
				 if (province.equals("全部")) {
					 province="";
				 }
				 if (city.equals("默认")) {
					 city="";
				}else  if (city.equals("全部")) {
					 city="";
				 }
				if (onSelectCityListener!=null) {
					onSelectCityListener.ClickSure(province, city, null);
					mPopupWindow.dismiss();
				}
			}
		});
	}

	/**
	 * 关闭弹框
	 */
	public static void cancel() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}

	public class MyAdapter extends BaseAdapter {

		Context context;
		LayoutInflater inflater;
		List<String> provinceList;
		int last_item;
		private int selectedPosition = -1;

		public MyAdapter(Context context, List<String> provinceList) {
			this.context = context;
			this.provinceList = provinceList;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return provinceList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.popupwondow_select_city_mylist_item, parent,false);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.textview);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.imageview);
				holder.layout = (LinearLayout) convertView
						.findViewById(R.id.colorlayout);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 设置选中效果
			if (selectedPosition == position) {
//				holder.textView.setTextColor(Color.BLUE);
				holder.layout.setBackgroundColor(Color.GRAY);
			} else {
//				holder.textView.setTextColor(Color.BLACK);
				holder.layout.setBackgroundColor(Color.TRANSPARENT);
			}

			holder.textView.setText(provinceList.get(position));
			// holder.textView.setTextColor(Color.BLACK);
			// holder.imageView.setBackgroundResource(images[position]);

			return convertView;
		}

		public class ViewHolder {
			public TextView textView;
			public ImageView imageView;
			public LinearLayout layout;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

	}

	public class SubAdapter extends BaseAdapter {

		Context context;
		LayoutInflater layoutInflater;
		List<String> cityList;

		public SubAdapter(Context context, List<String> cityList) {
			this.cityList = cityList;
			this.context = context;
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return cityList.size();
		}

		@Override
		public Object getItem(int position) {
			return getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.popupwondow_select_city_sublist_item, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.textview1);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.textView.setText(cityList.get(position));
			viewHolder.textView.setTextColor(Color.BLACK);

			return convertView;
		}

		public class ViewHolder {
			public TextView textView;
		}

	}

}
