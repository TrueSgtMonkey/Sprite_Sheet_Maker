package sprite;

import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class EntryPoint
{
	public static void main(String args[])
	{
		String readPath, savePath;
		
		Scanner myObj = new Scanner(System.in);
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
		
		//getting the file chooser set up
		JFileChooser j = new JFileChooser(new File(readPath));
		JFileChooser jSaver = new JFileChooser(new File(savePath));
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int sel = j.showOpenDialog(null);
		
		if(sel == JFileChooser.APPROVE_OPTION)
		{
			readPath = j.getSelectedFile().getAbsolutePath();
			readPath = readPath;
			SpriteSheet sprite = new SpriteSheet(readPath);
		
			System.out.println("\nwidth: " + sprite.getWidth());
			System.out.println("height: " + sprite.getHeight());
			System.out.println("grid width: " + sprite.getGridWidth());
			System.out.println("grid height: " + sprite.getGridHeight());
			
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
		}
		
		
	}
}