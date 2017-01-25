package ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import Factory.BoardFactory;
import game.Main;
import game.Game;
import log.Log;
import ui.component.BoardDisplayer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Random;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -3124347100103349080L;
	private Game game;
	private BoardDisplayer boardDisplayer;
	public static final int MINIMUM_HEIGHT = 900;
	private JScrollPane logPane;
	private GridBagLayout gridBagLayout;
	private JTextArea log;
	private String seedSource;

	public MainWindow(Game game) {
		this.game = game;

		Main.addWindow(this);
		setTitle("Design Optimisation WS 16/17 - Die Siedler von Catan");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				Main.removeWindow(e.getWindow());
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.removeWindow(SwingUtilities.getWindowAncestor((Component) arg0.getSource()));
				dispose();
			}
		});

		JMenuItem mntmNewDefaultGame = new JMenuItem("New default game");
		mntmNewDefaultGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mntmNewDefaultGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new MainWindow(Game.defaultGame());
			}
		});
		mnFile.add(mntmNewDefaultGame);

		JMenuItem mntmNewCustomGame = new JMenuItem("New custom game");
		mntmNewCustomGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GameSetupFrame();
			}
		});

		JMenuItem mntmNewRandomGame = new JMenuItem("New Random game");
		mntmNewRandomGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seed = String.valueOf(new Date().getTime());
				Game game = new Game(BoardFactory.creatBoard(new Random(seed.hashCode())),
						(int) (new Random().nextGaussian() * 0.8 + (Game.DEFAULT_PLAYER_COUNT)));
				MainWindow window = new MainWindow(game);
				window.setSeedSource(seed);
			}
		});
		mntmNewRandomGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmNewRandomGame);
		mntmNewCustomGame
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmNewCustomGame);
		mnFile.addSeparator();
		mnFile.add(mntmClose);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setMnemonic('E');
		menuBar.add(mnEdit);

		JMenuItem mntmClearLog = new JMenuItem("Clear log");
		mntmClearLog
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mntmClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Log.clear();
			}
		});
		mnEdit.add(mntmClearLog);

		JMenu mnAction = new JMenu("Actions");
		mnAction.setMnemonic('A');
		menuBar.add(mnAction);

		JMenuItem mntmExecuteTurn = new JMenuItem("Execute Turn");
		mntmExecuteTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeTurn();
			}
		});
		mntmExecuteTurn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mnAction.add(mntmExecuteTurn);

		JMenuItem mntmFinishRound = new JMenuItem("Finish Round");
		mntmFinishRound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishRound();
			}
		});
		mntmFinishRound.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mnAction.add(mntmFinishRound);

		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 200, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		getContentPane().add(splitPane, gbc_splitPane);

		boardDisplayer = new BoardDisplayer(game);
		splitPane.setLeftComponent(boardDisplayer);

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 95, 0, 0 };
		gbl_panel.rowHeights = new int[] { 79, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JButton btnPlaceNext = new JButton("Execute Turn");
		btnPlaceNext.setMnemonic('X');
		btnPlaceNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				executeTurn();
			}
		});
		panel_1.add(btnPlaceNext);

		Component verticalStrut_1 = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut_1);

		JButton btnNewButton_1 = new JButton("Finish Round");
		btnNewButton_1.setMnemonic('I');
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishRound();
			}
		});
		panel_1.add(btnNewButton_1);

		Component verticalStrut = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut);

		JButton btnNewButton_2 = new JButton("Get seed");
		btnNewButton_2.setMnemonic('G');
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (seedSource == null) {
					Log.log("No seed found.");
				} else {
					Log.log("Seed: " + seedSource);
				}
			}
		});
		panel_1.add(btnNewButton_2);

		Component verticalStrut_2 = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut_2);

		JButton btnNewButton = new JButton("Clar Log");
		btnNewButton.setMnemonic('C');
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Log.clear();
			}
		});

		JButton btnCopySeed = new JButton("Copy seed");
		btnCopySeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seedSource != null) {
					try {
						StringSelection stringSelection = new StringSelection(seedSource);
						Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
						clpbrd.setContents(stringSelection, null);
						Log.log("Copied seed " + seedSource + " to the clipboard!");
					} catch (Exception ex) {
						Log.log("Failed to copy the seed to the clipboard. Reason: " + ex.getClass().getSimpleName());
						ex.printStackTrace();
					}
				}
			}
		});
		panel_1.add(btnCopySeed);

		Component verticalStrut_3 = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut_3);
		panel_1.add(btnNewButton);

		logPane = new JScrollPane();
		GridBagConstraints gbc_logPane = new GridBagConstraints();
		gbc_logPane.fill = GridBagConstraints.BOTH;
		gbc_logPane.gridx = 1;
		gbc_logPane.gridy = 0;
		panel.add(logPane, gbc_logPane);
		logPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		splitPane.setResizeWeight(0.7);

		log = new JTextArea();
		logPane.setViewportView(log);
		log.setText("test");
		log.setEditable(false);
		Log.addOutputArea(log, logPane.getVerticalScrollBar());
		printSystemLogs();

		setSize(MINIMUM_HEIGHT, MINIMUM_HEIGHT * 9 / 16);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);

		setVisible(true);
	}

	private void finishRound() {
		game.finishRound();
		repaint();
	}

	private void executeTurn() {
		game.nextTurn();
		repaint();
	}

	public String getSeedSource() {
		return seedSource;
	}

	public void setSeedSource(String seedSource) {
		this.seedSource = seedSource;
	}

	private void printSystemLogs() {
		Log.log("JVM Running information: Java Vendor: " + System.getProperty("java.vendor"));
		Log.log("JVM Running information: Java Version: " + System.getProperty("java.version"));
		Log.log("JVM Running information: Java Vendor URL: " + System.getProperty("java.vendor.url"));
		Log.log("JVM Running information: Java installed in: " + System.getProperty("java.home"));
		Log.log("JVM Running information: Java VM Name: " + System.getProperty("java.vm.name"));
		Log.log("JVM Running information: Java VM Vendor: " + System.getProperty("java.vm.vendor"));
		Log.log("JVM Running information: Java VM Version: " + System.getProperty("java.vm.version"));
		Log.log("JVM Running information: Java Specification Name: " + System.getProperty("java.specification.name"));
		Log.log("JVM Running information: Java Specification Vendor: "
				+ System.getProperty("java.specification.vendor"));
		Log.log("JVM Running information: Java Specification Version: "
				+ System.getProperty("java.specification.version"));
		Log.log("JVM Running information: Java VM Specification Name: "
				+ System.getProperty("java.vm.specification.name"));
		Log.log("JVM Running information: Java VM Specification Vendor: "
				+ System.getProperty("java.vm.specification.vendor"));
		Log.log("JVM Running information: Java VM Specification Version: "
				+ System.getProperty("java.vm.specification.version"));
	}

}
