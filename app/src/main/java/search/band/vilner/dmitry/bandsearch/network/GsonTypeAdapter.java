package search.band.vilner.dmitry.bandsearch.network;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


public class GsonTypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private static final Gson gson = new Gson();
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return gson.toJsonTree(src);
    }
}
