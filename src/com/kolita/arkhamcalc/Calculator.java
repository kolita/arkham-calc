package com.kolita.arkhamcalc;

public class Calculator {
	
	private int mDice;
	private int mTough;
	private boolean mIsBlessed;
	private boolean mIsCursed;
	
	public Calculator(int dice, int tough, boolean isBlessed, boolean isCursed){
		mDice = dice;
		mTough = tough;
		mIsBlessed = isBlessed;
		mIsCursed = isCursed;
	}
	
	public double calculate(){
		double probOneSuccess = getProbOneSuccess();
		double probSuccess = 0;
		for(int i = mTough; i <= mDice; i++){
			probSuccess += getProbExactSuccess(i, probOneSuccess);
		}
		return probSuccess;
	}
	
	private double getProbExactSuccess(int exactNumberDice, double probOneSuccess) {
		return nCr(mDice, exactNumberDice) * Math.pow(probOneSuccess, exactNumberDice) * Math.pow(1 - probOneSuccess, mDice - exactNumberDice);
	}

	private long nCr(int n, int r) {
		return factorial(n) / (factorial(n - r) * factorial(r));
	}
	
	private long factorial(int n){
		if(n <= 1) return 1;
		return n * factorial(n - 1);
	}

	private double getProbOneSuccess(){
		if(mIsBlessed){
			return (double)1 / 2;
		}
		if(mIsCursed){
			return (double)1 / 6;
		}
		return (double)1 / 3;
	}
	
}
