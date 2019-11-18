/*******************************************************************************
 * Copyright 2016 Texas A&M University System
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *******************************************************************************/
package org.ddukki.gameengine.dev.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Provides a {@link NumberTextField} component that accepts only Double values.
 * 
 * @author David Kim
 * @since 1.0
 * @version 0.1
 */
@Deprecated
public class DoubleTextField extends NumberTextField<Double> {

	/* DO NOT SERIALIZE */
	private static final long serialVersionUID = 1L;

	public DoubleTextField() {
		super();
	}

	/**
	 * Every time the min and max values are changed, the amount that the value
	 * can increment / decrement should also change
	 */
	private void adjustModAmount() {
		if (maxValue == null || minValue == null) {
			return;
		}

		final double range = maxValue - minValue;

		setSmallValModAmnt(range / 1000);
		setMediumValModAmnt(range / 100);
		setLargeValModAmnt(range / 10);
	}

	@Override
	protected Double getDefaultInitialValue() {
		return 0.0;
	}

	@Override
	public Double getDefaultLargeModValAmnt() {
		return 1000.0;
	}

	@Override
	public Double getDefaultMediumModValAmnt() {
		return 10.0;
	}

	@Override
	protected NumberFormat getDefaultNumberFormat() {
		return new DecimalFormat("0.0##");
	}

	@Override
	public Double getDefaultSmallModValAmnt() {
		return 0.01;
	}

	@Override
	protected Double getParsedValue() {
		final String theText = getText().trim();
		switch (theText) {
		case "Inf":
			return Double.POSITIVE_INFINITY;
		case "-Inf":
			return Double.NEGATIVE_INFINITY;
		default:

			try {
				return Double.parseDouble(getText());
			} catch (NumberFormatException nfe) {
				setText(numberFormatter.format(currentValue));
				return currentValue;
			}
		}
	}

	@Override
	public void modifyValue(final ValueChangeDirection direction,
			final Double amount) {
		final double modAmount;

		switch (direction) {
		case NEGATIVE:
			modAmount = amount.doubleValue() * -1;
			break;
		case POSITIVE:
			modAmount = amount.doubleValue();
			break;
		default:
			return;
		}
		setValue(currentValue + modAmount);
	}

	@Override
	public void setMaxValue(final Double value) {
		super.setMaxValue(value);
		adjustModAmount();
	}

	@Override
	public void setMinMaxValues(final Double min, final Double max) {
		super.setMinMaxValues(min, max);
		adjustModAmount();
	}

	@Override
	public void setMinValue(final Double value) {
		super.setMinValue(value);
		adjustModAmount();
	}

	@Override
	protected void setTextFromCurrentValue() {
		setText(numberFormatter.format(currentValue));
	}
}
/**
 * REVISION HISTORY
 * 
 * 2016-08-11, 0.1, Anish V. Abraham: Recreated.
 *
 */
