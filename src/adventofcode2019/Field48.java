/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2019;

/**
 *
 * @author Lukas Thöni <Lukas.thoeni@gmx.ch>
 */
public class Field48 {

    private Field48 outer, inner;
    private final Space48[][] field;

    public Field48() {
        this.outer = null;
        this.inner = null;
        this.field = new Space48[5][5];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new Space48();
            }
        }
    }

    public void setOuter(Field48 outer) {
        this.outer = outer;
    }

    public Field48 getOuter() {
        return outer;
    }

    public void setInner(Field48 inner) {
        this.inner = inner;
    }

    public Field48 getInner() {
        return inner;
    }

    public void stepAll() {
        for (Space48[] row : field) {
            for (Space48 space : row) {
                space.step();
            }
        }
    }

    public int getNumberOfNeighboursForField(int x, int y) {
        return field[x][y].getNumberOfNeighbours();
    }

    public void updateAll() {
        for (Space48[] row : field) {
            for (Space48 space : row) {
                space.update();
            }
        }
    }
}
