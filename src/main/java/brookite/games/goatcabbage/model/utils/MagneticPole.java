package brookite.games.goatcabbage.model.utils;

public enum MagneticPole {
    NORTH,
    SOUTH;

    public MagneticPole opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
        };
    }

    public boolean isOpposite(MagneticPole other) {
        if (other == null) {
            return false;
        }
        return !equals(other);
    }
}
