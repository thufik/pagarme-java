package me.pagar.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JSONUtils {
    private static final Gson GSON_DATA_PROVIDER;

    static {
        GSON_DATA_PROVIDER = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(DateTime.class, new DateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setExclusionStrategies(new ExclusionStrategy(){

                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }

                    public boolean shouldSkipField(FieldAttributes fieldAttrs) {
                        return fieldAttrs.equals(null);
                    }
                    
                })
                .create();
    }

    public static final Gson getInterpreter() {
        return GSON_DATA_PROVIDER;
    }

    public static <T> T getAsObject(JsonObject json, Class<T> clazz) {
        return GSON_DATA_PROVIDER.fromJson(json, clazz);
    }

    public static <T> Collection<T> getAsList(JsonArray json, Type listType) {
        return GSON_DATA_PROVIDER.fromJson(json, listType);
    }

    public static String getAsJson(Object object) {
        return GSON_DATA_PROVIDER.toJson(object);
    }

    public static Map<String, Object> objectToMap(Object object) {
        final String json = GSON_DATA_PROVIDER.toJson(object);
        return GSON_DATA_PROVIDER.fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }

    public static JsonObject treeToJson(Object object) {
        return GSON_DATA_PROVIDER.toJsonTree(object).getAsJsonObject();
    }
}
