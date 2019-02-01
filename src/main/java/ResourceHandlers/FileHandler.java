package ResourceHandlers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class FileHandler { //TODO: implement save file handling, level file handling, any others that are needed

    private static final Logger errorLogger = Logger.getLogger("errorLogger");

    //TODO: Temporary, make generic
    public static void writeLevel() {
        String[][] level = new String[10][10];
        for(String[] row : level) {
            Arrays.fill(row, "-");
        }
        level[5][5] = "R1";
        level[5][4] = "E0";
        level[5][3] = "PC0";
        level[5][6] = "E0";
        level[5][7] = "S1";

        try(Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("level1.txt"), "utf-8"))) {
            String line = "";
            for(String[] row : level) {
                for(String entry : row) {
                    line += entry + " ";
                }
                writer.write(line);
                ((BufferedWriter) writer).newLine();
                line = "";
            }
        } catch (Exception e) {
            errorLogger.log(Level.SEVERE,"Error writing level file", e);
        }

    }

    //TODO: throws exception?
    public static List<List<String>> readLevel(int level) {
        List<List<String>> levelMap = new ArrayList<>();
        List<String> file;

        try {
            file = Files.readAllLines(Paths.get("level"+level+".txt"));
            for(String line : file) {
                levelMap.add(Arrays.asList(line.split("\\s+")));
            }
            return levelMap;
        } catch (Exception e) {
            errorLogger.log(Level.SEVERE, "Error reading level file", e);
            return null;
        }
    }

}
