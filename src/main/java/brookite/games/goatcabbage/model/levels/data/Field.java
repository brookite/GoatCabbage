package brookite.games.goatcabbage.model.levels.data;

import java.util.Objects;

public class Field {
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return width == field.width && height == field.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
