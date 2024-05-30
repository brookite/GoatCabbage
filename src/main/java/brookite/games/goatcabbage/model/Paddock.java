package brookite.games.goatcabbage.model;


import brookite.games.goatcabbage.model.entities.*;
import brookite.games.goatcabbage.model.utils.CellPosition;
import brookite.games.goatcabbage.model.utils.Direction;

import java.util.*;

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

    Cell findNeighbour(Cell cell, Direction direction) {
        for (int row = 1; row <= this.height; row++) {
            for (int col = 1; col <= this.width; col++) {
                if (cell(row, col) == cell) {
                    if (direction.equals(Direction.NORTH)) {
                        row -= 1;
                    } else if (direction.equals(Direction.SOUTH)) {
                        row += 1;
                    } else if (direction.equals(Direction.WEST)) {
                        col -= 1;
                    } else if (direction.equals(Direction.EAST)) {
                        col += 1;
                    }
                    if (row < 1 || row > this.height || col < 1 || col > this.width) {
                        return null;
                    } else {
                        return cell(row, col);
                    }
                }
            }
        }
        return null;
    }

    boolean isNeighbours(Cell cell1, Cell cell2) {
        CellPosition cell1Position = position(cell1);
        return cell(cell1Position.row - 1, cell1Position.col) == cell2
                || cell(cell1Position.row, cell1Position.col - 1) == cell2
                || cell(cell1Position.row + 1, cell1Position.col) == cell2
                || cell(cell1Position.row, cell1Position.col + 1) == cell2;
    }

    CellPosition position(Cell cell) {
        int index = cells.indexOf(cell);
        if (index == -1) {
            return null;
        }
        return new CellPosition((index / width) + 1, (index % width) + 1);
    }


    public Cell cell(int row, int col) {
        if (row < 1 || col < 1 || row > this.height || col > this.width) {
            return null;
        }
        return cells.get((row - 1) * this.width + (col - 1));
    }

    public Cell cell(CellPosition pos) {
        return cell(pos.row, pos.col);
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

