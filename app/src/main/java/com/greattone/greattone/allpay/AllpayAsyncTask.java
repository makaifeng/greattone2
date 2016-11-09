package com.greattone.greattone.allpay;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AllpayAsyncTask<BackgroundClass extends BackgroundBase,APIClass extends API_Base> 
					extends AsyncTask<BackgroundClass, Void, APIClass>{
	private ProgressDialog pd ; 
	private AllpayBackgroundTaskCompleted listener;
	private Context cxt;
	private Class<APIClass> type;
	 
	
	public AllpayAsyncTask(AllpayBackgroundTaskCompleted listener, Class<APIClass> type){
	        this.listener = listener;
	        this.cxt = (Context)listener;
	        this.type = type;
	}
	public AllpayAsyncTask(AllpayBackgroundTaskCompleted listener, Class<APIClass> type, ProgressDialog pd){
		 	this(listener, type);
	        this.pd = pd;
	        this.pd.show();
	}
	
	 
	@Override
	protected APIClass doInBackground(BackgroundClass... params) {
		// TODO Auto-generated method stub
		APIClass result = null;
		try {
			BackgroundClass oParams = params[0];
			Log.d(Utility.LOGTAG, "AllpayAsyncTask.doInBackground -> " + oParams.getJSON());
			
			String sPath = oParams instanceof BackgroundOTP ? Utility.OTP : Utility.ServerOrder;			
			
			String source = HttpUtil.post(oParams.getEnvironment().toString() + sPath, 
											oParams.getPostData(), 
											cxt);
			
			result = new com.google.gson.Gson().fromJson(source, type);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				
		
		return result;
	}
	
	
	 @Override
	 protected void onPostExecute(APIClass result) {
	 // TODO Auto-generated method stub
		super.onPostExecute(result);
		
		try {
			if(pd != null && pd.isShowing())
				pd.dismiss();
			
			Log.d(Utility.LOGTAG, "AllpayAsyncTask.onPostExecute -> " + result.getJSON(cxt));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		listener.onAllpayBackgroundTaskCompleted(result);
	}
	 
	
}
