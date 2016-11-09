package com.greattone.greattone.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.greattone.greattone.R;
import com.greattone.greattone.Listener.ActivityBackListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.VideoPlayActivity;
import com.greattone.greattone.activity.plaza.ShowPictureActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.BitmapUtil;
import com.greattone.greattone.util.DisplayUtil;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.ImageLoaderUtil;
import com.greattone.greattone.widget.MyRoundImageView;
import com.kf_test.picselect.GalleryActivity;

public class PostGridAdapter extends BaseAdapter {
	private Context context;
	String FILE_PATH;
	private ArrayList<Picture> pathList=new ArrayList<Picture>(); 
//	private ArrayList<String> mPathList=new ArrayList<String>(); 
	private int screenWidth;
//	private long lastTime;
	private int type;
	private int maxsize;
//	private OnBtnItemClickListener itemClickListener;
	String imgName="icon.png";
	public PostGridAdapter(Context context, int type,int maxsize) {
		this.context = context;
		this.type = type;
		this.maxsize = maxsize;
		screenWidth = ((BaseActivity) context).screenWidth;
		getActivityBack();
	}
	public PostGridAdapter(Context context, ArrayList<Picture> pathList, int type,int maxsize) {
		this.context = context;
//		this.mPathList.addAll(pathList);
		this.pathList = pathList;
		this.type = type;
		this.maxsize = maxsize;
		screenWidth = ((BaseActivity) context).screenWidth;
		getActivityBack();
	}

	public void setList2(ArrayList<Picture> pathList) {
		this.pathList.clear();
		this.pathList.addAll(pathList);
		notifyDataSetChanged();
	}
	public void setList(ArrayList<String> pathList) {
		ArrayList<Picture> mPathList = new ArrayList<Picture>();
		for (Picture picture : this.pathList) {
			if (picture.getType()==1) {
				mPathList.add(picture);
			}
		}
		this.pathList.clear();
		this.pathList.addAll(mPathList);
		 for (String string : pathList) {
			Picture picture=new Picture();
			picture.setPicUrl(string);
			picture.setType(0);
			this.pathList.add(picture);
		}
		notifyDataSetChanged();
	}
	/**
	 * 获取要上传的照片集合
	 * @return 
	 */
	public ArrayList<Picture> getList() {
		ArrayList<Picture> mlist = new ArrayList<Picture>();
		for (Picture pic : pathList) {
			if (pic.getType()==0) {
				mlist.add(pic);
			}
		}
		return mlist;
	}
	/**
	 * 获取网络的照片
	 * @return 返回以"::::::"隔开的路径字符串
	 */
	public String getUrlList() {
		String photo="";
		for (Picture pic : pathList) {
			if (pic.getType()==1) {
				photo=photo+pic.getPicUrl()+"::::::";
			}
		}
		return photo;
	}

	@Override
	public int getCount() {
		return pathList.size() >= maxsize ? pathList.size() : pathList.size() + 1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		ViewHolder holder = null;
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_post_pic, group, false);
			holder.iv_del = ((ImageView) convertView.findViewById(R.id.iv_del));
			holder.rl_view = ((RelativeLayout) convertView
					.findViewById(R.id.rl_view));
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(
					(screenWidth - DisplayUtil.dip2px(context, 10)) / 4,
					(screenWidth - DisplayUtil.dip2px(context, 10)) /4);
			holder.rl_view.setLayoutParams(params);
			holder.rl_view.setPadding(DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 5),
					DisplayUtil.dip2px(context, 5));
			holder.iv_play = ((ImageView) convertView
					.findViewById(R.id.iv_play));
			holder.iv_pic = ((MyRoundImageView) convertView
					.findViewById(R.id.iv_pic));
			holder.iv_pic.setRadius(15);
			convertView.setTag(holder);
		holder.setPosition(position);
		return convertView;
	}

	class ViewHolder {
		RelativeLayout rl_view;
		private MyRoundImageView iv_pic;
		private ImageView iv_play;
		private ImageView iv_del;
		int position;

		public void setPosition( int position) {
			this.position = position;
			iv_pic.setOnClickListener(lis);
			iv_del.setOnClickListener(lis);
			iv_del.setVisibility(View.VISIBLE);
			if (position == pathList.size()) {//最后一张
				iv_pic.setImageResource(R.drawable.icon_sc);
				iv_play.setVisibility(View.GONE);
				iv_del.setVisibility(View.GONE);
				return;
			}
			if (type == 0) {// 图片
				iv_play.setVisibility(View.GONE);
				if (pathList.get(position).getType()==1) {//网络图片
					ImageLoaderUtil.getInstance().setImagebyurl(
							 pathList.get(position).getPicUrl(), iv_pic);
				}else		//本地图片
				ImageLoaderUtil.getInstance().setImagebyurl(
						"file://" + pathList.get(position).getPicUrl(), iv_pic);
			} else if (type == 1) {// 语音
			} else if (type == 2) {// 视频
				iv_play.setVisibility(View.VISIBLE);
				iv_pic.setImageBitmap(BitmapUtil.getVideoThumbnail(  pathList.get(position).getPicUrl(),300,300,Thumbnails.MINI_KIND));
			}
		}

		OnClickListener lis = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == iv_pic) {//点击图片
					if (type == GalleryActivity.TYPE_PICTURE) {// 图片
						if (position == pathList.size()) {
							imgName=System.currentTimeMillis()+".png";
							BitmapUtil.getPictures(context, imgName,maxsize,pathList);
//				              Intent intent = new Intent(context, PhotoPickerActivity.class);
//				                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
//				                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE,  PhotoPickerActivity.MODE_MULTI);
//				                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 8);
//				                ((Activity) context).    startActivityForResult(intent, 1);
						} else {
							showPic();
						}
					} else if (type == 1) {// 语音
					} else if (type == 2) {// 视频
						if (position == pathList.size()) {
					BitmapUtil.getVideos(context, 1);
						} else {
							showVideo();
						}
					}
				} else if (v == iv_del) {//删除
//					 ArrayList<Picture> mList=new ArrayList<Picture>(); 
//					mList.addAll(pathList);
//					if (position<mPathList.size()) {
//						mPathList.remove(position);
//					}else {
						pathList.remove(position);
//					}
						notifyDataSetChanged();
				}
			}
		};

//		protected void selectPic() {
//			MyIosDialog.ShowBottomDialog(context, "", new String[] { "去拍照",
//					"去相册" }, new DialogItemClickListener() {
//
//				@Override
//				public void itemClick(String result, int position) {
//					if (result.equals("去拍照")) {
//					    Intent  intent = new Intent();
//					                     // 指定开启系统相机的Action
//					               intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//					                 intent.addCategory(Intent.CATEGORY_DEFAULT);
//					                 FILE_PATH=FileUtil.getLocalImageUrl(context, "icon.png");
//					                  // 根据文件地址创建文件
//					                 File file = new File(FILE_PATH);
//					                    // 设置系统相机拍摄照片完成后图片文件的存放地址
//					                      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//					                      ((Activity) context).    startActivityForResult(intent, 0);
//					} else if (result.equals("去相册")) {
//						Intent intent = new Intent(context,
//								GalleryActivity.class);
//						intent.putExtra(Constants.EXTRA_PHOTO_LIMIT,
//								8 - pathList.size());// 最大选择数
//						intent.putExtra("type", type);// 选择类型
//						((Activity) context).startActivityForResult(intent, 1);
//					}
//				}
//			});
//		}

		/** 播放 */
		protected void showVideo() {
			Intent intent = new Intent(context, VideoPlayActivity.class);
			intent.putExtra("url",  "file://"+pathList.get(position));
			context.startActivity(intent);
		}


		/** 显示图片 */
		protected void showPic() {
			ArrayList<String > mList=new ArrayList<String>();
			for (Picture pic : pathList) {
				if (pic.getType()==1) {
					mList.add(pic.getPicUrl());
				}else
				mList.add("file://"+pic);
			}
			Intent intent=new Intent(context, ShowPictureActivity.class);
			intent.putStringArrayListExtra("uriList", mList);
			intent.putExtra("position",position);
			((Activity)context).startActivity(intent);
		}

	}

//	public void setOna(OnBtnItemClickListener itemClickListener) {
//		this.itemClickListener = itemClickListener;
//
//	}
	/**
	 * onActivityResult回调
	 */
	private void getActivityBack() {
		((BaseActivity)context).setActivityBack(new ActivityBackListener() {
			
			@Override
			public void activityBack(int requestCode, int resultCode, Intent data) {
				ArrayList<String> mList = new ArrayList<String>();
				if (resultCode == Activity.RESULT_OK && requestCode == 1) {// 相册选取视频或图片
				 mList = data
							.getStringArrayListExtra(Constants.EXTRA_PHOTO_PATHS);
//				 if (type==GalleryActivity.TYPE_VIDEO) {
//					long size=data.getLongExtra("size", 0);
//					String s=FileUtil.getFormatSize(size);
//				}
					setList(mList);
				} else if (resultCode ==  Activity.RESULT_OK && requestCode == 0) {// 录制视频或拍照
					if (type==GalleryActivity.TYPE_PICTURE) {//拍照
						mList.add(FileUtil.getLocalImageUrl(context, imgName));
						setList(mList);
					}else if (type==GalleryActivity.TYPE_VIDEO) {//录制视频
						mList.add(data.getStringExtra("data"));
						setList(mList);
					}
		
				}
			}
		});
	}

}
