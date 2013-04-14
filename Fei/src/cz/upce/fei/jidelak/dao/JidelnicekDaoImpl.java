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

	/*private static final String SQL_SELECT = new StringBuilder()
				.append("select ") 
				.append(DBUtils.TABLE_DNY).append(".").append(DBUtils.COLUMN_ID).append(", ")
				.append(DBUtils.COLUMN_DEN).append(", ")
				.append(DBUtils.COLUMN_JIDLO) 
				.append(" from ").append(DBUtils.TABLE_DNY)
				.append(" left join ").append(DBUtils.TABLE_JIDLA) 
				.append(" on ").append(DBUtils.TABLE_DNY).append(".").append(DBUtils.COLUMN_ID)
				.append(" = ")
				.append(DBUtils.TABLE_JIDLA).append(".").append(DBUtils.COLUMN_ID_DEN)
				.append(" where ").append(WHERE_TYP) 
				.append(" order by ").append(DBUtils.TABLE_DNY).append(".").append(DBUtils.COLUMN_ID)
				.append(", ").append(DBUtils.TABLE_JIDLA).append(".").append(DBUtils.COLUMN_ID)
				.append(";")
				.toString();
	*/

	private static final String SQL_SELECT = new StringBuilder()
				.append("select ")
				.append(DBUtils.COLUMN_ID).append(", ")
				.append(DBUtils.COLUMN_JIDELNA).append(", ")
				.append(DBUtils.COLUMN_JIDELNICEK)
				.append(" from ").append(DBUtils.TABLE_JIDELNICKY)
				.append(";")
				.toString();

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
