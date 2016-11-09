package com.greattone.greattone.allpay;


import java.lang.reflect.Field;

import android.content.Context;

public class API_Base {
	public int RtnCode;
	public String RtnMsg;
	
	public String getJSON(Context context) throws Exception
	{
		return new com.google.gson.Gson().toJson(this);
	}
	
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for (Field field : this.getClass().getFields()) {
			try {
				field.setAccessible(true); 
			    Object value = field.get(this); 
			    if (value != null) {
			    	sb.append(String.format("%s = %s\n", field.getName(), value));
			    }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		    
		}
		
		return sb.toString();
	}
	
	
}
