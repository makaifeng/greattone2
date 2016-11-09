package com.greattone.greattone.activity.post;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.EntryActivity;
import com.greattone.greattone.activity.MusicChoiceActivity;
import com.greattone.greattone.activity.map.SelectBdLocationActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

public class PostMusicActivity extends BaseActivity {
//	private TextView send;
	private EditText et_theme;
//	private LinearLayout ll_type;
//	private TextView tv_type;
	private EditText et_content;
	private TextView tv_select_location;
	private TextView tv_select_music;
	private String filepass;
	private String musicUrl="";
	private String classid=ClassId.音乐广场_音乐_ID+"";
	String mid = "11";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_video);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.发送音乐), true, true);
		setOtherText(getResources().getString(R.string.发送), lis);

		et_theme = (EditText) findViewById(R.id.et_theme);
//		ll_type = (LinearLayout) findViewById(R.id.ll_type);
//		tv_type = (TextView) findViewById(R.id.tv_type);
		et_content = (EditText) findViewById(R.id.et_content);
//		iv_select_video = (ImageView) findViewById(R.id.iv_select_video);
//		iv_select_video.setVisibility(View.GONE);
		 findViewById(R.id.ll_music).setVisibility(View.VISIBLE);;
		tv_select_music = (TextView) findViewById(R.id.tv_select_music);
		tv_select_music.setOnClickListener(lis);
		tv_select_location = (TextView) findViewById(R.id.tv_select_location);
		tv_select_location.setOnClickListener(lis);
		 findViewById(R.id.tv_select_video).setVisibility(View.GONE);
			ImageView iv_gg = (ImageView) findViewById(R.id.iv_gg);
			iv_gg.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth/4));
			ImageLoaderUtil.getInstance().setImagebyurl(getIntent().getStringExtra("ggUrl"), iv_gg);
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_other://发送
				post1();
				break;
			case R.id.tv_select_music://选择音乐
				startActivityForResult(new Intent(context, MusicChoiceActivity.class), 0);
				break;
			case R.id.tv_select_location:// 位置
				startActivityForResult(new Intent(context,
						SelectBdLocationActivity.class), 101);
				break;
			default:
				break;
			}
		}
	};
	
	/**	发送视频*/
	protected void post1() {
		final String title = et_theme.getText().toString().trim();
		final String newstext = et_content.getText().toString().trim();
		filepass= System.currentTimeMillis() + "";
		if (title.isEmpty()) {
			toast(getResources().getString(R.string.请填写主题));
			return;
		}
		if (newstext.isEmpty()) {
			toast(getResources().getString(R.string.请填写内容));
			return;
		}
		if (TextUtils.isEmpty(musicUrl)) {
			toast("请选择音乐");
		}else{
			MyProgressDialog.show(context);
		HttpProxyUtil.updatePicture(context, filepass, classid, musicUrl, 	new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						musicUrl = JSON.parseObject(message.getData()).getString(
								"url");
						post2(title, newstext);
					}
				}, null);
	}}
	/**	发帖*/
	protected void post2(String title, String newstext) {
	
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("mid", mid);
		map.put("enews", "MAddInfo");
		map.put("classid", classid);
		map.put("open", "1");
		map.put("laiyuan", Data.user.getUsername());
		map.put("zuozhe", Data.user.getUsername());
		map.put("yinyue", musicUrl);
		map.put("title", title);
		map.put("titlepic",Data.myinfo.getUserpic());
		map.put("smalltext", newstext);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						Intent intent=new Intent(context, EntryActivity.class);
						intent.putExtra("type", "音乐广场");
						MyProgressDialog.Cancel();
						finish();
					}
				}, null));
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg0==0&&arg1==RESULT_OK) {
			musicUrl=arg2.getStringExtra("data");
			String ss[]=musicUrl.split("\\/");
			((TextView)findViewById(R.id.tv_music)).setText(ss[ss.length-1]);
		}
		super.onActivityResult(arg0, arg1, arg2);
	}
}
