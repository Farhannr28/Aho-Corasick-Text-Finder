package com.textfinder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent mainScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/mainScene.fxml")));
        primaryStage.setTitle("Text Finder");
        primaryStage.setScene(new Scene(mainScene));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}