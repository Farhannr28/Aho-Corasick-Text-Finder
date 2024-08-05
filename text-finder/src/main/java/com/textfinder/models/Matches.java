package com.textfinder.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Matches {
    private Map<String, ArrayList<Integer>> map;

    public Matches(ArrayList<State> states){
        Map<String, ArrayList<Integer>> m = new HashMap<>();
        for (State state : states){
            if (state.isAcceptingState()){
                m.put(state.getName(), new ArrayList<>(state.getMatchIndexes()));
            }
        }
        this.map = m;
    }

    public Map<String, ArrayList<Integer>> getMap() {
        return map;
    }
}
