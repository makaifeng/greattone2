package com.greattone.greattone.MultiPart;

//@SuppressWarnings("deprecation")
//public class HttpMultipartPostVideo extends AsyncTask<String, Long, String> {
//	Map<String, byte[]> bytesUpload = new HashMap<String, byte[]>();
//	Map<String, File> fileUpload = new HashMap<String, File>();
//	Map<String, String> stringUpload = new HashMap<String, String>();
//	UpdateListener downloadListener;
//	private Context context;
////	private String filePath;
////	private ProgressDialog pd;
//	private long totalSize;
//	String url;
//
//	public HttpMultipartPostVideo(Context context, String url) {
//		this.context = context;
//		this.url = url;
//	}
//
//	@Override
//	protected void onPreExecute() {
//		downloadListener.handleStatus("", 1);
////		if (pd != null) {
////			pd.cancel();
////		}
////		pd = new ProgressDialog(context);
////		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
////		pd.setMessage("上传中...");
////		pd.setCancelable(false);
////		pd.show();
//	}
//
//	@SuppressWarnings({  "resource" })
//	@Override
//	protected String doInBackground(String... params) {
//		String serverResponse = null;
//
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpContext httpContext = new BasicHttpContext();
//		HttpPost httpPost = new HttpPost(url);
//		httpPost.setHeader(
//				"Cookie",
//				"tupofdospacevstats213=1;tupofdospacevstats212=1;PHPSESSID=90a45b403d27eb9d097dfe6e090f5f70");
//
//		try {
//			CustomMultipartEntity multipartContent = new CustomMultipartEntity(
//					new ProgressListener() {
//						@Override
//						public void transferred(long num) {
//							publishProgress(num);
//						}
//
//					});
//			// // We use FileBody to transfer an image
//			// multipartContent.addPart("file", new FileBody(new File(
//			// filePath)));
//			// Iterate the fileUploads
//			ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,
//					HTTP.UTF_8);
//			// Iterate the stringUploads
//			if (stringUpload!=null) {
//			for (Map.Entry<String, String> entry : stringUpload.entrySet()) {
//				try {
//					// builder.addPart(((String) entry.getKey()),
//					// new StringBody((String) entry.getValue(), contentType));
//					multipartContent.addPart(((String) entry.getKey()),
//							new StringBody((String) entry.getValue(),
//									contentType));
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			}
//			if (fileUpload!=null) {
//			for (Map.Entry<String, File> entry : fileUpload.entrySet()) {
//				String suffix[] = entry.getValue().getPath().split("\\.");
//				multipartContent.addPart(((String) entry.getKey()),
//						new FileBody((File) entry.getValue(), contentType,
//								System.currentTimeMillis() + "."
//										+ suffix[suffix.length - 1]));
//			}
//			}
//			if (bytesUpload!=null) {
//			for (Map.Entry<String, byte[]> entry : bytesUpload.entrySet()) {
//				InputStream in = new ByteArrayInputStream(entry.getValue());
//				multipartContent.addPart(
//						((String) entry.getKey()),
//						new InputStreamBody(in, contentType, System
//								.currentTimeMillis() + ".png"));
//			}
//			}
//			totalSize = multipartContent.getContentLength();
//			// Send it
//			httpPost.setEntity(multipartContent);
//			HttpResponse response = httpClient.execute(httpPost, httpContext);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				serverResponse = EntityUtils.toString(response.getEntity());
//			} else {
//				serverResponse = "error";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return serverResponse;
//	}
//
//	@Override
//	protected void onProgressUpdate(Long... progress) {
////		pd.setProgress((int) (progress[0]));
//		downloadListener.handleProcess(progress[0], totalSize, "");
//	}
//
//	@Override
//	protected void onPostExecute(String result) {
////		pd.dismiss();
//		if (result==null||result.equals("error")) {
//			MyProgressDialog.Cancel();
////			((ma) context).toast("请检查网络！");
//			if (downloadListener != null) {
//				downloadListener.handleCancel();
//			}
//		} else {
//			downloadListener.handleStatus("结束", 3);
////			Message2 message = JSON.parseObject(result, Message2.class);
////			if (message.getErr_msg().equals("success")) {
////				responseListener.setResponseHandle(message);
////			} else {
////				MyProgressDialog.Cancel();
////				((BaseActivity) context).toast(message.getInfo());
////				if (errorResponseListener != null) {
////					errorResponseListener.setServerErrorResponseHandle(message);
////				}
////			}
//		}
//	}
//
//	@Override
//	protected void onCancelled() {
//		System.out.println("cancle");
//	}
//
//	public void addFileUpload(Map<String, File> fileUpload) {
//		this.fileUpload = fileUpload;
//	}
//
//	public void addStringUpload(Map<String, String> stringUpload) {
//		this.stringUpload = stringUpload;
//	}
//
//	public void addBitmapUpload(Map<String, byte[]> bytesUpload) {
//		this.bytesUpload = bytesUpload;
//	};
//
//	public void setUpdateFileListener(UpdateListener downloadListener) {
//		this.downloadListener = downloadListener;
//	};
//}
