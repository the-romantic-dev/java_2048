package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Tile extends StackPane {
    public final static int TILE_SIZE = 100;
    private static final Color[] TILE_COLORS = {
            Color.valueOf("#CDC1B4"), Color.valueOf("#EDE0C8"), Color.valueOf("#F2B179"),
            Color.valueOf("#F59563"), Color.valueOf("#F67C5F"), Color.valueOf("#F65E3B"),
            Color.valueOf("#EDCF72"), Color.valueOf("#EDCC61"), Color.valueOf("#EDC850"),
            Color.valueOf("#EDC53F"), Color.valueOf("#EDC22E"), Color.valueOf("#3C3A32")
    };

    private final Label label;
    private final Rectangle border;

    public Tile() {
        border = new Rectangle(TILE_SIZE, TILE_SIZE);
        border.setFill(Color.WHITE);
        border.setStroke(Color.GRAY);

        label = new Label();
        label.setFont(Font.font("Arial", 24));
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.BLACK);

        getChildren().addAll(border, label);
        setAlignment(Pos.CENTER);
    }

    public Tile(int value) {
        this();
        setValue(value);
    }

    public void setValue(int value) {
        if (value == 0) {
            label.setText("");
            this.setVisible(false);
        } else {
            label.setText(String.valueOf(value));
            this.setVisible(true);
        }

        setColor(value);
    }

    private void setColor(int value) {
        int exponent = value > 0 ? (int) (Math.log(value) / Math.log(2)) : 0;
        int colorIndex = Math.min(exponent, TILE_COLORS.length - 1);
        Color tileColor = TILE_COLORS[colorIndex];
        border.setFill(tileColor);
    }
}
