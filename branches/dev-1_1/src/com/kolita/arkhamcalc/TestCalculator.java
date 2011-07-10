/*ArkhamCalc
Copyright (C) 2011  Matthew Cole

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.*/

package com.kolita.arkhamcalc;

import java.util.Random;

import junit.framework.TestCase;

public class TestCalculator extends TestCase {
	private static final int NUMBER_ITERATIONS = 10000000;
	private static final double EPS = 0.001;
	
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
		assertEquals(percentageWins, new Calculator(requiredDice, requiredSuccesses, false, false).calculate(), EPS);
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
		assertEquals(percentageWins, new Calculator(requiredDice, requiredSuccesses, true, false).calculate(), EPS);
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
		assertEquals(percentageWins, new Calculator(requiredDice, requiredSuccesses, false, true).calculate(), EPS);
	}
	
	public void testCalculateNoSuccesses() {
		assertEquals(0.0, new Calculator(2, 3, false, false).calculate(), 0.0);
	}
	
	public void testCalculateChances() {
		int requiredDice = 7;
		int requiredSuccesses = 4;
		int requiredChances = 3;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			for (int j = 0; j < requiredChances; j++) {
				int totalSuccesses = 0;
				for (int k = 0; k < requiredDice; k++) {
					int dieValue = getRandomDieValue();
					if (dieValue >= 5) {
						totalSuccesses++;
					}
				}
				if (totalSuccesses >= requiredSuccesses) {
					totalWins++;
					break;
				}
			}
		}
		
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		assertEquals(percentageWins, new Calculator(requiredDice, requiredSuccesses, false, false).calculate(requiredChances), EPS);
	}
	
	public void testCalculateShotgun() {
		int requiredDice = 6;
		int requiredSuccesses = 4;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue == 6) {
					totalSuccesses += 2;
				} else if (dieValue == 5) {
					totalSuccesses += 1;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		
		Calculator calculator = new Calculator(requiredDice, requiredSuccesses, false, false);
		calculator.setIsShotgun(true);
		assertEquals(percentageWins, calculator.calculate(), EPS);
	}
	
	public void testCalculateShotgunBlessed() {
		int requiredDice = 6;
		int requiredSuccesses = 3;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue == 6) {
					totalSuccesses += 2;
				} else if (dieValue >= 4) {
					totalSuccesses += 1;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		
		Calculator calculator = new Calculator(requiredDice, requiredSuccesses, true, false);
		calculator.setIsShotgun(true);
		assertEquals(percentageWins, calculator.calculate(), EPS);
	}
	
	public void testCalculateShotgunCursed() {
		int requiredDice = 6;
		int requiredSuccesses = 3;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue == 6) {
					totalSuccesses += 2;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		
		Calculator calculator = new Calculator(requiredDice, requiredSuccesses, false, true);
		calculator.setIsShotgun(true);
		assertEquals(percentageWins, calculator.calculate(), EPS);
	}
	
	public void testCalculateShotgunHuge() {
		int requiredDice = 12;
		int requiredSuccesses = 5;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue == 6) {
					totalSuccesses += 2;
				} else if (dieValue == 5) {
					totalSuccesses += 1;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		
		Calculator calculator = new Calculator(requiredDice, requiredSuccesses, false, false);
		calculator.setIsShotgun(true);
		assertEquals(percentageWins, calculator.calculate(), EPS);
	}
	
	public void testCalculateShotgunOneSuccess() {
		Calculator shotgunCalc = new Calculator(4, 1, false, false);
		shotgunCalc.setIsShotgun(true);
		assertEquals(new Calculator(4, 1, false, false).calculate(), shotgunCalc.calculate());
	}
	
	public void testCalculateShotgunImpossible() {
		int requiredDice = 2;
		int requiredSuccesses = 5;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue == 6) {
					totalSuccesses += 2;
				} else if (dieValue == 5) {
					totalSuccesses += 1;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		
		Calculator calculator = new Calculator(requiredDice, requiredSuccesses, false, false);
		calculator.setIsShotgun(true);
		assertEquals(percentageWins, calculator.calculate(), EPS);
	}
	
	public void testCalculateShotgunAlmostImpossible() {
		int requiredDice = 3;
		int requiredSuccesses = 6;
		
		int totalWins = 0;
		for (int i = 0; i < NUMBER_ITERATIONS; i++) {
			int totalSuccesses = 0;
			for (int j = 0; j < requiredDice; j++) {
				int dieValue = getRandomDieValue();
				if (dieValue == 6) {
					totalSuccesses += 2;
				} else if (dieValue == 5) {
					totalSuccesses += 1;
				}
			}
			if (totalSuccesses >= requiredSuccesses) {
				totalWins++;
			}
		}
		double percentageWins = (double)totalWins / NUMBER_ITERATIONS;
		
		Calculator calculator = new Calculator(requiredDice, requiredSuccesses, false, false);
		calculator.setIsShotgun(true);
		assertEquals(percentageWins, calculator.calculate(), EPS);
	}
	
	private int getRandomDieValue() {
		return random.nextInt(6) + 1;
	}

}
