package brookite.games.goatcabbage.model.levels.data;

import brookite.games.goatcabbage.model.Paddock;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class Command {
    public static class Deserializer implements JsonDeserializer<Command> {
        private String commandNameField;
        private Gson gson;
        private Map<String, Class<? extends Command>> commandTypeRegistry;

        public Deserializer(String commandNameField) {
            this.commandNameField = commandNameField;
            this.gson = new Gson();
            this.commandTypeRegistry = new HashMap<>();
        }

        public void registerSubtype(String commandTypeName, Class<? extends Command> animalType) {
            commandTypeRegistry.put(commandTypeName, animalType);
        }

        public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject animalObject = json.getAsJsonObject();
            JsonElement animalTypeElement = animalObject.get(commandNameField);

            Class<? extends Command> animalType = commandTypeRegistry.get(animalTypeElement.getAsString());
            return gson.fromJson(animalObject, animalType);
        }
    }
    @SerializedName("command")
    protected String commandName;

    public abstract void execute(Paddock paddock);
}
