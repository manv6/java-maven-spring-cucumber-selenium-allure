package com.workable.hackathon.helpers.drivers.browsers;

/**
 * Enum for browser types supported by this tool
 */
public enum Browsers {

    FIREFOX,
    CHROME,
    SAFARI,
    EDGE,
    OPERA,
    REMOTE;

    /**
     * Returns the enum value based on String input.
     *
     * @param browser string with browser value with ignored case.
     * @return the enum value based on String input.
     * @throws IllegalArgumentException and prints the invalid browser input given
     */
    public static Browsers getBrowserForName(String browser) throws IllegalArgumentException {
        for (Browsers b : values()) {
            if (b.toString().equalsIgnoreCase(browser)) {
                return b;
            }
        }
        throw new IllegalArgumentException(("Invalid browser [" + browser + "]"));
    }

}
