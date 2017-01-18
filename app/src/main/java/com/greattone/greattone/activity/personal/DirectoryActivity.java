package com.greattone.greattone.activity.personal;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
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
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.adapter.DirectoryListAdapter;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.data.HttpConstants;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
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
public class DirectoryActivity extends BaseActivity {
	private SwipeMenuListView lv_content;
	private MyLetterListView letterListView;
//	private RadioGroup radiogroup;
	String url = HttpConstants.CONTACTS_URL;
	Map<String, List<Friend>> contactsMap = new HashMap<String, List<Friend>>();
	List<Friend> contactsList = new ArrayList<Friend>();
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
		setContentView(R.layout.activity_directory);
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

		// 标题栏
		rg_title = (RadioGroup) findViewById(R.id.rg_title);
		rl_line = (RelativeLayout) findViewById(R.id.rl_line);
		horizontalScrollViewAddView();
//		rg_title.setOnCheckedChangeListener(checkedChangeListener);
		initButton(buttons.get(0));
//		rg_title.check(buttons.get(0).getId());
//		radioButton1= (RadioButton) findViewById(R.id.radioButton1);
//		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
//		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
//		radioButton1.setChecked(true);
//		radioButton1.setOnClickListener(lis);
//		radioButton2.setOnClickListener(lis);
//		radioButton3.setOnClickListener(lis);
//		radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
//		radiogroup.check(R.id.radioButton1);
//		radiogroup.setOnCheckedChangeListener(listener);
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

	@SuppressWarnings("deprecation")
	private void horizontalScrollViewAddView() {
		buttons = new ArrayList<RadioButton>();
		String title_names[]={};
		if (Data.myinfo!=null){
			if (Data.myinfo.getGroupid()==1||Data.myinfo.getGroupid()==2)
				title_names=getResources().getStringArray(R.array.directory_groupid1);
			if (Data.myinfo.getGroupid()==3)
				title_names=getResources().getStringArray(R.array.directory_groupid3);
			if (Data.myinfo.getGroupid()==4)
				title_names=getResources().getStringArray(R.array.directory_groupid4);
			if (Data.myinfo.getGroupid()==5)
				title_names=getResources().getStringArray(R.array.directory_groupid5);
		}
		for (int i = 0; i < title_names.length; i++) {
			RadioButton button = new RadioButton(context);
			button.setId(i);
			button.setText(title_names[i]);
			button.setTextSize(18);
			button.setTextColor(getResources().getColor(R.color.recommend_textcolor));
			button.setGravity(Gravity.CENTER);
			button.setPadding(DisplayUtil.dip2px(context, 15),
					DisplayUtil.dip2px(context, 10),
					DisplayUtil.dip2px(context, 15),
					DisplayUtil.dip2px(context, 10));
			RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 5));
			button.setLayoutParams(layoutParams);
			button.setButtonDrawable(new ColorDrawable(
					getResources().getColor(android.R.color.transparent)));
			button.setBackgroundResource(R.drawable.checked_button_bg);
			button.setOnClickListener(lis);
			rg_title.addView(button);
			buttons.add(button);
		}
	}
	/** 我的关注或关注我的 */
	private void getContacts() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "tongxunlu/getFollowList");
		map.put("userid", Data.user.getUserid());
		map.put("type", type);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("[")) {
						contactsList = JSON.parseArray(message.getData(),
								Friend.class);
						followNum=JSON.parseObject(message.getMsg()).getInteger("ti_guanzhu");
						}
						filterByLetters();
						initContentAdapter();
						MyProgressDialog.Cancel();
						setNum(followNum);
					}

				}, null));
	}
	/** 学生 */
	private void getstudent() {
		contactsList.clear();	initContentAdapter();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "tongxunlu/getStudentList");
		map.put("userid", Data.user.getUserid());
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("[")) {
							contactsList = JSON.parseArray(message.getData(),
									Friend.class);
						}
						filterByLetters();
						initContentAdapter();
					}

				}, null));
	}
	/** 老师 */
	private void getteacher() {
		contactsList.clear();	initContentAdapter();
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/getTeacherList");
		map.put("userid", Data.user.getUserid());
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("[")) {
							contactsList = JSON.parseArray(message.getData(),
									Friend.class);
						}
						filterByLetters();
						initContentAdapter();
					}

				}, null));
	}
	/** 教室 */
	private void getclassroom() {
		contactsList.clear();	initContentAdapter();
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/getClassRoomList");
		map.put("userid", Data.user.getUserid());
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("[")) {
							contactsList = JSON.parseArray(message.getData(),
									Friend.class);
						}
						filterByLetters();
						initContentAdapter();
					}

				}, null));
	}
	/** 品牌 */
	private void getpinpai() {
		contactsList.clear();	initContentAdapter();
		HashMap<String, String> map = new HashMap<>();
		map.put("api", "tongxunlu/getPinPaiList");
		map.put("userid", Data.user.getUserid());
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("[")) {
							contactsList = JSON.parseArray(message.getData(),
									Friend.class);
						}
						filterByLetters();
						initContentAdapter();
					}

				}, null));
	}
	/** 获取好友 */
	protected void getFriends() {
//		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "tongxunlu/getFriendList");
		map.put("pageSize", "200");
		map.put("userid ", Data.user.getUserid());
		map.put("pageIndex", "1");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						contactsList.clear();
						if (message.getData().startsWith("[")) {
							contactsList = JSON.parseArray(message.getData(),
									Friend.class);
					
//							moveList(mlist);
						}
						MyProgressDialog.Cancel();
						filterByLetters();
						initContentAdapter();
					}

				}, null));
	}

//	protected void moveList(List<Friend2> mlist) {
//	
//		for (int i = 0; i < mlist.size(); i++) {
//			Friend friend = new Friend();
//			friend.setUsername(mlist.get(i).getFname());
//			friend.setUserpic(mlist.get(i).getFpic());
//			friend.setSaytext(mlist.get(i).getFsay());
//			friend.setUserid(mlist.get(i).getFuserid());
//			contactsList.add(friend);
//		}
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
			}else{
				initMap();
				initButton(v);
				startAnimation(v, 200);
				et_search.setText("");
				if (((RadioButton)v).getText().equals(getString(R.string.directory_1))) {//偶像
					addMenuCreator(creator);
					type = "feed";
					getContacts();
				}else	if (((RadioButton)v).getText().equals(getString(R.string.directory_2))) {//知音
					addMenuCreator(creator);
					type = "friend";
					getFriends();
				}else		if (((RadioButton)v).getText().equals(getString(R.string.directory_3))) {//声粉
					addMenuCreator(null);
					type = "follow";
					getContacts();
				}else		if (((RadioButton)v).getText().equals(getString(R.string.directory_4))) {//学生
					addMenuCreator(null);
					type = "student";
					getstudent();
				}else		if (((RadioButton)v).getText().equals(getString(R.string.directory_5))) {//老师
					addMenuCreator(null);
					type = "teacher";
					getteacher();
				}else		if (((RadioButton)v).getText().equals(getString(R.string.directory_6))) {//教室
					addMenuCreator(null);
					type = "classroom";
					getclassroom();
				}else		if (((RadioButton)v).getText().equals(getString(R.string.directory_7))) {//品牌
					addMenuCreator(null);
					type = "pinpai";
					getpinpai();
				}
			}
		}
	};
	OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(context, MyChatActivity.class);
			intent.putExtra("name", contactsList.get(position).getUsername());
//			intent.putExtra("id", messageList.get(position).getFuid());
			intent.putExtra("image", contactsList.get(position).getUserpic());
			startActivity(intent);
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
//	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			initMap();
//			et_search.setText("");
//			switch (checkedId) {
//			case R.id.radioButton1:// 我的知音
//				type = "feed";
//				getContacts();
//				break;
//			case R.id.radioButton2:// 我的好友
//				type = "friend";
//				getFriends();
//				break;
//			case R.id.radioButton3:// 我的粉丝
//				type = "follow";
//				getContacts();
//				break;
//
//			default:
//				break;
//			}
//		}
//	};
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
	private void initButton(View v) {
		for (RadioButton btn:buttons) {
			if (btn==v){
				btn.setChecked(true);
				btn.setTextColor(getResources().getColor(R.color.white));
			}else {
				btn.setChecked(false);
				btn.setTextColor(getResources().getColor(R.color.black));
			}
		}
	}

	/**
	 * 下标线的动画
	 *
	 */
	protected void startAnimation(View button, int time) {
		TranslateAnimation animation = new TranslateAnimation(lineX,
				button.getX()+button.getPaddingLeft(), button.getTop(), button.getTop());
		animation.setDuration(time);
		animation.setFillAfter(true);
		lineX = button.getX();
		rl_line.setAnimation(animation);
		setLineWidth(button);
	}
	/**
	 * 设置下标线的宽度
	 */
	private void setLineWidth(final View button) {
		ViewTreeObserver vto = button.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				buttonWidth = button.getMeasuredWidth();
				ViewGroup.LayoutParams layoutParams = rl_line.getLayoutParams();
				layoutParams.width = buttonWidth-button.getPaddingLeft()-button.getPaddingRight();
				rl_line.setLayoutParams(layoutParams);
				return true;
			}
		});
	}
	/**
	 * 加载ContentAdapter数据
	 */
	protected void initContentAdapter() {
		DirectoryListAdapter adapter = new DirectoryListAdapter(context,
				contactsList, type);
		lv_content.setAdapter(adapter);
	}

	/** 按这么筛选 */
	protected void filterByLetters() {
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
		HttpProxyUtil.addattention(context, contactsList.get(position).getUserid() , new HttpUtil.ResponseListener() {

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
