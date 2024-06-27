package img;

import datatype.Vector3;

public class Camera {
	
	// Class is build based on XML file
	
	private Vector3 originPosition;
	private Vector3 lookAt;  
	private Vector3 upVector; 
	private int width;
	private int height;
	private double fov;
	private int maxBounces; 
	
	public Camera(Vector3 originPosition, Vector3 lookAt, Vector3 upVector, int width, int height, double fov,
			int maxBounces) {
		this.originPosition = originPosition;
		this.lookAt = lookAt;
		this.upVector = upVector;
		this.width = width;
		this.height = height;
		this.fov = fov;
		this.maxBounces = maxBounces;
	}
	
	public Camera() { 
		// default camera, used for my simple scene T2 
		this.originPosition = new Vector3(0.0, 0.0, 1.0);
		this.lookAt = new Vector3(0.0, 0.0, -1.0);
		this.upVector = new Vector3(0.0, 1.0, 0.0);
		this.width = 512;
		this.height = 512;
		this.fov = 45; 
		this.maxBounces = 100;	
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Ray getRayToPixel(int x, int y) {
		// normalize the coordinates
		double x_n = ((double)x + 0.5) / this.width;
		double y_n = ((double)y + 0.5) / this.height;
		
		double fov_x = this.fov * (Math.PI / 180);
		double fov_y = fov_x * ( (double) this.height / (double) this.width);
		
		double x_img = ((2 * x_n) - 1) * Math.tan(fov_x);
		double y_img = ((2 * y_n) - 1)* Math.tan(fov_y); 
		
		Vector3 direction = new Vector3( x_img, y_img, -1);
		Vector3 normalized = direction.normalize();
		
		return new Ray(this.originPosition, normalized);  
	}
	

}
