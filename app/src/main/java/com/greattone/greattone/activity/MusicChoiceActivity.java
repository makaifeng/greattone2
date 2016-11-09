package com.greattone.greattone.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.kf_test.Mp3Info;

/** 音乐选择器 */
public class MusicChoiceActivity extends BaseActivity {
	private ListView listView;
	private List<Mp3Info> list;

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
		list=	FileUtil.getMp3Infos(context);
		// 设置数据适配器
		 DataAdapter dataAdapter = new DataAdapter(this,
				 list);
		listView.setAdapter(dataAdapter);
		listView.setOnItemClickListener(listener);
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

	public class DataAdapter extends BaseAdapter {

		Context context;// 上下文
		List<Mp3Info> list= new ArrayList<Mp3Info>(); // 遍历的文件列表

		public DataAdapter(Context context, List<Mp3Info> list) {
			this.context = context;
			this.list = list;
		}

//		// 更新界面的方法
//		public void UpdateView(int id) {
//			System.out.println("is : " + list.get(id));
//
//			// 如果是音乐文件的话，就播放。如果不是就提示不是音乐文件。如果是文件夹的话就在进一步
//			File choiceFile = new File(list.get(id));
//
//			if (choiceFile.isDirectory()) {
//				// 如果是文件夹的话就进步深入
//				list.removeAll(list);
//				System.out.println("size=" + list.size());
//				// 重新载入
//				File files[] = choiceFile.listFiles();
//				for (int i = 0; i < files.length; i++) {
//					// 加载到数据集中
//					list.add(files[i].getAbsolutePath());
//				}
//				System.out.println("size=" + list.size());
//			} else {
//				// 是其他的文件
//				String fileNmae = choiceFile.getName();
//				int len = fileNmae.lastIndexOf(".");
//				String hz = fileNmae.substring(len + 1);
//				System.out.println("hz = " + hz);
//				for (int i = 0; i < musicType.length; i++) {
//					if (hz.equals(musicType[i])) {
//						// 是音乐文件
//						MediaPlayer player = new MediaPlayer();
//						try {
//							player.setDataSource(choiceFile.getAbsolutePath());
//							player.prepare();
//							player.start();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						return;
//					}// if
//				}// for
//				System.out.println("这个不是音乐文件！");
//			}
//		}

		public void  setList(List<Mp3Info> list) {
		this.list=list;
		notifyDataSetChanged();
		}
		public int getCount() {
			return list.size();
		}

		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			TextView textView = new TextView(context);
			textView.setText(list.get(arg0).getUrl());
			textView.setSingleLine();
			textView.setEllipsize(TruncateAt.START);
			textView.setPadding(DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context, 10));
			return textView;
		}

	}
}
