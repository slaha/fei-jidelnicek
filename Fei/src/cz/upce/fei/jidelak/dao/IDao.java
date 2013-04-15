package cz.upce.fei.jidelak.dao;

import android.database.SQLException;
import cz.upce.fei.jidelak.model.JidelnicekTyp;

public interface IDao {

	String getJidelnicek(JidelnicekTyp typ);
	void save(String jidelnicek, JidelnicekTyp typ);

	void open() throws SQLException;
	void close();
}
