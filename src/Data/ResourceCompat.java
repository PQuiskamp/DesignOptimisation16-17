package Data;

import java.awt.Color;

import Data.Const.Resource;

public abstract class ResourceCompat {

	private static final Color SAND = new Color(181, 181, 18);
	private static final Color WOOD = new Color(20,130,20);
	private static final Color ORE = new Color(188, 209, 189);
	private static final Color SHEEP = new Color(8, 255, 17);
	private static final Color WHEAT = new Color(255, 255, 0);
	private static final Color CLAY = new Color(196, 121, 0);

	public static Color getColor(Resource resource) {
		switch (resource) {
		case Wasser:
			return Color.BLUE;
		case Holz:
			return WOOD;
		case Erz:
			return ORE;
		case Lehm:
			return CLAY;
		case Schaf:
			return SHEEP;
		case Wüste:
			return SAND;
		case Weizen:
			return WHEAT;
		case Void :
			return Color.BLACK;
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
