package com.greattone.greattone.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "db_craftsman";
	private static final int DATABASE_VERSION = 1;
	public static final String ADDRESS_TABLE = "address";
	public static final String MAIN_ADDRESS = "mainAddress";
	public static final String DETAILED_ADDRESS = "detailedAddress";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE= "longitude";


	public SQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists " + ADDRESS_TABLE
				+ "(_id integer primary key autoincrement," + MAIN_ADDRESS
				+ " text," + DETAILED_ADDRESS + " text,"+LATITUDE+" REAL"+LONGITUDE+" REAL"+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {

	}
	//向表中增加数据
	public void addAddress(String mainAddress, String detailedaddress,double latitude,double longitude) {
		SQLiteDatabase db =getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MAIN_ADDRESS, mainAddress);
		values.put(DETAILED_ADDRESS, detailedaddress);
		values.put(LATITUDE, latitude);
		values.put(LONGITUDE, longitude);
		db.insert(ADDRESS_TABLE, "_id", values);
		db.close();
	}
	//删除
		public void del (int id){
			SQLiteDatabase db = getWritableDatabase();
			db.delete("data", "_id=?", new String[]{id+""});
			db.close();
		}
		//查询
		public List<Map<String, Object>> queryAddress(){
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query(ADDRESS_TABLE, null,null, null, null, null, null);
			while(cursor.moveToNext()){
				String mainAddress=cursor.getString(cursor.getColumnIndex(MAIN_ADDRESS));
				String detailedaddress=cursor.getString(cursor.getColumnIndex(DETAILED_ADDRESS));
				double latitude=cursor.getDouble(cursor.getColumnIndex(LATITUDE));
				double longitude=cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
				Map<String, Object> map=new HashMap<String, Object>();
				map.put(MAIN_ADDRESS, mainAddress);
				map.put(DETAILED_ADDRESS, detailedaddress);
				map.put(LATITUDE, latitude);
				map.put(LONGITUDE, longitude);
				map.put("id", cursor.getColumnIndex(DETAILED_ADDRESS));
				list.add(map);
			}
			db.close();
			return list;
		}
	// public void DBHelper(Context ctx) throws SQLException {
	// this.mCtx = ctx;
	// mDbHelper = new SQLiteHelper(mCtx);
	// mDb = mDbHelper.getWritableDatabase();
	// }
	//
	// /**
	// * 关闭数据源
	// */
	// public void closeConnection() {
	// if (mDb != null && mDb.isOpen())
	// mDb.close();
	// if (mDbHelper != null)
	// mDbHelper.close();
	// }
	// /**
	// * 插入数据 参数
	// * @param tableName 表名
	// * @param initialValues 要插入的列对应值
	// * @return
	// */
	// public long insert(String tableName, ContentValues initialValues) {
	// return mDb.insert(tableName, null, initialValues);
	// }
	// /**
	// * 删除数据
	// * @param tableName 表名
	// * @param deleteCondition 条件
	// * @param deleteArgs 条件对应的值（如果deleteCondition中有“？”号，将用此数组中的值替换，一一对应）
	// * @return
	// */
	// public boolean delete(String tableName, String deleteCondition, String[]
	// deleteArgs) {
	// return mDb.delete(tableName, deleteCondition, deleteArgs) > 0;
	// }
	// /**
	// * 更新数据
	// * @param tableName 表名
	// * @param initialValues 要更新的列
	// * @param selection 更新的条件
	// * @param selectArgs 更新条件中的“？”对应的值
	// * @return
	// */
	// public boolean update(String tableName, ContentValues initialValues,
	// String selection, String[] selectArgs) {
	// return mDb.update(tableName, initialValues, selection, selectArgs) > 0;
	// }
	// /**
	// * 取得一个列表
	// * @param distinct 是否去重复
	// * @param tableName 表名
	// * @param columns 要返回的列
	// * @param selection 条件
	// * @param selectionArgs 条件中“？”的参数值
	// * @param groupBy 分组
	// * @param having 分组过滤条件
	// * @param orderBy 排序
	// * @return
	// */
	// public Cursor findList(boolean distinct, String tableName, String[]
	// columns, String selection, String[] selectionArgs, String groupBy, String
	// having, String orderBy, String limit) {
	// return mDb.query(distinct, tableName, columns, selection, selectionArgs,
	// groupBy, having, orderBy, limit);
	// }
	// /**
	// * 取得单行记录
	// * @param tableName 表名
	// * @param columns 获取的列数组
	// * @param selection 条件
	// * @param selectionArgs 条件中“？”对应的值
	// * @param groupBy 分组
	// * @param having 分组条件
	// * @param orderBy 排序
	// * @param limit 数据区间
	// * @param distinct 是否去重复
	// * @return
	// * @throws SQLException
	// */
	// public Cursor findOne(boolean distinct, String tableName, String[]
	// columns, String selection, String[] selectionArgs, String groupBy, String
	// having, String orderBy, String limit) throws SQLException {
	// Cursor mCursor = findList(distinct, tableName, columns, selection,
	// selectionArgs, groupBy, having, orderBy, limit);
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }
	// /**
	// * 执行SQL(带参数)
	// * @param sql
	// * @param args SQL中“？”参数值
	// */
	// public void execSQL(String sql, Object[] args) {
	// mDb.execSQL(sql, args);
	// }
	// /**
	// * 执行SQL
	// * @param sql
	// */
	// public void execSQL(String sql) {
	// mDb.execSQL(sql);
	// }
	// /**
	// * 判断某张表是否存在
	// * @param tabName 表名
	// * @return
	// */
	// public boolean isTableExist(String tableName) {
	// boolean result = false;
	// if (tableName == null) {
	// return false;
	// }
	// try {
	// Cursor cursor = null;
	// String sql =
	// "select count(1) as c from sqlite_master where type ='table' and name ='"
	// + tableName.trim() + "'";
	// cursor = mDb.rawQuery(sql, null);
	// if (cursor.moveToNext()) {
	// int count = cursor.getInt(0);
	// if (count > 0) {
	// result = true;
	// }
	// }
	// cursor.close();
	// }
	// catch (Exception e) {
	// }
	// return result;
	// }
	// /**
	// * 判断某张表中是否存在某字段(注，该方法无法判断表是否存在，因此应与isTableExist一起应用)
	// * @param tabName 表名
	// * @param columnName 列名
	// * @return
	// */
	// public boolean isColumnExist(String tableName, String columnName) {
	// boolean result = false;
	// if (tableName == null) {
	// return false;
	// }
	// try {
	// Cursor cursor = null;
	// String sql =
	// "select count(1) as c from sqlite_master where type ='table' and name ='"
	// + tableName.trim() + "' and sql like '%" + columnName.trim() + "%'";
	// cursor = mDb.rawQuery(sql, null);
	// if (cursor.moveToNext()) {
	// int count = cursor.getInt(0);
	// if (count > 0) {
	// result = true;
	// }
	// }
	// cursor.close();
	// }
	// catch (Exception e) {
	// }
	// return result;
	// }
}
