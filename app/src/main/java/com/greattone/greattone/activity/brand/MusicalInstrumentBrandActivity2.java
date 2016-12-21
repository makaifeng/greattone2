package com.greattone.greattone.activity.brand;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.SearchAct;
import com.greattone.greattone.adapter.BrandTypeGirdAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.OrderBy;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.widget.PullToRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 乐器品牌列表
 * @author makaifeng
 */
public class MusicalInstrumentBrandActivity2 extends BaseActivity {

	private LinearLayout ll_radiobutton1;

	private LinearLayout ll_radiobutton2;
	private PullToRefreshView pull_to_refresh;
	/** 页数 */
	private int page = 1;
	private int pageSize = 30;
	private int sear = 1;
	private String name;
	private String username;
	private List<UserInfo> musicBrandList = new ArrayList<UserInfo>();
	private GridView gv_content;
	String orderby;
	String fillter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musical_instrument_brand2);
		try {
			initView();
			getMusicBrands();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 加载视图
	 */
	private void initView() {
		setHead("乐器品牌", true, true);
		//搜索
		((BaseActivity)context).setOtherText(getResources().getString(R.string.search), new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(context,SearchAct.class);
				 startActivityForResult(intent, 1);
			}
		});
		ll_radiobutton1 = (LinearLayout) findViewById(R.id.ll_radiobutton1);// 位置筛选
		ll_radiobutton1.setOnClickListener(lis);
		ll_radiobutton2= (LinearLayout) findViewById(R.id.ll_radiobutton2);// 排序方式
		ll_radiobutton2.setOnClickListener(lis);
		pull_to_refresh = (PullToRefreshView) 				findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) findViewById(R.id.gv_content);// 正文
		gv_content.setHorizontalSpacing(DisplayUtil.dip2px(context, 15));
		gv_content.setVerticalSpacing(DisplayUtil.dip2px(context, 5));
		gv_content.setNumColumns(2);
		gv_content.setPadding(DisplayUtil.dip2px(context, 5), DisplayUtil.dip2px(context, 10),
				DisplayUtil.dip2px(context, 5), 0);
		gv_content.setClipToPadding(false);
		gv_content.setOnItemClickListener(itemClickListener);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		initContentAdapter();
	}
	PullToRefreshView.OnFooterRefreshListener footerRefreshListener = new PullToRefreshView.OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getMusicBrands();
		}
	};
	PullToRefreshView.OnHeaderRefreshListener headerRefreshListener = new PullToRefreshView.OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			musicBrandList.clear();
			getMusicBrands();
		}
	};
	AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
			Intent intent = new Intent(context, BrandDetailActivity.class);
			intent.putExtra("userid", musicBrandList.get(position).getUserid());
			startActivity(intent);
		}
	};
	private OnClickListener lis=new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				if (v==ll_radiobutton1) {// 筛选
         		   fillter();
                }else if (v==ll_radiobutton2){// 排序方式
                    sort();
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	/** 筛选 */
	protected void fillter() {
		NormalPopuWindow popuWindow = new NormalPopuWindow(context,  Data.filter_pinpai.getClassname(),
				ll_radiobutton1);
		popuWindow.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				name = text;
				page=1;
				musicBrandList.clear();
				getMusicBrands();
			}
		});
		popuWindow.show();
	}
	/** 排序 */
	protected void sort() {
		List<String> list = new ArrayList<String>();
		for (OrderBy orderBy : Data.filter_pinpai.getOrderby()) {
			list.add(orderBy.getName());
		}
		NormalPopuWindow popuWindow = new NormalPopuWindow(context, list,
				ll_radiobutton2);
		popuWindow.setOnItemClickBack(new NormalPopuWindow.OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				orderby = Data.filter_pinpai.getOrderby().get(position)
						.getField();
				page=1;
				musicBrandList.clear();
				getMusicBrands();
			}
		});
		popuWindow.show();
	}
	/**
	 * 获取乐器品牌列表
	 */
	private void getMusicBrands() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "search/musicalInstrumentBrand");
		map.put("sear", sear + "");
		map.put("type", name);
		map.put("orderby", orderby);
		map.put("username", username);
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map, new HttpUtil.ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (!message.getData().isEmpty()) {
					List<UserInfo> mList = JSON.parseArray(message.getData(), UserInfo.class);
					if (mList.size() == 0) {
						toast(getResources().getString(R.string.cannot_load_more));
					}
					musicBrandList.addAll(mList);
				} else {
					toast(getResources().getString(R.string.cannot_load_more));
				}
				pull_to_refresh.onHeaderRefreshComplete();
				pull_to_refresh.onFooterRefreshComplete();
					initContentAdapter();
				MyProgressDialog.Cancel();
			}
		}, null));
	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
			Parcelable listState = gv_content.onSaveInstanceState();
			BrandTypeGirdAdapter adapter = new BrandTypeGirdAdapter(context, musicBrandList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
	}
}
