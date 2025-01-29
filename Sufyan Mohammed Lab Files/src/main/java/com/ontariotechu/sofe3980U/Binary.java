package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary {
    private final String number;

    /**
     * Constructor that initializes a Binary object.
     *
     * @param number A string containing only '0' or '1'. If invalid, defaults to "0".
     */
    public Binary(String number) {
        if (number == null || !number.matches("[01]+")) {
            this.number = "0";
        } else {
            this.number = number.replaceFirst("^0+(?!$)", ""); // Remove leading zeros
        }
    }

    /**
     * Returns the binary value as a string.
     *
     * @return Binary number as a string.
     */
    public String getValue() {
        return this.number;
    }

    /**
     * Adds two binary numbers.
     *
     * @return A new Binary object representing the sum.
     */
    public static Binary add(Binary num1, Binary num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0, i = num1.number.length() - 1, j = num2.number.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) sum += num1.number.charAt(i--) - '0';
            if (j >= 0) sum += num2.number.charAt(j--) - '0';
            result.append(sum % 2);
            carry = sum / 2;
        }

        return new Binary(result.reverse().toString());
    }

    /**
     * Performs a bitwise OR operation.
     *
     * @return A new Binary object representing the result.
     */
    public static Binary or(Binary num1, Binary num2) {
        return bitwiseOperation(num1, num2, '|');
    }

    /**
     * Performs a bitwise AND operation.
     *
     * @return A new Binary object representing the result.
     */
    public static Binary and(Binary num1, Binary num2) {
        return bitwiseOperation(num1, num2, '&');
    }

    /**
     * Multiplies two binary numbers.
     *
     * @return A new Binary object representing the product.
     */
    public static Binary multiply(Binary num1, Binary num2) {
        Binary result = new Binary("0");
        for (int i = num2.number.length() - 1; i >= 0; i--) {
            if (num2.number.charAt(i) == '1') {
                Binary shifted = shiftLeft(num1, num2.number.length() - 1 - i);
                result = add(result, shifted);
            }
        }
        return result;
    }

    /**
     * Shifts a binary number left by a given amount.
     *
     * @return A new Binary object representing the shifted value.
     */
    public static Binary shiftLeft(Binary num, int times) {
        return new Binary(num.number + "0".repeat(times));
    }

    /**
     * Helper method for bitwise operations (AND/OR).
     */
    private static Binary bitwiseOperation(Binary num1, Binary num2, char op) {
        StringBuilder result = new StringBuilder();
        int i = num1.number.length() - 1, j = num2.number.length() - 1;

        while (i >= 0 || j >= 0) {
            char bit1 = (i >= 0) ? num1.number.charAt(i--) : '0';
            char bit2 = (j >= 0) ? num2.number.charAt(j--) : '0';
            result.append((op == '|') ? (bit1 | bit2) - '0' : (bit1 & bit2) - '0');
        }

        return new Binary(result.reverse().toString());
    }
}
