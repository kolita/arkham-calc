package com.kolita.arkhamcalc;

import java.util.Random;

import junit.framework.TestCase;

public class TestCalculator extends TestCase {
	private static final int NUMBER_ITERATIONS = 1000000;
	private static final double EPS = 0.01;
	
	private Random random;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		random = new Random();
	}
	
	public void testCalculate() {
		int requiredDice = 8;
		int requiredSuccesses = 3;
		int totalWins = 0;
		
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue >= 5) {
					totalSuccesses++;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		assertEquals(percentageWins, new Calculator(8, 3, false, false).calculate(), EPS);
	}
	
	public void testCalculateBlessed() {
		int requiredDice = 6;
		int requiredSuccesses = 2;
		int totalWins = 0;
		
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue >= 4) {
					totalSuccesses++;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		assertEquals(percentageWins, new Calculator(6, 2, true, false).calculate(), EPS);
	}
	
	public void testCalculateCursed() {
		int requiredDice = 3;
		int requiredSuccesses = 1;
		int totalWins = 0;
		
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue >= 6) {
					totalSuccesses++;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		assertEquals(percentageWins, new Calculator(3, 1, false, true).calculate(), EPS);
	}
	
	public void testCalculateNoSuccesses() {
		assertEquals(0.0, new Calculator(2, 3, false, false).calculate(), 0.0);
	}
	
	private int getRandomDieValue() {
		return random.nextInt(6) + 1;
	}

}
