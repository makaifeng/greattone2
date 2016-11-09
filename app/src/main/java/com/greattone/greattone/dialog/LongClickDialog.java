package com.greattone.greattone.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.greattone.greattone.R;

public class LongClickDialog extends Dialog implements
		View.OnClickListener {

	Context context;
	View.OnClickListener onClickListener;
	public LongClickDialog(Context context) {
		super(context, R.style.MyDialogStyle);
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView=new TextView(context);
		textView.setBackgroundResource(R.drawable.delect_item);
		textView.setTextColor(context.getResources().getColor(R.color.white));
		textView.setText("删除");
		setContentView(textView);
		textView.setOnClickListener(this);
	}
	
	// 直接在这里面写的话就必须要将那些参数传进来，增加这个自定义控件的负担
	@Override
	public void onClick(View v) {
		if (onClickListener!=null) {
			onClickListener.onClick(v);
		}
	}

public void setOnClickListener(	View.OnClickListener onClickListener){
	this.onClickListener=onClickListener;
}
}
