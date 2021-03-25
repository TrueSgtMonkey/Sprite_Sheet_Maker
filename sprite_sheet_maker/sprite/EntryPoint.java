package sprite;

import java.util.Scanner;

public class EntryPoint
{
	public static void main(String args[])
	{
		Scanner myObj = new Scanner(System.in); 
		
		System.out.print("Enter folder name (no need for a path): ");
		String folder = myObj.nextLine();
		
		SpriteSheet sprite = new SpriteSheet(folder);
		
		System.out.println("\nwidth: " + sprite.getWidth());
		System.out.println("height: " + sprite.getHeight());
		System.out.println("grid width: " + sprite.getGridWidth());
		System.out.println("grid height: " + sprite.getGridHeight());
		
		System.out.print("Enter output file name (no need for a path): ");
		String filename = myObj.nextLine();
		sprite.saveImage(filename);
	}
}