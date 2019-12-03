package adventofcode2019;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class Intcode {

    public static int[] runProgram(int[] program) {
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
}
