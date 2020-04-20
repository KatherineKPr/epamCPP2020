package com.company;

import crystal.CrystalField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameWindow {

    static Stage primaryStage=new Stage();

    public void createGameWindow(int crystalNumber) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("GameWindow.fxml"));

        CrystalField gameField=new CrystalField();
        gameField.initialLayout(root, crystalNumber);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Match game!");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public  void closeGameWindow(){
        primaryStage.close();
    }
}
