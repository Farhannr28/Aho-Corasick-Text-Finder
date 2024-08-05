package com.textfinder.models;

import java.util.ArrayList;

public class Automaton {
    private ArrayList<State> states;

    public void printStates(){
        int i=0;
        for(State state : this.states){
            System.out.println(i + " " + state.getName() + " " + state.getLink() + " " +  state.getNearestAccepting() + " " + state.isAcceptingState());
            i++;
        }
    }

    public void printResults(){
        int i=0;
        for(State state : this.states){
            System.out.print(state.getName() + ": ");
            state.printMatchingIndexes();
            System.out.println();
            i++;
        }
    }

    public void addString(String s){
        int currState = 0;
        char[] charArr = s.toCharArray();
        for (Character c : charArr){
            State st = this.states.get(currState);
            if (st.getTransition(c) == State.NULL_INT){
                st.setTransition(c, this.states.size());
                this.states.add(new State(st.getName() + c, currState));
            }
            currState = this.states.get(currState).getTransition(c);
        }
        this.states.get(currState).setAcceptingState(true);
        this.states.get(currState).initMatchIndexes();
    }

    public int useLink(int start) {
        if (this.states.get(start).getLink() == State.NULL_INT){
            if (start == 0 || this.states.get(start).getRoot() == 0){
                this.states.get(start).setLink(0);
            } else {
                int root = this.states.get(start).getRoot();
                char c = this.states.get(start).getName().charAt(this.states.get(start).getName().length() - 1);
                this.states.get(start).setLink(jump(useLink(root), c));
            }
        }
        return this.states.get(start).getLink();
    }

    public int jump(int start, char c){
        if (this.states.get(start).getTransition(c) == State.NULL_INT){
            if (start == 0) {
                this.states.get(start).setTransition(c, 0);
            } else {
                this.states.get(start).setTransition(c, jump(useLink(start), c));
            }
        }
        return this.states.get(start).getTransition(c);
    }

    public void createExitLink(){
        int currState = 0;
        for (int i=0; i<this.states.size(); i++){
            currState = useLink(i);
            while (!this.states.get(currState).isAcceptingState() && currState != 0){
                currState = useLink(currState);
            }
            this.states.get(i).setNearestAccepting(currState);
            if (this.states.get(i).containsExit()){
                this.states.get(i).initMatchIndexes();
            }
        }
    }

    public Matches runText(String text){
        int len = text.length();
        int currState = 0;
        char c;
        int i=0;

        // run Automata
        while (i<len){
            c = text.charAt(i);
            currState = jump(currState, c);
            if (this.states.get(currState).containsExit() || this.states.get(currState).isAcceptingState()){
                this.states.get(currState).addMatchIndexes(i);
            }
            i++;
        }

        // Combine match indexes using exit links
        for (State state : this.states){
            if (state.containsExit()){
                currState = state.getNearestAccepting();
                while (currState != 0){
                    this.states.get(currState).getMatchIndexes().addAll(state.getMatchIndexes());
                    currState = this.states.get(currState).getNearestAccepting();
                }
            }
        }

        // Convert to Matches model
        return new Matches(this.states);
    }

    public Automaton(String[] patterns) {
        this.states = new ArrayList<>();
        State root = new State("", 0);
        root.setLink(0);
        this.states.add(root);
        for (String pattern : patterns) {
            addString(pattern);
        }
        createExitLink();
    }

    public static void main(String[] args){
        Automaton a = new Automaton(new String[]{"saya", "aman", "ayam"});
        a.printStates();
        a.runText("saya sangat suka matkul irk. saya jadi ingin makan ayam.");
        a.printResults();
    }
}
