package com.greattone.greattone.util;


import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.greattone.greattone.Listener.UpdateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class UpdateObjectToOSSUtil {
    private static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
    private static final String accessKeyId = "7iFtlkzGkuWioxh5";
    private static final String accessKeySecret = "cbrkUVkZSQgonvzIhrJAngpCyWwvwL";

    public static final String testBucket = "greattone";
    public static final String uploadObject_userPic = "userpic";
    public static final String uploadObject_iamge = "iamge";
    public static final String uploadObject_folder = "folder";

    private OSSClient oss;
    static  UpdateObjectToOSSUtil updateObjectToOSSUtil=new UpdateObjectToOSSUtil();
    public static UpdateObjectToOSSUtil getInstance() {
        return updateObjectToOSSUtil;
    }


    public void init(Context context) {
        OSSPlainTextAKSKCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
         oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider,conf);
    }
    /**
     * 上传图片 用户头像
     * @param filePath
     */
    public  void uploadImage_userPic(Context context, final String filePath,final UpdateListener updateListener) {
        String uploadObject=uploadObject_userPic;
        uploadImage(context,filePath,testBucket,uploadObject,updateListener);
    }
    /**
     * 上传图片
     * @param filePath
     */
    public  void uploadImage(Context context, final String filePath,String uploadBucket,String uploadObject, final UpdateListener updateListener) {
        String[] suffix=filePath.split("\\.");
        String name="/"+System.currentTimeMillis()+"."+suffix[suffix.length-1];
        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectKey>", "<uploadFilePath>");
        PutObjectRequest put = new PutObjectRequest(uploadBucket, uploadObject+name, filePath);
// 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if (updateListener!=null)  updateListener.onProgress(request,currentSize,totalSize);
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (updateListener!=null)   updateListener.onSuccess(request,result);
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    if (updateListener!=null)    updateListener.onFailure(request,clientExcepion,serviceException);
                    // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
//        task.cancel();
//        task.waitUntilFinished(); // 可以等待任务完成
    }
    /**
     * 上传图片
     * @param uploadData
     */
    public  void uploadImage_by_bytes(Context context, final byte[] uploadData,String uploadBucket,String uploadObject, final UpdateListener updateListener) {
        String name="/"+System.currentTimeMillis()+".png";
        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectKey>", "<uploadFilePath>");
        PutObjectRequest put = new PutObjectRequest(uploadBucket, uploadObject+name, uploadData);
// 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                if (updateListener!=null)  updateListener.onProgress(request,currentSize,totalSize);
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if (updateListener!=null)   updateListener.onSuccess(request,result);
            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    if (updateListener!=null)    updateListener.onFailure(request,clientExcepion,serviceException);
                    // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
//        task.cancel();
//        task.waitUntilFinished(); // 可以等待任务完成
    }
    /**
     * 上传图片 其他图片
     * @param filePath
     */
    public  void uploadImage_iamge(Context context, final String filePath,final UpdateListener updateListener) {
        String uploadObject=uploadObject_iamge;
        uploadImage(context,filePath,testBucket,uploadObject,updateListener);
    }
    /**
     * 上传图片 其他图片
     * @param uploadData
     */
    public  void uploadImage_iamge_by_bytes(Context context,  byte[] uploadData, UpdateListener updateListener) {
        String uploadObject=uploadObject_iamge;
        uploadImage_by_bytes(context,uploadData,testBucket,uploadObject,updateListener);
    }
    /**
     * 上传图片  多图
     * @param filePath
     */
    public  void uploadImage_folder(Context context, final String filePath,final UpdateListener updateListener) {
        String uploadObject=uploadObject_folder;
        uploadImage(context,filePath,testBucket,uploadObject,updateListener);
    }
    int num;
    List<String> piclist =new ArrayList<>();
    public String getUrl(String bucketName,String objectKey){
        return   oss.presignPublicObjectURL(bucketName, objectKey);
    }
    public OSSClient getOSS(){
        return   oss;
    }
}
