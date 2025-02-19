package com.mycompany.a1;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

// The Spider represents the ants enemy.
// extends movable for movement
// Doesn't implement IFoodie b/c doesn't eat food.

// Constructors for spider class, setting up position, size, heading, speed, color
public class Spider extends Movable {
    public Spider(float x, float y, int size, int heading, int speed) {
    	super(size, new Point(x, y), ColorUtil.rgb(0,0,0), heading, speed);
        setFoodLevel(0);
        }

    @Override
    public void move() {
        // Call move from movable
        super.move();
        // If the spider is at the world boundary, change heading to stay in bounds.
        com.codename1.charts.models.Point loc = getLocation();
        if (loc.getX() <= 0 || loc.getX() >= 1000 || loc.getY() <= 0 || loc.getY() >= 1000) {
            setHeading(getHeading() + 180);
        }
    }

    @Override
    public String toString() {
        return "Spider: " + super.toString() + " heading=" + getHeading() + " speed=" + getSpeed() +
               " size=" + getSize();
    }

}