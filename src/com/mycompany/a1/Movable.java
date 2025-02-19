package com.mycompany.a1;

import com.codename1.charts.models.Point;

// Abstract class for movement for movable objects
public abstract class Movable extends GameObject {
    private int heading; // direction of movement
    private int speed; // speed of movement
    protected int foodLevel; // All movable objects have a food level

    public Movable(int size, Point location, int color, int heading, int speed) {
        super(size, location, color);
        this.heading = heading;
        this.speed = speed;
        this.foodLevel = 100; // default food level; for Ant this will be used
    }

    // Updates the location based on current heading and speed.
    public void move() {
    	//         // Calculate angle in radians: theta = 90 - heading.
        double theta = Math.toRadians(90 - heading);
        double deltaX = Math.cos(theta) * speed;
        double deltaY = Math.sin(theta) * speed;
        float newX = getLocation().getX() + (float)deltaX;
        float newY = getLocation().getY() + (float)deltaY;
        
        // Ensure the new location remains within the world bounds (0 to 1000)
        if(newX < 0) newX = 0;
        if(newX > 1000) newX = 1000;
        if(newY < 0) newY = 0;
        if(newY > 1000) newY = 1000;
        
        // Set new location
        setLocation(new com.codename1.charts.models.Point(newX, newY));
    }

    // Getter for heading
    public int getHeading() {
        return heading;
    }

    // Setter for heading
    public void setHeading(int heading) {
        this.heading = ((heading % 360) + 360) % 360;
    }

    // Getter for speed.
    public int getSpeed() {
        return speed;
    }

    // Setter for speed, makes sure doesn't go negative.
    public void setSpeed(int speed) {
        if(speed < 0) speed = 0;
        this.speed = speed;
    }

    // Getter for food level.
    public int getFoodLevel() {
        return foodLevel;
    }

    // Setter for food level.
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }
}