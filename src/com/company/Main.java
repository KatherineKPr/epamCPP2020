package com.company;

import crystal.CrystalField;

import crystal.GameAnimation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {
    GameWindow gameWindow = new GameWindow();

    public static final int MAX_CRYSTAL_NUMBER = 7;
    public static final int MIN_CRYSTAL_NUMBER = 4;

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());
        primaryStage.setScene(scene);// установка сцены для объекта Stage
        primaryStage.setTitle("Match game!");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    public BorderPane startWindow;

    @FXML
    protected void setEasyMode(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) startWindow.getScene().getWindow();
        stage.close();
        gameWindow.createGameWindow(MIN_CRYSTAL_NUMBER);
    }

    @FXML
    protected void setHardMode(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) startWindow.getScene().getWindow();
        stage.close();
        gameWindow.createGameWindow(MAX_CRYSTAL_NUMBER);
    }
}


