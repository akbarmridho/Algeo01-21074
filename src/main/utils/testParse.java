package main.utils;

import java.util.ArrayList;

import main.utils.Parser;
import java.util.Arrays;

public class testParse {
    public static void main(String[] args) {
        ArrayList<String> a = Parser.readFile(System.getProperty("user.dir")+"/src/main/utils/test.txt");
        a.add("1 -3 5 2");
        a.add("1.5 106 20 2.598");
        double[][] p = new double[2][2]; 


        System.out.println(Arrays.deepToString(Parser.listOfStringToDouble(a)));
        //System.out.println(Arrays.deepToString(p));
    }
}
