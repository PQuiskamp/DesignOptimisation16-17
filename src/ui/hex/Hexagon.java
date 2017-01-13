package ui.hex;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.HashMap;

import Data.Knoten;
import Data.Resourcenfeld;
import Data.Const.KnotenName;
import Data.Const.KnotenName.*;

public class Hexagon extends Polygon {

	private static final long serialVersionUID = 1598655470833370302L;

	private HashMap<KnotenName, Point> edgeList;
	private Resourcenfeld feld;

	public Hexagon(int[] cx, int[] cy, int nPoints, Resourcenfeld feld) {
		super(cx, cy, nPoints);

		this.feld = feld;

		edgeList = new HashMap<>();
		edgeList.put(KnotenName.TopLeft, new Point(cx[0], cy[0]));
		edgeList.put(KnotenName.BottomLeft, new Point(cx[1], cy[1]));
		edgeList.put(KnotenName.BottomMid, new Point(cx[2], cy[2]));
		edgeList.put(KnotenName.BottomRight, new Point(cx[3], cy[3]));
		edgeList.put(KnotenName.TopRight, new Point(cx[4], cy[4]));
		edgeList.put(KnotenName.TopMid, new Point(cx[5], cy[5]));
	}

	public Point getKnoten(KnotenName name) {
		return edgeList.get(name);
	}

	public Resourcenfeld getFeld() {
		return feld;
	}
}
