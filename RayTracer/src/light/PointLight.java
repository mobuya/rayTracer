package light;

import datatype.Vector3;

public class PointLight extends Light {
	private Vector3 position;

	public PointLight(Vector3 cColor, Vector3 cPosition) {
		super(cColor);
		this.position = cPosition;
	}
	
	public Vector3 getPosition() {
		return position;
	}
}
