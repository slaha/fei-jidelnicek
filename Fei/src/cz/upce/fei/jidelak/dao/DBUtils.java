package cz.upce.fei.jidelak.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtils extends SQLiteOpenHelper {

	  public static final String TABLE_JIDLA = "dny";
	  
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_DEN = "den";
	  public static final String COLUMN_POLIVKA = "polivka";
	  public static final String COLUMN_JIDLO_1 = "jidlo1";
	  public static final String COLUMN_JIDLO_2 = "jidlo2";
	  public static final String COLUMN_JIDLO_3 = "jidlo3";
	  public static final String COLUMN_BEZMASE = "bezmase";
	  public static final String COLUMN_VECERE_1 = "vecere1";
	  public static final String COLUMN_VECERE_2 = "vecere2";
	  public static final String COLUMN_TYP = "typ";

	  private static final String DATABASE_NAME = "jidla.db";
	  private static final int DATABASE_VERSION = 2;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_JIDLA + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + 
	      COLUMN_DEN + " text, " +
	      COLUMN_POLIVKA + " text, " +
	      COLUMN_JIDLO_1 + " text, " +
	      COLUMN_JIDLO_2 + " text, " +
	      COLUMN_JIDLO_3 + " text, " +
	      COLUMN_BEZMASE + " text, " +
	      COLUMN_VECERE_1 + " text, " +
	      COLUMN_VECERE_2 + " text, " +
	      COLUMN_TYP + " text);";
	      
	  public DBUtils(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_JIDLA);
	    onCreate(db);
	  }
	

}
