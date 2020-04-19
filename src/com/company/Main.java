package com.company;

import crystal.CrystalField;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Main extends Application {


    public static void main(String[] args) {

        Application.launch(args);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {



        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        CrystalField gameField=new CrystalField();
        gameField.initialLayout(root);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());

        primaryStage.setScene(scene);// установка сцены для объекта Stage
        primaryStage.setTitle("Match game!");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}


