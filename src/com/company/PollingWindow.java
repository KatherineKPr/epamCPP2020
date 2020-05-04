package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PollingWindow {


    public void createPollingWindow() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PollingWindow.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    @FXML
    public Button closeButton;

    @FXML
    protected void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        GameWindow gameWindow = new GameWindow();
        gameWindow.closeGameWindow();
    }

    @FXML
    protected void restart(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        GameWindow gameWindow = new GameWindow();
        gameWindow.closeGameWindow();
        Main startWindow = new Main();
        Stage newPrimaryStage = new Stage();
        startWindow.start(newPrimaryStage);
    }
}
