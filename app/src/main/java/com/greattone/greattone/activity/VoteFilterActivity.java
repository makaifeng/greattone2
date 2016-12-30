package com.greattone.greattone.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.greattone.greattone.R;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.NormalPopuWindow;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.HaiXuanFilter;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;


public class VoteFilterActivity extends BaseActivity {
	private TextView tv_sign_up1;
	private TextView tv_sign_up2;
	protected HaiXuanFilter haiXuanFilter;
List<String> groupList1=new ArrayList<String>();
List<String> groupList2=new ArrayList<String>();
String group1,group2;
private Button btn_sign_in;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_filter);
		initView();
	}

	private void initView() {
		setHead("选择组别", true, true);
		tv_sign_up1=(TextView)findViewById(R.id.tv_sign_up1);
		tv_sign_up2=(TextView)findViewById(R.id.tv_sign_up2);
		btn_sign_in=(Button)findViewById(R.id.btn_sign_in);
		tv_sign_up1.setOnClickListener(lis);
		tv_sign_up2.setOnClickListener(lis);
		btn_sign_in.setOnClickListener(lis);
		getGroup();
	}
	OnClickListener lis=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v==tv_sign_up1) {
				showPopWindow(tv_sign_up1,groupList1,1);
			}else	if (v==tv_sign_up2) {
				if (!TextUtils.isEmpty(group1)) {
					showPopWindow(tv_sign_up2,groupList2,2);
				}else{
					toast("请选择分组1");
				}
			}else	if (v==btn_sign_in) {
				setResult(RESULT_OK, getIntent().putExtra("filter1", group1).putExtra("filter2", group2));
				finish();
			}
		}
	};
	private TreeMap<String, List<String>> map;

	
	/**获取分组*/
private void getGroup() {
	MyProgressDialog.show(context);
	HashMap<String, String> localHashMap = new HashMap<String, String>();
	localHashMap.put("api", "extend/haixuanType");
	localHashMap.put("titleid", getIntent().getStringExtra("id"));
	addRequest(HttpUtil.httpConnectionByPost(context, localHashMap,
			new ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					MyProgressDialog.Cancel();
					try {
						haiXuanFilter = JSON.parseObject(
							message.getData(), HaiXuanFilter.class);
					} catch (JSONException e) {
					}
					initGroups();
				}

			}, null));
		
	}
private void initGroups() {
	if(haiXuanFilter.getGroup()!=null&&haiXuanFilter.getGroup().startsWith("{")){
		try {
			org.json.JSONObject jsonObject2=new org.json.JSONObject(haiXuanFilter.getGroup());
			Iterator<String> iterator =	jsonObject2.keys();
			map=new TreeMap<>();
			while (iterator.hasNext()) { // 遍历每个key
				String key = (String) iterator.next();
				iterator.toString();				groupList1.add(key);
				map.put(key,JSON.parseArray( jsonObject2.getString(key),String.class));
			}
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}
//		JSONObject jsonobject = JSON.parseObject(haiXuanFilter.getGroup());
//	 Set<String> set =jsonobject.;
//	 groupList1=new ArrayList<String>(set);
//	 map=new TreeMap<String, List<String>>();
//	 for (String string : set) {
//			map.put(string,JSON.parseArray( jsonobject.getString(string),String.class));
//		}
	}
}
	protected void showPopWindow( TextView textview, List<String> mlist,final int type) {
		final TextView textview2= textview;
		NormalPopuWindow popuWindow=new NormalPopuWindow(context, mlist, textview);
		popuWindow.setOnItemClickBack(new OnItemClickBack() {
			
			@Override
			public void OnClick(int position, String text) {
				textview2.setText(text);
				if (type==1) {
				group1=text;
				groupList2=map.get(group1);
				}else{
					group2=text;
				}
			}
		});
		popuWindow.show();
	}
	
}
