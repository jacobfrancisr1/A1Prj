package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.models.Point;


public class Flag extends Fixed {
    private int sequenceNumber;

    public Flag(float x, float y, int size, int sequenceNumber) {
        super(size, new Point(x, y), ColorUtil.BLUE);
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    // Prevent changing the flag's color.
    @Override
    public void setColor(int color) {
    }

    @Override
    public String toString() {
        return "Flag: " + super.toString() + " size=" + getSize() + " seqNum=" + sequenceNumber;
    }
}