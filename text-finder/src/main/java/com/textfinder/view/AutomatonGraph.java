package com.textfinder.view;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.*;
import com.textfinder.models.Automaton;
import com.textfinder.models.State;

public class AutomatonGraph {

    SmartGraphPanel<String, String> graphView;
    Digraph<String, String> g;
    ArrayList<String> endStateArray;
    ArrayList<String> stateNames;
    Automaton automaton;

    public AutomatonGraph(Automaton automaton){
        this.automaton = automaton;
        g = new DigraphEdgeList<>();
        endStateArray = new ArrayList<>();
        stateNames = new ArrayList<>();

        URI cssUri = null;
        SmartGraphProperties properties = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("com/textfinder/config/smartgraph.properties");
        properties = new SmartGraphProperties(inputStream);

//        Path cssPath = Paths.get("com/textfinder/css/smartgraph.css");
//        URI cssUri = cssPath.toUri();

        try {
            URL cssUrl = AutomatonGraph.class.getClassLoader().getResource("com/textfinder/css/smartgraph.css");
            if (cssUrl != null) {
                cssUri = cssUrl.toURI();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        SmartPlacementStrategy initialPlacement = new SmartCircularSortedPlacementStrategy();
        this.graphView = new SmartGraphPanel<>(g, properties,  initialPlacement, cssUri);
        this.graphView.setMinSize(677, 373);
        this.graphView.setAutomaticLayout(true);
    }

    public SmartGraphPanel<String, String> getGraphView(){
        return this.graphView;
    }

//    public void addEdges(){
//        for (State state : automaton.getStates()){
//            state.getTransitionMap().forEach((k,v) -> {
//                if (v != 0) {
//                    g.insertEdge(state.getName(), automaton.getStates().get(v).getName(), "(" + (state.getName().isEmpty() ? "root" : state.getName()) + ", " + k + ")");
//                }
//            });
//            this.graphView.updateAndWait();
//        }
//
//        for (State state : automaton.getStates()){
//            g.insertEdge(state.getName(), automaton.getStates().get(state.getLink()).getName(), "link-"+state.getName());
//            stateNames.add(state.getName());
//            this.graphView.updateAndWait();
//        }
//
//        for (String s : stateNames){
//            this.graphView.getStylableEdge("link-" + s).setStyleClass("failure-link");
//        }
//        this.graphView.updateAndWait();
//    }
//
//    public void addVertex(){
//        State state;
//        for (int i=1; i<automaton.getStates().size(); i++){
//            state = automaton.getStates().get(i);
//            g.insertVertex(state.getName());
//            if (state.isAcceptingState()){
//                endStateArray.add(state.getName());
//            }
//            this.graphView.updateAndWait();
//        }
//
//        this.graphView.getStylableVertex("").setStyleClass("root");
//        this.graphView.updateAndWait();
//
//        for (String s : endStateArray){
//            this.graphView.getStylableVertex(s).setStyleClass("end");
//            this.graphView.updateAndWait();
//        }
//    }

    public void insertGraph(){
        boolean[] vis = new boolean[automaton.getStates().size()];
        Queue<Integer> q = new LinkedList<>();

        q.add(0);
        vis[0] = true;
        g.insertVertex("");

        while (!q.isEmpty()){
            Integer curr = q.poll();
            State state = automaton.getStates().get(curr);
            if (state.isAcceptingState()) {
                endStateArray.add(state.getName());
            }
            state.getTransitionMap().forEach((k, v) -> {
                if (v != 0 && !vis[v]) {
                    vis[v] = true;
                    g.insertVertex(automaton.getStates().get(v).getName());
                    g.insertEdge(state.getName(), automaton.getStates().get(v).getName(), "(" + (state.getName().isEmpty() ? "root" : state.getName()) + ", " + k + ")");
                    graphView.updateAndWait();
                    q.offer(v);
                }
            });
        }

        this.graphView.getStylableVertex("").setStyleClass("root");
        this.graphView.updateAndWait();

        for (String s : endStateArray){
            this.graphView.getStylableVertex(s).setStyleClass("end");
            this.graphView.updateAndWait();
        }

        for (State state : automaton.getStates()){
            g.insertEdge(state.getName(), automaton.getStates().get(state.getLink()).getName(), "link-"+(state.getName().isEmpty() ? "root" : state.getName()));
            stateNames.add(state.getName());
            this.graphView.updateAndWait();
        }

        for (String s : stateNames){
            this.graphView.getStylableEdge("link-" + (s.isEmpty() ? "root" : s)).setStyleClass("failure-link");
        }
    }

    public void initGraphView(){
        this.graphView.init();
//        this.addVertex();
//        this.addEdges();
        this.insertGraph();
    }
}
