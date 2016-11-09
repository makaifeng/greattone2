package com.greattone.greattone.activity.yuepu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.SearchAct;
import com.greattone.greattone.adapter.YuePuAdapter;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Column;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Yuepu;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
/**
 * 乐谱中心列表
 * @author makaifeng
 *
 */
public class YuePuAct extends BaseActivity {
	List<Yuepu> yplist = new ArrayList<Yuepu>();
	List<String> typeList  = new ArrayList<String>();
	List<Column> classList  = new ArrayList<Column>();
	private RadioGroup rg_group;
	private TextView tv_name;
	private PullToRefreshView pull_to_refresh;
	private ListView lv_content;
	private int page = 1;
	private YuePuAdapter adapter;
	int classid = ClassId.音乐乐谱_ID;
	private String keyboard;
	private String type;
	private TextView tv_type;
	int num=0,all=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yue_pu);
//		yuepu_types = getResources().getStringArray(R.array.yuepu_type);
//		yuepu_classids = getResources().getIntArray(R.array.yuepu_classids);
		initView();
		getData();
		getType();
	}


	private void initView() {
		setHead("乐谱中心", true, true);
		setOtherText("搜索", lis);
		rg_group = (RadioGroup) findViewById(R.id.rg_group);
		findViewById(R.id.ll_btn1).setOnClickListener(lis);
		findViewById(R.id.ll_btn2).setOnClickListener(lis);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_type = (TextView) findViewById(R.id.tv_type);
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
		lv_content = (ListView) findViewById(R.id.lv_content);
		adapter = new YuePuAdapter(context, yplist);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(listener);
		pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);
		pull_to_refresh.setOnFooterRefreshListener(footerRefreshListener);
	}

	private void getType() {
		HttpProxyUtil.getYuepuType(context, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null && message.getData().startsWith("{")) {
					 typeList = JSON.parseArray( JSON.parseObject(message.getData()).getString("type"), String.class);
					 classList = JSON.parseArray(JSON.parseObject(message.getData()).getString("class"), Column.class);
				}
				num++;
				MyProgressDialog.Cancel(num,all);
			}}, null);
	}
	private void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "yuepu/list");
		map.put("classid", classid + "");
		map.put("keyboard", keyboard);
		map.put("type", type);
		map.put("pageIndex", page + "");
		addRequest(HttpUtil.httpConnectionByPost(context, map, new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData() != null && message.getData().startsWith("[")) {
					List<Yuepu> mList = JSON.parseArray(message.getData(), Yuepu.class);
					if (mList != null && mList.size() > 0) {
						yplist.addAll(mList);
						initApapter();
					} else {
						toast(getResources().getString(R.string.cannot_load_more));
					}
				} else {
					toast(getResources().getString(R.string.cannot_load_more));
				}
				pull_to_refresh.onHeaderRefreshComplete();
				pull_to_refresh.onFooterRefreshComplete();
				num++;
				MyProgressDialog.Cancel(num,all);
			}
		}, null));

	}


	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			List<String> list;
			NormalPopuWindow popuWindow;
			switch (v.getId()) {
			case R.id.ll_btn1:// 乐谱类型
				list = new ArrayList<String>();
				for (Column col : classList) {
					list.add(col.getName());
				}
				popuWindow = new NormalPopuWindow(context, list, rg_group);
				popuWindow.setOnItemClickBack(new OnItemClickBack() {

					@Override
					public void OnClick(int position, String text) {
						if (tv_name.getText().equals(text)) {
							return;
						}
						tv_name.setText(text);
						classid = classList.get(position).getClassid();
						page = 1;
						yplist.clear();
						getData();
					}
				});
				popuWindow.show();
				break;
			case R.id.ll_btn2:// 风格
				popuWindow = new NormalPopuWindow(context, typeList, rg_group);
				popuWindow.setOnItemClickBack(new OnItemClickBack() {

					@Override
					public void OnClick(int position, String text) {
						if (text.equals(type)) {
							return;
						}
						type = text.equals("全部")?"":text;
						tv_type.setText(text);
						page = 1;
						yplist.clear();
						getData();
					}
				});
				popuWindow.show();
				break;
			case R.id.tv_head_other:// 搜索
				startActivityForResult(new Intent(context, SearchAct.class), 11);
				break;

			default:
				break;
			}
		}
	};
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			ArrayList<String> mlist = new ArrayList<String>();
			String s[] = yplist.get(position).getPhoto().split("\\::::::");
			for (String string : s) {
				mlist.add(string);
			}
			Intent intent = new Intent(context, YuePuDetailAct.class);
			intent.putStringArrayListExtra("uriList", mlist);
			intent.putExtra("title", yplist.get(position).getTitle());
			intent.putExtra("content", yplist.get(position).getSmalltext());
			intent.putExtra("titleurl", yplist.get(position).getTitleurl());
			intent.putExtra("id", yplist.get(position).getId());
			intent.putExtra("classid", yplist.get(position).getClassid());
			startActivity(intent);
		}
	};
	private OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page = 1;
			yplist.clear();
			getData();
		}
	};
	private OnFooterRefreshListener footerRefreshListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			page++;
			getData();
		}
	};

	/**
	 * 加载数据
	 */
	protected void initApapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.notifyDataSetChanged();
		lv_content.onRestoreInstanceState(listState);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 11 && arg1 == RESULT_OK) {
			keyboard = arg2.getStringExtra("data");
			page = 1;
			yplist.clear();
			getData();
		}
	}
}
