package com.greattone.greattone.Listener;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

public interface UpdateListener {
	void onProgress(PutObjectRequest request, long currentSize, long totalSize);

	void onSuccess(PutObjectRequest request, PutObjectResult result);

	void onFailure(PutObjectRequest request, ClientException clientExcepion,
				   ServiceException serviceException);
}