package com.greattone.greattone.activity.personal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.AddNewFriendAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnFooterRefreshListener;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;
/**
 * 添加朋友
 * @author yff
 * 2015-9-2
 */
public class AddNewFriendActivity extends BaseActivity implements OnClickListener, OnFooterRefreshListener, OnHeaderRefreshListener {

//	private LinearLayout ll_phoneman,ll_sys;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private TextView mTextView ;
	private ImageView mImageView;
	private EditText et_search;
	int page = 1;
	
	PullToRefreshView pull;
	ListView lv_content;
	AddNewFriendAdapter adapter;
//	List<AddNewFreindObj> list = new  ArrayList<AddNewFreindObj>();
	List<UserInfo> userlist=new ArrayList<UserInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_friend);
		
		initView();
	}
	
	private void initView() {
		setHead(getResources().getString(R.string.添加朋友), true, true);
//		ll_phoneman=(LinearLayout) findViewById(R.id.ll_phoneman);
//		ll_sys=(LinearLayout) findViewById(R.id.ll_sys);
		mTextView = (TextView) findViewById(R.id.result); 
		mImageView=(ImageView) findViewById(R.id.qrcode_bitmap);
		
//		ll_phoneman.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				inintent(AddNewFriendActivity.this, PhoneManActivity.class);
//			}
//		});
//		ll_sys.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				 
//				//inintent(AddNewFriendActivity.this, );
//				Intent intent = new Intent();
//				intent.setClass(AddNewFriendActivity.this, MipcaActivityCapture.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
//			}
//		});
		
		pull = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
		lv_content = (ListView) findViewById(R.id.lv_content);
//		sv_friend = (ScrollView) findViewById(R.id.sv_add_friend);
		et_search = (EditText) findViewById(R.id.et_friend_search);

		adapter = new AddNewFriendAdapter(AddNewFriendActivity.this, userlist);
		lv_content.setAdapter(adapter);
		pull.setOnHeaderRefreshListener(this);
		pull.setOnFooterRefreshListener(this);
		findViewById(R.id.ll_search).setOnClickListener(this);
		et_search.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				
				if(actionId == EditorInfo.IME_ACTION_SEARCH){
					getFreiend();
				}
				return false;
			}
		});
		
//		add.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				sv_friend.setVisibility(View.VISIBLE);
//				pull.setVisibility(View.GONE);
//				list.clear();
//				adapter.notifyDataSetChanged();
//			}
//		});
		
	}

	
	//获取好友列表
	private void getFreiend(){
		String search = et_search.getText().toString().trim();
		if(TextUtils.isEmpty(search)){
			toast(getResources().getString(R.string.搜索内容不能为空));
			return;
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "search/member");
		map.put("keyboard", search);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData().startsWith("[")) {
							List<UserInfo>mlist	 = JSON.parseArray(message.getData(),
								UserInfo.class);
							if (mlist.size()==0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							userlist.addAll(mlist);
						}
						pull.onHeaderRefreshComplete();
						pull.onFooterRefreshComplete();
						initContentAdapter();
						MyProgressDialog.Cancel();
					}

				}, null));
//       AndroidUtil.hideMethod(this);
//		
//		baseMap.clear();
//		baseMap.put("uid", getUserId());
//		baseMap.put("token",getToken());
//		baseMap.put("key", search);
//		baseMap.put("page", page+"");
////		baseMap.put("pageSize", );这个不传,显示默认的个数20
//		
//		HttpSender sender = new HttpSender(uurl.addfriend,"添加好友",baseMap,new MyOnHttpResListener() {
//			
//			@Override
//			public void doSuccess(String data, String name, String info, int status) {
//				super.doSuccess(pull);
//				
//				HttpBaseList<AddNewFreindObj> temp=(HttpBaseList<AddNewFreindObj>) gsonUtil.getInstance().json2List(data, new TypeToken<HttpBaseList<AddNewFreindObj>>(){}.getType());
//				if(temp == null || temp.getList() == null || temp.getList().size() == 0){
//					toast(page == 1 ? "暂无数据" : "已无更多数据");
//					if(page == 1){
//						adapter.notifyDataSetChanged();
//					}
//				}else{
//					sv_friend.setVisibility(View.GONE);
//					pull.setVisibility(View.VISIBLE);
//					initTitleText("", "取消");
//					page ++;
//					list.addAll(temp.getList());
//					adapter.notifyDataSetChanged();
//				}
//			}
//		});
//		sender.setContext(page == 1 ? this : null);
//		sender.send();
	}
	
	
	protected void initContentAdapter() {
		Parcelable listState = lv_content.onSaveInstanceState();
		adapter.setList(userlist);
		lv_content.onRestoreInstanceState(listState);
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				mTextView.setText(bundle.getString("result"));
				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
    }

	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.ll_search){
			userlist.clear();
			getFreiend();
		}
		
	}


	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		page = 1;
//		list.clear();
		getFreiend();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		getFreiend();
		
	}	

}
