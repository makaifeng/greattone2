package com.greattone.greattone.activity.celebrity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.qa.AskQuestionActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.widget.MyRoundImageView;
import com.greattone.greattone.widget.PullToRefreshView;

/** 个人空间 */
public class CelebrityActivity extends BaseActivity   {
	private TextView ask;
	private UserInfo people;
//	public List<Blog> postList = new ArrayList<Blog>();
	public PullToRefreshView pull_to_refresh;
	private MyRoundImageView iv_icon;
	private TextView tv_identity;
	private RadioButton tv_home;
	private RadioButton tv_basic_info;
	private TextView tv_focus;
	private TextView tv_talk;
	int num;
	private TextView tv_fans;
//	private ImageView iv_level;

	// /** 第一页 */
	// private final int FIRSTPAGE = 1;
	// /** 页数 */
	// private int page = FIRSTPAGE;
	//
	// private final int NO_PULL = 0;// 其他
	// private final int PULL_UP = 2;// 上拉
	// private final int PULL_DOWN = 1;// 下拉
	// /**
	// * 加载数据时的状态 判断是其他，上拉还是下拉，NO_PULL其他，PULL_DOWN下拉，PULL_UP上拉
	// * */
	 int groupid =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_celebrity);
		groupid=getIntent().getIntExtra("groupid",0);
		initView();
		getpeople();
	}

	private void initView() {
		setHead(getResources().getString(R.string.personal_space), true, true);//个人空间
		if (groupid>0||groupid!=5) {
		ask = (TextView) findViewById(R.id.tv_head_other);
		ask.setVisibility(View.VISIBLE);
		ask.setTextSize(13);
		ask.setText(getResources().getString(R.string.I_need_to_ask_a_question));
		ask.setOnClickListener(lis);
		}
		
		pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);//
		iv_icon = (MyRoundImageView) findViewById(R.id.iv_icon);
		LayoutParams params = new LayoutParams(screenWidth / 3, screenWidth / 3);
		params.topMargin = screenWidth / 3 / 3;
		params.bottomMargin = screenWidth / 3 / 3;
		iv_icon.setLayoutParams(params);
		iv_icon.setRadius(DisplayUtil.dip2px(context, 20));
		tv_identity = (TextView) findViewById(R.id.tv_identity);
//		tv_level = (TextView) findViewById(R.id.tv_level);
//		iv_level = (ImageView) findViewById(R.id.iv_level);
		tv_fans = (TextView) findViewById(R.id.tv_fans);
		tv_home = (RadioButton) findViewById(R.id.tv_home);
		tv_home.setOnClickListener(lis);
		tv_home.setChecked(true);
		tv_basic_info = (RadioButton) findViewById(R.id.tv_basic_info);
		tv_basic_info.setOnClickListener(lis);
		tv_focus = (TextView) findViewById(R.id.tv_focus);
		tv_focus.setOnClickListener(lis);
		tv_talk = (TextView) findViewById(R.id.tv_talk);
		if (groupid==5) {
			tv_talk.setVisibility(View.GONE);
		}else{
			tv_talk.setOnClickListener(lis);
		}
	}

	private void getpeople() {
		MyProgressDialog.show(context);
		HttpProxyUtil.getUserInfo(context,  getIntent().getStringExtra("id"), null,		new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				people = JSON.parseObject(message.getData(),
						UserInfo.class);
				initViewData();
				num++;
				// MyProgressDialog.Cancel(num, 2);
				MyProgressDialog.Cancel();
			}

		}, null);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_home:
				tv_home.setChecked(true);
				tv_basic_info.setChecked(false);
//				tv_home.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));// 加粗
//				tv_home.setTextSize(15);
//				tv_basic_info.setTypeface(Typeface
//						.defaultFromStyle(Typeface.NORMAL));// 普通
//				tv_basic_info.setTextSize(12);
				addFragment(new CelebrityPostsFragment());
				break;
			case R.id.tv_basic_info:
				tv_home.setChecked(false);
				tv_basic_info.setChecked(true);
//				tv_basic_info.setTypeface(Typeface
//						.defaultFromStyle(Typeface.BOLD));// 加粗
//				tv_basic_info.setTextSize(15);
//				tv_home.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));// 普通
//				tv_home.setTextSize(12);
				CelebrityBasicInfoFragment fragment=new CelebrityBasicInfoFragment();
				fragment.setData(people);
				addFragment(fragment);
				break;
			case R.id.tv_focus://关注
				addattention();
				break;
			case R.id.tv_talk://私信
				MyProgressDialog.show(context);
				HttpProxyUtil.isFriend(context, people.getUserid(), new ResponseListener() {
					
					@Override
					public void setResponseHandle(Message2 message) {
						MyProgressDialog.Cancel();
						if (message.getData().equals("1")) {
							Intent intent = new Intent(context, MyChatActivity.class);
							intent.putExtra("name", people.getUsername());
							intent.putExtra("image", people.getUserpic());
							startActivity(intent);
						} else {
							toast(getResources().getString(R.string.互相关注后才能发送私信));
						}
					}
				}, null);
				break;
			case R.id.tv_head_other://提问
				if (people.getUserid().equals(Data.myinfo.getUserid())) {
					toast("不能向自己提问");
					return ;
				}
				Intent intent = new Intent(context, AskQuestionActivity.class);
				intent.putExtra("id",people.getUserid());
				intent.putExtra("name",people.getUsername());
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

	/** 加载数据 */
	protected void initViewData() {
		setHead(people.getUsername(), true, true);
		ImageLoaderUtil.getInstance().setImagebyurl(people.getUserpic(),
				iv_icon);
//		ImageLoaderUtil.getInstance()
//				.setImagebyurl(people.getLevel().getPic(), iv_level);
		tv_identity.setText( MessageUtil.getIdentity(people)+"  |  "+people.getLevel().getName());
		tv_fans.setText(getResources().getString(R.string.关注_hint) + people.getFeednum() + "  |  "+getResources().getString(R.string.粉丝数_hint)
				+ people.getFollownum());//关注：1 | 粉丝数：0
		if ( people.getIsfeed()==1) {
			tv_focus.setText(getResources().getString(R.string.已关注));//取消
		}else {
			tv_focus.setText(getResources().getString(R.string.guanzhu));//关注
		}
		addFragment(new CelebrityPostsFragment());
	}
/**关注*/
	protected void addattention() {
		MyProgressDialog.show(context);
		HttpProxyUtil.addattention(context, people.getUserid(),new ResponseListener() {

			@Override
			public void setResponseHandle(Message2 message) {
				((BaseActivity) context).toast(message.getInfo());
				if (message.getInfo().equals("增加关注成功")) {
					tv_focus.setText(getResources().getString(R.string.取消));//取消
					people.setIsfeed(1);
				} else {
					people.setIsfeed(0);
					tv_focus.setText(getResources().getString(R.string.guanzhu));//关注
				}
				MyProgressDialog.Cancel();
			}

		}, null);
	}

	protected void addFragment(Fragment fragment) {
		Bundle bundle=new Bundle();
		bundle.putString("id", getIntent().getStringExtra("id"));
		fragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment, fragment).commit();
	}
}
