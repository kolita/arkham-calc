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

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The main activity. Routes user input to Calculator and prints its results.
 */
public class ArkhamCalc extends Activity
{
    private static final int DICE_MAX = 16;
    private static final int TOUGH_MAX = 6;
    private static final int CHANCE_MAX = 5;

    private SeekBar mDiceSeekBar;
    private TextView mDiceValue;
    private SeekBar mToughSeekBar;
    private TextView mToughValue;
    private SeekBar mChanceSeekBar;
    private TextView mChanceValue;
    private CheckBox mBlessCheckBox;
    private CheckBox mCurseCheckBox;
    private CheckBox mShotgunCheckBox;
    private CheckBox mMandyCheckBox;
    private CheckBox mRerollOnesCheckBox;
    private CheckBox mAddOneCheckBox;
    private TextView mResultTextView;

    private int mPreviousChanceValue;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        //find controls
        mDiceSeekBar = (SeekBar) findViewById(R.id.diceSeekBar);
        mDiceValue = (TextView) findViewById(R.id.diceValue);
        mToughSeekBar = (SeekBar) findViewById(R.id.toughSeekBar);
        mToughValue = (TextView) findViewById(R.id.toughValue);
        mChanceSeekBar = (SeekBar) findViewById(R.id.chanceSeekBar);
        mChanceValue = (TextView) findViewById(R.id.chanceValue);
        mBlessCheckBox = (CheckBox) findViewById(R.id.blessCheckBox);
        mCurseCheckBox = (CheckBox) findViewById(R.id.curseCheckBox);
        mShotgunCheckBox = (CheckBox) findViewById(R.id.shotgunCheckBox);
        mMandyCheckBox = (CheckBox) findViewById(R.id.mandyCheckBox);
        mRerollOnesCheckBox = (CheckBox) findViewById(R.id.rerollOnesCheckBox);
        mAddOneCheckBox = (CheckBox) findViewById(R.id.addOneCheckBox);
        mResultTextView = (TextView) findViewById(R.id.resultTextView);

        //setup controls
        mDiceSeekBar.setMax(DICE_MAX - 1);
        mToughSeekBar.setMax(TOUGH_MAX - 1);
        mChanceSeekBar.setMax(CHANCE_MAX - 1);

        //attach callbacks
        mDiceSeekBar.setOnSeekBarChangeListener(new OnSeekBarProgressChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekBarValues();
                recalculate();
            }
        });
        mToughSeekBar.setOnSeekBarChangeListener(new OnSeekBarProgressChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekBarValues();
                recalculate();
            }
        });
        mChanceSeekBar.setOnSeekBarChangeListener(new OnSeekBarProgressChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekBarValues();
                recalculate();

                mPreviousChanceValue = getPreviousProgress();
                handleOneTimeAbilityChancesChanged(mMandyCheckBox.isChecked(), R.string.mandy_chances_toast);
                handleOneTimeAbilityChancesChanged(mRerollOnesCheckBox.isChecked(), R.string.reroll_ones_chances_toast);
            }
        });
        mBlessCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //can't be cursed and blessed at the same time
                    mCurseCheckBox.setChecked(false);
                }
                recalculate();

            }
        });
        mCurseCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //can't be cursed and blessed at the same time
                    mBlessCheckBox.setChecked(false);
                }
                recalculate();

            }
        });
        mShotgunCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                recalculate();
            }
        });
        mMandyCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //both Mandy and Reroll ones on at same time not supported
                    mRerollOnesCheckBox.setChecked(false);
                }
                recalculate();
                handleOneTimeAbilityOptionChanged(mMandyCheckBox.isChecked(), R.string.mandy_chances_toast);
            }
        });
        mRerollOnesCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //both Mandy and Reroll ones on at same time not supported
                    mMandyCheckBox.setChecked(false);
                }
                recalculate();
                handleOneTimeAbilityOptionChanged(mRerollOnesCheckBox.isChecked(), R.string.reroll_ones_chances_toast);
            }
        });
        mAddOneCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                recalculate();
            }
        });    	

        //restore state (if saved)
        if(savedInstanceState != null){
            mDiceSeekBar.setProgress(savedInstanceState.getInt("dice"));
            mToughSeekBar.setProgress(savedInstanceState.getInt("tough"));
            mChanceSeekBar.setProgress(savedInstanceState.getInt("chance"));
            mBlessCheckBox.setChecked(savedInstanceState.getBoolean("isBlessed"));
            mCurseCheckBox.setChecked(savedInstanceState.getBoolean("isCursed"));
            mShotgunCheckBox.setChecked(savedInstanceState.getBoolean("isShotgun"));
            mMandyCheckBox.setChecked(savedInstanceState.getBoolean("isMandy"));
            mRerollOnesCheckBox.setChecked(savedInstanceState.getBoolean("isRerollOnes"));
            mAddOneCheckBox.setChecked(savedInstanceState.getBoolean("isAddOne"));
        }

        //first calculation
        setSeekBarValues();
        recalculate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        //save state
        outState.putInt("dice", mDiceSeekBar.getProgress());
        outState.putInt("tough", mToughSeekBar.getProgress());
        outState.putInt("chance", mChanceSeekBar.getProgress());
        outState.putBoolean("isBlessed", mBlessCheckBox.isChecked());
        outState.putBoolean("isCursed", mCurseCheckBox.isChecked());
        outState.putBoolean("isShotgun", mShotgunCheckBox.isChecked());
        outState.putBoolean("isMandy", mMandyCheckBox.isChecked());
        outState.putBoolean("isRerollOnes", mRerollOnesCheckBox.isChecked());
        outState.putBoolean("isAddOne", mAddOneCheckBox.isChecked());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_item_feedback:
                sendFeedbackEmail();
                return true;
            case R.id.menu_item_help:
                startActivity(new Intent(this, ArkhamCalcHelp.class));
                return true;
        }
        return false;
    }

    private void sendFeedbackEmail()
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { getResourceString(R.string.email_to) });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResourceString(R.string.email_subject));
        emailIntent.setType("plain/text");
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            showToast(getResourceString(R.string.toast_exception_email));
        }
    }

    private void recalculate()
    {
        //get input
        int dice = mDiceSeekBar.getProgress() + 1;
        int tough = mToughSeekBar.getProgress() + 1;
        int chance = mChanceSeekBar.getProgress() + 1;
        boolean isBlessed = mBlessCheckBox.isChecked();
        boolean isCursed = mCurseCheckBox.isChecked();
        boolean isShotgun = mShotgunCheckBox.isChecked();
        boolean isMandy = mMandyCheckBox.isChecked();
        boolean isRerollOnes = mRerollOnesCheckBox.isChecked();
        boolean isAddOne = mAddOneCheckBox.isChecked();

        //calculate
        Calculator calculator = new Calculator(dice, tough, isBlessed, isCursed);
        calculator.setIsShotgun(isShotgun);
        calculator.setIsMandy(isMandy);
        calculator.setIsRerollOnes(isRerollOnes);
        calculator.setIsAddOne(isAddOne);
        double result = calculator.calculate(chance);

        //format and set ui
        CalculateResultFormatter formatter = new CalculateResultFormatter(result);

        String resultString = formatter.getResultString();
        mResultTextView.setText(resultString);

        int color = formatter.getColor();
        mResultTextView.setTextColor(color);
    }

    private void setSeekBarValues()
    {
        mDiceValue.setText(Integer.toString(mDiceSeekBar.getProgress() + 1));
        mToughValue.setText(Integer.toString(mToughSeekBar.getProgress() + 1));
        mChanceValue.setText(Integer.toString(mChanceSeekBar.getProgress() + 1));
    }

    /**
     * Show a message to the user regarding this one-time ability if user has more than one chance.
     * Case where ability has been changed; check number of chances.
     */
    private void handleOneTimeAbilityOptionChanged(boolean isAbilityChecked, int resourceStringId)
    {
        if (isAbilityChecked && mChanceSeekBar.getProgress() > 0) {
            showToast(getResourceString(resourceStringId));
        }
    }

    /**
     * Show a message to the user regarding this one-time ability if user has more than one chance.
     * Case where number of chances have changed; check if ability is selected and chances have changed
     * from one to > 1.
     */
    private void handleOneTimeAbilityChancesChanged(boolean isAbilityChecked, int resourceStringId)
    {
        if (isAbilityChecked && mPreviousChanceValue <= 0 && mChanceSeekBar.getProgress() > 0) {
            showToast(getResourceString(resourceStringId));
        }
    }

    private void showToast(String toastText)
    {
        Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_LONG).show();
    }

    private String getResourceString(int resourceStringId)
    {
        return getResources().getString(resourceStringId);
    }

    private abstract class OnSeekBarProgressChangeListener implements OnSeekBarChangeListener
    {
        private int mPreviousProgress;
        public int getPreviousProgress(){
            return mPreviousProgress;
        }

        @Override
        public abstract void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser);

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mPreviousProgress = seekBar.getProgress();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
