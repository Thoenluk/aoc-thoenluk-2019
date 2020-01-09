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
public class Space48 {

    private LinkedList<Space48> adjacentSpaces;
    private char value, nextValue;

    public Space48() {
        this('.');
    }

    public Space48(char value) {
        this.adjacentSpaces = new LinkedList<>();
        this.value = value;
        this.nextValue = '.';
    }

    public LinkedList<Space48> getAdjacentSpaces() {
        return adjacentSpaces;
    }

    public void addAdjacentSpace(Space48 adjacent) {
        this.adjacentSpaces.add(adjacent);
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void step() {
        this.value = this.nextValue;
    }

    public void update() {
        int neighbours = getNumberOfNeighbours();
        if (neighbours == 1 || (!this.isBug() && neighbours == 2)) {
            nextValue = '#';
        } else {
            nextValue = '.';
        }
    }

    public int getNumberOfNeighbours() {
        int neighbours = 0;
        for (Space48 space : adjacentSpaces) {
            if (space.isBug()) {
                neighbours++;
            }
        }
        return neighbours;
    }

    public boolean isBug() {
        return this.value == '#';
    }

}
