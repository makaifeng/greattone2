package com.greattone.greattone.activity.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseFragment;
import com.greattone.greattone.adapter.MyMessageListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Chat;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.SQLManager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 我的消息
 */
public class MyMessageFragment extends BaseFragment {
	/**
	 * fragment 主布局
	 */
	private View rootView;
	// /**
	// * 屏幕宽度
	// */
	// private int screenWidth;

	/**
	 * 正文内容
	 */
	private ListView lv_content;

	List<Chat> messageList = new ArrayList<Chat>();
private int pageSize=200;
private int page=1;
private boolean isInitView;
private List<Chat> sqlList=new ArrayList<Chat>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getDB();
		getMyMessage();
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.layout_list, container, false);// 关联布局文件
		// screenWidth = ((BaseActivity) getActivity()).screenWidth;
		initView();
		return rootView;
	}

	/**
	 * 加载视图
	 */
	private void initView() {
		lv_content = (ListView) rootView.findViewById(R.id.lv_content);// 正文
		lv_content.setOnItemClickListener(listener);
//		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		isInitView=true;
	}


	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View v, int position,
				long arg3) {
			SQLManager sql=SQLManager.bulid(context);
			ContentValues 		values=new ContentValues();
			values.put("no_read_num", 0);
			sql.update(context, values, messageList.get(position).getFrom_username());
			sqlList=	sql.queryChats(context);
			sql.closeDB();
			initContentAdapter();
			Intent intent = new Intent(context, MyChatActivity.class);
			intent.putExtra("name", messageList.get(position).getFrom_username());
//			intent.putExtra("id", messageList.get(position).getFuid());
			intent.putExtra("image", messageList.get(position).getUserpic());
			startActivity(intent);
		}
	};

	/**
	 * 获取sql数据
	 */
	private void getDB() {
		SQLManager sql=SQLManager.bulid(context);
		sqlList=	sql.queryChats(context);
		sql.closeDB();
	}
	/**
	 * 获取聊天数据
	 */
	private void getMyMessage() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "message/getChatList");
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData()!=null&&message.getData().startsWith("[")) {
							List<Chat> mList = JSON.parseArray(
									message.getData(), Chat.class);
							if (mList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							} else {
								messageList.addAll(mList);
							}
						} else {
							toast(getResources().getString(R.string.cannot_load_more));
						}
						initContentAdapter();
						MyProgressDialog.Cancel();
					}

				}, null));
	}

	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
//		if (messageList.size() > 0) {
			if (isInitView) {
				Parcelable listState = lv_content.onSaveInstanceState();
				MyMessageListAdapter adapter = new MyMessageListAdapter(
						getActivity(), messageList,sqlList);
				lv_content.setAdapter(adapter);
					lv_content.onRestoreInstanceState(listState);
				}

	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
