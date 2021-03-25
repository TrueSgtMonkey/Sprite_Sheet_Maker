package sprite;

/**
This program is ready to be made with a GUI.
All of the code is abstracted and able to work with GUI components
*/

public class EntryPoint
{
	public static void main(String args[])
	{
		//loading in the paths and getting the file chooser ready
		ImageGen gen = new ImageGen();
		
		//getting all of our sub-images from a file and putting it into one 
		//large one
		SpriteSheet sprite = gen.impImages();
		
		if(sprite != null)
			//exporting a mega image to a specific directory.
			gen.expImage(sprite);
	}
}