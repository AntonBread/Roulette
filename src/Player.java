package casino.roulette;

import java.util.*;

public class Player {
	private long balance;
	private ArrayList<Bet> bets;
	private static Scanner in = new Scanner(System.in);

	public Player() {
		this.balance = 10000;
	}

	public long getBalance() {
		return this.balance;
	}

	public ArrayList<Bet> getBets() {
		return this.bets;
	}

	public void payout(Bet bet) {
		this.balance += (bet.getSum() * bet.getMult());
		System.out.printf("You won the bet! Your current balance: %d\n", balance);
	}
	
	public void lost() {
		System.out.printf("You lost the bet. Your current balance: %d\n", balance);
	}

	public ArrayList<Bet> placeBets() {
		ArrayList<Bet> bets = new ArrayList<>();
		boolean betting = true;
		while (betting) {
			Bet bet = new Bet();
			if (balance - bet.getSum() < 0) {
				System.out.println("You cannot go below 0 money units, create the bet again");
				continue;
			}
			balance -= bet.getSum();
			System.out.printf("Your current balance: %d\n", balance);
			bets.add(bet);
			System.out.print("Place another bet? (Y/N) ");
			String again = in.nextLine();
			again = again.toLowerCase();
			while (!again.equals("y") && !again.equals("n")) {
				System.out.print("Invalid input, try again: ");
				again = in.nextLine();
			}
			if (again.equals("n"))
				betting = false;
		}

		return bets;
	}
}
