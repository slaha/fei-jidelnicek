package cz.upce.fei.jidelak.model;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 12:29 */
public interface Saveable {

	/**
	 * @return string which represents this instance and which can be saved into
	 *         DB
	 */
	String getSaveableString();
}
