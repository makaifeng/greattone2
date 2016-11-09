package com.greattone.greattone.activity;


import android.app.Activity;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.greattone.greattone.R;

/**
 * 搜索
 * @author yff 2015-9-7
 */
public class SearchAct extends BaseActivity {

	EditText et_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setHead(getResources().getString(R.string.search), true, true);
		et_search = (EditText) findViewById(R.id.et_search);
		 findViewById(R.id.act_search_search).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 
				Intent intent = new Intent();
				intent.putExtra("data", et_search.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		et_search.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View paramView, int paramInt,
					KeyEvent paramKeyEvent) {

				if (paramInt == KeyEvent.KEYCODE_ENTER
						&& paramKeyEvent.getAction() == KeyEvent.ACTION_DOWN) {
					// InputMethodManager
					// g=(InputMethodManager)getSystemService("");
					// 先隐藏键盘service
					((InputMethodManager) context
							.getSystemService(InputMethodService.INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
									.getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					// toast("111111");
					Intent intent = new Intent();
					intent.putExtra("data", et_search.getText().toString());
					setResult(RESULT_OK, intent);
					finish();
				}

				return false;
			}

		});

//		add.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putExtra("data", et_search.getText().toString());
//				setResult(0x002, intent);
//				finish();
//			}
//		});
	}
}
