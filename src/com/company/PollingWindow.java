package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PollingWindow {

    public boolean close;



    public  void createPollingWindow() throws IOException {

      //  Parent primaryRoot = FXMLLoader.load(getClass().getResource("Main.fxml"));

        Stage stage=new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("PollingWindow.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());

        stage.setScene(scene);// установка сцены для объекта Stage
        stage.show();

        //   scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());
    }

    @FXML
    public BorderPane generalArea;

    @FXML
    public Button closeButton;

    @FXML
    protected void exit(ActionEvent actionEvent){

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        close=true;

    }

    @FXML
    protected void restart(ActionEvent actionEvent) {

    }
}
