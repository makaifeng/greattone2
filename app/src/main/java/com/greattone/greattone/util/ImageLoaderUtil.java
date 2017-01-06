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
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

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
				.imageDownloader(new AuthImageDownloader(paramContext))
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
		boolean bool = url.startsWith("http://")&&url.startsWith("https://");
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
		if ((!url.startsWith("http://"))&&(!url.startsWith("https://"))&& (!url.startsWith("file://"))
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
			if ((!url.startsWith("http://")) &&(!url.startsWith("https://"))&& (!url.startsWith("file://"))
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
			if ((!url.startsWith("http://"))&&(!url.startsWith("https://")) && (!url.startsWith("file://"))
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


	public class AuthImageDownloader extends BaseImageDownloader {

		private SSLSocketFactory mSSLSocketFactory;
		public AuthImageDownloader(Context context) {
			super(context);
			SSLContext sslContext = sslContextForTrustedCertificates();
			mSSLSocketFactory = sslContext.getSocketFactory();
		}
		public AuthImageDownloader(Context context, int connectTimeout, int readTimeout) {
			super(context, connectTimeout, readTimeout);
			SSLContext sslContext = sslContextForTrustedCertificates();
			mSSLSocketFactory = sslContext.getSocketFactory();
		}
		@Override
		protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
			URL url = null;
			try {
				url = new URL(imageUri);
			} catch (MalformedURLException e) {
			}
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection)conn).setSSLSocketFactory(mSSLSocketFactory);
				((HttpsURLConnection)conn).setHostnameVerifier((DO_NOT_VERIFY));
			}
			return new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
		}
		// always verify the host - dont check for certificate
		final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		public SSLContext sslContextForTrustedCertificates() {
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
			javax.net.ssl.TrustManager tm = new miTM();
			trustAllCerts[0] = tm;
			SSLContext sc = null;
			try {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, null);
				//javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}catch (KeyManagementException e) {
				e.printStackTrace();
			}finally {
				return sc;
			}
		}
	}
	class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}
		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
}
