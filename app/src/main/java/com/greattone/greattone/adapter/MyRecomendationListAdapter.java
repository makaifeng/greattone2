package com.greattone.greattone.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Video;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyRecomendationListAdapter extends BaseAdapter {

	private Context context;
	private List<Video> videoList;
	private int screenWidth;
	int type;

	public MyRecomendationListAdapter(Context context, List<Video> videoList) {
		this.context = context;
		this.videoList = videoList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return videoList.size();
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
					R.layout.adapter_my_recomm_video, group, false);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					screenWidth * 2 / 5, screenWidth * 2 * 3 / 5 / 5);
			holder.icon.setLayoutParams(params);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//

			holder.cname = (TextView) convertView.findViewById(R.id.tv_cname);//
			holder.remarks = (TextView) convertView
					.findViewById(R.id.tv_remarks);//
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 标题 */
		TextView title;
		/** 名字 */
		TextView name;
		/** 身份 */
		TextView cname;
		/** 推荐按钮 */
		TextView remarks;
		/** 图片 */
		ImageView icon;
		int position;
		public void setPosition( final int position) {
			this.position=position;
			title.setText(videoList.get(position).getTitle());
			name.setText(videoList.get(position).getUserInfo().getUsername());
			ImageLoaderUtil.getInstance().setImagebyurl(
					videoList.get(position).getTitlepic(), icon);
			cname.setText("("
					+ videoList.get(position).getUserInfo().getTeacher_type()
					+ ")");
			remarks.setText(videoList.get(position).getPlnum() + "");
			if (type == 0) {
				remarks.setText("取消推荐");
			} else {
				remarks.setText("我要推荐");
			}
			icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(context,VideoPlayActivity.class);
					intent.putExtra("url",videoList.get(position).getShipin());
					context.startActivity(intent);
				}
			});
			remarks.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					 if (type == 0) {//取消推荐
						 showdialog();
					 }else{
						 remarks();
					 }
				
				}
			});
		}

		protected void showdialog() {
			new AlertDialog.Builder(context).setTitle("取消推荐").setMessage("是否取消？")
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					remarks();
				}
			})
			.setNegativeButton("否", null).show();
		}
			/**
			 * 推荐或取消推荐
			 */
		protected void remarks() {
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "tuijian/doRecommend");
			map.put("id", videoList.get(position).getId());
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			((BaseActivity) context).addRequest(HttpUtil
					.httpConnectionByPost(context, map,
							new ResponseListener() {

								@Override
								public void setResponseHandle(
										Message2 message) {
									if (type == 0) {
										((BaseActivity) context)
												.toast("取消成功！");
									} else {
										((BaseActivity) context)
												.toast("推荐成功！");
									}
									videoList.remove(position);
									MyRecomendationListAdapter.this
											.notifyDataSetChanged();
									MyProgressDialog.Cancel();
								}
							}, null));
			
		}
	}

	public void setType(int type) {
		this.type = type;
	}
}
