package org.example;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.GameController;
import org.example.view.MainFrame;

public class Game extends Application {
    @Override
    public void start(Stage primaryStage) {
        MainFrame frame = new MainFrame(primaryStage);
        GameController controller = new GameController(frame);
        controller.startNewGame();
        frame.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}