package com.greattone.greattone.widget.emoji;

public class EmojiUtils {
	
	public static String convertTag(String str){
		return str
		.replaceAll("<", "&lt;")
		.replaceAll("%%i:1%%","<img src=\"e001\"/>")
		.replaceAll("%%i:2%%","<img src=\"e002\"/>")
		.replaceAll("%%i:3%%","<img src=\"e003\"/>")
		.replaceAll("%%i:4%%","<img src=\"e004\"/>")
		.replaceAll("%%i:5%%","<img src=\"e005\"/>")
		.replaceAll("%%i:6%%","<img src=\"e006\"/>")
		;
	}
}