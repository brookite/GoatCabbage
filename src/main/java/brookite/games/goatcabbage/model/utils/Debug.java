package brookite.games.goatcabbage.model.utils;

import brookite.games.goatcabbage.model.Cell;
import brookite.games.goatcabbage.model.Paddock;
import brookite.games.goatcabbage.model.entities.*;

public class Debug {
    public static Paddock paddockFromString(String s) {
        String[] rows = s.split("\n");
        int columns = rows[0].length();
        for (String row : rows) {
            columns = Math.max(columns, row.length());
        }
        Paddock pd = new Paddock(columns, rows.length);
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < rows[i].length(); j++) {
                switch (rows[i].charAt(j)) {
                    case '@':
                        pd.cell(i + 1, j + 1).putEntity(new Goat(30));
                        break;
                    case '#':
                        pd.cell(i + 1, j + 1).putEntity(new Wall());
                        break;
                    case '$':
                        pd.cell(i + 1, j + 1).putEntity(new SimpleBox());
                        break;
                    case 'M':
                        pd.cell(i + 1, j + 1).putEntity(new MetalBox());
                        break;
                    case 'U':
                        pd.cell(i + 1, j + 1).putEntity(new MagnetBox(Direction.WEST, true));
                        break;
                    case '<':
                        pd.cell(i + 1, j + 1).putEntity(new MagnetBox(Direction.NORTH, true));
                        break;
                    case '>':
                        pd.cell(i + 1, j + 1).putEntity(new MagnetBox(Direction.SOUTH, false));
                        break;
                    case ')':
                        pd.cell(i + 1, j + 1).putEntity(new MagnetBox(Direction.NORTH, false));
                        break;
                    case '(':
                        pd.cell(i + 1, j + 1).putEntity(new MagnetBox(Direction.SOUTH, true));
                        break;
                    case '^':
                        pd.cell(i + 1, j + 1).putEntity(new MagnetBox(Direction.EAST, false));
                        break;
                    case '.':
                        pd.cell(i + 1, j + 1).putEntity(new Cabbage());
                        break;
                    default:
                        break;
                }
            }
        }
        return pd;
    }

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
