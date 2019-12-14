package com.foley.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Instructions for an int code computer
 *
 * @author Evan Foley
 * @version 13 Dec 2019
 */
public class IntCode {
    // Program variables
    private int instrPtr;
    private int currInstr;

    // Data handlers
    private int opcode;
    private int mode1;
    private int mode2;
    private int param1;
    private int param2;
    private int store;

    // Program data
    private int memoryOut;
    private int memoryIn;
    private int[] memory;

    /**
     * Creates a new int code computer
     *
     * @param str The input string of data
     */
    public IntCode(String str, String delimeter) {
        this(str, delimeter, str.length());
    }

    /**
     * Creates a new int code computer
     *
     * @param str The input string of data
     * @param length The length of the program memory
     */
    public IntCode(String str, String delimeter, int length) {
        this(str.split(delimeter), length);
    }

    /**
     * Creates a new int code computer
     *
     * @param arr The input array
     */
    public IntCode(String[] arr) {
        this(arr, arr.length);
    }

    /**
     * Creates a new int code computer
     *
     * @param arr The input array
     * @param length The length of the program memory
     */
    public IntCode(String[] arr, int length) {
        this(Formatting.convertToIntArray(arr), length);
    }

    /**
     * Creates a new int code computer
     *
     * @param arr The input array
     */
    public IntCode(int[] arr) {
        this(arr, arr.length);
    }

    /**
     * Creates a new int code computer
     *
     * @param arr The input array
     * @param length The length of the program memory
     */
    public IntCode(int[] arr, int length) {
        // Ensure proper length was given
        if(length < arr.length || length < 1) {
            throw new IllegalArgumentException("Memory length cannot be less than the input array length, or less than 1");
        }
        // Load program into memory
        memory = new int[length];
        for(int i = 0; i < arr.length; i++) {
            memory[i] = arr[i];
        }
        instrPtr = 0;
    }

    /**
     * Gets the current opcode
     *
     * @return The current opcode
     */
    public int getCurrentOpcode() {
        return memory[instrPtr] % 100;
    }

    /**
     * Runs the program until it halts
     */
    public void run() {
        // Run until the halting opcode is reached
        while(opcode != 99) {
            cycle();
        }
    }

    /**
     * Runs one iteration of the instruction set
     */
    public void cycle() {
        // Prepare parameters
        currInstr = memory[instrPtr];
        opcode = currInstr % 100;
        mode1 = currInstr / 100 % 10;
        mode2 = currInstr / 1000 % 10;
        param1 = memory[instrPtr + 1];

        // Perform the specified opcode action
        switch (opcode) {
            case 1: // Add
                param2 = memory[instrPtr + 2];
                store = memory[instrPtr + 3];
                memory[store] = (mode1 == 1 ? param1 : memory[param1]) + (mode2 == 1 ? param2 : memory[param2]);
                instrPtr += 4;
                break;
            case 2: // Multiply
                param2 = memory[instrPtr + 2];
                store = memory[instrPtr + 3];
                memory[store] = (mode1 == 1 ? param1 : memory[param1]) * (mode2 == 1 ? param2 : memory[param2]);
                instrPtr += 4;
                break;
            case 3: // Read
                memory[param1] = memoryIn;
                instrPtr += 2;
                break;
            case 4: // Write
                memoryOut = (mode1 == 1 ? param1 : memory[param1]);
                instrPtr += 2;
                break;
            case 5: // Jump-if-true
                param2 = memory[instrPtr + 2];
                instrPtr += 3;
                if ((mode1 == 1 ? param1 : memory[param1]) != 0) {
                    instrPtr = (mode2 == 1 ? param2 : memory[param2]);
                }
                break;
            case 6: // Jump-if-false
                param2 = memory[instrPtr + 2];
                instrPtr += 3;
                if ((mode1 == 1 ? param1 : memory[param1]) == 0) {
                    instrPtr = (mode2 == 1 ? param2 : memory[param2]);
                }
                break;
            case 7: // Less than
                param2 = memory[instrPtr + 2];
                store = memory[instrPtr + 3];
                memory[store] = (mode1 == 1 ? param1 : memory[param1]) < (mode2 == 1 ? param2 : memory[param2]) ? 1 : 0;
                instrPtr += 4;
                break;
            case 8: // Equals
                param2 = memory[instrPtr + 2];
                store = memory[instrPtr + 3];
                memory[store] = (mode1 == 1 ? param1 : memory[param1]) == (mode2 == 1 ? param2 : memory[param2]) ? 1 : 0;
                instrPtr += 4;
        }
    }

    /**
     * Gives input to the program
     *
     * @param in The input
     */
    public void setInput(int in) {
        memoryIn = in;
    }

    /**
     * Gets the value in the output memory for the int code
     *
     * @return The value in the output memory
     */
    public int getOutput() {
        return memoryOut;
    }

    /**
     * Gets a value from the program memory
     *
     * @param pos The position to get
     * @return The value at the position
     */
    public int getFromMemory(int pos) {
        if(pos >= 0 && pos < memory.length) {
            return memory[pos];
        }
        else {
            throw new IllegalArgumentException("Requested position must fall into acceptable array bounds");
        }
    }

    /**
     * Sets the memory value at the specified position
     *
     * @param pos The position to set
     * @param val The value to set
     */
    public void setMemoryValue(int pos, int val) {
        if(pos >= 0 && pos < memory.length) {
            memory[pos] = val;
        }
        else {
            throw new IllegalArgumentException("Requested position must fall into acceptable array bounds");
        }
    }
}
