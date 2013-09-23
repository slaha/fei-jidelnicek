package cz.upce.fei.jidelak.model;

import android.os.Parcel;
import android.os.Parcelable;
import cz.upce.fei.jidelak.utils.HtmlHelper;

/** Created with IntelliJ IDEA. User: slaha Date: 31.7.13 Time: 16:52 */
public class Menu implements Parcelable, Saveable {

	private String jidelnicekHtml;

	public Menu(String jidelnicekSource) {
		this.jidelnicekHtml = jidelnicekSource;
	}

	private Menu(Parcel parcel) {
		this.jidelnicekHtml = parcel.readString();
	}

	@Override
	public String getSaveableString() {
		return jidelnicekHtml;
	}

	public boolean isEmpty() {
		return this.jidelnicekHtml == null || this.jidelnicekHtml.isEmpty();
	}

	public String getStylledHtml(CssTyp css) {
		return HtmlHelper.surroundHtml(this.jidelnicekHtml, css);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(jidelnicekHtml);
	}

	public static final Creator<Menu> CREATOR = new Creator<Menu>() {
		@Override
		public Menu createFromParcel(Parcel parcel) {
			return new Menu(parcel);
		}

		@Override
		public Menu[] newArray(int i) {
			return new Menu[i];
		}
	};
}
