package ui.hex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import Data.Board;
import Data.ResourceCompat;
import Data.Resourcenfeld;
import Data.Const.Resource;
import ui.component.BoardDisplayer;

public class HexMaker {

	public boolean XYVertex = true;
	private int borders = 50; // default number of pixels for the border.

	private int s = 0; // length of one side
	private int t = 0; // short side of 30o triangle outside of each hex
	private int r = 0; // radius of inscribed circle (centre to middle of each
						// side). r= h/2
	private int h = 0; // height. Distance between centres of two adjacent
						// hexes. Distance between two opposite sides in a hex.
	private Board board;

	public HexMaker(int width, int height, Board board) {
		this.board = board;

		setSide(width);
		setHeight(height);
	}

	public void setXYasVertex(boolean b) {
		XYVertex = b;
	}

	public void setBorders(int b) {
		borders = b;
	}

	public void setSide(int side) {
		s = side;
		t = (int) (s / 2); // t = s sin(30) = (int) CalculateH(s);
		r = (int) (s * 0.8660254037844); // r = s cos(30) = (int) CalculateR(s);
		h = 2 * r;
	}

	public void setHeight(int height) {
		h = height; // h = basic dimension: height (distance between two adj
					// centresr aka size)
		r = h / 2; // r = radius of inscribed circle
		s = (int) (h / 1.73205); // s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h /
									// sqrt(3)
		t = (int) (r / 1.73205); // t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2
									// sqrt(3)) = r / sqrt(3)
	}

	public Polygon hex(int x0, int y0) {

		int y = y0 + borders;
		int x = x0 + borders;
		if (s == 0 || h == 0) {
			System.out.println("ERROR: size of hex has not been set");
			return new Polygon();
		}

		int[] cx, cy;

		if (XYVertex)
			cx = new int[] { x, x + s, x + s + t, x + s, x, x - t };
		else
			cx = new int[] { x + t, x + s + t, x + s + t + t, x + s + t, x + t, x };

		cy = new int[] { y, y, y + r, y + r + r, y + r + r, y + r };
		return new Polygon(cx, cy, 6);
	}

	public void drawHex(int i, int j, Graphics2D g2) {
		int x = i * (s + t);
		int y = j * h + (i % 2) * h / 2;
		Polygon poly = hex(x, y);
		g2.setColor(BoardDisplayer.COLOURCELL);
		// g2.fillPolygon(hexmech.hex(x,y));
		g2.fillPolygon(poly);
		g2.setColor(BoardDisplayer.COLOURGRID);
		g2.drawPolygon(poly);
	}

	public void fillHex(int i, int j, Graphics2D g2) {
		char c = 'o';
		int x = i * (s + t);
		int y = j * h + (i % 2) * h / 2;

		Resourcenfeld feld = board.getResourceAt(i, j);
		g2.setColor(ResourceCompat.getColor(feld.getRes()));
		g2.fillPolygon(hex(x, y));

		// if (n < 0) {
		// g2.setColor(BoardDisplayer.COLOURONE);
		// g2.fillPolygon(hex(x, y));
		// g2.setColor(BoardDisplayer.COLOURONETXT);
		// c = (char) (-n);
		// g2.drawString("" + c, x + r + borders, y + r + borders + 4);
		// }
		// if (n > 0) {
		// g2.setColor(BoardDisplayer.COLOURTWO);
		// g2.fillPolygon(hex(x, y));
		// g2.setColor(BoardDisplayer.COLOURTWOTXT);
		// c = (char) n;
		// g2.drawString("" + c, x + r + borders, y + r + borders + 4);
		// }
	}

	public Point pxtoHex(int mx, int my) {
		Point p = new Point(-1, -1);

		mx -= borders;
		my -= borders;
		if (XYVertex)
			mx += t;

		int x = (int) (mx / (s + t));
		int y = (int) ((my - (x % 2) * r) / h);

		int dx = mx - x * (s + t);
		int dy = my - y * h;

		if (my - (x % 2) * r < 0)
			return p; // prevent clicking in the open halfhexes at the top of
						// the screen

		// even columns
		if (x % 2 == 0) {
			if (dy > r) { // bottom half of hexes
				if (dx * r / t < dy - r) {
					x--;
				}
			}
			if (dy < r) { // top half of hexes
				if ((t - dx) * r / t > dy) {
					x--;
					y--;
				}
			}
		} else { // odd columns
			if (dy > h) { // bottom half of hexes
				if (dx * r / t < dy - h) {
					x--;
					y++;
				}
			}
			if (dy < h) { // top half of hexes
				// System.out.println("" + (t- dx)*r/t + " " + (dy - r));
				if ((t - dx) * r / t > dy - r) {
					x--;
				}
			}
		}
		p.x = x;
		p.y = y;
		return p;
	}
}
