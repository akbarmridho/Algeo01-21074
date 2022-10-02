package main.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<String> doubleToArrayOfString(double[][] contents) {
        ArrayList<String> output = new ArrayList<>();

        for (double[] content : contents) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < contents[0].length; j++) {
                temp.append(String.format("%.3f", content[j]));
                if (j != contents[0].length - 1) {
                    temp.append(' ');
                }
            }
            output.add(temp.toString());
        }

        return output;
    }

    public static void writeFile(ArrayList<String> output, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        for (String str : output) {
            writer.write(str);
        }
        writer.close();
    }

}
