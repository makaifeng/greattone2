package com.greattone.greattone.activity.discuss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

public class DiscussPostAct extends BaseActivity {

	private EditText m_title, m_content;
	private View
//	type_ll, 
	cate_ll;
	private TextView 
	m_cate;
//	m_type;
//	private String cid, cname, type;
	// private List<BbsCate> bbscatelist;
	private int classid = 0, mid = 1;
	private Button btn_commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_post);
		initView();
	}

	private void initView() {
		setHead(getResources().getString(R.string.发布帖子), true, true);//发布帖子
		m_title = (EditText) findViewById(R.id.activity_discuss_post_title);
//		type_ll = findViewById(R.id.activity_discuss_post_type_ll);
//		m_type = (TextView) findViewById(R.id.activity_discuss_post_type);
		cate_ll = findViewById(R.id.activity_discuss_post_cate_ll);
		cate_ll.setOnClickListener(lis);
		m_cate = (TextView) findViewById(R.id.activity_discuss_post_cate);
		m_content = (EditText) findViewById(R.id.activity_discuss_post_content);
		m_content = (EditText) findViewById(R.id.activity_discuss_post_content);
		btn_commit = (Button) findViewById(R.id.btn_commit);
		btn_commit.setOnClickListener(lis);
	}

	OnClickListener lis = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_commit:
				commit();
				break;
			// case R.id.activity_discuss_post_cate_ll:
			// Intent intent=new Intent(self,PostDiscussCateAct.class);
			// intent.putExtra("cate", (Serializable)bbscatelist);
			// startActivityForResult(intent,11);
			// break;
			case R.id.activity_discuss_post_cate_ll:
				ShowType();
				break;
			}
		}
	};
	private String filepass;

	private void commit() {
		String title = m_title.getText().toString().trim();
		String content = m_content.getText().toString().trim();
		filepass = System.currentTimeMillis() + "";
		if (TextUtils.isEmpty(title)) {
			toast(getResources().getString(R.string.请填写主题));
			return;
		}

		if (classid == 0) {
			toast(getResources().getString(R.string.请选择分类));//请选择分类
			return;
		}
		if (TextUtils.isEmpty(content)) {
			toast(getResources().getString(R.string.请填写内容));//请填写内容
			return;
		}
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "post/ecms");
		map.put("enews", "MAddInfo");
		map.put("filepass", filepass);
		map.put("mid", mid + "");
		map.put("title", title);
		map.put("newstext", content);
		map.put("classid", classid + "");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						toast(message.getInfo());
						finish();
						setResult(RESULT_OK);
						MyProgressDialog.Cancel();
					}
				}, null));
	}
//
//	@Override
//	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(arg0, arg1, arg2);
//		if (arg2 != null) {
//			cid = arg2.getStringExtra("id");
//			cname = arg2.getStringExtra("name");
//			m_cate.setText(cname);
//		}
//	}

	protected void ShowType() {
		List<String> mlist = new ArrayList<String>();
		String name[]=getResources().getStringArray(R.array.discussion_area_type);
		final int classids[]=getResources().getIntArray(R.array.discussion_area_classids);
	for (String string : name) {
		mlist.add(string);
	}
		NormalPopuWindow popu = new NormalPopuWindow(context, mlist,
				cate_ll);
		popu.setOnItemClickBack(new OnItemClickBack() {

			@Override
			public void OnClick(int position, String text) {
				m_cate.setText(text);
				classid=		classids[position];
			}
		});
		popu.show();
	}
}
