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
}
