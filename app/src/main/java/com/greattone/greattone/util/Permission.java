package com.greattone.greattone.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.greattone.greattone.Listener.OnRequestPermissionsBackListener;
import com.greattone.greattone.activity.BaseActivity;

/**
 * Created by Administrator on 2016/11/9.
 */
public class Permission {
    BaseActivity activity;
    /**读写文件的权限返回*/
    final public static int REQUEST_CODE_READ_EXTERNAL_STORAGE = 123;
    /**相机权限返回*/
    final public static int REQUEST_CODE_CAMERA = 124;
    /**音频的权限返回*/
    final public static int REQUEST_CODE_RECORD_AUDIO = 125;
    boolean back=true;
    /**
     *监听是否有相机权限
     * @param activity BaseActivity
     */
        public   boolean hasPermission_CAMERA(BaseActivity activity){
            this.activity=activity;
            try {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(
                        activity.	getApplicationContext(),
                        Manifest.permission.CAMERA);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat
                            .requestPermissions(
                                    activity,
                                    new String[] { Manifest.permission.CAMERA },
                                     REQUEST_CODE_CAMERA);
                    activity.toast("无权拍照限使用，请打开权限");
                    return false;
                }
                activity.setOnRequestPermissionsBack(new OnRequestPermissionsBackListener() {
                    @Override
                    public void onRequestPermissionsBack(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                     if (requestCode ==  REQUEST_CODE_CAMERA) {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                back =true;
                            } else {
                                back =  false;
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return back;
        }

    /**
     * 监听是否有读写内存权限
     * @param activity
     */
        public   boolean hasPermission_READ_EXTERNAL_STORAGE(BaseActivity activity){
            this.activity=activity;
            try {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(
                        activity.	getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat
                            .requestPermissions(
                                    activity,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                     REQUEST_CODE_READ_EXTERNAL_STORAGE);
                    activity.toast("无读写权限使用，请打开权限");
                    return  false;
                }
                activity.setOnRequestPermissionsBack(new OnRequestPermissionsBackListener() {
                    @Override
                    public void onRequestPermissionsBack(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                        if (requestCode ==  REQUEST_CODE_READ_EXTERNAL_STORAGE) {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                back =true;
                            } else {
                                back =  false;
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return back;
        }

    /**
     *  监听是否有录音权限
     * @param activity
     * @return
     */
        public   boolean hasPermission_RECORD_AUDIO(BaseActivity activity){
            this.activity=activity;
            try {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(
                        activity.	getApplicationContext(),
                        Manifest.permission.RECORD_AUDIO);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat
                            .requestPermissions(
                                    activity,
                                    new String[] { Manifest.permission.RECORD_AUDIO },
                                     REQUEST_CODE_RECORD_AUDIO);
                    activity.toast("无录音权限使用，请打开权限");
                    return  false;
                }
                activity.setOnRequestPermissionsBack(new OnRequestPermissionsBackListener() {
                    @Override
                    public void onRequestPermissionsBack(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                        if (requestCode ==  REQUEST_CODE_RECORD_AUDIO) {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                               back =true;
                            } else {
                                back =  false;
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  back;
        }
}


