package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.entity.PersonList;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyTescherListAdapter extends BaseAdapter {
	private Context context;
	private List<PersonList> list;
	OnBtnItemClickListener itemClickListener;
	String type;

	public MyTescherListAdapter(Context context, List<PersonList> list,
			String type) {
		this.context = context;
		this.list = list;
		this.type = type;
	}

	public void setList(List<PersonList> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_my_teacher, group, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.btn1 = (TextView) convertView.findViewById(R.id.tv_focus);//
			holder.btn2 = (TextView) convertView.findViewById(R.id.tv_private);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView btn2;
		TextView btn1;
		TextView name;
		ImageView icon;

		public void setPosition(final int position) {
			name.setText(list.get(position).getUsername());
			ImageLoaderUtil.getInstance().setImagebyurl(
					list.get(position).getUserpic(), icon);
			setButtonText(list.get(position).getStatusinfo());
			btn1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (itemClickListener != null) {
						itemClickListener.onItemClick(v, position);
					}
				}
			});
			btn2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 私信
					Intent intent = new Intent(context, MyChatActivity.class);
					intent.putExtra("name", list.get(position).getUsername());
					intent.putExtra("image", list.get(position).getUserpic());
					context.startActivity(intent );
				}
			});
		}

		private void setButtonText(String statusinfo) {
			btn2.setText("私信");
			if (statusinfo.endsWith("申请加入")) {
				if (type.equals("申请")) {
					btn1.setText("申请");
				} else {
					btn1.setText("邀请");
				}
			} else if (statusinfo.endsWith("删除")) {
				btn1.setText("删除");
				btn2.setText("私信");
			} else if (statusinfo.startsWith("同意")) {
				btn1.setText("同意");
				btn2.setText("私信");
			} else if (statusinfo.endsWith("取消申请")) {
				btn1.setText(statusinfo);
			} else if (statusinfo.endsWith("取消邀请")) {
				btn1.setText(statusinfo);
				// if ( type.equals("取消申请")) {
				// }else {
				// btn1.setText("取消邀请");
				// }
				btn2.setText("私信");
			}
		}
	}

	/** 点击事件 */
	public void setOnBtnItemClicklistener(
			OnBtnItemClickListener btnItemClickListener) {
		itemClickListener = btnItemClickListener;
	}
}
