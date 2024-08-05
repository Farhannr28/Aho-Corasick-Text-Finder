package com.textfinder.service;

import com.textfinder.models.Automaton;
import com.textfinder.models.Matches;

public class AhoCorasick {

    private final Matches matches;
    private final Automaton automaton;

    public AhoCorasick(String _text, String[] _patterns){
        String text = _text.toLowerCase();
        String[] patterns = new String[_patterns.length];
        for (int i=0; i<_patterns.length; i++){
            patterns[i] = _patterns[i].toLowerCase();
        }
        this.automaton = new Automaton(patterns);
        // Start String Matching
        this.matches = automaton.runText(text);
    }

    public Matches getMatches(){
        return this.matches;
    }

    public Automaton getAutomaton(){
        return this.automaton;
    }
}
