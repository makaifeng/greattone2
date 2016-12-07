package com.greattone.greattone.activity.discuss;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Discuss;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.greattone.greattone.widget.MyListView;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** 论坛主页 */
public class DiscussFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	// private PullToRefreshView pull_to_refresh;
	private MyGridView gv_content;
	private MyListView lv_content;
	private List<Discuss> mList = new ArrayList<Discuss>();
	private String[] name;
	private int classids[] ;
	// private List<BBSCate> cateList = new ArrayList<BBSCate>();
	private boolean isInitView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		name = getResources().getStringArray(R.array.discussion_area_type);
		 classids=getResources().getIntArray(R.array.discussion_area_classids);   
		getCateList();
		getForumIndex();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater
				.inflate(R.layout.fragment_discuss, container, false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();

		return rootView;
	}

	private void initView() {
		// 发帖
		((BaseActivity) context).setOtherText(
				getResources().getString(R.string.post), new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (Data.myinfo.getSign() == 1) {
							Intent intent = new Intent(context,
									DiscussPostAct.class);
							startActivityForResult(intent, 1);
						} else {
							toast(context.getResources().getString(
									R.string.请先完善资料));
						}
					}
				});
		// pull_to_refresh = (PullToRefreshView) rootView
		// .findViewById(R.id.pull_to_refresh);//
		lv_content = (MyListView) rootView.findViewById(R.id.lv_content);//
		lv_content.setOnItemClickListener(listener);
		gv_content = (MyGridView) rootView.findViewById(R.id.gv_content);//
		gv_content.setOnItemClickListener(listener);
		isInitView = true;
		lv_content.setAdapter(new DiscussListAdapter2(context, mList));
		gv_content.setAdapter(new DiscussGridAdapter(context));
		// pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		// pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
	}

	/**
	 * 讨论区首页
	 */
	private void getForumIndex() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/list");
		map.put("classid", ClassId.声粉论坛_ID + "");
		map.put("query", "isgood");
		map.put("pageSize", 4 + "");
		map.put("pageIndex", 1 + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData().startsWith("[")) {
							mList = JSON.parseArray(message.getData(),
									Discuss.class);
						}
						// cateList = JSON.parseArray(
						// JSON.parseObject(message.getData()).getString(
						// "bbscate"), BBSCate.class);
						if (isInitView) {
							lv_content.setAdapter(new DiscussListAdapter2(
									context, mList));
							gv_content.setAdapter(new DiscussGridAdapter(
									context));
						}
						MyProgressDialog.Cancel();
					}
				}, null));

	}

	private void getCateList() {

	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View v,
				int position, long arg3) {
			if (adapterView == lv_content) {// 详情
				Intent intent = new Intent(context, WebActivity.class);
				intent.putExtra("urlPath", FileUtil.getNewsH5Url(
						mList.get(position).getClassid(), mList.get(position)
								.getId()));
				intent.putExtra("title",
						getResources().getString(R.string.讨论区详情));
				intent.putExtra("id", mList.get(position).getId());
				// intent.putExtra("cid", mList.get(position).getCid());
				intent.putExtra("classid", mList.get(position).getClassid());
				intent.putExtra("action", "replyPost");
				startActivity(intent);
			} else if (adapterView == gv_content) {// 列表
				Intent intent = new Intent(context, DiscussCateActivity.class);
				intent.putExtra("title", name[position]);
				intent.putExtra("id", classids[position]+"");
				startActivity(intent);
			}

		}
	};
	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {

		}
	};
	OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {

		}
	};

	public class DiscussGridAdapter extends BaseAdapter {
		private int[] colors = { R.color.discuss_first, R.color.discuss_second,
				R.color.discuss_third, R.color.discuss_fourth,
				R.color.discuss_fifth, R.color.discuss_sixth,
				R.color.discuss_seventh, R.color.discuss_eighth };

		Context context;

		public DiscussGridAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return name.length;
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
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_discuss_grid, parent, false);
			}
			TextView textview = (TextView) convertView
					.findViewById(R.id.tv_name);
			textview.setBackgroundColor(context.getResources().getColor(
					colors[position]));
			textview.setText(name[position]);
			return convertView;
		}
	}
	public class DiscussListAdapter2 extends BaseAdapter {
		private Context context;
		private List<Discuss> list;

		public DiscussListAdapter2(Context context, List<Discuss> list) {
			this.list = list;
			this.context = context;
		}

		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			return this.list.get(position);
		}

		public long getItemId(int paramInt) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup group) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView=new TextView(context);
				holder.content = ((TextView)convertView);
				holder.content.setPadding(0,DisplayUtil.dip2px(context,10),0,DisplayUtil.dip2px(context,10));
				holder.content.setBackgroundColor(getResources().getColor(R.color.white));
				holder.content.setTextSize(15);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.setPosition(position);
			return convertView;
		}

		private class ViewHolder {
			private TextView content;
			private  void setPosition(int position ){
				content.setText(list.get(position).getTitle());
			}
		}
	}
}
