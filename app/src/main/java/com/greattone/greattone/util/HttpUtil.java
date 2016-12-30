package com.greattone.greattone.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.greattone.greattone.MultiPart.HttpMultipartPost;
import com.greattone.greattone.activity.BaseActivity;
import com.greattone.greattone.data.Constants;
import com.greattone.greattone.data.HttpConstants2;
import com.greattone.greattone.dialog.MyProgressDialog;
import com.greattone.greattone.entity.Message2;

public class HttpUtil {
	// static int errorCount = 1;
	public static JsonObjectRequest request;
	public static StringRequest stringRequest = null;

	/**
	 * 响应处理
	 * 
	 * @author pc-002
	 *
	 */
	public interface ResponseListener {
		abstract void setResponseHandle(Message2 message);// 正确响应处理

	}

	/**
	 * 错误响应处理
	 * 
	 * @author pc-002
	 *
	 */
	public interface ErrorResponseListener {
		abstract void setServerErrorResponseHandle(Message2 message);// 服务器返回的错误响应处理

		abstract void setErrorResponseHandle(VolleyError error);// 错误响应处理

	}

	/**
	 * post请求
	 * 
	 * @param context
	 * @param map
	 * @param responseListener
	 * @param errorResponseListener
	 * @return
	 */
	public static StringRequest httpConnectionByPost(final Context context,
			Map<String, String> map, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		return httpConnectionByPost(context, HttpConstants2.APPAPI_URL, map,
				responseListener, errorResponseListener);
	}

	/**
	 * post请求
	 * 
	 * @param context
	 * @param msg
	 * @param responseListener
	 * @param errorResponseListener
	 * @return
	 */
	public static StringRequest httpConnectionByPost(final Context context,
			String msg, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		return httpConnectionByPost(context, HttpConstants2.APPAPI_URL, msg,
				responseListener, errorResponseListener);
	}

	/**
	 * post请求
	 * 
	 * @param context
	 * @param url
	 * @param map
	 * @param responseListener
	 * @param errorResponseListener
	 * @return
	 */
	public static StringRequest httpConnectionByPost(final Context context,
			String url, Map<String, String> map,
			ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> key = map.keySet().iterator();
		while (key.hasNext()) {
			String str = (String) key.next();
			if (map.get(str) != null && !TextUtils.isEmpty(map.get(str))) {
				builder.append(str + "=" + map.get(str));
				builder.append("&");
			}
		}
		builder.deleteCharAt(-1 + builder.length());
		return httpConnectionByPost(context, url, builder.toString(),
				responseListener, errorResponseListener);
	}
	public static final String TAG="com.greattone.greattone";
	/**
	 * post请求
	 * 
	 * @param context
	 * @param url
	 * @param msg
	 * @param responseListener
	 * @param errorResponseListener
	 * @return
	 */
	public static StringRequest httpConnectionByPost(final Context context,
			String url, String msg, final ResponseListener responseListener,
			final ErrorResponseListener errorResponseListener) {
		final byte[] updata = msg.getBytes();
		final String url1= url+"?"+msg;

		stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
//						Log.i(TAG,"POST=url=>" + url1);
//						Log.i(TAG,"POST=response=>" + response);

						try {
							Message2 message = JSON.parseObject(response,
									Message2.class);
							if (message.getErr_msg() != null) {
								if (message.getErr_msg().equals("success")) {
									responseListener.setResponseHandle(message);
								} else {
									MyProgressDialog.Cancel();
									toast(context,message
												.getInfo());
									if (errorResponseListener != null) {
										errorResponseListener
												.setServerErrorResponseHandle(message);
									}
								}
							}
						} catch (Exception e) {
							MyProgressDialog.Cancel();
							e.printStackTrace();
//							((BaseActivity) context).toast("请求错误！");
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						MyProgressDialog.Cancel();
						toast(context, "请求错误，请确认网络畅通！");
						if (error.toString().equals(
								Constants.NO_CONNECTION_ERROR)) {
//							if(context  instanceof BaseActivity){
////								((BaseActivity) context).addRequest(stringRequest);
//							}
						} else {
							if (errorResponseListener != null) {
								errorResponseListener
										.setErrorResponseHandle(error);
							}
						}
					}
				}) {
			@Override
			public byte[] getBody() throws AuthFailureError {
				return updata;
			}

			@Override
			public Map<String, String> getHeaders() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Cookie",
						"tupofdospacevstats213=1;tupofdospacevstats212=1;PHPSESSID=90a45b403d27eb9d097dfe6e090f5f70");
				map.put("Accept-Language", "zh-CN,zh;q=0.8");
				return map;
			}
		};
		return stringRequest;
	}

	/**
	 * post请求
	 *
	 * @param context
	 * @param map
	 * @param bitmap
	 * @param responseListener
	 * @param errorResponseListener
	 * @return
	 */
	// public static StringRequest httpConnectionByPostPicture(final Context
	// context,
	// Map<String, Object> map,Bitmap bitmap,
	// final MyStringResponseHandle2 myResponseHandle) {
	// StringBuilder builder = new StringBuilder();
	// Iterator<String> key = map.keySet().iterator();
	// while (true) {
	// String str = (String) key.next();
	// builder.append(str + "=" + map.get(str));
	// builder.append("&");
	// if (!key.hasNext()) {
	// builder.deleteCharAt(-1 + builder.length());
	// break;
	// }
	// }
	// String str=builder.append("&file=").toString();
	// byte[] image =BitmapUtil.Bitmap2Bytes(bitmap);
	//
	// stringRequest = new StringRequest(Method.POST, HttpConstants2.APPAPI_URL,
	// new Response.Listener<String>() {
	//
	// @Override
	// public void onResponse(String response) {
	// Message2 message = JSON.parseObject(response,
	// Message2.class);
	// if (message.getErr_msg().equals("success")) {
	// myResponseHandle.setResponseHandle(message);
	// } else {
	// MyProgressDialog.Cancel();
	// ((BaseActivity) context).toast(message.getInfo());
	// myResponseHandle
	// .setServerErrorResponseHandle(message);
	// }
	//
	// }
	// }, new Response.ErrorListener() {
	//
	// @Override
	// public void onErrorResponse(VolleyError error) {
	// MyProgressDialog.Cancel();
	// ((BaseActivity) context).toast("请检查网络！");
	// if (error.toString().equals(
	// Constants.NO_CONNECTION_ERROR)) {
	// ((BaseActivity) context).addRequest(stringRequest);
	// } else {
	// myResponseHandle.setErrorResponseHandle(error);
	// }
	// }
	// }) {
	// @Override
	// public byte[] getBody() throws AuthFailureError {
	// return updata;
	// }
	// @Override
	// public Map<String, String> getHeaders() {
	// Map<String, String> map = new HashMap<String, String>();
	// // map.put("Content-Type",
	// "multipart/form-data; boundary=----WebKitFormBoundary29jWfANRrmbyaife");
	// map.put("Cookie", "PHPSESSID=3f22aa2a27dba8e20011cbbfff3c6c3b");
	// // map.put("Accept",
	// " text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	// return map;
	// }
	// };
	// return stringRequest;
	// }
	public static void httpConnectionByPostFile(final Context context,String url,
			final Map<String, String> map, final Map<String, File> files,Boolean isShowProgress,
			final ResponseListener responseListener,
			final ErrorResponseListener errorResponseListener) {
		HttpMultipartPost httpMultipartPost = new HttpMultipartPost(context,
				url);
		if (!map.equals("")||map!=null) {
			httpMultipartPost.addStringUpload(map);
		}
		if (!files.equals("")||files!=null) {
			httpMultipartPost.addFileUpload(files);
		}
		httpMultipartPost.setShowProgress( isShowProgress);
		httpMultipartPost.setResponseListener(responseListener);
		httpMultipartPost.setErrorResponseListener(errorResponseListener);
		httpMultipartPost.execute("上传成功");
	}
	public static void httpConnectionByPostFile(final Context context,
			final Map<String, String> map, final Map<String, File> files,Boolean isShowProgress,
			final ResponseListener responseListener,
			final ErrorResponseListener errorResponseListener) {
		httpConnectionByPostFile(context, HttpConstants2.APPAPI_URL, map, files,isShowProgress, responseListener, errorResponseListener);
	}
	public static void httpConnectionByPostFile(final Context context,String url,
			final Map<String, String> map, final Map<String, File> files,
			final ResponseListener responseListener,
			final ErrorResponseListener errorResponseListener) {
		httpConnectionByPostFile(context, url, map, files,false, responseListener, errorResponseListener);
	}
	public static void httpConnectionByPostFile(final Context context,
			final Map<String, String> map, final Map<String, File> files,
			final ResponseListener responseListener,
			final ErrorResponseListener errorResponseListener) {
		httpConnectionByPostFile(context,	HttpConstants2.APPAPI_URL, map, files, responseListener, errorResponseListener);
	}

	public static void httpConnectionByPostBytes(Context context,
			Map<String, String> map, Map<String, byte[]> bytes,String suffix,Boolean isShowProgress,
			ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		HttpMultipartPost httpMultipartPost = new HttpMultipartPost(context,
				HttpConstants2.APPAPI_URL);
		httpMultipartPost.addStringUpload(map);
		httpMultipartPost.addBitmapUpload(bytes,suffix);
		httpMultipartPost.setResponseListener(responseListener);
		httpMultipartPost.setShowProgress( isShowProgress);
		httpMultipartPost.setErrorResponseListener(errorResponseListener);
		httpMultipartPost.execute("上传成功");
	}

	public static StringRequest httpConnectionByGet(final Context context,
			Map<String, String> map, ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		return httpConnectionByGet(context, HttpConstants2.APPAPI_URL, map,
				responseListener, errorResponseListener);
	}

	public static StringRequest httpConnectionByGet(final Context context,
			String url, Map<String, String> map,
			ResponseListener responseListener,
			ErrorResponseListener errorResponseListener) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> key = map.keySet().iterator();
		while (true) {
			String str = (String) key.next();
			if (map.get(str) != null) {
				builder.append(str + "=" + map.get(str));
				builder.append("&");
			}
			if (!key.hasNext()) {
				builder.deleteCharAt(builder.length() - 1);
				return httpConnectionByGet(context, url, builder.toString(),
						responseListener, errorResponseListener);
			}
		}
	}

	public static StringRequest httpConnectionByGet(final Context context,
			String url, String msg, final ResponseListener responseListener,
			final ErrorResponseListener errorResponseListener) {
		url = url + "?" + msg;
		stringRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						if (response.startsWith("{")) {
							Message2 message = JSON.parseObject(response,
									Message2.class);
							if (message.getErr_msg().equals("success")) {
								responseListener.setResponseHandle(message);
							} else {
								MyProgressDialog.Cancel();
									toast(context, message.getInfo());
								if (errorResponseListener != null) {
									errorResponseListener
											.setServerErrorResponseHandle(message);

								}
							}
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						MyProgressDialog.Cancel();
						toast(context,"请检查网络！");
						if (error.toString().equals(
								Constants.NO_CONNECTION_ERROR)) {
							if(context  instanceof BaseActivity){
								((BaseActivity) context).addRequest(stringRequest);
							}
						} else {
							if (errorResponseListener != null) {
								errorResponseListener
										.setErrorResponseHandle(error);
							}
						}
					}
				}) {
			@Override
			public Map<String, String> getHeaders() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Cookie",
						"tupofdospacevstats213=1;tupofdospacevstats212=1;PHPSESSID=90a45b403d27eb9d097dfe6e090f5f70");
				map.put("Accept-Language", "zh-CN");
				return map;
			}
		};
		return stringRequest;
	}

	/** Toast显示 */
	private static void toast(Context context,String text) {
		Toast toast = null;
		if ((text == null) || (text.equals("")))
			return;
		if (toast == null) {
			toast = Toast.makeText(context.getApplicationContext(),
					text, Toast.LENGTH_SHORT);
		}
		toast.show();
	}
}
