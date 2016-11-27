package com.lucek.androidgameengine2d.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lucek.androidgameengine2d.GeApplication;
import com.lucek.androidgameengine2d.R;

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

    private static void set(String key, Object value) {
        if (value instanceof String) setter.putString(key, (String) value);
        if (value instanceof Boolean) setter.putBoolean(key, (Boolean) value);
        if (value instanceof Long) setter.putLong(key, (Long) value);
        if (value instanceof Integer) setter.putInt(key, (Integer) value);
        if (value instanceof Float) setter.putFloat(key, (Float) value);
        setter.apply();
    }

    @Nullable
    private static String get(@NonNull String key, @Nullable String defaultVaule) {
        return getter.getString(key, defaultVaule);
    }

    @Nullable
    private static Boolean get(@NonNull String key, @Nullable Boolean defaultVaule) {
        return getter.getBoolean(key, defaultVaule == null ? false : defaultVaule);
    }

    @Nullable
    private static Long get(@NonNull String key, Long defaultVaule) {
        return getter.getLong(key, defaultVaule);
    }

    @Nullable
    private static Integer get(@NonNull String key, Integer defaultVaule) {
        return getter.getInt(key, defaultVaule);
    }

    @Nullable
    private static Float get(@NonNull String key, Float defaultVaule) {
        return getter.getFloat(key, defaultVaule);
    }


    public static class NoGo {
        private static final String ALGORITHM_TOURNAMENT = "algorithm_tournament";
        private static final String TIME_FOR_TURN = "turn_time";
        private static final String GAMES_COUNT = "games_count";
        private static final String PLAYER_1 = "player_1";
        private static final String PLAYER_2 = "player_2";

        public static Boolean getAlgorithmTournament() {
            return get(ALGORITHM_TOURNAMENT, false);
        }

        public static void setAlgorithmTournament(Boolean value) {
            set(ALGORITHM_TOURNAMENT, value);
        }

        public static String getPlayer1() {
            return get(PLAYER_1, GeApplication.getAppContext().getString(R.string.human_dropdown_value));
        }

        public static void setPlayer1(String value) {
            set(PLAYER_1, value);
        }

        public static String getPlayer2() {
            return get(PLAYER_2, GeApplication.getAppContext().getString(R.string.human_dropdown_value));
        }

        public static void setPlayer2(String value) {
            set(PLAYER_2, value);
        }

        public static Long getTimeForTurn() {
            return get(TIME_FOR_TURN, 1000L);
        }

        public static void setTimeForTurn(Long value) {
            set(TIME_FOR_TURN, value);
        }

        public static Integer getGamesCount() {
            return get(GAMES_COUNT, 1);
        }

        public static void setGamesCount(Integer value) {
            set(GAMES_COUNT, value);
        }

    }
}
