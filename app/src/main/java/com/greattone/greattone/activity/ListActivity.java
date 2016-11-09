package com.greattone.greattone.activity;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.ClassFilter;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

/** 筛选 */
public class ListActivity extends BaseActivity {

	private ListView lv_content;
	List<ClassFilter> typeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();
		getData();
	}

	private void initView() {
		setHead(getResources().getString(R.string.filter), true, true);

		lv_content = (ListView) findViewById(R.id.lv_content);
		lv_content.setOnItemClickListener(listener);

	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent data=new Intent();
			data.putExtra("classid", typeList.get(position).getClassid());
			setResult(RESULT_OK,	 data);
			finish();
		}
	};

	/***/
	protected void getData() {
		MyProgressDialog.show(context);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("api", "info/hxclass");
		map.put("bclassid", "6");
		map.put("loginuid", Data.user.getUserid());
		map.put("logintoken", Data.user.getToken());
		addRequest(HttpUtil.httpConnectionByPost(context, map,
				new ResponseListener() {

					@Override
					public void setResponseHandle(Message2 message) {
						if (message.getData() != null
								&& message.getData().startsWith("[")) {
							typeList = JSON.parseArray(message.getData(),
									ClassFilter.class);
							if (typeList.size() == 0) {
								toast(getResources().getString(R.string.cannot_load_more));
							}
							lv_content.setAdapter(new MyAdapter(context, typeList));
						}
						MyProgressDialog.Cancel();
					}
				}, null));
	}

	private class MyAdapter extends BaseAdapter {
		Context context;
		List<ClassFilter> typeList;

		public MyAdapter(Context context, List<ClassFilter> typeList) {
			this.context = context;
			this.typeList = typeList;
		}

		@Override
		public int getCount() {
			return typeList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						android.R.layout.simple_list_item_1, parent, false);
				holder.name = (TextView) convertView.findViewById(android.R.id.text1);//
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.setPosition(position);
			return convertView;
		}
		class ViewHolder {
			TextView name;

			public void setPosition(int position) {
				name.setText(typeList.get(position).getClassname());
			}
		}
	}

}
