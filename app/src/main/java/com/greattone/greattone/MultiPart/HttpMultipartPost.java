package com.greattone.greattone.MultiPart;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.greattone.greattone.Listener.UpdateFileListener;
import com.greattone.greattone.MultiPart.CustomMultipartEntity.ProgressListener;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;
import com.greattone.greattone.util.HttpUtil.ErrorResponseListener;
import com.greattone.greattone.util.HttpUtil.ResponseListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class HttpMultipartPost extends AsyncTask<String, Long, String> {
	Map<String, byte[]> bytesUpload = new HashMap<String, byte[]>();
	Map<String, File> fileUpload = new HashMap<String, File>();
	Map<String, String> stringUpload = new HashMap<String, String>();
	ResponseListener responseListener;
	ErrorResponseListener errorResponseListener;
	UpdateFileListener listener;
	private Context context;
//	private String filePath;
	private ProgressDialog pd;
	private long totalSize;
	String url;
boolean isShowProgress;
private String suffix;
	public HttpMultipartPost(Context context, String url) {
		this.context = context;
		this.url = url;
	}
	public HttpMultipartPost(Context context, String url,UpdateFileListener listener) {
		this.context = context;
		this.url = url;
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		if (pd != null) {
			pd.cancel();
		}
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("上传中...");
		pd.setCancelable(false);
		if (isShowProgress) {
			pd.show();
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String serverResponse = null;

		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(
				"Cookie",
				"tupofdospacevstats213=1;tupofdospacevstats212=1;PHPSESSID=90a45b403d27eb9d097dfe6e090f5f70");

		try {
			CustomMultipartEntity multipartContent = new CustomMultipartEntity(
					new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress(num);
						}
					});
			// // We use FileBody to transfer an image
			// multipartContent.addPart("file", new FileBody(new File(
			// filePath)));
			// Iterate the fileUploads
			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
					HTTP.UTF_8);
			// Iterate the stringUploads
			if (stringUpload!=null) {
			for (Map.Entry<String, String> entry : stringUpload.entrySet()) {
				try {
					// builder.addPart(((String) entry.getKey()),
					// new StringBody((String) entry.getValue(), contentType));
					multipartContent.addPart(((String) entry.getKey()),
							new StringBody((String) entry.getValue(),
									contentType));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
			if (fileUpload!=null) {
			for (Map.Entry<String, File> entry : fileUpload.entrySet()) {
				String suffix[] = entry.getValue().getPath().split("\\.");
				multipartContent.addPart(((String) entry.getKey()),
						new FileBody((File) entry.getValue(), contentType,
								System.currentTimeMillis() + "."
										+ suffix[suffix.length - 1]));
			}
			}
			if (bytesUpload!=null) {
			for (Map.Entry<String, byte[]> entry : bytesUpload.entrySet()) {
				InputStream in = new ByteArrayInputStream(entry.getValue());
				multipartContent.addPart(
						((String) entry.getKey()),
						new InputStreamBody(in, contentType, System
								.currentTimeMillis() + "."+suffix));
			}
			}
			totalSize = multipartContent.getContentLength();
			// Send it
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			if (response.getStatusLine().getStatusCode() == 200) {
				serverResponse = EntityUtils.toString(response.getEntity());
			} else {
				serverResponse = "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverResponse;
	}

	@Override
	protected void onProgressUpdate(Long... progress) {
		pd.setProgress((int) ((progress[0] / (float) totalSize) * 100));
		if (listener!=null) {
			listener.onProgressUpdate(((long)progress[0]), totalSize);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		pd.dismiss();
		if (result==null||result.equals("error")) {
			MyProgressDialog.Cancel();
			((BaseActivity) context).toast("请检查网络！");
			if (errorResponseListener != null) {
				errorResponseListener.setErrorResponseHandle(null);
			}
			if (listener!=null) {
				listener.updateError();
			}
		} else {
			Message2 message = JSON.parseObject(result, Message2.class);
			if (message.getErr_msg().equals("success")) {
				if (responseListener!=null) {
					responseListener.setResponseHandle(message);
				}
				if (listener!=null) {
					listener.updateSuccess(message);
				}
			} else {
				if (listener!=null) {
					listener.updateError();
				}
				MyProgressDialog.Cancel();
//				((BaseActivity) context).toast(message.getInfo());
				if (errorResponseListener != null) {
					errorResponseListener.setServerErrorResponseHandle(message);
				}
			}
		}
	}

	@Override
	protected void onCancelled() {
		System.out.println("cancle");
	}

	public void addFileUpload(Map<String, File> fileUpload) {
		this.fileUpload = fileUpload;
	}

	public void addStringUpload(Map<String, String> stringUpload) {
		this.stringUpload = stringUpload;
	}

	public void addBitmapUpload(Map<String, byte[]> bytesUpload,String suffix) {
		this.bytesUpload = bytesUpload;
		this.suffix = suffix;
	};

	public void setResponseListener(ResponseListener responseListener) {
		this.responseListener = responseListener;
	};
	public void setErrorResponseListener(ErrorResponseListener errorResponseListener) {
		this.errorResponseListener = errorResponseListener;
	}

	public void setShowProgress(Boolean isShowProgress) {
		this.isShowProgress=isShowProgress;
		
	};
}
