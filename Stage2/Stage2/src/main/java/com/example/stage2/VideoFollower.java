package com.example.stage2;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.util.Duration;

public class VideoFollower extends Subscriber {
    public VideoFollower(String name, String topicName) {
        super(name, topicName);
        Label etiqueta = new Label(name + "->" + topicName + ":");
        video = new Button("Sin video");

        video.setOnAction(e -> {
            if (currentUrl != null && !currentUrl.isEmpty()) {
                reproducirVideo(currentUrl);
            }
        });

        view = new HBox(10, etiqueta, video);
    }

    @Override
    public void update(String message) {
        currentUrl = message;
        video.setText(message);
    }

    private void reproducirVideo(String url) {
        try {
            String limpio = url.trim();
            Media media = new Media(limpio);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(640);
            mediaView.setFitHeight(360);

            // Botones de control
            Button forwardButton = new Button(">");
            forwardButton.setOnAction(e -> {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5)));
            });

            Button rewindButton = new Button("<<");
            rewindButton.setOnAction(e -> mediaPlayer.seek(mediaPlayer.getStartTime()));

            Label volumeLabel = new Label("Volumen");
            Slider volumeSlider = new Slider(0, 1, 0.5);
            mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty());

            HBox controls = new HBox(10, forwardButton, rewindButton, volumeLabel, volumeSlider);
            controls.setPadding(new Insets(10));
            controls.setStyle("-fx-alignment: center;");

            BorderPane root = new BorderPane();
            root.setCenter(mediaView);
            root.setBottom(controls);

            Scene scene = new Scene(root, 640, 400);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public HBox getView() {
        return view;
    }

    private HBox view;
    private Button video;
    private String currentUrl;
}
