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
import com.greattone.greattone.activity.chat.MyChatActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.personal.DirectoryActivity2;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyIosDialog;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Friend;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpProxyUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import java.util.ArrayList;
import java.util.List;

public class DirectoryListAdapter2 extends BaseAdapter {
	private Context context;
	private List<Friend> contactsList;
	private List<Friend> inviteList;
	// private int screenWidth;
//	private String ramark;

	public DirectoryListAdapter2(Context context, List<Friend> contactsList,
								 List<Friend> inviteList) {
		this.context = context;
		this.contactsList = contactsList;
		this.inviteList = inviteList;
		// screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return contactsList.size()+inviteList.size();
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
					R.layout.adapter_directory2, group, false);
			holder.name = (TextView) convertView.findViewById(R.id.tv_name);//
			holder.click = (TextView) convertView.findViewById(R.id.tv_click);//
			holder.click2 = (TextView) convertView.findViewById(R.id.tv_click2);//
//			holder.add = (TextView) convertView.findViewById(R.id.tv_add);//
			holder.tv_guanxi = (TextView) convertView.findViewById(R.id.tv_guanxi);//
			holder.lines = (View) convertView.findViewById(R.id.lines);//
			holder.shengfen = (TextView) convertView
					.findViewById(R.id.tv_shengfen);//
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
//		TextView add;
		TextView click;
		TextView click2;
		TextView shengfen;
		TextView tv_guanxi;
//		TextView other;
		View lines;
		ImageView iv_vip;
		MyRoundImageView icon;
		int position;
		private int type2;
		private int guanxi;
		Friend friend;
		boolean isinvite;
		public void setPosition(int position) {
			lines.setVisibility(View.GONE);
			if (position>inviteList.size()-1) {
				this.position = position-inviteList.size();
				friend=contactsList.get(this.position);
				isinvite=false;
			}else {
				isinvite=true;
				click2.setText("拒绝");
				click.setText("同意");
				friend=inviteList.get(position);
			}
			if (position==inviteList.size()-1){
				lines.setVisibility(View.VISIBLE);
			}
				ImageLoaderUtil.getInstance().setImagebyurl(
						friend.getUserpic(), icon);
				name.setText(friend.getUsername());
				shengfen.setText(getIdentity(friend));
//			other.setText("| 粉丝数："+friend.getFollownum()+"	关注："+friend.getFeednum());
//			level.setText(friend.getLevel().getName());
				if (friend.getCked() == 1) {
					iv_vip.setVisibility(View.VISIBLE);
				} else iv_vip.setVisibility(View.INVISIBLE);
				guanxi = friend.getGuanxi();
//				add.setVisibility(View.GONE);
				click.setVisibility(View.GONE);
				click2.setVisibility(View.GONE);
				tv_guanxi.setVisibility(View.GONE);
//				add.setOnClickListener(lis);
				click.setOnClickListener(lis);
				click2.setOnClickListener(lis);
			if (isinvite) {//邀请列表
				tv_guanxi.setVisibility(View.VISIBLE);
				click.setVisibility(View.VISIBLE);
				click2.setVisibility(View.VISIBLE);
				if (guanxi == 1) {//好友
					tv_guanxi.setText("邀请你成为他的好友");
				}else {
					if (friend.getInvitetype() == 1) {//1学生->老师，2学生->教室，3老师->学生，4老师->教室，5教室->学生，6教室->老师
						tv_guanxi.setText("邀请你成为ta的学生");
					} else if (friend.getInvitetype() == 2) {
						tv_guanxi.setText("邀请你成为ta的学生");
					} else if (friend.getInvitetype() == 3) {
						tv_guanxi.setText("邀请你成为ta的老师");
					} else if (friend.getInvitetype() == 4) {
						tv_guanxi.setText("邀请你成为ta的老师");
					} else if (friend.getInvitetype() == 5) {
						tv_guanxi.setText("申请成为ta的教室");
					} else if (friend.getInvitetype() == 6) {
						tv_guanxi.setText("申请成为ta的教室");
					}
				}
			}else{
				if (guanxi == 0) {//我的关注
					tv_guanxi.setVisibility(View.VISIBLE);
					tv_guanxi.setText("我的关注");
					click2.setVisibility(View.VISIBLE);
					click2.setText("添加关系");
//					add.setVisibility(View.VISIBLE);
					if (friend.getInvite() == 1) {
						click2.setText("已邀请");
						click2.setBackgroundResource(R.drawable.tongxunlu_button_unclick);
						click2.setClickable(false);
					} else {
						click2.setBackgroundResource(R.drawable.tongxunlu_button);
						click2.setClickable(true);
					}
				} else if (guanxi == 1) {//好友
					tv_guanxi.setVisibility(View.VISIBLE);
					click.setVisibility(View.VISIBLE);
					tv_guanxi.setText("我的好友");
					if (getguanxi() != 0) {
						click2.setVisibility(View.VISIBLE);
						click2.setText("添加关系");
						if (friend.getInvite() == 1) {
							click2.setText("已邀请");
							click2.setBackgroundResource(R.drawable.tongxunlu_button_unclick);
							click2.setClickable(false);
						} else {
							click2.setBackgroundResource(R.drawable.tongxunlu_button);
							click2.setClickable(true);
						}
					}
				} else if (guanxi == 2) {//学生
					tv_guanxi.setVisibility(View.VISIBLE);
					click.setVisibility(View.VISIBLE);
					click2.setVisibility(View.VISIBLE);
					tv_guanxi.setText("我的学生");
					click2.setText("解除关系");
				} else if (guanxi == 3) {//老师
					tv_guanxi.setVisibility(View.VISIBLE);
					click.setVisibility(View.VISIBLE);
					click2.setVisibility(View.VISIBLE);
					tv_guanxi.setText("我的老师");
					click2.setText("解除关系");
				} else if (guanxi == 4) {//教室
					tv_guanxi.setVisibility(View.VISIBLE);
					click.setVisibility(View.VISIBLE);
					click2.setVisibility(View.VISIBLE);
					tv_guanxi.setText("我的教室");
					click2.setText("解除关系");
				}
			}
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
//				if (v==add){
//					clickaddguanxi();
//				} else
				if (v == click) {// 按钮1
					if (isinvite) {//邀请列表
						//同意邀请
						agreeadd();
					}else {
						Intent intent = new Intent(context, MyChatActivity.class);
						intent.putExtra("name", friend.getUsername());
//			intent.putExtra("id", messageList.get(position).getFuid());
						intent.putExtra("image", friend.getUserpic());
						context.startActivity(intent);
					}
				} else if (v == click2) {// 按钮2
					if (isinvite) {//邀请列表
						//拒绝邀请
						rejectguanxi();
					}else {
						if (guanxi == 1||guanxi==0) {//好友
							clickaddguanxi();
						} else if (guanxi > 1) {
							unguanxi();
						}
					}
				} else
				if (v == icon) {// 头像
					toCenter(position);
				}
			}
		};

		/**
         * 添加关系
		 */
		private void clickaddguanxi() {
			String guanxiname="";
			int guanxi=getguanxi();// 0没关系，1学生，2老师，3教室
			if (guanxi==1)guanxiname="学生";
			else if (guanxi==2) guanxiname="老师";
			else if (guanxi==3) guanxiname="教室";
			if (friend.getGuanxi()==0){
				String []name;
				if (guanxi==0)name=new String[]{"好友"};
				else name=new String[]{"好友",guanxiname};
				showbuttondialog(context,name);
			}else	if (friend.getGuanxi()==1){
				String []name=new String[]{};
				if (guanxi!=0) name=new String[]{guanxiname};
				showbuttondialog(context,name);
			}
		}

		/**
         * 获取关系  0没关系，1学生，2老师，3教室
		 */
		private int getguanxi() {
			if (Data.myinfo.getGroupid()==1||Data.myinfo.getGroupid()==2){
				if (friend.getGroupid()==3){
					return 2;
				}else if (friend.getGroupid()==4){
					return 3;
				}else {
					return 0;
				}
			}else if (Data.myinfo.getGroupid()==3){
				if (friend.getGroupid()==1||friend.getGroupid()==2){
					return 1;
				}else if (friend.getGroupid()==4){
					return 3;
				}else {
					return 0;
				}
			}else if (Data.myinfo.getGroupid()==4){
				if (friend.getGroupid()==1||friend.getGroupid()==2){
					return 1;
				}else if (friend.getGroupid()==3){
					return 2;
				}else {
					return 0;
				}
			}
			return  0;
		}

		/**
		 * 显示弹框
		 * @param context
		 * @param buttonname
         */
		private void  showbuttondialog(Context context,String [] buttonname){
			List<String> mlist=new ArrayList<>();
			for (String s:buttonname				 ) {
				mlist.add(s);
			}
			MyIosDialog.ShowListDialog(context,"",mlist,new MyIosDialog.DialogItemClickListener(){

				@Override
				public void itemClick(String result, int position) {
					if (result.equals("好友")){
						type2=1;
					}else if (result.equals("学生")){
						type2=2;
					}else if (result.equals("老师")){
						type2=3;
					}else if (result.equals("教室")){
						type2=4;
					}
					addguanxi();
				}
			}).show();
		}
		/**跳转到个人主页
		 * @param position */
		protected void toCenter(int position) {
			int group=Integer.valueOf(friend.getGroupid());
			Intent intent=new Intent();
			if (group==1||group==2) {//普通会员和名人
				intent .setClass(context, CelebrityActivity.class);
				intent.putExtra("id",friend.getUserid()+"");
				intent.putExtra("groupid",Integer.valueOf( friend.getGroupid()));
			}else 	if (group==3) {//老师
				intent .setClass(context, TeacherActivity.class);
				intent.putExtra("id", friend.getUserid()+"");
			}else 	if (group==4) {//教室
				intent .setClass(context, ClassRoomActivity.class);
				intent.putExtra("id", friend.getUserid()+"");
			}else 	if (group==5) {//品牌
				intent .setClass(context,BrandDetailActivity.class);
				intent.putExtra("id", friend.getUserid()+"");
			}else {
				intent .setClass(context, CelebrityActivity.class);
				intent.putExtra("id",friend.getUserid()+"");
				intent.putExtra("groupid",Integer.valueOf( friend.getGroupid()));
			}
			context.startActivity(intent);
		}
//		/** 关注 */
//		protected void addattention() {
//			MyProgressDialog.show(context);
//			HttpProxyUtil.addattention(context, friend.getUserid() , new HttpUtil.ResponseListener() {
//
//						@Override
//						public void setResponseHandle(Message2 message) {
//							((BaseActivity) context).toast(message.getInfo());
//							contactsList.remove(position);
//							notifyDataSetChanged();
//							if (message.getInfo().equals("增加关注成功")) {
//							} else {
//							}
//							MyProgressDialog.Cancel();
//						}
//
//					}, null);
//		}
		/** 添加关系*/
		protected void addguanxi() {
			MyProgressDialog.show(context);
			HttpProxyUtil.addguanxi(context, friend.getUserid() ,type2, new HttpUtil.ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					((BaseActivity) context).toast(message.getInfo());
					((DirectoryActivity2) context).contactsMap.get("#").get(friend.getPosition()).setInvite(1);
					friend.setInvite(1);

//					add.setClickable(false);
//					add.setBackgroundResource(R.drawable.tongxunlu_button_unclick);
					click2.setText("已邀请");
					click2.setClickable(false);
					click2.setBackgroundResource(R.drawable.tongxunlu_button_unclick);
					MyProgressDialog.Cancel();
				}

			}, null);
		}
		/** 解除关系*/
		protected void unguanxi() {
			MyProgressDialog.show(context);
			HttpProxyUtil.unguanxi(context, friend.getUserid() , new HttpUtil.ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					((BaseActivity) context).toast(message.getInfo());
					friend.setGuanxi(0);
					MyProgressDialog.Cancel();
					notifyDataSetChanged();
				}

			}, null);
		}
		/** 同意添加关系*/
		protected void agreeadd() {
			MyProgressDialog.show(context);
			HttpProxyUtil.agreeadd(context, friend.getUserid() ,friend.getGuanxi(), new HttpUtil.ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					((BaseActivity) context).toast(message.getInfo());
					(((DirectoryActivity2)context)).getContacts();
					MyProgressDialog.Cancel();
				}

			}, null);
		}
		/** 拒绝添加关系*/
		protected void rejectguanxi() {
			MyProgressDialog.show(context);
			HttpProxyUtil.rejectguanxi(context, friend.getUserid() ,friend.getGuanxi(), new HttpUtil.ResponseListener() {

				@Override
				public void setResponseHandle(Message2 message) {
					((BaseActivity) context).toast(message.getInfo());
					(((DirectoryActivity2)context)).getContacts();
					MyProgressDialog.Cancel();
					notifyDataSetChanged();
				}
			}, null);
		}



	}

}
