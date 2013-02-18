package cz.upce.fei.jidelak.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cz.upce.fei.jidelak.model.DenniJidelnicekFeiImpl;
import cz.upce.fei.jidelak.model.DenniJidelnicekKampusImpl;
import cz.upce.fei.jidelak.model.IDenniJidelnicek;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

public class DaoDenImpl implements IDao<IDenniJidelnicek> {

	// Database fields
	private SQLiteDatabase database;
	private DBUtils dbHelper;

	private static final String WHERE_TYP = DBUtils.COLUMN_TYP + " like ?";

	private static final String SQL_SELECT = 
				"select " 
				+ DBUtils.TABLE_DNY + "." + DBUtils.COLUMN_ID + ", " + DBUtils.COLUMN_DEN + ", " + DBUtils.COLUMN_JIDLO 
				+ " from " + DBUtils.TABLE_DNY
				+ " left join " + DBUtils.TABLE_JIDLA 
				+ " on " + DBUtils.TABLE_DNY + "." + DBUtils.COLUMN_ID + " = " + DBUtils.TABLE_JIDLA + "." + DBUtils.COLUMN_ID_DEN
				+ " where " + DBUtils.TABLE_DNY + "." + DBUtils.COLUMN_TYP + " like ? " 
				+ " order by " +  DBUtils.TABLE_DNY + "." + DBUtils.COLUMN_ID + ", "+  DBUtils.TABLE_JIDLA + "." + DBUtils.COLUMN_ID + ";";
	
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
		
		Cursor c = database.rawQuery(SQL_SELECT, new String[] { typ.toString() });
		
		c.moveToFirst();
		Map<Long, IDenniJidelnicek> mapa = getAllWeek(c, typ);
		c.close();
		
		return getAllWeek(mapa);
	}
	
	private Map<Long, IDenniJidelnicek> getAllWeek(Cursor c, JidelnicekTyp typ) 
	{
		Map<Long, IDenniJidelnicek> mapa = new LinkedHashMap<Long, IDenniJidelnicek>();
		while (!c.isAfterLast()) {
			long id = c.getLong(0);
			String den = c.getString(1);
			String jidlo = c.getString(2);

			IDenniJidelnicek denniJidelnicek = mapa.get(id);
			List<String> jidelnicek;
			
			if (denniJidelnicek == null) {
				jidelnicek = new ArrayList<String>();
				if (typ == JidelnicekTyp.FEI) {
					denniJidelnicek = new DenniJidelnicekFeiImpl(den, jidelnicek);
				} else {
					denniJidelnicek = new DenniJidelnicekKampusImpl(den, jidelnicek);
				}
				mapa.put(id, denniJidelnicek);
			} else {
				jidelnicek = denniJidelnicek.getJidla();
			}

			jidelnicek.add(jidlo);

			c.moveToNext();
		}
		return mapa;
	}
	
	private List<IDenniJidelnicek> getAllWeek(Map<Long, IDenniJidelnicek> mapa) {
		
		List<IDenniJidelnicek> week = new ArrayList<IDenniJidelnicek>();
		
		for (IDenniJidelnicek denniJidelnicek : mapa.values()) {
			week.add(denniJidelnicek);
		}
		
		return week;
	}
}
