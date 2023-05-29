package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class EndGameLabel extends StackPane {
    private Label label;
    private Rectangle background;

    public EndGameLabel() {
        setupLabel();
        setupBackground();

        this.getChildren().add(background);
        this.getChildren().add(label);
        this.setMouseTransparent(true);
    }

    private void setupLabel() {
        label = new Label();
        label.setText("GAME OVER");
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 30));
        label.setTextFill(Paint.valueOf("#FFFFFF"));
    }

    private void setupBackground() {
        background = new Rectangle(500, 200);
        background.setFill(Paint.valueOf("#FF0000"));
        background.setOpacity(0.7);
    }
}
