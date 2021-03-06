package com.greattone.greattone.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.greattone.greattone.R;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.activity.MusicChoiceActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.dialog.MyIosDialog.DialogItemClickListener;
import com.greattone.greattone.entity.Picture;
import com.greattone.greattone.util.FileUtil;
import com.greattone.greattone.util.Permission;
import com.kf_test.picselect.GalleryActivity;

import java.io.File;
import java.util.ArrayList;

public class SelectPictureDialog {

	String FILE_PATH;
	Context context;
	int type;
	int maxSize;
	ArrayList<Picture> pathList=new ArrayList<Picture>();
	String imgName;
//	private PostGridAdapter adapter;

	public SelectPictureDialog(Context context, int type, int maxSize
//			, PostGridAdapter postGridAdapter
			) {
		this.context = context;
		this.maxSize = maxSize;
		this.type = type;
//		if (postGridAdapter!=null) {
//			this.adapter = postGridAdapter;
//		}
	}
	public SelectPictureDialog(Context context,  String imgName,int type, int maxSize, ArrayList<Picture> pathList
//			,PostGridAdapter postGridAdapter
			) {
		this.context = context;
		this.imgName = imgName;
		this.maxSize = maxSize;
		this.type = type;
		this.pathList = pathList;
//		if (postGridAdapter!=null) {
//			this.adapter = postGridAdapter;
//		}
	}

	public void show() {
		if (type == GalleryActivity.TYPE_VIDEO) {
			selectVideo();
		} else if (type == GalleryActivity.TYPE_MUSIC) {
			selectMusic();
		} else if (type == GalleryActivity.TYPE_PICTURE) {
			selectPic(imgName);
		}
	}

	private void selectMusic() {
		((Activity) context).startActivityForResult(new Intent(context,
				MusicChoiceActivity.class), 0);
	}

	protected void selectVideo() {
		MyIosDialog.ShowBottomDialog(context, "", new String[] {context. getResources().getString(R.string.去录制视频),
				context.	getResources().getString(R.string.从相册选择视频) }, new DialogItemClickListener() {

			@Override
			public void itemClick(String result, int position) {
				if (result.equals(context.getResources().getString(R.string.去录制视频))) {
					Permission permission	=new Permission();
					boolean hasPermission_CAMERA=permission.hasPermission_CAMERA((BaseActivity) context);
					boolean hasPermission_RECORD_AUDIO=permission.hasPermission_RECORD_AUDIO((BaseActivity) context);
					boolean hasPermission_READ_EXTERNAL_STORAGE=permission.hasPermission_READ_EXTERNAL_STORAGE((BaseActivity) context);
					if(hasPermission_CAMERA&&hasPermission_RECORD_AUDIO&&hasPermission_READ_EXTERNAL_STORAGE){
//						Intent intent = new Intent(context,MediaRecorderActivity.class);
//						((Activity) context).startActivityForResult(intent, 0);
						Intent intent = new Intent();
						intent.setAction("android.media.action.VIDEO_CAPTURE");
						((Activity) context).startActivityForResult(intent, 0);
					}else if (!hasPermission_CAMERA){
						((BaseActivity) context).  toast("无法打开相机,拍照功能关闭，请打开权限");
						return;
					}else if (!hasPermission_RECORD_AUDIO){
						((BaseActivity) context).  toast("无法打开相机,录音功能关闭，请打开权限");
						return;
					}else if (!hasPermission_READ_EXTERNAL_STORAGE){
						((BaseActivity) context).  toast("无法打开相机,读写文件功能关闭，请打开权限");
						return;
					}
				} else if (result.equals(context.getResources().getString(R.string.从相册选择视频))) {
//					Intent intent = new Intent(context, GalleryActivity.class);
//					intent.putExtra(Constants.EXTRA_PHOTO_LIMIT, maxSize);// 最大选择数
//					intent.putExtra(Constants.EXTRA_SELECTED_PICTURES, getList(pathList));//已选择的图片
//					intent.putExtra("type", type);// 选择类型
//					((Activity) context).startActivityForResult(intent, 1);
					boolean hasPermission = new Permission().hasPermission_READ_EXTERNAL_STORAGE((BaseActivity) context);
					if(hasPermission){
						Intent intent = new Intent(context,
								GalleryActivity.class);
						intent.putExtra(Constants.EXTRA_PHOTO_LIMIT,maxSize);// 最大选择数
//			intent.putExtra(Constants.EXTRA_SELECTED_PICTURES, getList(pathList));//已选择的图片
						intent.putExtra("type", GalleryActivity.TYPE_VIDEO);// 选择类型
						((Activity) context).startActivityForResult(intent,
								1);
					}else{
						((BaseActivity) context).     toast("无法打开相册");
					}

				}
			}
		});
	}

	protected void selectPic(final String imgName) {
		MyIosDialog.ShowBottomDialog(context, "",
				new String[] { context.getResources().getString(R.string.去拍照),context.getResources().getString(R.string.去相册) }, new DialogItemClickListener() {

					@Override
					public void itemClick(String result, int position) {
						if (result.equals(context.getResources().getString(R.string.去拍照))) {
							Intent intent = new Intent();
							// 指定开启系统相机的Action
							intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
							intent.addCategory(Intent.CATEGORY_DEFAULT);
							FILE_PATH = FileUtil.getLocalImageUrl(context,
									imgName);
							// 根据文件地址创建文件
							File file = new File(FILE_PATH);
							// 设置系统相机拍摄照片完成后图片文件的存放地址
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							((Activity) context).startActivityForResult(intent,
									0);
						} else if (result.equals(context.getResources().getString(R.string.去相册))) {
							Intent intent = new Intent(context,
									GalleryActivity.class);
							intent.putExtra(Constants.EXTRA_PHOTO_LIMIT,
									maxSize);// 最大选择数
							intent.putExtra(Constants.EXTRA_SELECTED_PICTURES, getList(pathList));//已选择的图片
							intent.putExtra("type", type);// 选择类型
							((Activity) context).startActivityForResult(intent,
									1);
						}
					}
				});

	}
	private ArrayList<String > getList(ArrayList<Picture> pathList){
		ArrayList<String> mlist=new ArrayList<String>();
		for (Picture pic : pathList) {
			if (pic.getType()==0) {
				mlist.add(pic.getPicUrl());
			}
		}
		return mlist;
		
	}
}
