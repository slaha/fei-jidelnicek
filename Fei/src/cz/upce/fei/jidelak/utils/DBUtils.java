package cz.upce.fei.jidelak.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtils extends SQLiteOpenHelper {

	  public static final String TABLE_JIDLA = "jidla";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_DEN = "den";
	  public static final String COLUMN_POLIVKA = "polivka";
	  public static final String COLUMN_J1 = "jidlo1";
	  public static final String COLUMN_J2 = "jidlo2";
	  public static final String COLUMN_J3 = "jidlo3";

	  private static final String DATABASE_NAME = "jidla.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_JIDLA + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + 
	      COLUMN_DEN + " text, " +
	      COLUMN_POLIVKA + " text, " +
	      COLUMN_J1 + " text, " +
	      COLUMN_J2 + " text, " +
	      COLUMN_J3 + " text);";
	      
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
