package com.mycompany.a1;

import java.util.ArrayList;
import java.util.Random;
import com.codename1.charts.util.ColorUtil;

// Game manager, game objects, logic, progression, etc
public class GameWorld {
    private ArrayList<GameObject> gameObjects;  // List of all objects in the game world
    private int clock;                          // Game clock/tick counter
    private int lives;                          // Number of lives remaining for the ant
    private Ant ant;
    private Random rand;                        // Random number generator for game events
    private boolean exitPending = false;        // Flag for exit confirmation

    // Initialize the list, clock, lives, and random generator.
    public GameWorld() {
        gameObjects = new ArrayList<>();
        clock = 0;
        lives = 3;
        rand = new Random();
    }

    // CREATE OBJECTS
    public void init() {
        // Clear any existing objects.
        gameObjects.clear();
        // Create 4 Flags
        gameObjects.add(new Flag(200, 200, 10, 1));
        gameObjects.add(new Flag(200, 800, 10, 2));
        gameObjects.add(new Flag(700, 800, 10, 3));
        gameObjects.add(new Flag(900, 400, 10, 4));
        // Create the Ant that starts at Flag 1
        ant = new Ant(200, 200, 40, 0, 50, 2);
        gameObjects.add(ant);
        // Create two Spiders with random attributes.
        int spiderSize = rand.nextInt(21) + 10; // Size between 10 and 30.
        int spiderHeading = rand.nextInt(360);
        int spiderSpeed = rand.nextInt(6) + 5;     // Speed between 5 and 10.
        gameObjects.add(new Spider(rand.nextInt(1000), rand.nextInt(1000), spiderSize, spiderHeading, spiderSpeed));
        spiderSize = rand.nextInt(21) + 10;
        spiderHeading = rand.nextInt(360);
        spiderSpeed = rand.nextInt(6) + 5;
        gameObjects.add(new Spider(rand.nextInt(1000), rand.nextInt(1000), spiderSize, spiderHeading, spiderSpeed));
        // Create two FoodStations with random attributes.
        int foodSize = rand.nextInt(11) + 10; // Size between 10 and 20.
        int capacity = foodSize; // For example, capacity equals size.
        gameObjects.add(new FoodStation(rand.nextInt(1000), rand.nextInt(1000), foodSize, capacity));
        foodSize = rand.nextInt(11) + 10;
        capacity = foodSize;
        gameObjects.add(new FoodStation(rand.nextInt(1000), rand.nextInt(1000), foodSize, capacity));
    }



    // Increases the ant's speed
    public void accelerate() {
        // Check if ant has enough food to go faster
        if(ant.getFoodLevel() <= 0 || ant.getHealthLevel() <= 0) {
            System.out.println("Ant cannot accelerate, no food or health.");
            return;
        }
        int currentSpeed = ant.getSpeed();
        int increment = 5;
        int newSpeed = currentSpeed + increment;
        // Maximum speed = maximum speed scaled by the ant's health
        int allowedMaxSpeed = (int)(ant.getMaximumSpeed() * (ant.getHealthLevel() / 10.0));
        if(newSpeed > allowedMaxSpeed) {
            newSpeed = allowedMaxSpeed;
        }
        ant.setSpeed(newSpeed);
        System.out.println("Accelerating the ant. New speed: " + newSpeed);
    }

    // Decreases the ant's speed by a fixed amount.
    public void brake() {
        int currentSpeed = ant.getSpeed();
        int decrement = 5;
        int newSpeed = currentSpeed - decrement;
        if(newSpeed < 0) newSpeed = 0;
        ant.setSpeed(newSpeed);
        System.out.println("Braking the ant. New speed: " + newSpeed);
    }

    // Turns the ant to the left by decreasing its heading.
    public void turnLeft() {
        int currentHeading = ant.getHeading();
        ant.setHeading(currentHeading - 5);
        System.out.println("Turning the ant left. New heading: " + ant.getHeading());
    }

    // Turns the ant to the right by increasing its heading.
    public void turnRight() {
        int currentHeading = ant.getHeading();
        ant.setHeading(currentHeading + 5);
        System.out.println("Turning the ant right. New heading: " + ant.getHeading());
    }

    // Increases the ant's food consumption rate.
    public void setFoodConsumptionRate() {
        ant.setFoodConsumptionRate(ant.getFoodConsumptionRate() + 1);
        System.out.println("Setting the ant's food consumption rate. New rate: " + ant.getFoodConsumptionRate());
    }

     // Handles the collisions with the flags
    public void flagCollision(int flagNumber) {
        if(flagNumber == ant.getLastFlagReached() + 1) {
            ant.setLastFlagReached(flagNumber);
            System.out.println("Ant reached flag " + flagNumber);
            // Check for win condition, if you hit flag 4 you win
            if(flagNumber == 4) {
                System.out.println("Game over, you win! Total time: " + clock);
                System.exit(0);
            }
        } else {
            System.out.println("Flag collision ignored. Expected flag: " + (ant.getLastFlagReached() + 1));
        }
    }


    // Handles collision with a FoodStation.
    public void collideWithFoodStation() {
        // Find a non-empty FoodStation.
        ArrayList<FoodStation> nonEmptyStations = new ArrayList<>();
        for(GameObject obj : gameObjects) {
            if(obj instanceof FoodStation) {
                FoodStation fs = (FoodStation)obj;
                if(fs.getCapacity() > 0) {
                    nonEmptyStations.add(fs);
                }
            }
        }
        if(nonEmptyStations.size() > 0) {
            // Choose a random non-empty FoodStation.
            FoodStation fs = nonEmptyStations.get(rand.nextInt(nonEmptyStations.size()));
            // Increase the ant's food level by the station's capacity.
            ant.setFoodLevel(ant.getFoodLevel() + fs.getCapacity());
            System.out.println("Ant collided with FoodStation. Food level increased to: " + ant.getFoodLevel());
            // Set the FoodStation's capacity to 0 and fade its color.
            fs.setCapacity(0);
            fs.fadeColor();
            // Add a new FoodStation with random attributes.
            int foodSize = rand.nextInt(11) + 10;
            int capacity = foodSize;
            FoodStation newFS = new FoodStation(rand.nextInt(1000), rand.nextInt(1000), foodSize, capacity);
            gameObjects.add(newFS);
            System.out.println("New FoodStation added to the game.");
        } else {
            System.out.println("No non-empty FoodStation available for collision.");
        }
    }

    // Handles collision with a Spider. Decreases the ant's health, adjusts its color and speed, and causes the ant to lose a life if health reaches zero.
    public void collideWithSpider() {
        ant.setHealthLevel(ant.getHealthLevel() - 1);
        System.out.println("Ant collided with Spider. Health decreased to: " + ant.getHealthLevel());
        // Fade the ant's color to a lighter red.
        int currentColor = ant.getColor();
        int red = ColorUtil.red(currentColor);
        int newColor = ColorUtil.rgb(red, 100, 100); // Arbitrary lighter red.
        ant.setColor(newColor);
        // Adjust speed
        int allowedMaxSpeed = (int)(ant.getMaximumSpeed() * (ant.getHealthLevel() / 10.0));
        if(ant.getSpeed() > allowedMaxSpeed) {
            ant.setSpeed(allowedMaxSpeed);
            System.out.println("Ant's speed reduced to allowed maximum: " + allowedMaxSpeed);
        }
        // If health is 0 or less, the ant loses a life.
        if(ant.getHealthLevel() <= 0) {
            loseLife();
        }
    }

   // when clock ticks, it Updates the positions of movable objects, reduces food level, and checks for game conditions
    public void clockTick() {
        // Update all movable objects.
        for(GameObject obj : gameObjects) {
            if(obj instanceof Spider) {
                Spider sp = (Spider)obj;
                // Randomly change the spider's heading by +/- 5 degrees.
                int delta = rand.nextBoolean() ? 5 : -5;
                sp.setHeading(sp.getHeading() + delta);
            }
            if(obj instanceof Movable) {
                ((Movable)obj).move();
            }
        }
        // Reduce the ant's food level.
        ant.setFoodLevel(ant.getFoodLevel() - ant.getFoodConsumptionRate());
        clock++;
        System.out.println("Clock ticked. Clock: " + clock);
        // If the ant runs out of food, it loses a life.
        if(ant.getFoodLevel() <= 0) {
            System.out.println("Ant has run out of food.");
            loseLife();
        }
    }


    // handles lost life, reset stats if you have more lives, but die if you have none left.
    private void loseLife() {
        lives--;
        if(lives > 0) {
            System.out.println("Ant has lost a life. Lives remaining: " + lives);
            // Reset ant's stats for the next life
            ant.setFoodLevel(100);
            ant.setHealthLevel(10);
            ant.setSpeed(50);
            ant.setHeading(0);
            ant.setLastFlagReached(1);
            // Place the ant back at the starting flag location
            ant.setLocation(new com.codename1.charts.models.Point(200, 200));
        } else {
            System.out.println("Game over, you failed!");
            System.exit(0);
        }
    }

    // Displays a summary of the current game state.
    public void display() {
        System.out.println("Lives: " + lives + ", Clock: " + clock + 
                           ", Last Flag Reached: " + ant.getLastFlagReached() +
                           ", Ant Food Level: " + ant.getFoodLevel() +
                           ", Ant Health Level: " + ant.getHealthLevel());
    }

    // Prints the details of every game object in the game world.
    public void map() {
        for (GameObject obj : gameObjects) {
            System.out.println(obj);
        }
    }

    // Getter for the exitPending flag
    public boolean isExitPending() {
        return exitPending;
    }

    // Setter for the exitPending flag
    public void setExitPending(boolean flag) {
        exitPending = flag;
    }

    // Initiates the exit process by setting the exit confirmation flag.
    public void exit() {
        setExitPending(true);
        System.out.println("Are you sure you want to exit? (y/n)");
    }
}
