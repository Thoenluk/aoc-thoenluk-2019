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
    private final long[] argsByOpcode = new long[100];
    private static final HashMap<String, Long> NUMBER_CACHE = new HashMap<>();

    public Amplifier(long[] program) {
        this.program = new HashMap<>();
        this.originalProgram = new HashMap<>();
        for (int i = 0; i < program.length; i++) {
            this.program.put(Long.valueOf(i), program[i]);
            this.originalProgram.put(Long.valueOf(i), program[i]);
        }
        argsByOpcode[1] = 3;
        argsByOpcode[2] = 3;
        argsByOpcode[3] = 1;
        argsByOpcode[4] = 1;
        argsByOpcode[5] = 2;
        argsByOpcode[6] = 2;
        argsByOpcode[7] = 3;
        argsByOpcode[8] = 3;
        argsByOpcode[9] = 1;
        argsByOpcode[99] = 0;
    }

    public Amplifier(String savedState) {
        this.program = new HashMap<>();
        this.originalProgram = new HashMap<>();
        String[] lines = savedState.split("\n");
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
        argsByOpcode[1] = 3;
        argsByOpcode[2] = 3;
        argsByOpcode[3] = 1;
        argsByOpcode[4] = 1;
        argsByOpcode[5] = 2;
        argsByOpcode[6] = 2;
        argsByOpcode[7] = 3;
        argsByOpcode[8] = 3;
        argsByOpcode[9] = 1;
        argsByOpcode[99] = 0;
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
            opcode = (int) ((long) safeGet(instPointer) % 100);
            for (j = 0; j < argsByOpcode[(int) opcode]; j++) {
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
                instPointer += argsByOpcode[opcode] + 1;
            }
        }
        return false;
    }

    public void inputString(String string) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            inputBuffer.add((long) chars[i]);
        }
    }

    private void add(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        program.put(args[2], args[0] + args[1]);
    }

    private void mult(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        program.put(args[2], args[0] * args[1]);
    }

    //Retval: False while input is blocked, true on successful read.
    private boolean input(long[] args) {
        if (inputBuffer.isEmpty()) {
            return false;
        }
        program.put(args[0], inputBuffer.removeFirst());
        return true;
    }

    private void output(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        outputBuffer.add(args[0]);
    }

    private boolean jumpIfTrue(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        if (args[0] != 0) {
            args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private boolean jumpIfFalse(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        if (args[0] == 0) {
            args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private void lessThan(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        if (args[0] < args[1]) {
            program.put(args[2], (long) 1);
        } else {
            program.put(args[2], (long) 0);
        }
    }

    private void equalTo(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? safeGet(args[0]) : args[0];
        args[1] = argIsPos[1] ? safeGet(args[1]) : args[1];
        if (args[0] == args[1]) {
            program.put(args[2], (long) 1);
        } else {
            program.put(args[2], (long) 0);
        }
    }

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

    public String outputAsString() {
        String output = "";
        for (Long value : outputBuffer) {
            output += (char) value.intValue();
        }
        return output;
    }

    public String flushOutput() {
        String output = outputAsString();
        clearOutput();
        return output;
    }

    public void clearOutput() {
        outputBuffer.clear();
    }

    public void resetProgram() {
        this.program = (HashMap<Long, Long>) originalProgram.clone();
        instPointer = 0;
        relativeBase = 0;
    }

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

    private long safeGet(long index) {
        if (!program.containsKey(index)) {
            program.put(index, (long) 0);
        }
        return program.get(index);
    }

    private long stringToLong(String value) {
        if (!NUMBER_CACHE.containsKey(value)) {
            NUMBER_CACHE.put(value, Long.parseLong(value));
        }
        return NUMBER_CACHE.get(value);
    }

}
