package brookite.games.goatcabbage.model.utils;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.*;

public class Debug {
    public static String drawPaddock(Paddock pd) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < pd.getHeight() + 1; i++) {
            for (int j = 1; j < pd.getWidth() + 1; j++) {
                Cell cell = pd.cell(i, j);
                if (cell.hasSolidEntity()) {
                    if (cell.getSolidEntity().get() instanceof Goat) {
                        builder.append('@');
                    } else if (cell.getSolidEntity().get() instanceof Wall) {
                        builder.append('#');
                    } else if (cell.getSolidEntity().get() instanceof SimpleBox) {
                        builder.append('$');
                    } else if (cell.getSolidEntity().get() instanceof MetalBox) {
                        builder.append('M');
                    } else if (cell.getSolidEntity().get() instanceof MagnetBox mb) {
                        switch (mb.getMagnetDirection()) {
                            case NORTH -> builder.append('U');
                            case SOUTH -> builder.append("^");
                            case WEST -> {
                                if (mb.getPole().get(MagneticPole.NORTH).equals(Direction.NORTH)) {
                                    builder.append(')');
                                } else {
                                    builder.append('>');
                                }
                            }
                            case EAST -> {
                                if (mb.getPole().get(MagneticPole.NORTH).equals(Direction.NORTH)) {
                                    builder.append('<');
                                } else {
                                    builder.append('(');
                                }
                            }
                        }
                    }
                } else if (cell.hasEntity(Cabbage.class)) {
                    builder.append('.');
                } else {
                    builder.append(' ');
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
