package cz.upce.fei.jidelak.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 20:06 */
public class SaveableBundle<T, Key> implements Iterable<SaveableBundle<T, Key>.Bundle> {

	public class Bundle {

		private final T value;
		private final Key key;

		private Bundle(T value, Key key) {
			this.value = value;
			this.key = key;
		}

		public Key getKey() {
			return key;
		}

		public T getValue() {
			return value;
		}
	}

	private final List<Bundle> values;

	SaveableBundle() {
		this.values = new ArrayList<Bundle>();
	}

	public void add(T value, Key key) {
		Bundle b = new Bundle(value, key);
		values.add(b);
	}

	@Override
	public Iterator<Bundle> iterator() {
		return values.iterator();
	}

}
