import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads in values from a TSP location file, with a specific format, and builds
 * an array list of values to import into the graph. File must be in the
 * following format:
 * Each line has 3 integers; city identifier, x-coord, y-coord.
 * A complete example is 0 200 800
 * @author Seth Dovgan
 * @version 18FEB18
 */
public class FileInputProcessor {

    private Scanner scanner;
    private String fileName;
    private File file;

    /**
     * Constructor. Sets the file name to read in and creates a new file
     * @param fileName of the input file to read from
     */
    public FileInputProcessor(String fileName){
        this.fileName = fileName;
        file = new File(fileName);
    }

    /**
     * Checks if the given fileName is valid.
     * @return true if the fileName is valid and false otherwise.
     */
    public boolean isValidFile(){
        return file.isFile();
    }

    public String getFileName(){
        return fileName;
    }

    /**
     * Initializes the input file reader. Returns true if the file was
     * successfully initiated and false otherwise. This method must be called
     * before any other functions are called in the class.
     * @return true if setup was successful and false otherwise.
     */
    public boolean setup(){

        try { // Create the file reader

            // Input File scanner
            scanner = new Scanner(file);
            return true;

        // If the file is not found, print the error
        } catch(FileNotFoundException e){

            System.out.println("File " + fileName + " could not be found.");
            return false;
        }
    }

    /**
     * Closes the open file reader. This method must be called after all
     * file processing is completed.
     */
    public void teardown(){
        scanner.close();
    }

    /**
     * Processes the file and returns the all the vertices from the file in
     * an ArrayList or vertices.
     * @return as vertices derived from the file.
     */
    public ArrayList<Vertex> getVerticesFromFile(){

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();

        // Check if file still has data to process and process next line
        while(scanner.hasNextLine()){

            String[] data = scanner.nextLine().trim().split("\\s+");

            // Check if all the elements exist in the line
            if(data.length == 3){

                int identifier = Integer.parseInt(data[0]);
                int x = Integer.parseInt(data[1]);
                int y = Integer.parseInt(data[2]);

                vertices.add(new Vertex(identifier, new Point(x, y)));

            } else { // Print an error message and disregard line

                System.err.print("ERROR IN FILE < Missing Elements in Line > ");

                for(String string: data){
                    System.err.print(string + " ");
                }
                System.err.print("\n");
            }
        }

        return vertices;
    }
}

