package com.greattone.greattone.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.rent.PostRoomRentActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Lease;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;

public class MyRoomRentListAdapter extends BaseAdapter {
	private Context context;
	private  List<Lease>leaseList;
	private int screenWidth;

	public MyRoomRentListAdapter(Context context,  List<Lease>leaseList) {
		this.context = context;
		this.leaseList = leaseList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return leaseList.size();
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
					R.layout.adapter_my_room_rent, group, false);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);//
			holder.price = (TextView) convertView.findViewById(R.id.tv_price);//
			holder.num = (TextView) convertView.findViewById(R.id.tv_num);//
			holder.edit = (TextView) convertView.findViewById(R.id.tv_edit);//
			holder.del = (TextView) convertView.findViewById(R.id.tv_del);//
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_pic);//
			LayoutParams params = new LayoutParams(
					(screenWidth - DisplayUtil.dip2px(context, 10)) / 2,
					(screenWidth - DisplayUtil.dip2px(context, 10)) / 2 *3 / 5);
			holder.icon.setLayoutParams(params);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		TextView del;
		TextView edit;
		TextView num;
		TextView title;
		TextView price;
		ImageView icon;
		private Lease lease;

		public void setPosition(int position) {
			lease = leaseList.get(position);
			ImageLoaderUtil.getInstance().setImagebyurl(lease.getTitlepic(), icon);
			title.setText(lease.getTitle());
			price.setText(lease.getPrice() + "元/时");
			num.setText("购买人数：" + lease.getPmaxnum()+ "人");
			edit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					 Intent intent = new Intent(context, PostRoomRentActivity.class);
					 intent.putExtra("id", lease.getId());
					 intent.putExtra("classid", lease.getClassid());
					 context.startActivity(intent);
				}
			});
			del.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					final AlertDialog.Builder mDialog = new AlertDialog.Builder(
							context);
					mDialog.setMessage("你确定删除吗？");
					mDialog.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									delete(lease);
								}

							});
					mDialog.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									mDialog.create().cancel();
								}
							});
					mDialog.show();
				}
			});
		}

		private void delete(final Lease lease) {
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "post/ecms");
			map.put("enews", "MDelInfo");
			map.put("classid", lease.getClassid()+"");
			map.put("id", lease.getId()+"");
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(context, map,
					new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast("删除成功");
							leaseList.remove(lease);
							MyProgressDialog.Cancel();
							notifyDataSetChanged();
						}

					}, null));
		}
	}

}
