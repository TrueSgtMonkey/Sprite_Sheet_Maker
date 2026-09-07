package sprite;

import javax.swing.JFileChooser;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class MultiDirInfo {
    private ArrayList<String> paths;
    private int columns;
    private String slash;
    public final String COLUMN_STRING;
    public final String PATH_STRING;

    public MultiDirInfo() {
        columns = 0;
        paths = new ArrayList<String>();
        COLUMN_STRING = "column:";
        PATH_STRING   = "path:";
        slash = (System.getProperty("os.arch").toLowerCase().contains("windows")) ? "\\" : "/";
        System.out.println(slash);
    }

    public void getInfoFromFile(ImageGen imageGen) {
        System.out.println("Select a folder that has the input file...");
        int sel = imageGen.getJreader().showOpenDialog(null);
        if (sel == JFileChooser.APPROVE_OPTION) {
            imageGen.setReadPath(imageGen.getJreader().getSelectedFile().getAbsolutePath());
            String[] skipPaths = {};
            Vector<String> filenameVec = imageGen.getListDirectory(imageGen.getReadPath(), skipPaths);
            int choice;
            do {
                System.out.print("Choice: ");
                choice = Integer.valueOf(imageGen.getUserInput().nextLine());
            } while(choice >= filenameVec.size());

            try {

                String readPath = imageGen.getReadPath() + slash + filenameVec.elementAt(choice);
                BufferedReader reader = new BufferedReader(new FileReader(readPath));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(COLUMN_STRING)) {
                        columns = Integer.parseInt(line.substring(COLUMN_STRING.length()).trim());
                        System.out.println("columns: " + columns);
                    } else if (line.startsWith(PATH_STRING)) {
                        String currentPath = imageGen.getReadPath() + slash + line.substring(PATH_STRING.length()).trim();
                        System.out.println("path: " + currentPath);
                        paths.add(currentPath);
                    } else {
                        System.out.println("Cannot parse: " + line);
                    }
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Cannot find file: " + filenameVec.elementAt(choice));
                exception.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Nothing selected... Exiting.");
        }
    }

    public int getColumns() {
        return columns;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }
}
