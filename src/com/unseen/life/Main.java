package com.unseen.life;

public class Main {

	public static void main(String argv[]) {
		if (argv.length != 1) {
			System.out.println("Usage: life <initial population>");
			System.out.println("\t<initial population> is a string of pairs of coordinates surrounded");
			System.out.println("\t\tby parentheses. Like this: '(1,1)(2,3)(2,2)'");
			System.exit(0);
		}
		Population population = new Population(argv[0]);
		for (int i = 0; i < 10; i++) {
			System.out.println("Population " + Integer.toString(i));
			population.dump();
			population.advance();
		}
	}
}
