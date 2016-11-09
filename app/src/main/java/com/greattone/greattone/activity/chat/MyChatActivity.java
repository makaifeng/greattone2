package com.greattone.greattone.activity.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.adapter.ChatListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.data.PullStatus;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Chat;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyEmojiView;
import com.greattone.greattone.widget.MyEmojiView.onExpressionItemClicklistener;
import com.greattone.greattone.widget.PullToRefreshView;
import com.greattone.greattone.widget.PullToRefreshView.OnHeaderRefreshListener;

/** 聊天界面 */
public class MyChatActivity extends BaseActivity {
	public static String ACTION_INTENT_RECEIVER = "com.greattone.greattone.receiver";  
	private List<Chat> chatList = new ArrayList<Chat>();
	private ListView lv_content;
	private String name;
//	private String image;
	private PullToRefreshView pull_to_refresh;
	private TextView tv_voice;
	private EditText et_content;
//	private TextView tv_voice_press;
	private TextView tv_chat_phiz;
	private TextView tv_chat_show;
	private TextView tv_chat_send;
	private ChatListAdapter adapter;
	private int page = 1;
	private int pageSize = 20;
	int state = PullStatus.NO_PULL;
	private MyEmojiView myEmojiView;
	public static boolean isForeground = false;
	int type = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_chat);
		name = getIntent().getStringExtra("name");
//		image = getIntent().getStringExtra("image");
		initView();
		getChatData(true);
		registerMessageReceiver();
	}

	private void initView() {
		setHead(name, true, true);

		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		lv_content = (ListView) findViewById(R.id.lv_content);
		// pull_to_refresh.setOnHeaderRefreshListener(headerRefreshListener);

		tv_voice = (TextView) findViewById(R.id.tv_voice);
		tv_voice.setOnClickListener(lis);
		et_content = (EditText) findViewById(R.id.et_content);
		et_content.addTextChangedListener(watcher);
//		tv_voice_press = (TextView) findViewById(R.id.tv_voice_press);
		tv_chat_phiz = (TextView) findViewById(R.id.tv_chat_phiz);
		tv_chat_phiz.setOnClickListener(lis);
		tv_chat_show = (TextView) findViewById(R.id.tv_chat_show);
		tv_chat_show.setOnClickListener(lis);
		tv_chat_send = (TextView) findViewById(R.id.tv_chat_send);
		tv_chat_send.setOnClickListener(lis);
		myEmojiView = (MyEmojiView) findViewById(R.id.myEmojiView);
		myEmojiView
				.setOnExpressionItemClicklistener(expressionItemClicklistener);
		adapter = new ChatListAdapter(context, chatList);
		lv_content.setAdapter(adapter);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		lv_content.setSelection(lv_content.getBottom()); 
		//给该layout设置监听，监听其布局发生变化事件
		headlayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(){

		    @Override
		    public void onGlobalLayout(){
		 
		       //比较Activity根布局与当前布局的大小
		        int heightDiff = headlayout.getRootView().getHeight()- headlayout.getHeight();
		        if(heightDiff >100){
		        //大小超过100时，一般为显示虚拟键盘事件
		        	myEmojiView.setVisibility(View.GONE);
		             }else{
		        //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
		       }
		     }
		});
	}

	TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s.length() > 0) {
				tv_chat_send.setVisibility(View.VISIBLE);
				tv_chat_show.setVisibility(View.GONE);
			} else {
				tv_chat_show.setVisibility(View.VISIBLE);
				tv_chat_send.setVisibility(View.GONE);
			}
		}
	};

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			closeKeyBoard();
			if (v == tv_chat_phiz) {// 表情
				if (myEmojiView.getVisibility() == View.VISIBLE) {
					myEmojiView.setVisibility(View.GONE);
				} else {
					myEmojiView.setVisibility(View.VISIBLE);
				}
			} else if (v == tv_voice) {// 语音
			} else if (v == tv_chat_show) {// 图片
			} else if (v == tv_chat_send) {// 发送信息
				type = 1;
				send();
			}
		}
	};

	onExpressionItemClicklistener expressionItemClicklistener = new onExpressionItemClicklistener() {

		@Override
		public void onExpressionItemClick(String resName) {
setTextMsg(resName);
		}
	};
	// 编辑框里放表情
	private void setTextMsg(String imageName) {
		SpannableString string = FaceImageDeal.valueToString(context,
				imageName);
		if (string == null || string.length() == 0) {
			toast("=SpannableString  is null=" + string);
			return;
		}
		et_content.append(string);
	}
	/** 获取聊天记录 */
	private void getChatData(boolean isShowDialog) {
		if (isShowDialog) {
			MyProgressDialog.show(context);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "message/getChatRecord");
		map.put("to_username", name);
		map.put("pageSize", pageSize + "");
		map.put("pageIndex", page + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							List<Chat> mList = JSON.parseArray(
									message.getData(), Chat.class);
//							Collections.reverse(chatList);
							if (mList != null) {
								chatList.addAll(mList);
//								Collections.reverse(chatList);
								pull_to_refresh.onHeaderRefreshComplete();
								Parcelable listState = lv_content
										.onSaveInstanceState();
								adapter.notifyDataSetChanged();
								lv_content.setSelection(lv_content.getBottom()); 
								if (mList.size() > 0) {
									lv_content
											.onRestoreInstanceState(listState);
								} else {
									toast(getResources().getString(R.string.cannot_load_more));
								}
							}
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	/** 发送消息 */
	protected void send() {
		String content = et_content.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			return;
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "message/addMessage");
		map.put("to_username", name);
		map.put("title", "信息");
		map.put("saytext", content);
		map.put("msg_type", type + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
//						if (message.getData() != null
//								&& message.getData().startsWith("{")) {
							chatList.clear();
							page = 1;
							et_content.setText("");
							MyProgressDialog.Cancel();
							getChatData(false);
//						}
					}
				}, null));
	}

	public  void addMessage() {
		chatList.clear();
		page = 1;
		getChatData(true);
	}

	OnHeaderRefreshListener headerRefreshListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			page++;
			getChatData(true);
		}
	};
	private MessageReceiver mMessageReceiver;
	   /** 
     * 动态注册广播 
     */  
    public void registerMessageReceiver() {  
        mMessageReceiver = new MessageReceiver();  
        IntentFilter filter = new IntentFilter();  
  
        filter.addAction(ACTION_INTENT_RECEIVER);  
        registerReceiver(mMessageReceiver, filter);  
    }  
    public class MessageReceiver extends BroadcastReceiver {  
    	  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            // TODO Auto-generated method stub  
            if (intent.getAction().equals(ACTION_INTENT_RECEIVER)) {  
            	addMessage();
            }  
        }  
  
    }  
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}

	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}
@Override
public void onBackPressed() {
	if (myEmojiView.getVisibility()==View.VISIBLE) {
		myEmojiView.setVisibility(View.GONE);
	}else {
		super.onBackPressed();
	}
}
@Override
protected void onDestroy() {
    unregisterReceiver(mMessageReceiver);  
	super.onDestroy();
}
}
