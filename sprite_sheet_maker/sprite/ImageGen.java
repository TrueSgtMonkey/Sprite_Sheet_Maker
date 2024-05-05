package sprite;

/**
Trying to abstract out the information from EntryPoint to be able to expand 
this project for the GUI.
*/

import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;

public class ImageGen
{
    private String readPath, savePath;
    private JFileChooser jReader, jSaver;
    private Boolean isMacOS;
    
    //will contain all of our sprites once we can import multiple ones
    private ArrayList<SpriteSheet> spriteSheets;
    
    private Scanner userInput;
    private String  userPath;
    private String  userPathMac;
    
    //loading in the paths and getting the file chooser ready
    public ImageGen()
    {
        String osName = System.getProperty("os.name").toLowerCase();
        isMacOS = osName.startsWith("mac os x");

        // Creating user file if it doesn't exist.
        userPath = "save_paths.txt";
        userPathMac = "mac_save_paths.txt";
        File userFile = null;
        File userFileMac = null;
        String userFileContents = "C:\\\nC:\\";
        String userFileContentsMac = "/\n/";

        if(isMacOS)
            userFileMac = createUserFile(userPathMac, userFileContentsMac);
        else
            userFile = createUserFile(userPath, userFileContents);
        
        // Reading from the user file (whether just created or not)
        Scanner paths = null;
        try
        {
            if(isMacOS)
                paths = new Scanner(userFileMac);
            else
                paths = new Scanner(userFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
        // Getting strings from user file and then opening up the directories
        // based on strings
        readPath = paths.nextLine();
        savePath = paths.nextLine();
        File readFile = new File(readPath);
        File saveFile = new File(savePath);
        
        // checking if we are one directory too far if on Mac - otherwise returning file
        readFile = checkMacDirectory(readFile);
        saveFile = checkMacDirectory(saveFile);

        // returning root if directory invalid
        readFile = checkInvalidDirectory(readFile);
        saveFile = checkInvalidDirectory(saveFile);
        
        jReader = new JFileChooser(readFile);
        jSaver = new JFileChooser(saveFile);
        
        jReader.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jSaver.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        spriteSheets = new ArrayList<SpriteSheet>();
        
        userInput = new Scanner(System.in);
    }
    
    //getting all of our sub-images from a file and putting it into one 
    //large one
    public SpriteSheet impImages()
    {
        System.out.print("Enter how many images you want per row: ");
        int columns = Integer.valueOf(userInput.nextLine());
        //error checking
        while(columns <= 0)
        {
            System.out.print("Enter how many images you want per row (Must be greater than 0): ");
            columns = Integer.valueOf(userInput.nextLine());
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
            String filename = userInput.nextLine();
            spriteSheets.get(index).saveImage(savePath, filename);
            readPath = editPath(readPath);
            savePath = editPath(savePath);
            
            try 
            {
                FileWriter myWriter; 
                if(isMacOS)
                    myWriter = new FileWriter("mac_save_paths.txt");
                else
                    myWriter = new FileWriter("save_paths.txt");
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
            String filename = chooseFromOutputDirectory();
            sprite.saveImage(savePath, filename);
            if(isMacOS)
            {
                readPath = editPath(readPath);
                savePath = editPath(savePath);
                if(readPath.equals("") || savePath.equals(""))
                {
                    System.out.println("Could not find readPath or savePath...");
                    return;
                }
            }
            
            try 
            {
                FileWriter myWriter; 
                if(isMacOS)
                    myWriter = new FileWriter("mac_save_paths.txt");
                else
                    myWriter = new FileWriter("save_paths.txt");
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

    private String chooseFromOutputDirectory()
    {
        int index = 0;
        savePath = jSaver.getSelectedFile().getAbsolutePath();
        File file = new File(savePath);

        Vector<String> filenameVec = new Vector<String>();
        setupVectorFile(filenameVec, file);

        for (String filename : filenameVec)
        {
            System.out.println(index + ") " + filename);
            index += 1;
        }
        System.out.println(index + ") Create new file");
        System.out.print("Choice: ");
        int choice = Integer.valueOf(userInput.nextLine());

        // Either outside the list's range or our range of choices
        // Whatever the case, creating a new file is safer
        if (choice >= filenameVec.size())
        {
            System.out.print("Enter output file name (no need for a path): ");
            String filenameToReturn = userInput.nextLine();
            if (!filenameToReturn.endsWith(".png") && !filenameToReturn.endsWith(".jpg"))
                filenameToReturn += ".png";

            return filenameToReturn;
        }

        return filenameVec.get(choice);
    }

    private void setupVectorFile(Vector<String> filenameVec, File file)
    {
        File[] fileList = file.listFiles();
        if (fileList == null)
        {
            System.out.println("Invalid directory: " + savePath);
        }

        for(File dirFile : fileList)
        {
            if (!dirFile.getName().endsWith(".png") && !dirFile.getName().endsWith(".jpg"))
                continue;

            filenameVec.addElement(dirFile.getName());
        }
    }
  
    private String editPath(String s)
    {
        while(s.length() > 0 && s.charAt(s.length() - 1) != '/')
        {
            s = s.substring(0, s.length() - 1);
        }
        if(s.length() > 0)
        {
            s = s.substring(0, s.length() - 1);
        }
        else
        {
            s = "";
            System.out.println("Could not find / in path!");
        }
    
        return s;
    }

    private File createUserFile(String path, String contents)
    {
        File file = new File(path);

        try
        {
            // file already exists - just return file at path with saved contents
            if (!file.createNewFile())
                return file;

            // start the file out with a read path and a save path
            FileWriter writer = new FileWriter(path);
            writer.write(contents);
            writer.close();
        } catch (IOException e)
        {
            System.out.println("Cannot create new file: " + path);
            e.printStackTrace();
        }

        return file;
    }

    private File checkMacDirectory(File file)
    {
        // go back one directory if we went too far
        if(file.exists())
            return file;

        if(isMacOS)
            readPath = "../";
        file = new File(readPath);

        return file;
    }

    private File checkInvalidDirectory(File file)
    {
        // file is valid
        if(file.exists())
            return file;

        // file is invalid - start at the root directory
        if(isMacOS)
            file = new File("/");
        else
            file = new File("C:\\");

        return file;
    }
}