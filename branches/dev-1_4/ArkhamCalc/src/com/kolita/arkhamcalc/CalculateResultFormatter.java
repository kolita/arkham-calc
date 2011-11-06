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

import java.text.NumberFormat;

public class CalculateResultFormatter
{
	private static final int COLOR_GREEN = 0xFF008000;
	private static final int COLOR_YELLOW = 0xFFC4C100;
	private static final int COLOR_RED = 0xFF800000;
	
	private double mResult;
	
	public CalculateResultFormatter(double result)
	{
		mResult = result;
	}
	
	public String getResultString()
	{
		NumberFormat numberFormat= NumberFormat.getPercentInstance();
		numberFormat.setMaximumFractionDigits(1);
		return numberFormat.format(mResult);
	}
	
	public int getColor()
	{
		if (mResult > .66) {
			return COLOR_GREEN;
		}
		if (mResult > .33) {
			return COLOR_YELLOW;
		}
		return COLOR_RED;
	}
}
