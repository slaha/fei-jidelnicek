package cz.upce.fei.jidelak.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtils extends SQLiteOpenHelper {

	public static final String TABLE_DNY = "dny";

	public static final String TABLE_JIDLA = "jidla";

	public static final String TABLE_JIDELNICKY = "jidelnicky";
	public static final String COLUMN_JIDELNA = "jidelna";
	public static final String COLUMN_JIDELNICEK = "jidelnicek";
	public static final String COLUMN_ID = "id";

	private static final String DATABASE_NAME = "jidla.db";
	private static final int DATABASE_VERSION = 5;

	// Database creation sql statement
	private static final String TABLE_JIDELNICKY_CREATE = new StringBuilder().append("create table ")
			.append(TABLE_JIDELNICKY).append("(").append(COLUMN_ID).append(" integer primary key autoincrement, ")
			.append(COLUMN_JIDELNA).append(" text, ").append(COLUMN_JIDELNICEK).append(" text);").toString();

	public DBUtils(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_JIDELNICKY_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_JIDLA);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DNY);
		onCreate(db);
	}

}
