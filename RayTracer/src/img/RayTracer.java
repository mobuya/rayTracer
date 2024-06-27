package img;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import datatype.Sphere;
import datatype.Vector3;
import light.AmbientLight;
import light.Light;
import light.ParallelLight;

public class RayTracer {
	
	public RayTracer() {}
	
	public void renderScene(Scene scene) {
		Camera sceneCamera = scene.getCamera();

		int imageHeight = sceneCamera.getHeight();
		int imageWidth = sceneCamera.getWidth();
		
		ArrayList<Color> imgPixels = new ArrayList<>();
		
		for(int y = 0; y < imageHeight; ++y) {
			for(int x = 0; x < imageWidth; ++x) {
				Ray ray = sceneCamera.getRayToPixel(x, y); 
				Color color = trace(ray, scene);  
				imgPixels.add(color);
			}
		}
		
		scene.saveImage(imgPixels);
	}
	
	public Color trace(Ray ray, Scene scene) {
		List<Sphere> sceneObjects = scene.getSpheres();
		for(Sphere object : sceneObjects) {
			Intersection intersection = object.intersectionTest(ray);
			
			if(intersection.isPresent()) {
				
				if(scene.getLights().size() == 0) {
					return object.getcolor();   // case for my custom scene T2
				}
				
				Color objectColor = object.getcolor();
				Vector3 intersectionPoint = intersection.getIntersectionPoint();
				
				Vector3 ambientColor = new Vector3();
				Vector3 diffuseColor = new Vector3();
				Vector3 specularColor = new Vector3(); 
			
				
				for(Light light : scene.getLights()) {
					
					if(light instanceof AmbientLight) {
						
						double ambientK = object.getPhongAmbient();
						Vector3 lightColor = ((AmbientLight) light).getColor();
						Vector3 newAmb = lightColor.multiply(ambientK);
						ambientColor = newAmb;
					}
					
					if(light instanceof ParallelLight) {
						
						Vector3 shadowRayDirection = ((ParallelLight) light).getLightDirection().multiply(-1);
						Ray shadowRay = new Ray(intersectionPoint, shadowRayDirection.normalize());
						boolean inShadow = false;
						
						for(Sphere otherObject : sceneObjects) {
							
								Intersection shadowIntersection = otherObject.intersectionTest(shadowRay);
								
								if(shadowIntersection.isPresent()) {
									double returnRed = object.getPhongAmbient();
									double returnGreen = object.getPhongAmbient();
									double returnBlue =object.getPhongAmbient();
									ambientColor = new Vector3(returnRed, returnGreen, returnBlue);
									
									inShadow = true;
									break;
								}
						}
						
						if(!inShadow) {
						Vector3 normalAtIntersection = object.getSurfaceNormalToPoint(intersectionPoint);
						
						double diffuseK = object.getPhongDiffuse();
						double specularK = object.getPhongSpecular(); 
						Vector3 lightColorD = ((ParallelLight) light).getColor();
						Vector3 lightDirection = ((ParallelLight) light).getLightDirection().multiply(-1);   
						
						Vector3 lVec = lightDirection.normalize();
						// DIFFUSE
						double NL = lVec.dotProduct(normalAtIntersection);
						double diffuse_factor = Math.max(0.0, NL);
						
						Vector3 newDiff = lightColorD.multiply(diffuse_factor * diffuseK);
						diffuseColor = newDiff;
						
						// SPECULAR
						Vector3 reflection = normalAtIntersection.multiply(2 * NL).subtract(lVec);  
						Vector3 viewVector = ray.getDirection().multiply(-1).normalize();
						
						double specular_factor = Math.max(viewVector.dotProduct(reflection), 0.0);
						int exp = object.getExponent();
						specular_factor = Math.pow(specular_factor, exp);
						
						Vector3 newSpec = lightColorD.multiply(specularK * specular_factor);
						specularColor = newSpec;
						}
					}
				}
				
				double resultRed = (ambientColor.getX() + diffuseColor.getX()) * (objectColor.getRed() / 255.0)  + specularColor.getX() ;
                double resultGreen = (ambientColor.getY() + diffuseColor.getY()) * (objectColor.getGreen() / 255.0) + specularColor.getY();
                double resultBlue = (ambientColor.getZ() + diffuseColor.getZ()) * (objectColor.getBlue() / 255.0) + specularColor.getZ();

                resultRed = Math.min(255, Math.max(0, resultRed * 255));
                resultGreen = Math.min(255, Math.max(0, resultGreen * 255));
                resultBlue = Math.min(255, Math.max(0, resultBlue * 255));
								
				//System.out.println("The colors are: r( " + resultRed + " ), g( " +resultGreen + " ), ( " + resultBlue + " )");
				
				Color returnColor = new Color((int) resultRed, (int) resultGreen,(int) resultBlue);
				return returnColor;
			}
		}
		return scene.getBackgroundColor();
	}
}
