package com.greattone.greattone.activity.post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.map.SelectBdLocationActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.util.ArrayList;

public class PostPictureActivity extends BaseActivity {
//	private LinearLayout ll_select_picture;
	int type=GalleryActivity.TYPE_PICTURE;
	String mid = "10";
	String classid = "12";
	String filepass = System.currentTimeMillis()+"";
	int num=0;
	UpdateObjectToOSSUtil updateObjectToOSSUtil;
//	private TextView send;
	private EditText et_theme;
//	private LinearLayout ll_type;
//	private TextView tv_type;
	private EditText et_content;
//	private ImageView iv_select_video;
	private TextView tv_select_location;
	private TextView tv_select_video;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	private ProgressDialog pd;
	private ArrayList<Picture> pictureFileList=new ArrayList<>();
	private String title;
	private String newstext;
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
//	private ArrayList<String> pictureUrlList=new ArrayList<String>();
	private  String photos="";
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
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message message) {
			switch (message.what){
				case 0:
					updatePic2();
					break;
				case 1:
					pd.setMessage((CharSequence) message.obj);
					break;
				case 2:
					pd.dismiss();
					break;
				default:
					super.handleMessage(message);
					break;
			}
		}
	};

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

	/**发帖*/
	protected void post() {
		title = et_theme.getText().toString().trim();
		 newstext = et_content.getText().toString().trim();
		filepass= System.currentTimeMillis()+"";
		pictureFileList=adapter.getList();
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
//		for (int i = 0; i < pictureFileList.size(); i++) {
//			HttpProxyUtil.updatePicture(context, filepass, classid, pictureFileList.get(i).getPicUrl(),	new ResponseListener() {
//
//				@Override
//				public void setResponseHandle(Message2 message) {
//			String picUrl = JSON.parseObject(message.getData()).getString(
//							"url");
//			pictureUrlList.add(picUrl);
//			if (pictureUrlList.size()==pictureFileList.size()) {
//				post1(title, newstext);
//			}
//				}
//			}, null);
//		}
		num=0;
//		MyProgressDialog.show(context);
		updateObjectToOSSUtil=UpdateObjectToOSSUtil.getInstance();
		pd=new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		pd.show();
		updatePic2();

	}

	protected void updatePic2() {
		pd.setMessage("上传第"+(num+1)+"张");
		String path= pictureFileList.get(num).getPicUrl();
		updateObjectToOSSUtil.uploadImage_folder(context,path, new UpdateListener() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				pd.setMax((int)totalSize);
				pd.setProgress((int)currentSize);
			}

			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				num++;
				photos=photos+updateObjectToOSSUtil.getUrl(request.getBucketName(),request.getObjectKey())+"::::::";
				if (num==pictureFileList.size()) {
					post1();
				}else {
					Message.obtain(handler,0).sendToTarget();
				}
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});
	}

	protected void post1() {
		Message.obtain(handler,1,"上传数据中...").sendToTarget();
		String msg="api=post/ecms&enews=MAddInfo&mid="+mid
				+"&classid="+classid
				+"&title="+title
				+"&smalltext="+newstext+"&open="+"1"
//				+"&pictureUrlList="+pictureUrlList.get(0)
				+"&loginuid="+Data.user.getUserid()
				+"&logintoken="+Data.user.getToken()
//				+"&zuozhe="+Data.user.getUsername()
//				+"&laiyuan="+Data.user.getUsername()
				+"&filepass="+filepass;
//		for (int i = 0; i < pictureUrlList.size(); i++) {
//			msg=msg+"&msmallpic[]="+pictureUrlList.get(i);
//		}
		msg=msg+"&new_photo="+photos;
		addRequest(HttpUtil.httpConnectionByPost(context, msg,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						Message.obtain(handler, 2).sendToTarget();
						MyProgressDialog.Cancel();
						finish();
					}
				}, new HttpUtil.ErrorResponseListener() {
					@Override
					public void setServerErrorResponseHandle(Message2 message) {
						Message.obtain(handler,2).sendToTarget();
					}

					@Override
					public void setErrorResponseHandle(VolleyError error) {
						Message.obtain(handler,2).sendToTarget();
					}
				}));
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
