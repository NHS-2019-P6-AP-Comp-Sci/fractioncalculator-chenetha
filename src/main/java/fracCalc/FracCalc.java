/**
 * @author Mr. Rasmussen
 */

package fracCalc;

import java.util.Scanner;

public class FracCalc {

	public static void main(String[] args) {
		// TODO: Read the input from the user and call produceAnswer with an
		// equation
		Scanner sc = new Scanner(System.in);
		while (true) {
			String input = sc.nextLine();
			if (input.equals("quit")) {
				break;
			} else {
				System.out.println(produceAnswer(input));
			}
		}
		sc.close();
	}

	// ** IMPORTANT ** DO NOT DELETE THIS FUNCTION. This function will be used
	// to test your code
	// This function takes a String 'input' and produces the result
	//
	// input is a fraction string that needs to be evaluated. For your program,
	// this will be the user input.
	// e.g. input ==> "1/2 + 3/4"
	//
	// The function should return the result of the fraction after it has been
	// calculated
	// e.g. return ==> "1_1/4"
	public static String produceAnswer(String input) {
		// TODO: Implement this function to produce the solution to the input
		String values[] = input.split(" ");
		String improper = toImproperFraction(values[0]);
		long num = FracNum(improper);
		long denom = FracDenom(improper);
		for (int ii = 1; ii < values.length; ii += 2) {
			String improper2 = toImproperFraction(values[ii + 1]);
			long num2 = FracNum(improper2);
			long denom2 = FracDenom(improper2);

			switch (values[ii]) {
			case "+":
				num *= denom2;
				num += num2 * denom;
				denom *= denom2;
				break;
			case "-":
				num *= denom2;
				num -= num2 * denom;
				denom *= denom2;
				break;
			case "*":
				num *= num2;
				denom *= denom2;
				break;
			case "/":
				num *= denom2;
				denom *= num2;
				break;
			}
		}
		return toMixedFraction(reduce(num, denom));
	}

	// TODO: Fill in the space below with any helper methods that you think you
	// will need

	public static String toImproperFraction(String value) {
		String fraction = null;
		long whole = 0;
		long num = 0;
		long denom = 1;

		if (value.contains("_")) {
			String values[] = value.split("_");
			whole = Long.parseLong(values[0]);
			fraction = values[1];
		} else if (value.contains("/")) {
			fraction = value;
		} else {
			whole = Long.parseLong(value);
		}

		if (fraction != null) {
			num = FracNum(fraction);
			denom = FracDenom(fraction);
			if (whole < 0) {
				num *= -1;
			}
		}

		num = denom * whole + num;
		if (denom < 0) {
			denom *= -1;
			num *= -1;
		}
		return makeFractionString(num, denom);
	}

	public static String makeFractionString(long num, long denom) {
		return num + "/" + denom;
	}

	public static long FracNum(String fraction) {
		if (fraction.contains("/")) {
			String values[] = fraction.split("/");
			return Long.parseLong(values[0]);
		} else {
			return Long.parseLong(fraction);
		}
	}

	public static long FracDenom(String fraction) {
		if (fraction.contains("/")) {
			String values[] = fraction.split("/");
			return Long.parseLong(values[1]);
		} else {
			return 1;
		}
	}

	public static String toMixedFraction(String fraction) {
		int num_sign = 1, den_sign = 1;
		long num = FracNum(fraction);
		long denom = FracDenom(fraction);
		if (num < 0) {
			num_sign = -1;
			num *= -1;
		}
		if (denom < 0) {
			den_sign = -1;
			denom *= -1;
		}
		int sign = num_sign * den_sign;

		long whole = num / denom;
		num = num % denom;
		whole *= sign;
		num *= sign;
		return makeMixedFractionString(whole, num, denom);
	}

	public static String makeMixedFractionString(long whole, long num, long denom) {
		if (num == 0) {
			return Long.toString(whole);
		} else if (whole == 0) {
			return "" + num + "/" + denom;
		} else {
			if (whole > 0) {
				return "" + whole + "_" + num + "/" + denom;
			} else {
				return "" + whole + "_" + -num + "/" + denom;
			}
		}
	}

	public static String reduce(long num, long denom) {
		long gcd = MixedFraction.gcd(num, denom);
		if (gcd < 0) {
			gcd *= -1;
		}
		num /= gcd;
		denom /= gcd;
		return makeFractionString(num, denom);
	}

	public static class MixedFraction {
		public long whole = 0;
		public long num = 0;
		public long denom = 1;

		public static MixedFraction fromString(String value) {
			MixedFraction newNum = new MixedFraction();
			String fraction = null;
			if (value.contains("_")) {
				String values[] = value.split("_");
				newNum.whole = Long.parseLong(values[0]);
				fraction = values[1];
			} else if (value.contains("/")) {
				fraction = value;
			} else {
				newNum.whole = Long.parseLong(value);
			}

			if (fraction != null) {
				if (fraction.contains("/")) {
					String values[] = fraction.split("/");
					newNum.num = Long.parseLong(values[0]);
					newNum.denom = Long.parseLong(values[1]);
					if (newNum.whole < 0) {
						newNum.num *= -1;
					}
				}
			}

			return newNum;
		}

		public String description() {
			return "whole:" + whole + " num:" + num + " denom:" + denom;
		}

		public String toString() {
			if (num == 0) {
				return Long.toString(whole);
			} else if (whole == 0) {
				return "" + num + "/" + denom;
			} else {
				if (whole > 0) {
					return "" + whole + "_" + num + "/" + denom;
				} else {
					return "" + whole + "_" + -num + "/" + denom;
				}
			}
		}

		public MixedFraction copy() {
			MixedFraction newNum = new MixedFraction();
			newNum.whole = whole;
			newNum.num = num;
			newNum.denom = denom;
			return newNum;
		}

		public MixedFraction reduced() {
			MixedFraction newNum = copy();
			long gcd = gcd(num, denom);
			if (gcd < 0) {
				gcd *= -1;
			}
			newNum.num /= gcd;
			newNum.denom /= gcd;
			return newNum;
		}

		public MixedFraction improper() {
			MixedFraction newNum = new MixedFraction();
			newNum.denom = denom;
			newNum.num = denom * whole + num;
			if (newNum.denom < 0) {
				newNum.denom *= -1;
				newNum.num *= -1;
			}
			return newNum;
		}

		public static long gcd(long a, long b) {
			while (true) {
				if (b == 0) {
					return a;
				} else {
					long temp = a % b;
					a = b;
					b = temp;
				}
			}
		}

		public MixedFraction mixed() {
			MixedFraction newNum = new MixedFraction();
			int whole_sign = 1, num_sign = 1, den_sign = 1;
			if (whole < 0) {
				whole_sign = -1;
				whole *= -1;
			}
			if (num < 0) {
				num_sign = -1;
				num *= -1;
			}
			if (denom < 0) {
				den_sign = -1;
				denom *= -1;
			}
			int sign = whole_sign * num_sign * den_sign;
			newNum.whole = whole + num / denom;
			newNum.num = num % denom;
			newNum.denom = denom;
			newNum.whole *= sign;
			newNum.num *= sign;
			return newNum;
		}

		public MixedFraction reciprocal() {
			MixedFraction a = improper();
			long temp = a.denom;
			a.denom = a.num;
			a.num = temp;
			if (a.denom < 0) {
				a.denom *= -1;
				a.num *= -1;
			}
			return a;
		}

		public MixedFraction addition(MixedFraction other) {
			MixedFraction a = improper();
			other = other.improper();
			a.num *= other.denom;
			a.num += other.num * a.denom;
			a.denom *= other.denom;
			return a.reduced().mixed();
		}

		public MixedFraction multiplication(MixedFraction other) {
			MixedFraction a = improper();
			other = other.improper();
			a.num *= other.num;
			a.denom *= other.denom;
			return a.reduced().mixed();
		}

		public MixedFraction division(MixedFraction other) {
			return multiplication(other.reciprocal());
		}

		public MixedFraction subtraction(MixedFraction other) {
			other = other.improper();
			other.num *= -1;
			return addition(other);
		}
	}

}
