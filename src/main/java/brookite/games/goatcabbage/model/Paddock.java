package brookite.games.goatcabbage.model;


import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.ArrayList;

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

    public void neighbor(Cell cell, Direction direction) {

    }

    public void cell(int row, int col) {

    }

    @Override
    public String toString() {
        return super.toString();
    }

}

