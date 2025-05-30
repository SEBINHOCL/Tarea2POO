package com.example.stage1;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class VideoPublisher extends Publisher {
    public VideoPublisher(String name, Broker broker, String topicName) {
        super(name, broker, topicName);

        Label etiqueta = new Label(name + "->" + topicName + ":");
        message = new TextField();
        message.setPromptText("Ingrese URL del video...");
        message.setOnAction(e -> {
            String texto = message.getText();
            if (!texto.isEmpty()) {
                publishNewEvent(texto);
                message.clear();
            }
        });

        view = new HBox(10, etiqueta, message);
    }

    public HBox getView(){return view;}
    private HBox view;
    private TextField message;
}