import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import game.Game;
import ui.MainWindow;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(Game.defaultGame());
			}
		});
	}

}
