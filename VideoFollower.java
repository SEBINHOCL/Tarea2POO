package com.example.stage1;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class VideoFollower extends Subscriber {
    public VideoFollower(String name, String topicName) {
        super(name, topicName);
        Label etiqueta = new Label(name + "->" + topicName + ":");
        video = new Button("Sin video");
        view = new HBox(10, etiqueta, video);
    }

    @Override
    public void update(String message) {
        video.setText(message);
    }

    public HBox getView() {return view;}
   private HBox view;
   private Button video;
}