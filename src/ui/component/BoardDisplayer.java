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
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Data.Board;
import Data.Const.Resource;
import Data.ResourceCompat;
import log.Log;
import ui.hex.HexMaker;

public class BoardDisplayer extends JPanel {

	private static final long serialVersionUID = 3755425870029209163L;

	private Board board;
	private ArrayList<Color> randomColorList;

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

	public BoardDisplayer(Board board) {
		this.board = board;

		randomColorList = new ArrayList<>();
		randomColorList.add(Color.RED);
		randomColorList.add(Color.BLUE);
		randomColorList.add(Color.GREEN);
		randomColorList.add(Color.YELLOW);
		randomColorList.add(Color.ORANGE);
		randomColorList.add(Color.CYAN);
		randomColorList.add(Color.PINK);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (maker == null) {
					return;
				}

				int x = e.getX();
				int y = e.getY();
				// mPt.x = x;
				// mPt.y = y;
				Point p = new Point(maker.pxtoHex(e.getX(), e.getY()));
				if (p.x < 0 || p.y < 0 || p.x >= board.getWidth() || p.y >= board.getHeight())
					return;

				// DEBUG: colour in the hex which is supposedly the one clicked
				// on
				// clear the whole screen first.
				/*
				 * for (int i=0;i<BSIZE;i++) { for (int j=0;j<BSIZE;j++) {
				 * board[i][j]=EMPTY; } }
				 */

				// What do you want to do when a hexagon is clicked?
				Log.log("Clicked on (" + p.x + "," + p.y + ")! Field description: "
						+ ResourceCompat.getName(board.getResourceAt(p.x, p.y).getRes()));
				// board[p.x][p.y] = (int) 'X';
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		// Color black = Color.BLACK;
		//
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		if (board == null) {
			return;
		}
		int boardWidth = board.getWidth();
		int boardHeight = board.getHeight();
		maker = new HexMaker(width / (boardWidth + 1), height / (boardHeight + 1), board);

		// Color helloWorldColor = getRandomColor();
		// g.setColor(helloWorldColor);
		// g.drawString("Hello World", width / 2, height / 2);
		//
		// Log.log("Test log: Drawing 'Hello World' in " +helloWorldColor);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

		// draw grid
		for (int j = 0; j < boardHeight; j++) {
			for (int i = 0; i < boardWidth; i++) {
				maker.drawHex(i, j, g2);
			}
		}
		// fill in hexes
		for (int j = 0; j < boardHeight; j++) {
			for (int i = 0; i < boardWidth; i++) {
				// if (board[i][j] <
				// 0)maker.fillHex(i,j,COLOURONE,-board[i][j],g2);
				// if (board[i][j] > 0)
				// hexmech.fillHex(i,j,COLOURTWO,board[i][j],g2);
				maker.fillHex(i, j, g2);
			}
		}
	}

	private Color getRandomColor() {
		return randomColorList.get(new Random().nextInt(randomColorList.size()));
	}

}
