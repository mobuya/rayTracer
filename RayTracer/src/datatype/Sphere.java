package datatype;

import java.awt.Color;

import img.Intersection;
import img.Ray;

public class Sphere {
	
	// Class is build based on XML file
	
	private Vector3 centerPoint;
	private double radius;
	private Color color;
	private double phongAmbient;
	private double phongDiffuse;
	private double phongSpecular;
	private int exponent;
	
	public Sphere(Vector3 cPoint, double r, Color c, Vector3 phongValues, int exp) {
		this.centerPoint = cPoint;
		this.radius = r;
		this.color = c;
		this.phongAmbient = phongValues.getX();
		this.phongDiffuse = phongValues.getY();
		this.phongSpecular = phongValues.getZ();
		this.exponent = exp;
	}
	
	public Sphere(Vector3 cPoint, double r, Color c) {
		// Constructor for my custom simple scene
		this.centerPoint = cPoint;
		this.radius = r;
		this.color = c;
	}

	public double getPhongAmbient() {
		return phongAmbient;
	}

	public double getPhongDiffuse() {
		return phongDiffuse;
	}

	public double getPhongSpecular() {
		return phongSpecular;
	}

	public int getExponent() {
		return exponent;
	}

	public Vector3 getPosition() {
		return centerPoint;
	}

	public double getRadius() {
		return radius;
	}
	
	public Color getcolor() {
		return this.color;
	}
	
	public Vector3 getSurfaceNormalToPoint(Vector3 intersectionPoint) {
		Vector3 returnVector = intersectionPoint.subtract(centerPoint).normalize();
		
		return returnVector;
	}
	
	public Intersection intersectionTest(Ray ray) {	
		boolean hasIntersection = false;
		Vector3 intersectionPoint = new Vector3();
		
		Vector3 d = ray.getDirection();  
		Vector3 o = ray.getOrigin(); 
		
		Vector3 oMinusC = o.subtract(this.centerPoint);
		
		double a = d.dotProduct(d);
		Vector3 twoD = d.multiply(2);
		double b = twoD.dotProduct(oMinusC);
		double c = oMinusC.dotProduct(oMinusC) - Math.pow(getRadius(), 2);
		
		double discriminant =  Math.pow(b, 2) - 4* (a * c);
			
		Intersection rayIntersection = new Intersection(hasIntersection, intersectionPoint);	
		
		if(discriminant < 0 ) {
			return rayIntersection;
		}else {

			double discriminantSqr = Math.sqrt(discriminant);
			double t1 = (-b + discriminantSqr) / (2 * a);
		    double t2 = (-b - discriminantSqr) / (2 * a);
		    double tValue;
		    
		    if(t1 > 0 && t2 >0) {
		    	tValue = Math.min(t1, t2);
			    hasIntersection = true;
		    }else if(t1 > 0) {
		    	tValue=t1;
		    }else if(t2 > 0) {
		    	tValue = t2; 
		    }else {
		    	tValue = 0;
		    }
		    
			intersectionPoint = o.add(d.multiply(tValue));
			return new Intersection(hasIntersection, intersectionPoint);
		}
	}
	
}
