package brookite.games.goatcabbage.model.levels.data;

import java.util.Objects;

public class UIProperties {
    private String cellBackground = "cell.png";

    public String getLevelCellBackground() {
        return cellBackground;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIProperties that = (UIProperties) o;
        return Objects.equals(cellBackground, that.cellBackground);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellBackground);
    }
}
