package com.github.zethi;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VirtualScreen {

    private Dimensions dimensions;
    private Screen screen;
    private final Robot robot;
    private String actualScreen;

    public VirtualScreen(Dimensions dimensions, Robot robot) {
        this.robot = robot;
        this.dimensions = dimensions;
        this.actualScreen = MouseInfo.getPointerInfo().getDevice().getIDstring();
    }

    public Dimensions updatePropertyToActualScreen() {
        if (actualScreen.equals(MouseInfo.getPointerInfo().getDevice().getIDstring())) return this.dimensions;
        actualScreen = MouseInfo.getPointerInfo().getDevice().getIDstring();
        Screen currentScreen = Screen.getScreensForRectangle(new Rectangle2D(
                MouseInfo.getPointerInfo().getLocation().getX(),
                MouseInfo.getPointerInfo().getLocation().getY(),
                1, 1)).get(0);
        if (currentScreen != screen) {
            screen = currentScreen;
            this.dimensions = new Dimensions(screen.getBounds().getMinX(), screen.getBounds().getMinY(), screen.getBounds().getWidth(), screen.getBounds().getHeight());
        }

        return this.dimensions;
    }

    public void setImage(Dimensions dimensions, GraphicsContext graphicContext) {
        BufferedImage screenImage = robot.createScreenCapture(new Rectangle((int) dimensions.getScreenX(), (int) dimensions.getScreenY(),
                (int) dimensions.getScreenWidth(), (int) dimensions.getScreenHeight()));

        WritableImage fxImage = new WritableImage(screenImage.getWidth(), screenImage.getHeight());

        for (int x = 0; x < screenImage.getWidth(); x++) {
            for (int y = 0; y < screenImage.getHeight(); y++) {
                fxImage.getPixelWriter().setArgb(x, y, screenImage.getRGB(x, y));
            }
        }

        graphicContext.drawImage(fxImage, 0, 0);
    }
}
