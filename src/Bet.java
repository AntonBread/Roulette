package casino.roulette;

import java.util.*;

public class Bet {
	private long betSum;
	private int betmult;
	private BetType type = BetType.NONE;
	private ArrayList<Integer> numbers = new ArrayList<>();

	public ArrayList<Integer> getNums() {
		return numbers;
	}

	public long getSum() {
		return this.betSum;
	}

	public int getMult() {
		return this.betmult;
	}
	
	public BetType getType() {
		return this.type;
	}

	private static Scanner in = new Scanner(System.in);

	public Bet() {
		createBet();
	}

	// For setting BetType
	public void setType() {
		System.out.println("Choose bet type: ");
		System.out.println(
				"0) Zero\n1) Single\n2) Split\n3) Street\n4) Square\n5) Line\n6) Half\n7) Third\n8) Column\n9) Color\n10) Parity");
		while (this.type == BetType.NONE) {
			int code = in.nextInt();
			switch (code) {
				case 0:
					this.type = BetType.zero;
					this.betmult = 36;
					break;
				case 1:
					this.type = BetType.single;
					this.betmult = 36;
					break;
				case 2:
					this.type = BetType.split;
					this.betmult = 18;
					break;
				case 3:
					this.type = BetType.street;
					this.betmult = 12;
					break;
				case 4:
					this.type = BetType.square;
					this.betmult = 9;
					break;
				case 5:
					this.type = BetType.line;
					this.betmult = 6;
					break;
				case 6:
					this.type = BetType.half;
					this.betmult = 2;
					break;
				case 7:
					this.type = BetType.third;
					this.betmult = 3;
					break;
				case 8:
					this.type = BetType.column;
					this.betmult = 3;
					break;
				case 9:
					this.type = BetType.color;
					this.betmult = 2;
					break;
				case 10:
					this.type = BetType.parity;
					this.betmult = 2;
					break;
				default:
					System.out.println("Invalid input, try again: ");
					this.type = BetType.NONE;
			}
		}
	}

	// For setting bet sum
	public void setSum() {
		System.out.print("Input bet sum (from 10 to 1000000): ");
		long sum = in.nextLong();
		while (sum < 10 || sum > 1000000) {
			System.out.print("Invalid bet value, input again: ");
			sum = in.nextLong();
		}
		this.betSum = sum;
	}

	// For setting which numbers are being bet on (if any)
	public void setNumbers() {
		numbers.clear();
		int n1, n2, n3, n4, n5, n6;
		switch (type) {
			case zero:
				numbers.add(0);
				break;

			case single:
				System.out.print("Input your number (1 to 36): ");
				n1 = inputNum1();
				numbers.add(n1);
				System.out.printf("Bet placed on number %d\n", n1);
				break;

			case split:
				System.out.print("Input first number: ");
				n1 = inputNum1();
				n2 = inputNum2(n1);
				numbers.add(n1);
				numbers.add(n2);
				System.out.printf("Bet placed on numbers %d, %d\n", n1, n2);
				break;

			case street:
				System.out.print("Input any number from desired row: ");
				n1 = inputNum1();
				if (n1 % 3 == 1) {
					n2 = n1 + 1;
					n3 = n1 + 2;
				}
				else if (n1 % 3 == 2) {
					n2 = n1 - 1;
					n3 = n1 + 1;
				}
				else {
					n2 = n1 - 1;
					n3 = n2 - 2;
				}
				numbers.add(n1);
				numbers.add(n2);
				numbers.add(n3);
				System.out.printf("Bet placed on numbers %d, %d, %d\n", n1, n2, n3);
				break;

			case square:
				System.out.print("Input first number: ");
				n1 = inputNum1();
				n2 = inputNum2(n1);
				int[] nums = inputNum34(n1, n2);
				Arrays.sort(nums);
				n1 = nums[0];
				numbers.add(n1);
				n2 = nums[1];
				numbers.add(n2);
				n3 = nums[2];
				numbers.add(n3);
				n4 = nums[3];
				numbers.add(n4);
				System.out.printf("Bet placed on numbers %d, %d, %d, %d\n", n1, n2, n3, n4);
				break;

			case line:
				System.out.print("Input any number from the first row: ");
				n1 = inputNum1();
				if (n1 == 1 || n1 == 2 || n1 == 3) {
					System.out.println("Only one row available to form the line");
					n1 = 1;
					numbers.add(n1);
					n2 = 2;
					numbers.add(n2);
					n3 = 3;
					numbers.add(n3);
					n4 = 4;
					numbers.add(n4);
					n5 = 5;
					numbers.add(n5);
					n6 = 6;
					numbers.add(n6);
				}
				else if (n1 == 34 || n1 == 35 || n1 == 36) {
					System.out.println("Only one row available to form the line");
					n1 = 31;
					numbers.add(n1);
					n2 = 32;
					numbers.add(n2);
					n3 = 33;
					numbers.add(n3);
					n4 = 34;
					numbers.add(n4);
					n5 = 35;
					numbers.add(n5);
					n6 = 36;
					numbers.add(n6);
				}
				else {
					int row = -1;
					for (int i = 1; i < 13; i++)
						for (int j = 0; j < 3; j++)
							if (board[i][j] == n1)
								row = i;
					int[] available1 = board[row - 1];
					int[] available2 = board[row + 1];
					System.out.printf(
							"Two rows available to form the line: %s (1) and %s (2)\nPick one by inputting 1 or 2: ",
							Arrays.toString(available1), Arrays.toString(available2));
					int pick = in.nextInt();
					while (pick != 1 && pick != 2) {
						System.out.print("Invalid input, try again: ");
						pick = in.nextInt();
					}
					if (pick == 2) {
						n1 = board[row][0];
						numbers.add(n1);
						n2 = board[row][1];
						numbers.add(n2);
						n3 = board[row][2];
						numbers.add(n3);
						n4 = board[row + 1][0];
						numbers.add(n4);
						n5 = board[row + 1][1];
						numbers.add(n5);
						n6 = board[row + 1][2];
						numbers.add(n6);
					}
					else {
						n1 = board[row - 1][0];
						numbers.add(n1);
						n2 = board[row - 1][1];
						numbers.add(n2);
						n3 = board[row - 1][2];
						numbers.add(n3);
						n4 = board[row][0];
						numbers.add(n4);
						n5 = board[row][1];
						numbers.add(n5);
						n6 = board[row][2];
						numbers.add(n6);
					}
				}
				System.out.printf("Bet placed on numbers %d, %d, %d, %d, %d, %d\n", n1, n2, n3, n4, n5, n6);
				break;

			case half:
				System.out.print("Which half (1 or 2): ");
				int half = in.nextInt();
				while (half != 1 && half != 2) {
					System.out.print("Invalid input, try again: ");
					half = in.nextInt();
				}
				numbers.add(half);
				if (half == 1)
					System.out.println("Bet placed on numbers 1-18");
				else
					System.out.println("Bet placed on numbers 19-36");
				break;

			case third:
				System.out.print("Which third (1, 2 or 3): ");
				int third = in.nextInt();
				while (third != 1 && third != 2 && third != 3) {
					System.out.print("Invalid input, try again: ");
					third = in.nextInt();
				}
				numbers.add(third);
				if (third == 1)
					System.out.println("Bet placed on numbers 1-12");
				else if (third == 2)
					System.out.println("Bet placed on numbers 13-24");
				else
					System.out.println("Bet placed on numbers 25-36");
				break;

			case column:
				System.out.print("Which column (1, 2 or 3): ");
				int column = in.nextInt();
				while (column != 1 && column != 2 && column != 3) {
					System.out.print("Invalid input, try again: ");
					column = in.nextInt();
				}
				numbers.add(column);
				if (column == 1)
					System.out.println("Bet placed on the 1st column");
				else if (column == 2)
					System.out.println("Bet placed on the 2nd column");
				else
					System.out.println("Bet placed on the 3rd column");
				break;

			case color:
				System.out.print("Which color (Black = 0, Red = 1): ");
				int color = in.nextInt();
				while (color != 0 && color != 1) {
					System.out.print("Invalid input, try again: ");
					color = in.nextInt();
				}
				numbers.add(color);
				if (color == 0)
					System.out.println("Bet placed on black");
				else
					System.out.println("Bet placed on red");
				break;

			case parity:
				System.out.print("Even or odd (1 or 0): ");
				int parity = in.nextInt();
				while (parity != 0 && parity != 1) {
					System.out.print("Invalid input, try again: ");
					parity = in.nextInt();
				}
				numbers.add(parity);
				if (parity == 0)
					System.out.println("Bet placed on odd");
				else
					System.out.println("Bet placed on even");
				break;

			case NONE:
				System.out.println("BetType error");
				break;
		}
	}

	// For creating a full bet using methods above
	// This method is directly called in constructor
	public void createBet() {
		setType();
		setNumbers();
		setSum();
	}
	
	// For single number bets
	// and inputting first number in multi-number bets
	public int inputNum1() {
		int n = in.nextInt();
		while (n < 1 || n > 36) {
			System.out.print("Invalid number, input again: ");
			n = in.nextInt();
		}
		return n;
	}

	static int[][] board = Roulette.board;
	
	// For split (2 number) bets
	// and inputting first two numbers in square bets
	public int inputNum2(int n1) {
		// First, we are finding what numbers are valid for the second input given the
		// first one
		// Roulette board is made into a matrix to make the process simpler
		int x = -1, y = -1;
		for (int i = 1; i < 13; i++)
			for (int j = 0; j < 3; j++)
				if (Roulette.board[i][j] == n1) {
					x = j;
					y = i;
				}

		int[] available = new int[4];
		if (x == 0) {
			if (y == 1) {
				available[0] = board[y][x + 1];
				available[1] = board[y + 1][x];
			}
			else if (y == 12) {
				available[0] = board[y][x + 1];
				available[1] = board[y - 1][x];
			}
			else {
				available[0] = board[y][x + 1];
				available[1] = board[y + 1][x];
				available[2] = board[y - 1][x];
			}
		}

		else if (x == 1) {
			if (y == 1) {
				available[0] = board[y][x - 1];
				available[1] = board[y][x + 1];
				available[2] = board[y + 1][x];
			}
			else if (y == 12) {
				available[0] = board[y][x - 1];
				available[1] = board[y][x + 1];
				available[2] = board[y - 1][x];
			}
			else {
				available[0] = board[y][x - 1];
				available[1] = board[y][x + 1];
				available[2] = board[y - 1][x];
				available[3] = board[y + 1][x];
			}
		}

		else if (x == 2) {
			if (y == 1) {
				available[0] = board[y][x - 1];
				available[1] = board[y + 1][x];
			}
			else if (y == 12) {
				available[0] = board[y][x - 1];
				available[1] = board[y - 1][x];
			}
			else {
				available[0] = board[y][x - 1];
				available[1] = board[y + 1][x];
				available[2] = board[y - 1][x];
			}
		}

		System.out.print("Available numbers: ");
		for (int i = 0; i < available.length; i++)
			if (available[i] != 0)
				System.out.print(available[i] + " ");

		System.out.print("\nInput second number: ");
		int n2 = in.nextInt();
		while (n2 != available[0] && n2 != available[1] && n2 != available[2] && n2 != available[3] || n2 == 0) {
			System.out.print("Invalid input, try again: ");
			n2 = in.nextInt();
		}

		return n2;
	}
	
	// For square (4 number) bets
	public int[] inputNum34(int n1, int n2) {
		int[] nums = new int[4];
		nums[0] = n1;
		nums[1] = n2;

		int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
		for (int i = 1; i < 13; i++)
			for (int j = 0; j < 3; j++)
				if (n1 == board[i][j]) {
					x1 = j;
					y1 = i;
				}
				else if (n2 == board[i][j]) {
					x2 = j;
					y2 = i;
				}

		if (x1 == x2) {
			if (x1 % 3 == 0) {
				System.out.println("Only one square possible");
				nums[2] = n1 + 1;
				nums[3] = n2 + 1;
			}
			else if (x1 % 3 == 2) {
				System.out.println("Only one square possible");
				nums[2] = n1 - 1;
				nums[3] = n2 - 1;
			}
			else {
				System.out.printf("Two squares possible:\n%d %d\n%d %d (1)\nOR\n%d %d\n%d %d (2)\n", n1 - 1, n1, n2 - 1,
						n2, n1, n1 + 1, n2, n2 + 1);
				System.out.print("Pick one by inputting 1 or 2: ");
				int pick = in.nextInt();
				while (pick != 1 && pick != 2) {
					System.out.print("Invalid input, try again: ");
					pick = in.nextInt();
				}
				if (pick == 1) {
					nums[2] = n1 - 1;
					nums[3] = n2 - 1;
				}
				else {
					nums[2] = n1 + 1;
					nums[3] = n2 + 1;
				}
			}
		}

		else if (y1 == y2) {
			if (y1 == 1) {
				System.out.println("Only one square possible");
				nums[2] = board[y1 + 1][x1];
				nums[3] = board[y1 + 1][x2];
			}
			else if (y1 == 12) {
				System.out.println("Only one square possible");
				nums[2] = board[y1 - 1][x1];
				nums[3] = board[y1 - 1][x2];
			}
			else {
				System.out.printf("Two squares possible: \n%d %d\n%d %d (1)\nOR\n%d %d\n%d %d (2)\n", n1, n2,
						board[y1 + 1][x1], board[y1 + 1][x2], n1, n2, board[y1 - 1][x1], board[y1 - 1][x2]);
				System.out.print("Pick one by inputting 1 or 2: ");
				int pick = in.nextInt();
				while (pick != 1 && pick != 2) {
					System.out.print("Invalid input, try again: ");
					pick = in.nextInt();
				}
				if (pick == 1) {
					nums[2] = board[y1 + 1][x1];
					nums[3] = board[y1 + 1][x2];
				}
				else {
					nums[2] = board[y1 - 1][x1];
					nums[3] = board[y1 - 1][x2];
				}
			}
		}

		return nums;
	}
}
