package com.greattone.greattone.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

import com.greattone.greattone.activity.BaseActivity;


public class PhotoUtil {
	public final static int PHOTOGRAPH = 222; // 请求系统拍照功能
	public final static int ALBUM = 223; // 请求系统相册功能
	public final static int PHOTO_REQUEST_CUT = 224; // 裁剪
	/**
	 * 进入相册
	 */
	public static void setalbum(Context context) {
		// 使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图图片
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		getAlbum.setType("image/*");
		((Activity) context).startActivityForResult(getAlbum, ALBUM);
	}

	/**
	 * 拍照
	 */
	public static void setPhotograph(Context context, File file) {
		Intent intent = new Intent();
		// 指定开启系统相机的Action
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		((Activity) context).startActivityForResult(intent,
				PHOTOGRAPH);
	}
	/**
	 *获取图片的后缀名
	 */
	@SuppressWarnings("deprecation")
	public static String  getFileExtName(Context context, Uri originalUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		 // 好像是android多媒体数据库的封装接口，具体的看Android文档
		 Cursor cursor = ((BaseActivity)context).managedQuery(originalUri, proj, null, null,
		 null);
		 if (cursor==null) {
			 String[] s = originalUri.toString().split("\\.");
			 if (s.length>2) {
				 return  s[s.length-1];
			}
			 return "png";
		}
		 // 按我个人理解 这个是获得用户选择的图片的索引值
		 int column_index = 0;
		 try {
			 column_index = cursor
					 .getColumnIndex(MediaColumns.DATA);
		} catch (Exception e) {
		}
		 // 将光标移至开头 ，这个很重要，不小心很容易引起越界
		 cursor.moveToFirst();
		 // 最后根据索引值获取图片路径
		 String path = cursor.getString(column_index);
		 String[] s = path.split("\\.");
		 if (s.length>2) {
			 return  s[s.length-1];
		}
		 return "png";
	}

	/**
	 * 裁剪
	 * 
	 * @param context
	 * @param uri
	 * @param aspectX
	 *            是宽的比例
	 * @param aspectY
	 *            是高的比例
	 * @param outputX
	 *            剪裁图片的宽
	 * @param outputY
	 *            剪裁图片的高
	 */
	public static void startPhotoZoom(Context context, Uri uri, double aspectX,
			double aspectY, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true); 
//		intent.putExtra("noFaceDetection", true);
		((Activity) context).startActivityForResult(intent,
				PHOTO_REQUEST_CUT);
	}
	/**
	 * 保存图片到本地
	 * 
	 * @param bitmap
	 * @param imageName
	 */
	public static void sendImageToSDCard(Bitmap bitmap, String fileName) {
		// 存入sd卡中
		FileOutputStream b = null;
		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
