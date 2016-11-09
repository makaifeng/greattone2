package com.greattone.greattone.activity.chat;

import android.content.Context;

/**
 * 通过反射来找到资源文件
 * 
 * @author Administrator
 * 
 */
public class IdentifierFindId {

	public static int getLayoutId(Context paramContext, String paramString) {
		
		return getResId(paramContext,paramString,"layout");
	}

	public static int getStringId(Context paramContext, String paramString) {
		
		 return getResId(paramContext,paramString,"string");
	}

	public static int getDrawableId(Context paramContext, String paramString) {
		
		 return getResId(paramContext,paramString,"drawable");
	}

	public static int getStyleId(Context paramContext, String paramString) {
		
		 return getResId(paramContext,paramString,"style");
	}

	public static int getId(Context paramContext, String paramString) {
		
		 return getResId(paramContext,paramString,"id");
	}

	public static int getColorId(Context paramContext, String paramString) {

		return getResId(paramContext, paramString, "color");
	}

	public static int getArrayId(Context paramContext, String paramString) {

		return getResId(paramContext, paramString, "array");
	}

	private static int getResId(Context paramContext, String paramString,
			String type) {

		return paramContext.getResources().getIdentifier(paramString,type,
				paramContext.getPackageName());
	}
}
