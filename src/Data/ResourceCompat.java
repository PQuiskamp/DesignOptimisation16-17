package Data;

import java.awt.Color;

import Data.Const.Resource;

public abstract class ResourceCompat {

	private static final Color BROWN = new Color(142, 87, 19);
	private static final Color SAND = new Color(193, 193, 0);

	public static Color getColor(Resource resource) {
		switch (resource) {
		case Wasser:
			return Color.BLUE;
		case Holz:
			return BROWN;
		case Erz:
			return Color.GRAY;
		case Lehm:
			return Color.ORANGE;
		case Schaf:
			return Color.GREEN;
		case Wüste:
			return SAND;
		case Weizen:
			return Color.YELLOW;

		default:
			return Color.WHITE;
		}
	}
	
	public static String getName(Resource resource) {
		switch (resource) {
		case Wasser:
			return "Wasser";
		case Holz:
			return "Holz";
		case Erz:
			return "Erz";
		case Lehm:
			return "Lehm";
		case Schaf:
			return "Wolle";
		case Wüste:
			return "Wüste";
		case Weizen:
			return "Weizen";

		default:
			return "?";
		}
	}
}
