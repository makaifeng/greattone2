package com.greattone.greattone.activity.brand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.BrandTypeGirdAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 乐器品牌列表
 * 
 * @author makaifeng
 *
 */
public class BrandTypeFragment extends BaseFragment {
	private View rootView;
	private PullToRefreshView pull_to_refresh;
	/** 页数 */
	private int page = 1;
	private int pageSize = 30;
	private boolean isInitView;
	private int sear = 1;
	private String name;
	private String username;
	private List<UserInfo> musicBrandList = new ArrayList<UserInfo>();
	private GridView gv_content;
	public void setName(String name) {
		 this.name = name;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		musicBrandList.clear();
		getMusicBrands();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.layout_grid, container, false);// 关联布局文件
		initView();
		return rootView;
	}

	private void initView() {
		pull_to_refresh = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);
		gv_content.setHorizontalSpacing(DisplayUtil.dip2px(context, 15));
		gv_content.setVerticalSpacing(DisplayUtil.dip2px(context, 5));
		gv_content.setNumColumns(2);
		gv_content.setPadding(DisplayUtil.dip2px(context, 5), DisplayUtil.dip2px(context, 10),
				DisplayUtil.dip2px(context, 5), 0);
		gv_content.setClipToPadding(false);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
		gv_content.setOnItemClickListener(itemClickListener);
		isInitView = true;
		initContentAdapter();
	}

	/**
	 * 获取乐器品牌列表
	 */
	private void getMusicBrands() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "search/musicalInstrumentBrand");
		map.put("sear", sear + "");
		map.put("type", name);
		map.put("username", username);
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map, new ResponseListener() {

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
				if (isInitView) {
					initContentAdapter();
				}
				MyProgressDialog.Cancel();
			}
		}, null));
	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		if (isInitView) {
			Parcelable listState = gv_content.onSaveInstanceState();
			BrandTypeGirdAdapter adapter = new BrandTypeGirdAdapter(context, musicBrandList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
		}
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
			Intent intent = new Intent(context, BrandDetailActivity.class);
			intent.putExtra("userid", musicBrandList.get(position).getUserid());
			// intent.putExtra("classid", competitionList.get(position)
			// .getClassid());// 1活动 2赛事
			// intent.putExtra("history", history);// 1活动 2赛事
			// intent.putExtra("type", 2);// 1活动 2赛事
			startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getMusicBrands();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			musicBrandList.clear();
			getMusicBrands();
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 1) {
			username = data.getStringExtra("data");
			musicBrandList.clear();
			page = 1;
			getMusicBrands();
		}
	}
}
