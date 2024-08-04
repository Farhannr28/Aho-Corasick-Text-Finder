package com.textfinder.models;

import java.util.Map;

public class State {
    String stateName;
    Map<Character, Integer> transitions;
    boolean isAcceptingState;
    int link;
    int[] matchIndexes;
    int nearestAcceptingByLink;
}
