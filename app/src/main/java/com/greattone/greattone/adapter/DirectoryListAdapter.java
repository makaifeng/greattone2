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
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

public class DirectoryListAdapter extends BaseAdapter {
	private Context context;
	private List<Friend> contactsList;
	// private int screenWidth;
	private String type;
//	private String ramark;

	public DirectoryListAdapter(Context context, List<Friend> contactsList,
			String type) {
		this.context = context;
		this.contactsList = contactsList;
		this.type = type;
		// screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return contactsList.size();
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
					R.layout.adapter_directory, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.click = (TextView) convertView.findViewById(R.id.tv_click);//
			holder.content = (TextView) convertView
					.findViewById(R.id.tv_content);//
			holder.send_letter = (ImageView) convertView
					.findViewById(R.id.iv_send_letter);//
			holder.call = (ImageView) convertView.findViewById(R.id.iv_call);//
			holder.icon = (MyRoundImageView) convertView
					.findViewById(R.id.iv_icon);//
			holder.icon.setRadius(DisplayUtil.dip2px(context, 10));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView click;
		TextView content;
		ImageView send_letter;
		ImageView call;
		MyRoundImageView icon;
		int position;

		public void setPosition(int position) {
			this.position = position;
			ImageLoaderUtil.getInstance().setImagebyurl(
					contactsList.get(position).getUserpic(), icon);
			name.setText(contactsList.get(position).getUsername());
			content.setText(contactsList.get(position).getSaytext());
			call.setVisibility(View.GONE);
			if (type.equals("feed")) {// 知音
//				click.setText("修改备注");
				click.setVisibility(View.GONE);
			} else if (type.equals("friend")) {// 好友
				send_letter.setVisibility(View.VISIBLE);
				click.setVisibility(View.GONE);
//				click.setText("邀请");
			} else if (type.equals("follow")) {// 粉丝
				click.setText(context.getResources().getString(R.string.focus));//关注
			}
			call.setOnClickListener(lis);
			send_letter.setOnClickListener(lis);
			click.setOnClickListener(lis);
		}

		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == click) {// 按钮
					if (type.equals("feed")) {// 知音
//						ShowDialog();//备注
					} else if (type.equals("friend")) {// 好友
//						invitePenson();//邀请
					} else if (type.equals("follow")) {// 粉丝
						addattention();//关注
					}
				} else if (v == send_letter) {// 私信
					MyProgressDialog.show(context);
					HttpProxyUtil.isFriend(context, contactsList.get(position).getUserid(), new ResponseListener() {
						
						@Override
						public void setResponseHandle(Message2 message) {
							MyProgressDialog.Cancel();
							if (message.getData().equals("1")) {
								Intent intent = new Intent(context, MyChatActivity.class);
								intent.putExtra("name", contactsList.get(position).getUsername());
								intent.putExtra("image", contactsList.get(position).getUserpic());
								context.startActivity(intent);
							} else {
							(	(BaseActivity)context).toast(context.getResources().getString(R.string.互相关注后才能发送私信));
							}
						}
					}, null);
				} else if (v == call) {// 电话
//					ActivityUtil.CallPhone(context, contactsList.get(position).get);
				}
			}
		};
//		private EditText editText;
//		private AlertDialog dialog;

//		/** 显示对话框 */
//		@SuppressWarnings("deprecation")
//		protected void ShowDialog() {
//			LinearLayout layout = new LinearLayout(context);
//			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//					LayoutParams.WRAP_CONTENT));
//			layout.setOrientation(LinearLayout.VERTICAL);
//			layout.setGravity(Gravity.CENTER_HORIZONTAL);
//			layout.setBackgroundColor(context.getResources().getColor(
//					R.color.gray_eeeeee));
//			TextView textView = new TextView(context);
//			textView.setLayoutParams(new LinearLayout.LayoutParams(
//					LinearLayout.LayoutParams.WRAP_CONTENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT));
//			textView.setGravity(Gravity.CENTER);
//			textView.setText("修改备注");
//			textView.setPadding(0, DisplayUtil.dip2px(context, 5), 0,
//					DisplayUtil.dip2px(context, 5));
//			layout.addView(textView);
//			editText = new EditText(context);
//			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//					LinearLayout.LayoutParams.MATCH_PARENT,
//					LinearLayout.LayoutParams.WRAP_CONTENT);
//			layoutParams.setMargins(DisplayUtil.dip2px(context, 10),
//					DisplayUtil.dip2px(context, 10),
//					DisplayUtil.dip2px(context, 10),
//					DisplayUtil.dip2px(context, 10));
//			editText.setLayoutParams(layoutParams);
//			editText.setBackgroundResource(R.drawable.edit_bg);
//			editText.setHint("请填写备注名称");
//			layout.addView(editText);
//			TextView textView2 = new TextView(context);
//			textView2.setLayoutParams(layoutParams);
//			textView2.setGravity(Gravity.CENTER);
//			textView2.setText("确定");
//			textView2.setPadding(0, DisplayUtil.dip2px(context, 8), 0,
//					DisplayUtil.dip2px(context, 8));
//			textView2.setBackgroundColor(context.getResources().getColor(
//					R.color.red_b90006));
//			textView2.setTextColor(context.getResources().getColor(
//					R.color.white));
//			textView2.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					ramark = editText.getText().toString().trim();
//					if (!TextUtils.isEmpty(ramark)) {
//						remarks();
//						dialog.cancel();
//					} else {
//						((BaseActivity) context).toast("请填写备注！");
//					}
//				}
//			});
//			layout.addView(textView2);
//
//			dialog = new AlertDialog.Builder(context).setView(layout).show();
//
//		}

//		/** 邀请某人 */
//		protected void invitePenson() {
//			MyProgressDialog.show(context);
//			String msg = "id=" + contactsList.get(position).getUserid() + "&uid="
//					+ Data.userData.getUid() + "&page=1&token="
//					+ Data.userData.getToken();
//			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							HttpConstants.TEACHER_INVITE_URL, msg,
//							new MyStringResponseHandle() {
//
//								@Override
//								public void setServerErrorResponseHandle(
//										Message message) {
//
//								}
//
//								@Override
//								public void setResponseHandle(Message message) {
//									((BaseActivity) context).toast(message.getInfo());
//									MyProgressDialog.Cancel();
//
//								}
//
//								@Override
//								public void setErrorResponseHandle(
//										VolleyError error) {
//
//								}
//							}));
//		}
		
		/** 关注 */
		protected void addattention() {
			MyProgressDialog.show(context);
			HttpProxyUtil.addattention(context, contactsList.get(position).getUserid() , new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast(message.getInfo());
							if (message.getInfo().equals("增加关注成功")) {
								click.setText(context.getResources().getString(R.string.取消));
							} else {
								click.setText(context.getResources().getString(R.string.focus));//关注
							}
							MyProgressDialog.Cancel();
						}

					}, null);
		}

		/** 修改某人的备注 */
		protected void remarks() {
			MyProgressDialog.show(context);
//			String msg = "id=" + contactsList.get(position).getUserid() + "&uid="
//					+ Data.userData.getUid() + "&page=1&token="
//					+ Data.userData.getToken() + "&name=" + ramark;
//			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							HttpConstants.CENTRE_REMARKS_URL, msg,
//							new MyStringResponseHandle() {
//
//								@Override
//								public void setServerErrorResponseHandle(
//										Message message) {
//
//								}
//
//								@Override
//								public void setResponseHandle(Message message) {
//									((BaseActivity) context).toast("修改成功");
//									MyProgressDialog.Cancel();
//
//								}
//
//								@Override
//								public void setErrorResponseHandle(
//										VolleyError error) {
//
//								}
//							}));
		}
	}

}
