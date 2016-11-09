package com.greattone.greattone.widget;

import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.Textutil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class MyRadioButton extends RadioButton {

	public MyRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

//	public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr,
//			int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//	}
	@Override
	public void setText(CharSequence text, BufferType type) {
		if (LanguageUtil.getLanguage().equals("TW")) {
			text=Textutil.Sim2Tra(text);
		}
		super.setText(text, type);
	}
	
	
	@Override
	public CharSequence getText() {
		if (LanguageUtil.getLanguage().equals("TW")) {
			return Textutil.Tra2Sim(super.getText());
		}
		return super.getText();
	}
}
