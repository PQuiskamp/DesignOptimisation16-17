package ui;

import javax.swing.JFrame;

import Data.Board;
import log.Log;
import ui.component.BoardDisplayer;

import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -3124347100103349080L;
	private Board board;
	private BoardDisplayer boardDisplayer;
	public static final int MINIMUM_HEIGHT = 900;

	public MainWindow(Board board) {
		this.board = board;

		setTitle("Design Optimisation WS 16/17 - Die Siedler von Catan");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmClose);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmClearLog = new JMenuItem("Clear log");
		mntmClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Log.clear();
			}
		});
		mnEdit.add(mntmClearLog);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 176, 200, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		boardDisplayer = new BoardDisplayer(board);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(boardDisplayer, gbc_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		getContentPane().add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JButton button = new JButton("Do");
		panel_1.add(button);

		Component verticalStrut = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut);

		JButton btnNewButton = new JButton("Some");
		panel_1.add(btnNewButton);

		Component verticalStrut_1 = Box.createVerticalStrut(10);
		panel_1.add(verticalStrut_1);

		JButton btnStuff = new JButton("Stuff");
		panel_1.add(btnStuff);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		Log.setOutputArea(textArea);

		setSize(MINIMUM_HEIGHT, MINIMUM_HEIGHT * 9 / 16);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);

		setVisible(true);
	}

}
