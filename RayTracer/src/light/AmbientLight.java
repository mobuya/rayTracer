package light;

import datatype.Vector3;

public class AmbientLight  extends Light{
	private Vector3 color;

	public AmbientLight(Vector3 cColor) {
		super(cColor);
		this.color = cColor;
	}
	
	public Vector3 getColor() {
		return this.color;
	}

}
