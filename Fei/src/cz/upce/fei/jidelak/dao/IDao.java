package cz.upce.fei.jidelak.dao;

import android.database.SQLException;
import cz.upce.fei.jidelak.model.Saveable;

/**
 * T is type of what to save or get
 * Key is type of the key to find the right object (typically it will be an ID).
 */
public interface IDao<T extends Saveable, Key> {

	/**
	 * Get T which is identified by {@code key}
	 * 
	 * @param key
	 *            identificator of fetched object
	 * @return object identified by {@code key} or null if there is no object
	 *         identified by {@code key}
	 */
	T get(Key key) throws SQLException;

	/**
	 * Save {@code saveable} with identificator {@code key}
	 * 
	 * @param saveable
	 *            object to save
	 * @key identificator
	 */
	void save(T saveable, Key key) throws SQLException;

	/**
	 * Get SaveableBundle of objects identified by keys
	 * 
	 * @param keys
	 *            identificators
	 * @return SaveableBundle of objects. It will never be null but objects
	 *         inside it can be.
	 */
	SaveableBundle<T, Key> getMultiple(Key... keys) throws SQLException;
}
