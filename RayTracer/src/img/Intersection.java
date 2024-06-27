package img;

import datatype.Vector3;

public class Intersection {
	private boolean present;
	private Vector3 intersectionPoint;
	
	public Intersection(boolean p, Vector3 iP) {
		this.present = p;
		this.intersectionPoint = iP;
	}

	public boolean isPresent() {
		return present;
	}

	public Vector3 getIntersectionPoint() {
		return intersectionPoint;
	}
	
	
	
}
