package com.twoseasgames.rivet.common;

public class Size {

    private int width = 0;
    private int height = 0;
    
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public String toString() {
        return "(" + String.valueOf(this.width) + ", " + String.valueOf(this.height) + ")";
    }
    
}
