package main.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Parser {

    public static ArrayList<String> readFile(String path) {
        ArrayList<String> output = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> output.add(s + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static double[][] listOfStringToDouble(ArrayList<String> contents) {
        int row = contents.size();
        int col = 0;
        //
        for (int j = 0; j < contents.get(0).length(); j++) {
            if (contents.get(0).charAt(j) == ' ') {
                col += 1;
            }
            if (j == contents.get(0).length() - 1) {
                col += 1;
            }
        }
        System.out.println(col);

        double[][] output = new double[row][col];
        for (int i = 0; i < row; i++) {
            int k = 0;
            for (int j = 0; j < col; j++) {
                StringBuilder temp = new StringBuilder();
                boolean endNumber = false;
                while (!endNumber && k < contents.get(i).length()) {
                    if (contents.get(i).charAt(k) == ' ' || contents.get(i).charAt(k) == '\n') {
                        endNumber = true;
                        k++;
                    }
                    if (!endNumber) {
                        temp.append(contents.get(i).charAt(k));
                        k++;
                    }

                }
                output[i][j] = Double.parseDouble(temp.toString());
            }
        }

        return output;
    }

    public static ArrayList<String> doubleToArrayOfString(double[][] contents) {
        ArrayList<String> output = new ArrayList<>();

        for (double[] content : contents) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < contents[0].length; j++) {
                temp.append(content[j]);
                if (j == contents[0].length - 1) {
                    temp.append('\n');
                } else {
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
