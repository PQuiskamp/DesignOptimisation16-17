package ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JPanel;

import Data.Board;
import Data.Const.KnotenName;
import Data.Const.Resource;
import Data.Knoten;
import Data.Resourcenfeld;
import game.Game;
import game.Player;
import log.Log;
import ui.hex.HexMaker;
import ui.hex.Hexagon;

public class BoardDisplayer extends JPanel {

	private static final long serialVersionUID = 3755425870029209163L;

	public final static Color COLORBESTSCORE = new Color(218, 35, 255);
	public final static Color COLOURCELL = Color.ORANGE;
	public final static Color COLOURGRID = Color.BLACK;
	public final static Color COLOURONE = new Color(255, 255, 255, 200);
	public final static Color COLOURONETXT = Color.BLUE;
	public final static Color COLOURTWO = new Color(0, 0, 0, 200);
	public final static Color COLOURTWOTXT = new Color(255, 100, 255);
	final static int EMPTY = 0;
	final static int HEXSIZE = 60; // hex size in pixels
	final static int BORDERS = 15;

	private HexMaker maker;
	private Game game;

	public BoardDisplayer(Game game) {
		this.game = game;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (maker == null) {
					return;
				}

				// int x = e.getX();
				// int y = e.getY();
				// mPt.x = x;
				// mPt.y = y;
				// Point p = new Point(maker.pxtoHex(e.getX(), e.getY()));
				// if (p.x < 0 || p.y < 0 || p.x >= board.getWidth() || p.y >=
				// board.getHeight())
				// return;

				// DEBUG: colour in the hex which is supposedly the one clicked
				// on
				// clear the whole screen first.
				/*
				 * for (int i=0;i<BSIZE;i++) { for (int j=0;j<BSIZE;j++) {
				 * board[i][j]=EMPTY; } }
				 */

				// What do you want to do when a hexagon is clicked?
				// Log.log("Clicked on (" + p.x + "," + p.y + ")! Field
				// description: "+
				// ResourceCompat.getName(board.getResourceAt(p.x,
				// p.y).getRes()));
				// board[p.x][p.y] = (int) 'X';

				Point p = e.getPoint();
				Log.log("Clicked: (" + p.x + ", " + p.y + ")");
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		if (game == null) {
			return;
		}
		Board board = game.getBoard();
		if (board == null) {
			return;
		}
		int boardWidth = board.getWidth();
		int boardHeight = board.getHeight();
		maker = new HexMaker(width / (boardWidth + 1), height / (boardHeight + 1), -70, 50, board);

		// Color helloWorldColor = getRandomColor();
		// g.setColor(helloWorldColor);
		// g.drawString("Hello World", width / 2, height / 2);
		//
		// Log.log("Test log: Drawing 'Hello World' in " +helloWorldColor);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

		// draw hexes
		ArrayList<Hexagon> hexes = new ArrayList<>();
		for (int j = 0; j < boardHeight; j++) {
			for (int i = 0; i < boardWidth; i++) {
				// if (board[i][j] <
				// 0)maker.fillHex(i,j,COLOURONE,-board[i][j],g2);
				// if (board[i][j] > 0)
				// hexmech.fillHex(i,j,COLOURTWO,board[i][j],g2);
				Hexagon hex = maker.fillHex(i, j, g2);
				hexes.add(hex);
			}
		}

		HashMap<Knoten, Point> pointMap = new HashMap<>();
		for (Hexagon hex : hexes) {
			Resourcenfeld feld = hex.getFeld();
			Resource resource = feld.getRes();

			if (resource == Resource.Wüste || resource == Resource.Wasser || resource == Resource.Void) {
				continue;
			}

			// Draw nodes
			g2.setColor(Color.BLACK);
			for (KnotenName name : KnotenName.values()) {
				Knoten k = feld.getKnoten(name);
				Point p = hex.getKnoten(name);

				pointMap.put(k, p);
			}
		}

		int fontSize = maker.getBaseFontSize();
		// Draw Knoten
		for (Knoten k : pointMap.keySet()) {
			if (k == null) {
				continue;
			}

			Point p = pointMap.get(k);

			int size = (int) (fontSize * 0.75);
			Player owner = k.getOwner();
			if (owner == null) {
				double score = k.getScore();
				Color scoreColor = Color.BLACK;

				if(k.isClaimable()){
					double normalizedScore = normalize(score);
					int red = COLORBESTSCORE.getRed();
					int green = COLORBESTSCORE.getGreen();
					int blue = COLORBESTSCORE.getBlue();

					scoreColor = new Color((int) (red * normalizedScore), (int) (green * normalizedScore),
							(int) (blue * normalizedScore));
				}

				g2.setColor(scoreColor);
				g2.fillOval(p.x - size / 2, p.y - size / 2, size, size);

				if (k.isClaimable()) {
					g2.setColor(Color.WHITE);

					g2.drawString(String.format("%.01f", score), p.x - size / 4, p.y + size / 4);
					// g2.drawString(k.getAllField()[0]., p.x - size / 4, p.y +
					// size / 4);
				}
			} else {
				g2.setColor(Color.BLACK);
				g2.fillRect(p.x - size / 2, p.y - size / 2, size, size);

				g2.setColor(owner.getColor());
				g2.fillRect(p.x - size / 2 + 5, p.y - size / 2 + 5, size - 10, size - 10);
			}
		}

		// Draw players
		Point textOrigin = new Point(width - fontSize * 6, 5 + fontSize);
		g2.setFont(new Font(HexMaker.FONT_NAME, Font.PLAIN, fontSize));
		g2.setColor(Color.WHITE);
		g2.drawString(" Round: " + game.getRound(), textOrigin.x, textOrigin.y);

		for (int i = 0; i < game.getPlayerCount(); i++) {
			Player p = game.getPlayer(i);
			g2.setColor(p.getColor());

			String turnTag = " ";
			if (game.getCurrentPlayer() == p) {
				turnTag = ">";
			}

			String playername = turnTag + "Player " + (i + 1);
			g2.drawString(playername, textOrigin.x, textOrigin.y + fontSize * (i + 1));
		}
	}

	private double normalize(double score) {
		ArrayList<Knoten> knoten = new ArrayList<>(game.getBoard().getKnotenListe());
		Collections.sort(knoten);
		double max = knoten.get(knoten.size() - 1).getScore();
		double min = knoten.get(0).getScore();

		return 1 - (score - min) / (max - min);
	}

}
