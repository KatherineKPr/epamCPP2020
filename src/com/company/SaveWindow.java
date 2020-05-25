package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class SaveWindow extends Main {
    String activeDir = "src\\replay";
    String testFile = "test.json";

    @FXML
    private void clickSave(ActionEvent event) throws Exception {
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("SaveWindow.fxml"));
        Scene settingsScene = new Scene(settingsRoot, 500, 300);
        settingsRoot.setId("saveRoot");
        Stage saveStage = new Stage();
        settingsScene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());
        saveStage.getIcons().add(new Image("/assets/images/icon.png"));
        saveStage.setScene(settingsScene);
        saveStage.centerOnScreen();
        saveStage.show();
    }

    @FXML
    public Button confirmButton;
    public TextField textField;

    @FXML
    protected void closeSaveWindow(ActionEvent actionEvent) throws IOException {

        // Drawer d = new Drawer();
        //d.createGameWindow();
        String newFileName;
        newFileName = textField.getText() + ".json";
        File tFile = new File(activeDir, testFile);
        File file = new File(activeDir, newFileName);
        tFile.renameTo(file);
        file = new File(activeDir, testFile);
        file.createNewFile();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

  //  protected void clickView(ActionEvent actionEvent) {
  //  }
}


