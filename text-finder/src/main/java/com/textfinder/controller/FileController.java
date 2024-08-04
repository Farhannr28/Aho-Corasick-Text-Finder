package com.textfinder.controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileController {

    private final FileChooser fileChooser = new FileChooser();
    private String inputText;
    private String[] inputPatterns;

    public FileController() {
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
    }

    public void selectFile() {
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));
                JSONObject jsonObject = new JSONObject(content);
                inputText = jsonObject.getString("text");
                JSONArray jsonPatternsList = jsonObject.getJSONArray("patterns");
                int len = jsonPatternsList.length();
                inputPatterns = new String[len];
                for (int i = 0; i < len; i++) {
                    inputPatterns[i] = jsonPatternsList.getString(i);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public String getInputText() {
        return inputText;
    }

    public String[] getInputPatterns() {
        return inputPatterns;
    }
}
