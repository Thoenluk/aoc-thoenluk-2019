/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2019;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
            case 5:
                result = challengeFive(input);
                break;
            case 6:
                result = challengeSix(input);
                break;
            case 7:
                result = challengeSeven(input, false);
                break;
            case 8:
                result = challengeSeven(input, true);
                break;
            case 9:
                result = challengeNine(input);
                break;
            case 10:
                result = challengeNine(input);
                break;
            case 11:
                result = challengeEleven(input);
                break;
            case 12:
                result = challengeTwelve(input);
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
        program = Intcode.runSimpleProgram(program);
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
        returnedProgram = Intcode.runSimpleProgram(returnedProgram);
        doubleZeroReturn = returnedProgram[0];

        //Step 2: Get return value for noun = 1
        returnedProgram = originalProgram.clone();
        returnedProgram[1] = 1;
        returnedProgram = Intcode.runSimpleProgram(returnedProgram);
        nounOneReturn = returnedProgram[0];

        //Step 3: Get return value for verb = 1
        returnedProgram = originalProgram.clone();
        returnedProgram[2] = 1;
        returnedProgram = Intcode.runSimpleProgram(returnedProgram);
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

    private static int challengeFive(List<String> input) {
        String[] instructions;
        HashMap<Integer, HashSet<Integer>> firstVisitedCoordinates = new HashMap<>();
        firstVisitedCoordinates.put(0, new HashSet<>());
        HashSet<int[]> crossings = new HashSet<>();
        int x, y, distance, index = 0;
        char direction;
        for (String wire : input) {
            instructions = wire.split(",");
            x = 0;
            y = 0;
            index++;
            for (int i = 0; i < instructions.length; i++) {
                distance = Integer.parseInt(instructions[i].substring(1));
                direction = instructions[i].charAt(0);
                for (int j = 1; j <= distance; j++) {
                    switch (direction) {
                        case 'U':
                            y++;
                            break;
                        case 'R':
                            x++;
                            break;
                        case 'L':
                            x--;
                            break;
                        case 'D':
                            y--;
                            break;
                    }
                    if (index == 1) {
                        if (!firstVisitedCoordinates.containsKey(x)) {
                            firstVisitedCoordinates.put(x, new HashSet<>());
                        }
                        firstVisitedCoordinates.get(x).add(y);
                    } else if (firstVisitedCoordinates.containsKey(x)
                            && firstVisitedCoordinates.get(x).contains(y)) {
                        crossings.add(new int[]{x, y});
                    }
                }
            }
        }
        int manhattanDistance = Integer.MAX_VALUE;
        int currentDistance;
        for (int[] currentCrossing : crossings) {
            currentDistance = Math.abs(currentCrossing[0]) + Math.abs(currentCrossing[1]);
            if (currentDistance < manhattanDistance) {
                manhattanDistance = currentDistance;
                System.out.println(currentCrossing[0] + " " + currentCrossing[1]);
            }
        }
        return manhattanDistance;
    }

    private static int challengeSix(List<String> input) {
        String[] instructions;
        HashMap<Integer, HashMap<Integer, Integer>> visitedCoordinates = new HashMap<>();
        visitedCoordinates.put(0, new HashMap<>());
        HashSet<int[]> crossings = new HashSet<>();
        int x, y, distance, index = 0, stepsTaken;
        char direction;
        for (String wire : input) {
            instructions = wire.split(",");
            x = 0;
            y = 0;
            index++;
            stepsTaken = 0;
            for (int i = 0; i < instructions.length; i++) {
                distance = Integer.parseInt(instructions[i].substring(1));
                direction = instructions[i].charAt(0);
                for (int j = 1; j <= distance; j++) {
                    switch (direction) {
                        case 'U':
                            y++;
                            break;
                        case 'R':
                            x++;
                            break;
                        case 'L':
                            x--;
                            break;
                        case 'D':
                            y--;
                            break;
                    }
                    stepsTaken++;
                    if (index == 1) {
                        if (!visitedCoordinates.containsKey(x)) {
                            visitedCoordinates.put(x, new HashMap<>());
                        }
                        if (!visitedCoordinates.get(x).containsKey(y)) {
                            visitedCoordinates.get(x).put(y, stepsTaken);
                        }

                    } else if (visitedCoordinates.containsKey(x)
                            && visitedCoordinates.get(x).containsKey(y)) {
                        crossings.add(new int[]{x, y, stepsTaken + visitedCoordinates.get(x).get(y)});
                    }
                }
            }
        }
        stepsTaken = Integer.MAX_VALUE;
        for (int[] currentCrossing : crossings) {
            stepsTaken = currentCrossing[2] < stepsTaken ? currentCrossing[2] : stepsTaken;
        }
        return stepsTaken;
    }

    private static int challengeSeven(List<String> input, boolean noLargeGroups) {
        String[] limits = input.get(0).split("-");
        int min = Integer.parseInt(limits[0]);
        int max = Integer.parseInt(limits[1]);
        int passwords = 0, i, j;
        boolean hasDouble, noDecrease;
        int[] numberLilEndian = new int[limits[1].length()];
        HashMap<Integer, Integer> occurences = new HashMap<>();
        //Given that the conditions are more stringent towards the end of the
        //number (more digits to be less than), using the number in reverse order
        //allows for a more intuitive forward-reading check with efficiency.
        for (i = 0; i < limits[0].length(); i++) {
            numberLilEndian[i] = (int) (min / Math.pow(10, i)) % 10;
        }
        for (i = min; i <= max; i++) {
            hasDouble = false;
            noDecrease = true;
            occurences.clear();
            for (j = 0; j < numberLilEndian.length; j++) {
                if (j < numberLilEndian.length - 1 && numberLilEndian[j] < numberLilEndian[j + 1]) {
                    noDecrease = false;
                    break;
                }
                if (!occurences.containsKey(numberLilEndian[j])) {
                    occurences.put(numberLilEndian[j], 1);
                } else {
                    occurences.put(numberLilEndian[j], occurences.get(numberLilEndian[j]) + 1);
                }
            }
            if (noLargeGroups) {
                hasDouble = occurences.containsValue(2);
            } else {
                for (Integer occurence : occurences.values()) {
                    hasDouble = hasDouble || occurence >= 2;
                }
            }
            if (hasDouble && noDecrease) {
                passwords++;
            }
            for (j = 0; j < numberLilEndian.length; j++) {
                numberLilEndian[j]++;
                if (numberLilEndian[j] == 10) {
                    numberLilEndian[j] = 0;
                    /*At this point, a small aside on how my choice of number
                    representation has come to bite me.

                    While keeping the number in this per-digit form does allow
                    for very fast processing rather than copying an int (say, i)
                    into an array with a dozen mods and powers and divisions each
                    time, it does mean that I have no idea about the value of the
                    number at any given time, without reclaiming it into int form
                    through an equally lengthy process.

                    This means that I can't very well optimise further. It should
                    be obvious that 0 will never appear in a password, as numbers
                    can't start with 0 and digits must grow or be equal to their left.
                    This makes 1 the least value any digit in a password can have.
                    Logically, one could then skip past 0 while incrementing the
                    number and write 1 rather than 0 to a wrapping digit. Or, if
                    especially fancy, keep track of the least permissible value
                    for a digit and write that instead, potentially skipping
                    several tens of thousands of values at a time even with six-
                    -digit passwords.

                    However. I can't easily keep track of how many numbers I skip
                    without performing expensive operations. The simplest I can
                    think of is to keep the max value in the same array form and
                    compare digit by digit on each number actually evaluated.
                    Doing that would allow me to terminate the algorithm after
                    the proper amount of number space has been checked. As I
                    currently count the number of checks done, skipping any
                    numbers hugely falsifies the result.

                    That has a time complexity of O(n*k), with n being the length
                    of the input numbers and k being their difference. It's quite
                    possible that doing it would beat the current algorithm on
                    large inputs and relatively large k values especially. I have
                    not done any benchmarking to test this.*/
                } else {
                    break;
                }
            }
        }
        return passwords;
    }

    private static int challengeNine(List<String> input) {
        String[] numbers = input.get(0).split(",");
        int[] program = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Integer.parseInt(numbers[i]);
        }
        program = Intcode.runProgram(program);
        return 0;
    }

    private static int challengeEleven(List<String> input) {
        HashMap<String, List<String>> orbits = new HashMap<>();
        String[] objects;
        for (String relation : input) {
            objects = relation.split("\\)");
            if (!orbits.containsKey(objects[0])) {
                orbits.put(objects[0], new LinkedList<>());
            }
            orbits.get(objects[0]).add(objects[1]);
        }

        LinkedList<Integer> nextIndex = new LinkedList<>();
        LinkedList<String> route = new LinkedList<>();
        String position = "COM";
        int totalOrbits = 0, depth = 0;
        nextIndex.add(0);
        route.add(position);
        while (!nextIndex.isEmpty()) {
            if (orbits.containsKey(position) && nextIndex.getLast() < orbits.get(position).size()) {
                position = orbits.get(position).get(nextIndex.getLast());
                nextIndex.add(nextIndex.removeLast() + 1);
                depth++;
                totalOrbits += depth;
                route.add(position);
                nextIndex.add(0);
            } else {
                route.removeLast();
                if (!route.isEmpty()) {
                    position = route.peekLast();
                }
                nextIndex.removeLast();
                depth--;
            }
        }
        return totalOrbits;
    }

    private static int challengeTwelve(List<String> input) {
        HashMap<String, String> orbits = new HashMap<>();
        String[] objects;
        for (String relation : input) {
            objects = relation.split("\\)");
            orbits.put(objects[1], objects[0]);
            //I admit this is slightly mounting the christmas tree on the ceiling,
            //but there's no need to have a fully connected tree in this problem.
            //Since every node has exactly one parent there is no need for a list.
        }
        LinkedList<String> myRoute = new LinkedList<>();
        HashSet<String> myRouteSet = new HashSet<>();
        LinkedList<String> santaRoute = new LinkedList<>();
        HashSet<String> santaRouteSet = new HashSet<>();
        String me = "YOU";
        String santa = "SAN";
        //Basic idea here: Since this orbital construct is a tree, we know that
        //the two nodes have exactly one common ancestor, and exactly one parent.
        //As such, traverse up the tree instead of down until an ancestor is present
        //in both routes, then prune the length down to this shared ancestor since
        //one route will probably be longer than the other, continuing upwards
        //aimlessly.
        //The Sets are not strictly necessary, but serve to give faster checks
        //for whether a node is present in the other route. O(1) is good.
        //Notably this has absolutely no protection against running over the root
        //of the tree and crashing horribly. But that didn't happen so hooray.
        while (true) {
            me = orbits.get(me);
            myRoute.add(me);
            myRouteSet.add(me);
            santa = orbits.get(santa);
            santaRoute.add(santa);
            santaRouteSet.add(santa);
            if (myRouteSet.contains(santa)) {
                break;
            } else if (santaRouteSet.contains(me)) {
                santa = me; //This line amuses me.
                break;
            }
        }
        return myRoute.indexOf(santa) + santaRoute.indexOf(santa);
    }
}
