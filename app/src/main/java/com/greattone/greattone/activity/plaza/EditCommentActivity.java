package com.greattone.greattone.activity.plaza;

import java.util.LinkedHashMap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
/**我要评论*/
public class EditCommentActivity extends BaseActivity {
	String type;
	public TextView tv_head_other;
	private EditText et_content;
	private Button btn_ok;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_edit_comment);
	initView();
}
private void initView() {
	setHead(getResources().getString(R.string.我要评论), true, true);
	et_content = (EditText)findViewById(R.id.et_content);
	btn_ok = (Button)findViewById(R.id.btn_ok);
	btn_ok.setOnClickListener(lis);
}
OnClickListener lis=new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String content=et_content.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			toast(getResources().getString(R.string.请输入评论内容));
			return;
		}
//		Intent data=new Intent();
//		data.putExtra("content", content);
//		setResult(RESULT_OK, data);
//		finish();
		
		postComment(content);
	}
};
/**发布评论
 * @param content */
protected void postComment(String content) {
	MyProgressDialog.show(context);
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	map.put("api", "comment/post");
	map.put("classid", getIntent().getStringExtra("classid"));
	map.put("id",getIntent().getStringExtra("id"));
	map.put("saytext", content);
	map.put("loginuid", Data.user.getUserid());
	map.put("logintoken", Data.user.getToken());
	addRequest(HttpUtil.httpConnectionByPost(context, map,
			new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					toast(message.getInfo());
					MyProgressDialog.Cancel();
					setResult(RESULT_OK);
					finish();
				}
			}, null));
}
}
