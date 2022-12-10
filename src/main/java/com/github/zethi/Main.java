package com.github.zethi;


import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private Dimensions dimensions;
    private final Screen screen = Screen.getPrimary();
    private final Stage program = new Stage();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws AWTException {
        Group rootGroup = new Group();
        setUpDimensions();
        setProgramOnBackground();
        setUpApplication(rootGroup);

        Canvas canvas = new Canvas(dimensions.getScreenWidth(), dimensions.getScreenWidth());
        VirtualScreen virtualScreen = new VirtualScreen(dimensions, new Robot());
        rootGroup.getChildren().add(canvas);

        int UPDATE_TIME = 1000 / 60;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            dimensions = virtualScreen.updatePropertyToActualScreen();
            resizeApplication(dimensions.getScreenWidth(), dimensions.getScreenHeight());
            virtualScreen.setImage(dimensions, canvas.getGraphicsContext2D());
        }, 0, UPDATE_TIME, TimeUnit.MILLISECONDS);

    }

    private void setUpDimensions() {
        this.dimensions = new Dimensions(screen.getBounds().getMinX(), screen.getBounds().getMinY(), screen.getBounds().getWidth(), screen.getBounds().getHeight());
    }

    private void setUpApplication(Group root) {
        program.setTitle("Focus");
        program.setScene(new Scene(root));
        program.setX(dimensions.getScreenX());
        program.setY(dimensions.getScreenY());
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