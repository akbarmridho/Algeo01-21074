package main;

import main.SPL.Cramer;
import main.SPL.Gauss;
import main.SPL.GaussJordan;
import main.SPL.Inverse;
import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.utils.Parser;
import main.matrix.MatrixAugmented;
import main.matrix.Matrix;
import main.matrix.errors.NotMatrixSquareException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean isExit = false;

        try (Scanner in = new Scanner(System.in)) {
            while (!isExit) {
                System.out.println("MENU");
                System.out.println("1. Sistem Persamaan Linear");
                System.out.println("2. Determinan");
                System.out.println("3. Matriks balikan");
                System.out.println("4. Interpolasi Polinom");
                System.out.println("5. Interpolasi Bicubic");
                System.out.println("6. Regresi linier berganda");
                System.out.println("7. Keluar");
    
                int choice = in.nextInt();

                switch (choice) {
                    case 1:
                        linearEquation(in);
                        break;
                    case 2:
                        determinent(in);
                        break;
                    case 3:
                        inverse(in);
                        break;
                    case 4:
                        polynomialInterpolation(in);
                        break;
                    case 5:
                        bicubicInterpolation(in);
                        break;
                    case 6:
                        multipleRegression(in);
                        break;
                    case 7:
                        isExit = true;
                        break;
                    default:
                        System.out.println("Pilihan salah!");
                        break;
                }
                    
            }
            
        }
        
    }

    public static void linearEquation(Scanner in) {
        int option = 1;
        MatrixAugmented matrix = new MatrixAugmented(new double[2][2]);

        while (true) {
            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode eliminasi Gauss-Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");

            option = in.nextInt();

            if (option != 1 && option != 2 && option != 3 && option !=4) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }

        int inputOption = fileOrInputChoice(in);

        if (inputOption == 1)
        {
            System.out.println("Masukkan banyaknya persamaan: ");
            int equationCount = in.nextInt();
            System.out.println("Masukkan banyaknya peubah: ");
            int variableCount = in.nextInt();

            double[][] contents = new double[equationCount][variableCount+1];

            for (int i = 0; i<equationCount; i++)
            {
                System.out.println("Masukkan persamaan: (format a1 ... an bi");
                for (int j = 0; j<variableCount; j++)
                {
                    contents[i][j] = in.nextDouble();
                }

                contents[i][variableCount] = in.nextDouble();
            }

            matrix = new MatrixAugmented(contents);
        } else {
            ArrayList<String> result = readFile(in);
        }

        double[] result;
        ArrayList<String> output = new ArrayList<String>();

        switch (option)
        {
            case 1:
                try {
                    output = Transformers.printParametric(Gauss.solve(matrix));
                } catch(NotMatrixSquareException | InfinitySolutionException e) {
                    System.out.println(e.getMessage());
                } catch(NoSolutionException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                try {
                    output = Transformers.printParametric(Gauss.solve(matrix));
                } catch(NotMatrixSquareException | InfinitySolutionException e) {
                    System.out.println(e.getMessage());
                } catch(NoSolutionException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                try {
                    result = Inverse.solve(matrix);
                    double[][] content = {result};
                    Matrix solution = new Matrix(content);
                    output = Transformers.printParametric(solution.transpose());
                } catch(NotMatrixSquareException | InfinitySolutionException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                try {
                    result = Cramer.solve(matrix);
                    double[][] content = {result};
                    Matrix solution = new Matrix(content);
                    output = Transformers.printParametric(solution.transpose());
                } catch(NotMatrixSquareException | InfinitySolutionException e) {
                    System.out.println(e.getMessage());
                } catch(NoSolutionException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                result = new double[]{};
                break;
        }

        System.out.println("");
        // hasil bisa printout ke terminal atau file???
        saveToFile(output,System.getProperty("user.dir")+"/src/main/utils/test.txt",in);
    }

    public static void determinent(Scanner in) {
        int option = 1;

        while (true) {
            System.out.println("1. Metode minor-kofaktor");
            System.out.println("2. Metode matriks eseon");

            option = in.nextInt();

            if (option != 1 && option != 2 && option != 3 && option !=4) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }
    }

    public static void inverse(Scanner in) {
        int option = 1;

        while (true) {
            System.out.println("1. Metode adjoin");
            System.out.println("2. Metode Gauss");

            option = in.nextInt();

            if (option != 1 && option != 2 && option != 3 && option !=4) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }
    }

    public static void polynomialInterpolation(Scanner in) {
        //
    }

    public static void bicubicInterpolation(Scanner in) {
        //
    }

    public static void multipleRegression(Scanner in) {
        //
    }

    public static int fileOrInputChoice(Scanner in) {
        int option = 1;

        while (true) {
            System.out.println("Pilih sumber data:");
            System.out.println("1. Input manual");
            System.out.println("2. File");

            option = in.nextInt();

            if (option != 1 && option != 2) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }

        return option;
    }

    public static ArrayList<String> readFile(Scanner in) {
        ArrayList<String> result = new ArrayList<>();

        while (true) {
            System.out.println("Masukkan path file: (contoh: file.txt)");
            String path = in.nextLine().replace("\n", "");

            try {
                BufferedReader br = new BufferedReader(new FileReader(path));

                String line = br.readLine();
                while (line!= null)
                {
                    result.add(line);
                }

                break;
            } catch (FileNotFoundException e) {
                System.out.println("File dengan path " + path + " tidak ditemukan");
            } catch (IOException e) {
                System.out.println("Unexpected error occur: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return result;
    }

    static void saveToFile(ArrayList<String> output, String path, Scanner in){
        System.out.println("Simpan output pada {" + path + "} ?");
        System.out.println("Ya (1) : Tidak (0)");
        
        int choice = in.nextInt();

        switch(choice) {
            case 1 :
                Parser.writeFile(output,path);
                break;
            case 0 :
                System.out.println("file tidak disimpan.");
                break;
            default :
                System.out.println("file tidak disimpan.");
                break;
                
        }
    }
}
