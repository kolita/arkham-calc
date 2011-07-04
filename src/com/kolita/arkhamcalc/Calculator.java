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
	
	public Calculator(int dice, int tough, boolean isBlessed, boolean isCursed)
	{
		mDice = dice;
		mTough = tough;
		mIsBlessed = isBlessed;
		mIsCursed = isCursed;
	}
	
	public double calculate()
	{
		double probOneSuccess = getProbOneSuccess();
		double probSuccess = 0;
		for(int i = mTough; i <= mDice; i++){
			probSuccess += getProbExactSuccess(i, probOneSuccess);
		}
		return probSuccess;
	}
	
	private double getProbExactSuccess(int exactNumberDice, double probOneSuccess)
	{
		return nCr(mDice, exactNumberDice) * Math.pow(probOneSuccess, exactNumberDice) * Math.pow(1 - probOneSuccess, mDice - exactNumberDice);
	}

	private long nCr(int n, int r)
	{
		return factorial(n) / (factorial(n - r) * factorial(r));
	}
	
	private long factorial(int n)
	{
		if(n <= 1) return 1;
		return n * factorial(n - 1);
	}

	private double getProbOneSuccess()
	{
		if(mIsBlessed){
			return (double)1 / 2;
		}
		if(mIsCursed){
			return (double)1 / 6;
		}
		return (double)1 / 3;
	}
}
