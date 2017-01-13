package ui.hex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;

import Data.Board;
import Data.Knoten;
import Data.ResourceCompat;
import Data.Resourcenfeld;
import Data.Const.Resource;
import log.Log;
import ui.component.BoardDisplayer;

public class HexMaker {

	public static final String FONT_NAME = "Courier new";
	public boolean XYVertex = true;
	private int offset;
	private int baseFontsize;

	private int s = 0; // length of one side
	private int t = 0; // short side of 30o triangle outside of each hex
	private int r = 0; // radius of inscribed circle (centre to middle of each
						// side). r= h/2
	private int h = 0; // height. Distance between centres of two adjacent
						// hexes. Distance between two opposite sides in a hex.
	private Board board;

	public HexMaker(int width, int height, int offset, Board board) {
		this.board = board;

		setSide(width);
		setHeight(height);
		this.offset = offset;
		baseFontsize = (int) (s * 0.7);
	}

	public void setXYasVertex(boolean b) {
		XYVertex = b;
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

	public Hexagon hex(int x0, int y0, Resourcenfeld feld) {
		int y = y0 + offset;
		int x = x0 + offset;
		// double mod = Math.sqrt(Math.pow(x, (y + h * 0.25))+Math.pow((x + h *
		// 0.5), y));
		double mod = t * 2 / 3;

		int[] cx, cy;

		// if (XYVertex)
		// cx = new int[] { x, x + s, x + s + t, x + s, x, x - t };
		// else
		// cx = new int[] { x + t, x + s + t, x + s + t + t, x + s + t, x + t, x
		// };
		// cy = new int[] { y, y, y + r, y + r + r, y + r + r, y + r };

		cx = new int[] { x, x, (int) (x + h * 0.5), x + h, x + h, (int) (x + h * 0.5) };
		// cy = new int[] { (int) (y +h * 0.25), (int) (y + h * 0.75), y+h,
		// (int) (y + h * 0.75), (int) (y + h * 0.25),y}; <- this is the default
		// one
		cy = new int[] { (int) (y + h * 0.25), (int) (y + (h) * 0.75 + mod), (int) (y + h + mod),
				(int) (y + (h) * 0.75 + mod), (int) (y + h * 0.25), y };
		// cy = new int[] { (int) (y + (h) * 0.25), (int) (y + (h) * 0.75),
		// y+h+t, (int) (y + (h) * 0.75), (int) (y + (h) * 0.25), y-t };

		Hexagon hexagon = new Hexagon(cx, cy, 6, feld);
		return hexagon;
	}

	@Deprecated
	public void drawHex(int i, int j, Graphics2D g2) {
		int x = i * (s + t);
		int y = j * h + (i % 2) * h / 2;
		Hexagon hex = hex(x, y, null);
		g2.setColor(BoardDisplayer.COLOURCELL);
		// g2.fillPolygon(hexmech.hex(x,y));
		g2.fillPolygon(hex);
		g2.setColor(BoardDisplayer.COLOURGRID);
		g2.drawPolygon(hex);
	}

	public Hexagon fillHex(int i, int j, Graphics2D g2) {
		// int x = i * (s + t);
		// int y = j * h + (i % 2) * h / 2;
		// int x = (int) ((double)i *3/2 * (double)s);
		// int y = (int) ((double)s * Math.sqrt(3) *
		// ((double)j+0.5*(double)(i%2)));

		int x = (int) ((double) s * Math.sqrt(3) * ((double) i + (double) j / 2));
		int y = (int) ((double) s * 3 / 2 * (double) j);

		Resourcenfeld feld = board.getResourceAt(i, j);
		Hexagon hex = hex(x, y, feld);
		Resource resource = feld.getRes();
		g2.setColor(ResourceCompat.getColor(resource));
		g2.fillPolygon(hex);

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

		// Draw filled hexagons
		int würfelvalue = feld.getDiceValue();
		if (würfelvalue != -1) {
			baseFontsize = (int) (s * 0.7);
			Font textFont = new Font(FONT_NAME, Font.PLAIN, baseFontsize);

			g2.setColor(Color.BLACK);
			g2.setFont(textFont);
			g2.drawString(String.valueOf(würfelvalue), x + r + offset - baseFontsize / 3, y + r + offset + 4);

			String percentage = feld.getProbability() + "%";
			g2.setFont(new Font(FONT_NAME, Font.PLAIN, baseFontsize / 2));
			g2.drawString(percentage, x + r + offset - baseFontsize / 3, y + r + offset + 4 + baseFontsize / 2);
		}

		// Draw Grid
		g2.setColor(Color.BLACK);
		g2.drawPolygon(hex);

		return hex;
	}

	public int getBaseFontSize() {
		return baseFontsize;
	}

	public Point pxtoHex(int mx, int my) {
		Point p = new Point(-1, -1);

		mx -= offset;
		my -= offset;
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
