package brookite.games.goatcabbage.model.levels.json;

import java.util.Arrays;
import java.util.Objects;

public class Level {
    private String name;
    private Field field;
    private GoatInfo goat;

    private UIProperties uiProps;
    private Command[] commands;

    public String getName() {
        return name;
    }

    public Command[] getCommands() {
        return commands;
    }

    public GoatInfo getGoat() {
        return goat;
    }

    public Field getField() {
        return field;
    }

    public UIProperties getUiProps() {
        return uiProps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return Objects.equals(name, level.name) && Objects.equals(field, level.field) && Objects.equals(goat, level.goat) && Objects.equals(uiProps, level.uiProps) && Arrays.equals(commands, level.commands);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, field, goat, uiProps);
        result = 31 * result + Arrays.hashCode(commands);
        return result;
    }

}