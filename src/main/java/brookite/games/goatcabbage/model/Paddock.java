package brookite.games.goatcabbage.model;


import brookite.games.goatcabbage.model.entities.Goat;
import brookite.games.goatcabbage.model.entities.Cabbage;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.model.utils.Direction;

import javax.security.auth.login.AccountExpiredException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Paddock implements Iterable<Cell> {
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
        cells = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            cells.add(new Cell(this));
        }
        goat = null;
        cabbage = null;
    }

    public Cell neighbour(Cell cell, Direction direction) {
        for (int row = 1; row <= this.height; row++) {
            for (int col = 1; col <= this.width; col++) {
                if (cell(row, col) == cell) {
                    if (direction.equals(Direction.north())) {
                        row -= 1;
                    } else if (direction.equals(Direction.south())) {
                        row += 1;
                    } else if (direction.equals(Direction.west())) {
                        col -= 1;
                    } else if (direction.equals(Direction.east())) {
                        col += 1;
                    }
                    if (row < 1 || row > this.width || col < 1 || col > this.height) {
                        return null;
                    } else {
                        return cell(row, col);
                    }
                }
            }
        }
        return null;
    }

    public Cell cell(int row, int col) {
        return cells.get((row - 1) * this.width + (col - 1));
    }

    public CellPosition cellPosition(Cell cell) {
        for (int row = 1; row <= this.height; row++) {
            for (int col = 1; col <= this.width; col++) {
                if (cell(row, col) == cell) {
                    return new CellPosition(row, col);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("Paddock[%dx%d]", this.width, this.height);
    }

    public Goat getGoat() {
        return goat;
    }

    public Cabbage getCabbage() {
        return cabbage;
    }

    boolean setGoat(Goat goat) {
        for (Cell cell : cells) {
            if (cell.hasEntity(goat)) {
                this.goat = goat;
                return true;
            }
        }
        return false;
    }

    boolean setCabbage(Cabbage cabbage) {
        for (Cell cell : cells) {
            if (cell.hasEntity(cabbage)) {
                this.cabbage = cabbage;
                return true;
            }
        }
        return false;
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

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }
}

