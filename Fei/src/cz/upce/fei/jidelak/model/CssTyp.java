package cz.upce.fei.jidelak.model;

/**
 * Created with IntelliJ IDEA.
 * User: slaha
 * Date: 2013-04-15
 * Time: 20:11
 */
public enum CssTyp {

	BLACK_ON_WHITE("black-on-white.css"),
	WHITE_ON_BLACK("white-on-black.css");

	private  String CSS;

	private CssTyp(String css) {
		this.CSS = css;
	}

	public String getCss() {
		return this.CSS;
	}
}
