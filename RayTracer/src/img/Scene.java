package img;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import datatype.Sphere;
import light.Light;

public class Scene {
	
	private Camera camera;
	private String outputFile;
	private Color backgroundColor;
	private List<Sphere> spheres = new ArrayList<>();
	private List<Light> lights = new ArrayList<>();
	
	public Scene(String saveFile) {
		// default scene
		this.camera = new Camera();
		this.outputFile = saveFile;
		this.backgroundColor = new Color(255, 255, 255);
	}
	
	public Scene(Camera camera, String outputFile, Color backgroundColor, List<Sphere> sph, List<Light> lgt) {
		this.camera = camera;
		this.outputFile = outputFile;
		this.backgroundColor = backgroundColor;
		this.spheres = sph;
		this.lights = lgt;
	}

	public List<Light> getLights() {
		return lights;
	}

	public void addSphere(Sphere s) {
		this.spheres.add(s);
	}
	
	public Camera getCamera() {
		return camera;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public List<Sphere> getSpheres() {
		return spheres;
	}

	public void createBlackImage() {
		BufferedImage img = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		File f = new File(outputFile);
		
		try {
			ImageIO.write(img, "PNG", f);
			System.out.println("Successfully saved black image to " + outputFile);
		} catch (IOException e) {
			System.out.println("Picture saving to  " + outputFile + " failed, error: ");
			e.printStackTrace();
		}
	}
	
	public void saveImage(List<Color> imgPixels) {
		int height = camera.getHeight();
		int width = camera.getWidth();
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		File f = new File(outputFile);
				
		int pixelCounter = 0;
		for (int y  = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
                Color color = imgPixels.get(pixelCounter++);
                int rgb = color.getRGB();
                int newY = height - 1 - y ;
                img.setRGB(x, newY, rgb);
			}
		}
		
		try {
			ImageIO.write(img, "PNG", f);
			System.out.println("Successfully saved new image to " + outputFile);
		} catch (IOException e) {
			System.out.println("Error when saving picture: " + outputFile + ", error: ");
			e.printStackTrace();
		}
		
	}
			
}
