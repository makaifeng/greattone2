package com.greattone.greattone.activity.classroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.MySwipeMenuCreator;
import com.greattone.greattone.SwipeMenu.SwipeMenu;
import com.greattone.greattone.SwipeMenu.SwipeMenuCreator;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView.OnMenuItemClickListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.post.PostNoticeActivity;
import com.greattone.greattone.adapter.Adapter;
import com.greattone.greattone.adapter.ViewHolder;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Notice;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class NoticeActivity extends BaseActivity {
	private SwipeMenuListView lv_content;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
		getData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.活动公告), true, true);//我的动态
		
		setOtherText("发布", lis);
		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);
		adapter = new MyAdapter(context, noticelist, R.layout.adapter_notice);
		lv_content.setAdapter(adapter);
		lv_content.setOnItemClickListener(listener);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		SwipeMenuCreator creator = new MySwipeMenuCreator(context);
		// set creator
		lv_content.setMenuCreator(creator);
		lv_content.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				 switch (index) {
			        case 0://编辑
			        	edit(position);
			            break;
			        case 1://删除
			        	delect(position);
			        	break;
			        }
				return true;
			}
		});
	}

	protected void edit(int position) {
		Intent		intent = new Intent(context, PostNoticeActivity.class);
		intent.putExtra("notice", noticelist.get(position));
		intent.setType("edit");
		startActivityForResult(intent, 1);
	}

	protected void delect(final int position) {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MDelInfo");
		map.put("classid", noticelist.get(position).getClassid() + "");
		map.put("id", noticelist.get(position).getId() + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		((BaseActivity) context).addRequest(HttpUtil
				.httpConnectionByPost(context, map,
						new ResponseListener() {

							@Override
							public void setResponseHandle(
									Message2 message) {
								((BaseActivity) context)
										.toast(message.getInfo());
								noticelist.remove(position);
								initViewData();
								MyProgressDialog.Cancel();
							}

						}, null));
	}

	List<Notice> noticelist = new ArrayList<Notice>();

	private void getData() {
		MyProgressDialog.show(context);
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("api", "jiaoshi/activity");
		map.put("userid", getIntent().getStringExtra("userid"));
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							noticelist = JSON.parseArray(message.getData(),
									Notice.class);
							initViewData();
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	protected void initViewData() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.setList(noticelist);
		lv_content.onRestoreInstanceState(listState);
	}

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		    Intent intent = new Intent(context, WebActivity.class);
		    intent.putExtra("title",noticelist.get(position).getTitle());
	        intent.putExtra("urlPath", FileUtil.getNoticeH5Url(noticelist.get(position).getClassid()+"", noticelist.get(position).getId()+""));
			startActivity(intent);
		}
	};
//	private OnItemLongClickListener onItemLongClickListener=new OnItemLongClickListener() {
//
//		@Override
//		public boolean onItemLongClick(AdapterView<?> arg0, View view,
//				final int position, long arg3) {
//	   MyIosDialog.showButtons(context, "删除",new String[]{"编辑"}, new OnAlertViewClickListener(){
//
//		@Override
//		public void confirm(String result) {
//			edit(position);
//		}
//
//		@Override
//		public void cancel() {
//			delect(position);
//		}
//		   
//	   });
//	return true;
//	}
//	};

	private OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_other:
		Intent		intent = new Intent(context, PostNoticeActivity.class);
				startActivityForResult(intent, 1);
				break;

			default:
				break;
			}
		}
	};
	class MyAdapter extends Adapter<Notice> {

		public MyAdapter(Context context, List<Notice> list, int resId) {
			super(context, list, resId);
		}

		@Override
		public void getview(ViewHolder holder, int position, Notice notice) {
			TextView tv_title = (TextView) holder.getView(R.id.tv_title);
			TextView tv_time = (TextView) holder.getView(R.id.tv_time);
			tv_time.setText(notice.getNewstime());
			tv_title.setText(notice.getTitle());
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0==1&&arg1==RESULT_OK) {
			noticelist.clear();
			getData();
		}
	}
}
