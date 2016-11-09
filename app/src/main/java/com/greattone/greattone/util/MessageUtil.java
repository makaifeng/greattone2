package com.greattone.greattone.util;

import com.greattone.greattone.entity.UserInfo;

public class MessageUtil {
	/**获取身份*/
	 public static String getIdentity(UserInfo info){
		 String identity=null;
		 if (info.getGroupid()==1) {//普通会员
			 if (info.getPutong_shenfen()!=null&&info.getPutong_shenfen().length()>1) {
				 identity=info.getPutong_shenfen();
			 }else{
				 identity=info.getGroupname();
			 }
		}else if (info.getGroupid()==2) {//音乐名人
			 if (info.getMusic_star()!=null&&info.getMusic_star().length()>1) {
				 identity=info.getMusic_star();
			 }else{
				 identity=info.getGroupname();
			 }
		 }else
		 if (info.getGroupid()==3) {//音乐老师
			 if (info.getTeacher_type()!=null&&info.getTeacher_type().length()>1) {
				 identity=info.getTeacher_type();
			 }else{
				 identity=info.getGroupname();
			 }
		 }else
		 if (info.getGroupid()==4) {//音乐教室
			 if (info.getClassroom_type()!=null&&info.getClassroom_type().length()>1) {
				 identity=info.getClassroom_type();
			 }else{
				 identity=info.getGroupname();
			 }
		 }else		 if (info.getGroupid()==5) {//品牌
				 identity=info.getGroupname();
		 }else{
			 identity=info.getGroupname();
		 }
		return identity;
	 }

}
