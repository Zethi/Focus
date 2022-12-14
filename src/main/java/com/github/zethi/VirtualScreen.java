package com.github.zethi;

import com.github.zethi.cursor.Cursor;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VirtualScreen {

    private Dimensions dimensions;
    private final Robot robot;
    private final com.github.zethi.cursor.Cursor cursor;
    private String screenID;


    public VirtualScreen(Dimensions dimensions, Robot robot, Cursor cursor) {
        this.robot = robot;
        this.dimensions = dimensions;
        this.cursor = cursor;
        this.screenID = cursor.getFocusScreenID();
    }

    public Dimensions getCurrentDimensions() {
        return this.dimensions;
    }

    public void updateScreen() {
        setScreenID(cursor.getFocusScreenID());
        Screen newScreen = Screen.getScreensForRectangle(new Rectangle2D(
                cursor.getLocation().X(),
                cursor.getLocation().Y(),
                1, 1)).get(0);

        Dimensions newScreenDimensions = new Dimensions(
                newScreen.getBounds().getMinX(),
                newScreen.getBounds().getMinY(),
                newScreen.getBounds().getWidth(),
                newScreen.getBounds().getHeight());

        setDimensions(newScreenDimensions);
    }

    public void setImage(GraphicsContext graphicContext) {
        BufferedImage screenImage = robot.createScreenCapture(new Rectangle((int) dimensions.screenX(), (int) dimensions.screenY(),
                (int) dimensions.screenWidth(), (int) dimensions.screenHeight()));

        WritableImage fxImage = new WritableImage(screenImage.getWidth(), screenImage.getHeight());

        for (int x = 0; x < screenImage.getWidth(); x++) {
            for (int y = 0; y < screenImage.getHeight(); y++) {
                fxImage.getPixelWriter().setArgb(x, y, screenImage.getRGB(x, y));
            }
        }

        graphicContext.drawImage(fxImage, 0, 0);
    }

    public boolean haveToUpdateScreen() {
        return !cursor.getFocusScreenID().equals(screenID);
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public void setScreenID(String pointerFocusScreenID) {
        this.screenID = pointerFocusScreenID;
    }


}
