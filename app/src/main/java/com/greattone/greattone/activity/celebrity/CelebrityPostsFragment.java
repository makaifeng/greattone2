package com.greattone.greattone.activity.celebrity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.plaza.PlazaMusicDetailsActivity;
import com.greattone.greattone.adapter.CelebrityPostsListAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyListView;

public class CelebrityPostsFragment extends BaseFragment {
	private List<Blog> blogList = new ArrayList<Blog>();
	private List<Blog> blogList2 = new ArrayList<Blog>();
	ArrayList<String> list;
	private View rootView;
	private View ll_sxx;
	private MyListView lv_comments;
	private CelebrityPostsListAdapter adapter;
	protected List<ImageData> imageUrlList;
	private PopupWindow popupWindow;
	private int classid=ClassId.音乐广场_ID ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_celebrity_posts,
				container, false);// 关联布局文件
		initView();
		return rootView;
	}

	private void initView() {
		ll_sxx = rootView.findViewById(R.id.ll_sxx);
		lv_comments = (MyListView) rootView.findViewById(R.id.lv_comments);
		this.adapter = new CelebrityPostsListAdapter(context, blogList2,imageUrlList,0);
		this.lv_comments.setAdapter(this.adapter);
		
		ll_sxx.setOnClickListener(lis);
		lv_comments.setOnItemClickListener(listener);
		initPopupWindow();
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(context, PlazaMusicDetailsActivity.class);
			intent.putExtra("id", blogList.get(position).getId());
			intent.putExtra("classid", blogList.get(position).getClassid());
			startActivity(intent);
		}
	};

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
			} else {
				popupWindow.showAsDropDown((View) v.getParent(),
						(int) (v.getLeft()), 0);
			}
		}
	};

	/** 获取名人的发帖 */
	private void getData() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", classid + "");
		map.put("userid", getArguments().getString("id"));
		map.put("ismember", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
						blogList = JSON.parseArray(message.getData(),
								Blog.class);
						blogList2.addAll(blogList);
						adapter.notifyDataSetChanged();
						}
						// MyProgressDialog.Cancel(num, 2);
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	private void initPopupWindow() {
		ListView listView = new ListView(context);
		listView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		list=new ArrayList<String>();
		
		String types[] = getResources().getStringArray(R.array.posts_type);
		for (String string : types) {
			list.add(string);
		}
		listView.setAdapter(new MyAdapter(list));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				blogList2.clear();
			if (position==1) {
				classid=11;
			}else if (position==2) {
				classid=12;
			}else if (position==3) {
				classid=13;
			}else {
				classid=1;
			}
				getData();
				popupWindow.dismiss();
			}
		});
		popupWindow = new PopupWindow(listView,
				DisplayUtil.dip2px(context, 50), LayoutParams.WRAP_CONTENT,
				true);
		popupWindow.setTouchable(true);
		this.popupWindow.setBackgroundDrawable(new ColorDrawable(
				android.R.color.transparent));
		this.popupWindow.setOutsideTouchable(true);
	}

	class MyAdapter extends BaseAdapter {
		ArrayList<String> list;

		public MyAdapter(ArrayList<String> list) {
			super();
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(context);
			textView.setLayoutParams(new AbsListView.LayoutParams(
					AbsListView.LayoutParams.WRAP_CONTENT,
					AbsListView.LayoutParams.WRAP_CONTENT));
			textView.setBackgroundColor(getResources().getColor(R.color.white));
			textView.setText(list.get(position));
			textView.setPadding(DisplayUtil.dip2px(context, 10),
					DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 10),
					DisplayUtil.dip2px(context, 5));
			return textView;
		}

	}
}
