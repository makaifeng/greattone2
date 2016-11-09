package com.greattone.greattone.util;


import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class PhoneInfo {
    private TelephonyManager telephonemanager;
    private Context ctx;
    /**
     * 获取手机国际识别码IMEI
     * */
    public  PhoneInfo(Context context){
        ctx=context;
        telephonemanager=(TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
    }
    
    /**
     * 获取手机号码 
     * */
    public String getDeviceID(){
       
       String deviceId=null;
       deviceId=telephonemanager.getDeviceId();
        
       return deviceId;        
    }
    
    /**
     * 获取手机信息
     * */
    public String getPhoneInfo(){
       
        TelephonyManager tm=(TelephonyManager)ctx.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder sb=new StringBuilder();
    
        sb.append("\nDeviceID(IMEI)"+tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion:"+tm.getDeviceSoftwareVersion());
        sb.append("\ngetLine1Number:"+tm.getLine1Number());
        sb.append("\nNetworkCountryIso:"+tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator:"+tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName:"+tm.getNetworkOperatorName());
        sb.append("\nNetworkType:"+tm.getNetworkType());
        sb.append("\nPhoneType:"+tm.getPhoneType());
        sb.append("\nSimCountryIso:"+tm.getSimCountryIso());
        sb.append("\nSimOperator:"+tm.getSimOperator());
        sb.append("\nSimOperatorName:"+tm.getSimOperatorName());
        sb.append("\nSimSerialNumber:"+tm.getSimSerialNumber());
        sb.append("\ngetSimState:"+tm.getSimState());
        sb.append("\nSubscriberId:"+tm.getSubscriberId());
        sb.append("\nVoiceMailNumber:"+tm.getVoiceMailNumber());
        
        return sb.toString();
                
    }            
    public static String getDeviceInfo(Context context) {
        try{
          org.json.JSONObject json = new org.json.JSONObject();
          TelephonyManager tm = (TelephonyManager) context
              .getSystemService(Context.TELEPHONY_SERVICE);

          String device_id = tm.getDeviceId();

          android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

          String mac = wifi.getConnectionInfo().getMacAddress();
          json.put("mac", mac);

          if( TextUtils.isEmpty(device_id) ){
            device_id = mac;
          }

          if( TextUtils.isEmpty(device_id) ){
            device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
          }

          json.put("device_id", device_id);

          return json.toString();
        }catch(Exception e){
          e.printStackTrace();
        }
      return null;
    }

}