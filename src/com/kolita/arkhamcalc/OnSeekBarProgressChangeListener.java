package com.kolita.arkhamcalc;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public abstract class OnSeekBarProgressChangeListener implements OnSeekBarChangeListener {
	@Override
	public abstract void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser);

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}
