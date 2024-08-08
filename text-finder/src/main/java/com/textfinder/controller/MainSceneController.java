package com.textfinder.controller;

import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.textfinder.models.Automaton;
import com.textfinder.models.Matches;
import com.textfinder.service.AhoCorasick;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.textfinder.view.AutomatonGraph;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class MainSceneController implements Initializable {

    @FXML
    private Label textLabel;
    @FXML
    private Label patternsLabel;
    @FXML
    private Button searchButton;
    @FXML
    private Label patternCountLabel;
    @FXML
    private BorderPane automatonGraphPane;

    private final FileController fileController = new FileController();
    private String text;
    private String[] patterns;
    private Matches matches;
    private Automaton automaton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchButton.setDisable(true);
    }

    public void changeTextLabel(String newText){
        textLabel.setText(newText);
    }

    public void changePatternsLabel(String[] newPatterns){
        StringBuilder newText = new StringBuilder(newPatterns[0]);
        for (int i=1; i<newPatterns.length; i++){
            newText.append(", ").append(newPatterns[i]);
        }
        patternsLabel.setText(newText.toString());
    }

    public void clickedSelectFileButton(MouseEvent e) {
        // System.out.println("clickedSelectFileButton");
        fileController.selectFile();
        text = fileController.getInputText();
        patterns = fileController.getInputPatterns();
        changeTextLabel(text);
        changePatternsLabel(patterns);
        searchButton.setDisable(false);
    }

    public void clickedSearchButton(MouseEvent e) {
        // System.out.println("clickedSearchButton");
        AhoCorasick ac = new AhoCorasick(text, patterns);
        this.matches = ac.getMatches();
        // highlightTextLabel(matches);
        setPatternCountLabel();
        this.automaton = ac.getAutomaton();
        renderAutomatonGraph();
    }

    public void setPatternCountLabel(){
        String result = "";
        ArrayList<int[]> arr = null;
        int[] match;
        for (String pattern : this.patterns){
            arr = this.matches.getMap().get(pattern.toLowerCase());
            if (arr == null){
                result += "• " + pattern + " : " + 0 + "x\n";
            } else {
                result += "• " + pattern + " : " + arr.size() + "x\n";
                if (!arr.isEmpty()) {
                    result += "        Index : ";
                    match = arr.getFirst();
                    result += "[" + match[0] + ", " + match[1] + "]";
                    for (int i = 1; i < arr.size(); i++) {
                        match = arr.get(i);
                        result += ", [" + match[0] + ", " + match[1] + "]";
                    }
                    result += "\n";
                }
            }
        }
        patternCountLabel.setText(result);
    }

    public void renderAutomatonGraph(){
        AutomatonGraph ag = new AutomatonGraph(this.automaton);
        this.automatonGraphPane.setCenter(ag.getGraphView());
        Platform.runLater(ag::initGraphView);
    }
}