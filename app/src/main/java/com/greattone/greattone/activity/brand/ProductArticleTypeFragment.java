package com.greattone.greattone.activity.brand;

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
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.adapter.ProductArticleTypeGirdAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Product;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
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
 * @author makaifeng
 *
 */
public class ProductArticleTypeFragment extends BaseFragment {
	private View rootView;
	private PullToRefreshView pull_to_refresh;
	/** 页数 */
	private int page = 1;
	private int pageSize = 30;
	private boolean isInitView;
	private String name;
	private List<Product> productList = new ArrayList<Product>();
	private GridView gv_content;
	private int classid=114;
	private String userid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		productList.clear();
		name=getArguments().getString("name");
		userid=getArguments().getString("userid");
		getProducts();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.layout_grid, container,
				false);// 关联布局文件
		initView();
		return rootView;
	}

	private void initView() {
		pull_to_refresh = (PullToRefreshView) rootView
				.findViewById(R.id.pull_to_refresh);//
		gv_content = (GridView) rootView.findViewById(R.id.gv_content);
		gv_content.setHorizontalSpacing(DisplayUtil.dip2px(context, 15));
		gv_content.setVerticalSpacing(DisplayUtil.dip2px(context, 5));
		gv_content.setNumColumns(2);
		gv_content.setPadding(DisplayUtil.dip2px(context,5), DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context,5), 0);
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
	private void getProducts() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "brand/productlist");
		map.put("ismember", "1");
		map.put("userid", userid);
		map.put("type", name);
		map.put("classid", classid+"");
		map.put("pageIndex", page+"");
		map.put("pageSize",pageSize+"");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<Product> mList = JSON.parseArray(
									message.getData(), Product.class);
							if (mList.size() == 0) {
//								toast(getResources().getString(R.string.cannot_load_more));
							}
							productList.addAll(mList);
						} else {
//							toast(getResources().getString(R.string.cannot_load_more));
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
			ProductArticleTypeGirdAdapter adapter = new ProductArticleTypeGirdAdapter(context,
					productList);
			gv_content.setAdapter(adapter);
			gv_content.onRestoreInstanceState(listState);
		}
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			Intent intent = new Intent(context,
					WebActivity.class);
		    intent.putExtra("title","产品详情");
	        intent.putExtra("urlPath", FileUtil.getProductH5Url(productList.get(position).getClassid(),productList.get(position).getId()));
			startActivity(intent);
		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getProducts();
		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			productList.clear();
			getProducts();
		}
	};
}
