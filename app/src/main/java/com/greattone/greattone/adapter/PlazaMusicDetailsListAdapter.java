package com.greattone.greattone.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.chat.FaceImageDeal;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.dialog.NormalPopuWindow.OnItemClickBack;
import com.greattone.greattone.entity.Comment;
import com.greattone.greattone.entity.Replys;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import java.util.List;

public class PlazaMusicDetailsListAdapter extends BaseAdapter {
	private Context context;
	private List<Comment> userList;
	OnItemClickBack clickBack;

	public PlazaMusicDetailsListAdapter(Context context, List<Comment> userList) {
		this.context = context;
		this.userList = userList;
	}

	@Override
	public int getCount() {
		return userList.size();
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
					R.layout.adapter_palza_music_details, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.name.setTextSize(12);
			holder.retext = (TextView) convertView
					.findViewById(R.id.tv_repText);//
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_content);//
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);//
			holder.replay = (TextView) convertView.findViewById(R.id.tv_replay);//
			// holder.add = (LinearLayout)
			// convertView.findViewById(R.id.ll_add);//
			holder.icon = (MyRoundImageView) convertView
					.findViewById(R.id.iv_icon);//
			holder.icon.setRadius(DisplayUtil.dip2px(context, 15));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	public void setOnItemClick(OnItemClickBack clickBack) {
		this.clickBack = clickBack;
	}

	class ViewHolder {
		/**
		 * 姓名
		 */
		TextView name;
		TextView retext;
		TextView content;
		TextView time;
		TextView replay;
		MyRoundImageView icon;
		// LinearLayout add;
		int position;

		public void setPosition(int position) {
			this.position = position;
			replay.setVisibility(View.GONE);
			ImageLoaderUtil.getInstance().setImagebyurl(
					userList.get(position).getUserpic(), icon);
			name.setText(userList.get(position).getPlusername());
			if (userList.get(position).getSaytext().startsWith("<div")) {
//				String repName=subString(userList.get(position).getSaytext(), "target=\"_blank\">", "</a>");
//				String repText=subString(userList.get(position).getSaytext(), "target=\"_blank\">", "</a>");
//				String text=subString(userList.get(position).getSaytext(), "target=\"_blank\">", "</a>");
//				retext.setText(getReplyString(repName, userList.get(position).getPlusername(), repText));
//				retext.setVisibility(View.VISIBLE);
//				content.setText(text);
//				content.setText(Html
//						.fromHtml(userList
//								.get(position)
//								.getSaytext()
//								.replace(
//										"<div",
//										"<div style=\"margin-bottom:12px;overflow-x:hidden;overflow-y:hidden;padding-bottom:3px; padding-left:3px;padding-right: 3px;padding-top: 3px;    background: rgba(255,255,238,0.8);    padding:3px;    border: solid 1px #eee;\"")
//								.replace("<span",
//										"<span style=\"float: left; color: #F96;  display: block;  width: 100%;\"")
//								.replace("<p",
//										"<p style=\"margin: 0;padding: 0;word-wrap: break-word;\"")));
				if (userList.get(position).getReplys()!=null) {
					content.setText(JSON.parseObject(userList.get(position).getReplys(), Replys.class).getSaytext());
				}
			} else {
				SpannableString ss = FaceImageDeal.changeString(context,userList.get(position).getSaytext(),true);
				content.setText(ss);
			}

			time.setText(userList.get(position).getSaytime());
			// replay.setText("回复");
			replay.setOnClickListener(lis);
			icon.setOnClickListener(lis);
		}

		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()){
					case R.id.iv_icon:
						toCenter();
						break;
					case R.id.tv_replay:
						clickBack.OnClick(position, userList.get(position).getPlid()
								+ "");
						break;
				}

			}
		};
		/** 跳转到个人中心 */
		protected void toCenter() {
			int group = userList.get(position)
					.getGroupid();
			Intent intent = new Intent();
			if (group == 1 || group == 2) {// 普通会员和名人
				intent.setClass(context, CelebrityActivity.class);
				intent.putExtra("id", userList.get(position).getId()+ "");
				intent.putExtra("groupid",userList.get(position).getGroupid());
			} else if (group == 3) {// 老师
				intent.setClass(context, TeacherActivity.class);
				intent.putExtra("id", userList.get(position).getId() + "");
			} else if (group == 4) {// 教室
				intent.setClass(context, ClassRoomActivity.class);
				intent.putExtra("id", userList.get(position).getId() + "");
			}
			context.startActivity(intent);
		}

		// /** 添加回复记录 */
		// private void addReplayTextView() {
		// add.removeAllViews();
		// for (int i = 0; i < userList.get(position).getRecomment().size();
		// i++) {
		// TextView textView = new TextView(context);
		// textView.setLayoutParams(new LayoutParams(
		// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		// textView.setText(getReplyString(userList.get(position)
		// .getRecomment().get(i).getUname(),
		// userList.get(position).getRecomment().get(i)
		// .getRuname(), userList.get(position)
		// .getRecomment().get(i).getContent()));
		// add.addView(textView);
		// }
		// }

		@SuppressWarnings("deprecation")
		public Spanned getReplyString(String person1, String person2,
				String content) {
			return Html
					.fromHtml("<font color='#68B2E7'>person1</font><font color='#616161'>回复</font><font color='#68B2E7'>person2</font><font color='#616161'>:content</font>"
							.replace("person1", person1)
							.replace("person2", person2)
							.replace("content", content));
		}
//
//		 private String subString(String text, String start, String end) {
//		 int sPosition=text.indexOf(start) ;
//		 if (sPosition==-1) {
//		 sPosition=0;
//		 }
//		 if (end.isEmpty()) {
//		 return text.substring(sPosition+ start.length());
//		 }else {
//		 int ePosition=text.indexOf(end) ;
//		 if (sPosition==-1) {
//		 ePosition=0;
//		 }
//		 return text.substring(sPosition+ start.length(),
//		 ePosition);
//		 }
//		 }
	}

}
