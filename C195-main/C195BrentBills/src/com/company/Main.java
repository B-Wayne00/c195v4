package com.company;

import Helper.JDBC;
import java.io.IOException;

import Model.Contact;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/LogIn.fxml"));
        primaryStage.setTitle("Appointment Scheduling System");
        primaryStage.setScene(new Scene(root, 350, 300));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
