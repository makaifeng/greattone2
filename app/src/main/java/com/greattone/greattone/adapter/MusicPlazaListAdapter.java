package com.greattone.greattone.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.activity.WebActivity;
import com.greattone.greattone.activity.celebrity.CelebrityActivity;
import com.greattone.greattone.activity.classroom.ClassRoomActivity;
import com.greattone.greattone.activity.haixuan_and_activitise.VoteDetailsActivity;
import com.greattone.greattone.activity.plaza.ShowPictureActivity;
import com.greattone.greattone.activity.teacher.TeacherActivity;
import com.greattone.greattone.data.ClassId;
import com.greattone.greattone.data.Data;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.dialog.ReplayDialog;
import com.greattone.greattone.dialog.SharePopWindow;
import com.greattone.greattone.entity.Blog;
import com.greattone.greattone.entity.ImageData;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.entity.Pic;
import com.greattone.greattone.entity.UserInfo;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.HttpUtil;
import com.greattone.greattone.util.HttpUtil.ResponseListener;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.util.MessageUtil;
import com.greattone.greattone.widget.MyRoundImageView;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class MusicPlazaListAdapter extends BaseAdapter {
	private Context context;
	private List<Blog> blogsList;
	private int screenWidth;
	private List<ImageData> imageUrlList=new  ArrayList<ImageData>(); 
	private int type;
	public MusicPlazaListAdapter(Context context, List<Blog> blogsList, List<ImageData> imageUrlList, int type) {
		this.context = context;
		this.blogsList = blogsList;
		if (imageUrlList!=null) {
			this.imageUrlList = imageUrlList;
		}
		this.type = type;
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

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_comments, group, false);
			holder.name = (TextView) convertView
					.findViewById(R.id.adapter_comments_name);//
			// holder.level = (ImageView)
			// convertView.findViewById(R.id.iv_level);//
			holder.time = (TextView) convertView
					.findViewById(R.id.adapter_comments_time);//
			holder.delete = (ImageView) convertView
					.findViewById(R.id.adapter_comments_delete);//
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
			holder.video = (ImageView) convertView
					.findViewById(R.id.adapter_comments_videopic);//
			holder.music = (ImageView) convertView
					.findViewById(R.id.iv_music);//
			holder.ll_video = (RelativeLayout) convertView
					.findViewById(R.id.adapter_comments_videopic_vi);//
			holder.ll_music = (RelativeLayout) convertView
					.findViewById(R.id.ll_music);//
			holder.commnum = (TextView) convertView
					.findViewById(R.id.adapter_comments_commnum);//
			holder.ll_comm = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_co);//
			holder.zfnum = (TextView) convertView
					.findViewById(R.id.adapter_comments_reprnum);//
			holder.ll_zf = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_zf);//
			holder.ll_collect = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_sc);//
			holder.collect = (ImageView) convertView
					.findViewById(R.id.adapter_comments_sc_icon);//
			holder.ll_share = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_share);//
			holder.ll_like = (LinearLayout) convertView
					.findViewById(R.id.adapter_comments_like);//
			holder.tv_like = (TextView) convertView
					.findViewById(R.id.tv_like);//
			holder.iv_ad = (ImageView) convertView
					.findViewById(R.id.iv_ad);//
			LayoutParams params = new LayoutParams(
					screenWidth, screenWidth/3);
			holder.iv_ad.setLayoutParams(params);
			holder.icon = (MyRoundImageView) convertView
					.findViewById(R.id.adapter_comments_head);//
			 params = new LayoutParams(
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
		/** 姓名 */
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
		/** 视频 */
		RelativeLayout ll_video;
		ImageView video;
		/** 音乐 */
		RelativeLayout ll_music;
		ImageView music;
		/** 评论 */
		LinearLayout ll_comm;
		TextView commnum;
		/** 转发 */
		LinearLayout ll_zf;
		TextView zfnum;
		/** 收藏 */
		LinearLayout ll_collect;
		ImageView collect;
		/** 分享 */
		LinearLayout ll_share;
		/** 点赞*/
		LinearLayout ll_like;
		TextView tv_like;
		/** 头像 */
		MyRoundImageView icon;
		/** 删除 */
		ImageView delete;
		ImageView iv_ad;
		/** 等级 */
		// ImageView level;
		int position;
		private Blog blog;

		public void setPosition(int position) {
			this.position = position;
			blog = blogsList.get(position);
			iv_ad.setVisibility(View.GONE);
			zhuanfa.setVisibility(View.GONE);
			address.setVisibility(View.GONE);
			vote.setVisibility(View.GONE);
			ll_pic.setVisibility(View.GONE);
			ll_video.setVisibility(View.GONE);
			ll_music.setVisibility(View.GONE);
			if (position%3==2&&type==1&&imageUrlList.size()>0) {
				iv_ad.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyFile(imageUrlList.get((position/3)%imageUrlList.size()).getPic(), iv_ad);
			}
			
			if (blogsList.get(position).getUserInfo() != null
					&& !blogsList.get(position).getUserInfo().isEmpty()) {
				UserInfo info = JSON.parseObject(blogsList.get(position)
						.getUserInfo(), UserInfo.class);
				name.setText(info.getUsername() + "  |  "
						+ MessageUtil.getIdentity(info) + "  |  "
						+ info.getLevel().getName());
				// ImageLoaderUtil.getInstance().setImagebyurl(
				// info.getLevel().getPic(), level);
				ImageLoaderUtil.getInstance().setImagebyurl(info.getUserpic(),
						icon);
			}
			icon.setOnClickListener(lis);
			ll_like.setOnClickListener(lis);
			commnum.setText(blogsList.get(position).getPlnum());
			zfnum.setText(blogsList.get(position).getResendnum());
			time.setText(blogsList.get(position).getNewstime());
			tv_like.setText(blogsList.get(position).getDiggtop());
			
			// SimpleDateFormat format = new
			
			// SimpleDateFormat("yyyy-MM-dd HH:mm",
			// Locale.CHINA);
			// time.setText(format.format(new Date(blogsList.get(position)
			// .getCtime() * 1000)));
			if (blogsList.get(position).getClassid()== ClassId.音乐海选_图片_ID) {// 海选图片报名
				ll_pic.setVisibility(View.VISIBLE);
				ll_pic.removeAllViews();
				if (blogsList.get(position).getMorepic() != null) {
					addPic(JSON.parseArray(
							blogsList.get(position).getMorepic(), Pic.class));
				}
				vote.setVisibility(View.VISIBLE);
				vote.setOnClickListener(lis);
				title.setText(blogsList.get(position).getHai_name() +context.getResources().getString(R.string.的报名));
				content.setText(blogsList.get(position).getHai_petition());
			}else
			if (blogsList.get(position).getClassid()== ClassId.音乐海选_视频_ID) {// 海选视频报名
				ll_video.setVisibility(View.VISIBLE);
				vote.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getHai_photo(), video);
				vote.setOnClickListener(lis);
				title.setText(blogsList.get(position).getHai_name() +context.getResources().getString(R.string.的报名));
				content.setText(blogsList.get(position).getHai_petition());
			} else {
				title.setText(blogsList.get(position).getTitle());
				content.setText(blogsList.get(position).getSmalltext());
			}
			// ll_video.setOnClickListener(lis);
			iv_ad.setOnClickListener(lis);
			ll_comm.setOnClickListener(lis);
			ll_zf.setOnClickListener(lis);
			ll_collect.setOnClickListener(lis);
			ll_share.setOnClickListener(lis);
			if (blogsList.get(position).getClassid()== ClassId.音乐广场_视频_ID) {// 视频
				ll_video.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getThumbnail(), video);
			} else if (blogsList.get(position).getClassid()== ClassId.音乐广场_音乐_ID) {// 音乐
				title.setText(blogsList.get(position).getTitle());
				ll_music.setVisibility(View.VISIBLE);
				ImageLoaderUtil.getInstance().setImagebyurl(
						blogsList.get(position).getThumbnail(), music);
			} else if (blogsList.get(position).getClassid()== ClassId.音乐广场_图片_ID) {// 图片
				ll_pic.setVisibility(View.VISIBLE);
				ll_pic.removeAllViews();
				if (blogsList.get(position).getMorepic() != null) {
					addPic(JSON.parseArray(
							blogsList.get(position).getMorepic(), Pic.class));
					// addPic2(blogsList.get(position).getPic());
				}
			}
			if (blogsList.get(position).getIsfava() == 1) {
				collect.setImageResource(R.drawable.icon_scon);
			} else {
				collect.setImageResource(R.drawable.icon_stars);
			}
		}

		// /** 添加图片 */
		// private void addPic2(List<Pic> list) {
		// for (int i = 0; i < list.size(); i++) {
		// ImageView imageView = new ImageView(context);
		// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		// (screenWidth - DisplayUtil.dip2px(context, 16)) / 4,
		// (screenWidth - DisplayUtil.dip2px(context, 16)) / 4);
		//
		// params.setMargins(DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5));
		// imageView.setPadding(DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5),
		// DisplayUtil.dip2px(context, 5));
		// imageView.setLayoutParams(params);
		// imageView.setScaleType(ScaleType.FIT_XY);
		// com.greattone.greattone.util.ImageLoaderUtil.getInstance()
		// .setImagebyurl(list.get(i).getThumb(), imageView);
		// ll_pic.addView(imageView);
		// }
		// }

		/** 添加图片 */
		private void addPic(final List<Pic> list) {
			if (list.size() == 0) {
				return;
			}
	int	num= list.size() >4?4:list.size();
			for (int i = 0; i <1 ; i++) {
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
					imageView.setLayoutParams(params);
					imageView.setScaleType(ScaleType.FIT_XY);
					final int mPosition = j + i * 4;
					ImageLoaderUtil.getInstance()
							.setImagebyurl(list.get(mPosition).getThumbnail(),
									imageView);
					// 图片点击
					imageView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 显示图片
							ArrayList<String> mList = new ArrayList<String>();
							for (int k = 0; k < list.size(); k++) {
								mList.add(list.get(k).getUrl());
							}
							Intent intent = new Intent(context,
									ShowPictureActivity.class);
							intent.putStringArrayListExtra("uriList", mList);
							intent.putExtra("position", mPosition);
							((Activity) context).startActivity(intent);
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
					Intent intent = new Intent(context, VideoPlayActivity.class);
					intent.putExtra("url",FileUtil.getNetWorkUrl(  blogsList.get(position).getShipin()));
					context.startActivity(intent);
				} else if (v == ll_comm) {// 评论
					comment();
				} else if (v == ll_collect) {// 收藏
					collect();
				} else if (v == ll_share) {// 分享
					share();
				} else if (v == ll_zf) {// 转发
					zhuanfa();
				} else if (v == vote) {// 投票
					vote();
				} else if (v == icon) {// 头像
					toCenter();
				} else if (v == ll_like) {// 点赞
					toLike();
				} else if (v == iv_ad) {// 广告图
					toWeb(imageUrlList.get((position/3)%imageUrlList.size()));
				}
			}
		};

		/** 转发 */
		protected void zhuanfa() {
			ReplayDialog localReplayDialog = new ReplayDialog(context);
			localReplayDialog.setSendText(context.getResources().getString(R.string.转发));
			localReplayDialog.setContentHint(context.getResources().getString(R.string.转发));
			localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

				@Override
				public void OnReplay(String paramString) {
					MyProgressDialog.show(context);
					// String msg = "uid=" + Data.userData.getUid() + "&id="
					// + blogsList.get(position).getId() + "&token="
					// + Data.userData.getToken() + "&content="
					// + paramString;
					// ((BaseActivity) context).addRequest(HttpUtil
					// .sendStringToServerByGet(context,
					// HttpConstants.BLOG_DYNAMIC_URL, msg,
					// new MyStringResponseHandle() {
					//
					// @Override
					// public void setServerErrorResponseHandle(
					// com.greattone.greattone.entity.Message message) {
					//
					// }
					//
					// @Override
					// public void setResponseHandle(
					// com.greattone.greattone.entity.Message message) {
					// ((BaseActivity) context)
					// .toast("转发成功！");
					// int i = 1 + Integer.parseInt(zfnum
					// .getText().toString());
					// zfnum.setText(i + "");
					// MyProgressDialog.Cancel();
					// }
					//
					// @Override
					// public void setErrorResponseHandle(
					// VolleyError error) {
					//
					// }
					// }));
				}
			});
			localReplayDialog.show();
		}
		/**广告跳转*/
		protected void toWeb(ImageData imageData) {
			if (!TextUtils.isEmpty((imageData.getUrl()))) {
				Intent intent=new Intent(context, WebActivity.class);
				intent.putExtra("urlPath", imageData.getUrl());
				intent.putExtra("title", imageData.getTitle());
				context.startActivity(intent);
			}
		}

/**点赞
 * @param v */
		protected void toLike() {
			MyProgressDialog.show(context);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("api", "comment/dianzan");
			map.put("classid", blog.getClassid()+"");
			map.put("id", blog.getId()+"");
			map.put("loginuid", Data.user.getUserid());
			map.put("logintoken", Data.user.getToken());
			((BaseActivity) context).addRequest(HttpUtil.httpConnectionByPost(
					context, map, new ResponseListener() {

						@Override
						public void setResponseHandle(Message2 message) {
							blogsList.get(position).setDiggtop(Integer.valueOf(blogsList.get(position).getDiggtop())+1+"");
							tv_like.setText(blogsList.get(position).getDiggtop());
							((BaseActivity) context).toast(message.getInfo());
							MyProgressDialog.Cancel();
						}
					}, null));
		}

		/** 跳转到个人中心 */
		protected void toCenter() {
			int group = JSON.parseObject(blog.getUserInfo(), UserInfo.class)
					.getGroupid();
			Intent intent = new Intent();
			if (group == 1 || group == 2) {// 普通会员和名人
				intent.setClass(context, CelebrityActivity.class);
				intent.putExtra("id", blog.getUserid() + "");
				intent.putExtra("groupid",blog.getGroupid());
			} else if (group == 3) {// 老师
				intent.setClass(context, TeacherActivity.class);
				intent.putExtra("id", blog.getUserid() + "");
			} else if (group == 4) {// 教室
				intent.setClass(context, ClassRoomActivity.class);
				intent.putExtra("id", blog.getUserid() + "");
			}
			context.startActivity(intent);
		}

		/** 投票 */
		protected void vote() {
			Intent intent = new Intent(context, VoteDetailsActivity.class);
			intent.putExtra("id", blog.getId() + "");
			intent.putExtra("classid", "73");
			context.startActivity(intent);
		}

		/** 评论 */
		protected void comment() {
			ReplayDialog localReplayDialog = new ReplayDialog(context);
			localReplayDialog.setListener(new ReplayDialog.OnReplayListener() {

				@Override
				public void OnReplay(String text) {
					MyProgressDialog.show(context);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("api", "comment/post");
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
											MyProgressDialog.Cancel();
											blog.setPlnum((Integer.valueOf(blog.getPlnum())+1)+"");
											commnum.setText(blog.getPlnum());
										}

									}, null));
				}
			});
			localReplayDialog.show();
		}

		/** 收藏 */
		protected void collect() {
			// String url;
			// if (blogsList.get(position).getCollect().equals("1")) {
			// url = HttpConstants.BLOG_UNCOLLECT_URL;
			// } else {
			// url = HttpConstants.BLOG_COLLECT_URL;
			// }
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
							if (blogsList.get(position).getIsfava() == 1) {
								collect.setImageResource(R.drawable.icon_stars);
								blogsList.get(position).setIsfava(0);
							} else {
								collect.setImageResource(R.drawable.icon_scon);
								blogsList.get(position).setIsfava(1);
							}
							MyProgressDialog.Cancel();
						}

					}, null));
		}

		/** 分享 */
		protected void share() {
			String titlePic = null;
			if (blogsList.get(position).getClassname().equals("海选投票")) {// 海选投票
				titlePic = JSON.parseObject(blog.getUserInfo(), UserInfo.class)
						.getUserpic();
				SharePopWindow.build(context).setTitle(blogsList.get(position).getHai_name() +context.getResources().getString(R.string.的报名))
						.setContent(blogsList.get(position).getHai_petition())
						.setTOargetUrl(blog.getTitleurl())
						.setIconPath(titlePic).show();
			} else {
				if (blogsList.get(position).getClassname().equals("视频")) {// 视频
					titlePic = blog.getTitlepic();
				} else if (blogsList.get(position).getClassname().equals("音乐")) {// 音乐
					titlePic = JSON.parseObject(blog.getUserInfo(),
							UserInfo.class).getUserpic();
				} else if (blogsList.get(position).getClassname().equals("图片")) {// 图片
					titlePic = JSON.parseArray(blog.getMorepic(), Pic.class)
							.get(0).getUrl();
				}
//				ShareUtil.open(context, blog.getTitle(), blog.getSmalltext(), titlePic, blog.getTitleurl(), umShareListener);
				SharePopWindow.build(context).setTitle(blog.getTitle())
						.setContent(blog.getSmalltext())
						.setTOargetUrl(blog.getTitleurl())
						.setIconPath(titlePic).show();
			}
		}
	}
UMShareListener umShareListener=new UMShareListener() {
		
		@Override
		public void onResult(SHARE_MEDIA arg0) {
			((BaseActivity)context).toast("分享成功");
			MyProgressDialog.Cancel();
	
		}
		
		@Override
		public void onError(SHARE_MEDIA arg0, Throwable arg1) {
			((BaseActivity)context).toast("分享失败");
			MyProgressDialog.Cancel();
		}
		
		@Override
		public void onCancel(SHARE_MEDIA arg0) {
			((BaseActivity)context).toast("分享取消");
			MyProgressDialog.Cancel();
		}
	};

}
