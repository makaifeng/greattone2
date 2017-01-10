package com.greattone.greattone.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.greattone.greattone.R;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.FileUtil;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class MediaRecorderActivity extends BaseActivity implements Callback {
	private SurfaceView surfaceView;
	private Camera mCamera;
	private SurfaceHolder sh;
	private TextView textview;
	private Button button_start, button_stop;
	// private Button change_Button;
	private int hou, min, sec;
	private int cameraId;
	private Handler handler;
	private android.view.ViewGroup.LayoutParams lp;
	private File storageDir;
	private File tempFile;
	private MediaRecorder mediaRecorder;
	boolean isStart = true;
	private ImageView iv_bg;
	private TextView button_save;
	private TextView button_cancel;
	private TextView tv_cancle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 窗口去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_media_recorder_demo);


		textview = (TextView) findViewById(R.id.textView_time);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		// change_Button = (Button) findViewById(R.id.change);
		lp = surfaceView.getLayoutParams();
		sh = surfaceView.getHolder();
		sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		sh.addCallback(this);

		handler = new Handler();
		iv_bg = (ImageView) findViewById(R.id.iv_bg);
		button_save = (TextView) findViewById(R.id.button_save);
		button_save.setOnClickListener(saveOnCliclListener);
		button_cancel = (TextView) findViewById(R.id.button_cancel);
		button_cancel.setOnClickListener(cancelOnCliclListener);
		button_start = (Button) findViewById(R.id.button_start);
		button_start.setOnClickListener(startOnCliclListener);
		button_stop = (Button) findViewById(R.id.button_stop);
		button_stop.setOnClickListener(stopOnCliclListener);
		tv_cancle = (TextView) findViewById(R.id.tv_cancle);
		tv_cancle.setOnClickListener(backOnCliclListener);
		// change_Button.setOnClickListener(changOnCliclListener);
		// 判断手机存储卡是否存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			storageDir=getExternalFilesDir(Environment.DIRECTORY_DCIM + File.separator + "Video" + File.separator);
//			storageDir = new File(Environment.getExternalStorageDirectory()
//					.toString() + File.separator + "Video" + File.separator);
			if (!storageDir.exists()) {
				storageDir.mkdir();
			}
			button_stop.setVisibility(View.GONE);
		} else {
			button_start.setVisibility(View.GONE);
			Toast.makeText(MediaRecorderActivity.this, "SDcard is not exist",
					Toast.LENGTH_LONG).show();
		}
	}
	// OnClickListener changOnCliclListener=new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	//
	// mCamera.stopPreview();
	// mCamera.release();
	// if (cameraId == 0) {
	// cameraId = 1;
	// } else {
	// cameraId = 0;
	// }
	// OpenCameraAndSetSurfaceviewSize(cameraId);
	// // the surfaceview is ready after the first launch
	// SetAndStartPreview(sh);
	//
	// }
	//
	// };
	OnClickListener startOnCliclListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			button_start.setVisibility(View.GONE);
			button_stop.setVisibility(View.VISIBLE);
			tv_cancle.setVisibility(View.GONE);
			hou = 0;
			min = 0;
			sec = 0;
			handler.postDelayed(refreshTime, 1000);
			int width = 800;
			int height = 480;
			isStart = true;
			try {
				mCamera.unlock();
				tempFile = File.createTempFile("video", ".mp4", storageDir);
				
				
				mediaRecorder = new MediaRecorder();
				mediaRecorder.reset();
				mediaRecorder.setCamera(mCamera);
				mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
				mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//				mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				 mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//				 mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);

				mediaRecorder.setVideoSize(width, height);
				mediaRecorder.setVideoFrameRate(16);
				mediaRecorder.setOrientationHint(90);
				mediaRecorder.setPreviewDisplay(sh.getSurface());
				mediaRecorder.setOutputFile(tempFile.getAbsolutePath());
				mediaRecorder.setOnInfoListener(onInfoListener);
				mediaRecorder.setOnErrorListener(onErrorListener);
				mediaRecorder.prepare();
				mediaRecorder.start();
			} catch (IllegalStateException e) {
				isStart = false;
				e.printStackTrace();
			} catch (IOException e) {
				isStart = false;
				e.printStackTrace();
			}
		}
	};
	OnInfoListener onInfoListener=new OnInfoListener() {
		
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			
		}
	};
	OnErrorListener onErrorListener=new OnErrorListener() {
		
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			kill_camera();
			kill_recorder();
		}
	};
	OnClickListener stopOnCliclListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			button_stop.setVisibility(View.GONE);
			button_start.setVisibility(View.GONE);
			tv_cancle.setVisibility(View.GONE);
			button_save.setVisibility(View.VISIBLE);
			button_cancel.setVisibility(View.VISIBLE);
			iv_bg.setVisibility(View.VISIBLE);
			handler.removeCallbacks(refreshTime);
			kill_recorder();
			if (isStart) {
				iv_bg.setImageBitmap(BitmapUtil.getVideoThumbnail(
						tempFile.getAbsolutePath(),500,500));
			}
		}
	};
	OnClickListener saveOnCliclListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isStart) {
				Intent intent=new Intent();
				intent.putExtra("data", 	tempFile.getAbsolutePath());
				setResult(RESULT_OK, intent);
			}
			finish();
		}
	};
	OnClickListener cancelOnCliclListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			button_save.setVisibility(View.GONE);
			button_cancel.setVisibility(View.GONE);
			iv_bg.setVisibility(View.GONE);
			tv_cancle.setVisibility(View.VISIBLE);
			button_start.setVisibility(View.VISIBLE);
			textview.setText("00:00:00");
			FileUtil.RecursionDeleteFile(tempFile);//移除文件
		}
	};
	OnClickListener backOnCliclListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	private Runnable refreshTime = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			sec++;
			handler.postDelayed(refreshTime, 1000);
			if (sec >= 60) {
				sec = sec % 60;
				min++;
			}
			if (min >= 60) {
				min = min % 60;
				hou++;
			}
			textview.setText(timeFormat(hou) + ":" + timeFormat(min) + ":"
					+ timeFormat(sec));
		}
	};

	private String timeFormat(int t) {
		if (t / 10 == 0) {
			return "0" + t;
		} else {
			return t + "";
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		cameraId = 0;// default id
		OpenCameraAndSetSurfaceviewSize(cameraId);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		kill_recorder();
		kill_camera();
	}

	@Override
	protected void onDestroy() {
		kill_camera();
		kill_recorder();
		super.onDestroy();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		SetAndStartPreview(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		kill_recorder();
		kill_camera();
	}

	private Void SetAndStartPreview(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.setDisplayOrientation(90);
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Void OpenCameraAndSetSurfaceviewSize(int cameraId) {
		mCamera = Camera.open(cameraId);
		Camera.Parameters parameters = mCamera.getParameters();
		Size pre_size = parameters.getPreviewSize();
//		Size pic_size = parameters.getPictureSize();
		Camera.CameraInfo info = new Camera.CameraInfo();
		Camera.getCameraInfo(cameraId, info);
//		int camera_number = Camera.getNumberOfCameras();
		textview.setText("00:00:00");
//		if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
//			textview.setText("There are " + camera_number + " camera."
//					+ "This is the Front Camera!");
//		} else {
//			textview.setText("There are " + camera_number + " camera."
//					+ "This is the Back Camera!");
//		}
		lp.height = pre_size.width * 2;
		lp.width = pre_size.height * 2;
		return null;
	}

	private void kill_camera() {
		if (mCamera != null) {
			// mCamera.lock();
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	private void kill_recorder() {
		if (mediaRecorder != null) {
			mediaRecorder.stop();
			mediaRecorder.release();
			mediaRecorder = null;
		}
	}
}
