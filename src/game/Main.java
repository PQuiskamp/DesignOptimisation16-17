package game;

import java.awt.Window;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import game.Game;
import log.Log;
import ui.MainWindow;

public class Main {

	private static ArrayList<Window> windows;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}

				windows = new ArrayList<>();
				new MainWindow(Game.defaultGame());
			}
		});
	}

	public static void addWindow(Window window) {
		Log.log("Added a window.");
		windows.add(window);
	}

	public static void removeWindow(Window window) {
		windows.remove(window);
		Log.log("Removed a winodw. Remaining: " + windows.size());

		if (windows.isEmpty()) {
			Log.log("No more open windows. Exiting.");
			System.exit(0);
		}
	}

}
