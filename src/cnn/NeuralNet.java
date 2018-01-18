package cnn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;


public class NeuralNet 
{
	public List<Dataset> trainingData = new ArrayList<Dataset>();
	public BufferedImage inputImage;
	
	
	public NeuralNet()
	{
		// Load input image
		try 
		{
			inputImage = ImageIO.read(new File("assets\\input_singledigit.jpg")); 
		}
		catch (IOException e) 
		{
			System.err.println(e.toString());
		}
		
		System.out.println("input img size: " + inputImage.getWidth() + "x" + inputImage.getHeight());
		
		
		int w = inputImage.getWidth();
		int h = inputImage.getHeight();

		int[] array = new int[w * h];
		array = inputImage.getRGB(0, 0, w, h, array, 0, w);
		
		int[] filterTop = new int[] {
			-1, -1, -1,
			0, 	0,	0,
			1,	1, 	1
		};
		
		int[] filterBottom = new int[] {
			 1,  1,  1,
			 0,  0,  0,
			-1,	-1, -1
		};
		
		int[] filterLeft = new int[] {
			 1,  0,  -1,
			 1,  0,  -1,
			 1,	 0,  -1
		};
		
		int[] filterRight = new int[] {
			 -1,  0,  1,
			 -1,  0,  1,
			 -1,  0,  1
		};
		
		int[] filterTopLeftCorner = new int[] {
			 1,  1,   0,
			 1,  0,  -1,
			 0,	 -1, -1
		};
		
		int[] filterBottomRightCorner = new int[] {
			-1, -1,  0,
			-1,  0,  1,
			 0,	 1,  1
		};
		
		int[] filterTopRightCorner = new int[] {
			 0,  1,  1,
			-1,  0,  1,
			-1,	-1,  0
		};
		
		int[] filterBottomLeftCorner = new int[] {
			0, -1,  -1,
			1,  0,  -1,
			1,	1,   0
		};
		
		int[] filtered = writeConvFile(w, h, array, filterTop, "filterTop.png");
		
		
		writeConvFile(w, h, array, filterBottom, "filterBottom.png");
		writeConvFile(w, h, array, filterLeft, "filterLeft.png");
		writeConvFile(w, h, array, filterRight, "filterRight.png");
		writeConvFile(w, h, array, filterTopLeftCorner, "filterTopLeftCorner.png");
		writeConvFile(w, h, array, filterBottomRightCorner, "filterBottomRightCorner.png");
		writeConvFile(w, h, array, filterTopRightCorner, "filterTopRightCorner.png");
		writeConvFile(w, h, array, filterBottomLeftCorner, "filterBottomLeftCorner.png");
	}
	
	public int[] writeConvFile(int w, int h, int[] array, int[] filter, String filename) 
	{
		int[] newArray = convolution(w, h, array, filter);
		
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		bufferedImage.getRaster().setPixels(0, 0, w, h, newArray);
		
		try 
		{
			ImageIO.write(bufferedImage, "png", new File(filename));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return newArray;
	}
	
	public int[] convolution(int w, int h, int[] img, int[] filter) 
	{
		int[] newImage = new int[w * h];
		Arrays.fill(newImage, 0);
		
		for(int y = 0; y < h-2; y++)
		{
			for(int x = 0; x < w-2; x++)
			{
				newImage = apply3x3Filter(x, y, w, img, filter, newImage);
			}
		}
		
		return newImage;
	}
	
	public int[] apply3x3Filter(int imgX, int imgY, int imgW, int[] img, int[] filter, int[] newImg) 
	{
		float sum = 0.0f;
		for(int filterY = 0; filterY < 3; filterY++)
		{
			for(int filterX = 0; filterX < 3; filterX++)
			{
				sum += img[((imgY + filterY) * imgW) + imgX + filterX] * filter[(filterY * 3) + filterX];
			}
		}
		
		sum /= (3 * 3);
		sum = relu(sum);
		
		newImg[((imgY + 1) * imgW) + imgX + 1] = Math.round(sum);
		return newImg;
	}
	
	public float relu(float value) 
	{
		return Math.max(0.0f, value);
	}
}













