package com.textfinder.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Matches {
    private Map<String, ArrayList<int[]>> map;

    public Matches(ArrayList<State> states){
        Map<String, ArrayList<int[]>> m = new HashMap<>();
        int len;
        ArrayList<int[]> arr;
        for (State state : states){
            if (state.isAcceptingState()){
                len = state.getName().length() - 1;
                arr = new ArrayList<>();
                for (int i : state.getMatchIndexes()){
                    arr.add(new int[]{i - len, i});
                }
                m.put(state.getName(), arr);
            }
        }
        this.map = m;
    }

    public Map<String, ArrayList<int[]>> getMap() {
        return map;
    }
}
