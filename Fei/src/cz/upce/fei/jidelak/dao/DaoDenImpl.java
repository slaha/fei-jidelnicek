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
	private String[] allColumns = { DBUtils.COLUMN_DEN, DBUtils.COLUMN_POLIVKA,
			DBUtils.COLUMN_JIDLO_1, DBUtils.COLUMN_JIDLO_2,
			DBUtils.COLUMN_JIDLO_3, DBUtils.COLUMN_BEZMASE,
			DBUtils.COLUMN_VECERE_1, DBUtils.COLUMN_VECERE_2 };

	private static final String WHERE = DBUtils.COLUMN_TYP + " like ?";
	
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
		database.delete(DBUtils.TABLE_JIDLA, WHERE, new String[] { typ.toString() });

		for (IDenniJidelnicek den : dny) {
			insertDay(den, typ);
		}
	}

	private void insertDay(IDenniJidelnicek d, JidelnicekTyp typ) {
		ContentValues values = new ContentValues();
		values.put(DBUtils.COLUMN_DEN, d.getDen());
		values.put(DBUtils.COLUMN_POLIVKA, d.getPolivka());
		values.put(DBUtils.COLUMN_JIDLO_1, d.getJidlo1());
		values.put(DBUtils.COLUMN_JIDLO_2, d.getJidlo2());
		values.put(DBUtils.COLUMN_JIDLO_3, d.getJidlo3());
		
		if (typ == JidelnicekTyp.KAMPUS) {
			values.put(DBUtils.COLUMN_BEZMASE, d.getBezmase());
			values.put(DBUtils.COLUMN_VECERE_1, d.getVecere1());
			values.put(DBUtils.COLUMN_VECERE_2, d.getVecere2());
		}
		
		values.put(DBUtils.COLUMN_TYP, typ.toString());
		
		database.insert(DBUtils.TABLE_JIDLA, null, values);
	}

	@Override
	public List<IDenniJidelnicek> getAll(JidelnicekTyp typ) {
		List<IDenniJidelnicek> dny = new ArrayList<IDenniJidelnicek>();
		Cursor cursor = database.query(DBUtils.TABLE_JIDLA, allColumns, WHERE,
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

	private IDenniJidelnicek cursorToDen(Cursor cursor, JidelnicekTyp typ) {
		IDenniJidelnicek d;
		if (typ == JidelnicekTyp.FEI) {
			d = new DenniJidelnicekFeiImpl(
					cursor.getString(0), // den
					cursor.getString(1), // polivka
					cursor.getString(2), // jidlo 1
					cursor.getString(3), // jidlo 2
					cursor.getString(4) // jidlo 3
			);

			return d;
		} else {
			d = new DenniJidelnicekKampusImpl(
					cursor.getString(0), // den
					cursor.getString(1), // polivka
					cursor.getString(2), // jidlo 1
					cursor.getString(3), // jidlo 2
					cursor.getString(4), // jidlo 3
					cursor.getString(5), // bezmase jidlo
					cursor.getString(6), // vecere 1
					cursor.getString(7) // vecere 2
			);
		}
		
		return d;
	}

}
