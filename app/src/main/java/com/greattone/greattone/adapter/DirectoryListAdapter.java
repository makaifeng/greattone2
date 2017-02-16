package com.greattone.greattone.adapter;

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
import com.greattone.greattone.activity.brand.BrandDetailActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import java.util.List;

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
//			holder.click2 = (TextView) convertView.findViewById(R.id.tv_click2);//
			holder.shengfen = (TextView) convertView
					.findViewById(R.id.tv_shengfen);//
			holder.level = (TextView) convertView
					.findViewById(R.id.tv_level);//
			holder.other = (TextView) convertView
					.findViewById(R.id.tv_other);//
			holder.iv_vip = (ImageView) convertView.findViewById(R.id.iv_vip);//
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
//		TextView click2;
		TextView shengfen;
		TextView level;
		TextView other;
		ImageView iv_vip;
		MyRoundImageView icon;
		int position;

		public void setPosition(int position) {
			this.position = position;
			ImageLoaderUtil.getInstance().setImagebyurl(
					contactsList.get(position).getUserpic(), icon);
			name.setText(contactsList.get(position).getUsername());
			shengfen.setText(getIdentity(contactsList.get(position)));
			other.setText("| 粉丝数："+contactsList.get(position).getFollownum()+"	关注："+contactsList.get(position).getFeednum());
			level.setText(contactsList.get(position).getLevel().getName());
			if (contactsList.get(position).getCked()==1){
				iv_vip.setVisibility(View.VISIBLE);
			}else 			iv_vip.setVisibility(View.INVISIBLE);
			click.setTextColor(context.getResources().getColor(R.color.white));
			click.setBackgroundColor(context.getResources().getColor(R.color.yellow_ffa200));
			click.setOnClickListener(lis);
			if (type.equals("feed")) {// 偶像
//				click1.setText("私信");
				click.setVisibility(View.INVISIBLE);
//				click2.setText("取消关注");
			} else if (type.equals("friend")) {// 知音
				if (Data.myinfo.getGroupid()==3){
					if (contactsList.get(position).getGroupid()==1||contactsList.get(position).getGroupid()==2){
						if (contactsList.get(position).getInvite()==0){
							click.setText("添加");
						}else {
							click.setTextColor(context.getResources().getColor(R.color.black));
							click.setBackgroundColor(context.getResources().getColor(R.color.gray_b3b3b3));
							click.setText("已添加");
							click.setOnClickListener(null);
						}
					}else {
						click.setVisibility(View.INVISIBLE);
					}
				}else if (Data.myinfo.getGroupid()==4){
					if (contactsList.get(position).getGroupid()==1||contactsList.get(position).getGroupid()==2||contactsList.get(position).getGroupid()==3){
						if (contactsList.get(position).getInvite()==0){
							click.setText("添加");
						}else {
							click.setTextColor(context.getResources().getColor(R.color.black));
							click.setBackgroundColor(context.getResources().getColor(R.color.gray_b3b3b3));
							click.setText("已添加");
							click.setOnClickListener(null);
						}
					}else {
						click.setVisibility(View.INVISIBLE);
					}
				}else {
					click.setVisibility(View.INVISIBLE);
				}
//				click2.setText("取消关注");
			} else if (type.equals("follow")) {// 粉丝
//				click1.setVisibility(View.INVISIBLE);
				click.setText("关注");
			} else if (type.equals("student")) {//学生
//				click1.setVisibility(View.INVISIBLE);
				click.setText("删除");
				click.setTextColor(context.getResources().getColor(R.color.black));
				click.setBackgroundColor(context.getResources().getColor(R.color.gray_b3b3b3));
			} else if (type.equals("teacher")) {// 老师
				if (Data.myinfo.getGroupid()==4){
//					click1.setVisibility(View.INVISIBLE);
					click.setText("删除");
					click.setTextColor(context.getResources().getColor(R.color.black));
					click.setBackgroundColor(context.getResources().getColor(R.color.gray_b3b3b3));
				}else {
					click.setVisibility(View.INVISIBLE);
//					click2.setVisibility(View.INVISIBLE);
				}
			} else if (type.equals("classroom")) {//教室
				click.setVisibility(View.INVISIBLE);
//				click2.setVisibility(View.INVISIBLE);
			} else if (type.equals("pinpai")) {//品牌
				click.setVisibility(View.INVISIBLE);
//				click2.setVisibility(View.INVISIBLE);
			}

//			click2.setOnClickListener(lis);
			icon.setOnClickListener(lis);
		}
		/**获取身份*/
		public  String getIdentity(Friend info){
			String identity=null;
			if (info.getGroupid()==1) {//普通会员
				if (info.getPutong_shenfen()!=null&&info.getPutong_shenfen().length()>1) {
					identity=info.getPutong_shenfen();
				}else{
					identity=info.getGroupname();
				}
			}else if (info.getGroupid()==2) {//音乐名人
				if (info.getMusic_star()!=null&&info.getMusic_star().length()>1) {
					identity=info.getMusic_star();
				}else{
					identity=info.getGroupname();
				}
			}else
			if (info.getGroupid()==3) {//音乐老师
				if (info.getTeacher_type()!=null&&info.getTeacher_type().length()>1) {
					identity=info.getTeacher_type();
				}else{
					identity=info.getGroupname();
				}
			}else
			if (info.getGroupid()==4) {//音乐教室
				if (info.getClassroom_type()!=null&&info.getClassroom_type().length()>1) {
					identity=info.getClassroom_type();
				}else{
					identity=info.getGroupname();
				}
			}else		 if (info.getGroupid()==5) {//品牌
				identity=info.getGroupname();
			}else{
				identity=info.getGroupname();
			}
			return identity;
		}
		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == click) {//按钮1
					if (type.equals("feed")) {// 偶像
					} else if (type.equals("friend")) {// 知音
						if (Data.myinfo.getGroupid()==3){
							if (contactsList.get(position).getGroupid()==1||contactsList.get(position).getGroupid()==2){
								if (contactsList.get(position).getInvite()==0){
									//邀请
									invite();
								}else {
//									//取消邀请
//									uninvite();
								}
							}
						}else if (Data.myinfo.getGroupid()==4){
							if (contactsList.get(position).getGroupid()==1||contactsList.get(position).getGroupid()==2||contactsList.get(position).getGroupid()==3){
								if (contactsList.get(position).getInvite()==0){
									//邀请
									invite();
								}else {
//									//取消邀请
//									uninvite();
								}
							}
						}
					} else if (type.equals("follow")) {// 粉丝
						//关注
						addattention();
					} else if (type.equals("student")) {//学生
						//取消邀请
						uninvite();
					} else if (type.equals("teacher")) {// 老师
						//取消邀请
						uninvite();
					} else if (type.equals("classroom")) {//教室
					} else if (type.equals("pinpai")) {//品牌
					}
//				} else if (v == click2) {// 按钮2
//					if (type.equals("feed")) {// 偶像
//						//取消关注
//						addattention();
//					} else if (type.equals("friend")) {// 知音
//						//取消关注
//						addattention();
//					} else if (type.equals("follow")) {// 粉丝
//
//					} else if (type.equals("student")) {//学生
//
//					} else if (type.equals("teacher")) {// 老师
//
//					} else if (type.equals("classroom")) {//教室
//					} else if (type.equals("pinpai")) {//品牌
//					}
				} else if (v == icon) {// 头像
					toCenter(position);
				}
			}
		};
		/**跳转到个人主页
		 * @param position */
		protected void toCenter(int position) {
			int group=Integer.valueOf(contactsList.get(position).getGroupid());
			Intent intent=new Intent();
			if (group==1||group==2) {//普通会员和名人
				intent .setClass(context, CelebrityActivity.class);
				intent.putExtra("id",contactsList.get(position).getUserid()+"");
				intent.putExtra("groupid",Integer.valueOf( contactsList.get(position).getGroupid()));
			}else 	if (group==3) {//老师
				intent .setClass(context, TeacherActivity.class);
				intent.putExtra("id", contactsList.get(position).getUserid()+"");
			}else 	if (group==4) {//教室
				intent .setClass(context, ClassRoomActivity.class);
				intent.putExtra("id", contactsList.get(position).getUserid()+"");
			}else 	if (group==5) {//品牌
				intent .setClass(context,BrandDetailActivity.class);
				intent.putExtra("id", contactsList.get(position).getUserid()+"");
			}else {
				intent .setClass(context, CelebrityActivity.class);
				intent.putExtra("id",contactsList.get(position).getUserid()+"");
				intent.putExtra("groupid",Integer.valueOf( contactsList.get(position).getGroupid()));
			}
			context.startActivity(intent);
		}
		/** 关注 */
		protected void addattention() {
			MyProgressDialog.show(context);
			HttpProxyUtil.addattention(context, contactsList.get(position).getUserid() , new HttpUtil.ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast(message.getInfo());
							contactsList.remove(position);
							notifyDataSetChanged();
							if (message.getInfo().equals("增加关注成功")) {
							} else {
							}
							MyProgressDialog.Cancel();
						}

					}, null);
		}
		/** 邀请*/
		protected void invite() {
			MyProgressDialog.show(context);
			HttpProxyUtil.invite(context, contactsList.get(position).getUserid() , new HttpUtil.ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast(message.getInfo());
							if (type.equals("friend")) {
								contactsList.get(position).setInvite(1);
								click.setTextColor(context.getResources().getColor(R.color.black));
								click.setBackgroundColor(context.getResources().getColor(R.color.gray_b3b3b3));
								click.setText("已添加");
								click.setOnClickListener(null);
							}else {
								contactsList.remove(position);
								notifyDataSetChanged();
							}
							MyProgressDialog.Cancel();
						}

					}, null);
		}
		/** 取消邀请*/
		protected void uninvite() {
			MyProgressDialog.show(context);
			HttpProxyUtil.uninvite(context, contactsList.get(position).getUserid() , new HttpUtil.ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast(message.getInfo());
							if (type.equals("friend")) {
								contactsList.get(position).setInvite(0);
								click.setText("邀请");
								click.setTextColor(context.getResources().getColor(R.color.white));
								click.setBackgroundColor(context.getResources().getColor(R.color.yellow_ffa200));
							}else {
								contactsList.remove(position);
								notifyDataSetChanged();
							}
							MyProgressDialog.Cancel();
						}

					}, null);
		}

	}

}
