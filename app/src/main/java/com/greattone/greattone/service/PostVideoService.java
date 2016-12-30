package com.greattone.greattone.service;

import java.io.File;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;
import com.alibaba.sdk.android.vod.upload.model.UploadFileInfo;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greattone.greattone.Listener.UpdateFileListener;
import com.greattone.greattone.MultiPart.HttpMultipartPost;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.receiver.MyReceiver;
import com.greattone.greattone.update.PutVideoSamples;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PostVideoService extends Service {
	public static final int Flag_Init = 0; // 初始状态
	public static final int Flag_Update = 1; // 上传状态
	public static final int Flag_False = 2; // 失败状态
	public static final int Flag_Done = 3; // 完成状态
	private static PostVideoService instance;
	public SharedPreferences preferences;

	// private long max;
	// private long num ;
	private String videoPath;
	// private OSSClient oss;
	private VODUploadClient upload;
	private String objectKey;
	private RequestQueue queue;
	// 运行sample前需要配置以下字段为有效的值
	// private static final String endpoint =
	// "http://oss-cn-shanghai.aliyuncs.com";
	private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
	// private static final String accessKeyId = "LCN2t148awhKJ9CB";
	// private static final String accessKeySecret =
	// "FFqFHu4USnTgjIjx75rkHmGMgiwPVi";
	private static final String accessKeyId = "7iFtlkzGkuWioxh5";
	private static final String accessKeySecret = "cbrkUVkZSQgonvzIhrJAngpCyWwvwL";

	private static final String testBucket = "hqsvideo";
	private static final String uploadObject = "video";

	// private static final String downloadObject = "video";

	@Override
	public void onCreate() {
		super.onCreate();
		 queue = Volley.newRequestQueue(this);
		instance = this;
		status = Flag_Init;
		preferences = getSharedPreferences(Constants.PREFERENCES_NAME_USER,
				MODE_PRIVATE);
	
		// // // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
		// OSSCredentialProvider credentialProvider = new
		// OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
		//
		// ClientConfiguration conf = new ClientConfiguration();
		// conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		// conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		// conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
		// conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
		// OSSLog.enableLog();
		// oss = new OSSClient(getApplicationContext(), endpoint,
		// credentialProvider, conf);
	}

	public static PostVideoService getInstance() {
		return instance;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		videoPath = intent.getExtras().getString("videoPath");
		if (videoPath != null) {
			if (status != Flag_Update) {
//				if (LanguageUtil.getLanguage().equals("CN")) {
				uploadVidoe(videoPath);
//				}else{
//					uploadVidoe(videoPath,HttpConstants2.UPDATE_URL);
//				}
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
/**
 * 上传视频到阿里云  （用于国内版本）
 * @param videoPath
 */
	public void uploadVidoe(final String videoPath) {
		upload = new VODUploadClient(getApplicationContext());
		String s[] = videoPath.split("\\.");
		objectKey = uploadObject + "/" + System.currentTimeMillis() + "."
				+ s[s.length - 1];
		new PutVideoSamples(upload, accessKeyId, accessKeySecret, testBucket,
				endpoint, videoPath, objectKey)
				.asyncPutVideoFromLocalFile(callback);
	}

	// public void uploadVidoe(final String videoPath, String url) {
	// String s[]=videoPath.split("\\/");
	// new PutObjectSamples(oss, testBucket, uploadObject+"/"+s[s.length-1],
	// videoPath).asyncPutObjectFromLocalFile(updateFileListener);
	// }
	//
	/**
	 * 上传视频到指定服务器（用于国外版本）
	 * @param videoPath
	 * @param url
	 */
	 public void uploadVidoe(String videoPath, String url) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "post/upfile");
			map.put("uploadkey", "e7627f53d4712552f8d82c30267d9bb4");
			map.put("classid", preferences.getString("updateClassid", "11"));
			map.put("open", "1");
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
	 HashMap<String, File> files = new HashMap<String, File>();
	 files.put("file", new File(videoPath));

		HttpMultipartPost httpMultipartPost = new HttpMultipartPost(this,
				url,listener);
		if (!files.equals("")||files!=null) {
			httpMultipartPost.addFileUpload(files);
		}
		if (!files.equals("")||files!=null) {
			httpMultipartPost.addStringUpload(map);
		}
		httpMultipartPost.setShowProgress( false);
		httpMultipartPost.execute("上传成功");
	 }
UpdateFileListener listener=new UpdateFileListener() {
	
	
	@Override
	public void updateError() {
		preferences.edit().putInt("updateState", 2).commit();
		status = Flag_Init;
		Intent intent = new Intent(MyReceiver.ACTION_UPDATE_FAIL);
		sendBroadcast(intent);
	}
	
	@Override
	public void onProgressUpdate(long uploadedSize, long totalSize) {
		status = Flag_Update;
		preferences.edit().putInt("updateState", status).commit();
		Intent intent = new Intent(MyReceiver.ACTION_UPDATE_PROGRESS);
		intent.putExtra("max", totalSize);
		intent.putExtra("progress", uploadedSize);
		sendBroadcast(intent);
	}

	@Override
	public void updateSuccess(Message2 message) {
		status = Flag_Done;
		preferences.edit().putInt("updateState", status).commit();
		Intent intent = new Intent(MyReceiver.ACTION_UPDATE_SUCCESS);
		sendBroadcast(intent);
		if (preferences.getString("updateClassid", "11").equals("11")) {// 广场发帖
			post( JSON.parseObject(message.getData()).getString("url"));

		} else if (preferences.getString("updateClassid", "11")
				.equals("73")) {// 海选报名
			post1();
		} else if (preferences.getString("updateClassid", "11")
				.equals("85")) {//提问
			post2();
		}
	}
};
//	UpdateListener updateFileListener = new UpdateListener() {
//
//		@Override
//		public void onProgress(PutObjectRequest request, long currentSize,
//				long totalSize) {
//			status = Flag_Update;
//			preferences.edit().putInt("updateState", status).commit();
//			Intent intent = new Intent(MyReceiver.ACTION_UPDATE_PROGRESS);
//			intent.putExtra("max", totalSize);
//			intent.putExtra("progress", currentSize);
//			sendBroadcast(intent);
//
//		}
//
//		@Override
//		public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//			status = Flag_Done;
//			preferences.edit().putInt("updateState", status).commit();
//			Intent intent = new Intent(MyReceiver.ACTION_UPDATE_SUCCESS);
//			sendBroadcast(intent);
//		}
//
//		@Override
//		public void onFailure(PutObjectRequest request,
//				ClientException clientExcepion,
//				ServiceException serviceException) {
//			preferences.edit().putInt("updateState", 2).commit();
//			status = Flag_Init;
//			Intent intent = new Intent(MyReceiver.ACTION_UPDATE_FAIL);
//			sendBroadcast(intent);
//
//		}
//	};

	VODUploadCallback callback = new VODUploadCallback() {
		@Override
		public void onUploadSucceed(UploadFileInfo info) {
			status = Flag_Done;
			preferences.edit().putInt("updateState", status).commit();

			if (preferences.getString("updateClassid", "11").equals("11")) {// 广场发帖
				String videoUrl = endpoint.replace("http://", "http://" + testBucket
				+ ".")
				+ "/" + objectKey;
				post(videoUrl);
			} else if (preferences.getString("updateClassid", "11")
					.equals("73")) {// 海选报名
				post1();
			} else if (preferences.getString("updateClassid", "11")
					.equals("85")) {
				post2();
			}
			Log.e("onUploadSucceed", "onUploadSucceed: " + info.getFilePath());
		}

		@Override
		public void onUploadFailed(UploadFileInfo info, String code,
				String message) {
			status = Flag_False;
			preferences.edit().putInt("updateState", status).commit();
			Intent intent = new Intent(MyReceiver.ACTION_UPDATE_FAIL);
			sendBroadcast(intent);
			Log.e("onUploadFailed", "onUploadFailed: " + info.getFilePath()
					+ ",code:" + code + ",message:" + message);
		}

		@Override
		public void onUploadProgress(UploadFileInfo info, long uploadedSize,
				long totalSize) {
			status = Flag_Update;
			Intent intent = new Intent(MyReceiver.ACTION_UPDATE_PROGRESS);
			intent.putExtra("max", totalSize);
			intent.putExtra("progress", uploadedSize);
			sendBroadcast(intent);

		}

		@Override
		public void onUploadTokenExpired() {
			Log.e("onUploadTokenExpired", "onUploadTokenExpired: ");
		}
	};

	/**
	 * 发帖
	 * 
	 * @param videoid
	 */
	protected void post(String url) {

		String classid = preferences.getString("updateClassid", "11");
		// String id = preferences.getString("updateId", "0");
		String filepass = preferences.getString("updateFilepass", "0");
		String picUrl = preferences.getString("updateUrl", "");
		String title = preferences.getString("updateTitle", "");
		String newstext = preferences.getString("updateContent", "");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("mid", "14");
		map.put("enews", "MAddInfo");
		map.put("classid", classid);
		map.put("filepass", filepass);
		map.put("open", 1 + "");
		// map.put("laiyuan", Data.user.getUsername());
		// map.put("zuozhe", Data.user.getUsername());
		map.put("shipin", url);
		map.put("title", title);
		map.put("titlepic", picUrl);
		map.put("smalltext", newstext);
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(instance, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						Intent intent = new Intent(MyReceiver.ACTION_UPDATE_SUCCESS);
						sendBroadcast(intent);
						Toast.makeText(getApplicationContext(),
								"发帖成功，请等待审核", Toast.LENGTH_LONG).show();
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	/** 报名 */
	protected void post1() {
		String videoUrl = endpoint.replace("http://", "http://" + testBucket
				+ ".")
				+ "/" + objectKey;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms_bm");
		map.put("mid", "20");
		map.put("enews", "MAddInfo");
		map.put("classid", "73");
		map.put("bao_type", "3");// 海选
		map.put("hai_id", preferences.getString("updateId", "11"));
		map.put("filepass", preferences.getString("updateFilepass", "11"));
		map.put("hai_video", videoUrl);// 上传视频
		map.put("hai_name", preferences.getString("updateTitle", ""));// 选手姓名
		map.put("hai_phone", preferences.getString("updateHai_phone", ""));// 联系电话
		map.put("hai_address", preferences.getString("updateHai_address", ""));// 详细地址
		map.put("hai_petition", preferences.getString("updateHai_petition", ""));// 参赛曲目
		map.put("hai_mend", preferences.getString("updateHai_mend", ""));// 所推荐的琴行(老师)
		map.put("hai_piano", preferences.getString("updateHai_piano", ""));// 琴行(老师)电话
		map.put("hai_division", preferences.getString("updateHai_division", ""));// 比赛赛区
		map.put("hai_grouping", preferences.getString("updateHai_grouping", ""));// 选择分组1
		map.put("hai_age", preferences.getString("updateHai_age", "11"));// 年龄
		map.put("hai_grouping1",
				preferences.getString("updateHai_grouping2", ""));// 选择分组2
		map.put("hai_photo", preferences.getString("updateUrl", ""));//
		map.put("pintype", preferences.getString("updatepPintype", ""));//乐器分类
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(instance, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						Intent intent = new Intent(MyReceiver.ACTION_UPDATE_SUCCESS);
						sendBroadcast(intent);
						Toast.makeText(getApplicationContext(),
								"报名成功，请等待审核",
								Toast.LENGTH_LONG).show();
					}
				}, null));
	}

	/** 提问 */
	protected void post2() {
		String videoUrl = endpoint.replace("http://", "http://" + testBucket
				+ ".")
				+ "/" + objectKey;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("mid", "8");
		map.put("enews", "MAddInfo");
		map.put("classid", "85");
		map.put("filepass", preferences.getString("updateFilepass", "11"));
		map.put("qa_video", videoUrl);
		map.put("qa_name", preferences.getString("updateQa_name", ""));
		map.put("qa_id", preferences.getString("updateId", "11"));
		map.put("title", preferences.getString("updateTitle", ""));
		map.put("titlepic", preferences.getString("updateUrl", ""));
		map.put("smalltext", preferences.getString("updateContent", "11"));
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(instance, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						Intent intent = new Intent(MyReceiver.ACTION_UPDATE_SUCCESS);
						sendBroadcast(intent);
						Toast.makeText(getApplicationContext(),
								message.getInfo(), Toast.LENGTH_LONG).show();
					}
				}, null));
	}
	/**
	 * 加入的volley网络请求的队列中
	 * 
	 * @param request
	 */
	public void addRequest(Request<?> request) {
		if (request!=null) {
			request.setRetryPolicy(new DefaultRetryPolicy(3 * 60 * 1000, 1, 1.0f));
			request.setTag(this);
			if (queue!=null) {
				queue.add(request);
			}
		}
	}
	// 下载状态标志
	private int status;

	public int getStatus() {
		return status;
	}

	@Override
	public void onDestroy() {
		if (status == Flag_Update) {
			preferences.edit().putInt("updateState", Flag_False).commit();
		} else
			preferences.edit().putInt("updateState", Flag_Init).commit();
		super.onDestroy();
	}
}
