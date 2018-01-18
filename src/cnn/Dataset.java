package cnn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Dataset 
{
	public String label;
	public BufferedImage image;
	
	public Dataset(String label, String imgFile)
	{
		this.label = label;
		try 
		{
			image = ImageIO.read(new File(String.format("%s\\%s", "assets", imgFile)));
		} 
		catch(IOException e)  
		{
			System.err.println(e.toString());
		}
		
	}
}
