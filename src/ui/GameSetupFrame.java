package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Data.Board;
import Factory.BoardFactory;
import game.Game;

public class GameSetupFrame extends JFrame {

	private static final long serialVersionUID = -2479672129995284806L;
	private JTextField seedTF;
	private JSpinner playersSP;
	private JCheckBox closeBT;

	public GameSetupFrame() {
		setTitle("Design Optimisation WS 16/17 - New Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 218, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblPlayers = new JLabel(" Players:");
		GridBagConstraints gbc_lblPlayers = new GridBagConstraints();
		gbc_lblPlayers.anchor = GridBagConstraints.EAST;
		gbc_lblPlayers.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayers.gridx = 0;
		gbc_lblPlayers.gridy = 0;
		getContentPane().add(lblPlayers, gbc_lblPlayers);

		playersSP = new JSpinner();
		playersSP.setModel(new SpinnerNumberModel(new Integer(4), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_playersSP = new GridBagConstraints();
		gbc_playersSP.gridwidth = 2;
		gbc_playersSP.insets = new Insets(0, 0, 5, 0);
		gbc_playersSP.fill = GridBagConstraints.HORIZONTAL;
		gbc_playersSP.gridx = 1;
		gbc_playersSP.gridy = 0;
		getContentPane().add(playersSP, gbc_playersSP);

		JLabel lblSeed = new JLabel(" Seed: ");
		GridBagConstraints gbc_lblSeed = new GridBagConstraints();
		gbc_lblSeed.anchor = GridBagConstraints.EAST;
		gbc_lblSeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeed.gridx = 0;
		gbc_lblSeed.gridy = 1;
		getContentPane().add(lblSeed, gbc_lblSeed);

		seedTF = new JTextField();
		seedTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				go();
			}
		});
		GridBagConstraints gbc_seedTF = new GridBagConstraints();
		gbc_seedTF.insets = new Insets(0, 0, 5, 5);
		gbc_seedTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_seedTF.gridx = 1;
		gbc_seedTF.gridy = 1;
		getContentPane().add(seedTF, gbc_seedTF);
		seedTF.setColumns(10);

		JButton btnRandom = new JButton("Random");
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				randomSeed();
			}
		});
		GridBagConstraints gbc_btnRandom = new GridBagConstraints();
		gbc_btnRandom.insets = new Insets(0, 0, 5, 0);
		gbc_btnRandom.gridx = 2;
		gbc_btnRandom.gridy = 1;
		getContentPane().add(btnRandom, gbc_btnRandom);

		closeBT = new JCheckBox("Close this window after creating");
		closeBT.setSelected(true);
		GridBagConstraints gbc_closeBT = new GridBagConstraints();
		gbc_closeBT.anchor = GridBagConstraints.WEST;
		gbc_closeBT.gridwidth = 3;
		gbc_closeBT.insets = new Insets(0, 0, 5, 5);
		gbc_closeBT.gridx = 0;
		gbc_closeBT.gridy = 2;
		getContentPane().add(closeBT, gbc_closeBT);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.anchor = GridBagConstraints.SOUTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		getContentPane().add(panel, gbc_panel);

		JButton btnNewButton = new JButton("Create");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				go();
			}
		});
		panel.add(btnNewButton);
		pack();
		setMinimumSize(getSize());
		randomSeed();
		setVisible(true);
	}

	private void go() {
		int players = (int) playersSP.getValue();
		String seed = seedTF.getText();

		Board board = BoardFactory.creatBoard(new Random(seed.hashCode()));
		Game game = new Game(board, players);
		MainWindow window = new MainWindow(game);
		window.setSeedSource(seed);

		if (closeBT.isSelected()) {
			dispose();
		} else {
			requestFocus();
		}
	}

	private void randomSeed() {
		seedTF.setText(String.valueOf(new Date().getTime()));
	}
}
