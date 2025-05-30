package com.example.stage2;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import java.util.Optional;

public class Stage2 extends Application {
    private VBox vBoxLeft, vBoxRight;
    private Broker broker;
    public void start(Stage primaryStage) {
        broker = new Broker();
        MenuBar menuBar = new MenuBar();
        Menu menuPublisher = new Menu("Publisher");
        Menu menuSubscriber = new Menu("Subscriber");
        menuBar.getMenus().addAll(menuPublisher, menuSubscriber);
        MenuItem menuItemVideoPub = new MenuItem("Video");
        MenuItem menuItemGPSPub = new MenuItem("Car's GPS");
        menuPublisher.getItems().addAll(menuItemVideoPub, menuItemGPSPub);
        MenuItem menuItemVideoSubs = new MenuItem("Video");
        MenuItem menuItemGPSSubs = new MenuItem("Car's GPS");
        menuSubscriber.getItems().addAll(menuItemVideoSubs, menuItemGPSSubs);
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
        primaryStage.setTitle("Publisher-Subscriber Simulator"); // Set the window title
        primaryStage.setScene(scene); // Place the scene in the window
        primaryStage.show(); // Display the window
        menuItemVideoPub.setOnAction(e -> addVideoPub());
        menuItemVideoSubs.setOnAction(e -> addVideoSub());
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