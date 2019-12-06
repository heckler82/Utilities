package com.foley.util;

/**
 * Provides various utility methods for formatting data
 *
 * @author Evan Foley
 * @version 04 Dec 2019
 */
public class Formatting {
    /**
     * Parses an array of strings to an array of integers
     *
     * @param arr The string array
     * @return A new array of integers
     */
    public static int[] convertToIntArray(String[] arr) {
        int[] newArray = new int[arr.length];
        for(int i = 0; i < arr.length;i++) {
            newArray[i] = Integer.parseInt(arr[i]);
        }
        return newArray;
    }
}
