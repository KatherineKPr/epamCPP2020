package com.company;
import crystal.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;

public class Main extends Application {//новый класс расширяет возможности старого,т.е. подкласс от класса

    public static void main(String[] args) {//класс Main, как и всякий главный класс приложения Java, начинает свою работу с метода main

        Application.launch(args);
    }


    @Override// дочерний класс переопределяет метод родительского
    public void start(Stage primaryStage) throws Exception {//stage-пользовательское окно, графический интерфейс
        //обязательно использовать исключение,иначе лоуд не срабатывает

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("MainController.fxml"));
        //Parent root = (Parent)loader.load();
        //((MainController)loader.getController()).setStage(primaryStage);

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        Scene scene = new Scene(root);//это контейнер верхнего уровня для всех графических элементов

        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());//подключение css

        primaryStage.setScene(scene);// установка сцены для объекта Stage
        primaryStage.setTitle("Match game!");
        primaryStage.setMaximized(true);//full screen
        primaryStage.show();
    }


}


