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
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.entity.BaseData;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyStudentAdapter2 extends BaseAdapter {
	private Context context;
	private List<BaseData> mlist;
	private String tag;

	public MyStudentAdapter2(Context context, List<BaseData> mlist, String tag) {
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
		BaseData data;
		int position;
		@SuppressWarnings("deprecation")
		public void setPosition(int position) {
			this.position=position;
			del.setVisibility(View.GONE);
			data = mlist.get(position);
			text.setText(data.getName());
//			cname.setText("身份：" + data.getCname());
//			level.setText("等级：" + data.getLevel());
			ImageLoaderUtil.getInstance().setImagebyurl(data.getPhoto(), pic);
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
			} else if (tag.equals("全部")) {
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
				del.setVisibility(View.VISIBLE);
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
					}
				} else if (v == Btn2) {
					Intent intent = new Intent(context, MyChatActivity.class);
					intent.putExtra("friendId", data.getUid());
					intent.putExtra("friendName", data.getName());
					context.startActivity(intent);
				} else if (v == del) {
					delete();
				}
			}
		};

		/** 删除 */
		protected void delete() {
//			MyProgressDialog.show(context);
//			String msg = "uid=" + Data.userData.getUid() + "&id="
//					+ data.getId() + "&token=" + Data.userData.getToken();
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
//					+ data.getId() + "&token=" + Data.userData.getToken()
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
//					+ data.getId() + "&token=" + Data.userData.getToken()
//					+ "&type=" + i + "&cate=" + Data.userData.getGroupid();
//			((BaseActivity) context).addRequest(HttpUtil
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
	}
}
