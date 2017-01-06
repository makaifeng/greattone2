package com.greattone.greattone.Listener;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

import java.util.List;

public interface UpdateListListener {
	void onProgress(PutObjectRequest request, long currentSize, long totalSize);

	void onSuccess(List<String> mlist);

	void onFailure(PutObjectRequest request, ClientException clientExcepion,
				   ServiceException serviceException);
}