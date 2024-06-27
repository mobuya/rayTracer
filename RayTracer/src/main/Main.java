package main;

import java.awt.Color;

import converter.XMLConverter;
import datatype.Sphere;
import datatype.Vector3;
import img.RayTracer;
import img.Scene;

public class Main {

	public static void main(String[] args) {
		
		// TASK 1: create black image
		Scene firstScene = new Scene("blackImage.png");
		firstScene.createBlackImage();
		
		// TASK 2: output image with spheres 
		
		Scene twoSpheres = new Scene("twoSpheres.png");
		RayTracer tracer2 = new RayTracer();
		
		Sphere newSphere1 = new Sphere(new Vector3(0.8, 0.8, -3), 1, new Color(255, 0, 0));
		twoSpheres.addSphere(newSphere1);
		
		Sphere newSphere2 = new Sphere(new Vector3(-0.8, -0.8, -3), 1, new Color(0, 0, 255));
		twoSpheres.addSphere(newSphere2);
		
		
		tracer2.renderScene(twoSpheres);
		
		// TASK 3 - 5 : please input file path as * run arguments*   ! ! ! ! 
		// file path as C:\\Users\\***\\.....\\scenes\\example3.xml
		
		// You can add all scene paths, or just one by one :)
		
		for(int xmlIndx = 0; xmlIndx < args.length; xmlIndx++) {
			XMLConverter newConvXML = new XMLConverter(args[xmlIndx]); 
			Scene sceneXML = newConvXML.createScene();
			RayTracer tracerXML = new RayTracer();
			tracerXML.renderScene(sceneXML);
		}
	
		
	}
}
