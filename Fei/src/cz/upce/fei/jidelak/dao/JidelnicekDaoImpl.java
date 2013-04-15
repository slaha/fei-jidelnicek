package cz.upce.fei.jidelak.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

public class JidelnicekDaoImpl implements IDao {

	// Database fields
	private SQLiteDatabase database;
	private DBUtils dbHelper;

	private static final String WHERE_TYP = DBUtils.COLUMN_JIDELNA + " like ?";

	public JidelnicekDaoImpl(Context context) {
		dbHelper = new DBUtils(context);
	}

	@Override
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		dbHelper.close();
	}

	@Override
	public void save(String jidelnicek, JidelnicekTyp typ) {
		database.delete(DBUtils.TABLE_JIDELNICKY, WHERE_TYP, new String[] { typ.toString() });

		ContentValues values = new ContentValues();
		values.put(DBUtils.COLUMN_JIDELNICEK, jidelnicek);
		values.put(DBUtils.COLUMN_JIDELNA , typ.toString());

		database.insert(DBUtils.TABLE_JIDELNICKY, null, values);
	}


	@Override
	public String getJidelnicek(JidelnicekTyp typ) {
		Cursor c =  database.query(
				DBUtils.TABLE_JIDELNICKY,
				new String[] { DBUtils.COLUMN_JIDELNICEK },
				WHERE_TYP,
				new String[] { typ.toString() },
				null, null, null, null);

		String jidelnicekHTML = null;
		if (c.getCount() > 0) {
			c.moveToFirst();
			jidelnicekHTML = c.getString(0);
			c.close();
		}
		return jidelnicekHTML;
	}
}
