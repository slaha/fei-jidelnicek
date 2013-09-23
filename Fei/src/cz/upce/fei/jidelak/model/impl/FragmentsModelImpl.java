package cz.upce.fei.jidelak.model.impl;

import android.content.Context;
import cz.upce.fei.jidelak.controller.impl.PrefferenceManagerImpl;
import cz.upce.fei.jidelak.model.FragmentModel;
import cz.upce.fei.jidelak.model.MenuType;
import cz.upce.fei.jidelak.view.fragments.FeiJidelnicekFragmentImpl;
import cz.upce.fei.jidelak.view.fragments.IJidelnicekFragment;
import cz.upce.fei.jidelak.view.fragments.KampusJidelnicekFragmentImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/** Created with IntelliJ IDEA. User: slaha Date: 31.7.13 Time: 22:33 */
public class FragmentsModelImpl implements FragmentModel<IJidelnicekFragment> {

	private List<IJidelnicekFragment> fragments;
	private PrefferenceManagerImpl pm;

	public FragmentsModelImpl(Context context) {
		this.fragments = new ArrayList<IJidelnicekFragment>(2);
		this.pm = new PrefferenceManagerImpl(context);

	}

	@Override
	public void addFragment(IJidelnicekFragment fragment, String title) {
		MenuType typ = MenuType.fromTitle(title);
		int position = computePosition(typ);
		if (fragment == null) {
			fragment = getFragmentByTyp(typ);
		}
		add(position, fragment);
	}

	@Override
	public Iterator<IJidelnicekFragment> iterator() {
		return fragments.iterator();
	}

	public IJidelnicekFragment get(int position) {
		return fragments.get(position);

	}

	@Override
	public List<MenuType> getTypes() {
		return getFragmentsOrder();
	}

	public int count() {
		return fragments.size();
	}

	public int indexOf(Object object) {
		return fragments.indexOf(object);
	}

	public void add(int position, IJidelnicekFragment fragment) {
		fragments.add(position, fragment);
	}

	public boolean isReferenceEqual(IJidelnicekFragment fragment, MenuType typ) {

		return fragment == getFragmentByTyp(typ);

	}

	private IJidelnicekFragment getFragmentByTyp(MenuType typ) {
		for (IJidelnicekFragment menu : fragments) {
			if (menu.getTyp() == typ) {
				return menu;
			}
		}
		IJidelnicekFragment fragment = null;
		switch (typ) {

		case KAMPUS:
			fragment = new KampusJidelnicekFragmentImpl();
			break;
		case FEI:
			fragment = new FeiJidelnicekFragmentImpl();
			break;
		}
		if (fragment != null) {
			//fragments.add(fragment);
			return fragment;
		}

		throw new IllegalStateException("type must be one of " + Arrays.toString(MenuType.values()));
	}

	private int computePosition(MenuType menuType) {

		int index = getFragmentsOrder().indexOf(menuType);
		if (index < 0) {
			throw new IllegalStateException("type must be one of " + Arrays.toString(MenuType.values()));
		}
		return index;
	}

	public void recreate() {

		fragments.clear();

		List<MenuType> orderedTypes = getFragmentsOrder();
		for (MenuType type : orderedTypes) {
			IJidelnicekFragment fragment = getFragmentByTyp(type);
			fragments.add(fragment);
			fragment.updateJidelnicek();
		}
	}

	private List<MenuType> getFragmentsOrder() {
		List<MenuType> orderedMenuTypes = new ArrayList<MenuType>();
		orderedMenuTypes.add(MenuType.KAMPUS);

		if (pm.isDownloadFeiEnabled()) {
			if (pm.isDefaultScreen(MenuType.FEI)) {
				orderedMenuTypes.add(0, MenuType.FEI);
			} else {
				orderedMenuTypes.add(MenuType.FEI);
			}
		}

		return orderedMenuTypes;
	}

	public IJidelnicekFragment get(MenuType typ) {
		return getFragmentByTyp(typ);
	}
}
