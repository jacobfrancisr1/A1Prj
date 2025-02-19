package com.mycompany.a1;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

// The ant is the main character controlled by the player
// Extends Movable to allow for movement
// Implements IFoodie to manage food consumption
public class Ant extends Movable implements IFoodie {
    private int maximumSpeed; // Max speed of ant
    private int foodConsumptionRate; // Rate of consumption per tick
    private int healthLevel; // Current health of ant, out of 10
    private int lastFlagReached; // The last flag the ant has reached

    public Ant(float x, float y, int size, int heading, int speed, int foodConsumptionRate) {
        super(size, new Point(x, y), ColorUtil.rgb(255, 0, 0), heading, speed); // Red color
        this.maximumSpeed = speed;
        this.foodConsumptionRate = foodConsumptionRate;
        this.healthLevel = 10;
        this.lastFlagReached = 1;
        // Set initial food level
        setFoodLevel(100);
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public int getFoodConsumptionRate() {
        return foodConsumptionRate;
    }

    public int getHealthLevel() {
        return healthLevel;
    }

    public void setHealthLevel(int healthLevel) {
        this.healthLevel = healthLevel;
    }

    public int getLastFlagReached() {
        return lastFlagReached;
    }

    public void setLastFlagReached(int flag) {
        this.lastFlagReached = flag;
    }

    @Override
    public void setFoodConsumptionRate(int rate) {
        this.foodConsumptionRate = rate;
    }

    @Override
    public String toString() {
        return "Ant: " + super.toString() + " heading=" + getHeading() + " speed=" + getSpeed() +
               " size=" + getSize() + "\nmaxSpeed=" + maximumSpeed + " foodConsumptionRate=" + foodConsumptionRate;
    }

}