package light;

import datatype.Vector3;

public abstract class Light {
	private Vector3 color;
	
	public Light(Vector3 cColor) {
		this.color = cColor;
	}
	
	public void printcolor() {
		System.out.println("The color is: r(" + color.getX() + "), g(" + color.getY() + "), b(" + color.getZ() + ")");
	}
}
