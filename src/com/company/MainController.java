package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainController extends Main {
    @FXML
    private void click(ActionEvent event) throws Exception {
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("MainController.fxml"));

        Scene settingsScene = new Scene(settingsRoot, 500, 100);

        settingsRoot.setId("settingsRoot");

        Stage newSettingsStage = new Stage();
        settingsScene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());//подключение css

        newSettingsStage.setScene(settingsScene);

        newSettingsStage.centerOnScreen();

        newSettingsStage.show();
    }


}
