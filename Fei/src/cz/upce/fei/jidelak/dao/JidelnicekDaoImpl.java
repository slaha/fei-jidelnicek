package cz.upce.fei.jidelak.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cz.upce.fei.jidelak.model.Menu;
import cz.upce.fei.jidelak.model.MenuType;

public class JidelnicekDaoImpl implements IDao<Menu, MenuType> {

	// Database fields
	private SQLiteDatabase database;
	private DBUtils dbHelper;

	private boolean isOpen;

	private static final String WHERE_TYP = DBUtils.COLUMN_JIDELNA + " like ?";

	public JidelnicekDaoImpl(Context context) {
		dbHelper = new DBUtils(context);
	}

	private void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
		isOpen = true;
	}

	private void close() {
		dbHelper.close();
		isOpen = false;
	}

	@Override
	public void save(Menu menu, MenuType typ) {

		open();

		database.delete(DBUtils.TABLE_JIDELNICKY, WHERE_TYP, new String[] { typ.toString() });

		ContentValues values = new ContentValues();
		values.put(DBUtils.COLUMN_JIDELNICEK, menu.getSaveableString());
		values.put(DBUtils.COLUMN_JIDELNA, typ.toString());

		database.insert(DBUtils.TABLE_JIDELNICKY, null, values);

		close();
	}

	@Override
	public SaveableBundle<Menu, MenuType> getMultiple(MenuType... menuTypes) throws SQLException {

		SaveableBundle<Menu, MenuType> bdl = new SaveableBundle<Menu, MenuType>();

		open();

		for (MenuType type : menuTypes) {
			Menu m = get(type);
			bdl.add(m, type);
		}

		close();

		return bdl;
	}

	@Override
	public Menu get(MenuType typ) {
		boolean needToClose = false;
		if (!isOpen) {
			open();
			needToClose = true;
		}

		Cursor c = database.query(DBUtils.TABLE_JIDELNICKY, new String[] { DBUtils.COLUMN_JIDELNICEK }, WHERE_TYP,
				new String[] { typ.toString() }, null, null, null, null);

		String jidelnicekHtml = null;
		if (c.getCount() > 0) {
			c.moveToFirst();
			jidelnicekHtml = c.getString(0);
			c.close();
		}

		if (needToClose) {
			close();
		}
		return new Menu(jidelnicekHtml);
	}
}
