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
		DisplayPopulation display = new DisplayPopulation(population);
		display.start();
		display.centerPopulation();
		for (int i = 0; i < 50; i++) {
			display.draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			final long startTime = System.nanoTime();
			population.advance();
			final long diff = System.nanoTime() - startTime;
			display.drawCalculationTime(diff);
			display.refresh();
		}
		display.stop();
	}
}
