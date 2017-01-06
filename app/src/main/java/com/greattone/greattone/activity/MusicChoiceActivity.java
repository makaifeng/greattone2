package com.greattone.greattone.activity;

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

import com.greattone.greattone.R;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.Permission;
import com.kf_test.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/** 音乐选择器 */
public class MusicChoiceActivity extends BaseActivity {
	private ListView listView;
	private List<Mp3Info> list;
	private DataAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		initView();

	}
	private void initView() {
		setHead(getResources().getString(R.string.select_music), true, true);//选择音乐
		// 列表框
		listView = (ListView) findViewById(R.id.lv_content);
		list=new ArrayList<>();
		// 设置数据适配器
		 dataAdapter = new DataAdapter(this,
				list);
		listView.setAdapter(dataAdapter);
		listView.setOnItemClickListener(listener);
		if (new Permission().hasPermission_READ_EXTERNAL_STORAGE(this)) {
			list = FileUtil.getMp3Infos(context);
			dataAdapter.setList(list);
		}
	}
	OnItemClickListener listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent=new Intent();
			intent.putExtra("data", list.get(position).getUrl());
			setResult(RESULT_OK, intent);
			finish();
		}
	};

//	public class DataAdapter extends Adapter<Mp3Info> {
//
//		public DataAdapter(Context context, List<Mp3Info> list) {
//			super(context, list, R.layout.adapter_music_choice);
//		}
//
//		@Override
//		public void getview(ViewHolder holder, int position, Mp3Info info) {
//			TextView tv_name= (TextView) holder.getView(R.id.tv_name);
//			tv_name.setText(list.get(position).getUrl());
//		}
//	}

	public class DataAdapter extends BaseAdapter {

		Context context;// 上下文
		List<Mp3Info> list= new ArrayList<Mp3Info>(); // 遍历的文件列表

		public DataAdapter(Context context, List<Mp3Info> list) {
			this.context = context;
			this.list = list;
		}
		public void  setList(List<Mp3Info> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
		}
		public int getCount() {
			return list.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup group) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.adapter_music_choice,null);
				holder. name =  (TextView) convertView.findViewById(R.id.tv_name);
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
				name.setText(list.get(position).getUrl());
			}
		}
	}
}
