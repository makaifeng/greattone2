package com.greattone.greattone.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;

import com.greattone.greattone.R;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.HttpConstants;
import com.greattone.greattone.data.HttpConstants2;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.io.File;

public class ImageLoaderUtil {
	// private String cacheDirName = "/ImageLoader/cache";
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {
		public void onLoadingCancelled(String paramAnonymousString,
				View paramAnonymousView) {
		}

		public void onLoadingComplete(String paramAnonymousString,
				View paramAnonymousView, Bitmap paramAnonymousBitmap) {
			((ImageView) paramAnonymousView)
					.setImageBitmap(paramAnonymousBitmap);
		}

		public void onLoadingFailed(String paramAnonymousString,
				View paramAnonymousView, FailReason paramAnonymousFailReason) {
		}

		public void onLoadingStarted(String paramAnonymousString,
				View paramAnonymousView) {
		}
	};
	private boolean isCacheInMemory = true;
	private boolean isCacheOnDisk = true;
	private int maxDiskCache = 50 * 1024 * 1024;
	private int maxMemoryCache = 2 * 1024 * 1024;
	private int maxThreadNums = 3;
	private int onEmptyImage = R.drawable.image_empty;
	private int onFailImage = R.drawable.image_error;
	private int onLoadingImage = R.drawable.image_loading;
	private DisplayImageOptions options;
	private int roundPixels = 10;

	private void configImageLoader() {
		this.options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(this.onEmptyImage)
				.showImageOnFail(this.onFailImage)
				.showImageOnLoading(this.onLoadingImage)
				.cacheOnDisk(isCacheOnDisk).cacheInMemory(isCacheInMemory)
				.displayer(new RoundedBitmapDisplayer(this.roundPixels))
				.build();
	}

	public static ImageLoaderUtil getInstance() {
		return ImageLoaderUtilInstance.instance;
	}

	// public Bitmap getBitMap(String paramString) {
	// return this.imageLoader.loadImageSync(paramString);
	// }

	public DisplayImageOptions getOptions() {
		return this.options;
	}
	 public static PauseOnScrollListener getPauseOnScrollListener(OnScrollListener scrollListener) {
	        PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(),
	                false, true, scrollListener);
	        return listener;
	    }
	 public static PauseOnScrollListener getPauseOnScrollListener() {
		 PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(),
				 false, true);
		 return listener;
	 }
	public void initImageLoader(Context paramContext) {
		// this.cacheDirName = ("/" + paramContext.getPackageName());
		ImageLoaderConfiguration localImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				paramContext)
				.diskCacheSize(this.maxDiskCache)
				.diskCache(
						new UnlimitedDiskCache(new File(FileUtil
								.getLocalImageFile(paramContext))))
				.memoryCacheSize(this.maxMemoryCache)
				.threadPoolSize(this.maxThreadNums).threadPriority(3)
				.denyCacheImageMultipleSizesInMemory().diskCacheFileCount(100)
				.memoryCache(new WeakMemoryCache())
				.defaultDisplayImageOptions(this.options)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		this.imageLoader.init(localImageLoaderConfiguration);
	}

	// public void setCacheDirName(String paramString) {
	// this.cacheDirName = paramString;
	// }

	public void setCacheInMemory(boolean paramBoolean) {
		this.isCacheInMemory = paramBoolean;
	}

	public void setCacheOnDisk(boolean paramBoolean) {
		this.isCacheOnDisk = paramBoolean;
	}

	public void setImage(String url, ImageView imageView) {
		if (this.options == null)
			configImageLoader();
		// if (canLoader(url))
		this.imageLoader.displayImage(url, imageView, this.options);
		this.isCacheOnDisk = true;
		this.isCacheInMemory = true;
	}

	public void setImageRound(String url, ImageView imageView, int paramInt) {
		if (this.options == null)
			configImageLoader();
		boolean bool = url.startsWith("http://");
		String str = null;
		if (!bool)
			str = new HttpConstants(Constants.isDebug).ServerUrl + "/" + url;
		DisplayImageOptions localDisplayImageOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(isCacheOnDisk).cacheInMemory(isCacheInMemory)
				.displayer(new RoundedBitmapDisplayer(paramInt)).build();
		// if (canLoader(url)){
		//
		str.replace("http://app.umecn.com", "http://hao.franzsandner.com");
		//
		this.imageLoader.displayImage(str, imageView, localDisplayImageOptions);
		// }
	}

	public void setImagebyurl(String url, ImageView imageView) {
		if (url != null) {
			String str =getUrl( url);
			if (this.options == null)
				configImageLoader();
			// if (canLoader(url)) {
			this.imageLoader.displayImage(str, imageView, this.options,
					this.imageLoadingListener);
			// } else {
			//
			// }
			this.isCacheOnDisk = true;
			this.isCacheInMemory = true;
		}
	}
	public void setImagebyurl(String url, ImageView imageView, DisplayImageOptions options) {
		if (url != null) {
			String str =getUrl( url);
			if (options == null)
				configImageLoader();
			this.imageLoader.displayImage(str, imageView, options
					);
			this.isCacheOnDisk = true;
			this.isCacheInMemory = true;
		}
	}

	private String getUrl(String url) {
		String str = null;
		if ((!url.startsWith("http://")) && (!url.startsWith("file://"))
				&& (!url.startsWith("drawable://"))) {
			if (url.startsWith("/")) {
				str =  HttpConstants2.SERVER_URL + url;
			} else {
				str =  HttpConstants2.SERVER_URL + "/"
						+ url;
			}
		}else {
			str=url;
		}
		return str;
	}

	public void setImagebyFile(String url, ImageView imageView) {
		if (url != null) {
			String str = url;
			if (this.options == null)
				configImageLoader();
			if ((!url.startsWith("http://")) && (!url.startsWith("file://"))
					&& (!url.startsWith("drawable://")))
				str = "file://" + url;
			this.imageLoader.displayImage(str, imageView, this.options,
					this.imageLoadingListener);
			this.isCacheOnDisk = true;
			this.isCacheInMemory = true;
		}
	}

	public void loadImage(String url, ImageLoadingListener imageLoadingListener) {
		if (url != null) {
			String str = url;
			if (this.options == null)
				configImageLoader();
			if ((!url.startsWith("http://")) && (!url.startsWith("file://"))
					&& (!url.startsWith("drawable://")))
				str = new HttpConstants(Constants.isDebug).ServerUrl + "/"
						+ url;
			// if (canLoader(url)) {
			this.imageLoader.loadImage(str, imageLoadingListener);
			// } else {
			//
			// }
			this.isCacheOnDisk = true;
			this.isCacheInMemory = true;
		}
	}

	public void setMaxDiskCache(int paramInt) {
		this.maxDiskCache = paramInt;
	}

	public void setMaxMemoryCache(int paramInt) {
		this.maxMemoryCache = paramInt;
	}

	public void setMaxThreadNums(int paramInt) {
		this.maxThreadNums = paramInt;
	}

	public void setOnEmptyImage(int paramInt) {
		this.onEmptyImage = paramInt;
	}

	public void setOnFailImage(int paramInt) {
		this.onFailImage = paramInt;
	}

	public void setOnLoadingImage(int paramInt) {
		this.onLoadingImage = paramInt;
	}

	public void setOptions(DisplayImageOptions paramDisplayImageOptions) {
		this.options = paramDisplayImageOptions;
	}

	public void setRoundPixels(int paramInt) {
		this.roundPixels = paramInt;
	}

	private static class ImageLoaderUtilInstance {
		private static ImageLoaderUtil instance = new ImageLoaderUtil();
	}

	// private boolean canLoader(String url) {
	// if (url.endsWith(".png") || url.endsWith(".jpg")
	// || url.endsWith(".gif") || url.endsWith(".jpeg")
	// || url.endsWith(".tiff") || url.endsWith(".svg")
	// || url.endsWith(".swf") || url.endsWith(".mp4"))
	// return true;
	//
	// return false;
	// }
}
