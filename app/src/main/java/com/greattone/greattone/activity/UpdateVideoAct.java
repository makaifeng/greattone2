package com.greattone.greattone.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.UpdateFileListener;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.receiver.MyReceiver;
import com.greattone.greattone.service.PostVideoService;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.FileUtil;

public class UpdateVideoAct extends BaseActivity {
	ImageView iv_title;
	TextView tv_title;
	TextView tv_content;
	private String updatePath;
	MyReceiver receiver;
	private ProgressBar progressbar;
//	private String classid;
//	private String videoid;
//	private String id;
	private Button btn_update;
	private TextView tv_state;
	int isSee;
	private OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startUpdateService();
			finish();
		}
	};
	private TextView tv_size;
	private RelativeLayout rl_all;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   
		setContentView(R.layout.activity_update_video);
		isSee=getIntent().getIntExtra("isSee", 0);
		initview();
		initViewData();
		receiver=new MyReceiver(context, updateFileListener);
	}
	private void initview() {
		tv_title=(TextView) findViewById(R.id.tv_title);
		iv_title=(ImageView) findViewById(R.id.iv_title);
		tv_content=(TextView) findViewById(R.id.tv_content);
		progressbar=(ProgressBar) findViewById(R.id.progressBar1);
		btn_update=(Button) findViewById(R.id.btn_update);
		tv_state=(TextView) findViewById(R.id.tv_state);
		tv_size=(TextView) findViewById(R.id.tv_size);
		rl_all=(RelativeLayout) findViewById(R.id.rl_all);
	}
	private void initViewData() {
		setHead("等待上传", true, true);
		int updateState=preferences.getInt("updateState", PostVideoService.Flag_Init);
		String title = "";
		String content;
			if ( preferences.getString("updateClassid", "11").equals("11")) {//广场发帖
				 title=preferences.getString("updateTitle", "");
			}else 	if ( preferences.getString("updateClassid", "11").equals("73")){//海选报名
				title=preferences.getString("updateTitle", "")+"的报名";
			}else{
				title=preferences.getString("updateTitle", "");
			}
			content=preferences.getString("updateContent", "");
		 updatePath=preferences.getString("updatePath", "");
		 if (isSee==1) {//上传跳转过来的
			 startUpdateService( );
		 }
		 if (updateState==PostVideoService.Flag_Init||updateState==PostVideoService.Flag_Update) {//没开始或上传中
			 rl_all.setVisibility(View.VISIBLE);
			 Bitmap bitmap=BitmapUtil.getVideoThumbnail(updatePath, 300, 300, Thumbnails.MINI_KIND);
			 tv_title.setText(title);
			 tv_content.setText(content);
			 iv_title.setImageBitmap(bitmap);
		}else if (updateState==PostVideoService.Flag_Done) {//完成
		}else if (updateState==PostVideoService.Flag_False) {//失败
			 rl_all.setVisibility(View.VISIBLE);
			btn_update.setVisibility(View.VISIBLE);
			btn_update.setText("再次上传");
			btn_update.setOnClickListener(lis);
			tv_state.setVisibility(View.VISIBLE);
			tv_state.setText("上传失败");
		}
//		updateSuccess1();
	}
	/**
	 * 上传文件
	 * @param videoid
	 * @param videoUrl
	 */
	void startUpdateService() {
		preferences.edit().putInt("updateState",PostVideoService.Flag_Update).commit();
		 Intent it = new Intent(context,PostVideoService.class);
		it.putExtra("videoPath",updatePath);
		startService(it);

	}
	UpdateFileListener updateFileListener =new UpdateFileListener() {
		

		@Override
		public void updateError() {
		toast("上传失败");
		 rl_all.setVisibility(View.VISIBLE);
		btn_update.setVisibility(View.VISIBLE);
		btn_update.setText("再次上传");
		btn_update.setOnClickListener(lis);
		tv_state.setVisibility(View.VISIBLE);
		tv_state.setText("上传失败");
		}

		@Override
		public void onProgressUpdate(long uploadedSize, long totalSize) {
			tv_size.setVisibility(View.VISIBLE);
			tv_size.setText(FileUtil.getFormatSize(uploadedSize)+"/"+FileUtil.getFormatSize(totalSize));
			progressbar.setProgress((int)(( uploadedSize/ (float) totalSize) *1000));
			progressbar.setMax(1000);
			
		}


		@Override
		public void updateSuccess(Message2 message) {
			finish();
		}
	};
	

	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyReceiver.ACTION_UPDATE_FAIL);
		filter.addAction(MyReceiver.ACTION_UPDATE_PROGRESS);
		filter.addAction(MyReceiver.ACTION_UPDATE_SUCCESS);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}
