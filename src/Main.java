import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Data.Board;
import Factory.BoardFactory;
import ui.MainWindow;

public class Main {

	private MainWindow mainWindow;
	private Board board;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		board = BoardFactory.creatBoard();

		mainWindow = new MainWindow(board);
	}

}
