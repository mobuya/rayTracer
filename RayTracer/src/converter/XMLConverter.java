package converter;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import datatype.Sphere;
import datatype.Vector3;
import img.Camera;
import img.Scene;
import light.AmbientLight;
import light.Light;
import light.ParallelLight;

public class XMLConverter {

	private String filePath;
	
	public XMLConverter(String fP) {
		// this argument is given as program arguments
		this.filePath = fP;
	}
	
	public Scene createScene() {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			File xmlFile = new File(this.filePath);
			Document document = builder.parse(xmlFile);
			
			document.getDocumentElement().normalize(); 
			
			// * * * file name definition
			Element root = document.getDocumentElement();
			String fileName = root.getAttribute("output_file");
			
			// * * * background color definition
			Element xmlBackgroundColor = (Element) document.getElementsByTagName("background_color").item(0);
			Color backgroundColor = colorExtracter(xmlBackgroundColor);
			
			// * * * camera definition 
			Element xmlCamera = (Element) document.getElementsByTagName("camera").item(0);
			Camera sceneCamera = cameraExtracter(xmlCamera);
			
			// * * * spheres definition
			NodeList sphereList = document.getElementsByTagName("sphere");
			List<Sphere> sceneSpheres = spheresExtracter(sphereList);
			
			// * * * lights
			Element xmlLights = (Element) document.getElementsByTagName("lights").item(0);
			List<Light> sceneLights = lightsExtracter(xmlLights);			
			
			Scene newScene = new Scene(sceneCamera, fileName, backgroundColor, sceneSpheres, sceneLights);  
			System.out.println("Successfully created new scene! ('" + fileName + "')");
			return newScene;
			
		}catch(Exception e) {
			System.out.println("Error while reading XML file: " + e.toString());
		}
		
		return null;
	}
	
	private List<Light> lightsExtracter(Element xmlLights){
		List<Light> lightsList = new ArrayList<>();
		
		NodeList allLights = xmlLights.getChildNodes();
		
		for(int i = 0; i < allLights.getLength(); i++) {
			String nodeName = allLights.item(i).getNodeName();
			
			if(nodeName.equals("ambient_light")) {
				
				Element ambientL = (Element)allLights.item(i);
				Element xmlColor = (Element) ambientL.getElementsByTagName("color").item(0);
			
				double aRed = Double.parseDouble(xmlColor.getAttribute("r"));
				double aGreen = Double.parseDouble(xmlColor.getAttribute("g"));
				double aBlue = Double.parseDouble(xmlColor.getAttribute("b"));
				
				AmbientLight sceneAmbL = new AmbientLight(new Vector3(aRed, aGreen, aBlue));
				lightsList.add(sceneAmbL);
			}
			
			if(nodeName.equals("parallel_light")){
				Element parallelL = (Element)allLights.item(i);
				Element xmlColor = (Element) parallelL.getElementsByTagName("color").item(0);
				
				double pRed = Double.parseDouble(xmlColor.getAttribute("r"));
				double pGreen = Double.parseDouble(xmlColor.getAttribute("g"));
				double pBlue = Double.parseDouble(xmlColor.getAttribute("b"));
				
				Element xmlDirection = (Element) parallelL.getElementsByTagName("direction").item(0);
				double xd = Double.parseDouble(xmlDirection.getAttribute("x"));
				double yd = Double.parseDouble(xmlDirection.getAttribute("y"));
				double zd = Double.parseDouble(xmlDirection.getAttribute("z"));
				
				ParallelLight sceneParallelL = new ParallelLight(new Vector3(pRed, pGreen, pBlue), new Vector3(xd, yd, zd));
				lightsList.add(sceneParallelL);
			}
		}
		
		return lightsList;
	}
	
	private List<Sphere> spheresExtracter(NodeList xmlSphereList){
		
		// * * * * * Only parts relevant  for Lab 3A were imported. * * * * * 
		 
		List<Sphere> spheresList = new ArrayList<>();
		
		for(int index = 0; index < xmlSphereList.getLength(); index++) {
			Element xmlSphere = (Element) xmlSphereList.item(index);
			double radius = Double.parseDouble(xmlSphere.getAttribute("radius"));
			
			Element xmlPosition = (Element) xmlSphere.getElementsByTagName("position").item(0);
			double sphereX = Double.parseDouble(xmlPosition.getAttribute("x"));
			double sphereY = Double.parseDouble(xmlPosition.getAttribute("y"));
			double sphereZ = Double.parseDouble(xmlPosition.getAttribute("z"));
			Vector3 position = new Vector3(sphereX, sphereY, sphereZ);
			
			Element xmlMaterialSolid = (Element) xmlSphere.getElementsByTagName("material_solid").item(0);
			Element xmlColor = (Element) xmlMaterialSolid.getElementsByTagName("color").item(0);
			Color color = colorExtracter(xmlColor);
			 
			Element xmlPhong = (Element) xmlMaterialSolid.getElementsByTagName("phong").item(0);
			double phongAmbient = Double.parseDouble(xmlPhong.getAttribute("ka"));
			double phongDiffuse = Double.parseDouble(xmlPhong.getAttribute("kd"));
			double phongSpecular = Double.parseDouble(xmlPhong.getAttribute("ks"));
			
			int exponent = Integer.parseInt(xmlPhong.getAttribute("exponent"));
			
			Vector3 phongValues = new Vector3(phongAmbient, phongDiffuse, phongSpecular);
			
			Sphere sphere = new Sphere(position, radius, color, phongValues, exponent);
			spheresList.add(sphere);
			
			// * * * * * reflectance, transmittance and refraction are not imported since it only 3B relevant	
		}
		
		return spheresList;
		
	}
	
	private Color colorExtracter(Element XMLColor) {
		double bRed = Double.parseDouble(XMLColor.getAttribute("r"));
		double bGreen = Double.parseDouble(XMLColor.getAttribute("g"));
		double bBlue = Double.parseDouble(XMLColor.getAttribute("b"));
		
		int finalRed = (int) Math.round(255*bRed);   
		int finalGreen = (int) Math.round(255*bGreen);  
		int finalBlue = (int) Math.round(255*bBlue); 
		
		return new Color(finalRed, finalGreen, finalBlue);
	}
	
	private Camera cameraExtracter(Element XMLCamera) {
		Element cameraPosition = (Element) XMLCamera.getElementsByTagName("position").item(0);
		double cameraX = Double.parseDouble(cameraPosition.getAttribute("x"));
		double cameraY = Double.parseDouble(cameraPosition.getAttribute("y"));
		double cameraZ = Double.parseDouble(cameraPosition.getAttribute("z"));
		
		Vector3 position = new Vector3(cameraX, cameraY, cameraZ);

		Element cameraLookAt = (Element) XMLCamera.getElementsByTagName("lookat").item(0);
		double cameraLX = Double.parseDouble(cameraLookAt.getAttribute("x"));
		double cameraLY = Double.parseDouble(cameraLookAt.getAttribute("y"));
		double cameraLZ = Double.parseDouble(cameraLookAt.getAttribute("z"));
		
		Vector3 lookAt = new Vector3(cameraLX, cameraLY, cameraLZ);
		
		Element cameraUp = (Element) XMLCamera.getElementsByTagName("up").item(0);
		double cameraUX = Double.parseDouble(cameraUp.getAttribute("x"));
		double cameraUY = Double.parseDouble(cameraUp.getAttribute("y"));
		double cameraUZ = Double.parseDouble(cameraUp.getAttribute("z"));
		
		Vector3 up = new Vector3(cameraUX, cameraUY, cameraUZ);
		
		Element cameraHorFov = (Element) XMLCamera.getElementsByTagName("horizontal_fov").item(0);
		double horiontal_fov = Double.parseDouble(cameraHorFov.getAttribute("angle"));
		
		Element cameraResolution = (Element) XMLCamera.getElementsByTagName("resolution").item(0);
		int width = Integer.parseInt(cameraResolution.getAttribute("horizontal"));
		int height = Integer.parseInt(cameraResolution.getAttribute("vertical"));

		Element cameraMaxBounces = (Element) XMLCamera.getElementsByTagName("max_bounces").item(0); 
		int maxBounces = Integer.parseInt(cameraMaxBounces.getAttribute("n"));
		
		Camera sceneCamera = new Camera(position, lookAt, up, width, height, horiontal_fov, maxBounces);
						
		return sceneCamera; 
	}
}
