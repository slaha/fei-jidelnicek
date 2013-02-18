package cz.upce.fei.jidelak.dao;

import java.util.ArrayList;
import java.util.List;

import cz.upce.fei.jidelak.model.DenniJidelnicekFeiImpl;
import cz.upce.fei.jidelak.model.DenniJidelnicekKampusImpl;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DaoDenImpl implements IDao<IDenniJidelnicek> {

	// Database fields
	private SQLiteDatabase database;
	private DBUtils dbHelper;
	private static final String[] dnyColumns = { DBUtils.COLUMN_ID, DBUtils.COLUMN_DEN };
	private static final String[] jidlaColumns = { DBUtils.COLUMN_JIDLO };

	private static final String WHERE_TYP = DBUtils.COLUMN_TYP + " like ?";
	private static final String WHERE_DEN_ID = DBUtils.COLUMN_ID_DEN + " = ?";
	
	public DaoDenImpl(Context context) {
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
	public void saveAll(List<IDenniJidelnicek> dny, JidelnicekTyp typ) {
		database.delete(DBUtils.TABLE_DNY, WHERE_TYP, new String[] { typ.toString() });

		for (IDenniJidelnicek den : dny) {
			insertDay(den, typ);
		}
	}

	private void insertDay(IDenniJidelnicek d, JidelnicekTyp typ) {
		ContentValues den = new ContentValues();
		den.put(DBUtils.COLUMN_DEN, d.getDen());
		den.put(DBUtils.COLUMN_TYP, d.getTyp().toString());
		
		long denID = database.insert(DBUtils.TABLE_DNY, null, den);
		
		if (denID >= 0) {
			ContentValues jidlo;
			for (String pokrm : d.getJidla()) {
				jidlo = new ContentValues();
				jidlo.put(DBUtils.COLUMN_JIDLO, pokrm);
				jidlo.put(DBUtils.COLUMN_ID_DEN, denID);
				database.insert(DBUtils.TABLE_JIDLA, null, jidlo);
			}
		}
	}

	@Override
	public List<IDenniJidelnicek> getAll(JidelnicekTyp typ) {
		List<IDenniJidelnicek> dny = new ArrayList<IDenniJidelnicek>();
		
		Cursor cursor = database.query(DBUtils.TABLE_DNY, dnyColumns, WHERE_TYP,
				new String[] { typ.toString() }, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			IDenniJidelnicek den = cursorToDen(cursor, typ);
			dny.add(den);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return dny;
	}

	private IDenniJidelnicek cursorToDen(Cursor c, JidelnicekTyp typ) {
		
		
		IDenniJidelnicek d;
		List<String> jidla = new ArrayList<String>();
		
		String denID = c.getString(0);
		
		Cursor cursor = database.query(DBUtils.TABLE_JIDLA, jidlaColumns, WHERE_DEN_ID,
				new String[] { denID }, null, null, DBUtils.COLUMN_ID);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			jidla.add(cursor.getString(0));
			cursor.moveToNext();
		}
		
		if (typ == JidelnicekTyp.FEI) {
			d = new DenniJidelnicekFeiImpl(
					c.getString(1), // den
					jidla
			);

			return d;
		} else {
			d = new DenniJidelnicekKampusImpl(
					c.getString(1), // den
					jidla
			);
		}
		
		return d;
	}

}
