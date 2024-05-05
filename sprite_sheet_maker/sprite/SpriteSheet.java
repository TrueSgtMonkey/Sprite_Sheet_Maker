package sprite;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class SpriteSheet
{
	//will contain the full list of pictures in the specified folder
	private ArrayList<BufferedImage> pics;
	private int width, height, gridWidth, gridHeight, columns;
	private boolean doneWithWidth;
	private BufferedImage outputImage;
	
	public SpriteSheet(String folder, int columns)
	{
		File path = new File(folder);
		//setting up dimensions for the single image
		width = 0; height = 0; gridWidth = 0; gridHeight = 0;
		this.columns = columns;
		doneWithWidth = false;
		if(path.exists())
		{
			pics = new ArrayList<BufferedImage>();
			File[] files = path.listFiles();
            Arrays.sort(files);
            if(files != null)
            {
    			for(int i = 0, j = 0; i < files.length; i++)
    			{
    				if(files[i].getName().endsWith(".png") || files[i].getName().endsWith(".jpg"))
    				{
						BufferedImage image = getImage(files[i]);

                        //the first image we come across, we will set all the grid and height
                        if(gridWidth == 0 && height == 0 && gridHeight == 0)
                        {
                			gridWidth = image.getWidth();
                			height = image.getHeight();
                			gridHeight = height;
                        }
    					pics.add(image);
                        //making sure the i is in the range of the array list
    					setupWidthHeight(j);
                        j++;
    				}
    			}
    			outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    			for(int i = 0; i < pics.size(); i++)
    			{
    				System.out.print("\r[");
    				setupTiles(i);
    				System.out.print((i+1) + "/" + pics.size() + "]");
    			}
    			System.out.println();
            }
			else
            {
                System.out.println("Cannot find " + folder);
            }
		}
		else
		{
			System.out.println("Directory: \"" + folder + "\" does not exist!");
			System.exit(1);
		}
	}
	
	public void setupWidthHeight(int i)
	{
        if(pics.get(i) == null)
        {
            System.out.println("Null at index: " + i);
            System.exit(1);
        }
		//new row
		if(i % columns == 0 && i > 0)
		{
			height += pics.get(i).getHeight();
			doneWithWidth = true;
		}
		else if(!doneWithWidth)
		{
			width += pics.get(i).getWidth();
		}
	}
	
	public void setupTiles(int i)
	{
		// the top left pixel to start at
		int currX = gridWidth * (i % columns);
		int currY = gridHeight * (i / columns);
		
		outputImage.setData(pics.get(i).getData().createTranslatedChild(currX, currY));
	}
	
	public void saveImage(String path, String filename)
	{
		try {
			// retrieve image
			File outputfile = new File(path + "/" + filename);
			System.out.print("Writing \"" + outputfile.getName() + "\" to file...");
			ImageIO.write(outputImage, "png", outputfile);
			System.out.println(" done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(File file)
	{
		BufferedImage image = null;
		try 
		{
			image = ImageIO.read(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return image;
	}
	
	public ArrayList<BufferedImage> getPics() { return pics; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getGridWidth() { return gridWidth; }
	public int getGridHeight() { return gridHeight; }
	public boolean doneWithWidth() { return doneWithWidth; }
	public BufferedImage getImage() { return outputImage; }
}