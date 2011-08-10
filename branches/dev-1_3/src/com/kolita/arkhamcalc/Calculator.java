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

public class Calculator
{
	private int mDice;
	private int mTough;
	private boolean mIsBlessed;
	private boolean mIsCursed;
	private boolean mIsShotgun;
	private boolean mIsMandy;
	
	public boolean getIsShotgun()
	{
		return mIsShotgun;
	}
	
	public void setIsShotgun(boolean value)
	{
		mIsShotgun = value;
	}
	
	public boolean getIsMandy()
	{
		return mIsMandy;
	}
	
	public void setIsMandy(boolean value)
	{
		mIsMandy = value;
	}
	
	public Calculator(int dice, int tough, boolean isBlessed, boolean isCursed)
	{
		mDice = dice;
		mTough = tough;
		mIsBlessed = isBlessed;
		mIsCursed = isCursed;
	}
	
	public double calculate()
	{
		return calculate(1);
	}
	
	public double calculate(int numberOfChances)
	{
		double probSuccess = 0.0;
		
		probSuccess += baseCalc(mDice, mTough);
		
		double probMandySuccess = 0.0;
		if (mIsMandy) {
			probMandySuccess = getProbMandyReroll();
		}
		
		return probSuccessWithChances(probSuccess, probMandySuccess, numberOfChances);
	}
	
	private double baseCalc(int totalDice, int totalTougnness)
	{
		double probSuccess = 0.0;
		
		for (int i = totalTougnness; i <= totalDice; i++) {
			probSuccess += getProbExactSuccess(totalDice, i, getProbOneSuccess());
		}
		if (mIsShotgun) {
			probSuccess += handleIsShotgun(totalDice, totalTougnness);
		}
		
		return probSuccess;
	}
	
	private double getProbMandyReroll() {
		double probSuccessMandy = 0.0;
		for (int sixSuccesses = 0; sixSuccesses < mTough; sixSuccesses++) {
			double probExactSixes = getProbExactSuccess(mDice, sixSuccesses, getProbSix());
			int sixSuccessValue = mIsShotgun ? 2 * sixSuccesses : sixSuccesses;
			
			for (int nonSixSuccesses = 0; nonSixSuccesses + sixSuccessValue < mTough; nonSixSuccesses++) {
				double probFirstRoll = probExactSixes * getProbExactSuccess(mDice - sixSuccesses, nonSixSuccesses, getProbSuccessWithoutSix());
				probSuccessMandy += probFirstRoll * baseCalc(mDice - sixSuccesses - nonSixSuccesses, mTough - sixSuccessValue - nonSixSuccesses);
			}
		}
		return probSuccessMandy;
	}

	private double handleIsShotgun(int totalDice, int totalToughness)
	{
		double probSuccessShotgun = 0.0;
		//go from one six to either all sixes or to the toughness, whichever comes first.
		//once we get to the toughness (i.e. on a 5 to do 3, once we get to three sixes), 
		//we've already counted in the base calculation.
		for (int i = 1; i < totalToughness && i <= totalDice; i++) {
			double exactSixes = getProbExactSuccess(totalDice, i, getProbSix());
			int remainingSuccessesRequired = totalToughness - 2 * i;
			for (int j = 0; i + j < totalToughness && i + j <= totalDice; j++) {
				//j represents the number of non-sixes that are successes. Don't count sixes + successes
				//that are >= toughness - those have already been counted in base calc
				if (j >= remainingSuccessesRequired) {
					//i.e. the non-six successes (j) are enough to win. Count all the ways to roll exactly that many
					//sixes and with the remaining dice roll that many non-six successes.
					probSuccessShotgun += exactSixes * getProbExactSuccess(totalDice - i, j, getProbSuccessWithoutSix());
				}
			}
		}
		return probSuccessShotgun;
	}
	
	private double getProbSix()
	{
		return (double)1 / 6;
	}
	
	private double getProbSuccessWithoutSix()
	{
		return (getProbOneSuccess() - getProbSix()) * 6 / 5;
	}
	
	private double getProbOneSuccess()
	{
		if (mIsBlessed){
			return (double)1 / 2;
		}
		if (mIsCursed){
			return (double)1 / 6;
		}
		return (double)1 / 3;
	}
	
	private static double probSuccessWithChances(double probSuccessOneChance, double probMandySuccess, int numberOfChances)
	{
		//mandy can only be used once - if you have any other chances, they won't include Mandy
		double probFailureFirstChance = 1 - probSuccessOneChance - probMandySuccess;
		double probFailureOtherChances = 1 - probSuccessOneChance;
		
		//note, numberOfChances always > 0, the exponent will never be < 0
		double probFailureAllChances = probFailureFirstChance * Math.pow(probFailureOtherChances, numberOfChances - 1);
		return 1 - probFailureAllChances;
	}
	
	private static double getProbExactSuccess(int totalDice, int exactSuccesses, double probOneSuccess)
	{
		return nCr(totalDice, exactSuccesses) * Math.pow(probOneSuccess, exactSuccesses) * Math.pow(1 - probOneSuccess, totalDice - exactSuccesses);
	}

	private static long nCr(int n, int r)
	{
		return factorial(n) / (factorial(n - r) * factorial(r));
	}
	
	private static long factorial(int n)
	{
		if (n <= 1) return 1;
		return n * factorial(n - 1);
	}
}
