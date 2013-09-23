package cz.upce.fei.jidelak.model;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-14
 * Time: 8:59
 */
public enum MenuType {
	KAMPUS,
	FEI;

	public static MenuType fromTitle(String title) {

		for (int i = 0; i < values().length; i++) {
			String name = values()[i].name();
			if (name.equalsIgnoreCase(title)) {
				return values()[i];
			}
		}

		throw new IllegalArgumentException("Cannot get MenuType from title " + title + ". Title should be one from "
				+ values());
	}
}
