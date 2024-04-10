package brookite.games.goatcabbage.model.utils;

import java.util.Objects;

public class CellPosition {
    public final int row;
    public final int col;

    public CellPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return String.format("CellPosition{row=%d, col=%d}", this.row, this.col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
