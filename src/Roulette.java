package casino.roulette;

import java.util.Random;

public class Roulette {

	static int[] wheel = createRoulette();
	static int[][] board = createBoard();

	private static Random rnd = new Random();

	public static int roll() {
		System.out.println("Rolling...");
		try {
			Thread.sleep(2500);
		}
		catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
		return wheel[rnd.nextInt(37)];
	}

	public static int[] createRoulette() {
		int[] arr = new int[37];
		for (int i = 0; i < 37; i++)
			arr[i] = i;
		return arr;
	}

	public static int[][] createBoard() {
		int[][] board = new int[13][3];
		int n = 1;
		for (int i = 1; i < 13; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = n++;
		return board;
	}

	public static void displayBoard() {
		System.out.printf("\t%d\n", 0);
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j < 3; j++)
				System.out.printf("%d\t", board[i][j]);
			System.out.println();
		}
	}

}
