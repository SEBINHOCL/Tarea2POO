package com.example.stage3;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;

public class Stage3 extends Application {
    private VBox vBoxLeft, vBoxRight;
    private Broker broker;
    private Stage primaryStage;
    private FileChooser fileChooser;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        broker = new Broker();
        fileChooser = new FileChooser();
        MenuBar menuBar = new MenuBar();
        Menu menuPublisher = new Menu("Publisher");
        Menu menuSubscriber = new Menu("Subscriber");
        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);

        MenuItem menuItemGPSPub = new MenuItem("Car's GPS");
        menuPublisher.getItems().add(menuItemGPSPub);

        MenuItem menuItemGPSSubs = new MenuItem("Car's GPS");
        menuSubscriber.getItems().add(menuItemGPSSubs);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        vBoxLeft = new VBox(5);
        vBoxLeft.setAlignment(Pos.CENTER);
        borderPane.setLeft(vBoxLeft);
        vBoxRight = new VBox(10);
        vBoxRight.setAlignment(Pos.CENTER);
        borderPane.setRight(vBoxRight);

        ScrollPane scrollPane = new ScrollPane(borderPane);
        Scene scene = new Scene(scrollPane, 800, 400);
        primaryStage.setTitle("Publisher-Subscriber Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(true);

        // Acciones de menú
        menuItemGPSPub.setOnAction(e -> addGPSPub());
        menuItemGPSSubs.setOnAction(e -> addGPSSub());
    }

    private String getInputString(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(prompt);
        dialog.setHeaderText("Please enter: " + prompt);
        dialog.setContentText(prompt + ":");
        Optional<String> result = dialog.showAndWait();

        return result.filter(s -> !s.trim().isEmpty()).orElse(null);
    }

    private void addGPSPub() {
        try {
            String name = getInputString("GPS Publisher Name");
            if (name == null) return;

            String topic = getInputString("GPS Publisher Topic");
            if (topic == null) return;

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file == null) return;

            Scanner scanner = new Scanner(file);
            GPSCarPublisher pub = new GPSCarPublisher(name, broker, topic, scanner);
            vBoxLeft.getChildren().add(pub.getView());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void addGPSSub() {
        String name = getInputString("GPS Subscriber Name");
        if (name == null) return;

        String topic = getInputString("GPS Subscriber Topic");
        if (topic == null) return;

        CarTracker sub = new CarTracker(name, topic);
        if (broker.subscribe(sub)) {
            sub.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
