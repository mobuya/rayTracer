package light;

import datatype.Vector3;

public class ParallelLight extends Light {
	private Vector3 direction;
	private Vector3 color;
	
	public ParallelLight(Vector3 cColor, Vector3 cDirection) {
		super(cColor);
		this.color = cColor;
		this.direction = cDirection;
	}
	
	public Vector3 getColor() {
		return this.color;
	}
	public Vector3 getLightDirection() {
		return this.direction;
	}

}
