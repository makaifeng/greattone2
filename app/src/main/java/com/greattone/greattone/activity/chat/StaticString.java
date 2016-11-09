package com.greattone.greattone.activity.chat;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015/7/22.
 */
public class StaticString {
    /**sdk路径*/
    public final static String  SDK = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**拍照图片存放的地方*/
    public final static String PICTUREPATH = SDK + "/haoqinsheng/image/";
    /**语音图片存放的地方*/
    public final static String VOICEPATH = SDK + "/haoqingsheng/vocie/";
    /**获取qq表情图片的时候需要解析的文件名,来对应图片*/
    public final static String QQFACE = "qqface.txt";

    public static void  init(){

        File file = new File(PICTUREPATH);
        File file1 = new File(VOICEPATH);
        if(!file.exists()){
            file.mkdirs();
        }
        if(!file1.exists()){
            file1.mkdirs();
        }
    }
}
