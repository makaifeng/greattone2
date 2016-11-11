package com.greattone.greattone.Listener;

import android.support.annotation.NonNull;

/**
 * 监听onRequestPermissionsResult返回
 *
 */
public interface OnRequestPermissionsBackListener {
	void onRequestPermissionsBack(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
