package sprite;

import java.util.Scanner;

/**
This program is ready to be made with a GUI.
All of the code is abstracted and able to work with GUI components
*/

public class EntryPoint {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        EntryPoint entryPoint = new EntryPoint();

        System.out.print(
            "Choose the type of Sprite Sheet export to run:\n" +
            "  1) One Directory Export\n" +
            "  2) Multi-directory Export using JSON\n" +
            "  0) Exit\n" +
            "Choice: "
        );
        int choice = scanner.nextInt();

        //loading in the paths and getting the file chooser ready
        ImageGen gen = new ImageGen();
        switch (choice) {
            case 1:
                entryPoint.generateImageSingleDirectory(gen);
                break;

            case 2:
                entryPoint.generateImageMultiDirectoryJson(gen);
                break;

            default:
                System.out.println("Exiting...");
                break;
        }
    }

    /**
     * Import one directory of the user's choosing into a sprite sheet
     * @param imageGen
     */
    public void generateImageSingleDirectory(ImageGen imageGen) {
        //getting all of our sub-images from a file and putting it into one 
        //large one
        SpriteSheet sprite = imageGen.impImages();
        
        if (sprite != null) {
            sprite.createOutputImage();

            //exporting a mega image to a specific directory.
            imageGen.expImage(sprite);
        }
    }

    /**
     * Import multiple directories into a single sprite sheet from an input
       file within the directory that the user chooses.

     * The input file will contain the paths that we want to import.
     * @param imageGen
    **/
    public void generateImageMultiDirectoryJson(ImageGen imageGen) {
        MultiDirInfo multiDirInfo = new MultiDirInfo();
        multiDirInfo.getInfoFromFile(imageGen);

        // need to get columns from JSON file to pass to this
        SpriteSheet sprite = new SpriteSheet(multiDirInfo.getColumns());
        for (int index = 0; index < multiDirInfo.getPaths().size(); index++) {
            sprite.addToSpriteSheet(multiDirInfo.getPaths().get(index));
        }
        sprite.createOutputImage();
        imageGen.expImage(sprite);
    }
}
