import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Writes the tour of a traveling salesman problem to a file including the
 * total tour distance.
 * @author Seth Dovgan
 * @version 18FEB18
 */
public class FileOutputProcessor {

    private PrintWriter writer;
    private String fileName;

    /**
     * Constructor. Sets the name of the file to write the tour to.
     * @param fileName to write the tour to
     */
    public FileOutputProcessor(String fileName){

        this.fileName = fileName;
    }

    /**
     * Sets up the input and output files. This method must be called before
     * any other functions are called in the class.
     */
    public void setup(){

        File outputFile;

        try { // Create the file writer

            // Output File
            outputFile = new File(fileName);
            writer = new PrintWriter(outputFile);

        // If any of the files are not found, print the error and exit
        } catch(FileNotFoundException e){

            System.out.println("File could not be found.");
            System.exit(99);
        }
    }

    /**
     * Close the open file reader and writer
     */
    public void teardown(){

        writer.close();
    }

    /**
     * Writes the given TSP tour to a file.
     * @param vertices to copy to the file.
     */
    public void writePathToFile(ArrayList<Vertex> vertices){

        System.out.println("\nWriting Results to File " + fileName + "...");
        for(Vertex vertex: vertices){
            writer.println(vertex.getIdentifier());
        }
        System.out.println("Writing Results to File Complete");
    }

    /**
     * Write the total TSP distance to the file.
     * @param distance to copy to the file.
     */
    public void writeDistanceToFile(long distance){
        writer.println(distance);
    }
}
