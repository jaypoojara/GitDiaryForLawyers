package com.rhmc.diaryforlawyers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	public static String DB_NAME = "mydb.db";
	public static int DB_VERSION = 1;
	//table constatnts
	public static String TABLE = "lawyer_diary";
	public static String SRNO = "srno";
	public static String CASE_NO = "caseno";
	public static String COURT_NAME = "courtname";
	public static String COMMENT = "comment";
	public static String DATE = "date_hearing";
	public static String PARTY_NAME = "party_name";
	public static String CATEGORY = "category";
	public static String CITY = "city";
	public static String DATE_TABLE = "date_table";
	public static String OPPONENT_NAME = "opponent_name";
	public static String JUDGE_NAME = "judge_name";
	public DatabaseHelper(Context context) {
		super(context,DB_NAME,null,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE +"("
				+SRNO+" INTEGER PRIMARY KEY,"
				+CASE_NO+ " TEXT,"
				+JUDGE_NAME+ " TEXT,"
				+COURT_NAME+" TEXT,"
				+PARTY_NAME+" TEXT,"
				+OPPONENT_NAME+" TEXT,"
				+DATE+" TEXT,"
				+CATEGORY+" TEXT,"
				+COMMENT+" TEXT,"
				+CITY+" TEXT);");
		db.execSQL("CREATE TABLE "+ DatabaseHelper.DATE_TABLE+"( "
				+SRNO+" INTEGER,"
				+DATE+" TEXT,"
				+" FOREIGN KEY("+SRNO+") REFERENCES "+TABLE+" ON DELETE CASCADE );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
