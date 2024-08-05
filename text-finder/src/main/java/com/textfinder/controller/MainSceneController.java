package com.textfinder.controller;

import com.textfinder.service.AhoCorasick;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private Label textLabel;
    @FXML
    private Label patternsLabel;
    @FXML
    private Button searchButton;

    private final FileController fileController = new FileController();
    private String text;
    private String[] patterns;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchButton.setDisable(true);
    }

    public void changeTextLabel(String newText){
        textLabel.setText(newText);
    }

    public void changePatternsLabel(String[] newPatterns){
        String newText = newPatterns[0];
        for (int i=1; i<newPatterns.length; i++){
            newText += ", " + newPatterns[i];
        }
        patternsLabel.setText(newText);
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
        ac.getAutomaton().printResults();
    }
}