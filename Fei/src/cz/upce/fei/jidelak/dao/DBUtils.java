package cz.upce.fei.jidelak.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtils extends SQLiteOpenHelper {
	  
	public static final String ENABLE_FOREIGN_KEYS = "PRAGMA foreign_keys=ON;";

	public static final String TABLE_DNY = "dny";
		  public static final String COLUMN_ID = "_id";
		  public static final String COLUMN_DEN = "den";
		  public static final String COLUMN_TYP = "typ";
	  
	  public static final String TABLE_JIDLA = "jidla";
		  public static final String COLUMN_JIDLO = "jidlo";
		  public static final String COLUMN_ID_DEN = "id_dne";


	  private static final String DATABASE_NAME = "jidla.db";
	  private static final int DATABASE_VERSION = 4;

	  // Database creation sql statement
	  private static final String TABLE_DNY_CREATE = "create table "
	      + TABLE_DNY + 
	      "(" + COLUMN_ID + " integer primary key autoincrement, " + 
	      COLUMN_DEN + " text, " +
	      COLUMN_TYP + " text);";
	  
	  private static final String TABLE_JIDLA_CREATE = "create table "
			  + TABLE_JIDLA + 
			  "(" + COLUMN_ID + " integer primary key autoincrement, " + 
			  COLUMN_JIDLO + " text, " +
			  COLUMN_ID_DEN + " integer,"
			  + " FOREIGN KEY (" + COLUMN_ID_DEN +") REFERENCES " + TABLE_DNY + " (" + COLUMN_ID + ") ON DELETE CASCADE);";
	  
	  
	  public DBUtils(Context context) {
		  super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  if (!database.isReadOnly()) {
			     // Enable foreign key constraints
			     database.execSQL(ENABLE_FOREIGN_KEYS);
			   }
		  database.execSQL(TABLE_DNY_CREATE);
		  database.execSQL(TABLE_JIDLA_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_JIDLA);
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DNY);
	    onCreate(db);
	  }
	

}
