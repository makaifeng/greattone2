package com.greattone.greattone.widget;

import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.Textutil;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditText extends EditText {

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		if (LanguageUtil.getLanguage().equals("TW")) {
			if (getHint()!=null&&getHint()!="") {
				setHint(Textutil.Sim2Tra(getHint()));
			}
		}
	}

	public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
//		if (LanguageUtil.getLanguage().equals("TW")) {
//			text=Textutil.Sim2Tra(text);
//		}
		super.setText(text, type);
	}

	@Override
	public Editable getText() {
//		if (LanguageUtil.getLanguage().equals("TW")) {
//			return Textutil.Tra2Sim(super.getText());
//		}
		return super.getText();
	}
	}
