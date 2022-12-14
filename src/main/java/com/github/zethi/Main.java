package com.github.zethi;

import com.github.zethi.cursor.Cursor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Robot;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    private final Screen screen = Screen.getPrimary();
    private final Stage program = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group rootGroup = new Group();
        setProgramOnBackground();
        setUpApplication(rootGroup);

        Canvas canvas = new Canvas(screen.getBounds().getWidth(), screen.getBounds().getWidth());
        rootGroup.getChildren().add(canvas);
        Dimensions dimensions = new Dimensions(screen.getBounds().getMinX(), screen.getBounds().getMinY(), screen.getBounds().getWidth(), screen.getBounds().getHeight());
        VirtualScreen virtualScreen = new VirtualScreen(dimensions, new Robot(), new Cursor());

        int UPDATE_TIME = 1000 / 30;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            if (!virtualScreen.haveToUpdateScreen()) {
                virtualScreen.setImage(canvas.getGraphicsContext2D());
                return;
            }
            virtualScreen.updateScreen();
            resizeApplication(virtualScreen.getCurrentDimensions().screenWidth(), virtualScreen.getCurrentDimensions().screenHeight());
        }, 0, UPDATE_TIME, TimeUnit.MILLISECONDS);
    }

    private void setUpApplication(Group root) {
        program.setTitle("Focus");
        program.setScene(new Scene(root));
        program.setX(screen.getBounds().getMinX());
        program.setY(screen.getBounds().getMinY());
        program.show();
        program.toBack();
        program.setOpacity(0.0);
    }

    private void setProgramOnBackground() {
        program.initStyle(StageStyle.UNDECORATED);
    }

    private void resizeApplication(double newWidth, double newHeight) {
        program.setHeight(newHeight);
        program.setWidth(newWidth);
    }
}