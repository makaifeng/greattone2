package com.greattone.greattone.adapter;

import java.util.List;

import android.annotation.SuppressLint;
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
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.PersonList;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyStudentAdapter extends BaseAdapter {
	private Context context;
	private List<PersonList> mlist;
	private String tag;
	OnBtnItemClickListener itemClickListener;
	public MyStudentAdapter(Context context, List<PersonList> mlist, String tag) {
		this.mlist = mlist;
		this.context = context;
		this.tag = tag;
	}

	public int getCount() {
		return this.mlist.size();
	}

	public Object getItem(int position) {
		return this.mlist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_my_student, null);
			holder.text = ((TextView) convertView.findViewById(R.id.tv_title));
			holder.Btn1 = ((TextView) convertView.findViewById(R.id.tv_btn1));
			holder.Btn2 = ((TextView) convertView.findViewById(R.id.tv_btn2));
			holder.cname = ((TextView) convertView.findViewById(R.id.tv_cname));
			holder.level = ((TextView) convertView.findViewById(R.id.tv_level));
			holder.pic = ((ImageView) convertView.findViewById(R.id.iv_pic));
			holder.del = ((ImageView) convertView.findViewById(R.id.iv_del));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView Btn1;
		TextView Btn2;
		TextView cname;
		ImageView del;
		TextView level;
		ImageView pic;
		TextView text;
		PersonList data;
		int position;
		@SuppressWarnings("deprecation")
		public void setPosition(int position) {
			this.position=position;
			del.setVisibility(View.GONE);
			cname.setVisibility(View.VISIBLE);
			level.setVisibility(View.VISIBLE);
			data = mlist.get(position);
			text.setText(data.getUsername());
//			cname.setText("身份：" + data.getCname());
			level.setText("等级：" + data.getLevel());
			Btn1.setOnClickListener(lis);
			Btn2.setOnClickListener(lis);
			del.setOnClickListener(lis);
			if (tag.equals("推荐")) {
				Btn1.setBackgroundColor(context.getResources().getColor(
						R.color.gray_eeeeee));
				Btn2.setBackgroundColor(context.getResources().getColor(
						R.color.black));
				Btn1.setTextColor(context.getResources()
						.getColor(R.color.black));
				Btn1.setText("取消推荐");
				Btn2.setText("私信");
				del.setVisibility(View.GONE);
				ImageLoaderUtil.getInstance().setImagebyurl(data.getUserpic(), pic);
			} else if (tag.equals("全部")) {
				ImageLoaderUtil.getInstance().setImagebyurl(data.getUserpic(), pic);
				Btn1.setBackgroundColor(context.getResources().getColor(
						R.color.red_c30000));
				Btn2.setBackgroundColor(context.getResources().getColor(
						R.color.black));
				Btn1.setText("我要推荐");
				Btn2.setText("私信");
				del.setVisibility(View.VISIBLE);
			} else if (tag.equals("新增")) {
				Btn1.setBackgroundColor(context.getResources().getColor(
						R.color.red_c30000));
				Btn2.setBackgroundColor(context.getResources().getColor(
						R.color.black));
				Btn1.setText("申请");
				Btn2.setText("私信");
				ImageLoaderUtil.getInstance().setImagebyurl(data.getUserpic(), pic);
				del.setVisibility(View.VISIBLE);
			} else if (tag.equals("邀请")) {
				Btn1.setBackgroundColor(context.getResources().getColor(
						R.color.red_c30000));
				Btn2.setBackgroundColor(context.getResources().getColor(
						R.color.black));
				Btn1.setText("邀请");
				Btn2.setText("私信");
				del.setVisibility(View.GONE);
				cname.setVisibility(View.GONE);
				level.setVisibility(View.GONE);
				ImageLoaderUtil.getInstance().setImagebyurl(data.getUserpic(), pic);
			} else if (tag.equals("student")) {
				ImageLoaderUtil.getInstance().setImagebyurl(data.getUserpic(), pic);
				Btn1.setBackgroundColor(context.getResources().getColor(
						R.color.gray_8f8f8f));
				Btn2.setBackgroundColor(context.getResources().getColor(
						R.color.black));
				Btn1.setText("已邀请");
				Btn2.setText("私信");
				del.setVisibility(View.GONE);
				cname.setVisibility(View.GONE);
				level.setVisibility(View.GONE);
			}
		}

		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == Btn1) {
					if (tag.equals("推荐")) {
						centreRecstuden(2);
					} else if (tag.equals("全部")) {
						centreRecstuden(1);
					} else if (tag.equals("新增")) {
						applyFor(1);
					} else if (tag.equals("邀请")) {
						Invite(1);
					} else if (tag.equals("取消邀请")) {
						cancleInvite();
					}
				} else if (v == Btn2) {
					Intent intent = new Intent(context, MyChatActivity.class);
					intent.putExtra("friendId", data.getUserid());
					intent.putExtra("friendName", data.getUsername());
					context.startActivity(intent);
				} else if (v == del) {
					delete();
				}
			}
		};

		/** 删除 */
		protected void delete() {
			MyProgressDialog.show(context);
//			String msg = "uid=" + Data.userData.getUid() + "&id="
//					+ data.getUserid() + "&token=" + Data.userData.getToken();
//			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							HttpConstants.CENTRE_DEL_STUDENT_URL, msg,
//							new MyStringResponseHandle() {
//
//								@Override
//								public void setServerErrorResponseHandle(
//										com.greattone.greattone.entity.Message message) {
//
//								}
//
//								@Override
//								public void setResponseHandle(
//										com.greattone.greattone.entity.Message message) {
//									((BaseActivity) context).toast("删除成功");
//									MyProgressDialog.Cancel();
//								}
//
//								@Override
//								public void setErrorResponseHandle(
//										VolleyError error) {
//
//								}
//							}));
		}

		/** 新增 */
		protected void applyFor(int i) {
//			MyProgressDialog.show(context);
//			String msg = "uid=" + Data.userData.getUid() + "&id="
//					+ data.getUserid() + "&token=" + Data.userData.getToken()
//					+ "&type=" + i + "&cate=" + Data.userData.getGroupid();
//			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							HttpConstants.CENTRE_SCONTROL_URL, msg,
//							new MyStringResponseHandle() {
//
//								@Override
//								public void setServerErrorResponseHandle(
//										com.greattone.greattone.entity.Message message) {
//
//								}
//
//								@Override
//								public void setResponseHandle(
//										com.greattone.greattone.entity.Message message) {
//									mlist.remove(position);
//								      notifyDataSetChanged();
//									MyProgressDialog.Cancel();
//								}
//
//								@Override
//								public void setErrorResponseHandle(
//										VolleyError error) {
//
//								}
//							}));
		}

		/** 推荐学生 */
		protected void centreRecstuden(int i) {
//			MyProgressDialog.show(context);
//			String msg = "uid=" + Data.userData.getUid() + "&id="
//					+ data.getUserid() + "&token=" + Data.userData.getToken()
//					+ "&type=" + i + "&cate=" + Data.userData.getGroupid();
////			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							HttpConstants.CENTRE_REC_STUDENT_URL, msg,
//							new MyStringResponseHandle() {
//
//								@Override
//								public void setServerErrorResponseHandle(
//										com.greattone.greattone.entity.Message message) {
//
//								}
//
//								@Override
//								public void setResponseHandle(
//										com.greattone.greattone.entity.Message message) {
//									mlist.remove(position);
//							      notifyDataSetChanged();
//									MyProgressDialog.Cancel();
//								}
//
//								@Override
//								public void setErrorResponseHandle(
//										VolleyError error) {
//
//								}
//							}));
		}
		/** 邀请学生 */
		protected void Invite(int i) {
//			MyProgressDialog.show(context);
//				String url = null;
//			if (Data.userData.getGroupid() == 4) {
//				url = HttpConstants.CLASSROOM_INVITE_URL;
//			} else if (Data.userData.getGroupid() == 3) {
//				url = HttpConstants.TEACHER_INVITE_URL;
//			}
//			String msg = "uid=" + Data.userData.getUid() + "&id="
//					+ data.getUserid() + "&token=" + Data.userData.getToken()
//					+ "&type=" + i + "&cate=" + Data.userData.getGroupid();
////			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							url, msg,
//							new MyStringResponseHandle() {
//						
//						@Override
//						public void setServerErrorResponseHandle(
//								com.greattone.greattone.entity.Message message) {
//							
//						}
//						
//						@Override
//						public void setResponseHandle(
//								com.greattone.greattone.entity.Message message) {
//							((BaseActivity) context).toast("已成功提交邀请");
//							mlist.remove(position);
//							notifyDataSetChanged();
//							MyProgressDialog.Cancel();
//						}
//						
//						@Override
//						public void setErrorResponseHandle(
//								VolleyError error) {
//							
//						}
//					}));
		}
		/** 取消邀请学生 */
		protected void cancleInvite() {
//			MyProgressDialog.show(context);
//			String url = null;
//			String type = null;
//			if (Data.userData.getGroupid() == 4) {
//				url = HttpConstants.CLASSROOM_CANCEL_INVITE_URL;
//				type="2";
//			} else if (Data.userData.getGroupid() == 3) {
//				url = HttpConstants.TEACHER_CANCEL_INVITE_URL;
//				type="1";
//			}
//			String msg = "uid=" + Data.userData.getUid() + "&id="
//					+ data.getUserid() + "&token=" + Data.userData.getToken()
//					+ "&type=" + type ;
//			((BaseActivity) context).addRequest(HttpUtil
//					.sendStringToServerByGet(context,
//							url, msg,
//							new MyStringResponseHandle() {
//						
//						@Override
//						public void setServerErrorResponseHandle(
//								com.greattone.greattone.entity.Message message) {
//							
//						}
//						
//						@Override
//						public void setResponseHandle(
//								com.greattone.greattone.entity.Message message) {
//							((BaseActivity) context).toast("已成功提交邀请");
//							mlist.remove(position);
//							notifyDataSetChanged();
//							MyProgressDialog.Cancel();
//						}
//						
//						@Override
//						public void setErrorResponseHandle(
//								VolleyError error) {
//							
//						}
//					}));
		}
	}
	/**点击事件*/
	public void setOnBtnItemClicklistener(OnBtnItemClickListener btnItemClickListener){
		itemClickListener=btnItemClickListener;
	}
}
