package com.greattone.greattone.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.ReplayDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class WebActivity extends BaseActivity {

	private WebView webview;
	private String urlPath;
	private int orientation;
	

	// private boolean isFristIntent = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_web);
			getIntentData();
			MyProgressDialog.show(context);
			initView();
			startAction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**启动方法
 * @throws NoSuchMethodException 
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException */
	private void startAction() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String action=getIntent().getStringExtra("action");
		if (action==null) {
			return;
		}
		//通过反射启动方法
		Class<?> cls=getClass();
		 Method m1 = cls.getDeclaredMethod(action);
		 m1.invoke(this); 
	}

	private void getIntentData() {
		urlPath = getIntent().getStringExtra("urlPath");
		orientation = getIntent().getIntExtra("orientation", 0);
		if (orientation==1) {//橫屏
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		if (getIntent().getStringExtra("title")!=null) {
			setHead(getIntent().getStringExtra("title"), true, true);
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {


		webview = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		 // 设置可以访问文件
		 webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		// 加载需要显示的网页
//	 urlPath = "http://www.greattone.net/apple/html/index.html";
		webview.loadUrl(urlPath);
		webview.addJavascriptInterface(new JsInteration(this), "android");
		// 设置Web视图
		webview.setWebViewClient(new webViewClient());
//		setWebChromeClient();
		// if (android.os.Build.VERSION.SDK_INT >
		// android.os.Build.VERSION_CODES.JELLY_BEAN) {
		// webview.addJavascriptInterface(new WBBehavior(this), "myObj");
		// }
		// webview.setOnClickListener(lis);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_head_back:
				if (webview.canGoBack()) {
					webview.goBack(); // goBack()表示返回WebView的上一页面
				} else {
					finish();
				}
				break;
			// case R.id.webview:
			// if (webview.getUrl().startsWith("ttm")) {
			// }
			// break;

			default:
				break;
			}
		}
	};
	private TextView tv_like;

	// @Override
	// // 设置回退
	// // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
	// webview.goBack(); // goBack()表示返回WebView的上一页面
	// return true;
	// }
	// finish();// 结束退出程序
	// return false;
	// }

	// Web视图
	private class webViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// if (url.startsWith("ttm://ActivityServiceList?id=310001")) {
			// if (isFristIntent) {// 存在调用2次次方法，用这个区分是否已经跳转
			// // Intent intent = new Intent(H5Activity.this,
			// // SubjectActivity.class);
			// // startActivity(intent);
			// isFristIntent=false;
			// // if (hasOneYuan==1) {
			// startActivity(new Intent(context, SubjectActivity.class));
			// // } else {
			// // setResult(MyResultCode.H5);
			// // }
			// finish();
			// }
			// } else {
			super.onPageStarted(view, url, favicon);
			// }
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// if (!url.startsWith("ttm")) {
			// tv_head_title.setText(view.getTitle());
			// }
			MyProgressDialog.Cancel();
			super.onPageFinished(view, url);
		}
	}
	@Override
	protected void onPause() {
		if (webview!=null) {
			webview.reload (); 
		}
		super.onPause();
	}
	/**
	 * 新闻功能
	 */
	protected void functionForNews(){
		getLikes();
	 tv_like=	((TextView)findViewById(R.id.tv_like));
		findViewById(R.id.ll_foot).setVisibility(View.VISIBLE);
(findViewById(R.id.ll_commit)).setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		commit();
	}
});
(findViewById(R.id.iv_tolike)).setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		toLike();
	}
});
(findViewById(R.id.iv_share)).setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		share();
	}
});
	}
	 /**
	  * 获取点赞数
	  */
private void getLikes() {
		HttpProxyUtil.getLikes(context, getIntent().getStringExtra("id"),new ResponseListener() {
			
			@Override
			public void setResponseHandle(Message2 message) {
				if (message.getData()!=null&&message.getData().startsWith("{")) {
					String text=JSON.parseObject(message.getData()).getString("like");
					tv_like.setText(text);
				}
			}
		}, null);
	}
/**帖子回复*/
	protected void replyPost() {
		 setOtherText(getResources().getString(R.string.回复), new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				commit();
			}
		});
	}
	/**
	 * 评论
	 */
	protected void commit() {
		ReplayDialog localReplayDialog = new ReplayDialog(context);
		localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

			@Override
			public void OnReplay(String text) {
				MyProgressDialog.show(context);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("api", "comment/post");
				map.put("classid", getIntent().getStringExtra("classid"));
				map.put("id",getIntent().getStringExtra("id"));
				map.put("saytext", text);
				map.put("loginuid", Data.user.getUserid());
				map.put("logintoken", Data.user.getToken());
				((BaseActivity) context).addRequest(HttpUtil
						.httpConnectionByPost(context, map,
								new ResponseListener() {

									@Override
									public void setResponseHandle(
											Message2 message) {
										((BaseActivity) context)
												.toast(message.getInfo());
										MyProgressDialog.Cancel();
										webview.reload();
									}

								}, null));
			}
		});
		localReplayDialog.show();
}
	/**点赞*/
			protected void toLike() {
				MyProgressDialog.show(context);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("api", "comment/dianzan");
				map.put("classid",getIntent().getStringExtra("classid"));
				map.put("id", getIntent().getStringExtra("id"));
				map.put("loginuid", Data.user.getUserid());
				map.put("logintoken", Data.user.getToken());
				((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(
						context, map, new ResponseListener() {

							@Override
							public void setResponseHandle(Message2 message) {
								((BaseActivity) context).toast(message.getInfo());
								webview.reload();
								try {
									tv_like.setText((Integer.valueOf(tv_like.getText().toString().trim())+1)+"");
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
								MyProgressDialog.Cancel();
							}
						}, null));
			}
	/**分享直播*/
	protected void shareZB() {
		setOtherText("分享", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				share();
			}
		});
	}

	protected void share() {
		String title=getIntent().getStringExtra("shareTitle");
		String content=getIntent().getStringExtra("shareContent");
		String titlepic=getIntent().getStringExtra("sharePic");
		SharePopWindow.build(context).setTitle(title)
		.setContent(content)
		.setTOargetUrl(urlPath).setIconPath(titlepic)
		.show();
	}

	private ValueCallback<Uri> mUploadFile;  
    /**拍照/选择文件请求码*/  
    private static final int REQUEST_UPLOAD_FILE_CODE = 12343;  
    /**
     * webView调用相册功能
     */
    @SuppressWarnings("unused")
    private void setWebChromeClient()  
    {  
        if (null != webview)  
        {  
        	webview.setWebChromeClient(new WebChromeClient()  
            {  
                // Andorid 4.1+  
				public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)  
                {  
                    openFileChooser(uploadFile);  
                }  
  
                // Andorid 3.0 +  
                public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType)  
                {  
                    openFileChooser(uploadFile);  
                }  
  
                // Android 3.0  
                public void openFileChooser(ValueCallback<Uri> uploadFile)  
                {  
                    // Toast.makeText(WebviewActivity.this, "上传文件/图片",Toast.LENGTH_SHORT).show();  
                    mUploadFile = uploadFile;  
                    startActivityForResult(Intent.createChooser(createCameraIntent(), "Image Browser"), REQUEST_UPLOAD_FILE_CODE);  
                }  
            });  
        }  
    }  
  
    private Intent createCameraIntent()  
    {  
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//拍照  
        //=======================================================  
        Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT);//选择图片文件  
        imageIntent.setType("image/*");  
        //=======================================================  
        return cameraIntent;  
    }  
    //最后在OnActivityResult中接受返回的结果  
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  
    {  
        if (requestCode == REQUEST_UPLOAD_FILE_CODE && resultCode == RESULT_OK)  
        {  
            if (null == mUploadFile)  
            {  
                return;  
            }  
            Uri result = (null == data) ? null : data.getData();  
            if (null != result)  
            {  
                ContentResolver resolver = this.getContentResolver();  
                String[] columns = { MediaStore.Images.Media.DATA };  
                Cursor cursor = resolver.query(result, columns, null, null, null);  
                cursor.moveToFirst();  
                int columnIndex = cursor.getColumnIndex(columns[0]);  
                String imgPath = cursor.getString(columnIndex);  
                System.out.println("imgPath = " + imgPath);  
                if (null == imgPath)  
                {  
                    return;  
                }  
                File file = new File(imgPath);  
                   //将图片处理成大小符合要求的文件  
                result = Uri.fromFile(handleFile(file));  
                mUploadFile.onReceiveValue(result);  
                mUploadFile = null;       
            }  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }  
    /**处理拍照/选择的文件*/  
    private File handleFile(File file)  
    {  
        DisplayMetrics dMetrics = getResources().getDisplayMetrics();  
        Options options = new Options();
        options.inJustDecodeBounds = true;  
         BitmapFactory.decodeFile(file.getAbsolutePath(), options);  
        int imageWidth = options.outWidth;  
        int imageHeight = options.outHeight;  
        System.out.println("  imageWidth = " + imageWidth + " imageHeight = " + imageHeight);  
        int widthSample = (int) (imageWidth / (dMetrics.density * 90));  
        int heightSample = (int) (imageHeight / (dMetrics.density * 90));  
        System.out.println("widthSample = " + widthSample + " heightSample = " + heightSample);  
        options.inSampleSize = widthSample < heightSample ? heightSample : widthSample;  
        options.inJustDecodeBounds = false;  
        Bitmap newBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);  
        System.out.println("newBitmap.size = " + newBitmap.getRowBytes() * newBitmap.getHeight());  
        File handleFile = new File(file.getParentFile(), "upload.png");  
        try  
        {  
            if (newBitmap.compress(CompressFormat.PNG, 50, new FileOutputStream(handleFile)))  
            {  
                System.out.println("保存图片成功");  
            }  
        }  
        catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
  
        return handleFile;  
  
    }

	public class JsInteration {
		private Context mContext;

		public JsInteration(Context context) {
			this.mContext = context;
		}

		/**
		 * 在js中调用window.android.toUserCenter(json)，便会触发此方法。
		 * 此方法名称一定要和js中showInfoFromJava方法一样
		 *
		 * @param json
		 */
		@JavascriptInterface
		public void toUserCenter(String json) {
			String userid = JSON.parseObject(json).getString("userid");
			Intent intent = new Intent();
			intent.setClass(context, CelebrityActivity.class);
			intent.putExtra("id",userid);
			context.startActivity(intent);
		}
	}
}
