package sprite;

import java.io.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class SpriteSheet
{
	//will contain the full list of pictures in the specified folder
	private ArrayList<BufferedImage> pics;
	private int width, height, gridWidth, gridHeight;
	private boolean doneWithWidth;
	private BufferedImage outputImage;
	
	public SpriteSheet(String folder)
	{
		File path = new File(folder);
		//setting up dimensions for the single image
		width = 0; height = 0; gridWidth = 0; gridHeight = 0;
		doneWithWidth = false;
		if(path.exists())
		{
			pics = new ArrayList<BufferedImage>();
			File[] files = path.listFiles();
			gridWidth = getImage(files[0]).getWidth();
			height = getImage(files[0]).getHeight();
			gridHeight = height;
			for(int i = 0; i < files.length; i++)
			{
				pics.add(getImage(files[i]));
				setupWidthHeight(i);
			}
			outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for(int i = 0; i < pics.size(); i++)
			{
				setupTiles(i);
			}
		}
		else
		{
			System.out.println("Directory: \"" + folder + "\" does not exist!");
			System.exit(1);
		}
	}
	
	public void setupTiles(int i)
	{
		// the top left pixel to start at
		int currX = gridWidth * (i % 8);
		int currY = gridHeight * (i / 8);
		
		outputImage.setData(pics.get(i).getData().createTranslatedChild(currX, currY));
	}
	
	public void saveImage(String filename)
	{
		try {
			// retrieve image
			File outputfile = new File(filename + ".png");
			ImageIO.write(outputImage, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(File file)
	{
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	
	public void setupWidthHeight(int i)
	{
		if(i % 8 == 0 && i > 0)
		{
			height += pics.get(i).getHeight();
			doneWithWidth = true;
		}
		else if(!doneWithWidth)
		{
			width += pics.get(i).getWidth();
		}
	}
	
	public ArrayList<BufferedImage> getPics() { return pics; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getGridWidth() { return gridWidth; }
	public int getGridHeight() { return gridHeight; }
	public boolean doneWithWidth() { return doneWithWidth; }
	public BufferedImage getImage() { return outputImage; }
}