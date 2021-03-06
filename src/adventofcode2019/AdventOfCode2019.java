/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2019;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

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
            case 13:
                result = challengeThirteen(input);
                break;
            case 14:
                result = (int) challengeFourteen(input);
                break;
            case 15:
                result = challengeFifteen(input);
                break;
            case 16:
                result = challengeSixteen(input);
                break;
            case 17:
                result = challengeSeventeen(input);
                break;
            case 18:
                result = challengeEighteen(input);
                break;
            case 19:
                result = challengeNineteen(input);
                break;
            case 20:
                result = challengeTwenty(input);
                break;
            case 21:
                result = challengeTwentyOne(input);
                break;
            case 22:
                result = challengeTwentyTwo(input);
                break;
            case 23:
                result = challengeTwentyThree(input);
                break;
            case 24:
                result = challengeTwentyFour(input);
                break;
            case 25:
                result = challengeTwentyFive(input);
                break;
            case 26:
                result = challengeTwentySix(input);
                break;
            case 27:
                result = challengeTwentySeven(input);
                break;
            case 28:
                result = challengeTwentyEight(input);
                break;
            case 29:
                result = challengeTwentyNine(input);
                break;
            case 30:
                result = challengeThirty(input);
                break;
            case 31:
                result = challengeThirtyOne(input);
                break;
            case 32:
                result = challengeThirtyTwo(input);
                break;
            case 33:
                result = challengeThirtyThree(input);
                break;
            case 34:
                result = challengeThirtyFour(input);
                break;

            case 41:
                result = challengeFortyOne(input);
                break;
            case 42:
                result = challengeFortyTwo(input);
                break;

            case 45:
                result = challengeFortyFive(input);
                break;
            case 46:
                result = challengeFortySix(input);
                break;
            case 47:
                result = challengeFortySeven(input);
                break;

            case 49:
                result = challengeFortyNine(input);
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
        int[] program = new int[numbers.length], programCopy;
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Integer.parseInt(numbers[i]);
        }

        program = Intcode.runProgram(program, () -> {
            Scanner in = new Scanner(System.in);
            System.out.println("Provide input");
            return in.nextInt();
        }, num -> {
            System.out.format("Output value: %d\n", num);
        });
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

    private static int challengeThirteen(List<String> input) {
        String[] numbers = input.get(0).split(",");
        int[] program = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Integer.parseInt(numbers[i]);
        }
        LinkedList<Integer> phaseSettings = new LinkedList<>();
        LinkedList<Integer> buffer = new LinkedList<>();
        return tryAll(program, phaseSettings, buffer, 0, 4);
    }

    private static int tryAll(int[] program, LinkedList<Integer> phaseSettings, LinkedList<Integer> buffer, int min, int max) {
        if (phaseSettings.size() == 5) {
            int[] copyProgram;
            buffer.add(0);
            for (int setting : phaseSettings) {
                buffer.add(0, setting);
                copyProgram = program.clone();
                Intcode.runProgram(copyProgram, () -> buffer.removeFirst(), num -> {
                    buffer.add(num);
                });
            }
            return buffer.remove();
        } else {
            int maxVal = Integer.MIN_VALUE, retVal;
            for (int i = min; i <= max; i++) {
                if (!phaseSettings.contains(i)) {
                    phaseSettings.add(i);
                    retVal = tryAll(program, phaseSettings, buffer, min, max);
                    maxVal = retVal > maxVal ? retVal : maxVal;
                    phaseSettings.removeLast();
                }
            }
            return maxVal;
        }
    }

    private static long challengeFourteen(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        int i;
        for (i = 0; i < numbers.length; i++) {
            program[i] = Integer.parseInt(numbers[i]);
        }
        LinkedList<Long> phaseSettings = new LinkedList<>();
        LinkedList<Amplifier> amplifiers = new LinkedList<>();
        for (i = 0; i < 5; i++) {
            amplifiers.add(new Amplifier(program.clone()));
            if (i > 0) {
                amplifiers.get(i).setInputBuffer(amplifiers.get(i - 1).getOutputBuffer());
            }
        }
        amplifiers.getFirst().setInputBuffer(amplifiers.getLast().getOutputBuffer());
        return tryAllAmps(program, phaseSettings, 5, 9, amplifiers);
    }

    private static long tryAllAmps(long[] program, LinkedList<Long> phaseSettings, int min, int max, LinkedList<Amplifier> amplifiers) {
        if (phaseSettings.size() == 5) {
            amplifiers.getFirst().getInputBuffer().add(Long.valueOf(0));
            for (int i = 0; i < 5; i++) {
                amplifiers.get(i).resetProgram();
                amplifiers.get(i).getInputBuffer().add(0, phaseSettings.get(i));
                amplifiers.get(i).runProgram();
            }
            boolean halted = false;
            while (!halted) {
                for (Amplifier current : amplifiers) {
                    halted = halted || current.runProgram();
                }
            }
            return amplifiers.getLast().getOutputBuffer().removeLast();
        } else {
            long maxVal = Integer.MIN_VALUE, retVal;
            for (long i = min; i <= max; i++) {
                if (!phaseSettings.contains(i)) {
                    phaseSettings.add(i);
                    retVal = tryAllAmps(program, phaseSettings, min, max, amplifiers);
                    maxVal = retVal > maxVal ? retVal : maxVal;
                    phaseSettings.removeLast();
                }
            }
            return maxVal;
        }
    }

    private static int challengeFifteen(List<String> input) {
        char[] image = input.get(0).toCharArray();
        int x = 25, y = 6, pixel, i, j;
        int layerSize = x * y;
        int numOfLayers = image.length / layerSize;
        int[][] pixelValues = new int[numOfLayers][3];
        for (i = 0; i < numOfLayers; i++) {
            for (j = 0; j < layerSize; j++) {
                pixel = image[i * layerSize + j] - '0';
                pixelValues[i][pixel]++;
            }
        }
        int max = Integer.MAX_VALUE;
        int result = 0;
        for (i = 0; i < numOfLayers; i++) {
            if (pixelValues[i][0] < max) {
                result = pixelValues[i][1] * pixelValues[i][2];
                max = pixelValues[i][0];
            }
        }
        return result;
    }

    private static int challengeSixteen(List<String> input) {
        char[] imageData = input.get(0).toCharArray();
        int x = 25, y = 6, i, j, k, pixel, pixelsAssigned = 0;
        int layerSize = x * y, layer = 0;
        int numOfLayers = imageData.length / layerSize;
        char[][] image = new char[y][x];
        char[][][] layeredImage = new char[y][x][numOfLayers];
        for (i = 0; i < y; i++) {
            for (j = 0; j < x; j++) {
                image[i][j] = 'F';
                for (k = 0; k < numOfLayers; k++) {
                    layeredImage[i][j][k] = imageData[k * layerSize + i * x + j];
                }
            }
        }

        while (pixelsAssigned < layerSize) {
            for (i = 0; i < y; i++) {
                for (j = 0; j < x; j++) {
                    if (image[i][j] == 'F' && layeredImage[i][j][layer] != '2') {
                        image[i][j] = layeredImage[i][j][layer];
                        pixelsAssigned++;
                    }
                }
            }
            layer++;
        }
        try (FileWriter writer = new FileWriter("output16.txt")) {
            for (i = 0; i < y; i++) {
                writer.write(image[i]);
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Oh noes! Following exception:" + e.getMessage());
        }
        return 0;
        //While I COULD make a letter parser given that I have very distinct pixel
        //values here, that seems a bit excessive. I can simply look at them with
        //my human eyeholes.
    }

    private static int challengeSeventeen(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Long.parseLong(numbers[i]);
        }
        LinkedList<Long> inputBuffer = new LinkedList<>();
        inputBuffer.add((long) 1);
        Amplifier amp = new Amplifier(program);
        amp.setInputBuffer(inputBuffer);
        amp.runProgram();
        for (long output : amp.getOutputBuffer()) {
            System.out.println(output);
        }
        return 0;
    }

    private static int challengeEighteen(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Long.parseLong(numbers[i]);
        }
        LinkedList<Long> inputBuffer = new LinkedList<>();
        inputBuffer.add((long) 2);
        Amplifier amp = new Amplifier(program);
        amp.setInputBuffer(inputBuffer);
        System.out.println("Starting calculations now...");
        long startTime = System.currentTimeMillis();
        amp.runProgram();
        System.out.format("Finished calculations in a mere %d milliseconds. Aren't I quick! The coordinates are: ", System.currentTimeMillis() - startTime);
        for (long output : amp.getOutputBuffer()) {
            System.out.println(output);
        }
        return 0;
    }

    private static int challengeNineteen(List<String> input) {
        int y = 0, x, max = Integer.MIN_VALUE;
        int[] relCoords = new int[2];
        double totalLength, epsilon = 0.0001;
        double[] vector;
        boolean addElement;
        HashSet<double[]> vectors = new HashSet<>();
        HashSet<int[]> asteroids = new HashSet<>();
        for (String line : input) {
            for (x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#') {
                    asteroids.add(new int[]{x, y});
                }
            }
            y++;
        }
        for (int[] current : asteroids) {
            vectors.clear();
            for (int[] other : asteroids) {
                if (current != other) {
                    vector = new double[2];
                    relCoords[0] = other[0] - current[0];
                    relCoords[1] = other[1] - current[1];
                    totalLength = Math.sqrt(relCoords[0] * relCoords[0] + relCoords[1] * relCoords[1]);
                    for (int i = 0; i < 2; i++) {
                        vector[i] = relCoords[i] / totalLength;
                    }
                    addElement = true;
                    for (double[] otherVector : vectors) {
                        if (Math.abs(otherVector[0] - vector[0]) < epsilon && Math.abs(otherVector[1] - vector[1]) < epsilon) {
                            addElement = false;
                        }
                    }
                    if (addElement) {
                        vectors.add(vector);
                    }
                }
            }
            max = max < vectors.size() ? vectors.size() : max;
        }
        return max;
    }

    private static int challengeTwenty(List<String> input) {
        //Station position is [27,19]
        int vaporised = 0, i = 0;
        int[] lastAsteroid = new int[2];
        int y = 0, x;
        int[] relCoords = new int[2];
        final int[] current = new int[]{27, 19}, relToCurr = new int[4];
        double totalLength, epsilon = 0.0001;
        double[] vector;
        final double[] distances = new double[2];
        boolean newElement;
        LinkedList<double[]> vectors = new LinkedList<>();
        HashSet<int[]> asteroids = new HashSet<>();
        HashMap<double[], LinkedList<int[]>> positions = new HashMap<>();
        for (String line : input) {
            for (x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '#' && !(x == current[0] && y == current[1])) {
                    asteroids.add(new int[]{x, y});
                }
            }
            y++;
        }
        for (int[] other : asteroids) {
            vector = new double[2];
            relCoords[0] = other[0] - current[0];
            relCoords[1] = other[1] - current[1];
            totalLength = Math.sqrt(relCoords[0] * relCoords[0] + relCoords[1] * relCoords[1]);
            for (i = 0; i < 2; i++) {
                vector[i] = relCoords[i] / totalLength;
            }
            newElement = true;
            for (double[] otherVector : vectors) {
                if (Math.abs(otherVector[0] - vector[0]) < epsilon && Math.abs(otherVector[1] - vector[1]) < epsilon) {
                    newElement = false;
                    vector = otherVector;
                }
            }
            if (newElement) {
                vectors.add(vector);
                positions.put(vector, new LinkedList<>());
            }
            positions.get(vector).add(other);
        }
        vectors.sort((double[] o1, double[] o2) -> {
            if (o1[1] == -1.0) {
                return -1;
            }
            if (o2[1] == -1.0) {
                return 1;
            }
            if (Math.signum(o1[0]) == Math.signum(o2[0])) {
                return (int) (Math.signum(o1[1] - o2[1]) * Math.signum(o1[0]));
            } else {
                return (int) (Math.signum(o2[0]) - Math.signum(o1[0]));
            }
        });
        for (LinkedList<int[]> angle : positions.values()) {
            angle.sort((int[] o1, int[] o2) -> {
                relToCurr[0] = o1[0] - current[0];
                relToCurr[1] = o1[1] - current[1];
                relToCurr[2] = o2[0] - current[0];
                relToCurr[3] = o2[1] - current[1];
                distances[0] = Math.sqrt(relToCurr[0] * relToCurr[0] + relToCurr[1] * relToCurr[1]);
                distances[1] = Math.sqrt(relToCurr[2] * relToCurr[2] + relToCurr[3] * relToCurr[3]);
                return (int) (distances[0] - distances[1]);
            });
        }
        i = 0;
        while (vaporised < 200) {
            vector = vectors.get(i);
            lastAsteroid = positions.get(vector).removeFirst();
            if (positions.get(vector).isEmpty()) {
                positions.remove(vector);
            }
            i++;
            i %= vectors.size();
            vaporised++;
        }
        return lastAsteroid[0] * 100 + lastAsteroid[1];
    }

    private static int challengeTwentyOne(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Long.parseLong(numbers[i]);
        }
        LinkedList<Long> inputBuffer = new LinkedList<>();
        Amplifier amp = new Amplifier(program);
        amp.setInputBuffer(inputBuffer);
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        HashMap<Integer, HashMap<Integer, Long>> hull = new HashMap<>();
        int robotX = 0, robotY = 0, dirHolder, squaresPainted = 0;
        int[] direction = new int[]{0, 1};
        boolean halted = false;
        while (!halted) {
            if (!hull.containsKey(robotX)) {
                hull.put(robotX, new HashMap<>());
            }
            if (!hull.get(robotX).containsKey(robotY)) {
                hull.get(robotX).put(robotY, (long) 0);
                squaresPainted++;
            }
            inputBuffer.add(hull.get(robotX).get(robotY));
            halted = amp.runProgram();
            if (!outputBuffer.isEmpty()) {
                hull.get(robotX).put(robotY, outputBuffer.removeFirst());
                if (outputBuffer.removeFirst() == 0) {
                    dirHolder = direction[0];
                    direction[0] = -direction[1];
                    direction[1] = dirHolder;
                } else {
                    dirHolder = direction[0];
                    direction[0] = direction[1];
                    direction[1] = -dirHolder;
                }
                robotX += direction[0];
                robotY += direction[1];
            }
        }
        return squaresPainted;
    }

    private static int challengeTwentyTwo(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            program[i] = Long.parseLong(numbers[i]);
        }
        LinkedList<Long> inputBuffer = new LinkedList<>();
        Amplifier amp = new Amplifier(program);
        amp.setInputBuffer(inputBuffer);
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        HashMap<Integer, HashMap<Integer, Long>> hull = new HashMap<>();
        int robotY = 0, robotX = 0, dirHolder, squaresPainted = 0;
        int[] direction = new int[]{1, 0};
        boolean halted = false;
        hull.put(robotY, new HashMap<>());
        hull.get(robotY).put(robotX, (long) 1);
        while (!halted) {
            if (!hull.containsKey(robotY)) {
                hull.put(robotY, new HashMap<>());
            }
            if (!hull.get(robotY).containsKey(robotX)) {
                hull.get(robotY).put(robotX, (long) 0);
                squaresPainted++;
            }
            inputBuffer.add(hull.get(robotY).get(robotX));
            halted = amp.runProgram();
            if (!outputBuffer.isEmpty()) {
                hull.get(robotY).put(robotX, outputBuffer.removeFirst());
                if (outputBuffer.removeFirst() == 0) {
                    dirHolder = direction[1];
                    direction[1] = -direction[0];
                    direction[0] = dirHolder;
                } else {
                    dirHolder = direction[1];
                    direction[1] = direction[0];
                    direction[0] = -dirHolder;
                }
                robotX += direction[1];
                robotY += direction[0];
            }
        }
        try (FileWriter writer = new FileWriter("output22.txt")) {
            //Notable: You need to insert a . on lines 2, 3, and 6 (1-indexed, so the first
            //line is line 1) to make the message legible. A more diligent coder
            //would scan for the minimum and maximum indices used and pad automatically.
            //Or initialise an array as it very clearly wants of me, but that would
            //still require the initial runthrough so I might as well read off the maps
            //I have now.
            //For future reference: The keySets should ABSOLUTELY be converted
            //into a List and sorted. It so happens that the natural order in
            //which they are returned (0 -> -5 for Y, 0 -> 42 for X) perfectly
            //arranges the output. However, since a hash value being arbitrary and
            //unconnected to the actual value is kind of the entire bloody point
            //of the things, I wouldn't expect this to always be the case.
            for (Integer lineKey : hull.keySet()) {
                for (Integer colourKey : hull.get(lineKey).keySet()) {
                    writer.write(hull.get(lineKey).get(colourKey) == 0 ? '.' : '#');
                    System.out.println(lineKey + " " + colourKey);
                }
                writer.write("\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return squaresPainted;
    }

    private static int challengeTwentyThree(List<String> input) {
        String[] numbers;
        String line;
        int i, j, k, iterations, alignment, result, pot, kin;
        int[][] positions = new int[input.size()][3];
        int[][] velocities = new int[input.size()][3];
        for (i = 0; i < input.size(); i++) {
            line = input.get(i);
            numbers = line.replaceAll("[^\\d,-]", "").split(",");
            for (j = 0; j < 3; j++) {
                positions[i][j] = Integer.parseInt(numbers[j]);
            }
        }
        for (iterations = 0; iterations < 1000; iterations++) {
            for (i = 0; i < positions.length; i++) {
                for (j = i + 1; j < positions.length; j++) {
                    for (k = 0; k < positions[i].length; k++) {
                        alignment = (int) Math.signum(positions[j][k] - positions[i][k]);
                        velocities[i][k] += alignment;
                        velocities[j][k] -= alignment;
                    }
                }
                for (k = 0; k < positions[i].length; k++) {
                    positions[i][k] += velocities[i][k];
                }
            }
        }
        result = 0;
        for (i = 0; i < positions.length; i++) {
            pot = 0;
            kin = 0;
            for (k = 0; k < positions[i].length; k++) {
                pot += Math.abs(positions[i][k]);
                kin += Math.abs(velocities[i][k]);
            }
            result += pot * kin;
        }
        return result;
    }

    private static int challengeTwentyFour(List<String> input) {
        String[] numbers;
        String line;
        int moon, otherMoon, axis, iterations, alignment;
        int[] cycleTimes = new int[3];
        int[][] originalPositions = new int[input.size()][3];
        int[][] positions = new int[input.size()][3];
        int[][] velocities = new int[input.size()][3];
        boolean cyclesFound = false, possibleCycle;
        long result;
        for (moon = 0; moon < input.size(); moon++) {
            line = input.get(moon);
            numbers = line.replaceAll("[^\\d,-]", "").split(",");
            for (axis = 0; axis < 3; axis++) {
                positions[moon][axis] = Integer.parseInt(numbers[axis]);
                originalPositions[moon][axis] = positions[moon][axis];
            }
        }
        iterations = 1;
        while (!cyclesFound) {
            for (moon = 0; moon < positions.length; moon++) {
                for (otherMoon = moon + 1; otherMoon < positions.length; otherMoon++) {
                    for (axis = 0; axis < positions[moon].length; axis++) {
                        alignment = (int) Math.signum(positions[otherMoon][axis] - positions[moon][axis]);
                        velocities[moon][axis] += alignment;
                        velocities[otherMoon][axis] -= alignment;
                    }
                }
                for (axis = 0; axis < positions[moon].length; axis++) {
                    positions[moon][axis] += velocities[moon][axis];
                }
            }
            iterations++;
            cyclesFound = true;
            for (axis = 0; axis < positions[0].length; axis++) {
                possibleCycle = true;
                for (moon = 0; moon < positions.length; moon++) {
                    if (positions[moon][axis] != originalPositions[moon][axis]) {
                        possibleCycle = false;
                        break;
                    }
                }
                if (possibleCycle && cycleTimes[axis] == 0) {
                    cycleTimes[axis] = iterations;
                }
                cyclesFound = cyclesFound && cycleTimes[axis] != 0;
            }
        }
        //The basic idea here: Since axes are independent from one another, they
        //each have their own cycles. Finding the total cycle is then a matter of
        //finding where each cycle finishes at the same time.
        //Interestingly, I've only had to check for positions, not velocities.
        //It seems trivial to construct a case where that is an insufficient
        //condition, so use with caution. In this case though, it worked. So.
        for (axis = 0; axis < cycleTimes.length; axis++) {
            System.out.format("The positions on axis %d loop every %d iterations.\n", axis, cycleTimes[axis]);
        }
        System.out.print("Given that, let there be light!\n"
                + "The total iterations to reach the original state are: ");
        result = cycleTimes[0];
        for (axis = 1; axis < cycleTimes.length; axis++) {
            result = lcm(result, cycleTimes[axis]);
        }
        System.out.println(result);
        return 0;
    }

    private static long lcm(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        long lcm;
        if (a < b) {
            lcm = a;
            a = b;
            b = lcm;
        }
        lcm = a;
        while (lcm % b != 0) {
            lcm += a;
        }
        return lcm;
    }

    private static int challengeTwentyFive(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        HashMap<String, Long> numberCache = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (!numberCache.containsKey(numbers[i])) {
                numberCache.put(numbers[i], Long.parseLong(numbers[i]));
            }
            program[i] = numberCache.get(numbers[i]);
        }
        LinkedList<Long> inputBuffer = new LinkedList<>();
        Amplifier amp = new Amplifier(program);
        amp.setInputBuffer(inputBuffer);
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        char[] tiles = new char[]{' ', 'X', '~', '=', 'o'};
        char[][] board = new char[44][24]; //X/Y
        int x, y, blocks = 0;
        amp.runProgram();
        Long[] asArray = new Long[0];
        asArray = outputBuffer.toArray(asArray);
        //I am leaving this here as a testament to what not to do. But see below
        //for the proper solution that is way faster and also actually empties
        //the output buffer.
        for (int i = 0; i < outputBuffer.size(); i += 3) {
            x = asArray[i].intValue();
            y = asArray[i + 1].intValue();
            board[x][y] = tiles[asArray[i + 2].intValue()];
            if (board[x][y] == '~') {
                blocks++;
            }
        }
        try (FileWriter writer = new FileWriter("output25.txt")) {
            for (y = 0; y < board[0].length; y++) {
                for (x = 0; x < board.length; x++) {
                    writer.append(board[x][y]);
                }
                writer.append('\n');
            }
        } catch (Exception e) {
            System.out.println("Oh no! Exception: " + e.getMessage());
        }
        return blocks;
    }

    private static int challengeTwentySix(List<String> input) {
        String[] numbers = input.get(0).split(",");
        long[] program = new long[numbers.length];
        HashMap<String, Long> numberCache = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (!numberCache.containsKey(numbers[i])) {
                numberCache.put(numbers[i], Long.parseLong(numbers[i]));
            }
            program[i] = numberCache.get(numbers[i]);
        }
        program[0] = 2; //I mean, who goes to space without any quarters? Scrub.
        LinkedList<Long> inputBuffer = new LinkedList<>();
        Amplifier amp = new Amplifier(program);
        amp.setInputBuffer(inputBuffer);
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        char[] tiles = new char[]{' ', 'X', '~', '=', 'o'};
        char[][] board = new char[44][24]; //X/Y
        int x = 0, y = 0, score = 0, ballX = 0, paddleX = 0;
        Long move = (long) 0;
        while (!amp.runProgram()) {
            while (!outputBuffer.isEmpty()) {
                x = outputBuffer.remove().intValue();
                y = outputBuffer.remove().intValue();
                if (x == -1 && y == 0) {
                    score = outputBuffer.remove().intValue();
                } else {
                    board[x][y] = tiles[outputBuffer.remove().intValue()];
                    if (board[x][y] == 'o') {
                        ballX = x;
                    } else if (board[x][y] == '=') {
                        paddleX = x;
                    }
                }
            }
            outputBuffer = new LinkedList<>();
            amp.setOutputBuffer(outputBuffer);
            //Uncomment to see my amazing high-tech AI in action. Warning: Lag.
            /*for (y = 0; y < board[0].length; y++) {
                for (x = 0; x < board.length; x++) {
                    System.out.print(board[x][y]);
                }
                System.out.println();
            }
            System.out.println("Current score: " + score);*/
            move = (long) Math.signum(ballX - paddleX);
            inputBuffer.add(move);
        }
        while (!outputBuffer.isEmpty()) {
            x = outputBuffer.remove().intValue();
            y = outputBuffer.remove().intValue();
            if (x == -1 && y == 0) {
                score = outputBuffer.remove().intValue();
                break;
            } else {
                outputBuffer.remove();
            }
        }
        return score;
    }

    private static int challengeTwentySeven(List<String> input) {
        HashMap<String, Chemical> knownChems = new HashMap<>();
        HashMap<String, Integer> numberCache = new HashMap<>();
        Chemical currentChem, requirement;
        String[] reaction, chemDescription;
        String chemName;
        int i, amount;
        for (String line : input) {
            reaction = line.split(" => ");
            chemDescription = reaction[1].split(" ");
            if (!numberCache.containsKey(chemDescription[0])) {
                numberCache.put(chemDescription[0], Integer.parseInt(chemDescription[0]));
            }
            chemName = chemDescription[1];
            if (!knownChems.containsKey(chemName)) {
                currentChem = new Chemical(chemName);
                knownChems.put(chemName, currentChem);
            } else {
                currentChem = knownChems.get(chemName);
            }
            amount = numberCache.get(chemDescription[0]);
            currentChem.setAmountProduced(amount);
            reaction = reaction[0].split(", ");
            for (i = 0; i < reaction.length; i++) {
                chemDescription = reaction[i].split(" ");
                if (!numberCache.containsKey(chemDescription[0])) {
                    numberCache.put(chemDescription[0], Integer.parseInt(chemDescription[0]));
                }
                amount = numberCache.get(chemDescription[0]);
                chemName = chemDescription[1];
                if (knownChems.containsKey(chemName)) {
                    requirement = knownChems.get(chemName);
                } else {
                    requirement = new Chemical(chemName);
                    knownChems.put(chemName, requirement);
                }
                currentChem.addRequired(requirement, amount);
            }
        }
        knownChems.get("ORE").setStock(Integer.MAX_VALUE);
        HashMap<Chemical, Long> costs = knownChems.get("FUEL").produce();
        return costs.get(knownChems.get("ORE")).intValue();
    }

    private static int challengeTwentyEight(List<String> input) {
        HashMap<String, Chemical> knownChems = new HashMap<>();
        HashMap<String, Integer> numberCache = new HashMap<>();
        Chemical currentChem, requirement;
        String[] reaction, chemDescription;
        String chemName;
        int i, amount;
        for (String line : input) {
            reaction = line.split(" => ");
            chemDescription = reaction[1].split(" ");
            if (!numberCache.containsKey(chemDescription[0])) {
                numberCache.put(chemDescription[0], Integer.parseInt(chemDescription[0]));
            }
            chemName = chemDescription[1];
            if (!knownChems.containsKey(chemName)) {
                currentChem = new Chemical(chemName);
                knownChems.put(chemName, currentChem);
            } else {
                currentChem = knownChems.get(chemName);
            }
            amount = numberCache.get(chemDescription[0]);
            currentChem.setAmountProduced(amount);
            reaction = reaction[0].split(", ");
            for (i = 0; i < reaction.length; i++) {
                chemDescription = reaction[i].split(" ");
                if (!numberCache.containsKey(chemDescription[0])) {
                    numberCache.put(chemDescription[0], Integer.parseInt(chemDescription[0]));
                }
                amount = numberCache.get(chemDescription[0]);
                chemName = chemDescription[1];
                if (knownChems.containsKey(chemName)) {
                    requirement = knownChems.get(chemName);
                } else {
                    requirement = new Chemical(chemName);
                    knownChems.put(chemName, requirement);
                }
                currentChem.addRequired(requirement, amount);
            }
        }
        long oreAvailable = ((long) 1000000) * 1000000;
        long remnantReactions = Long.MAX_VALUE, currentLifetime;
        Chemical fuel = knownChems.get("FUEL");
        Chemical ore = knownChems.get("ORE");
        knownChems.remove(ore.getName());
        ore.setStock(oreAvailable);
        HashMap<Chemical, Long> costs = fuel.produce();
        return 0;
    }

    private static int challengeTwentyNine(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        LinkedList<Long> inputBuffer = new LinkedList<>();
        amp.setInputBuffer(inputBuffer);
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        HashMap<Integer, HashMap<Integer, Integer>> board = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Integer>> distances = new HashMap<>();
        LinkedList<int[]> unexplored = new LinkedList<>();
        LinkedList<int[]> toBeUpdated = new LinkedList<>();
        board.put(0, new HashMap<>());
        board.get(0).put(0, 1);
        distances.put(0, new HashMap<>());
        distances.get(0).put(0, 1);
        int robotX = 0, robotY = 0, retVal = 0, i, min, nextMove = 0;
        int minX, maxX, minY, maxY;
        int[] currentDir, point, distPoint, adjacent = new int[2], target = new int[2];
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        do {
            for (i = 0; i < directions.length; i++) {
                currentDir = directions[i];
                adjacent[0] = robotX + currentDir[0];
                adjacent[1] = robotY + currentDir[1];
                if (!board.containsKey(adjacent[0])) {
                    board.put(adjacent[0], new HashMap<>());
                    distances.put(adjacent[0], new HashMap<>());
                }
                if (!board.get(adjacent[0]).containsKey(adjacent[1])) {
                    board.get(adjacent[0]).put(adjacent[1], -1);
                    distances.get(adjacent[0]).put(adjacent[1], 0);
                    unexplored.add(new int[]{adjacent[0], adjacent[1]});
                }
            }
            point = unexplored.removeLast();
            toBeUpdated.add(point);
            distances.values().stream().forEach((column) -> {
                column.keySet().stream().forEach((rowKey) -> {
                    column.put(rowKey, Integer.MAX_VALUE);
                });
            });
            distances.get(point[0]).put(point[1], 0);
            while (!toBeUpdated.isEmpty()) {
                distPoint = toBeUpdated.removeFirst();
                for (i = 0; i < directions.length; i++) {
                    currentDir = directions[i];
                    adjacent[0] = distPoint[0] + currentDir[0];
                    adjacent[1] = distPoint[1] + currentDir[1];
                    if (distances.containsKey(adjacent[0]) && distances.get(adjacent[0]).containsKey(adjacent[1])
                            && board.get(adjacent[0]).get(adjacent[1]) > 0
                            && distances.get(adjacent[0]).get(adjacent[1]) > distances.get(distPoint[0]).get(distPoint[1])) {
                        distances.get(adjacent[0]).put(adjacent[1], distances.get(distPoint[0]).get(distPoint[1]) + 1);
                        toBeUpdated.add(new int[]{adjacent[0], adjacent[1]});
                    }
                }
            }
            while (!(robotX == point[0] && robotY == point[1])) {
                min = Integer.MAX_VALUE;
                //Uncomment this and commented code below for more spam.
                /*minX = Integer.MAX_VALUE;
                maxX = Integer.MIN_VALUE;
                minY = Integer.MAX_VALUE;
                maxY = Integer.MIN_VALUE;
                for (Integer X : board.keySet()) {
                    minX = X < minX ? X : minX;
                    maxX = X > maxX ? X : maxX;
                    for (Integer Y : board.get(X).keySet()) {
                        minY = Y < minY ? Y : minY;
                        maxY = Y > maxY ? Y : maxY;
                    }
                }
                for (int y = maxY; y >= minY; y--) {
                    for (int x = minX; x <= maxX; x++) {
                        if (!(board.containsKey(x) && board.get(x).containsKey(y))) {
                            System.out.print(' ');
                        } else if (x == robotX && y == robotY) {
                            System.out.print('R');
                        } else {
                            switch (board.get(x).get(y)) {
                                case 0:
                                    System.out.print('#');
                                    break;
                                case 1:
                                    System.out.print('.');
                                    break;
                                case -1:
                                    System.out.print('?');
                            }
                        }
                    }
                    System.out.println();
                }
                System.out.println("Coordinates: " + robotX + " " + robotY);
                System.out.println("Target: " + point[0] + " " + point[1]);*/
                for (i = 0; i < directions.length; i++) {
                    currentDir = directions[i];
                    adjacent[0] = robotX + currentDir[0];
                    adjacent[1] = robotY + currentDir[1];
                    if (distances.get(adjacent[0]).get(adjacent[1]) < min) {
                        min = distances.get(adjacent[0]).get(adjacent[1]);
                        nextMove = i;
                    }
                    //System.out.println("Value of next field: " + distances.get(adjacent[0]).get(adjacent[1]));
                }
                //System.out.println("Next move: " + nextMove);
                adjacent[0] = robotX + directions[nextMove][0];
                adjacent[1] = robotY + directions[nextMove][1];
                inputBuffer.add((long) nextMove + 1);
                amp.runProgram();
                retVal = outputBuffer.removeFirst().intValue();
                robotX = adjacent[0];
                robotY = adjacent[1];
            }
            board.get(adjacent[0]).put(adjacent[1], retVal);
            if (retVal == 0) {
                //Just bonked, take a step back. Easier than not stepping in the loop.
                robotX -= directions[nextMove][0];
                robotY -= directions[nextMove][1];
            } else {
                //Step successful. Add newly discovered unexplored tiles.
                if (retVal == 2) {
                    target[0] = robotX;
                    target[1] = robotY;
                }
                for (i = 0; i < directions.length; i++) {
                    currentDir = directions[i];
                    adjacent[0] = robotX + currentDir[0];
                    adjacent[1] = robotY + currentDir[1];
                    if (!board.containsKey(adjacent[0])) {
                        board.put(adjacent[0], new HashMap<>());
                        distances.put(adjacent[0], new HashMap<>());
                    }
                    if (!board.get(adjacent[0]).containsKey(adjacent[1])) {
                        board.get(adjacent[0]).put(adjacent[1], -1);
                        distances.get(adjacent[0]).put(adjacent[1], 0);
                        unexplored.add(new int[]{adjacent[0], adjacent[1]});
                    }
                }
            }
        } while (!unexplored.isEmpty());
        toBeUpdated.add(new int[]{0, 0});
        distances.values().stream().forEach((column) -> {
            column.keySet().stream().forEach((rowKey) -> {
                column.put(rowKey, Integer.MAX_VALUE);
            });
        });
        distances.get(0).put(0, 0);
        while (!toBeUpdated.isEmpty()) {
            distPoint = toBeUpdated.removeFirst();
            for (i = 0; i < directions.length; i++) {
                currentDir = directions[i];
                adjacent[0] = distPoint[0] + currentDir[0];
                adjacent[1] = distPoint[1] + currentDir[1];
                if (distances.containsKey(adjacent[0]) && distances.get(adjacent[0]).containsKey(adjacent[1])
                        && board.get(adjacent[0]).get(adjacent[1]) > 0
                        && distances.get(adjacent[0]).get(adjacent[1]) > distances.get(distPoint[0]).get(distPoint[1])) {
                    distances.get(adjacent[0]).put(adjacent[1], distances.get(distPoint[0]).get(distPoint[1]) + 1);
                    toBeUpdated.add(new int[]{adjacent[0], adjacent[1]});
                }
            }
        }
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        for (Integer X : board.keySet()) {
            minX = X < minX ? X : minX;
            maxX = X > maxX ? X : maxX;
            for (Integer Y : board.get(X).keySet()) {
                minY = Y < minY ? Y : minY;
                maxY = Y > maxY ? Y : maxY;
            }
        }
        for (int y = maxY; y >= minY; y--) {
            System.out.print(y + "\t");
            for (int x = minX; x <= maxX; x++) {
                if (!(board.containsKey(x) && board.get(x).containsKey(y))) {
                    System.out.print(' ');
                } else if (x == robotX && y == robotY) {
                    System.out.print('R');
                } else {
                    switch (board.get(x).get(y)) {
                        case 0:
                            System.out.print('#');
                            break;
                        case 1:
                            System.out.print('.');
                            break;
                        case 2:
                            System.out.print('T');
                            break;
                        case -1:
                            System.out.print('?');
                    }
                }
            }
            System.out.println();
        }

        return distances.get(target[0]).get(target[1]);
    }

    private static int challengeThirty(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        LinkedList<Long> inputBuffer = new LinkedList<>();
        amp.setInputBuffer(inputBuffer);
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        HashMap<Integer, HashMap<Integer, Integer>> board = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Integer>> distances = new HashMap<>();
        LinkedList<int[]> unexplored = new LinkedList<>();
        LinkedList<int[]> toBeUpdated = new LinkedList<>();
        board.put(0, new HashMap<>());
        board.get(0).put(0, 1);
        distances.put(0, new HashMap<>());
        distances.get(0).put(0, 1);
        int robotX = 0, robotY = 0, retVal = 0, i, min, nextMove = 0;
        int minX, maxX, minY, maxY;
        int[] currentDir, point, distPoint, adjacent = new int[2], target = new int[2];
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        do {
            for (i = 0; i < directions.length; i++) {
                currentDir = directions[i];
                adjacent[0] = robotX + currentDir[0];
                adjacent[1] = robotY + currentDir[1];
                if (!board.containsKey(adjacent[0])) {
                    board.put(adjacent[0], new HashMap<>());
                    distances.put(adjacent[0], new HashMap<>());
                }
                if (!board.get(adjacent[0]).containsKey(adjacent[1])) {
                    board.get(adjacent[0]).put(adjacent[1], -1);
                    distances.get(adjacent[0]).put(adjacent[1], 0);
                    unexplored.add(new int[]{adjacent[0], adjacent[1]});
                }
            }
            point = unexplored.removeLast();
            toBeUpdated.add(point);
            distances.values().stream().forEach((column) -> {
                column.keySet().stream().forEach((rowKey) -> {
                    column.put(rowKey, Integer.MAX_VALUE);
                });
            });
            distances.get(point[0]).put(point[1], 0);
            while (!toBeUpdated.isEmpty()) {
                distPoint = toBeUpdated.removeFirst();
                for (i = 0; i < directions.length; i++) {
                    currentDir = directions[i];
                    adjacent[0] = distPoint[0] + currentDir[0];
                    adjacent[1] = distPoint[1] + currentDir[1];
                    if (distances.containsKey(adjacent[0]) && distances.get(adjacent[0]).containsKey(adjacent[1])
                            && board.get(adjacent[0]).get(adjacent[1]) > 0
                            && distances.get(adjacent[0]).get(adjacent[1]) > distances.get(distPoint[0]).get(distPoint[1])) {
                        distances.get(adjacent[0]).put(adjacent[1], distances.get(distPoint[0]).get(distPoint[1]) + 1);
                        toBeUpdated.add(new int[]{adjacent[0], adjacent[1]});
                    }
                }
            }
            while (!(robotX == point[0] && robotY == point[1])) {
                min = Integer.MAX_VALUE;
                //Uncomment this and commented code below for more spam.
                /*minX = Integer.MAX_VALUE;
                maxX = Integer.MIN_VALUE;
                minY = Integer.MAX_VALUE;
                maxY = Integer.MIN_VALUE;
                for (Integer X : board.keySet()) {
                    minX = X < minX ? X : minX;
                    maxX = X > maxX ? X : maxX;
                    for (Integer Y : board.get(X).keySet()) {
                        minY = Y < minY ? Y : minY;
                        maxY = Y > maxY ? Y : maxY;
                    }
                }
                for (int y = maxY; y >= minY; y--) {
                    for (int x = minX; x <= maxX; x++) {
                        if (!(board.containsKey(x) && board.get(x).containsKey(y))) {
                            System.out.print(' ');
                        } else if (x == robotX && y == robotY) {
                            System.out.print('R');
                        } else {
                            switch (board.get(x).get(y)) {
                                case 0:
                                    System.out.print('#');
                                    break;
                                case 1:
                                    System.out.print('.');
                                    break;
                                case -1:
                                    System.out.print('?');
                            }
                        }
                    }
                    System.out.println();
                }
                System.out.println("Coordinates: " + robotX + " " + robotY);
                System.out.println("Target: " + point[0] + " " + point[1]);*/
                for (i = 0; i < directions.length; i++) {
                    currentDir = directions[i];
                    adjacent[0] = robotX + currentDir[0];
                    adjacent[1] = robotY + currentDir[1];
                    if (distances.get(adjacent[0]).get(adjacent[1]) < min) {
                        min = distances.get(adjacent[0]).get(adjacent[1]);
                        nextMove = i;
                    }
                    //System.out.println("Value of next field: " + distances.get(adjacent[0]).get(adjacent[1]));
                }
                //System.out.println("Next move: " + nextMove);
                adjacent[0] = robotX + directions[nextMove][0];
                adjacent[1] = robotY + directions[nextMove][1];
                inputBuffer.add((long) nextMove + 1);
                amp.runProgram();
                retVal = outputBuffer.removeFirst().intValue();
                robotX = adjacent[0];
                robotY = adjacent[1];
            }
            board.get(adjacent[0]).put(adjacent[1], retVal);
            if (retVal == 0) {
                //Just bonked, take a step back. Easier than not stepping in the loop.
                robotX -= directions[nextMove][0];
                robotY -= directions[nextMove][1];
            } else {
                //Step successful. Add newly discovered unexplored tiles.
                if (retVal == 2) {
                    target[0] = robotX;
                    target[1] = robotY;
                }
                for (i = 0; i < directions.length; i++) {
                    currentDir = directions[i];
                    adjacent[0] = robotX + currentDir[0];
                    adjacent[1] = robotY + currentDir[1];
                    if (!board.containsKey(adjacent[0])) {
                        board.put(adjacent[0], new HashMap<>());
                        distances.put(adjacent[0], new HashMap<>());
                    }
                    if (!board.get(adjacent[0]).containsKey(adjacent[1])) {
                        board.get(adjacent[0]).put(adjacent[1], -1);
                        distances.get(adjacent[0]).put(adjacent[1], 0);
                        unexplored.add(new int[]{adjacent[0], adjacent[1]});
                    }
                }
            }
        } while (!unexplored.isEmpty());
        toBeUpdated.add(target);
        distances.values().stream().forEach((column) -> {
            column.keySet().stream().forEach((rowKey) -> {
                column.put(rowKey, Integer.MAX_VALUE);
            });
        });
        //For those curious what I changed: Since 29 already creates a full map
        //of the area (because I was too lazy to implement simple path discovery
        //and pathfinding from target to 0/0), all I had to change was to change
        //the origin point to the target position. Since Dijkstra's algorithm
        //works miracles, it is always accurate to take the step distance from
        //the target to the farthest point as the time for the oxygen to spread.
        //
        //It would be significantly cleaner code to simply pass a boolean to method
        //29. It really would be. But I didn't feel like it.
        distances.get(target[0]).put(target[1], 0);
        int max = Integer.MIN_VALUE;
        while (!toBeUpdated.isEmpty()) {
            distPoint = toBeUpdated.removeFirst();
            for (i = 0; i < directions.length; i++) {
                currentDir = directions[i];
                adjacent[0] = distPoint[0] + currentDir[0];
                adjacent[1] = distPoint[1] + currentDir[1];
                if (distances.containsKey(adjacent[0]) && distances.get(adjacent[0]).containsKey(adjacent[1])
                        && board.get(adjacent[0]).get(adjacent[1]) > 0
                        && distances.get(adjacent[0]).get(adjacent[1]) > distances.get(distPoint[0]).get(distPoint[1])) {
                    distances.get(adjacent[0]).put(adjacent[1], distances.get(distPoint[0]).get(distPoint[1]) + 1);
                    toBeUpdated.add(new int[]{adjacent[0], adjacent[1]});
                    max = distances.get(adjacent[0]).get(adjacent[1]) > max ? distances.get(adjacent[0]).get(adjacent[1]) : max;
                }
            }
        }
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        for (Integer X : board.keySet()) {
            minX = X < minX ? X : minX;
            maxX = X > maxX ? X : maxX;
            for (Integer Y : board.get(X).keySet()) {
                minY = Y < minY ? Y : minY;
                maxY = Y > maxY ? Y : maxY;
            }
        }
        for (int y = maxY; y >= minY; y--) {
            System.out.print(y + "\t");
            for (int x = minX; x <= maxX; x++) {
                if (!(board.containsKey(x) && board.get(x).containsKey(y))) {
                    System.out.print(' ');
                } else if (x == robotX && y == robotY) {
                    System.out.print('R');
                } else {
                    switch (board.get(x).get(y)) {
                        case 0:
                            System.out.print('#');
                            break;
                        case 1:
                            System.out.print('.');
                            break;
                        case 2:
                            System.out.print('T');
                            break;
                        case -1:
                            System.out.print('?');
                    }
                }
            }
            System.out.println();
        }

        return max;
    }

    private static long[] parseIntcode(String input) {
        String[] numbers = input.split(",");
        long[] program = new long[numbers.length];
        HashMap<String, Long> numberCache = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (!numberCache.containsKey(numbers[i])) {
                numberCache.put(numbers[i], Long.parseLong(numbers[i]));
            }
            program[i] = numberCache.get(numbers[i]);
        }
        return program;
    }

    private static int challengeThirtyOne(List<String> input) {
        char[] asArray = input.get(0).toCharArray();
        int[] number = new int[asArray.length];
        int[] nextNumber = new int[asArray.length];
        int[] basePattern = new int[]{0, 1, 0, -1};
        int[][] patterns = new int[asArray.length][asArray.length];
        int i, j, k, element, phase;
        for (i = 0; i < asArray.length; i++) {
            number[i] = asArray[i] - '0';
            for (j = 0; j * (i + 1) < asArray.length + 1; j++) {
                k = j == 0 ? 1 : 0;
                for (; k <= i; k++) {
                    if (j * (i + 1) + k - 1 < patterns[i].length) {
                        patterns[i][j * (i + 1) + k - 1] = basePattern[j % 4];
                    }
                }
            }
        }
        for (phase = 0; phase < 100; phase++) {
            for (i = 0; i < number.length; i++) {
                element = 0;
                for (j = 0; j < number.length; j++) {
                    element += number[j] * patterns[i][j];
                }
                nextNumber[i] = Math.abs(element % 10);
            }
            for (i = 0; i < number.length; i++) {
                number[i] = nextNumber[i];
            }
        }
        int result = 0;
        for (i = 0; i < 8; i++) {
            result *= 10;
            result += number[i];
        }
        return result;
    }

    private static int challengeThirtyTwo(List<String> input) {

        return 0;
    }

    private static int challengeThirtyThree(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        amp.runProgram();
        boolean isIntersection;
        int lineSize = 0, result = 0, x, y, i;
        int[][] directions = new int[][]{{0, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        while (outputBuffer.get(lineSize) != 10) {
            lineSize++;
        }
        char current = 0;
        char[][] view = new char[outputBuffer.size() / lineSize][lineSize];
        for (y = 0; y < view.length; y++) {
            for (x = 0; x < view[y].length; x++) {
                do {
                    current = (char) outputBuffer.remove().byteValue();
                } while (current == 10);
                view[y][x] = current;
            }
        }
        for (y = 1; y < view.length - 1; y++) {
            for (x = 1; x < view[y].length - 1; x++) {
                isIntersection = true;
                for (i = 0; i < directions.length; i++) {
                    isIntersection = isIntersection && view[y + directions[i][0]][x + directions[i][1]] == '#';
                }
                if (isIntersection) {
                    result += x * y;
                    view[y][x] = 'O';
                }
            }
        }
        for (y = 0; y < view.length; y++) {
            for (x = 0; x < view[y].length; x++) {
                System.out.print(view[y][x]);
            }
            System.out.println();
        }
        return result;
    }

    private static int challengeThirtyFour(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        LinkedList<Long> outputBuffer = amp.getOutputBuffer();
        amp.runProgram();
        boolean isIntersection;
        int lineSize = 0, result = 0, x, y, i;
        int[][] directions = new int[][]{{0, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        while (outputBuffer.get(lineSize) != 10) {
            lineSize++;
        }
        char current = 0;
        char[][] view = new char[outputBuffer.size() / lineSize][lineSize];
        for (y = 0; y < view.length; y++) {
            for (x = 0; x < view[y].length; x++) {
                do {
                    current = (char) outputBuffer.remove().byteValue();
                } while (current == 10);
                view[y][x] = current;
            }
        }
        for (y = 1; y < view.length - 1; y++) {
            for (x = 1; x < view[y].length - 1; x++) {
                isIntersection = true;
                for (i = 0; i < directions.length; i++) {
                    isIntersection = isIntersection && view[y + directions[i][0]][x + directions[i][1]] == '#';
                }
                if (isIntersection) {
                    result += x * y;
                    view[y][x] = 'O';
                }
            }
        }
        for (y = 0; y < view.length; y++) {
            for (x = 0; x < view[y].length; x++) {
                System.out.print(view[y][x]);
            }
            System.out.println();
        }
        directions = Arrays.copyOfRange(directions, 1, 5);
        return result;
    }

    private static int challengeFortyOne(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        amp.inputString("NOT T T\n"
                + "AND A T\n"
                + "AND B T\n"
                + "AND C T\n"
                + "NOT T T\n"
                + "AND D T\n"
                + "OR T J\n"
                + "WALK\n");
        amp.runProgram();
        int result = 0;
        while (!amp.getOutputBuffer().isEmpty()) {
            result = amp.getOutputBuffer().remove().intValue();
            System.out.print((char) result);
        }
        return result;
    }

    private static int challengeFortyTwo(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        amp.inputString("OR E T\n"
                + "OR H T\n"
                + "OR A J\n"
                + "AND B J\n"
                + "AND C J\n"
                + "NOT J J\n"
                + "AND T J\n"
                + "AND D J\n"
                + "RUN\n");
        amp.runProgram();
        int result = 0;
        while (!amp.getOutputBuffer().isEmpty()) {
            result = amp.getOutputBuffer().remove().intValue();
            System.out.print((char) result);
        }
        return result;
    }

    private static int challengeFortyFive(List<String> input) {
        long[] program = parseIntcode(input.get(0));
        Amplifier[] amps = new Amplifier[50];
        int i, address = 0;
        LinkedList<Long> outputBuffer;
        for (i = 0; i < amps.length; i++) {
            amps[i] = new Amplifier(program);
            amps[i].getInputBuffer().add((long) i);
            amps[i].getInputBuffer().add((long) -1);
            //No, this is not proper.
            //Yes, the only time that the amps are not effectively busy waiting
            //for input, being blocked despite the text claiming otherwise,
            //is on their SECOND input instruction, seemingly as a test that
            //you actually do provide -1 instead of simply busy waiting.
            //To which I say: Raspberries to you, good sir. I shall wait all I want.
        }
        long x = 0, y = 0;
        while (address != 255) {
            for (i = 0; i < amps.length; i++) {
                amps[i].runProgram();
                outputBuffer = amps[i].getOutputBuffer();
                while (!outputBuffer.isEmpty()) {
                    address = outputBuffer.removeFirst().intValue();
                    x = outputBuffer.removeFirst();
                    y = outputBuffer.removeFirst();
                    if (address == 255) {
                        return Long.valueOf(y).intValue();
                    }
                    amps[address].getInputBuffer().add(x);
                    amps[address].getInputBuffer().add(y);
                }
            }
        }
        return 0;
    }

    private static int challengeFortySix(List<String> input) {
        long[] program = parseIntcode(input.get(0));
        Amplifier[] amps = new Amplifier[50];
        int i, address = 0, idleAmps;
        LinkedList<Long> outputBuffer;
        for (i = 0; i < amps.length; i++) {
            amps[i] = new Amplifier(program);
            amps[i].getInputBuffer().add((long) i);
            amps[i].getInputBuffer().add((long) -1);
        }
        long x = 0, y = 0, natX = 0, natY = 0;
        HashSet<Long> usedValues = new HashSet<>();
        while (true) {
            idleAmps = 0;
            for (i = 0; i < amps.length; i++) {
                amps[i].runProgram();
                outputBuffer = amps[i].getOutputBuffer();
                if (amps[i].getOutputBuffer().isEmpty()) {
                    idleAmps++;
                }
                while (!outputBuffer.isEmpty()) {
                    address = outputBuffer.removeFirst().intValue();
                    x = outputBuffer.removeFirst();
                    y = outputBuffer.removeFirst();
                    if (address == 255) {
                        natX = x;
                        natY = y;
                    } else {
                        amps[address].getInputBuffer().add(x);
                        amps[address].getInputBuffer().add(y);
                    }
                }
            }
            if (idleAmps == 50) {
                if (usedValues.contains(natY)) {
                    break;
                }
                usedValues.add(natY);
                amps[0].getInputBuffer().add(natX);
                amps[0].getInputBuffer().add(natY);
            }
        }
        return Long.valueOf(natY).intValue();
    }

    private static int challengeFortySeven(List<String> input) {
        int lineLength = input.get(0).length();
        char[][] field = new char[input.size()][lineLength];
        char[][] nextField = new char[field.length][field[0].length];
        int i, j, x, y, neighbours;
        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        String line, state = "";
        HashSet<String> visitedStates = new HashSet<>();
        for (i = 0; i < input.size(); i++) {
            line = input.get(i);
            for (j = 0; j < lineLength; j++) {
                field[i][j] = line.charAt(j);
            }
        }
        state = "";
        for (char[] row : field) {
            state += new String(row);
        }
        visitedStates.add(state);
        System.out.println(state);
        while (true) {
            for (i = 0; i < field.length; i++) {
                for (j = 0; j < field[i].length; j++) {
                    neighbours = 0;
                    for (int[] direction : dirs) {
                        x = i + direction[0];
                        y = j + direction[1];
                        if (0 <= x && x < field.length && 0 <= y && y < field[i].length && field[x][y] == '#') {
                            neighbours++;
                        }
                    }
                    if (neighbours == 1 || (field[i][j] == '.' && neighbours == 2)) {
                        nextField[i][j] = '#';
                    } else {
                        nextField[i][j] = '.';
                    }
                }
            }
            for (i = 0; i < field.length; i++) {
                for (j = 0; j < field[i].length; j++) {
                    field[i][j] = nextField[i][j];
                }
            }
            state = "";
            for (char[] row : field) {
                state += new String(row);
            }
            if (visitedStates.contains(state)) {
                break;
            } else {
                visitedStates.add(state);
            }
            System.out.println(state);
        }
        int result = 0;
        int value = 1;
        for (i = 0; i < field.length; i++) {
            for (j = 0; j < field[i].length; j++) {
                if (field[i][j] == '#') {
                    result += value;
                }
                value *= 2;
            }
        }
        return result;
    }

    private static int challengeFortyNine(List<String> input) {
        Amplifier amp = new Amplifier(parseIntcode(input.get(0)));
        Scanner in = new Scanner(System.in);
        String quicksave = "", savedOutput = "", currentOutput, room = "", line;
        String checkpoint;
        HashSet<String> inventory = new HashSet<>(), availableItems = new HashSet<>();
        HashSet<String> desiredItems = new HashSet<>();
        ArrayList<String> possibleItems = new ArrayList<>();
        String[] splitHolder;
        int i, j, limit;
        amp.runProgram();
        currentOutput = amp.flushOutput();
        System.out.print(currentOutput);
        program:
        while (true) {
            line = in.nextLine().toLowerCase();
            switch (line) {
                case "quit":
                    break program;
                case "save":
                    System.out.println("Enter desired save name.");
                    try (FileWriter writer = new FileWriter("droneGame/" + in.next() + ".txt")) {
                        writer.write(amp.toString());
                    } catch (Exception e) {
                        System.out.println("Oh noes! Exception: " + e.getMessage());
                    }
                    break;
                case "load":
                    File saveFolder = new File("droneGame");
                    File[] saves = saveFolder.listFiles();
                    System.out.println("Which save game do you want to load?");
                    for (i = 0; i < saves.length; i++) {
                        System.out.println((i + 1) + " " + saves[i].getName().substring(0, saves[i].getName().length() - 4));
                    }
                    i = in.nextInt() - 1;
                    StringBuilder builder = new StringBuilder();
                    try (Stream<String> stream = Files.lines(saves[i].toPath())) {
                        stream.forEach(s -> builder.append(s).append("\n"));
                        amp.loadState(builder.toString());
                    } catch (Exception e) {
                        System.out.println("Oh noes! Exception: " + e.getMessage());
                    }
                    break;
                case "qsave":
                    quicksave = amp.toString();
                    System.out.println("Successfully quicksaved! Next command?");
                    savedOutput = currentOutput;
                    break;
                case "qload":
                    if (!quicksave.equals("")) {
                        amp.loadState(quicksave);
                        System.out.println("Successfully quickloaded!");
                        System.out.print(savedOutput);
                    } else {
                        System.out.println("No quicksave data exists!");
                    }
                    break;
                case "room":
                    System.out.print(room);
                    break;
                case "north":
                case "south":
                case "west":
                case "east":
                    amp.inputString(line + "\n");
                    amp.runProgram();
                    currentOutput = amp.flushOutput();
                    System.out.print(currentOutput);
                    room = currentOutput;
                    availableItems.clear();
                    splitHolder = room.split("Items here:\n");
                    if (splitHolder.length > 1) {
                        splitHolder = splitHolder[1].split("\n\n");
                        splitHolder = splitHolder[0].split("\n");
                        for (i = 0; i < splitHolder.length; i++) {
                            availableItems.add(splitHolder[i].substring(2));
                        }
                    }
                    break;
                case "inv":
                    amp.inputString(line + "\n");
                    amp.runProgram();
                    currentOutput = amp.flushOutput();
                    System.out.print(currentOutput);
                    inventory.clear();
                    splitHolder = currentOutput.split("inventory:\n");
                    if (splitHolder.length > 1) {
                        splitHolder = splitHolder[1].split("\n\n");
                        splitHolder = splitHolder[0].split("\n");
                        for (i = 0; i < splitHolder.length; i++) {
                            inventory.add(splitHolder[i].substring(2));
                        }
                    }
                    break;
                case "shuffle":
                    System.out.println("Welcome to the inventory item shuffling assistant.\n"
                            + "Available to you for shuffling are the following items:\n");
                    for (String item : inventory) {
                        System.out.println("- " + item);
                    }
                    for (String item : availableItems) {
                        System.out.println("- " + item);
                    }
                    System.out.println("Type the names of the items you want to be in your inventory,"
                            + " separated by commas and spaces. (such as "
                            + "\"spool of cat6, jam, infinite loop\"");
                    desiredItems.clear();
                    for (String item : in.nextLine().split(", ")) {
                        desiredItems.add(item);
                    }
                    sculptInventory(amp, inventory, availableItems, desiredItems);
                    amp.runProgram();
                    System.out.println("All done! Shuffled your items around. Ready for next command now.");
                    amp.flushOutput();
                    break;
                case "bruteforce":
                    System.out.println("Alright! Let's do this. In which direction is the checkpoint?");
                    checkpoint = in.nextLine();
                    desiredItems.clear();
                    sculptInventory(amp, inventory, availableItems, desiredItems);
                    possibleItems.clear();
                    possibleItems.addAll(availableItems);
                    limit = 1 << possibleItems.size();
                    for (i = 0; i < limit; i++) {
                        desiredItems.clear();
                        for (j = 0; j < possibleItems.size(); j++) {
                            if ((i >> j & 1) == 1) {
                                desiredItems.add(possibleItems.get(j));
                            }
                        }
                        //Let it be known, this algorithm could be vastly improved.
                        //Rather than checking *all* possibilities, start with all
                        //that contain one item. Some items may already be too
                        //heavy by themselves and, as such, can't be part of the
                        //solution. So when we search groups of n+1 items, we only
                        //have to consider items that were light enough to still
                        //make you too light by themselves. Then check all combinations
                        //of two such items, again eliminating all combinations
                        //that are too heavy (but not the items themselves - only
                        //noting that those two items together won't be in the solution.)
                        //To keep some amount of order, the items should be organised
                        //as lists sorted by some criterion. That would make it
                        //easy to avoid reusing combinations while selecting exactly
                        //n items - something the bitwise solution struggles with.
                        //This will greatly reduce the required search space.
                        //However, all of my code here is massive overhead for
                        //what is basically searching a depth 7 binary tree.
                        sculptInventory(amp, inventory, availableItems, desiredItems);
                        amp.runProgram();
                        amp.clearOutput();
                        amp.inputString(checkpoint + "\n");
                        amp.runProgram();
                        currentOutput = amp.flushOutput();
                        if (currentOutput.charAt(103) != 'l') {
                            System.out.print(currentOutput);
                            break;
                        }
                    }
                    break;
                default:
                    amp.inputString(line + "\n");
                    amp.runProgram();
                    currentOutput = amp.flushOutput();
                    System.out.print(currentOutput);
                    break;
            }
        }
        return 0;
    }

    private static void sculptInventory(Amplifier amp, HashSet<String> inventory,
            HashSet<String> availableItems, HashSet<String> desiredItems) {
        String item;
        Iterator it = inventory.iterator();
        while (it.hasNext()) {
            item = (String) it.next();
            if (!desiredItems.contains(item)) {
                amp.inputString("drop " + item + "\n");
                it.remove();
                availableItems.add(item);
            }
        }
        it = availableItems.iterator();
        while (it.hasNext()) {
            item = (String) it.next();
            if (desiredItems.contains(item)) {
                amp.inputString("take " + item + "\n");
                inventory.add(item);
                it.remove();
            }
        }
    }
}
