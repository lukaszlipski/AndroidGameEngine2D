package com.lucek.androidgameengine2d.game;

import com.lucek.androidgameengine2d.GeApplication;
import com.lucek.androidgameengine2d.R;

import java.util.ArrayList;
import java.util.List;

public class PlayerTypes {
    public enum Types {
        HUMAN(0, R.string.human_dropdown_value),
        SIMPLE_AI(1, R.string.simple_ai_dropdown_value),
        KOSMA_MARZEC(2, R.string.kosmamarzec_dropdown_value),
        BORKOWSKI_BARTOSZEK(3, R.string.borkowskibartoszek_dropdown_value);

        public int position;
        public String name;

        Types(int position, int stringResource) {
            this.position = position;
            name = GeApplication.getAppContext().getString(stringResource);
        }
    }

    private static List<String> types;

    public static List<String> getListOfTypes(){
        if (types == null) {
            types = new ArrayList<>(Types.values().length);
            for (Types type : Types.values()) {
                types.add(type.position, type.name);
            }
        }
        return types;
    }

    public static int getPositionByName(String name){
        for (Types type : Types.values()){
            if (type.name.equals(name)) return type.position;
        }
        return -1;
    }

    public static Types getTypeByName(String name){
        for (Types type : Types.values()){
            if (type.name.equals(name)) return type;
        }
        return null;
    }
}
