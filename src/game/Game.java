package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import Data.Board;
import Data.Knoten;
import Factory.BoardFactory;
import log.Log;

public class Game {

	public static final int DEFAULT_PLAYER_COUNT = 4;
	private Board board;
	private int round;
	private ArrayList<Player> players;
	private LinkedList<Player> remainingRoundPlayers;

	public Game(Board board, int playerClount) {
		this.board = board;
		round = 0;
		players = new ArrayList<>();

		LinkedList<Color> list = new LinkedList<>(getAllColors());
		Collections.shuffle(list);
		for (int i = 0; i < playerClount; i++) {
			Random rng = new Random();
			Color color = new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));
			if (!list.isEmpty()) {
				color = list.pop();
			}

			Player p = new Player(this, color);
			players.add(p);
		}

		nextRound();
		updateKnotenScores(getCurrentPlayer());
	}

	private void nextRound() {
		round++;
		remainingRoundPlayers = new LinkedList<>(players);
		if (round % 2 == 0) {
			Collections.reverse(remainingRoundPlayers);
		}
	}

	public void nextTurn() {
		Player p = remainingRoundPlayers.pop();
		executeTurn(p);

		if (remainingRoundPlayers.isEmpty()) {
			nextRound();
		}
	}

	public void finishRound() {
		int r = round;
		while (round == r) {
			nextTurn();
		}
	}

	private void executeTurn(Player player) {
		updateKnotenScores(player);
		Knoten best = board.getBestKnoten();

		if (best != null) {
			// Log.log("Player " + player.getColor().toString() + " claims the
			// node with the value of " + best.getScore());
			best.setOwner(player);

			// Update number of resources a player has
			player.updateResourceNumbers();

			Log.log("Player claimed vertex with score " + best.getScore());
			Log.log("Player resource status: " + player.toString());
		}
	}

	public void updateKnotenScores(Player player) {
		for (Knoten k : board.getKnotenListe()) {
			k.updateScore(round, player);
		}

		String res = "No best node found.";
		Knoten best = getBoard().getBestKnoten();
		if (best != null) {
			res = "The best node got the score " + best.getScore();
		}

		Log.log("Recalculating the node scores. " + res);
	}

	public Player getPlayer(int index) {
		return getPlayers().get(index);
	}

	public int getPlayerCount() {
		return getPlayers().size();
	}

	public ArrayList<Player> getPlayers() {
		return new ArrayList<>(players);
	}

	public Board getBoard() {
		return board;
	}

	public int getRound() {
		return round;
	}

	public static Game defaultGame() {
		Board b = BoardFactory.creatBoard();
		return new Game(b, DEFAULT_PLAYER_COUNT);
	}

	public Player getCurrentPlayer() {
		return remainingRoundPlayers.peek();
	}

	public static ArrayList<Color> getAllColors() {
		ArrayList<Color> colorList = new ArrayList<>();
		colorList.add(Color.RED);
		colorList.add(Color.GREEN);
		colorList.add(Color.YELLOW);
		colorList.add(Color.ORANGE);
		colorList.add(Color.CYAN);
		colorList.add(Color.PINK);
		colorList.add(Color.MAGENTA);
		colorList.add(Color.LIGHT_GRAY);

		return colorList;
	}
}
