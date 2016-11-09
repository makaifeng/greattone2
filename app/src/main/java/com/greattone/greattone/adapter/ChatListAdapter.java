package com.greattone.greattone.adapter;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.chat.FaceImageDeal;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.entity.Chat;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.TimeUtil;
import com.greattone.greattone.widget.MyRoundImageView;

public class ChatListAdapter extends BaseAdapter {
	private Context context;
	private List<Chat> chatList;
//	private int screenWidth;
//	private AnimationDrawable animationDrawable;
	private long lastTime;

	public ChatListAdapter(Context context, List<Chat> chatList) {
		this.context = context;
		this.chatList = chatList;
//		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return chatList.size();
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
					R.layout.adapter_chat, group, false);
			holder.youHead = ((MyRoundImageView) convertView
					.findViewById(R.id.you_talk_head));
			holder.myImage = ((ImageView) convertView
					.findViewById(R.id.my_talk_pic));
			holder.youImage = ((ImageView) convertView
					.findViewById(R.id.iv_talk_he_image));
			holder.myHead = ((MyRoundImageView) convertView
					.findViewById(R.id.my_talk_head));
			holder.youMsg = ((TextView) convertView
					.findViewById(R.id.you_talk_text));
			holder.myMsg = ((TextView) convertView
					.findViewById(R.id.my_talk_text));
//			holder.myVoice = ((TextView) convertView
//					.findViewById(R.id.my_talk_voice_time));
//			holder.youVoice = ((TextView) convertView
//					.findViewById(R.id.you_talk_voice_time));
			holder.tv_time = ((TextView) convertView.findViewById(R.id.tv_time));
			holder.youName = ((TextView) convertView
					.findViewById(R.id.you_talk_name));
			holder.ll_youContent = ((LinearLayout) convertView
					.findViewById(R.id.you_talk_line));
			holder.ll_myCotent = ((LinearLayout) convertView
					.findViewById(R.id.my_talk_content));
			holder.ll_right_video = ((LinearLayout) convertView
					.findViewById(R.id.ll_right_video));
			holder.ll_left_video = ((LinearLayout) convertView
					.findViewById(R.id.ll_left_video));
//			holder.iv_left_video = ((ImageView) convertView
//					.findViewById(R.id.iv_friend_video));
//			holder.iv_right_video = ((ImageView) convertView
//					.findViewById(R.id.iv_my_video));
			// holder.icon = (ImageView)
			// convertView.findViewById(R.id.iv_icon);//
			// RelativeLayout.LayoutParams params = new
			// RelativeLayout.LayoutParams(
			// screenWidth * 2 / 5, screenWidth * 2 * 3 / 5 / 5);
			// holder.icon.setLayoutParams(params);
			// holder.title = (TextView)
			// convertView.findViewById(R.id.tv_title);//
			//
			// holder.price = (TextView)
			// convertView.findViewById(R.id.tv_price);//
			// holder.old_price = (TextView) convertView
			// .findViewById(R.id.tv_old_price);//
			// holder.period = (TextView)
			// convertView.findViewById(R.id.tv_period);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
//		private ImageView iv_left_video;
//		private ImageView iv_right_video;
		private ImageView youImage;
		/** 我发送的图片 */
		private ImageView myImage;
		private MyRoundImageView youHead;
		/** 我的头像 */
		private MyRoundImageView myHead;
		private LinearLayout ll_left_video;
		/** 我发送语音的界面 */
		private LinearLayout ll_right_video;
		/** 我的发送界面 */
		private LinearLayout ll_myCotent;
		private LinearLayout ll_youContent;
		/** 我发送的文字 */
		private TextView myMsg;
		/** 我发送语音的时间 */
//		private TextView myVoice;
		private TextView tv_time;
		private TextView youMsg;
		private TextView youName;
//		private TextView youVoice;
		int position;
		private Chat chat;

		public void setPosition(int position) {
			this.position = position;
			chat = chatList.get(position);
			ll_myCotent.setVisibility(View.GONE);
			ll_youContent.setVisibility(View.GONE);
			ll_left_video.setVisibility(View.GONE);
			ll_right_video.setVisibility(View.GONE);
			youImage.setVisibility(View.GONE);
			myImage.setVisibility(View.GONE);
			youMsg.setVisibility(View.GONE);
			myMsg.setVisibility(View.GONE);

			if (isWithinFiveMinutes(position, lastTime)) {// 前后两条聊天时间在5分钟之内
				tv_time.setVisibility(View.GONE);
			} else {// 不在5分钟之内
				tv_time.setVisibility(View.VISIBLE);
				lastTime = Long.valueOf(chat.getMsgtime()) * 1000;
				tv_time.setText(getTime(position));
			}
			if (chat.getFrom_userid().equals(Data.myinfo.getUserid())) {// 我发的消息
				iniContent(ll_myCotent,myHead,null,myMsg);
			} else{// 对方发的消息
				iniContent(ll_youContent,youHead,youName,youMsg);
			}
		}
		/** 加载消息信息 */
		private void iniContent(LinearLayout ll_content,
				MyRoundImageView head, TextView name, TextView tv_content) {
			ll_content.setVisibility(View.VISIBLE);
			if (chat.getMsg_type().equals("1")) {// 文字消息
				initText(tv_content);
			} else if (chat.getMsg_type().equals("2")) {// 图片消息
//				initYouPicture();
			} else if (chat.getMsg_type().equals("3")) {// 语音消息
//				initYouVideo();
			}
			ImageLoaderUtil.getInstance()
			.setImagebyurl(head==youHead?chat.getUserpic():Data.myinfo.getUserpic(),head);
			if (name!=null) {
				name.setText(chat.getFrom_username());
			}
		}

//		/** 加载消息信息 */
//		private void iniContent(boolean isLeft) {
//			ll_youContent.setVisibility(isLeft ? View.VISIBLE : View.GONE);
//			ll_myCotent.setVisibility(!isLeft ? View.VISIBLE : View.GONE);
//			if (chat.getMsg_type().equals("1")) {// 文字消息
//				initText(true);
//			} else if (chat.getMsg_type().equals("2")) {// 图片消息
////				initYouPicture();
//			} else if (chat.getMsg_type().equals("3")) {// 语音消息
////				initYouVideo();
//			}
//			ImageLoaderUtil.getInstance()
//					.setImagebyurl(chat.getUserpic(),isLeft? youHead:myHead);
//			 youName.setText(isLeft?chat.getFrom_username():"");
//		}

//		private void initYouVideo() {
//			ll_left_video.setVisibility(View.VISIBLE);
//			youVoice.setText(JSON.parseObject(chat.getContent().getVoice())
//					.getString("voiceLength") + "\"");
//			animationDrawable = (AnimationDrawable) iv_left_video.getDrawable();
//			animationDrawable.stop();
//			iv_left_video.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					MusicUtil.StartVoice(JSON.parseObject(chat.getContent().getVoice())
//					.getString("voiceUrl"), new ListenOverBack() {
//						
//						@Override
//						public void cancle() {
//							animationDrawable.stop();
//						}
//						
//						@Override
//						public void back() {
//							animationDrawable.stop();
//						}
//					});
//					animationDrawable.start();
//				}
//			});
//		}
//
//		private void initYouPicture() {
//			youImage.setVisibility(View.VISIBLE);
//			ImageLoaderUtil.getInstance().setImagebyurl(
//					JSON.parseObject(chat.getContent().getImage()).getString(
//							"small_path"), youImage);
//		}

		private void initText(TextView tv_content) {
			SpannableString content = FaceImageDeal.changeString(context, chat.getMsgtext(),true);
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(content);
		}


//		private void initMyVideo() {
//			ll_right_video.setVisibility(View.VISIBLE);
//			myVoice.setText(JSON.parseObject(chat.getContent().getVoice())
//					.getString("voiceLength") + "\"");
//			animationDrawable = (AnimationDrawable) iv_right_video
//					.getDrawable();
//			animationDrawable.stop();
//			iv_right_video.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// media.play(JSON.parseObject(chat.getContent().getVideo()).getString("voiceUrl"));
//					animationDrawable.start();
//				}
//			});
//		}
//
//		private void initMyPicture() {
//			myImage.setVisibility(View.VISIBLE);
//			ImageLoaderUtil.getInstance().setImagebyurl(
//					JSON.parseObject(chat.getContent().getImage()).getString(
//							"small_path"), myImage);
//		}


		private String getTime(int position) {
			String Time;
			Calendar calendar = Calendar.getInstance();
			calendar.set(calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
			long differTime = calendar.getTimeInMillis()
					- Long.valueOf(chat.getMsgtime()) * 1000;
			if (differTime > 2 * 24 * 60 * 60 * 1000) {// 两天前
				Time = TimeUtil.format("M-d HH:mm", chatList.get(position)
						.getMsgtime());
			} else if (differTime > 24 * 60 * 60 * 1000) {//
				Time =context.getResources().getString(R.string.前天)
						+ TimeUtil.format("H:mm", chatList.get(position)
								.getMsgtime());
			} else if (differTime > 0) {//
				Time = context.getResources().getString(R.string.yesterday)
						+ TimeUtil.format("H:mm", chatList.get(position)
								.getMsgtime());
			} else {
				Time = context.getResources().getString(R.string.today)
						+ TimeUtil.format("H:mm", chatList.get(position)
								.getMsgtime());
			}
			return Time;
		}

		/**
		 * 与前面显示最后一天条的时间是否在5分钟之内
		 * 
		 * @param position
		 * @return
		 */
		private boolean isWithinFiveMinutes(int position, long lastTime) {
			if (position == 0) {
				return false;
			}
//		SimpleDateFormat dateFormat=new SimpleDateFormat("", Locale.CHINA);
//		long currentTime=chat.getMsgtime();
			if (Long.valueOf(chat.getMsgtime())*1000 - lastTime < 5 * 60 * 1000) {
				return true;
			}
			return false;
		}
	}

}
