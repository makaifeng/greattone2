package com.greattone.greattone.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.Textutil;

public class MyButton extends Button {

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	public void setText(CharSequence text, BufferType type) {
		if (LanguageUtil.getLanguage().equals("TW")) {
			if (text!=null&&text!="") {
			text=Textutil.Sim2Tra(text);
			}
		}
		super.setText(text, type);
	}
	
	@Override
	public CharSequence getText() {
		if (LanguageUtil.getLanguage().equals("TW")) {
			if (super.getText()!=null&&super.getText()!="") {
			return Textutil.Tra2Sim(super.getText());
			}
		}
		return super.getText();
	}
}
