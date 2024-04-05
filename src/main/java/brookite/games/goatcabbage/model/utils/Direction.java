package brookite.games.goatcabbage.model.utils;

// Направление  - "север/юг/запад/восток"
public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public int getRowDelta() {
        return switch (this) {
            case NORTH -> -1;
            case SOUTH -> 1;
            case EAST -> 0;
            case WEST -> 0;
        };
    }

    public int getColDelta() {
        return switch (this) {
            case NORTH -> 0;
            case SOUTH -> 0;
            case EAST -> 1;
            case WEST -> -1;
        };
    }

    public boolean isVertical() {
        return this == NORTH || this == SOUTH;
    }

    public boolean isHorizontal() {
        return this == WEST || this == EAST;
    }

    public boolean isOpposite(Direction other) {
        return this.opposite().equals(other);
    }
}