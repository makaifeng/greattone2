package com.greattone.greattone.util;

import android.content.Context;

import java.util.Locale;
/**
 * 手机语言
 * @author Administrator
 *
 */
public class LanguageUtil {
	static String language="other";
	public static void init(Context context){
		//判断手机语言
		Locale locale = Locale.getDefault();
		if (locale.getLanguage().equals("zh")&&locale.getCountry().equals("TW")) {//手机语言是中文繁体
			language="TW";
		}else 	if (locale.getLanguage().equals("zh")&&locale.getCountry().equals("CN")) {
			language="CN";
		}else {
			language="other";
		}
	}
	public static String getLanguage(){
		return language;
	}
}
