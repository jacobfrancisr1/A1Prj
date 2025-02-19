package com.mycompany.a1;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

// base class for all objects, stores size, location, and color
public abstract class GameObject {
    private int size;         // Size of the object
    private Point location;   // Current location of the object
    private int color;        // Color of the object in RGB format

    public GameObject(int size, Point location, int color) {
        this.size = size;
        this.location = location;
        this.color = color;
    }

    // Getter for size.
    public int getSize() {
        return size;
    }

    // Getter for location.
    public Point getLocation() {
        return location;
    }

    // Setter for location.
    public void setLocation(Point location) {
        this.location = location;
    }

    // Getter for color.
    public int getColor() {
        return color;
    }

    // Setter for color.
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        // Round x and y values to one decimal place.
        float x = Math.round(location.getX() * 10) / 10.0f;
        float y = Math.round(location.getY() * 10) / 10.0f;
        return "loc=" + x + "," + y + " color=[" + 
               ColorUtil.red(color) + "," + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "]";
    }
}
