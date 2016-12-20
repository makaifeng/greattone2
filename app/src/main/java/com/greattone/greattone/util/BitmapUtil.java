package com.greattone.greattone.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.dialog.SelectPictureDialog;
import com.greattone.greattone.entity.Picture;
import com.kf_test.picselect.GalleryActivity;

import net.bither.util.NativeUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BitmapUtil {

	/** 选择图片 
	 * @param  */
	public static void getPictures(final Context context,String imgName, final int maxSize,final ArrayList<Picture> pathList) {
		boolean hasPermission = new Permission().hasPermission_READ_EXTERNAL_STORAGE((BaseActivity) context);
		if(hasPermission){
			Intent intent = new Intent(context, GalleryActivity.class);
			intent.putExtra(Constants.EXTRA_PHOTO_LIMIT, maxSize);// 最大选择数
			intent.putExtra(Constants.EXTRA_SELECTED_PICTURES, getList(pathList));//已选择的图片
			intent.putExtra("type", GalleryActivity.TYPE_PICTURE);// 选择类型
			((Activity) context).startActivityForResult(intent, 1);
		}else{
			((BaseActivity) context).toast("无法打开相册");
		}
//		new SelectPictureDialog(context,  imgName,GalleryActivity.TYPE_PICTURE,maxSize,pathList).show();
		
	}
	/** 选择视频 
	 * @param  */
	public static void getVideos(Context context, int maxSize) {
		new SelectPictureDialog(context,  GalleryActivity.TYPE_VIDEO,maxSize).show();
		
//		Intent intent = new Intent(context,
//				GalleryActivity.class);
//		intent.putExtra(Constants.EXTRA_PHOTO_LIMIT,maxSize);// 最大选择数
////		intent.putExtra(Constants.EXTRA_SELECTED_PICTURES, getList(pathList));//已选择的图片
//		intent.putExtra("type", GalleryActivity.TYPE_VIDEO);// 选择类型
//		((Activity) context).startActivityForResult(intent,
//				1);
	
	}
	private static ArrayList<String > getList(ArrayList<Picture> pathList){
		ArrayList<String> mlist=new ArrayList<String>();
		for (Picture pic : pathList) {
			if (pic.getType()==0) {
				mlist.add(pic.getPicUrl());
			}
		}
		return mlist;
		
	}
	/** 获取指定路径的图片 */
	public static Bitmap getBitmapFromFile(String filePath) {
		Bitmap bitmap = null;
//		if (!filePath.startsWith("file://")) {
//			filePath="file://"+filePath;
//		}
		File f = new File(filePath);
		if (f.exists()) {
			bitmap = BitmapFactory.decodeFile(filePath);
			bitmap=BitmapUtil.zoomBitmap(bitmap, 300, 300*bitmap.getHeight()/bitmap.getWidth());
		}
		return bitmap;
	}

	/** 获取拍照的图片 */
	public static Bitmap getBitmapFromPHOTOGRAPH(Context context,
			String filePath) {
//		if (!filePath.startsWith("file://")) {
//			filePath="file://"+filePath;
//		}
		Bitmap bitmap = null;
		File f = new File(filePath);
		if (f.exists()) {
			bitmap = BitmapFactory.decodeFile(filePath);
			bitmap = zoomBitmap(bitmap, 200, 200);
		}
		return bitmap;
	}

	/** 获取相册的图片 */
	public static String getFileFromALBUM(Context context, Intent data) {
		if (data == null) {
			((BaseActivity) context).toast("选择图片文件出错");
			return null;
		}
		Uri originalUri = data.getData(); // 获得图片的uri
		String filePath;
		// 获取图片路径
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			filePath = getPath(context, originalUri);
		} else {
			filePath = selectImage(context, data);
		}
		return filePath;
	}

	/**
	 * 图片大小缩放
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		// if (w>h) {
		// matrix.postScale(scaleWidth, scaleWidth);
		// }else {
		// matrix.postScale(scaleHeight, scaleHeight);
		// }
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	public static String selectImage(Context context, Intent data) {
		Uri selectedImage = data.getData();
		return selectImage(context,selectedImage);
	}

	public static String selectImage(Context context, Uri selectedImage) {
		// Log.e(TAG, selectedImage.toString());
		if (selectedImage != null) {
			String uriStr = selectedImage.toString();
			String path = uriStr.substring(10, uriStr.length());
			if (path.startsWith("com.sec.android.gallery3d")) {
				Log.e("path error", "It's auto backup pic path:"
						+ selectedImage.toString());
				return null;
			}
		}
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
			
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}
	 /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * @param videoPath 视频的路径
     * @param width 指定输出视频缩略图的宽度
     * @param height 指定输出视频缩略图的高度度
     * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static  Bitmap getVideoThumbnail(String videoPath, int width, int height,
            int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap=   getVideoPic(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
    public static  Bitmap getVideoPic(String videoPath,
    		int kind) {
    	if (!new File(videoPath).exists()) {
			new File(videoPath).mkdir();
		}
    	return ThumbnailUtils.createVideoThumbnail(videoPath, kind);
    }
    public static Bitmap getVideoThumb(String path) {
    	MediaMetadataRetriever media = new MediaMetadataRetriever();
    	media.setDataSource(path);
    	Bitmap bitmap=media.getFrameAtTime();
    	media.release();
    	return bitmap ;
    	}

    public static  byte[] getVideoPicBytes(String videoPath,
    		int kind) {
    	Bitmap bm = getVideoPic(videoPath, kind);
    	return Bitmap2Bytes(bm);
    }
    public static  byte[] getPicBytes(String filePath) {
    	Bitmap bm = getBitmapFromFile(filePath);
    	return Bitmap2Bytes(bm);
    }
    /** 获取指定路径的图片压缩的bytes */
    public static byte[] getBytesFromFile(String filePath) {
    	return getBytesFromFile(filePath, 800);
    }
    /**
     *  获取指定路径的图片压缩的bytes
     * @param filePath 图片路径
     * @param maxWidth 宽高的最大值
     * @return
     */
	public static byte[] getBytesFromFile(String filePath,int maxWidth) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
		File f = new File(filePath);
		if (f.exists()) {
			bitmap = BitmapFactory.decodeFile(filePath,options);
			   options.inJustDecodeBounds =false;  
	           //计算缩放比  
	           int be;  
	           if (options.outHeight >options.outWidth) {
	        	   be = (int)(options.outHeight / (float)maxWidth);  
	           }else {
	        	   be = (int)(options.outWidth / (float)maxWidth); 
	           }
	           if(be <= 0)  
	                be =1;  
	           options.inSampleSize =be;  
	           bitmap = BitmapFactory.decodeFile(filePath,options);  
		}
		byte [] b=Bitmap2Bytes(bitmap);
		//回收图片资源，否则加载多张之后会报内存不足错误
		if (!bitmap.isRecycled()) {
			bitmap.recycle();
			System.gc() ; //提醒系统及时回收
		}
		return b;
	}
    /** 获取指定路径的图片压缩的路径 */
    public static File getFileFromBitmapThumb(Context context,String filePath) {
    	return getFileFromBitmapThumb(context,filePath, 800);
    }
	/**
	 *  获取指定路径的图片压缩的bytes
	 * @param filePath 图片路径
	 * @param maxWidth 宽高的最大值
	 * @return
	 */
	public static File getFileFromBitmapThumb(Context context,String filePath,int maxWidth) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inJustDecodeBounds = true;  
		File f = new File(filePath);
		if (f.exists()) {
			bitmap = BitmapFactory.decodeFile(filePath,options);
			options.inJustDecodeBounds =false;  
			//计算缩放比  
			int be;  
			if (options.outHeight >options.outWidth) {
				be = (int)(options.outHeight / (float)maxWidth);  
			}else {
				be = (int)(options.outWidth / (float)maxWidth); 
			}
			if(be <= 0)  
				be =1;  
			options.inSampleSize =be;  
			bitmap = BitmapFactory.decodeFile(filePath,options);
		}
		String s[]=filePath.split("\\.");
		String file=FileUtil.getLocalImageUrl(context,System.currentTimeMillis()+"."+ s[s.length-1]);
		File file1=	new File(file).getAbsoluteFile();
		File file2=file1.getParentFile();
		if (!file2.exists()){
			file2.mkdir();
		}
		NativeUtil.compressBitmap(bitmap, 90,
				file, true);
//		File file=saveBitmap(bitmap,filePath);
		//回收图片资源，否则加载多张之后会报内存不足错误
		if (!bitmap.isRecycled()) {
			bitmap.recycle();
			System.gc() ; //提醒系统及时回收
		}
		return file1;
	}

//	public static File saveBitmap(Bitmap bm, String filePath){
//		String s[]=filePath.split("\\.");
//        File file2= new File(FileUtil.getDownloadCacheDirectory(System.currentTimeMillis()+"."+ s[s.length-1]));
//        try {
//			if (file2.exists()){
//				file2.mkdir();
//			}
//         FileOutputStream out = new FileOutputStream(file2);
//         if(bm.compress(Bitmap.CompressFormat.PNG, 100, out)){
//             out.flush();
//             out.close();
//             return file2;
//         }
//     } catch (Exception e) {
//         // TODO: handle exception
//     }
//        return null;
//	}
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		byte[]    data = new byte[]{};
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	        try {
					if( bm.compress(Bitmap.CompressFormat.PNG, 100, baos)){  
						data	 = baos.toByteArray();
						baos.flush();  
						baos.close();  
					}
				} catch (IOException e) {
					e.printStackTrace();
				}  
    	return data;
    	}
	
}
