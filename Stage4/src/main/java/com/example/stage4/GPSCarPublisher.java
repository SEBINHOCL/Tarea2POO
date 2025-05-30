package com.example.stage4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Scanner;

public class GPSCarPublisher extends Publisher {

    public GPSCarPublisher(String name, Broker broker, String topicName, Scanner scanner) {
        super(name, broker, topicName);
        view = new HBox();
        GPS = new Label();
        view.getChildren().addAll(new Label(name + "->" + topicName + ":"), GPS);
        GPSfile = scanner;
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> reportPosition()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        prepareNextLine();
        timeline.play();
    }

    private void reportPosition() {
        elapsed += 1.0;

        while (elapsed > time_f) {
            if (!prepareNextLine()) {
                timeline.stop();
                return;
            }
        }

        double fraction = (elapsed - time_i) / (time_f - time_i);
        double x = xi + (xf - xi) * fraction;
        double y = yi + (yf - yi) * fraction;

        GPS.setText(String.format(" %.1f %.1f %.1f", elapsed, x, y));

        // Formato solicitado: Nombre->Topico:t x y
        publishNewEvent(String.format("%s->%s:%.1f %.1f %.1f", getName(), getTopicName(), elapsed, x, y));
    }

    private boolean prepareNextLine() {
        if (!GPSfile.hasNextLine()) return false;

        String line = GPSfile.nextLine();
        Scanner lineScanner = new Scanner(line);

        time_i = time_f;
        xi = xf;
        yi = yf;

        time_f = lineScanner.nextDouble();
        xf = lineScanner.nextDouble();
        yf = lineScanner.nextDouble();

        return true;
    }

    public HBox getView() {
        return view;
    }

    private HBox view;
    private Label GPS;
    private Scanner GPSfile;
    private double xi = 0, yi = 0, xf, yf, time_i = 0, time_f, elapsed = 0;
    private Timeline timeline;
}
