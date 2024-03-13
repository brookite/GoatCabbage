package brookite.games.goatcabbage.model.levels.data;

import java.util.Objects;

public class UIProperties {
    private String levelBackground = "defaultbg.png";

    public String getLevelBackground() {
        return levelBackground;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIProperties that = (UIProperties) o;
        return Objects.equals(levelBackground, that.levelBackground);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelBackground);
    }
}
