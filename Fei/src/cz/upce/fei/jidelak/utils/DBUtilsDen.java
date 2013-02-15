package cz.upce.fei.jidelak.utils;

import java.util.ArrayList;
import java.util.List;

import cz.upce.fei.jidelak.Den;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBUtilsDen {

	// Database fields
	  private SQLiteDatabase database;
	  private DBUtils dbHelper;
	  private String[] allColumns = { DBUtils.COLUMN_ID,
	      DBUtils.COLUMN_DEN, DBUtils.COLUMN_POLIVKA, DBUtils.COLUMN_J1, DBUtils.COLUMN_J2, DBUtils.COLUMN_J3 };

	  public DBUtilsDen(Context context) {
	    dbHelper = new DBUtils(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public void insertDays(List<Den> dny) {
		database.delete(DBUtils.TABLE_JIDLA, null, null);
		for (Den den : dny) {
			insertDay(den);
		}
	}
	  
	  private void insertDay(Den d) {
	    ContentValues values = new ContentValues();
	    values.put(DBUtils.COLUMN_DEN, d.getDen());
	    values.put(DBUtils.COLUMN_POLIVKA, d.getPolivka());
	    values.put(DBUtils.COLUMN_J1, d.getJ1());
	    values.put(DBUtils.COLUMN_J2, d.getJ2());
	    values.put(DBUtils.COLUMN_J3, d.getJ3());
	    database.insert(DBUtils.TABLE_JIDLA, null,
	        values);
	  }

	 public List<Den> getAllDays() {
	    List<Den> dny = new ArrayList<Den>();

	    Cursor cursor = database.query(DBUtils.TABLE_JIDLA,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Den den = cursorToDen(cursor);
	      dny.add(den);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return dny;
	  }

	  private Den cursorToDen(Cursor cursor) {
		Den d=  new Den(cursor.getString(1), //den 
				cursor.getString(2), //polivka
				cursor.getString(3), //jidlo
				cursor.getString(4), //jidlo
				cursor.getString(5) //jidlo
				);
	    d.setId(cursor.getLong(0));
	  
	    return d;
	  }

}
