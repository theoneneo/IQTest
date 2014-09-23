package com.psychoanalysis.iqtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private IQTestApplication app;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
		app = IQTestApplication.getApplication();
		if (app == null) {
			return;
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void CloseDB() {
		close();
	}

	public void initTenoriData() {
		
		Log.i("DatabaseHelper", "initTenoriData() ... start !");
		
		SQLiteDatabase db = getWritableDatabase();

		StringBuffer sb = new StringBuffer();
		sb.append("create table if not exists TenoriTable(");
		sb.append("name text,");
		sb.append("idx text,");
		sb.append("content text);");

		try {
			db.execSQL(sb.toString());
		} catch (SQLException e) {
			Log.e("ERROR", e.toString());
		}
		//close();
	}

	public void insertTenoriData(String name, String index, String content) {
		name = app.toValidRs(name);
		index = app.toValidRs(index);
		;
		content = app.toValidRs(content);
		SQLiteDatabase db = getWritableDatabase();
		String check = "select * from TenoriTable where name = '" + name
				+ "' and idx = '" + index + "'";
		try {
			Cursor tmp = db.rawQuery(check, null);

			if (tmp.getCount() == 0) {
				StringBuffer sb = new StringBuffer();
				sb.append("insert into TenoriTable (name, idx, content)");
				sb.append("values('");
				sb.append(name);
				sb.append("','");
				sb.append(index);
				sb.append("','");
				sb.append(content);
				sb.append("');");
				try {
					db.execSQL(sb.toString());
				} catch (SQLException e) {
					Log.e("ERROR", e.toString());
				}
				close();
			} else {
				ContentValues cv = new ContentValues();
				cv.put("name", name);
				cv.put("idx", index);
				cv.put("content", content);
				db
						.update("TenoriTable", cv,
								"idx = " + "'" + index + "'", null);
				close();
			}
		} catch (SQLException ce) {
			Log.e("ERROR", ce.toString());
			close();
		}
	}
	
	public void insertTenoriData(String index, String content) {

		index = app.toValidRs(index);
		;
		//content = app.toValidRs(content);
		SQLiteDatabase db = getWritableDatabase();
		String check = "select * from TenoriTable where " 
				+ " idx = '" + index + "'";
		
		String name = "12*12";
		try {
			Cursor tmp = db.rawQuery(check, null);

			if (tmp.getCount() == 0) {
				StringBuffer sb = new StringBuffer();
				sb.append("insert into TenoriTable (name, idx, content)");
				sb.append("values('");
				sb.append(name);
				sb.append("','");
				sb.append(index);
				sb.append("','");
				sb.append(content);
				sb.append("');");
				try {
					db.execSQL(sb.toString());
				} catch (SQLException e) {
					Log.e("ERROR", e.toString());
				}
				//close();
			} else {
				ContentValues cv = new ContentValues();
				cv.put("idx", index);
				cv.put("content", content);
				db
						.update("TenoriTable", cv,
								"idx = " + "'" + index + "'", null);
				//close();
			}
		} catch (SQLException ce) {
			Log.e("ERROR", ce.toString());
			//close();
		}
	}

	public Cursor checkTableEmpty() {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "select * from TenoriTable";
		try {
			Cursor cur = db.rawQuery(sql, null);
			cur.moveToFirst();
			close();
			return cur;
		} catch (SQLException e) {
			close();
			return null;
		}
	}

	public Cursor checkIndexExists(String idx) {
		SQLiteDatabase db = getReadableDatabase();
		//String sql = "select * from TenoriTable where idx = ?";
		String sql = "select * from TenoriTable where "
		+ " idx = '" + idx + "'";
		Cursor cur = db.rawQuery(sql, null);
		return cur;

	}

	public void deleteTenoriData(String idx) {
		try {

			idx = app.toValidRs(idx);

			SQLiteDatabase db = getWritableDatabase();

			db.delete("TenoriTable", "idx = '" + idx + "'", null);
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
