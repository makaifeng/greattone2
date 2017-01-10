package com.greattone.greattone.activity.post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.greattone.greattone.Listener.UpdateListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.UpdateVideoAct;
import com.greattone.greattone.activity.map.SelectBdLocationActivity;
import com.greattone.greattone.adapter.PostGridAdapter;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.receiver.MyReceiver;
import com.greattone.greattone.service.PostVideoService;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.UpdateObjectToOSSUtil;
import com.greattone.greattone.widget.MyGridView;
import com.kf_test.picselect.GalleryActivity;

import java.util.ArrayList;

public class PostVideoActivity extends BaseActivity {
	// private TextView send;
	private EditText et_theme;
	// private LinearLayout ll_type;
	// private TextView tv_type;
	private EditText et_content;
	// private ImageView iv_select_video;
	private TextView tv_select_location;
	private MyGridView gv_pic;
	private PostGridAdapter adapter;
	String filepass = System.currentTimeMillis() + "";
	String mid = "14";
	String classid = "11";
	int type = GalleryActivity.TYPE_VIDEO;
	private ArrayList<Picture> videoFileList = new ArrayList<Picture>();
	protected String picUrl;
	ProgressDialog pd;
	UpdateObjectToOSSUtil updateObjectToOSSUtil;
//	protected String videoUrl;
//	private ProgressBar progressBar1;
	MyReceiver receiver;
private ImageView iv_gg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_video);
		initView();
//		receiver=new MyReceiver(context, );
	}

	private void initView() {
		setHead(getResources().getString(R.string.发送视频), true, true);
		setOtherText(getResources().getString(R.string.发送), lis);

		et_theme = (EditText) findViewById(R.id.et_theme);
		// ll_type = (LinearLayout) findViewById(R.id.ll_type);
		// ll_type.setOnClickListener(lis);
		// tv_type = (TextView) findViewById(R.id.tv_type);
		gv_pic = (MyGridView) findViewById(R.id.gv_pic);
		adapter = new PostGridAdapter(context, type, 1);
		// adapter.setOnItemDel(itemClickListener);
		gv_pic.setAdapter(adapter);
		et_content = (EditText) findViewById(R.id.et_content);
//		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		// iv_select_video = (ImageView) findViewById(R.id.iv_select_video);
		// LinearLayout.LayoutParams params=new
		// LinearLayout.LayoutParams(screenWidth/4-DisplayUtil.dip2px(context,
		// 5), screenWidth/4-DisplayUtil.dip2px(context, 5));
		// params.setMargins(DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5), DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5));
		// iv_select_video.setLayoutParams(params);
		// iv_select_video.setOnClickListener(lis);
		// tv_select_video = (TextView) findViewById(R.id.tv_select_video);
		tv_select_location = (TextView) findViewById(R.id.tv_select_location);
		tv_select_location.setOnClickListener(lis);
		iv_gg = (ImageView) findViewById(R.id.iv_gg);
		iv_gg.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, screenWidth/4));
		ImageLoaderUtil.getInstance().setImagebyurl(getIntent().getStringExtra("ggUrl"), iv_gg);
	}

	private OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_other:// 发送
//				startActivity(new Intent(context, UpdateVideoAct.class));
				post();
				break;
			case R.id.tv_select_location:// 位置
				startActivityForResult(new Intent(context,
						SelectBdLocationActivity.class), 101);
				break;
			case R.id.ll_type:// 类型
				break;

			default:
				break;
			}
		}
	};
	protected String mToken;

	/** 发帖 */
	protected void post() {
		final String title = et_theme.getText().toString().trim();
		final String newstext = et_content.getText().toString().trim();
		filepass = System.currentTimeMillis() + "";
		videoFileList = adapter.getList();
		if (title.isEmpty()) {
			toast(getResources().getString(R.string.请填写主题));
			return;
		}
		if (newstext.isEmpty()) {
			toast(getResources().getString(R.string.请填写内容));
			return;
		}
		if (videoFileList.size() == 0) {
			toast(getResources().getString(R.string.请选择上传视频));
			return;
		}
			if (preferences.getInt("updateState", 0)==PostVideoService.Flag_Update) {
				toast( "已经有一个视频在上传");
				return;
		}
		preferences.edit().putString("updateTitle", title)
				.putString("updateContent", newstext).commit();
		// 上传图片
		updateObjectToOSSUtil= UpdateObjectToOSSUtil.getInstance();
		pd=new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		pd.show();
		updatePic(videoFileList.get(0).getPicUrl());
//			MyProgressDialog.show(context);
//		HttpProxyUtil.updatePictureByByte(context, filepass, classid,
//				videoFileList.get(0).getPicUrl(), false,new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						picUrl = JSON.parseObject(message.getData()).getString(
//								"url");
//						updateVideo(title, newstext);
//					}
//				}, null);
//		preferences.edit().putString("updateTitle", title)
//		.putString("updateContent", newstext)
////		.putString("updateVideoid", videoid)
//		.putString("updateClassid", classid)
////		.putString("updateUrl", videoUrl)
//		.putString("updatePath", videoFileList.get(0).getPicUrl())
//		.putInt("updateState", 0).commit();
//		startActivity(new Intent(context, UpdateVideoAct.class));
//		 getVideoId(title,newstext);
;
	}
	protected void updatePic(String videoPath) {
		pd.setMessage("上传视频缩略图");
		updateObjectToOSSUtil.uploadImage_iamge_by_bytes(context,BitmapUtil.getVideoPicBytes(videoPath), new UpdateListener() {
			@Override
			public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
				pd.setMax((int)totalSize);
				pd.setProgress((int)currentSize);
			}

			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				 picUrl=updateObjectToOSSUtil.getUrl(request.getBucketName(),request.getObjectKey());
				updateVideo();
				pd.dismiss();
			}

			@Override
			public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
				MyProgressDialog.Cancel();
				pd.dismiss();
			}
		});
	}
	/**
	 *添加到preferences和启动服务
	 */
	private void updateVideo() {
		preferences.edit()
		.putString("updateClassid", classid)
		.putString("updateUrl", picUrl)
		.putString("updatePath", videoFileList.get(0).getPicUrl())
		.putInt("updateState", 0).commit();
		Intent intent=new Intent(context, UpdateVideoAct.class);
		intent.putExtra("isSee", 1);
		startActivity(intent);
		MyProgressDialog.Cancel();
		finish();
	}
	
	
///**
// * 获取犀思云的videoid和videoUrl
// * @param newstext 
// * @param title 
// */
//	void getVideoId(final String title, final String newstext) {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "cloudv/getuploadurl");
//		map.put("loginuid", Data.user.getUserid());
//		map.put("logintoken", Data.user.getToken());
//		addRequest(HttpUtil.httpConnectionByPost(context, map,
//				new ResponseListener() {
//
//					@Override
//					public void setResponseHandle(Message2 message) {
//						String videoid = JSON.parseObject(message.getData())
//								.getString("videoid");
//						String videoUrl = JSON.parseObject(message.getData())
//								.getString("upload_url");
////						uploadVidoe(videoFileList.get(0).getPicUrl(), videoUrl);
//						preferences.edit().putString("updateTitle", title)
//						.putString("updateContent", newstext)
//						.putString("updateVideoid", videoid)
//						.putString("updateClassid", classid)
//						.putString("updateUrl", videoUrl)
//						.putString("updatePath", videoFileList.get(0).getPicUrl())
//						.putInt("updateState", 0)
//						.commit();
//						post2(title, newstext,videoid);
//					}
//				}, null));
//	}
	
	// private void getkey() {
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("api", "qiniu/getToken");
	// map.put("loginuid", Data.user.getUserid());
	// map.put("logintoken", Data.user.getToken());
	// addRequest(HttpUtil.httpConnectionByPost(context, map,
	// new ResponseListener() {
	//
	// @Override
	// public void setResponseHandle(Message2 message) {
	// toast(message.getData());
	// mToken = message.getData();
	// initDialog();
	// update();
	// }
	// }, null));
	// }
	// private void setInfo(String key, ResponseInfo info, JSONObject res) {
	// if (res!=null&&info!=null&&key!=null) {
	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("api", "setInfo");
	// map.put("key", key);
	// map.put("info", info.toString());
	// map.put("res", res.toString());
	// addRequest(HttpUtil.httpConnectionByPost(context,
	// "http://192.168.1.114:8088/e/appapi/", map,
	// new ResponseListener() {
	//
	// @Override
	// public void setResponseHandle(Message2 message) {
	// toast(message.getData());
	// }
	// }, null));
	// }
	// }
	////七牛上传
	// private void update() {
	// Configuration config = new Configuration.Builder()
	// .chunkSize(256 * 1024) //分片上传时，每片的大小。 默认256K
	// .putThreshhold(512 * 1024) // 启用分片上传阀值。默认512K
	// .connectTimeout(10) // 链接超时。默认10秒
	// .responseTimeout(60) // 服务器响应超时。默认60秒
	// // .recorder(recorder) // recorder分片上传时，已上传片记录器。默认null
	// // .recorder(recorder, keyGen) // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
	// .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
	// .build();
	// //重用uploadManager。一般地，只需要创建一个uploadManager对象
	// UploadManager uploadManager = new UploadManager(config);
	//
	//
	// // 重用uploadManager。一般地，只需要创建一个uploadManager对象
	// // UploadManager uploadManager = new UploadManager();
	// String data = videoFileList.get(0).getPicUrl();
	// String d[]= videoFileList.get(0).getPicUrl().split("\\/");
	// String key =d[d.length-1];//文件名
	// String token = mToken;
	// uploadManager.put(data, key, token, new UpCompletionHandler() {
	// @Override
	// public void complete(String key, ResponseInfo info, JSONObject res) {
	// // res包含hash、key等信息，具体字段取决于上传策略的设置。
	// // et_content.setText(key + ",\r\n " + info + ",\r\n " + res);
	// setInfo(key, info, res);
	// pd.dismiss();
	// }
	// }, new UploadOptions(null, null, false, new UpProgressHandler() {
	// public void progress(String key, double percent) {
	// // Log.i("qiniu", key + ": " + percent);
	// pd.setProgress((int) (percent*100));
	// // et_theme.setText( key + ": " + percent);
	// }
	// }, null));
	// }
	
//	/** 发送视频 */
//	protected void post1(final String title, final String newstext) {
//		HttpProxyUtil.updateVideo(context, filepass, classid, videoFileList
//				.get(0).getPicUrl(), new ResponseListener() {
//
//			@Override
//			public void setResponseHandle(Message2 message) {
//				videoUrl = JSON.parseObject(message.getData()).getString("url");
//				post2(title, newstext);
//			}
//		}, null);
//	}

//	/**
//	 * 发帖
//	 * @param title
//	 * @param newstext
//     */
//	protected void post2(final String title, final String newstext) {
//
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("api", "post/ecms");
//		map.put("mid", mid);
//		map.put("enews", "MAddInfo");
//		map.put("classid", classid);
//		map.put("filepass", filepass);
//		map.put("open", 1 + "");
////		map.put("laiyuan", Data.user.getUsername());
////		map.put("zuozhe", Data.user.getUsername());
////		map.put("shipin", videoUrl);
//		map.put("title", title);
//		map.put("titlepic", picUrl);
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
//						updateVideo(title, newstext);
//						finish();
//					}
//				}, null));
//	}

}
