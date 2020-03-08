package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class MainController extends Main {

    @FXML
    private void click(ActionEvent event) throws Exception {
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("MainController.fxml"));

        Scene settingsScene = new Scene(settingsRoot, 500, 100);

        settingsRoot.setId("settingsRoot");

        Stage newSettingsStage = new Stage();
        newSettingsStage.initStyle(StageStyle.UNDECORATED);//убираем кнопки управления окном
        settingsScene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());//подключение css

        newSettingsStage.setScene(settingsScene);

        // newSettingsStage.initModality(Modality.WINDOW_MODAL);//блокирует родительское
        //   newSettingsStage.initOwner(primaryStage);//обязательно,чтобы связать окна

        newSettingsStage.centerOnScreen();

        newSettingsStage.show();
    }
}
