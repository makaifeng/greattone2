package com.greattone.greattone.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.chat.FaceImageDeal;
import com.greattone.greattone.entity.Chat;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

public class MyMessageListAdapter extends BaseAdapter {
	private Context context;
	private List<Chat> messageList;
	private List<Chat> sqlList;

	// private int screenWidth;

	public MyMessageListAdapter(Context context, List<Chat> messageList,
			List<Chat> sqlList) {
		this.context = context;
		this.messageList = messageList;
		this.sqlList = sqlList;
		// screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return messageList.size();
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
					R.layout.adapter_my_message, group, false);
			holder.ll = (LinearLayout) convertView.findViewById(R.id.ll_chat);//
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);//
			holder.num = (TextView) convertView.findViewById(R.id.tv_num);//
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_content);//
			holder.icon = (MyRoundImageView) convertView
					.findViewById(R.id.iv_icon);//
			holder.icon.setRadius(DisplayUtil.dip2px(context, 20));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		LinearLayout ll;
		TextView name;
		TextView time;
		TextView content;
		TextView num;
		MyRoundImageView icon;
		Chat chat;

		public void setPosition(int position) {
			chat = messageList.get(position);
			ImageLoaderUtil.getInstance()
					.setImagebyurl(chat.getUserpic(), icon);
			icon.setOnClickListener(clickListener);
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
			// Locale.CHINA);
			name.setText(chat.getFrom_username());
			time.setText(chat.getMsgtime());
			content.setText(FaceImageDeal.changeString(context,
					chat.getMsgtext(), true));
			num.setVisibility(View.GONE);
			for (Chat chat : sqlList) {
				if (chat.getFrom_username().endsWith(chat.getFrom_username())) {
					if (chat.getIssys() != 0) {
						num.setVisibility(View.VISIBLE);
						num.setText(chat.getIssys() + "");
					} else {
						num.setVisibility(View.GONE);
					}
					break;
				}
			}
		}
		OnClickListener clickListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, CelebrityActivity.class);
				intent.putExtra("id", chat.getFrom_userid());
				intent.putExtra("groupid", 0);
				context.startActivity(intent);
				
			}
		};
	}
}
