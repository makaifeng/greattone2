package com.greattone.greattone.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.greattone.greattone.util.LanguageUtil;
import com.greattone.greattone.util.Textutil;


public class MyEditText extends android.support.v7.widget.AppCompatEditText {

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
		addTextChangedListener(mWatcher);
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

	TextWatcher mWatcher=new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			int index = getSelectionStart() - 1;
			if (index > 0) {
				if (isEmojiCharacter(s.charAt(index))) {
					Editable edit = getText();
					edit.delete(index, index + 1);
				}
			}
		}
	} ;
	private static boolean isEmojiCharacter(char codePoint) {
	       return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF))|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	    }
}
