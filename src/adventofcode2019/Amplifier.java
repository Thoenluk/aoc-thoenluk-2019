/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2019;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class Amplifier {

    private HashMap<Long, Long> program, originalProgram;
    private long instPointer = 0, relativeBase = 0;
    private LinkedList<Long> inputBuffer = new LinkedList<>();
    private LinkedList<Long> outputBuffer = new LinkedList<>();
    private final HashMap<Integer, Integer> argsByOpcode = new HashMap<>();
    private static final HashMap<String, Long> NUMBER_CACHE = new HashMap<>();

    public Amplifier(long[] program) {
        this.program = new HashMap<>();
        this.originalProgram = new HashMap<>();
        for (int i = 0; i < program.length; i++) {
            this.program.put(Long.valueOf(i), program[i]);
            this.originalProgram.put(Long.valueOf(i), program[i]);
        }
        argsByOpcode.put(1, 3);
        argsByOpcode.put(2, 3);
        argsByOpcode.put(3, 1);
        argsByOpcode.put(4, 1);
        argsByOpcode.put(5, 2);
        argsByOpcode.put(6, 2);
        argsByOpcode.put(7, 3);
        argsByOpcode.put(8, 3);
        argsByOpcode.put(9, 3);
        argsByOpcode.put(99, 0);
    }

    public Amplifier(String savedState) {
        this.program = new HashMap<>();
        this.originalProgram = new HashMap<>();
        loadState(savedState);
        argsByOpcode.put(1, 3);
        argsByOpcode.put(2, 3);
        argsByOpcode.put(3, 1);
        argsByOpcode.put(4, 1);
        argsByOpcode.put(5, 2);
        argsByOpcode.put(6, 2);
        argsByOpcode.put(7, 3);
        argsByOpcode.put(8, 3);
        argsByOpcode.put(9, 3);
        argsByOpcode.put(99, 0);
    }

    public boolean runProgram() {
        int opcode, j;
        long mode;
        long[] args = new long[3];
        boolean[] argIsPos = new boolean[3]; //They certainly are.
        //I recognise that this boolean array solution seems roundabout, but
        //write locations must be passed as immediate values despite being
        //classified as positional ones by the challenge as posed. Thus, it is
        //not sensible to retrieve the value at the position before knowing which
        //opcode will be executed and which arguments are actually write positions.
        //In the interest of modular code, give the modes to the function that will
        //know how to interpret the arguments.
        boolean stop = false, instPointerModified;
        while (!stop && instPointer < program.size()) {
            instPointerModified = false;
            opcode = (int) safeGet(instPointer) % 100;
            for (j = 0; j < argsByOpcode.get(opcode); j++) {
                args[j] = safeGet(instPointer + j + 1);
                mode = safeGet(instPointer) / (long) Math.pow(10, j + 2) % 10;
                if (mode == 1) {
                    argIsPos[j] = false;
                } else {
                    argIsPos[j] = true;
                    if (mode == 2) {
                        args[j] += relativeBase;
                    }
                }
            }
            switch (opcode) {
                case 1:
                    add(args, argIsPos);
                    break;
                case 2:
                    mult(args, argIsPos);
                    break;
                case 3:
                    stop = !input(args);
                    instPointerModified = stop; //Stop program from advancing if
                    //waiting on input to be provided.
                    break;
                case 4:
                    output(args, argIsPos);
                    break;
                case 5:
                    instPointerModified = jumpIfTrue(args, argIsPos);
                    break;
                case 6:
                    instPointerModified = jumpIfFalse(args, argIsPos);
                    break;
                case 7:
                    lessThan(args, argIsPos);
                    break;
                case 8:
                    equalTo(args, argIsPos);
                    break;
                case 9:
                    adjustRelativeBase(args, argIsPos);
                    break;
                case 99:
                    return true;
            }
            if (!instPointerModified) {
                instPointer += argsByOpcode.get(opcode) + 1;
            }
        }
        return false;
    }

    /**
     * Writes the specified string into this amplifier's input buffer, char by
     * char. Note: All strings input this way must be terminated by a newline
     * character, as most amplifier programs look for this termination. In order
     * to allow partial inputs, this function does NOT automatically add
     * newlines.
     *
     * @param string The string to be fed into input.
     */
    public void inputString(String string) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            inputBuffer.add((long) chars[i]);
        }
    }

    /**
     * Adds args[0] to args[1] and writes the result to args[2].
     *
     * @param args The values or positions to be used. See argIsPos argument.
     * @param argIsPos For each of the arguments arg[i], argIsPos[i] is true iff
     * args[i] is to be interpreted as a position rather than an immediate
     * value. That is, if argIsPos[i] is true, the function will retrieve the
     * value at address args[i] for the calculation. If it is false, it will use
     * the value in args[i] as an immediate. As per specification, the write
     * location is always used as an immediate value (containing the write
     * address), so argIsPos[2] is not used.
     */
    private void add(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        program.put(args[2], args[0] + args[1]);
    }

    /**
     * Multiplies args[0] to args[1] and writes the result to args[2].
     *
     * @param args The values or positions to be used. See argIsPos argument.
     * @param argIsPos For each of the arguments arg[i], argIsPos[i] is true iff
     * args[i] is to be interpreted as a position rather than an immediate
     * value. That is, if argIsPos[i] is true, the function will retrieve the
     * value at address args[i] for the calculation. If it is false, it will use
     * the value in args[i] as an immediate. As per specification, the write
     * location is always used as an immediate value (containing the write
     * address), so argIsPos[2] is not used.
     */
    private void mult(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        program.put(args[2], args[0] * args[1]);
    }

    /**
     * Reads the first value from the inputbuffer and writes it to the position
     * given by args[0].
     *
     * @param args The first element args[0] denotes the position to write the
     * value to as an immediate pointer. Other elements are not used.
     * @return False if the inputbuffer is empty and reading is thus blocked,
     * true if a value was successfully read.
     */
    private boolean input(long[] args) {
        if (inputBuffer.isEmpty()) {
            return false;
        }
        program.put(args[0], inputBuffer.removeFirst());
        return true;
    }

    /**
     * Writes the value given or pointed to by args[0] to the outputbuffer.
     *
     * @param args The first element args[0] denotes the value to be written (if
     * argIsPos[0] is false) or points to that value (if argIsPos[0] is true.)
     * Other elements are not used.
     * @param argIsPos If args[0] points to a location in the program,
     * argIsPos[0] is true. If it is to be interpreted as a value and itself
     * written to the outputbuffer, argIsPos[0] is false. Other elements are not
     * used.
     */
    private void output(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        outputBuffer.add(args[0]);
    }

    /**
     * If the value given or pointed to by args[0] is NOT 0, set instPointer to
     * the value given or point to by args[1], else do nothing.
     *
     * @param args args[0] contains the value or pointer to be compared to 0. If
     * it is nonzero, args[1] is written to the instruction pointer. See
     * argument argIsPos.
     * @param argIsPos If args[0] points to a location in the program,
     * argIsPos[0] is true. If it is to be interpreted as a value and itself
     * compared to 0, argIsPos[0] is false. args[1] is always interpreted as an
     * immediate value (containing the write address) and thus argIsPos[1] is
     * not used.
     * @return True if a jump occurred (args[0] is nonzero), false otherwise.
     */
    private boolean jumpIfTrue(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        if (args[0] != 0) {
            args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    /**
     * If the value given or pointed to by args[0] IS 0, set instPointer to the
     * value given or point to by args[1], else do nothing.
     *
     * @param args args[0] contains the value or pointer to be compared to 0. If
     * it is zero, args[1] is written to the instruction pointer. See argument
     * argIsPos.
     * @param argIsPos If args[0] points to a location in the program,
     * argIsPos[0] is true. If it is to be interpreted as a value and itself
     * compared to 0, argIsPos[0] is false. args[1] is always interpreted as an
     * immediate value (containing the write address) and thus argIsPos[1] is
     * not used.
     * @return True if a jump occurred (args[0] is zero), false otherwise.
     */
    private boolean jumpIfFalse(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        if (args[0] == 0) {
            args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    /**
     * If the value given or pointed to by args[0] is LESS than the one denoted
     * by args[1], write 1 to the location given by args[2], else write 0.
     *
     * @param args args[0] contains the value or pointer that is checked for
     * being less than the one denoted by args[1]. args[2] contains the write
     * location for the evaluation result. See argument argIsPos.
     * @param argIsPos If args[0] points to a location in the program,
     * argIsPos[0] is true. If it is to be interpreted as a value and itself
     * compared to 0, argIsPos[0] is false. The same applies for args[1].
     * args[2] is always interpreted as an immediate value (containing the write
     * address) and thus argIsPos[2] is not used.
     */
    private void lessThan(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        if (args[0] < args[1]) {
            program.put(args[2], (long) 1);
        } else {
            program.put(args[2], (long) 0);
        }
    }

    /**
     * If the value given or pointed to by args[0] is EQUAL TO the one denoted
     * by args[1], write 1 to the location given by args[2], else write 0.
     *
     * @param args args[0] contains the value or pointer that is checked for
     * being equal to the one denoted by args[1]. args[2] contains the write
     * location for the evaluation result. See argument argIsPos.
     * @param argIsPos If args[0] points to a location in the program,
     * argIsPos[0] is true. If it is to be interpreted as a value and itself
     * compared to 0, argIsPos[0] is false. The same applies for args[1].
     * args[2] is always interpreted as an immediate value (containing the write
     * address) and thus argIsPos[2] is not used.
     */
    private void equalTo(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        if (args[0] == args[1]) {
            program.put(args[2], (long) 1);
        } else {
            program.put(args[2], (long) 0);
        }
    }

    /**
     * Adds the value given or pointed to by args[0] to the relative base.
     *
     * @param args args[0] contains the value or pointer to be added to the
     * relative base. See argument argIsPos.
     * @param argIsPos If args[0] points to a location in the program,
     * argIsPos[0] is true. If it is to be interpreted as a value and itself
     * compared to 0, argIsPos[0] is false.
     */
    private void adjustRelativeBase(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        relativeBase += args[0];
    }

    public LinkedList<Long> getInputBuffer() {
        return inputBuffer;
    }

    public LinkedList<Long> getOutputBuffer() {
        return outputBuffer;
    }

    public void setInputBuffer(LinkedList inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public void setOutputBuffer(LinkedList buffer) {
        this.outputBuffer = buffer;
    }

    @Override
    public String toString() {
        String prog = "Program: " + program.toString() + "\n";
        String originalProg = "Original program: " + originalProgram.toString() + "\n";
        String instruction = "Instruction pointer: " + instPointer + "\n";
        String relBase = "Relative base: " + relativeBase + "\n";
        String input = "Input buffer content: " + inputBuffer.toString() + "\n";
        String output = "Output buffer content: " + outputBuffer.toString();
        return prog + originalProg + instruction + relBase + input + output;
    }

    /**
     * Assemble the current state of the outputbuffer (leaving it in place) and
     * return it as a String.
     *
     * @return String containing every value in the outputbuffer in order
     * interpreted as a char.
     */
    public String outputAsString() {
        String output = "";
        for (Long value : outputBuffer) {
            output += (char) value.intValue();
        }
        return output;
    }

    /**
     * Assemble the current state of the outputbuffer as a String, empty it, and
     * return the String.
     *
     * @return String containing every value in the outputbuffer in order
     * interpreted as a char, prior to it being emptied.
     */
    public String flushOutput() {
        String output = outputAsString();
        clearOutput();
        return output;
    }

    public void clearOutput() {
        outputBuffer.clear();
    }

    /**
     * Reset the program back its original state as it was when this Amplifier
     * was instantiated. Buffers are NOT emptied.
     */
    public void resetProgram() {
        this.program = (HashMap<Long, Long>) originalProgram.clone();
        instPointer = 0;
        relativeBase = 0;
    }

    /**
     * Overwrite this Amplifier's current state with the one specified in the
     * input String. Caution: There is no input validation in this method, as it
     * is intended to be used only with Strings generated by Amplifier's
     * toString method.
     *
     * @param state The state specification, as by toString's output.
     */
    public void loadState(String state) {
        program.clear();
        originalProgram.clear();
        String[] lines = state.split("\n");
        String[] location;
        String[] _program = lines[0].substring(10, lines[0].length() - 1).split(", ");
        for (String pair : _program) {
            location = pair.split("=");
            program.put(stringToLong(location[0]), stringToLong(location[1]));
        }
        String[] _originalProgram = lines[1].substring(19, lines[1].length() - 1).split(", ");
        for (String pair : _originalProgram) {
            location = pair.split("=");
            originalProgram.put(stringToLong(location[0]), stringToLong(location[1]));
        }
        String _instPointer = lines[2].substring(21);
        instPointer = stringToLong(_instPointer);
        String _relativeBase = lines[3].substring(15);
        relativeBase = stringToLong(_relativeBase);
        String[] _inputBuffer = lines[4].substring(23, lines[4].length() - 1).split(", ");
        if (!_inputBuffer[0].isEmpty()) {
            for (String value : _inputBuffer) {
                inputBuffer.add(stringToLong(value));
            }
        }
        String[] _outputBuffer = lines[5].substring(24, lines[5].length() - 1).split(", ");
        if (!_outputBuffer[0].isEmpty()) {
            for (String value : _outputBuffer) {
                outputBuffer.add(stringToLong(value));
            }
        }
    }

    /**
     * Get the value from the given address in memory. If there is no value at
     * that address set, create a mapping using 0 first and then return it.
     *
     * @param index The memory address to be read.
     * @return If the address exists in memory, returns its value, else 0.
     */
    private long safeGet(long index) {
        if (!program.containsKey(index)) {
            program.put(index, (long) 0);
        }
        return program.get(index);
    }

    /**
     * Convenience method that caches each String with the corresponding Long
     * value it describes to avoid unnecessary calls to parseLong. If the String
     * has never been seen before, add it to NUMBER_CACHE with its corresponding
     * Long.
     *
     * @param value A String containing the value to be translated into a Long
     * @return The Long value described by the input String.
     */
    private long stringToLong(String value) {
        if (!NUMBER_CACHE.containsKey(value)) {
            NUMBER_CACHE.put(value, Long.parseLong(value));
        }
        return NUMBER_CACHE.get(value);
    }

}
