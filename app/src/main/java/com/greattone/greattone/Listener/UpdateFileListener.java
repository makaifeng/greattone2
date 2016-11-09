package com.greattone.greattone.Listener;

import com.greattone.greattone.entity.Message2;



public interface UpdateFileListener {
	void onProgressUpdate(long uploadedSize, long totalSize) ;//上传中
	 void updateSuccess(Message2 message);// 正确响应处理
		 void updateError();// 服务器返回的错误响应处理
}
