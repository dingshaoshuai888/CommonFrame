package com.retrofit.demo.help;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class JsonHelp {
    private static volatile Gson gson;

    private JsonHelp() {

    }

    private static Gson getGson() {
        if (gson == null) {
            synchronized (JsonHelp.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 转成bean
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T toObject(String jsonString, Class<T> cls) {
        return getGson().fromJson(jsonString, cls);
    }

    /**
     * 转成list
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> toList(String jsonString, Class<T> cls) {
        return getGson().fromJson(jsonString, new TypeToken<T>() {
        }.getType());
    }

    /**
     * 转成list中有map的
     *
     * @param jsonString
     * @return
     */
    public static <T> List<Map<String, T>> toListMaps(String jsonString) {
        return getGson().fromJson(jsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
    }

    /**
     * 转成map的
     *
     * @param jsonString
     * @return
     */
    public static <T> Map<String, T> toMaps(String jsonString) {
        return getGson().fromJson(jsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }
}
