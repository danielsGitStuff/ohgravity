package tools;


public class OTools {

	public static String getSpaces(int level) {
		StringBuilder spaces = new StringBuilder();
		for (int i = 0; i < level; i++) {
			spaces.append("  ");
		}
		return spaces.toString();
	}
}
