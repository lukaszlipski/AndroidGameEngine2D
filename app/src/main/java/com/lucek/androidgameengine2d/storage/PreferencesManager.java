package com.lucek.androidgameengine2d.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lucek.androidgameengine2d.GeApplication;

public class PreferencesManager {
    private static final SharedPreferences getter;
    private static final SharedPreferences.Editor setter;

    static {
        Context context = GeApplication.getAppContext();
        getter = context.getSharedPreferences("gameengine_preferences", Context.MODE_PRIVATE);
        setter = getter.edit();
    }

    private static void clear(String... keys) {
        for (String key : keys)
            setter.remove(key);
        setter.apply();
    }

    private static void set(String key, String value) {
        setter.putString(key, value);
        setter.apply();
    }

    private static void set(String key, Boolean value) {
        setter.putBoolean(key, value);
        setter.commit();
    }

    @Nullable
    private static String get(@NonNull String key, @Nullable String defaultVaule) {
        return getter.getString(key, defaultVaule);
    }

    @Nullable
    private static Boolean get(@NonNull String key, @Nullable Boolean defaultVaule) {
        return getter.getBoolean(key, defaultVaule == null ? false : defaultVaule);
    }

    public static class NoGo {
        private static final String ALGORITHM_TOURNAMENT = "algorithm_tournament";

        public static Boolean getAlgorithmTournament() {
            return get(ALGORITHM_TOURNAMENT, false);
        }

        public static void setAlgorithmTournament(Boolean value) {
            set(ALGORITHM_TOURNAMENT, value);
        }
    }
}
