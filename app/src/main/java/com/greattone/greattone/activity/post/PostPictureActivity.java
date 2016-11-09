package com.greattone.greattone.activity.post;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.map.SelectBdLocationActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

public class PostPictureActivity extends BaseActivity {
//	private TextView send;
	private EditText et_theme;
//	private LinearLayout ll_type;
//	private TextView tv_type;
	private EditText et_content;
//	private ImageView iv_select_video;
	private TextView tv_select_location;
	private TextView tv_select_video;
//	private LinearLayout ll_select_picture;
	int type=GalleryActivity.TYPE_PICTURE;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	String mid = "10";
	String classid = "12";
	String filepass = System.currentTimeMillis()+"";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_video);
		initView();
	}
	
	private void initView() {
		setHead(getResources().getString(R.string.发送图片), true, true);
		setOtherText(getResources().getString(R.string.发送), lis);

		et_theme = (EditText) findViewById(R.id.et_theme);
//		ll_type = (LinearLayout) findViewById(R.id.ll_type);
//		ll_type.setOnClickListener(lis);
//		tv_type = (TextView) findViewById(R.id.tv_type);
		et_content = (EditText) findViewById(R.id.et_content);
//		iv_select_video = (ImageView) findViewById(R.id.iv_select_video);
//		iv_select_video.setOnClickListener(lis);
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);
		adapter=new PostGridAdapter(context, type,Constants.MAXPAGE);
		gv_pic.setAdapter(adapter);
		tv_select_video = (TextView) findViewById(R.id.tv_select_video);
//		tv_select_video.setText("选择图片");
		tv_select_video.setVisibility(View.GONE);
		tv_select_location = (TextView) findViewById(R.id.tv_select_location);
		tv_select_location.setOnClickListener(lis);
		
		ImageView iv_gg = (ImageView) findViewById(R.id.iv_gg);
		iv_gg.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth/4));
		ImageLoaderUtil.getInstance().setImagebyurl(getIntent().getStringExtra("ggUrl"), iv_gg);
	}
	private OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ll_type://类型
				break;
			case R.id.tv_head_other://发送
				post();
				break;
			case R.id.tv_select_location://位置
				startActivityForResult(new Intent(context, SelectBdLocationActivity.class), 101);
				break;
//			case R.id.iv_select_video://视频
//				 MyIosDialog.ShowBottomDialog(context, "", new String []{"去拍照","去相册"}, new DialogItemClickListener() {
//					
//					@Override
//					public void itemClick(String result, int position) {
//						if (result.equals("去拍照")) {
//							
//						}else if (result.equals("去相册")) {
//							Intent intent = new Intent(context,GalleryActivity.class);
//							intent.putExtra(Constants.EXTRA_PHOTO_LIMIT, 8);//最大选择数
//							intent.putExtra("type", type);//选择类型
//							startActivityForResult(intent, 1);
//						}
//					}
//				});
//				break;

			default:
				break;
			}
		}
	};
//	//图片删除按钮
//	OnBtnItemClickListener itemClickListener=new OnBtnItemClickListener() {
//		
//		@Override
//		public void onItemClick(View v, int position) {
//			pictureFileList.remove(position);
////			if (videoFileList.size()==0) {
////				iv_select_video.setVisibility(View.VISIBLE);
////			}
//			adapter.setList(pictureFileList);
//		}
//	};
//	private ArrayList<String> pictureFileList=new ArrayList<String>();
	private ArrayList<String> pictureUrlList=new ArrayList<String>();
	/**发帖*/
	protected void post() {
		final String title = et_theme.getText().toString().trim();
		final String newstext = et_content.getText().toString().trim();
		filepass= System.currentTimeMillis()+"";
		final ArrayList<Picture> pictureFileList=adapter.getList();
		if (pictureFileList.size()==0) {
			toast(getResources().getString(R.string.请选择图片));
			return;
		}
		if (title.isEmpty()) {
			toast(getResources().getString(R.string.请填写主题));
			return;
		}
		if (newstext.isEmpty()) {
			toast(getResources().getString(R.string.请填写内容));
			return;
		}
		
		//发送图片
		MyProgressDialog.show(context);
		for (int i = 0; i < pictureFileList.size(); i++) {
			HttpProxyUtil.updatePicture(context, filepass, classid, pictureFileList.get(i).getPicUrl(),	new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
			String picUrl = JSON.parseObject(message.getData()).getString(
							"url");
			pictureUrlList.add(picUrl);
			if (pictureUrlList.size()==pictureFileList.size()) {
				post1(title, newstext);
			}
				}
			}, null);
		}
	}
	protected void post1(String title,String newstext) {
		String msg="api=post/ecms&enews=MAddInfo&mid="+mid
				+"&classid="+classid
				+"&title="+title
				+"&smalltext="+newstext+"&open="+"1"
				+"&pictureUrlList="+pictureUrlList.get(0)
				+"&loginuid="+Data.user.getUserid()
				+"&logintoken="+Data.user.getToken()
//				+"&zuozhe="+Data.user.getUsername()
//				+"&laiyuan="+Data.user.getUsername()
				+"&filepass="+filepass;
		for (int i = 0; i < pictureUrlList.size(); i++) {
			msg=msg+"&msmallpic[]="+pictureUrlList.get(i);
		}
		addRequest(HttpUtil.httpConnectionByPost(context, msg,
				new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				toast(message.getInfo());
				MyProgressDialog.Cancel();
				finish();
			}
		}, null));
	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data){
//		super.onActivityResult( requestCode,  resultCode,  data);
//		if (resultCode==RESULT_OK&&requestCode==1) {//图片
//			 ArrayList<String>  mList=data.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
//		 pictureFileList.addAll(mList);
//		 adapter.setList(pictureFileList);
//		}else if (resultCode==RESULT_OK&&requestCode==0) {//拍照片
//			ArrayList<String>  mList=new ArrayList<String>();
//			mList.add(FileUtil.getLocalImageUrl(context,  "icon.png"));
//			pictureFileList.addAll(mList);
//			adapter.setList(pictureFileList);
//		}else if (resultCode==RESULT_OK&&requestCode==101) {//地址
//			
//		}
//	}
}
