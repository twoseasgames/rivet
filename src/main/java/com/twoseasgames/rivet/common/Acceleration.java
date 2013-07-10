package com.twoseasgames.rivet.common;

import java.util.List;

public class Acceleration {

    private static int count = 0;

    private int x;
    private int y;
    private int id;
    
    public Acceleration(int x, int y) {
        id = count;
        count++;
        this.x = x;
        this.y = y;
    }
    
    public Velocity step(Velocity velocity, float delta) {
        return new Velocity(
            velocity.x() + (int)(delta * x),
            velocity.y() + (int)(delta * y)
        );
    }

    public static Acceleration sum(List<Acceleration> accelerations) {
        Acceleration result = new Acceleration(0, 0);
        for (Acceleration acceleration : accelerations) {
            result.x += acceleration.x;
            result.y += acceleration.y;
        }
        return result;
    }
    
    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int id() {
        return id;
    }
}
