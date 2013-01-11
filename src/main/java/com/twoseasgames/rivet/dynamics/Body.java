package com.twoseasgames.rivet.dynamics;

import java.util.ArrayList;
import java.util.List;

import com.twoseasgames.rivet.common.Acceleration;
import com.twoseasgames.rivet.common.Point;
import com.twoseasgames.rivet.common.Rect;
import com.twoseasgames.rivet.common.Size;
import com.twoseasgames.rivet.common.Velocity;

public class Body {

	private static int count = 0;

	public interface Listener {
		
		public boolean onCollideTop(Body other);
		public boolean onCollideRight(Body other);
		public boolean onCollideBottom(Body other);
		public boolean onCollideLeft(Body other);
		
	}

	private Rect hitbox = null;
	private Point pos;
	private boolean dynamic = false;
	private List<Acceleration> accelerations;
	private Listener listener = null;
	private Velocity velocity;
	private int id;

	public Body(Point pos) {
		id = count;
		count++;
		this.pos = pos;
		this.accelerations = new ArrayList<Acceleration>();
		this.velocity = new Velocity(0, 0);
	}
	
	public Body(Point pos, Size hitboxSize, boolean dynamic) {
		id = count;
		count++;
		hitbox = new Rect(pos, hitboxSize);
		this.dynamic = dynamic;
		this.pos = pos;
		this.accelerations = new ArrayList<Acceleration>();
		this.velocity = new Velocity(0, 0);
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public int hashCode() {
		return id;
	}
	
	public boolean equals(Object other) {
		return id == other.hashCode();
	}
	
	public void addAcceleration(Acceleration acceleration) {
		accelerations.add(acceleration);
	}
	
	public void step(float delta, List<Body> bodies) {
		if (dynamic == true) {
			Acceleration acceleration = Acceleration.sum(accelerations);
			velocity = acceleration.step(velocity, delta);
			for (Body body : bodies) {
				if(body.id != id && body.hitbox != null) {
					if(hitbox.intersectTop(body.hitbox) && velocity.y() < 0) {
						if(listener == null || listener.onCollideTop(body)) {
							velocity.setY(0);
						}
						break;
					} else if(hitbox.intersectBottom(body.hitbox) && velocity.y() > 0) {
						if(listener == null || listener.onCollideBottom(body)) {
							velocity.setY(0);
						}
						break;
					} else if(hitbox.intersectLeft(body.hitbox) && velocity.x() < 0) {
						if(listener == null || listener.onCollideLeft(body)) {
							velocity.setX(0);
						}
						break;
					} else if(hitbox.intersectRight(body.hitbox) && velocity.x() > 0) {
						if(listener == null || listener.onCollideRight(body)) {
							velocity.setX(0);
						}
						break;
					}
				}
			}
		}
		pos = velocity.step(pos, delta);
		if (hitbox != null) {
			hitbox.setX(pos.x());
			hitbox.setY(pos.y());
		}
	}
	
	public void setXVelocity(int xVelocity) {
		velocity.setX(xVelocity);
	}

	public void setYVelocity(int yVelocity) {
		velocity.setY(yVelocity);
	}

	public Point pos() {
		return pos.copy();
	}
	
	public Rect hitbox() {
		return hitbox;
	}

	public int id() {
		return id;
	}

	public Velocity velocity() {
		return velocity;
	}
}
