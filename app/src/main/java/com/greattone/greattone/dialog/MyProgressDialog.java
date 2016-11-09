package com.greattone.greattone.dialog;

import com.greattone.greattone.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyProgressDialog {
	Dialog dialog;
	static MyProgressDialog progressDialog;
	private TextView tipTextView;
	private LinearLayout layout;
	Context context;
	String message;
	boolean isshow;

	private MyProgressDialog(Context context, CharSequence message) {
		this.context = context;
		init();
		initDialog();
		setMessage(message);
	}

	public static MyProgressDialog show(Context context, CharSequence message) {
		if (progressDialog==null) {
			progressDialog = new MyProgressDialog(context, message);
		}else {
			if (progressDialog.isshow) {
				return progressDialog;
			}
			progressDialog.setContext(context);
			progressDialog.init();
			progressDialog.initDialog();
			progressDialog.setMessage(message);
	
		}
		show();
		return progressDialog;
	}

	private static void show() {
		if (progressDialog.dialog.isShowing()) {
			return;
		}
		progressDialog.isshow=true;
			progressDialog.dialog.show();
	}

	public static MyProgressDialog show(Context context) {
		progressDialog = show(context, "数据加载中......");
		return progressDialog;
	}

	private void initDialog() {
		dialog = new Dialog(context, R.style.loading_dialog);
		dialog.setCancelable(true);// 不可以用“返回键”取消
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
	}

	@SuppressLint("InflateParams")
	private void init() {
		View v = LayoutInflater.from(context).inflate(R.layout.loading_dialog,
				null);// 得到加载view
		layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);

	}

	void setContext(Context  context) {
		this.context=context;
	}
	void setMessage(CharSequence message) {
		tipTextView.setText(message);// 设置加载信息
	}

	public static void Dismiss() {
		if (progressDialog != null) {
			if (progressDialog.dialog != null) {
				progressDialog.isshow=false;
				progressDialog.dialog.dismiss();
			}
		}
	}

	public static void Cancel() {Dismiss();}

	public static void Cancel(int num, int all) {
		if (num >= all) {
			Cancel();
		}
	}
}
