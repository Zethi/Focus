package com.github.zethi.cursor;


import java.awt.*;

public final class Cursor {

    public Cursor() {
    }

    public String getFocusScreenID() {
        return MouseInfo.getPointerInfo().getDevice().getIDstring();
    }

    public CursorLocation getLocation() {
        return new CursorLocation(MouseInfo.getPointerInfo().getLocation().getX(), MouseInfo.getPointerInfo().getLocation().getY());
    }

}
