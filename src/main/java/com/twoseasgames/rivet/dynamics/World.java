package com.twoseasgames.rivet.dynamics;

import java.util.ArrayList;
import java.util.List;

import com.twoseasgames.rivet.common.Acceleration;
import com.twoseasgames.rivet.common.Point;
import com.twoseasgames.rivet.common.Rect;

public class World {

	private int gravity;
	private List<Body> bodies;
	
	public World(int gravity) {
		this.gravity = gravity;
		this.bodies = new ArrayList<Body>();
	}

	public Body createBody(Point pos) {
		Body body = new Body(pos);
		this.bodies.add(body);
		return body;
	}
	
	public Body createBody(Point pos, Rect hitbox, boolean dynamic) {
		Body body = new Body(pos, hitbox, dynamic);
		body.addAcceleration(new Acceleration(0, gravity));
		this.bodies.add(body);
		return body;
	}
	
	public void step (float delta) {
		for (Body body : bodies) {
			body.step(delta, bodies);
		}
	}
}
