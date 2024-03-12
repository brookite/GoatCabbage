package brookite.games.goatcabbage.model;


import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Paddock {
    private ArrayList<Cell> cells;
    private Goat goat;
    private Cabbage cabbage;

    private int width;
    private int height;

    public Paddock(int width, int height) {
        this.width = width;
        this.height = height;
        clear();
    }

    public void clear() {

    }

    public Cell neighbor(Cell cell, Direction direction) {
        return null;
    }

    public Cell cell(int row, int col) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Goat getGoat() {
        return goat;
    }

    public Cabbage getCabbage() {
        return cabbage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Cell> getCells() {
        return new ArrayList<>(cells);
    }
}

