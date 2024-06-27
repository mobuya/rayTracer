package datatype;

public class Vector3 {
	
	//Custom vector class based on webGl vec3
	
	private double x;
	private double y;
	private double z;
	
	public Vector3() {
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
	}
	
	public Vector3(double cX, double cY, double cZ) {
		this.x = cX;
		this.y = cY;
		this.z = cZ;
	}
	
	public Vector3 add(Vector3 addedV) {
		double newX = this.getX() + addedV.getX();	
		double nexY = this.getY() + addedV.getY();
		double newZ = this.getZ() + addedV.getZ();
		
		return new Vector3(newX, nexY, newZ);
	}
	
	public Vector3 subtract(Vector3 subV) {
		double newX = this.getX() - subV.getX();	
		double nexY = this.getY() - subV.getY();
		double newZ = this.getZ() - subV.getZ();
		
		return new Vector3(newX, nexY, newZ);
	}
	
	public double length() {
		double result = 0;
		result = this.dotProduct(this);
		double returnResult = Math.sqrt(result);
		
		return returnResult;
	}
	
	public Vector3 normalize() {
		double vecLength = this.length();
		return new Vector3(this.x / vecLength, this.y / vecLength, this.z / vecLength);
	}
	
	
	public double dotProduct(Vector3 prodV) {
		double dotProduct = 0.0;
		dotProduct = (this.x * prodV.getX()) + (this.y * prodV.getY()) + (this.z * prodV.getZ());
		return dotProduct;
	}
	
	public Vector3 multiply(double m) {
		double mx = this.x * m;
		double my = this.y * m;
		double mz = this.z * m;
		
		return new Vector3(mx, my, mz);
	}
	
	 public Vector3 cross(Vector3 v) {
	        double newX = this.getY() * v.getZ() - this.getZ() * v.getY();
	        double newY=  this.getX() * v.getZ() - this.getZ() * v.getX();
	        double newZ = this.getX() * v.getY() - this.getY() * v.getX();
	        
	        return new Vector3(newX, newY, newZ);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	
}
