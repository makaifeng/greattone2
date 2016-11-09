package com.greattone.greattone.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.greattone.greattone.R;
/**性别选择*/
public class SelectorDialog extends Dialog {
	private Context context;
	private SexDismissListener sexDismissListener;

	public SelectorDialog(Context context,
			SexDismissListener paramSexDismissListener) {
		super(context, R.style.ActionSheetDialogStyle1);
		this.sexDismissListener = paramSexDismissListener;
		this.context = context;
	}

	@SuppressLint("InflateParams")
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		View localView1 = LayoutInflater.from(this.context).inflate(
				R.layout.dialog_sex, null, false);
		View localView2 = localView1.findViewById(R.id.dialog_sex_man);
		View localView3 = localView1.findViewById(R.id.dialog_sex_woman);
		localView2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SelectorDialog.this.sexDismissListener.finish("男");
				SelectorDialog.this.dismiss();
			}
		});
		localView3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				SelectorDialog.this.sexDismissListener.finish("女");
				SelectorDialog.this.dismiss();
			}
		});
		setContentView(localView1);
		getWindow().getAttributes().width = context.getResources()
				.getDisplayMetrics().widthPixels;
	}

	public void setLocation(boolean paramBoolean) {
		WindowManager.LayoutParams localLayoutParams = getWindow()
				.getAttributes();
		if (paramBoolean) {
			localLayoutParams.gravity = Gravity.BOTTOM;
		} else {
			localLayoutParams.gravity = Gravity.CENTER;
		}
	}

	public static abstract interface SexDismissListener {
		public abstract void finish(String str);
	}
}