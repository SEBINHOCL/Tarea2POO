package com.example.stage4;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CarTracker extends Subscriber {
    public CarTracker(String name, String topicName) {
        super(name, topicName);
        telemetry = new Label("t 0.0 x: 0.0, y: 0.0");
        car = new Circle(10, Color.RED);

        Pane map = new Pane();
        map.setPrefSize(400, 400);
        map.getChildren().add(car);

        BorderPane layout = new BorderPane();
        layout.setCenter(map);
        layout.setBottom(telemetry);
        BorderPane.setAlignment(telemetry, Pos.CENTER);

        Scene scene = new Scene(layout, 400, 450);
        stage = new Stage();
        stage.setTitle("Car Tracker: " + name + ", Topic: " + topicName);
        stage.setScene(scene);
    }

    @Override
    public void update(String message) {
        try {
            String[] tokens = message.split(":");
            String[] data = tokens[1].trim().split("\\s+");

            double t = Double.parseDouble(data[0].replace(',', '.'));
            double x = Double.parseDouble(data[1].replace(',', '.'));
            double y = Double.parseDouble(data[2].replace(',', '.'));

            telemetry.setText(String.format("t %.1f x: %.1f, y: %.1f", t, x, y));
            car.setTranslateX(x);
            car.setTranslateY(y);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void show() {
        stage.show();
    }

    private final Stage stage;
    private final Circle car;
    private final Label telemetry;
}
