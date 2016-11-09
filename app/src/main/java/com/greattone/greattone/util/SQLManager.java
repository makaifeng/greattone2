package com.greattone.greattone.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greattone.greattone.data.DBHelper;
import com.greattone.greattone.entity.Chat;

public class SQLManager  {
	public	static SQLManager sql;
	public	static SQLiteDatabase    db;
//	public	static 	DBHelper  	helper;
	public static SQLManager bulid(Context context){
		sql=new SQLManager();
		DBHelper	  	helper = new DBHelper(context);  
		    db = helper.getWritableDatabase();
			return sql;
	}
	/**
	 * 添加
	 * @param context
	 * @param values
	 * @param name
	 */
	public  void addFeeds(Context context,List<Chat> chats) {  
		Cursor cursor = null;
		db.beginTransaction();  //开始事务  
		try {  
			for (Chat chat : chats) {
				cursor= 	db.query("chat", new String[]{"_id","username","no_read_num"}, "username=?", new String[]{chat.getFrom_username()}, null, null, null);
				ContentValues contentValues=new ContentValues();
				contentValues.put("userid",Integer.valueOf(chat.getFrom_userid()));
				contentValues.put("username", chat.getFrom_username());
				contentValues.put("last_text", chat.getMsgtext());
				contentValues.put("saytime",Long.valueOf( chat.getMsgtime()));
				if (cursor.getCount()==0) {
					contentValues.put("no_read_num", 1);
					db.insert("chat", null, contentValues);
				}else {
					
				}
			}
			db.setTransactionSuccessful();  //设置事务成功完成  
		} finally {  
			db.endTransaction();    //结束事务  
		}  
		if (cursor!=null) {
			cursor.close();
		}
	}  
	/**
	 * 添加
	 * @param context
	 * @param values
	 * @param name
	 */
	public  void addChats(Context context,List<Chat> chats) {  
		Cursor cursor = null;
		db.beginTransaction();  //开始事务  
		try {  
			for (Chat chat : chats) {
				cursor= 	db.query("chat", new String[]{"_id","username","no_read_num"}, "username=?", new String[]{chat.getFrom_username()}, null, null, null);
				ContentValues contentValues=new ContentValues();
				contentValues.put("userid",Integer.valueOf(chat.getFrom_userid()));
				contentValues.put("username", chat.getFrom_username());
				contentValues.put("last_text", chat.getMsgtext());
				contentValues.put("saytime",Long.valueOf( chat.getMsgtime()));
				if (cursor.getCount()==0) {
					contentValues.put("no_read_num", 1);
					db.insert("chat", null, contentValues);
				}else {

				}
			}
			db.setTransactionSuccessful();  //设置事务成功完成  
		} finally {  
			db.endTransaction();    //结束事务  
		}  
		if (cursor!=null) {
			cursor.close();
		}
	}  
/**
 * 添加
 * @param context
 * @param values
 * @param name
 */
	    public  void addChat(Context context,ContentValues values,String name) {  
	    	Cursor cursor;
	        db.beginTransaction();  //开始事务  
	        try {  
	        	cursor= 	db.query("chat", new String[]{"_id","username","no_read_num"}, "username=?", new String[]{name}, null, null, null);
	        	 if (cursor.getCount()==0) {
	        		 values.put("no_read_num", 1);
	        		 db.insert("chat", null, values);
				}else {
					cursor.moveToNext();
					values.put("no_read_num", cursor.getInt(cursor.getColumnIndex("no_read_num"))+1);
					update(context, values, name);
				}
	            db.setTransactionSuccessful();  //设置事务成功完成  
	        } finally {  
	            db.endTransaction();    //结束事务  
	        }  
	        cursor.close();
	    }  
	    /**
	     * 
	     * @param context
	     * @param values
	     * @param name
	     */
	    public void update(Context context,ContentValues values,String name){
	    	db.update("chat", values, "username=?", new String[]{name} );
	    }
	    /** 
	     * query all persons, return list 
	     * @return List<Person> 
	     */  
	    public  List<Chat> queryChat(Context context,String name) {  
	    	List<Chat> chats=new ArrayList<Chat>();
	      	Cursor c = db.query("chat", new String[]{"_id","username","userid","no_read_num","last_text","saytime"}, "username=?", new String[]{name}, null, null, null);
				
	      
	        if (c.isBeforeFirst()) {
	      	while (c.moveToNext()) {
	            Chat chat = new Chat();  
	            chat.setFrom_userid( c.getInt(c.getColumnIndex("userid"))+"");
	            chat.setFrom_username( c.getString(c.getColumnIndex("username")));
	            chat.setIssys(c.getInt(c.getColumnIndex("no_read_num")));
	            chat.setMsgtext( c.getString(c.getColumnIndex("last_text")));
	            chat.setMsgtime(c.getString(c.getColumnIndex("saytime")));
	            chats.add(chat);  
	        }  
	      	}else {
	      	while (c.moveToFirst()) {
	            Chat chat = new Chat();  
	            chat.setFrom_userid( c.getInt(c.getColumnIndex("userid"))+"");
	            chat.setFrom_username( c.getString(c.getColumnIndex("username")));
	            chat.setIssys(c.getInt(c.getColumnIndex("no_read_num")));
	            chat.setMsgtext( c.getString(c.getColumnIndex("last_text")));
	            chat.setMsgtime(c.getString(c.getColumnIndex("saytime")));
	            chats.add(chat);  
	        }  
			}
	        c.close();  
	        return chats;  
	    }  
	    /** 
	     * query all persons, return list 
	     * @return List<Person> 
	     */  
	    public  List<Chat> queryChats(Context context) {  
	    	List<Chat> chats=new ArrayList<Chat>();
	    	Cursor c = db.query("chat", new String[]{"_id","username","userid","no_read_num","last_text","saytime"}, "username!=?", new String[]{"all"}, null, null, null);
	    	while (c.moveToNext()) {  
	    		Chat chat = new Chat();  
	    		chat.setFrom_userid( c.getInt(c.getColumnIndex("userid"))+"");
	    		chat.setFrom_username( c.getString(c.getColumnIndex("username")));
	    		chat.setIssys(c.getInt(c.getColumnIndex("no_read_num")));
	    		chat.setMsgtext( c.getString(c.getColumnIndex("last_text")));
	    		chat.setMsgtime(c.getLong(c.getColumnIndex("saytime"))+"");
	    		chats.add(chat);  
	    	}  
	    	c.close();  
	    	return chats;  
	    }  
	    /** 
	     * close database 
	     */  
	    public void closeDB() {  
	        db.close();  
	    } 
}
