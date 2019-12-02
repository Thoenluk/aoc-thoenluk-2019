/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2019;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class AdventOfCode2019 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        int result = 0;
        String resultString = "";
        System.out.println("Enter the number of the challenge you want me to solve.");
        Scanner keyboard = new Scanner(System.in);
        int challenge = keyboard.nextInt();
        List<String> input = ((challenge % 2) == 1)
                ? Files.readAllLines(Paths.get("input" + challenge + ".txt"))
                : Files.readAllLines(Paths.get("input" + (challenge - 1) + ".txt"));
        switch (challenge) {
            case 1:
                result = challengeOne(input);
                break;
            case 2:
                result = challengeTwo(input);
                break;
            case 3:
                result = challengeThree(input);
                break;
            case 4:
                result = challengeFour(input);
                break;
            default:
                System.out.println("lolno");
        }
        System.out.println(result + " " + resultString);

    }

    private static int challengeOne(List<String> input) {
        int fuelRequired = 0, moduleWeight;
        for (String module : input) {
            moduleWeight = Integer.parseInt(module);
            fuelRequired += moduleWeight / 3 - 2;
        }
        return fuelRequired;
    }

    private static int challengeTwo(List<String> input) {
        int fuelRequired = 0, moduleWeight, previousFuelWeight;
        for (String module : input) {
            moduleWeight = Integer.parseInt(module);
            previousFuelWeight = moduleWeight / 3 - 2;
            while (previousFuelWeight > 0) {
                fuelRequired += previousFuelWeight;
                previousFuelWeight = previousFuelWeight / 3 - 2;
            }
        }
        return fuelRequired;
    }

    private static int challengeThree(List<String> input) {
        String[] numbers = input.get(0).split(",");
        int[] program = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Integer.parseInt(numbers[i]);
        }
        program[1] = 12;
        program[2] = 2;
        program = Intcode.runProgram(program);
        return program[0];
    }

    private static int challengeFour(List<String> input) {
        String[] numbers = input.get(0).split(",");
        int[] originalProgram = new int[numbers.length], returnedProgram;
        int deltaNoun, deltaVerb, doubleZeroReturn, nounOneReturn, verbOneReturn;
        int targetReturn = 19690720, properNoun, properVerb;
        for (int i = 0; i < numbers.length; i++) {
            originalProgram[i] = Integer.parseInt(numbers[i]);
        }
        //The joke here is that both arguments increase the return value linearly.
        //Thus, suss out what the delta values are, then use math instead of loops.
        //Step 1: Get return value for 00
        returnedProgram = originalProgram.clone();
        returnedProgram = Intcode.runProgram(returnedProgram);
        doubleZeroReturn = returnedProgram[0];

        //Step 2: Get return value for noun = 1
        returnedProgram = originalProgram.clone();
        returnedProgram[1] = 1;
        returnedProgram = Intcode.runProgram(returnedProgram);
        nounOneReturn = returnedProgram[0];

        //Step 3: Get return value for verb = 1
        returnedProgram = originalProgram.clone();
        returnedProgram[2] = 1;
        returnedProgram = Intcode.runProgram(returnedProgram);
        verbOneReturn = returnedProgram[0];

        deltaNoun = nounOneReturn - doubleZeroReturn;
        deltaVerb = verbOneReturn - doubleZeroReturn;

        //Noun has a larger effect, such that the value of verb can be disregarded
        //while determining the value of verb. Alternatively, one could determine
        //both values the same way, then modulo 100 in order to put them into the
        //useful range.
        //As it happens deltaVerb = 1, but let's pretend it doesn't.
        properNoun = (targetReturn - doubleZeroReturn) / deltaNoun;
        properVerb = (targetReturn - doubleZeroReturn - properNoun * deltaNoun) / deltaVerb;
        return 100 * properNoun + properVerb;
    }
}
