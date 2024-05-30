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

    public boolean isVertical() {
        return this == NORTH || this == SOUTH;
    }

    public boolean isHorizontal() {
        return this == WEST || this == EAST;
    }

    public boolean isOpposite(Direction other) {
        return this.opposite().equals(other);
    }

    public Direction clockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
        };
    }
}