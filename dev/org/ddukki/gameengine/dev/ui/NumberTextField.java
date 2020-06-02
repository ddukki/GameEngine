package org.ddukki.gameengine.dev.ui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JTextField;

public abstract class NumberTextField<T extends Number & Comparable<T>> extends
		JTextField implements ActionListener, FocusListener, KeyListener {

	/**
	 * Simple indicator for which way the value of the field should change.
	 * Useful when field values are changing through UI callbacks.
	 */
	public enum ValueChangeDirection {
			POSITIVE, NEGATIVE;
	}

	/** Don't serialize */
	private static final long serialVersionUID = 1L;

	protected static final int DEFAULT_COLUMNS = 10;

	/** Will be used to parse strings to numbers and back. */
	protected final NumberFormat numberFormatter;

	/**
	 * Amounts by which the current value needs to be modified by the keyboard
	 * shortcuts. Should never be null. Initialized in the constructor.
	 * 
	 * @see #getDefaultSmallModValAmnt()
	 */
	protected T smallValModAmnt;

	/**
	 * @see #smallValModAmnt
	 * @see #getDefaultMediumModValAmnt()
	 */
	protected T mediumValModAmnt;

	/**
	 * @see #smallValModAmnt
	 * @see #getDefaultLargeModValAmnt()
	 */
	protected T largeValModAmnt;

	/**
	 * Should never be <code>null</code>. Child classes will be forced to
	 * implement {@link #getDefaultInitialValue()} to initialize this field
	 * during construction.
	 */
	protected T currentValue;

	protected T maxValue;

	protected T minValue;

	/** Standard cyclic-update guard. */
	protected boolean isUpdating = false;

	/**
	 * Adds itself as an action and focus listener, updates the component
	 * displays to reflect the current value, and sets the default current, max,
	 * and min values.
	 */
	public NumberTextField() {
		super(null, null, DEFAULT_COLUMNS);
		smallValModAmnt = getDefaultSmallModValAmnt();
		mediumValModAmnt = getDefaultMediumModValAmnt();
		largeValModAmnt = getDefaultLargeModValAmnt();

		addFocusListener(this);
		addActionListener(this);
		addKeyListener(this);
		numberFormatter = getDefaultNumberFormat();
		currentValue = getDefaultInitialValue();
		setTextFromCurrentValue();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		setCurrentValueFromText();
	}

	@Override
	public void focusGained(final FocusEvent e) {
		selectAll();
	}

	// Getters & Setters -------------------------------------------------------

	@Override
	public void focusLost(final FocusEvent e) {
		setCurrentValueFromText();
	}

	/**
	 * Ensures that the current value is within the min / max bounds. Meant to
	 * be called when the min / max values are being changed.
	 */
	protected void forceValueBounds() {
		setValue(currentValue);
	}

	/**
	 * Requires child classes to provide a valid default initial value for the
	 * field so the field is never in an invalid state. Implementing classes
	 * need to ensure that they DO NOT return {@link Null}
	 */
	protected abstract T getDefaultInitialValue();

	abstract public T getDefaultLargeModValAmnt();

	abstract public T getDefaultMediumModValAmnt();

	/**
	 * @return Gets the default number formatter from the child classes. This
	 *         formatter will be used in the parsing of strings to number values
	 *         and vice-versa.
	 */
	protected abstract NumberFormat getDefaultNumberFormat();

	/**
	 * Provide the default value modification ranges for the keyboard shortcuts.
	 */
	abstract public T getDefaultSmallModValAmnt();

	public T getMaxValue() {
		return maxValue;
	}

	public T getMinValue() {
		return minValue;
	}

	/**
	 * Reads the current text of the field and returns the parsed number value
	 * associated with it.
	 */
	protected abstract T getParsedValue();

	public T getValue() {
		return currentValue;
	}

	/**
	 * @return A value that is closes to the min/max bounds if the provided
	 *         value is outside the bounds, otherwise the provided value itself.
	 */
	public T getValueWithinBounds(final T value) {
		if (minValue != null && value.compareTo(minValue) < 0)
			return minValue;
		if (maxValue != null && value.compareTo(maxValue) > 0)
			return maxValue;
		return value;
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		// If the event queue is currently busy, don't dispatch events.
		if (Toolkit.getDefaultToolkit()
				.getSystemEventQueue()
				.peekEvent() != null)
			return;

		if (e.getSource() != this)
			return;

		ValueChangeDirection direction = null;
		T amount = null;

		// Get direction from key
		final int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_KP_UP)
			direction = ValueChangeDirection.POSITIVE;
		else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_KP_DOWN)
			direction = ValueChangeDirection.NEGATIVE;
		else
			return;

		// Get amount from modifiers
		final int modifiers = e.getModifiersEx();
		final int shiftDownMask = KeyEvent.SHIFT_DOWN_MASK;
		final int ctrlDownMask = KeyEvent.CTRL_DOWN_MASK;
		final int ctrlShiftDownMask = shiftDownMask | ctrlDownMask;
		final boolean shiftDownOnly;
		shiftDownOnly = (modifiers == shiftDownMask);
		final boolean ctrlShiftDown;
		ctrlShiftDown = (modifiers == ctrlShiftDownMask);
		if (modifiers == 0)
			amount = smallValModAmnt;
		else if (shiftDownOnly)
			amount = mediumValModAmnt;
		else if (ctrlShiftDown)
			amount = largeValModAmnt;

		if (direction != null && amount != null) {
			modifyValue(direction, amount);
			fireActionPerformed();
			requestFocus();
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
	}

	@Override
	public void keyTyped(final KeyEvent e) {
	}

	/**
	 * Modify the current value by the specified amount. Implementing classes
	 * should use the {@link #setValue(Number)} method to ensure that values are
	 * properly clamped within their bounds.
	 * 
	 */
	public abstract void modifyValue(ValueChangeDirection direction, T amount);

	/**
	 * Reads the text value from the field and sets it as the current value if
	 * it parses correctly; otherwise resets the field to the currently valid
	 * value.
	 */
	protected void setCurrentValueFromText() {
		final T parsedValue = getParsedValue();
		if (parsedValue == null) {
			setTextFromCurrentValue();
			requestFocus();
			return;
		}
		setValue(parsedValue);
	}

	public void setLargeValModAmnt(final T amount) {
		if (amount != null)
			largeValModAmnt = amount;
	}

	/**
	 * Sets the new max value after ensuring that it is not <code>null</code>
	 * and is greater than the current min value, if one is specified.
	 */
	public void setMaxValue(final T value) {
		if (value != null
				&& (minValue == null || value.compareTo(minValue) >= 0)) {
			maxValue = value;
			forceValueBounds();
		}
	}

	public void setMediumValModAmnt(final T amount) {
		if (amount != null)
			mediumValModAmnt = amount;
	}

	/**
	 * Sometimes setting the min and max values consecutively can be problematic
	 * depending on the existing old values. E.g. if the current min value is 1
	 * and the max value is 10, and the user wants to set the new min value to
	 * 100 and intend to set the next max value to 500. Since the new min value
	 * is greater than the current max value, it will not be set. This method
	 * provides a way of validly setting both simultaneously.
	 * 
	 * @param min
	 * @param max
	 */
	public void setMinMaxValues(final T min, final T max) {
		if (min != null && max != null && (min.compareTo(max) <= 0)) {
			minValue = min;
			maxValue = max;
			forceValueBounds();
		} else {
			// If there is anything wrong in the min / max value changes,
			// Don't follow through
			return;
		}
	}

	/**
	 * Sets the new min value after ensuring that it is not <code>null</code>
	 * and is less than the current max value, if one is specified.
	 */
	public void setMinValue(final T value) {
		if (value != null
				&& (maxValue == null || value.compareTo(maxValue) < 0)) {
			minValue = value;
			forceValueBounds();
		}
	}

	public void setSmallValModAmnt(final T amount) {
		if (amount != null)
			smallValModAmnt = amount;
	}

	@Override
	public void setText(final String t) {
		super.setText(t);

		if (!isUpdating) {
			isUpdating = true;
			setCurrentValueFromText();
			isUpdating = false;
		}
	}

	protected abstract void setTextFromCurrentValue();

	/**
	 * Sets the current value of the field to the provided value. If the
	 * provided value is outside the current min/max bounds, it is clamped to
	 * the nearest bound before being set. Fires a value change event only if
	 * the value was actually changed.
	 * 
	 * @return <code>true</code> if the provided value is different from the
	 *         current value and the current value was changed.
	 */
	public boolean setValue(final T value) {
		final T boundedValue = getValueWithinBounds(value);
		if (!currentValue.equals(boundedValue)) {
			currentValue = getValueWithinBounds(value);
		}

		// Set after the block to allow any reformatting as in the case of
		// percentages to happen even if the underlying value hasn't changed and
		// someone removed the percentage sign.
		if (!isUpdating)
			setTextFromCurrentValue();
		return value.equals(currentValue);
	}
}