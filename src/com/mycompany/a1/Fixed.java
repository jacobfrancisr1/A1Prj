package com.mycompany.a1;

import com.codename1.charts.models.Point;

public abstract class Fixed extends GameObject {
    public Fixed(int size, Point location, int color) {
        super(size, location, color);
    }
 // Prevent fixed objects from changing location after creation.
    @Override
    public void setLocation(Point location) {
        // Do nothing.
    }

}