package com.greattone.greattone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.Listener.OnBtnItemClickListener;
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.plaza.ShowPictureActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.PostDeletePopu;
import com.greattone.greattone.dialog.PostDeletePopu.onDeleteBack;
import com.greattone.greattone.dialog.ReplayDialog;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Pic;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPostListAdapter extends BaseAdapter {
	private Context context;
	private List<Blog> blogsList;
	private int screenWidth;
	private OnBtnItemClickListener btnItemClickListener;

	public MyPostListAdapter(Context context, List<Blog> blogsList) {
		this.context = context;
		this.blogsList = blogsList;
		screenWidth = ((BaseActivity) context).screenWidth;
	}

	@Override
	public int getCount() {
		return blogsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
public void setList(List<Blog> blogsList){
	this.blogsList=blogsList;
	notifyDataSetChanged();
}
	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_comments, group, false);
			holder.name = (TextView) convertView
					.findViewById(R.id.adapter_comments_name);//
			holder.time = (TextView) convertView
					.findViewById(R.id.adapter_comments_time);//
			holder.delete = (ImageView) convertView
					.findViewById(R.id.adapter_comments_delete);//
			holder.iv_like = (ImageView) convertView
					.findViewById(R.id.iv_like);//
			holder.vote = (TextView) convertView.findViewById(R.id.tv_my_vote);// 投票
			holder.address = (TextView) convertView
					.findViewById(R.id.adapter_comments_city);//
			holder.zhuanfa = (TextView) convertView
					.findViewById(R.id.adapter_comments_zhuanfa);//
			holder.content = (TextView) convertView
					.findViewById(R.id.adapter_comments_content);//
			holder.title = (TextView) convertView
					.findViewById(R.id.adapter_comments_title);//
			holder.ll_pic = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_ll);//
			holder.ll_music = (RelativeLayout) convertView
					.findViewById(R.id.ll_music);//
			holder.music = (ImageView) convertView
					.findViewById(R.id.iv_music);//
			holder.video = (ImageView) convertView
					.findViewById(R.id.adapter_comments_videopic);//
			holder.ll_video = (RelativeLayout) convertView
					.findViewById(R.id.adapter_comments_videopic_vi);//
			holder.commnum = (TextView) convertView
					.findViewById(R.id.adapter_comments_commnum);//
			holder.ll_comm = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_co);//
			holder.zfnum = (TextView) convertView
					.findViewById(R.id.adapter_comments_reprnum);//
			holder.tv_like = (TextView) convertView
					.findViewById(R.id.tv_like);//
			holder.ll_zf = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_zf);//
			holder.ll_collect = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_sc);//
			holder.collect = (ImageView) convertView
					.findViewById(R.id.adapter_comments_sc_icon);//
			holder.ll_share = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_share);//
			holder.icon = (MyRoundImageView) convertView
					.findViewById(R.id.adapter_comments_head);//
			LayoutParams params = new LayoutParams(
					DisplayUtil.dip2px(context, 60), DisplayUtil.dip2px(
							context, 60));
			holder.icon.setLayoutParams(params);
			holder.icon.setRadius(DisplayUtil.dip2px(context, 15));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		/** 教室姓名 */
		TextView name;
		/** 地址 */
		TextView address;
		/** 投票 */
		TextView vote;
		/** 时间 */
		TextView time;
		/** 转发提示语 */
		TextView zhuanfa;
		/** 标题 */
		TextView title;
		/** 内容 */
		TextView content;
		/** 图片集 */
		LinearLayout ll_pic;
		/** 音乐 */
		RelativeLayout ll_music;
		ImageView music;
		/** 视频 */
		RelativeLayout ll_video;
		ImageView video;
		/** 评论 */
		LinearLayout ll_comm;
		TextView commnum;
		/** 转发 */
		LinearLayout ll_zf;
		TextView zfnum;
		/** 点赞*/
//		LinearLayout ll_like;
		TextView tv_like;
		ImageView iv_like;
		/** 收藏 */
		LinearLayout ll_collect;
		ImageView collect;
		/** 分享 */
		LinearLayout ll_share;
		/** 头像 */
		MyRoundImageView icon;
		/** 删除 */
		ImageView delete;
		int position;
		private Blog blog;

		public void setPosition(int position) {
			this.position = position;
			blog = blogsList.get(position);
			UserInfo userInfo = JSON.parseObject(blog.getUserInfo(),
					UserInfo.class);
			zhuanfa.setVisibility(View.GONE);
			address.setVisibility(View.GONE);
			vote.setVisibility(View.GONE);
			ll_pic.setVisibility(View.GONE);
			ll_video.setVisibility(View.GONE);
			delete.setVisibility(View.VISIBLE);
			name.setText(blogsList.get(position).getUsername());
			ImageLoaderUtil.getInstance().setImagebyurl(userInfo.getUserpic(), icon);
			time.setText(blogsList.get(position).getNewstime());
			tv_like.setText(blogsList.get(position).getDiggtop());
			// SimpleDateFormat format = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm",
			// Locale.CHINA);
			// time.setText(format.format(new Date(blogsList.get(position)
			// .getCtime() * 1000)));
			title.setText(blogsList.get(position).getTitle());
			content.setText(blogsList.get(position).getSmalltext());
			commnum.setText(blogsList.get(position).getPlnum() + "");
			zfnum.setText(blogsList.get(position).getResendnum() + "");
//			ll_video.setOnClickListener(lis);
//			ll_comm.setOnClickListener(lis);
//			ll_zf.setOnClickListener(lis);
//			ll_collect.setOnClickListener(lis);
//			ll_share.setOnClickListener(lis);
			delete.setOnClickListener(lis);

			if (blogsList.get(position).getClassid()== ClassId.音乐海选_图片_ID) {// 海选图片报名
				ll_pic.setVisibility(View.VISIBLE);
				ll_pic.removeAllViews();
				if (blogsList.get(position).getMorepic() != null) {
					addPic(JSON.parseArray(
							blogsList.get(position).getMorepic(), Pic.class));
				}
//				vote.setVisibility(View.VISIBLE);
//				vote.setOnClickListener(lis);
//				title.setText(blogsList.get(position).getHai_name() +context.getResources().getString(R.string.的报名));
				title.setText(blogsList.get(position).getHai_name()+"参加"+blogsList.get(position).getHai_title()+"的比赛");
				content.setText(blogsList.get(position).getHai_petition());
				iv_like.setImageResource(R.drawable.toupiaoicon);
			}else
			if (blogsList.get(position).getClassid()== ClassId.音乐海选_视频_ID) {// 海选视频报名
				ll_video.setVisibility(View.VISIBLE);
//				vote.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getHai_photo(), video);
//				vote.setOnClickListener(lis);
//				title.setText(blogsList.get(position).getHai_name() +context.getResources().getString(R.string.的报名));
				title.setText(blogsList.get(position).getHai_name()+"参加"+blogsList.get(position).getHai_title()+"的比赛");
				content.setText(blogsList.get(position).getHai_petition());
				iv_like.setImageResource(R.drawable.toupiaoicon);
			} else {
				iv_like.setImageResource(R.drawable.icon_like);
				title.setText(blogsList.get(position).getTitle());
				content.setText(blogsList.get(position).getSmalltext());
			}
			if (blogsList.get(position).getClassname().equals("视频")) {// 视频
				ll_video.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getTitlepic(), video);
			} else if (blogsList.get(position).getClassname().equals("音乐")) {// 音乐
				ll_music.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getThumbnail(), music);
			} else if (blogsList.get(position).getClassname().equals("图片")) {// 图片
				ll_pic.setVisibility(View.VISIBLE);
				ll_pic.removeAllViews();
				if (blogsList.get(position).getMorepic() != null) {
					addPic(JSON.parseArray(
							blogsList.get(position).getMorepic(), Pic.class));
				}
			}
			if (blogsList.get(position).getIsfava() == 1) {
				collect.setImageResource(R.drawable.icon_scon);
			} else {
				collect.setImageResource(R.drawable.icon_stars);
			}
		}

		/** 添加图片 */
		private void addPic(final List<Pic> list) {
			if (list.size()==0) {
				return;
			}
			int	num= list.size() >4?4:list.size();
			for (int i = 0; i <1; i++) {
//		for (int i = 0; i <= (list.size() - 1) / 4; i++) {
//				int num = 4;
//				if (i == (list.size() - 1) / 4) {
//					if (list.size() % 4 != 0) {
//						num = list.size() % 4;
//					}
//				}
				LinearLayout layout = new LinearLayout(context);
				LayoutParams layoutParams = new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				layoutParams.topMargin = 20;
				layout.setLayoutParams(layoutParams);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				for (int j = 0; j < num; j++) {
					ImageView imageView = new ImageView(context);
					LayoutParams params = new LayoutParams(
							(screenWidth - DisplayUtil.dip2px(context,
									5 * 5 + 8)) / 4,
							(screenWidth - DisplayUtil.dip2px(context,
									5 * 5 + 8)) / 4);
					if (j == 3) {
						params.setMargins(DisplayUtil.dip2px(context, 5), 0,
								DisplayUtil.dip2px(context, 5), 0);
					} else {
						params.setMargins(DisplayUtil.dip2px(context, 5), 0, 0,
								0);
					}
				final int	mPosition=j + i * 4;
					imageView.setLayoutParams(params);
					imageView.setScaleType(ScaleType.FIT_XY);
					ImageLoaderUtil.getInstance()
					.setImagebyurl(list.get(mPosition).getThumbnail(),
							imageView);
					imageView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							ArrayList<String> mList=new ArrayList<String>();
							for (int k = 0; k < list.size(); k++) {
								mList.add(list.get(k ).getThumbnail());
							}
							Intent intent=new Intent(context, ShowPictureActivity.class);
							intent.putStringArrayListExtra("uriList", mList);
							intent.putExtra("position",mPosition);
							((Activity)context).startActivity(intent);
						}
					});
					layout.addView(imageView);

				}
				ll_pic.addView(layout);
			}

		}

		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == ll_video) {// 视频

				} else if (v == ll_comm) {// 评论
//					comment();
				} else if (v == ll_collect) {// 收藏
//					collect();
				} else if (v == ll_share) {// 分享
//					share();
				} else if (v == ll_zf) {// 转发
//					zhuanfa();
				} else if (v == delete) {// 删除/编辑
					PostDeletePopu popu=new PostDeletePopu(context, delete, new onDeleteBack() {
						
						@Override
						public void OnEdit() {
							edit();
						}
						

						@Override
						public void OnDelete() {
							delete();
						}

					});
					popu.show();
				}
			}
		};
		
		private void delete() {
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "post/ecms");
			map.put("enews", "MDelInfo");
			map.put("classid", blog.getClassid() + "");
			map.put("id", blog.getId() + "");
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			((BaseActivity) context).addRequest(HttpUtil
					.httpConnectionByPost(context, map,
							new ResponseListener() {

								@Override
								public void setResponseHandle(
										Message2 message) {
									((BaseActivity) context)
											.toast(message.getInfo());
									MyProgressDialog.Cancel();
									if (btnItemClickListener!=null) {
										btnItemClickListener.onItemClick(delete, position);
									}
								}

							}, null));
		}

		private void edit() {
			
		}
		/** 转发 */
		protected void zhuanfa() {
			ReplayDialog localReplayDialog = new ReplayDialog(context);
			localReplayDialog.setSendText("转发");
			localReplayDialog.setContentHint("转发...");
			localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

				@Override
				public void OnReplay(String paramString) {
					MyProgressDialog.show(context);
//					String msg = "uid=" + Data.userData.getUid() + "&id="
//							+ blogsList.get(position).getId() + "&token="
//							+ Data.userData.getToken() + "&content="
//							+ paramString;
//					((BaseActivity) context).addRequest(HttpUtil
//							.sendStringToServerByGet(context,
//									HttpConstants.BLOG_DYNAMIC_URL, msg,
//									new MyStringResponseHandle() {
//
//										@Override
//										public void setServerErrorResponseHandle(
//												com.greattone.greattone.entity.Message message) {
//
//										}
//
//										@Override
//										public void setResponseHandle(
//												com.greattone.greattone.entity.Message message) {
//											((BaseActivity) context)
//													.toast("转发成功！");
//											int i = 1 + Integer.parseInt(zfnum
//													.getText().toString());
//											zfnum.setText(i + "");
//											MyProgressDialog.Cancel();
//										}
//
//										@Override
//										public void setErrorResponseHandle(
//												VolleyError error) {
//
//										}
//									}));
				}
			});
			localReplayDialog.show();
		}

		/** 评论 */
		protected void comment() {
			ReplayDialog localReplayDialog = new ReplayDialog(context);
			localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

				@Override
				public void OnReplay(String text) {
					MyProgressDialog.show(context);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("api", "info/list");
					map.put("classid", blog.getClassid() + "");
					map.put("id", blog.getId() + "");
					map.put("saytext", text);
					map.put("loginuid", Data.user.getUserid());
					map.put("logintoken", Data.user.getToken());
					((BaseActivity) context).addRequest(HttpUtil
							.httpConnectionByPost(context, map,
									new ResponseListener() {

										@Override
										public void setResponseHandle(
												Message2 message) {
											((BaseActivity) context)
													.toast(message.getInfo());
										}

									}, null));
				}
			});
			localReplayDialog.show();
		}

		/** 收藏 */
		protected void collect() {
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "fava/addFava");
			map.put("classid", blog.getClassid() + "");
			map.put("id", blog.getId() + "");
			if (Data.favaList.size() > 0) {
				map.put("cid", Data.favaList.get(0).getCid() + "");
			}
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(
					context, map, new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							((BaseActivity) context).toast(message.getInfo());
							MyProgressDialog.Cancel();
							if (blogsList.get(position).getIsfava() == 1) {
								collect.setImageResource(R.drawable.icon_stars);
								blogsList.get(position).setIsfava(0);
							} else {
								collect.setImageResource(R.drawable.icon_scon);
								blogsList.get(position).setIsfava(1);
							}
						}

					}, null));
		}

		/** 分享 */
		protected void share() {

		}
	}
	public void setOnBtnItemClickListener(OnBtnItemClickListener btnItemClickListener){
		this.btnItemClickListener=btnItemClickListener;
	}
}
