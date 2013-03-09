package com.twoseasgames.rivet.dynamics;

import java.util.ArrayList;
import java.util.List;

import com.twoseasgames.rivet.common.Acceleration;
import com.twoseasgames.rivet.common.Point;
import com.twoseasgames.rivet.common.Rect;
import com.twoseasgames.rivet.common.Velocity;

public class Body {

	public static final Object NO_DATA = new Object();

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
	private Rect hitboxSpecs;
	private Object userData = NO_DATA;
	private boolean enable = true;
	
	public Body(Point pos) {
		id = count;
		count++;
		this.pos = pos;
		this.accelerations = new ArrayList<Acceleration>();
		this.velocity = new Velocity(0, 0);
	}
	
	public Body(Point pos, Rect hitbox, boolean dynamic) {
		id = count;
		count++;
		hitboxSpecs = hitbox;
		this.hitbox = new Rect(
			pos.x() + hitboxSpecs.x(),
			pos.y() + hitboxSpecs.y(),
			hitboxSpecs.width(),
			hitboxSpecs.height()
		);
		this.dynamic = dynamic;
		this.pos = pos;
		this.accelerations = new ArrayList<Acceleration>();
		this.velocity = new Velocity(0, 0);
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public void disable() {
		this.enable = false;
	}
	
	public void setUserData(Object userData) {
		this.userData = userData;
	}

	public Object userData() {
		return userData;
	}
	
	public void setPos(Point pos) {
		this.pos = pos;
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
		}
		Point nextPos  = velocity.step(pos, delta);
		Rect nextHitbox = hitbox.copy();
		nextHitbox.setX(nextPos.x() + hitboxSpecs.x());
		nextHitbox.setY(nextPos.y() + hitboxSpecs.y());
		if (velocity.x() != 0 || velocity.y() != 0) {
			for (Body body : bodies) {
				if(body.id != id && body.hitbox != null 
						&& Math.abs(nextHitbox.x() - body.hitbox.x()) <= nextHitbox.width() + body.hitbox.width()
						&& Math.abs(nextHitbox.y() - body.hitbox.y()) <= nextHitbox.height() + body.hitbox.height()) {
					if(velocity.y() < 0 && nextHitbox.intersectTop(body.hitbox)) {
						if(listener == null || listener.onCollideTop(body)) {
							velocity.setY(0);
							pos.setY(body.hitbox.y() + body.hitbox.height());
						}
					} else if(velocity.y() > 0 && nextHitbox.intersectBottom(body.hitbox)) {
						if(listener == null || listener.onCollideBottom(body)) {
							velocity.setY(0);
							pos.setY(body.hitbox.y() - hitbox.height() / 2);
						}
					} else if(velocity.x() < 0 && nextHitbox.intersectLeft(body.hitbox)) {
						if(listener == null || listener.onCollideLeft(body)) {
							velocity.setX(0);
							pos.setX(body.hitbox.x() + body.hitbox.width());
						}
					} else if(velocity.x() > 0 && nextHitbox.intersectRight(body.hitbox)) {
						if(listener == null || listener.onCollideRight(body)) {
							velocity.setX(0);
							pos.setX(body.hitbox.x() - hitbox.width() / 2);
						}
					}
				}
			}
		}
		pos = velocity.step(pos, delta);
		if (hitbox != null) {
			hitbox.setX(pos.x() + hitboxSpecs.x());
			hitbox.setY(pos.y() + hitboxSpecs.y());
		}
	}

	public void setHitbox(Rect hitbox) {
		hitboxSpecs = hitbox;
		this.hitbox.setWidth(hitbox.width());
		this.hitbox.setHeight(hitbox.height());
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
