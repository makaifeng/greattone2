package com.greattone.greattone.util;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.HttpConstants2;
import com.kf_test.Mp3Info;
import com.kf_test.picselect.ImageBean;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtil {
	/**
	 * 获取本地图片所在的目录
	 * 
	 */
	public static String getMyCacheDir(Context context) {
		String path = null;
		// 判断SDcard是否存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = context.getExternalCacheDir().toString();

			return path;
		} else {
			((BaseActivity) context).toast("你手机上不存在存储卡");
			path = context.getCacheDir().toString();
		}
		return path;
	}

	/**
	 * 获取本地图片地址
	 * 
	 */
	public static String getLocalImageUrl(Context context, String imageName) {
		return getLocalImageFile(context) + "/" + imageName;
	}

	/**
	 * 拼接网络路径
	 */
	public static String getNetWorkUrl( String url) {
		if (url.startsWith("http://")) {
			return url;
		} else if (url.startsWith("/")) {
			return HttpConstants2.SERVER_URL + url;
		} else {
			return HttpConstants2.SERVER_URL + "/" + url;
		}
	}

	/**
	 * 拼接新闻的h5页面路径
	 */
	public static String getNewsH5Url(String classid, String id) {
		return HttpConstants2.H5_SERVER_URL + "/app/news.php?classid="
				+ classid + "&id=" + id;
	}
/**
 * 用户公告
 * @param classid
 * @param id
 * @return
 */
	public static String getNoticeH5Url(String classid, String id) {
		return HttpConstants2.H5_SERVER_URL + "/app/bai_1.php?classid="
				+ classid + "&id=" + id;
	}
	public static String getSummaryH5Url(String classid, String id) {
		return HttpConstants2.H5_SERVER_URL + "/app/bai_2.php?classid="
				+ classid + "&id=" + id;
	}
	/**
	 * 品牌介绍
	 * @param classid
	 * @param userid
	 * @return
	 */
	public static String getBrondH5Url(String classid, String userid) {
		return HttpConstants2.H5_SERVER_URL + "/app/pinpai-index.php?classid="
				+ classid + "&userid=" + userid;
	}
	/**
	 *品牌的产品介绍
	 * @param classid
	 * @param id
	 * @return
	 */
	public static String getProductH5Url(String classid, String id) {
		return HttpConstants2.H5_SERVER_URL + "/app/pinpai-product-article.php?classid="
				+ classid + "&id=" + id;
	}
	/**
	 *品牌的新闻
	 * @param classid
	 * @param id
	 * @return
	 */
	public static String getPinpaiNewsH5Url(String classid, String id) {
		return HttpConstants2.H5_SERVER_URL + "/app/pinpai-news-article.php?classid="
				+ classid + "&id=" + id;
	}

	/**
	 * 获取本地图片所在的目录
	 * 
	 */
	public static String getLocalImageFile(Context context) {
		String path = null;
		path = Environment.getExternalStorageDirectory()+"/"+context.getPackageName()+"/p";
		createFile(context, path);
		return path;
	}
	/**
	 * 获取图片裁剪后的目录
	 */
	public static String getCropFile(Context context) {
		String path = null;
		path = Environment.getExternalStorageDirectory()+"/"+context.getPackageName()+"/crop";
		createFile(context, path);
		return path;
	}

	/**
	 * 创建路径
	 * 
	 * @param path
	 */
	private static void createFile(Context context, String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();// 创建文件目录
		}
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

	public static ArrayList<ImageBean> getMusicList(Context context) {
		ArrayList<ImageBean> imageList = new ArrayList<ImageBean>();

		Cursor imageCursor = context.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.Media.DATA,
						MediaStore.Video.Media._ID }, null, null,
				MediaStore.Video.Media._ID);

		if (imageCursor != null && imageCursor.getCount() > 0) {
			imageCursor.moveToFirst();
			while (imageCursor.moveToNext()) {
				ImageBean item = new ImageBean(
						imageCursor.getString(imageCursor
								.getColumnIndex(MediaStore.Video.Media.DATA)),0,
						false);
				imageList.add(item);
			}
		}
		if (imageCursor != null) {
			imageCursor.close();
		}
		// show newest photo at beginning of the list
		Collections.reverse(imageList);
		return imageList;
	}

	public static ArrayList<ImageBean> getVideoList(Context context) {
		ArrayList<ImageBean> imageList = new ArrayList<ImageBean>();

		Cursor imageCursor = context.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Video.Media.DATA,
						MediaStore.Video.Media._ID ,MediaStore.Video.Media.SIZE}, null, null,
				MediaStore.Video.Media._ID);

		if (imageCursor != null && imageCursor.getCount() > 0) {
			while (imageCursor.moveToNext()) {
				ImageBean item = new ImageBean();
				item.setPath(imageCursor.getString(imageCursor
								.getColumnIndex(MediaStore.Video.Media.DATA)));
				item.setSize(	Long.valueOf(imageCursor.getString(imageCursor
						.getColumnIndex(MediaStore.Video.Media.SIZE))));
				item.setSeleted(false);
				imageList.add(item);
			}
		}
		if (imageCursor != null) {
			imageCursor.close();
		}
		// show newest photo at beginning of the list
		Collections.reverse(imageList);
		return imageList;
	}
	public static ArrayList<ImageBean> getImagesList(Context context) {
		ArrayList<ImageBean> imageList = new ArrayList<ImageBean>();
//        ContentResolver mContentResolver = context.getContentResolver();
//        Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 只查询jpeg和png的图片
//        Cursor imageCursor = mContentResolver.query(imageUri, null,
//                MediaStore.Images.Media.MIME_TYPE + " in(?, ?)",
//                new String[] { "image/jpeg", "image/png" },
//                MediaStore.Images.Media.DATE_MODIFIED + " desc");
		Cursor imageCursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media.DATA,
						MediaStore.Images.Media._ID }, null, null,
				MediaStore.Images.Media._ID);

		if (imageCursor != null && imageCursor.getCount() > 0) {

			while (imageCursor.moveToNext()) {
				ImageBean item = new ImageBean(
						imageCursor.getString(imageCursor
								.getColumnIndex(MediaStore.Images.Media.DATA)),0,
						false);
				imageList.add(item);
			}
		}
		if (imageCursor != null) {
			imageCursor.close();
		}
		// show newest photo at beginning of the list
		Collections.reverse(imageList);
		return imageList;
	}

	public static List<Mp3Info> getMp3Infos(Context context) {
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
		for (int i = 0; i < cursor.getCount(); i++) {
			Mp3Info mp3Info = new Mp3Info();
			cursor.moveToNext();
			long id = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media._ID)); // 音乐id
			String title = cursor.getString((cursor
					.getColumnIndex(MediaStore.Audio.Media.TITLE)));// 音乐标题
			String artist = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.ARTIST));// 艺术家
			long duration = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.DURATION));// 时长
			long size = cursor.getLong(cursor
					.getColumnIndex(MediaStore.Audio.Media.SIZE)); // 文件大小
			String url = cursor.getString(cursor
					.getColumnIndex(MediaStore.Audio.Media.DATA)); // 文件路径
			int isMusic = cursor.getInt(cursor
					.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));// 是否为音乐
			if (isMusic != 0) { // 只把音乐添加到集合当中
				mp3Info.setId(id);
				mp3Info.setTitle(title);
				mp3Info.setArtist(artist);
				mp3Info.setDuration(duration + "");
				mp3Info.setSize(size + "");
				mp3Info.setUrl(url);
				mp3Infos.add(mp3Info);
			}
		}
		if (cursor != null) {
			cursor.close();
		}
		return mp3Infos;
	}

	/**
	 * 获取缓存大小
	 * 
	 * @param context
	 * @return
	 */
	public static String getCacheSize(Context context) {
		String size = null;
		try {
			size = getFormatSize(getFileSizes(context.getCacheDir())
					+ getFileSizes(context.getExternalCacheDir()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 清理缓存
	 * 
	 * @param context
	 */
	public static void clearCache(Context context) {
		deleteFolderFile(context.getCacheDir(), true);
		deleteFolderFile(context.getExternalCacheDir(), true);
	}

	/**
	 * 获取文件指定文件的指定单位的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @return double值的大小
	 */
	public static String getFileOrFilesSize(String filePath) {
	  File file = new File(filePath);
	  long blockSize = 0;
	  try {
	   if (file.isDirectory()) {
	    blockSize = getFileSizes(file);
	   } else {
	    blockSize = getFileSize(file);
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return getFormatSize(blockSize);
	 }

	/**
	 * 获取指定文件大小
	 * 
	 * @return
	 * @throws Exception
	 */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			size =  new FileInputStream(file).available();
		} else {
			// file.createNewFile();
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/** * 删除指定目录下文件及目录 * * @param deleteThisPath * @param filepath * @return */
	public static void deleteFolderFile(File file, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(file.getPath())) {
			try {
				// File file = new File(filePath);
				if (file.isDirectory()) {
					// 如果下面还有文件
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(new File(files[i].getAbsolutePath()),
								true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {
						// 如果是文件，删除
						file.delete();
					} else {
						// 目录
						if (file.listFiles().length == 0) {
							// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** * 格式化单位 * * @param size * @return */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte";
		}
		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}
		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}
		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}
}
