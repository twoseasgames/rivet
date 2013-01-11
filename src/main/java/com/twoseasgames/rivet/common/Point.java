package com.twoseasgames.rivet.common;

public class Point {
    private int x = 0;
    private int y = 0;
    
    public Point() {
        this.x = 0;
        this.y = 0;
    }
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }
    
    public void setX(int x) {
    	this.x = x;
    }

    public void setY(int y) {
    	this.y = y;
    }

    public Point reset(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point translate(int tx, int ty) {
        this.x += tx;
        this.y += ty;
        return this;
    }

    public Point translate(Point tPoint) {
        translate(tPoint.x(), tPoint.y());
        return this;
    }
    
    public Point getTranslation(Point tPoint) {
    	return copy().translate(tPoint);
    } 

    public Point getTranslation(int tx, int ty) {
    	return copy().translate(tx, ty);
    } 

    public String toString() {
        return "(" + String.valueOf(this.x) + ", " + String.valueOf(this.y) + ")";
    }
    
    public Point copy() {
        return new Point(this.x, this.y);
    }
    
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Point)){
            return false;
        }
        Point point = (Point) other;
        if(point.x() == this.x && point.y() == this.y) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return (1000 * this.x) + this.y;
    }
}
