package com.company;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.json.simple.parser.ParseException;
import replay.Drawer;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private void clickView(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        VBox vBox = new VBox();
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]+.json");
        File originalFile = new File("src\\replay\\test.json");//делай константой
        File folder = originalFile.getParentFile();
        for (File file : folder.listFiles()) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()) {
                Button btn = new Button(file.getName());
                btn.setPrefWidth(100);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Drawer drawer = new Drawer();
                        try {
                            drawer.createGameWindow(file.getName());
                        } catch (IOException | ParseException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        stage.close();
                    }
                });
                vBox.getChildren().add(btn);
            }
        }
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #ffb3b3");
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.setTitle("View replays");
        stage.getIcons().add(new Image("/assets/images/icon.png"));
        stage.setWidth(300);
        stage.setHeight(250);
        stage.show();
    }

    @FXML
    public Button confirmButton;
    public TextField textField;

    @FXML
    protected void closeSaveWindow(ActionEvent actionEvent) throws IOException {
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
}


