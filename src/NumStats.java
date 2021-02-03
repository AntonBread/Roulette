package casino.roulette;

public class NumStats {
	private int value;
	private boolean color; // TRUE = RED, FALSE = BLACK
	private boolean parity; // TRUE = EVEN, FALSE = ODD
	private int half;
	private int third;
	private int column;

	public NumStats(int value) {
		this.value = value;
		if (value == 0)
			return;
		color = colors[value - 1];
		parity = value % 2 == 0;
		half = (value - 1) / 18 + 1;
		third = (value - 1) / 12 + 1;
		column = value % 3 == 0 ? (3) : value % 3;
	}

	/*
	 * Getters
	 */
	public int getValue() {
		return value;
	}

	public boolean getColor() {
		return color;
	}

	public boolean getParity() {
		return parity;
	}

	public int getHalf() {
		return half;
	}

	public int getThird() {
		return third;
	}

	public int getColumn() {
		return column;
	}

	/*
	 * Color array, TRUE = RED, FALSE = BLACK
	 */
	private final boolean[] colors = { true, false, true, false, true, false, true, false, true, false, false, true,
			false, true, false, true, false, true, true, false, true, false, true, false, true, false, true, false,
			false, true, false, true, false, true, false, true };
}
