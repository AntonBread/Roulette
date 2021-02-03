package casino.roulette;

import java.util.*;

public class Game {

	public static void main(String[] args) {
		Player player = new Player();
		long start = player.getBalance();
		
		// Game cycle is simple, all the lines of code come from if/else
		// statements to check which bet type is in effect
		while (!Thread.currentThread().isInterrupted()) {
			ArrayList<Bet> bets = player.placeBets();
			NumStats roll = new NumStats(Roulette.roll());
			System.out.print("Roll result: ");
			if (roll.getValue() == 0)
				System.out.println("ZERO");
			else if (roll.getColor() == true)
				System.out.printf("RED %d\n", roll.getValue());
			else
				System.out.printf("BLACK %d\n", roll.getValue());
			
			for (int i = 0; i < bets.size(); i++) {
				Bet bet = bets.get(i);
				if (bet.getType() == BetType.zero) {
					if (roll.getValue() == 0)
						player.payout(bet);
					else
						player.lost();
				}
				else if (bet.getType() == BetType.color) {
					boolean color = (bet.getNums().get(0) == 1);
					if (roll.getColor() == color)
						player.payout(bet);
					else
						player.lost();
				}
				else if (bet.getType() == BetType.parity) {
					boolean parity = (bet.getNums().get(0) == 1);
					if (roll.getParity() == parity)
						player.payout(bet);
					else
						player.lost();
				}
				else if (bet.getType() == BetType.half) {
					if (roll.getHalf() == bet.getNums().get(0))
						player.payout(bet);
					else
						player.lost();
				}
				else if (bet.getType() == BetType.third) {
					if (roll.getThird() == bet.getNums().get(0))
						player.payout(bet);
					else
						player.lost();
				}
				else if (bet.getType() == BetType.column) {
					if (roll.getColumn() == bet.getNums().get(0))
						player.payout(bet);
					else
						player.lost();
				}
				else {
					int n = roll.getValue();
					ArrayList<Integer> nums = bet.getNums();
					for (int j = 0; j < nums.size(); j++)
						if (nums.get(j) == n) {
							player.payout(bet);
							break;
						}
						else if (j + 1 == nums.size())
							player.lost();
				}
			}
			
			// Play until you don't wish to continue anymore
			// Or if you run out of money
			// Your total win/loss amount will be displayed upon finishing the session
			System.out.print("Play again? (Y/N) ");
			String again = in.nextLine();
			again = again.toLowerCase();
			while (!again.equals("y") && !again.equals("n")) {
				System.out.print("Invalid input, try again: ");
				again = in.nextLine();
			}
			if (again.equals("n")) {
				long change = player.getBalance() - start;
				if (change < 0)
					System.out.printf("You lost %d money units\n", Math.abs(change));
				else if (change > 0)
					System.out.printf("You won %d money units\n", change);
				else
					System.out.printf("You won and lost nothing\n");
				Thread.currentThread().interrupt();
			}
			else if (player.getBalance() < 10) {
				System.out.println("You lost all money and cannot continue playing!");
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	static Scanner in = new Scanner(System.in);

}
