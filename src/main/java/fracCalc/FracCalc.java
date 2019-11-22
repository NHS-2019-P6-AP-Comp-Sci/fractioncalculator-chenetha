/**
 * @author Mr. Rasmussen
 */

package fracCalc;

import java.util.*;

public class FracCalc {

	public static void main(String[] args) {
		// TODO: Read the input from the user and call produceAnswer with an equation
		Scanner num = new Scanner(System.in);
		
		System.out.print("Enter a fraction problem: ");
		String userResponse = num.nextLine();
		while(!userResponse.equalsIgnoreCase("quit")) {
			System.out.println(produceAnswer(userResponse));
			System.out.print("Enter a fraction problem: ");
			userResponse = num.nextLine();
		}

		System.out.println(produceAnswer(userResponse));
	}

	// ** IMPORTANT ** DO NOT DELETE THIS FUNCTION. This function will be used to
	// test your code
	// This function takes a String 'input' and produces the result
	//
	// input is a fraction string that needs to be evaluated. For your program, this
	// will be the user input.
	// e.g. input ==> "1/2 + 3/4"
	//
	// The function should return the result of the fraction after it has been
	// calculated
	// e.g. return ==> "1_1/4"
	public static String produceAnswer(String input) {
		// TODO: Implement this function to produce the solution to the input
		String temp = input;
		String number1 = temp.substring(0, temp.indexOf(' '));
		temp = temp.substring(temp.indexOf(' ') + 1);
		String operator = temp.substring(0, temp.indexOf(' '));
		temp = temp.substring(temp.indexOf(' ') + 1);
		String number2 = temp;
		
		String number2Whole = findWhole(number2);
		String number2Numerator = findNum(number2);
		String number2Denominator = findDenom(number2);
		
		String checkAnswer = "whole:" + number2Whole + " numerator:" + number2Numerator + " denominator:" + number2Denominator;
		
		return checkAnswer;
	}		
		public static String findWhole(String str) {
			if (str.contains("_")) {
				return str.substring(0, str.indexOf('_'));
			} else if (str.contains("/")) {
				return "0";
			} else return str;
		}
		
		public static String findNum(String str) {
			if (str.contains("_")) {
				return str.substring(str.indexOf('_') + 1, str.indexOf('/'));
			} else if (str.contains("/")) {
				return str.substring(0, str.indexOf('/'));
			} else {
				return "0";
			}
		}
		 
		public static String findDenom(String str) {
			if (str.contains("/")) {
				return str.substring(str.indexOf("/") + 1);
			} else {
				return "1";
			}
		}
	{
	}
	// TODO: Fill in the space below with any helper methods that you think you will
	// need

}