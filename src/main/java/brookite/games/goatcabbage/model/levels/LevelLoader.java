package brookite.games.goatcabbage.model.levels;

import brookite.games.goatcabbage.model.levels.data.Command;
import brookite.games.goatcabbage.model.levels.data.Level;
import brookite.games.goatcabbage.model.levels.data.commands.CreateWallFigureCommand;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntitiesCommand;
import brookite.games.goatcabbage.model.levels.data.commands.PlaceEntityCommand;
import brookite.games.goatcabbage.model.levels.data.commands.SetWallCommand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class LevelLoader {
	public static final int LEVEL_COUNT = 44;

	public static LevelGameEnvironment levelFromResource(String resourceFile) throws IOException {
		Command.Deserializer deserializer = new Command.Deserializer("command");
		deserializer.registerSubtype("createWallFigure", CreateWallFigureCommand.class);
		deserializer.registerSubtype("placeEntities", PlaceEntitiesCommand.class);
		deserializer.registerSubtype("placeEntity", PlaceEntityCommand.class);
		deserializer.registerSubtype("setWall", SetWallCommand.class);

		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Command.class, deserializer)
				.create();
		String json = null;
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceFile)) {
			if (is == null) return null;
			try (InputStreamReader isr = new InputStreamReader(is);
				 BufferedReader reader = new BufferedReader(isr)) {
				json = reader.lines().collect(Collectors.joining(System.lineSeparator()));
			}
		}
		Level level = gson.fromJson(json, Level.class);
		return new LevelGameEnvironment(level);
	}

	public static LevelGameEnvironment[] loadAllLevels() throws IOException {
		LevelGameEnvironment[] array = new LevelGameEnvironment[LEVEL_COUNT];
		for (int i = 1; i < LEVEL_COUNT + 1; i++) {
			array[i - 1] = levelFromResource(String.format("levels/level_%d.level", i));
		}
		return array;
	}

}
