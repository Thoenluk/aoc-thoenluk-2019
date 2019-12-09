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
    private long instPointer = 0;
    private LinkedList<Long> inputBuffer;
    private final LinkedList<Long> outputBuffer = new LinkedList<>();
    private final long[] argsByOpcode = new long[100];

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
        argsByOpcode[99] = 0;
    }

    public boolean runProgram() {
        int opcode;
        int j;
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
            opcode = (int) ((long) program.get(instPointer) % 100);
            for (j = 0; j < argsByOpcode[(int) opcode]; j++) {
                argIsPos[j] = program.get(instPointer) / (long) Math.pow(10, j + 2) % 10 == 0;
                args[j] = program.get(instPointer + j + 1);
            }
            switch (opcode) {
                case 1:
                    add(args, argIsPos);
                    break;
                case 2:
                    mult(args, argIsPos);
                    break;
                case 3:
                    input(args);
                    break;
                case 4:
                    output(args, argIsPos);
                    stop = true;
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
                case 99:
                    return true;
            }
            if (!instPointerModified) {
                instPointer += argsByOpcode[opcode] + 1;
            }
        }
        return false;
    }

    private void add(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        args[1] = argIsPos[1] ? program.get(args[1]) : args[1];
        program.put(args[2], args[0] + args[1]);
    }

    private void mult(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        args[1] = argIsPos[1] ? program.get(args[1]) : args[1];
        program.put(args[2], args[0] * args[1]);
    }

    private void input(long[] args) {
        program.put(args[0], inputBuffer.removeFirst());
    }

    private void output(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        outputBuffer.add(args[0]);
    }

    private boolean jumpIfTrue(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        if (args[0] != 0) {
            args[1] = argIsPos[1] ? program.get(args[1]) : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private boolean jumpIfFalse(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        if (args[0] == 0) {
            args[1] = argIsPos[1] ? program.get(args[1]) : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private void lessThan(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        args[1] = argIsPos[1] ? program.get(args[1]) : args[1];
        if (args[0] < args[1]) {
            program.put(args[2], (long) 1);
        } else {
            program.put(args[2], (long) 0);
        }
    }

    private void equalTo(long[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program.get(args[0]) : args[0];
        args[1] = argIsPos[1] ? program.get(args[1]) : args[1];
        if (args[0] == args[1]) {
            program.put(args[2], (long) 1);
        } else {
            program.put(args[2], (long) 0);
        }
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

    public void resetProgram() {
        this.program = (HashMap<Long, Long>) originalProgram.clone();
        instPointer = 0;
    }

}
