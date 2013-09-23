package cz.upce.fei.jidelak.parser.impl.kampus;

/** Created with IntelliJ IDEA. User: slaha Date: 21.9.13 Time: 22:18 */
public class Food {
	private String flag, name, weight;

	public void setValue(String value) {
		if (flag == null) {
			flag = value;
		} else if (weight == null) {
			weight = value;
		} else if (name == null) {
			name = value;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (flag != null) {
			sb.append(flag).append(" ");
		}
		if (weight != null) {
			sb.append(weight).append(" ");
		}
		if (name != null) {
			sb.append(name);
		}

		return sb.toString();
	}

	public boolean isEmpty() {
		return flag == null && name == null && weight == null;
	}
}
