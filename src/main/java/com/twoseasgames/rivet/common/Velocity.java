package com.twoseasgames.rivet.common;

public class Velocity {
	
	private static int count = 0;
	
	private int x = 0;
	private int y = 0;
	private int id;
	
	public Velocity(int x, int y) {
		id = count;
		count++;
		this.x = x;
		this.y = y;
	}
	
	public Point step(Point point, float delta) {
		return new Point(
			point.x() + (int)(delta * x),
			point.y() + (int)(delta * y)
		);
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void reset(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public String toString() {
		return "(" 
			+ String.valueOf(x)
			+ ","
			+ String.valueOf(y)
			+ ")";
	}
}
