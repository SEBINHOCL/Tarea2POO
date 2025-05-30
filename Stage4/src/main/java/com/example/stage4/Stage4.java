package com.example.stage4;

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

public class Stage4 extends Application {
    private VBox vBoxLeft, vBoxRight;
    private Broker broker;
    private FileChooser fileChooser;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        broker = new Broker();
        fileChooser = new FileChooser();

        MenuBar menuBar = new MenuBar();
        Menu menuPublisher = new Menu("Publisher");
        Menu menuSubscriber = new Menu("Subscriber");
        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);

        MenuItem menuItemVideoPub = new MenuItem("Video");
        menuPublisher.getItems().addAll(menuItemVideoPub);

        MenuItem menuItemVideoSub = new MenuItem("Video");
        menuSubscriber.getItems().addAll(menuItemVideoSub);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        vBoxLeft = new VBox(5);
        vBoxLeft.setAlignment(Pos.TOP_CENTER);
        root.setLeft(vBoxLeft);

        vBoxRight = new VBox(10);
        vBoxRight.setAlignment(Pos.TOP_CENTER);
        root.setRight(vBoxRight);

        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Publisher-Subscriber Simulator");
        primaryStage.setResizable(true);
        primaryStage.show();

        // Acciones de menú
        menuItemVideoPub.setOnAction(e -> addVideoPub());

        menuItemVideoSub.setOnAction(e -> addVideoSub());
    }

    private String getInputString(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(prompt);
        dialog.setHeaderText("Please enter: " + prompt);
        dialog.setContentText(prompt + ":");
        Optional<String> result = dialog.showAndWait();

        return result.filter(s -> !s.trim().isEmpty()).orElse(null);
    }


    private void addVideoPub() {
        String name = getInputString("Video Publisher Name");
        if (name == null) return;

        String topic = getInputString("Video Publisher Topic");
        if (topic == null) return;

        vBoxLeft.getChildren().add(new VideoPublisher(name, broker, topic).getView());
    }


    private void addVideoSub() {
        String name = getInputString("Video Subscriber Name");
        if (name == null) return;

        String topic = getInputString("Video Subscriber Topic");
        if (topic == null) return;

        VideoFollower sub = new VideoFollower(name, topic);
        if (broker.subscribe(sub)) {
            vBoxRight.getChildren().add(sub.getView());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
