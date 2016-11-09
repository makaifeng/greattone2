package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
/**回复*/
public class ReplayDialog extends Dialog {
	private View view;
	private EditText edit;
	private TextView send;
	private OnReplayListener listener;

	@SuppressLint("InflateParams")
	public ReplayDialog(Context paramContext) {
		super(paramContext, R.style.ActionSheetDialogStyle1);
		view = getLayoutInflater().inflate(R.layout.replay_dialog, null);
		edit = (EditText) this.view.findViewById(R.id.replay_dialog_edit);
		send = (TextView) this.view.findViewById(R.id.replay_dialog_send);
		this.send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (ReplayDialog.this.edit.getText().toString().equals("")) {
					((BaseActivity) getContext()).toast("请输入内容");
					return;
				}
				ReplayDialog.this.listener.OnReplay(ReplayDialog.this.edit
						.getText().toString());
				edit.setText("");
			dismiss();
			}
		});
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		setContentView(this.view);
		Window window = getWindow();
		WindowManager.LayoutParams layoutParams = window
				.getAttributes();
		layoutParams.width =LayoutParams.MATCH_PARENT;
		layoutParams.alpha = 1.0F;
		window.setAttributes(layoutParams);
		window.setGravity(Gravity.BOTTOM);
	}

	public void setContentHint(String paramString) {
		if (this.edit != null)
			this.edit.setHint(paramString);
	}

	public void setListener(OnReplayListener paramOnReplayListener) {
		this.listener = paramOnReplayListener;
	}

	public void setSendText(String paramString) {
		if (this.send != null)
			this.send.setText(paramString);
	}

	public void setText(String paramString) {
		if (this.edit != null)
		this.edit.setText(paramString);
	}

	public static abstract interface OnReplayListener {
		public abstract void OnReplay(String paramString);
	}
}