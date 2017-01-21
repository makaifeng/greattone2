package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.SwipeMenu.MySwipeMenuCreator;
import com.greattone.greattone.SwipeMenu.SwipeMenu;
import com.greattone.greattone.SwipeMenu.SwipeMenuListView;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.brand.BrandDetailActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.adapter.DirectoryListAdapter2;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.data.HttpConstants;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.PinYinUtil;
import com.greattone.greattone.widget.BadgeView;
import com.greattone.greattone.widget.MyLetterListView;
import com.greattone.greattone.widget.MyLetterListView.OnTouchingLetterChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** 通讯录 */
public class DirectoryActivity2 extends BaseActivity {
	private SwipeMenuListView lv_content;
	private MyLetterListView letterListView;
//	private RadioGroup radiogroup;
	String url = HttpConstants.CONTACTS_URL;
	Map<String, List<Friend>> contactsMap = new HashMap<>();
	List<Friend> contactsList = new ArrayList<Friend>();
	List<Friend> inviteList = new ArrayList<Friend>();
	String[] b = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };
	private TextView tv_hint;
	String type = "feed";
	private ImageView iv_search;
	private EditText et_search;
int followNum;
private BadgeView badgeView;
//private RadioButton radioButton3;
//private RadioButton radioButton1;
//private RadioButton radioButton2;

	/**标题栏	 */
	private RadioGroup rg_title;
	/**标题栏下的下标线 */
	private RelativeLayout rl_line;
	/**	 * title栏选中按钮的宽度	 */
	private int buttonWidth;
	/** title栏选中按钮的id*/
	private int selectId;
	/**	 * 下标线x的左坐标	 */
	private float lineX;
	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	private MySwipeMenuCreator creator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directory2);
		initView();
		getContacts();
	}

	private void initView() {
		setHead(getResources().getString(R.string.通讯录), true, true);//通讯录
		setOtherText(getResources().getString(R.string.添加), new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, AddNewFriendActivity.class));
			}
		});
		tv_hint = (TextView) findViewById(R.id.tv_hint);
		et_search = (EditText) findViewById(R.id.et_search);
		iv_search = (ImageView) findViewById(R.id.iv_search);
		iv_search.setOnClickListener(lis);

		lv_content = (SwipeMenuListView) findViewById(R.id.lv_content);
		lv_content.setOnScrollListener(ImageLoaderUtil.getPauseOnScrollListener());
		lv_content.setOnItemClickListener(itemClickListener);

		 creator = new MySwipeMenuCreator(context);
		creator.setTexts(new String[]{"取消关注"});
		addMenuCreator(creator);

		letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView);
		letterListView
				.setOnTouchingLetterChangedListener(letterListViewListener);
//		badgeView = new BadgeView(this);
//		badgeView.setTargetView(radioButton3);
		initMap();
	}

	private void addMenuCreator(MySwipeMenuCreator creator) {
		// set creator
		lv_content.setMenuCreator(creator);
		lv_content.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
					case 0://取消关注
						if (type.equals("feed")) {// 偶像
							//取消关注
							addattention(position);

						} else if (type.equals("friend")) {// 知音
							//取消关注
							addattention(position);
						}
						break;
				}
				return true;
			}
		});
	}

	/** 我的关注或关注我的 */
	public void getContacts() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "tongxunlu/getAllList");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("{")) {
							contactsList.clear();
						contactsList = JSON.parseArray(JSON.parseObject(message.getData()).getString("content"),
								Friend.class);
							inviteList.clear();
						inviteList = JSON.parseArray(JSON.parseObject(message.getData()).getString("invite"),
								Friend.class);
//						followNum=JSON.parseObject(message.getMsg()).getInteger("ti_guanzhu");
						}
						filterByLetters();
						initContentAdapter();
						MyProgressDialog.Cancel();
//						setNum(followNum);
					}

				}, null));
	}
//	/** 学生 */
//	private void getstudent() {
//		contactsList.clear();	initContentAdapter();
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "tongxunlu/getStudentList");
//		map.put("userid", Data.user.getUserid());
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						contactsList.clear();
//						if (message.getData().startsWith("[")) {
//							contactsList = JSON.parseArray(message.getData(),
//									Friend.class);
//						}
//						filterByLetters();
//						initContentAdapter();
//					}
//
//				}, null));
//	}
//	/** 老师 */
//	private void getteacher() {
//		contactsList.clear();	initContentAdapter();
//		HashMap<String, String> map = new HashMap<>();
//		map.put("api", "tongxunlu/getTeacherList");
//		map.put("userid", Data.user.getUserid());
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						contactsList.clear();
//						if (message.getData().startsWith("[")) {
//							contactsList = JSON.parseArray(message.getData(),
//									Friend.class);
//						}
//						filterByLetters();
//						initContentAdapter();
//					}
//
//				}, null));
//	}
//	/** 教室 */
//	private void getclassroom() {
//		contactsList.clear();	initContentAdapter();
//		HashMap<String, String> map = new HashMap<>();
//		map.put("api", "tongxunlu/getClassRoomList");
//		map.put("userid", Data.user.getUserid());
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						contactsList.clear();
//						if (message.getData().startsWith("[")) {
//							contactsList = JSON.parseArray(message.getData(),
//									Friend.class);
//						}
//						filterByLetters();
//						initContentAdapter();
//					}
//
//				}, null));
//	}
//	/** 品牌 */
//	private void getpinpai() {
//		contactsList.clear();	initContentAdapter();
//		HashMap<String, String> map = new HashMap<>();
//		map.put("api", "tongxunlu/getPinPaiList");
//		map.put("userid", Data.user.getUserid());
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						contactsList.clear();
//						if (message.getData().startsWith("[")) {
//							contactsList = JSON.parseArray(message.getData(),
//									Friend.class);
//						}
//						filterByLetters();
//						initContentAdapter();
//					}
//
//				}, null));
//	}
//	/** 获取好友 */
//	protected void getFriends() {
////		MyProgressDialog.show(context);
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "tongxunlu/getFriendList");
//		map.put("pageSize", "200");
//		map.put("userid ", Data.user.getUserid());
//		map.put("pageIndex", "1");
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						contactsList.clear();
//						if (message.getData().startsWith("[")) {
//							contactsList = JSON.parseArray(message.getData(),
//									Friend.class);
//
////							moveList(mlist);
//						}
//						MyProgressDialog.Cancel();
//						filterByLetters();
//						initContentAdapter();
//					}
//
//				}, null));
//	}


	OnClickListener lis=new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v==iv_search) {
				String str=et_search.getText().toString().trim();
				if (!str.isEmpty()) {
					contactsList.clear();
					for (int i = 0; i < contactsMap.get("#").size(); i++) {
						if ( contactsMap.get("#").get(i).getUsername().startsWith(str)) {
							contactsList.add(contactsMap.get("#").get(i));
						}else if (contactsMap.get("#").get(i).getPinyin().startsWith(str)) {
							contactsList.add(contactsMap.get("#").get(i));
						}
					}
					initContentAdapter();
				}
			}
		}
	};
	OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

		}
	};
	OnTouchingLetterChangedListener letterListViewListener = new OnTouchingLetterChangedListener() {

		@Override
		public void onTouchingLetterChanged(String s) {
			tv_hint.setText(s);
			tv_hint.setVisibility(View.VISIBLE);
		}

		@Override
		public void onTouchUp() {
			tv_hint.setVisibility(View.GONE);
		}

		@Override
		public void onClick(String s) {
			contactsList.clear();
			List<Friend> mList = contactsMap.get(s);
			contactsList.addAll(mList);
			initContentAdapter();
		}
	};

	/**
	 * 添加数字
	 * @param position
     */
	private   void setNum(int position) {
		if (badgeView!=null) {
			if (position == 0) {
				badgeView.setVisibility(View.GONE);
			} else {
					badgeView.setVisibility(View.VISIBLE);
					badgeView.setBadgeCount(position);
			}
		}
	}


	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		DirectoryListAdapter2 adapter = new DirectoryListAdapter2(context,
				contactsList, inviteList);
		lv_content.setAdapter(adapter);
	}

	/** 按这么筛选 */
	protected void filterByLetters() {
		initMap();
		for (int i = 0; i < contactsList.size(); i++) {
			if (contactsList.get(i)
					.getUsername()==null) {
				break;
			}
			contactsList.get(i).setPinyin( PinYinUtil.getAllFirstSpell(contactsList.get(i)
					.getUsername()));
			for (int j = 1; j < b.length-1; j++) {
				if (	contactsList.get(i).getPinyin().startsWith(b[j].toLowerCase(Locale.CHINA))) {
					contactsMap.get(b[j]).add(contactsList.get(i));
				}
			}
		}
		contactsMap.get("#").addAll(contactsList);
	}
/**初始化map的数据*/
	private void initMap() {
		contactsMap.clear();
		for (int j = 0; j < b.length; j++) {
			contactsMap.put(b[j], new ArrayList<Friend>());
		}
	}
	/**跳转到个人主页
	 * @param position */
	protected void toCenter(int position) {
		int group=Integer.valueOf(contactsList.get(position).getGroupid());
		Intent intent=new Intent();
		if (group==1||group==2) {//普通会员和名人
			intent .setClass(context, CelebrityActivity.class);
			intent.putExtra("id",contactsList.get(position).getUserid()+"");
			intent.putExtra("groupid",Integer.valueOf( contactsList.get(position).getGroupid()));
		}else 	if (group==3) {//老师
			intent .setClass(context, TeacherActivity.class);
			intent.putExtra("id", contactsList.get(position).getUserid()+"");
		}else 	if (group==4) {//教室
			intent .setClass(context, ClassRoomActivity.class);
			intent.putExtra("id", contactsList.get(position).getUserid()+"");
		}else 	if (group==5) {//品牌
			intent .setClass(context,BrandDetailActivity.class);
			intent.putExtra("id", contactsList.get(position).getUserid()+"");
		}else {
			intent .setClass(context, CelebrityActivity.class);
			intent.putExtra("id",contactsList.get(position).getUserid()+"");
			intent.putExtra("groupid",Integer.valueOf( contactsList.get(position).getGroupid()));
		}
		context.startActivity(intent);
	}
	/** 关注 */
	protected void addattention(final int position) {
		MyProgressDialog.show(context);
		HttpProxyUtil.addattention(context, contactsList.get(position).getUserid() , new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				((BaseActivity) context).toast(message.getInfo());
				contactsList.remove(position);
				initContentAdapter();
				if (message.getInfo().equals("增加关注成功")) {
				} else {
				}
				MyProgressDialog.Cancel();
			}

		}, null);
	}
}
