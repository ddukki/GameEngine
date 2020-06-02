package org.ddukki.gameengine.dev.ui;

import java.text.NumberFormat;
import java.text.ParseException;

/** */
public class IntegerTextField extends NumberTextField<Integer> {

	private static final long serialVersionUID = 1L;

	public IntegerTextField() {
		super();
	}

	@Override
	protected Integer getDefaultInitialValue() {
		return 0;
	}

	@Override
	public Integer getDefaultLargeModValAmnt() {
		return 100;
	}

	@Override
	public Integer getDefaultMediumModValAmnt() {
		return 10;
	}

	@Override
	protected NumberFormat getDefaultNumberFormat() {
		final NumberFormat f = NumberFormat.getIntegerInstance();
		f.setGroupingUsed(false);
		return f;
	}

	@Override
	public Integer getDefaultSmallModValAmnt() {
		return 1;
	}

	@Override
	protected Integer getParsedValue() {
		try {
			final String text = getText();
			if (text == null)
				return null;

			final Number enteredValue = numberFormatter.parse(text);
			if (enteredValue instanceof Long) {
				if (enteredValue.longValue() > Integer.MAX_VALUE
						|| enteredValue.longValue() < Integer.MIN_VALUE) {
					return null;
				}
				return enteredValue.intValue();
			} else {
				return null;
			}
		} catch (final ParseException e) {
			return null;
		}
	}

	@Override
	public void modifyValue(final ValueChangeDirection direction,
			final Integer amount) {
		final int modAmount;
		switch (direction) {
		case NEGATIVE:
			modAmount = amount.intValue() * -1;
			break;
		case POSITIVE:
			modAmount = amount.intValue();
			break;
		default:
			return;
		}
		final int currentValue = this.currentValue.intValue();
		setValue(currentValue + modAmount);
	}

	@Override
	protected void setTextFromCurrentValue() {
		setText(numberFormatter.format(currentValue));
	}
}

/**
 * REVISION HISTORY:
 * 
 * 2016-08-11, 0.1, Anish V. Abraham: Recreated.
 *
 */
