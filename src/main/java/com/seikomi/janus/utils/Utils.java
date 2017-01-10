package com.seikomi.janus.utils;

/**
 * Utilities static methods. This methods extends the Java SDK and provide advance
 * function with high reusability.
 * 
 * @author Nicolas SYMPHORIEN (nicolas.symphorien@gmail.com)
 *
 */
public final class Utils {

	private Utils() {
		// Hide the public constructor.
	}

	/**
	 * Check if the string {@code s} represent an integer of radix 10.
	 * 
	 * @param string
	 *            the string to inspect
	 * @return {@code true} the string {@code s} represent an integer of radix
	 *         10, {@code false} otherwise.
	 */
	public static boolean isInteger(String string) {
		return isInteger(string, 10);
	}

	/**
	 * Check if the string {@code s} represent an integer of radix
	 * {@code radix}.
	 * 
	 * @param string
	 *            the string to inspect
	 * @param radix
	 *            the radix to apply
	 * @return {@code true} the string {@code s} represent an integer of radix
	 *         {@code radix, {@code false} otherwise.
	 */
	public static boolean isInteger(String string, int radix) {
		boolean isInteger = true;

		if (string.isEmpty()) {
			isInteger = false;
		}

		for (int i = 0; i < string.length(); i++) {
			if (i == 0 && string.charAt(i) == '-') {
				if (string.length() == 1) {
					isInteger = false;
				} else {
					continue;
				}
			}
			if (Character.digit(string.charAt(i), radix) < 0) {
				isInteger = false;
			}
		}

		return isInteger;
	}

}
