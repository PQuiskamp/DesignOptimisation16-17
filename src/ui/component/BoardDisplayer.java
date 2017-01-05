package ui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;

import Data.Board;
import log.Log;

public class BoardDisplayer extends JLabel {

	private static final long serialVersionUID = 3755425870029209163L;

	private Board board;
	private ArrayList<Color> randomColorList;

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
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		Color black = Color.BLACK;

		g.setColor(black);
		g.fillRect(0, 0, width, height);

		Color helloWorldColor = getRandomColor();
		g.setColor(helloWorldColor);
		g.drawString("Hello World", width / 2, height / 2);
		
		Log.log("Test log: Drawing 'Hello World' in " +helloWorldColor);
	}

	private Color getRandomColor() {
		return randomColorList.get(new Random().nextInt(randomColorList.size()));
	}

}
