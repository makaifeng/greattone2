package com.greattone.greattone.update;


import com.alibaba.sdk.android.vod.upload.VODUploadCallback;
import com.alibaba.sdk.android.vod.upload.VODUploadClient;

/**
 * Created by zhouzhuo on 12/3/15.
 */
public class PutVideoSamples {

    private String testBucket;
    private String endpoint;
    private String uploadFilePath;
    private String accessKeyId;
    private String accessKeySecret;
    private String objectKey;
    VODUploadClient upload;
    public PutVideoSamples(VODUploadClient upload, String accessKeyId, String accessKeySecret, String testBucket, String endpoint, String uploadFilePath, String objectKey) {
        this.upload = upload;
        this.accessKeyId = accessKeyId;
        this.objectKey = objectKey;
        this.accessKeySecret = accessKeySecret;
        this.testBucket = testBucket;
        this.endpoint = endpoint;
        this.uploadFilePath = uploadFilePath;
    }

//    // 从本地文件上传，采用阻塞的同步接口
//    public void putObjectFromLocalFile() {
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest(testBucket, testObject, uploadFilePath);
//
//        try {
//            PutObjectResult putResult = oss.putObject(put);
//
//            Log.d("PutObject", "UploadSuccess");
//
//            Log.d("ETag", putResult.getETag());
//            Log.d("RequestId", putResult.getRequestId());
//        } catch (ClientException e) {
//            // 本地异常如网络异常等
//            e.printStackTrace();
//        } catch (ServiceException e) {
//            // 服务异常
//            Log.e("RequestId", e.getRequestId());
//            Log.e("ErrorCode", e.getErrorCode());
//            Log.e("HostId", e.getHostId());
//            Log.e("RawMessage", e.getRawMessage());
//        }
//    }

    // 从本地文件上传，使用非阻塞的异步接口
    public void asyncPutVideoFromLocalFile(final VODUploadCallback callback) {
   
    	 upload.init(accessKeyId, accessKeySecret, callback);
    	 new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     upload.addFile(uploadFilePath, endpoint, testBucket, objectKey);
                     upload.startUpload();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }).start();
    }

}
