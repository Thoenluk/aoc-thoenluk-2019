package adventofcode2019;

import java.util.Scanner;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class Intcode {

    private static int[] program;
    private static int instPointer;
    private static final Scanner INPUT = new Scanner(System.in);

    public static int[] runSimpleProgram(int[] program) {
        boolean stop = false;
        int i = 0, valuesUsed;
        while (!stop && i < program.length) {
            switch (program[i]) {
                case 1:
                    program[program[i + 3]] = program[program[i + 1]] + program[program[i + 2]];
                    valuesUsed = 4;
                    break;
                case 2:
                    program[program[i + 3]] = program[program[i + 1]] * program[program[i + 2]];
                    valuesUsed = 4;
                    break;
                default:
                    stop = true;
                    valuesUsed = 1;
                    break;
            }
            i += valuesUsed;
        }
        return program;
    }

    public static int[] runProgram(int[] argProgram) {
        program = argProgram;
        instPointer = 0;
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
        boolean stop = false, instPointerModified = false;
        int[] argsByOpcode = new int[100];
        argsByOpcode[1] = 3;
        argsByOpcode[2] = 3;
        argsByOpcode[3] = 1;
        argsByOpcode[4] = 1;
        argsByOpcode[5] = 2;
        argsByOpcode[6] = 2;
        argsByOpcode[7] = 3;
        argsByOpcode[8] = 3;
        argsByOpcode[99] = 0;
        while (!stop && instPointer < program.length) {
            instPointerModified = false;
            opcode = program[instPointer] % 100;
            //System.out.format("Current instruction: opcode %d on pos %d\n", opcode, i);
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
                    stop = true;
                    break;
            }
            if (!instPointerModified) {
                instPointer += argsByOpcode[opcode] + 1;
            }
        }
        return program;
    }

    private static void add(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] + args[1];
    }

    private static void mult(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] * args[1];
    }

    private static void input(int[] args) {
        System.out.println("Provide input");
        program[args[0]] = INPUT.nextInt();
    }

    private static void output(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        System.out.format("Output value: %d\n", args[0]);
    }

    private static boolean jumpIfTrue(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        if (args[0] != 0) {
            args[1] = argIsPos[1] ? program[args[1]] : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private static boolean jumpIfFalse(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        if (args[0] == 0) {
            args[1] = argIsPos[1] ? program[args[1]] : args[1];
            instPointer = args[1];
            return true;
        }
        return false;
    }

    private static void lessThan(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] < args[1] ? 1 : 0;
    }

    private static void equalTo(int[] args, boolean[] argIsPos) {
        args[0] = argIsPos[0] ? program[args[0]] : args[0];
        args[1] = argIsPos[1] ? program[args[1]] : args[1];
        program[args[2]] = args[0] == args[1] ? 1 : 0;
    }
}
