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
		double probSuccess = 0;
		for (int i = mTough; i <= mDice; i++) {
			probSuccess += getProbExactSuccess(mDice, i);
		}
		
		if (mIsMandy) {
			probSuccess += getProbMandyReroll();
		}
		
		if (mIsShotgun) {
			probSuccess += handleIsShotgun();
		}
		
		return probSuccessWithChances(probSuccess, numberOfChances);
	}
	
	private double getProbMandyReroll() {
		double probSuccessMandy = 0.0;
		//start by figuring prob of exact successes on first roll - don't count 
		//a first roll that we won, because we've already counted that in base calc
		for (int i = 0; i < mTough; i++) {
			double probFirstRoll = getProbExactSuccess(mDice, i);
			//now figure out the prob of all of the second roll successes. Similiar to the
			//base calc except we multiply by the probFirstRoll in all cases
			for (int j = mTough - i; j <= mDice - i; j++) {
				probSuccessMandy += probFirstRoll * getProbExactSuccess(mDice - i, j);
			}
		}
		return probSuccessMandy;
	}

	private double handleIsShotgun()
	{
		double probSuccessShotgun = 0.0;
		final double probSix = (double)1 / 6;
		//go from one six to either all sixes or to the toughness, whichever comes first.
		//once we get to the toughness (i.e. on a 5 to do 3, once we get to three sixes), 
		//we've already counted in the base calculation.
		for (int i = 1; i < mTough && i <= mDice; i++) {
			double exactSixes = nCr(mDice, i) * Math.pow(probSix, i) * Math.pow(1 - probSix, mDice - i);
			int remainingSuccessesRequired = mTough - 2 * i;
			for (int j = 0; i + j < mTough && i + j <= mDice; j++) {
				//j represents the number of non-sixes that are successes. Don't count sixes + successes
				//that are >= toughness - those have already been counted in base calc
				if (j >= remainingSuccessesRequired) {
					//i.e. the non-six successes (j) are enough to win. Count all the ways to roll exactly that many
					//sixes and with the remaining dice roll that many non-six successes.
					probSuccessShotgun += exactSixes * nCr(mDice - i, j) * Math.pow((getProbOneSuccess() - probSix) * 6 / 5, j) * Math.pow(1 - ((getProbOneSuccess() - probSix) * 6 / 5), mDice - i - j);
				}
			}
		}
		return probSuccessShotgun;
	}
	
	private static double probSuccessWithChances(double probSuccessOneChance, int numberOfChances)
	{
		double probFailureOneChance = 1 - probSuccessOneChance;
		double probFailureAllChances = Math.pow(probFailureOneChance, numberOfChances) ;
		return 1 - probFailureAllChances;
	}
	
	private double getProbExactSuccess(int totalDice, int exactSuccesses)
	{
		return nCr(totalDice, exactSuccesses) * Math.pow(getProbOneSuccess(), exactSuccesses) * Math.pow(1 - getProbOneSuccess(), totalDice - exactSuccesses);
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
}
