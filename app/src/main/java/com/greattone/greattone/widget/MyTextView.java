package com.greattone.greattone.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.Textutil;

public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

//	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr,
//			int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//	}


	@Override
	public void setText(CharSequence text, BufferType type) {
		try {
			if (LanguageUtil.getLanguage().equals("TW")) {
				if (text!=null&&text!="") {
					text=Textutil.Sim2Tra(text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setText(text, type);
	}
	@Override
	public CharSequence getText() {
		try {
			if (LanguageUtil.getLanguage().equals("TW")) {
				if (super.getText()!=null&&super.getText()!="") {
					return Textutil.Tra2Sim(super.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getText();
	}
}
