package cz.upce.fei.jidelak.model;

import java.util.List;

/** Created with IntelliJ IDEA. User: slaha Date: 12.9.13 Time: 22:58 */
public interface FragmentModel<T> extends Iterable<T> {

	void addFragment(T fragment, String title);

	void recreate();

	boolean isReferenceEqual(T currentFragment, MenuType typ);

	T get(MenuType typ);

	int count();

	int indexOf(Object object);

	T get(int position);

	List<MenuType> getTypes();
}
