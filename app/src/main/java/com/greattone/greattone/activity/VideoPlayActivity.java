package com.greattone.greattone.activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.greattone.greattone.R;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.HttpConstants;
/**视频播放*/
public class VideoPlayActivity extends BaseActivity implements OnCompletionListener,OnErrorListener,OnPreparedListener{

    private ProgressBar progressBar;
	private VideoView videoView;
	private MediaController mediaController;
	private int intPositionWhenPause;
private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_play);
		type=getIntent().getIntExtra("type", 0);
		initVideoView();
//		if (ActivityUtil.GetNetworkType(context).equals("WIFI")) {
//		}else{
//			showMyDialog();
//		}
//		getTeacherInfo();
	}

	/** 
     *初始化videoview播放 
     */  
    public void initVideoView(){  
        //初始化进度条  
        progressBar= (ProgressBar) findViewById(R.id.progressBar);  
        //初始化VideoView  
        videoView= (VideoView) findViewById(R.id.video);  
        //初始化videoview控制条  
        mediaController=new MediaController(this);  
        //设置videoview的控制条  
        videoView.setMediaController(mediaController);  
        //设置显示控制条  
        mediaController.show(0); 
        //设置播放完成以后监听  
        videoView.setOnCompletionListener(this);  
        //设置发生错误监听，如果不设置videoview会向用户提示发生错误  
        videoView.setOnErrorListener(this);  
        //设置在视频文件在加载完毕以后的回调函数  
        videoView.setOnPreparedListener(this);  
        //设置videoView的点击监听  
//        videoView.setOnTouchListener(this);  
        String url=getUrl();
        	
        //设置网络视频路径  
//        Uri uri=Uri.parse("http://bj2-5.syscloud.tv/94Y5LM10JO/eb361gdNghVS0gdNgShhhgdNgD1gdNghVS0ShhhD1FpT1_ADWTJV.mp4?t=1450770823&m=77c8ef8507f78eed929e1d45666ac9ac");  
        Uri uri=Uri.parse(url);  
        videoView.setVideoURI(uri);  
        //设置为全屏模式播放  
//        setVideoViewLayoutParams(1);  
    }
    private String getUrl() {
    	String url=	getIntent().getStringExtra("url");
    	if (url.startsWith("/")) {
			url = new HttpConstants(Constants.isDebug).ServerUrl 
					+ url;
    	}else if ((!url.startsWith("http://")) && (!url.startsWith("file://"))
				&& (!url.startsWith("drawable://"))){
			url = new HttpConstants(Constants.isDebug).ServerUrl + "/"
					+ url;
		}
    	return url;
	}



	/**
     * 设置videiview的全屏和窗口模式
     * @param paramsType 标识 1为全屏模式 2为窗口模式
     */
    public void setVideoViewLayoutParams(int paramsType){
		//全屏模式
        if(1==paramsType) {
			//设置充满整个父布局
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			//设置相对于父布局四边对齐
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			//为VideoView添加属性
            videoView.setLayoutParams(LayoutParams);
        }else{
			//窗口模式
            //获取整个屏幕的宽高
            DisplayMetrics DisplayMetrics=new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
			//设置窗口模式距离边框50
            int videoHeight=DisplayMetrics.heightPixels-50;
            int videoWidth=DisplayMetrics.widthPixels-50;
            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth,videoHeight);
			//设置居中
            LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			//为VideoView添加属性
            videoView.setLayoutParams(LayoutParams);
        }
    }
//    /**
//     * videoView的点击监听  
//     */
//	@Override
//	public boolean onTouch(View arg0, MotionEvent arg1) {
//		return false;
//	}
	/**
	 * 播放完成以后监听  
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
		
	}
    /**
     * 视频播放发生错误时调用的回调函数
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e("text","发生未知错误");
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e("text","媒体服务器死机");
                break;
            default:
                Log.e("text","onError+"+what);
                break;
        }
        switch (extra){
            case MediaPlayer.MEDIA_ERROR_IO:
                //io读写错误
                Log.e("text","文件或网络相关的IO操作错误");
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                //文件格式不支持
                Log.e("text","比特流编码标准或文件不符合相关规范");
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                //一些操作需要太长时间来完成,通常超过3 - 5秒。
                Log.e("text","操作超时");
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                //比特流编码标准或文件符合相关规范,但媒体框架不支持该功能
                Log.e("text","比特流编码标准或文件符合相关规范,但媒体框架不支持该功能");
                break;
            default:
                Log.e("text","onError+"+extra);
                break;
        }
    //如果未指定回调函数， 或回调函数返回假，VideoView 会通知用户发生了错误。这点需要特别注意
        return false;
    }
    /**
     * 视频文件加载文成后调用的回调函数
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
     //如果文件加载成功,隐藏加载进度条
        progressBar.setVisibility(View.GONE);
        if (type==1) {
        	videoView.setBackgroundResource(R.drawable.music_play2);
        	mediaController.show();
		}
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        startVideoView();
    }
    private void startVideoView() {
        if (videoView!=null) {
        	//启动视频播放
        	videoView.start();
        	//设置获取焦点
        	videoView.setFocusable(true);
		}
	}
    /**
     * 页面暂停效果处理
     */
    @Override
    protected  void onPause() {
        super.onPause();
        if (videoView!=null) {
        	//如果当前页面暂停则保存当前播放位置，全局变量保存
        	intPositionWhenPause=videoView.getCurrentPosition();
        	System.out.println("intPositionWhenPause:"+intPositionWhenPause);
        	//停止回放视频文件
        	videoView.stopPlayback();
		}
    }

    /**
     * 页面从暂停中恢复
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (videoView!=null) {
        	//跳转到暂停时保存的位置
        	if(intPositionWhenPause>=0){
        		videoView.seekTo(intPositionWhenPause);
        		//初始播放位置
        		intPositionWhenPause=-1;
        	}
		}
    }


//	private void showMyDialog() {
//		new AlertDialog.Builder(context).setTitle("是否继续").setMessage("你不在wifi网络下，是否继续？")
//		.setPositiveButton(getResources().getString(R.string.确定),
//				new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				initVideoView();
//				startVideoView();
//			}
//		}).setNegativeButton(getResources().getString(R.string.取消),
//				new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				finish();
//			}
//		}).show();
//	}

}
