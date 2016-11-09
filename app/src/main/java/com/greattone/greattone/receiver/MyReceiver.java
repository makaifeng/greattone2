package com.greattone.greattone.receiver;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.greattone.greattone.Listener.UpdateFileListener;

public class MyReceiver extends BroadcastReceiver {
	public static final String ACTION_UPDATE_PROGRESS = "my_update_progress";
	public static final String ACTION_UPDATE_SUCCESS = "my_update_success";
	public static final String ACTION_UPDATE_FAIL = "my_update_fail";
	Context context;UpdateFileListener updateFileListener;
	public MyReceiver(Context context,UpdateFileListener updateFileListener) {
		this.context=context;
		this.updateFileListener=updateFileListener;
	}
	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (action.equals(ACTION_UPDATE_PROGRESS)) {
			long pro = intent.getExtras().getLong("progress");
			long max = intent.getExtras().getLong("max");
			updateFileListener.onProgressUpdate(pro,max);
//			progressBar.setVisibility(View.VISIBLE);
//			progressBar.setProgress((int)(( pro/ (float) max) * 100));
		}
		else if(action.equals(ACTION_UPDATE_SUCCESS)){
			updateFileListener.updateSuccess(null);
			
		}
		else if(action.equals(ACTION_UPDATE_FAIL)){
		}

	}

}
