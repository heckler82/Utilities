package com.foley.util;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Basic template for advent of code problems
 *
 * @author Evan Foley
 * @version 19 Jan 2019
 */
public abstract class AdventMaster {
    protected Scanner scanner;

    /**
     * Creates a new advent master
     *
     * @param fileName the name of the input file
     */
    public AdventMaster(String fileName) {
        if(fileName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }
        InputStream is = AdventMaster.class.getResourceAsStream(fileName);
        scanner = new Scanner(is);
    }

    /**
     * Parses the input file
     */
    protected abstract void parseFile();
}
