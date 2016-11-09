package com.kf_test.picselect;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

import com.greattone.greattone.Listener.PhotoCallBack;
import com.greattone.greattone.util.FileUtil;

public class PhotosTask extends AsyncTask<Object, Object, Object> {
	Context context;
	int type;
	PhotoCallBack photoCallBack;
	private ArrayList<ImageBean> mImages;
	public PhotosTask(Context context,int type,PhotoCallBack photoCallBack) {
		this.context=context;
		this.type=type;
		this.photoCallBack=photoCallBack;
	}
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Object doInBackground(Object... params) {
		if (type ==GalleryActivity. TYPE_PICTURE) {
			mImages = FileUtil.getImagesList(context);
		} else if (type == GalleryActivity.TYPE_VIDEO) {
			mImages = FileUtil.getVideoList(context);
		}
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
    	photoCallBack.initImageBean(mImages);
    }
}
