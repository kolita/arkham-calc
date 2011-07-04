package com.kolita.arkhamcalc;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;

public class ArkhamCalc extends Activity {
	private static final int DICE_MAX = 16;
	private static final int TOUGH_MAX = 5;
	
	private static final int COLOR_GREEN = 0xFF008000;
	private static final int COLOR_YELLOW = 0xFFC4C100;
	private static final int COLOR_RED = 0xFF800000;
	
	private SeekBar mDiceSeekBar;
	private TextView mDiceValue;
	private SeekBar mToughSeekBar;
	private TextView mToughValue;
	private CheckBox mBlessCheckBox;
	private CheckBox mCurseCheckBox;
	private TextView mResultTextView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //find controls
    	mDiceSeekBar = (SeekBar) findViewById(R.id.diceSeekBar);
    	mDiceValue = (TextView) findViewById(R.id.diceValue);
    	mToughSeekBar = (SeekBar) findViewById(R.id.toughSeekBar);
    	mToughValue = (TextView) findViewById(R.id.toughValue);
    	mBlessCheckBox = (CheckBox) findViewById(R.id.blessCheckBox);
    	mCurseCheckBox = (CheckBox) findViewById(R.id.curseCheckBox);
    	mResultTextView = (TextView) findViewById(R.id.resultTextView);
        
    	//setup controls
    	mDiceSeekBar.setMax(DICE_MAX - 1);
    	mToughSeekBar.setMax(TOUGH_MAX - 1);
    	
    	//attach callbacks
    	mDiceSeekBar.setOnSeekBarChangeListener(new OnSeekBarProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				setSeekBarValues();
				recalculate();
			}
		});
    	mToughSeekBar.setOnSeekBarChangeListener(new OnSeekBarProgressChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				setSeekBarValues();
				recalculate();
			}
		});
    	mBlessCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mCurseCheckBox.setChecked(false);
				}
				recalculate();
				
			}
		});
    	mCurseCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					mBlessCheckBox.setChecked(false);
				}
				recalculate();
				
			}
		});
    	
    	//restore state (if saved)
    	if(savedInstanceState != null){
    		mDiceSeekBar.setProgress(savedInstanceState.getInt("dice"));
    		mToughSeekBar.setProgress(savedInstanceState.getInt("tough"));
    		mBlessCheckBox.setChecked(savedInstanceState.getBoolean("isBlessed"));
    		mCurseCheckBox.setChecked(savedInstanceState.getBoolean("isCursed"));
    	}
    	
    	//first calculation
    	setSeekBarValues();
		recalculate();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState){
    	super.onSaveInstanceState(outState);
    	
    	//save state
    	outState.putInt("dice", mDiceSeekBar.getProgress());
    	outState.putInt("tough", mToughSeekBar.getProgress());
    	outState.putBoolean("isBlessed", mBlessCheckBox.isChecked());
    	outState.putBoolean("isCursed", mCurseCheckBox.isChecked());
    }

	private void recalculate() {
		//get input
		int dice = mDiceSeekBar.getProgress() + 1;
		int tough = mToughSeekBar.getProgress() + 1;
		boolean isBlessed = mBlessCheckBox.isChecked();
		boolean isCursed = mCurseCheckBox.isChecked();
		
		//calculate
		Calculator calculator = new Calculator(dice, tough, isBlessed, isCursed);
		double result = calculator.calculate();
		
		//set output
		NumberFormat numberFormat= NumberFormat.getPercentInstance();
		numberFormat.setMaximumFractionDigits(1);
		String resultString =  numberFormat.format(result);
		mResultTextView.setText(resultString);
		
		//Color logic
		//TODO: refactor
		int color;
		if(result > .66){
			color = COLOR_GREEN;
		}
		else if(result > .33){
			color = COLOR_YELLOW;
		}
		else{
			color = COLOR_RED;
		}
		mResultTextView.setTextColor(color);
	}

	private void setSeekBarValues() {
		mDiceValue.setText(Integer.toString(mDiceSeekBar.getProgress() + 1));
		mToughValue.setText(Integer.toString(mToughSeekBar.getProgress() + 1));
	}
}