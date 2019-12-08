/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2019;

import java.util.LinkedList;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class Amplifier {

    private int[] program, originalProgram;
    private int instPointer = 0;
    private LinkedList<Integer> inputBuffer;
    private final LinkedList<Integer> outputBuffer = new LinkedList<>();
    private final int[] argsByOpcode = new int[100];

    public Amplifier(int[] program) {
        this.program = program;
        this.originalProgram = program.clone();
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
        int opcode, j;
        int[] args = new int[3];
        boolean[] argIsPos = new boolean[3]; //They certainly are.
        //I recognise that this boolean array solution seems roundabout, but
        //write locations must be passed as immediate values despite being
        //classified as positional ones by the challenge as posed. Thus, it is
        //not sensible to retrieve the value at the position before knowing which
        //opcode will be executed and which arguments are actually write positions.
        //In the interest of modular code, give the modes to the function that will
        //know how to interpret the arguments.
        boolean stop = false, instPointerModified;
        while (!stop && instPointer < program.length) {
            instPointerModified = false;
            opcode = program[instPointer] % 100;
            for (j = 0; j < argsByOpcode[opcode]; j++) {
                argIsPos[j] = program[instPointer] / (int) Math.pow(10, j + 2) % 10 == 0;
                args[j] = program[instPointer + j + 1];
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

    private void add(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] + args[1];
    }

    private void mult(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] * args[1];
    }

    private void input(int[] args) {
        program[args[0]] = inputBuffer.removeFirst();
    }

    private void output(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        outputBuffer.add(args[0]);
    }

    private boolean jumpIfTrue(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        if (args[0] != 0) {
            args[1] = argIsPos[1] ? program[args[1]] : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private boolean jumpIfFalse(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        if (args[0] == 0) {
            args[1] = argIsPos[1] ? program[args[1]] : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private void lessThan(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] < args[1] ? 1 : 0;
    }

    private void equalTo(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] == args[1] ? 1 : 0;
    }

    public LinkedList<Integer> getInputBuffer() {
        return inputBuffer;
    }

    public LinkedList<Integer> getOutputBuffer() {
        return outputBuffer;
    }

    public void setInputBuffer(LinkedList inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public void resetProgram() {
        this.program = originalProgram.clone();
        instPointer = 0;
    }

}
