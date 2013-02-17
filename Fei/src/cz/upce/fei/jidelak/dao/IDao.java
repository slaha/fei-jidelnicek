package cz.upce.fei.jidelak.dao;

import java.util.List;

import cz.upce.fei.jidelak.model.JidelnicekTyp;


import android.database.SQLException;

public interface IDao<T> {

	List<T> getAll(JidelnicekTyp typ);
	void saveAll(List<T> ts, JidelnicekTyp typ);
	void open() throws SQLException; 
	void close();
}
