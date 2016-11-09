package com.greattone.greattone.activity.chat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

//import com.greattone.greattone.base.BaseActivity;

/**
 * 这个累是对图片表情的处理
 * 
 * @author Administrator
 * 
 */
public class FaceImageDeal {

	static int width, heigth;

	/**
	 * 通过传来的key来获取图片 2015-7-2 yff
	 */
	public static SpannableString keyToString(Activity activity, String key) {

		String imageName = getQqFaceName(activity, key, true);
		int resId = IdentifierFindId.getDrawableId(activity, imageName);
		if (resId == 0) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
				resId);
		ImageSpan imageSpan = new ImageSpan(activity, bitmap);
		SpannableString spannableString = new SpannableString(key);
		spannableString.setSpan(imageSpan, 0, key.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;

	}

	/**
	 * 通过传来的图片的文件名来获取图片 2015-7-2 yff
	 */
	public static SpannableString valueToString(Context context, String value) {
		String key = getQqFaceName(context, value, false);
		int resId = IdentifierFindId.getDrawableId(context, value);
		if (resId == 0) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		ImageSpan imageSpan = new ImageSpan(context, bitmap);
		SpannableString spannableString = new SpannableString(key);
		spannableString.setSpan(imageSpan, 0, key.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;

	}

	// /**
	// * 通过传来的图片的的资源id 2015-7-2 yff
	// */
	// public static SpannableString resIdToString(Activity activity, String
	// resId) {
	// if (!StringUtil.isNum(resId)) {
	// return null;
	// }
	//
	// String imageName = new Expressions(activity).getImageNameToResId(resId);
	// String key = ExpressionUtil.getQqFaceName(activity, imageName, false);
	//
	// Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
	// Integer.parseInt(resId));
	// ImageSpan imageSpan = new ImageSpan(activity, bitmap);
	// SpannableString spannableString = new SpannableString(key);
	// spannableString.setSpan(imageSpan, 0, key.length(),
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	// return spannableString;
	// }

	/**
	 * @param context
	 * @param str
	 * @param isChat
	 *            : 是否是聊天的表情
	 * @return
	 */
	public static SpannableString changeString(Context context, String str,
			boolean isChat) {

		// if(isChat){
		// setFaceWeightHeight(context,0);
		// }else{
		// setFaceWeightHeight(context,((BaseActivity)context).screenWidth/6 -
		// 10);
		// }

		String zz = "\\[([^\\[\\]]+)\\]";
		SpannableString spannableString = new SpannableString(str);
		Pattern sinaPatten = Pattern.compile(zz, Pattern.CASE_INSENSITIVE); // 通过传入的正则表达式来生成一个pattern
		try {
			dealExpression(context, spannableString, sinaPatten, 0);
		} catch (Exception e) {
			Log.e("dealExpression", e.getMessage());
		}
		return spannableString;
	}

	/**
	 * 对spanableString进行正则判断，如果符合要求，则以表情图片代替
	 * 
	 * @param context
	 * @param spannableString
	 * @param patten
	 * @param start
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void dealExpression(Context context,
			SpannableString spannableString, Pattern patten, int start)
			throws SecurityException, NoSuchFieldException,
			NumberFormatException, IllegalArgumentException,
			IllegalAccessException {
//		if (width == 0 || heigth == 0) {
//			setFaceWeightHeight(context, 0);
//		}
		Matcher matcher = patten.matcher(spannableString);
		while (matcher.find()) {
			String key = matcher.group();
			if (matcher.start() < start) {
				continue;
			}
			int resId = IdentifierFindId.getDrawableId(context,
					getQqFaceName(context, key, true));

			if (resId != 0) {
				// Bitmap bitmap = BitmapFactory.decodeResource(
				// context.getResources(), resId);
				// Bitmap bitmap1 =
				// Bitmap.createScaledBitmap(bitmap,width,heigth,true);
				ImageSpan imageSpan = new ImageSpan(context, resId);
				// 通过图片资源id来得到bitmap，用一个ImageSpan来包装
				int end = matcher.start() + key.length(); // 计算该图片名字的长度，也就是要替换的字符串的长度
				spannableString.setSpan(imageSpan, matcher.start(), end,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // 将该图片替换字符串中规定的位置中
				if (end < spannableString.length()) { // 如果整个字符串还未验证完，则继续。。
					dealExpression(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

//	public static void setFaceWeightHeight(Context context, int size) {
//		if (size == 0) {
//			width = ((BaseActivity) context).screenWidth / 6;
//			heigth = ((BaseActivity) context).screenHeight / 6;
//		} else {
//			width = size;
//			heigth = size;
//		}
//
//	}
    /**
     * 根据qq表情的在标识符获取qq表情的文件名 2015-7-1 yff
     * iskey: true(name:[乒乓]    返回  :Expression_60)
     *        flase(name:Expression_60    返回  :[乒乓)
     */
    public static String getQqFaceName(Context context, String name,boolean isKey) {

        try {
            InputStream input = context.getResources().getAssets()
                    .open(StaticString.QQFACE);

            String content = inputStream2String(input).trim();
            String key = content.replace("<key>", "");
            String string = key.replace("<string>", "");
            String[] value = string.split("</string>");
            for (int i = 0; i < value.length; i++) {
                String[] data = value[i].split("</key>");
                if(isKey){
                    if (data[0].trim().equals(name)) {
                        return data[1].trim();
                    }
                }else{
                    if (data[1].trim().equals(name)) {
                        return data[0].trim();
                    }
                }

            }
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }
    public static String inputStream2String(InputStream is) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
}
