package com.greattone.greattone.Listener;

import android.content.Intent;

/**
 * 监听onActivityResult返回
 * @author Administrator
 *
 */
public interface ActivityBackListener {
	void activityBack(int requestCode, int resultCode, Intent data);
}
