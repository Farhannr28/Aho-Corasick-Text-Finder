package com.textfinder.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class State {

    public static final int NULL_INT = -1;

    private final String stateName;
    private Map<Character, Integer> transitions;
    private final int root;
    private boolean acceptingState;
    private int link;
    private Set<Integer> matchIndexes;
    private int nearestAccepting;

    public String getName(){
        return this.stateName;
    }

    public void setTransition(char c, int x){
        this.transitions.put(c, x);
    }

    public int getTransition(char c){
        return this.transitions.getOrDefault(c, NULL_INT);
    }

    public int getRoot(){
        return this.root;
    }

    public void setAcceptingState(boolean _is) {
        this.acceptingState = _is;
    }

    public boolean isAcceptingState(){
        return this.acceptingState;
    }

    public void setLink(int _link) {
        this.link = _link;
    }

    public int getLink() {
        return this.link;
    }

    public void setNearestAccepting(int x){
        this.nearestAccepting = x;
    }

    public int getNearestAccepting(){
        return this.nearestAccepting;
    }

    public boolean containsExit(){
        return this.nearestAccepting != 0;
    }

    public void initMatchIndexes(){
        this.matchIndexes = new HashSet<Integer>();
    }

    public Set<Integer> getMatchIndexes(){
        return this.matchIndexes;
    }

    public void addMatchIndexes(int x){
        this.matchIndexes.add(x);
    }

    public void setMatchIndexesNull(){
        this.matchIndexes = null;
    }

    public void printMatchingIndexes(){
        if (this.matchIndexes != null){
            for (int element : this.matchIndexes) {
                System.out.print(element + " ");
            }
        }
    }

    public State(String stateName, int root) {
        this.stateName = stateName;
        this.transitions = new HashMap<Character, Integer>();
        this.root = root;
        this.acceptingState = false;
        this.link = NULL_INT;
        this.matchIndexes = null;
        this.nearestAccepting = NULL_INT;
    }
}
