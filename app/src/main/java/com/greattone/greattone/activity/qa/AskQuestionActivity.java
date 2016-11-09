package com.greattone.greattone.activity.qa;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.UpdateVideoAct;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;
/**我要提问*/
public class AskQuestionActivity extends BaseActivity {
//	/**视频类型*/
//	private RadioGroup radiogroup;
	/**文本输入*/
	private EditText et_content;
//	/**选择的视频*/
	private MyGridView gv_pic;
	private TextView tv_send;
	private PostGridAdapter adapter;
	int type = GalleryActivity.TYPE_VIDEO;
	private ArrayList<Picture> videoFileList=new ArrayList<Picture>();
	protected String picUrl;
	protected String videoUrl;
	String filepass;
	String mid = "8";
	String classid = "85";
	private EditText et_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_question);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.I_need_to_ask_a_question), true, true);//我要提问
		
//		radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
//		radiogroup.setOnCheckedChangeListener(checkedChangeListener);
		et_content=(EditText)findViewById(R.id.et_content);
		et_title=(EditText)findViewById(R.id.et_title);
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);
		adapter=new PostGridAdapter(context, type,1);
		gv_pic.setAdapter(adapter);
		tv_send=(TextView)findViewById(R.id.tv_send);
		tv_send.setOnClickListener(lis);
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_send://发送
				send();
				break;

			default:
				break;
			}
		}
	};
	OnCheckedChangeListener checkedChangeListener=new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
		}
	};

	protected void send() {
		final String title = et_title.getText().toString().trim();
		final String newstext = et_content.getText().toString().trim();
		filepass= System.currentTimeMillis() + "";
		 videoFileList = adapter.getList();
		if (title.isEmpty()) {
			toast(getResources().getString(R.string.请填写主题));
			return;
		}
		if (newstext.isEmpty()) {
			toast(getResources().getString(R.string.请填写内容));
			return;
		}
		if (videoFileList.size()==0) {
			toast(getResources().getString(R.string.请选择视频));
			return;
		}
		//发送视频的缩略图
		MyProgressDialog.show(context);
		HttpProxyUtil.updatePictureByByte(context, newstext, title, videoFileList.get(0).getPicUrl(), false,new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						picUrl = JSON.parseObject(message.getData()).getString(
								"url");
						updateVideo(title, newstext);
					}
				}, null);
	}
	/**
	 * 添加到preferences和启动服务
	 */
	private void updateVideo(final String title, final String newstext) {
		preferences.edit().putString("updateTitle", title)//选手姓名
		.putString("updateUrl", picUrl)
		.putString("updatePath", videoFileList.get(0).getPicUrl())
		.putString("updateContent", newstext)
		.putString("updateClassid", classid)
		.putString("updateId", getIntent().getStringExtra("id") )
		.putString("updateFilepass", filepass)
		.putString("updateQa_name",getIntent().getStringExtra("name"))//
		.putInt("updateState", 0).commit();
		Intent intent=new Intent(context, UpdateVideoAct.class);
		intent.putExtra("isSee", 1);
		startActivity(intent);
		MyProgressDialog.Cancel();
		finish();
	}
//	/**	发送视频*/
//	protected void post1(final String title, final String newstext) {
//		HttpProxyUtil.updateVideo(context, filepass, classid, videoFileList.get(0).getPicUrl(),new ResponseListener() {
//
//			@Override
//			public void setResponseHandle(Message2 message) {
//				videoUrl = JSON.parseObject(message.getData()).getString(
//						"url");
//				post2(title, newstext);
//			}
//		}, null);
//	}
//	/**	提问*/
//	protected void post2(String title, String newstext) {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "post/ecms");
//		map.put("mid", mid);
//		map.put("enews", "MAddInfo");
//		map.put("classid", classid);
//		map.put("filepass", filepass);
////		map.put("newstime", filepass);
//		map.put("qa_video", videoUrl);
//		map.put("qa_name",getIntent().getStringExtra("name") );
//		map.put("qa_id",getIntent().getStringExtra("id") );
//		map.put("title", title);
//		map.put("titlepic",picUrl);
//		map.put("smalltext", newstext);
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						toast(message.getInfo());
//						MyProgressDialog.Cancel();
//						finish();
//					}
//				}, null));
//	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK && requestCode == 1) {// 视频
//			videoFileList = data
//					.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
//			adapter.setList(videoFileList);
//		} else if (resultCode == RESULT_OK && requestCode == 0) {// 录制视频
////			videoFileList =new ArrayList<String>();
////			videoFileList.add(FileUtil.getLocalImageUrl(context, "video.mp4"));
////			adapter.setList(videoFileList);
//			Uri uri = data.getData();
//			Cursor cursor = this.getContentResolver().query(uri, null, null,
//					null, null);
//			if (cursor != null && cursor.moveToNext()) {
//				String filePath = cursor.getString(cursor
//						.getColumnIndex(VideoColumns.DATA));
//				ArrayList<String> mList = new ArrayList<String>();
//				mList.add(filePath);
//				adapter.setList(mList);
//				cursor.close();
//			}
//		}
//	}
}
