package sprite;

/**
Trying to abstract out the information from EntryPoint to be able to expand 
this project for the GUI.
*/

import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;

public class ImageGen
{
	private String readPath, savePath;
	private JFileChooser jReader, jSaver;
	
	//will contain all of our sprites once we can import multiple ones
	private ArrayList<SpriteSheet> spriteSheets;
	
	private Scanner myObj;
	
	//loading in the paths and getting the file chooser ready
	public ImageGen()
	{
		Scanner paths = null;
		try
		{
			paths = new Scanner(new File("save_paths.txt"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		readPath = paths.nextLine();
		savePath = paths.nextLine();
		
		File readFile = new File(readPath);
		File saveFile = new File(savePath);
		
		if(!readFile.exists())
		{
			readPath = "../";
			readFile = new File(readPath);
		}
		if(!saveFile.exists())
		{
			savePath = "../";
			saveFile = new File(savePath);
		}
		
		jReader = new JFileChooser(readFile);
		jSaver = new JFileChooser(saveFile);
		
		jReader.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		spriteSheets = new ArrayList<SpriteSheet>();
		
		myObj = new Scanner(System.in);
	}
	
	//getting all of our sub-images from a file and putting it into one 
	//large one
	public SpriteSheet impImages()
	{
		System.out.print("Enter how many images you want per row: ");
		int columns = Integer.valueOf(myObj.nextLine());
		//error checking
		while(columns <= 0)
		{
			System.out.print("Enter how many images you want per row (Must be greater than 0): ");
			columns = Integer.valueOf(myObj.nextLine());
		}
		
		int sel = jReader.showOpenDialog(null);
		
		if(sel == JFileChooser.APPROVE_OPTION)
		{
			readPath = jReader.getSelectedFile().getAbsolutePath();
			SpriteSheet sprite = new SpriteSheet(readPath, columns);
		
			System.out.println("\nwidth: " + sprite.getWidth());
			System.out.println("height: " + sprite.getHeight());
			System.out.println("grid width: " + sprite.getGridWidth());
			System.out.println("grid height: " + sprite.getGridHeight());
			
			spriteSheets.add(sprite);
			return sprite;
		}
		else
		{
			System.out.println("Nothing selected... Exiting.");
		}
		return null;
	}
	
	//exporting a mega image to a specific directory.
	public void expImage(int index)
	{
		//saving the complete sheet in a directory
		int saveSel = jSaver.showOpenDialog(null);
		
		if(saveSel == JFileChooser.APPROVE_OPTION)
		{
			System.out.print("Enter output file name (no need for a path): ");
			savePath = jSaver.getSelectedFile().getAbsolutePath();
			String filename = myObj.nextLine();
			spriteSheets.get(index).saveImage(savePath, filename);
			
			try 
			{
				FileWriter myWriter = new FileWriter("save_paths.txt");
				myWriter.write(readPath + "\n" + savePath);
				myWriter.close();
			} 
			catch (IOException e) 
			{
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Nothing selected... Exiting.");
		}
	}
	
	//exporting a mega image to a specific directory.
	public void expImage(SpriteSheet sprite)
	{
		//saving the complete sheet in a directory
		int saveSel = jSaver.showOpenDialog(null);
		
		if(saveSel == JFileChooser.APPROVE_OPTION)
		{
			System.out.print("Enter output file name (no need for a path): ");
			savePath = jSaver.getSelectedFile().getAbsolutePath();
			String filename = myObj.nextLine();
			sprite.saveImage(savePath, filename);
			
			try 
			{
				FileWriter myWriter = new FileWriter("save_paths.txt");
				myWriter.write(readPath + "\n" + savePath);
				myWriter.close();
			} 
			catch (IOException e) 
			{
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			
		}
		else
		{
			System.out.println("Nothing selected... Exiting.");
		}
	}
}