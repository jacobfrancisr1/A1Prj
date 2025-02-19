package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.models.Point;

// The FoodStation is a  location where the ant can replenish its food. Fixed Object.
public class FoodStation extends Fixed {
    private int capacity;  // The current food capacity available at the station

    public FoodStation(float x, float y, int size, int capacity) {
        // FoodStations are colored green.
        super(size, new Point(x, y), ColorUtil.GREEN);
        this.capacity = capacity;
    }

    // Getter for capacity.
    public int getCapacity() {
        return capacity;
    }

    // Setter for capacity.
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    // Changes color to light green, indicating it has been used.
    public void fadeColor() {
        setColor(ColorUtil.rgb(144,238,144));
    }
    
    @Override
    public String toString() {
        return "FoodStation: " + super.toString() + " size=" + getSize() + " capacity=" + capacity;
    }
}
