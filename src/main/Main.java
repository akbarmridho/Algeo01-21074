package main;

import main.SPL.Cramer;
import main.SPL.Gauss;
import main.SPL.GaussJordan;
import main.SPL.Inverse;
import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.interpolation.Bicubic;
import main.interpolation.Polynom;
import main.matrix.MatrixAlternative;
import main.matrix.errors.ZeroDeterminantException;
import main.regression.MultipleLinear;
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
                    case 1 -> linearEquation(in);
                    case 2 -> determinant(in);
                    case 3 -> inverse(in);
                    case 4 -> polynomialInterpolation(in);
                    case 5 -> bicubicInterpolation(in);
                    case 6 -> multipleRegression(in);
                    case 7 -> isExit = true;
                    default -> System.out.println("Pilihan salah!");
                }

            }

        }

    }

    public static void linearEquation(Scanner in) {
        int option;


        while (true) {
            System.out.println("1. Metode eliminasi Gauss");
            System.out.println("2. Metode eliminasi Gauss-Jordan");
            System.out.println("3. Metode matriks balikan");
            System.out.println("4. Kaidah Cramer");

            option = in.nextInt();

            if (option != 1 && option != 2 && option != 3 && option != 4) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }

        int inputOption = fileOrInputChoice(in);

        double[][] contents;


        if (inputOption == 1) {
            System.out.println("Masukkan banyaknya persamaan: ");
            int equationCount = in.nextInt();
            System.out.println("Masukkan banyaknya peubah: ");
            int variableCount = in.nextInt();

            contents = new double[equationCount][variableCount + 1];

            for (int i = 0; i < equationCount; i++) {
                System.out.println("Masukkan persamaan: (format a1 ... an bi)");
                for (int j = 0; j < variableCount; j++) {
                    contents[i][j] = in.nextDouble();
                }

                contents[i][variableCount] = in.nextDouble();
            }
        } else {
            ArrayList<String> stringList = readFile(in);
            contents = new double[stringList.size()][stringList.get(0).split("\\s+").length];

            for (int i = 0; i < stringList.size(); i++) {
                String[] rows = stringList.get(i).split("\\s+");
                for (int j = 0; j < rows.length; j++) {
                    contents[i][j] = Double.parseDouble(rows[j]);
                }
            }
        }

        int outputOption = fileOutputChoice(in);

        MatrixAugmented matrix = new MatrixAugmented(contents);

        ArrayList<String> output = new ArrayList<>();

        switch (option) {
            case 1:
                try {
                    output = Transformers.printParametric(Gauss.solve(matrix));
                } catch (NotMatrixSquareException | InfinitySolutionException | NoSolutionException e) {
                    output.add(e.getMessage());
                }
                break;
            case 2:
                try {
                    output = Transformers.printParametric(GaussJordan.solve(matrix));
                } catch (NotMatrixSquareException | InfinitySolutionException | NoSolutionException e) {
                    output.add(e.getMessage());
                }
                break;
            case 3:
                try {
                    double[] result = Inverse.solve(matrix);
                    double[][] content = {result};
                    Matrix solution = new Matrix(content);
                    output = Transformers.printParametric(solution.transpose());
                } catch (NotMatrixSquareException | InfinitySolutionException e) {
                    output.add(e.getMessage());
                }
                break;
            case 4:
                try {
                    double[] result = Cramer.solve(matrix);
                    double[][] content = {result};
                    Matrix solution = new Matrix(content);
                    output = Transformers.printParametric(solution.transpose());
                } catch (NotMatrixSquareException | InfinitySolutionException | NoSolutionException e) {
                    output.add(e.getMessage());
                }
                break;
            default:
                output.add("Metode salah\n");
                break;
        }

        for (String s : output) {
            System.out.println(s);
        }

        if (outputOption == 1) {
            saveToFile(output, "SPL" + System.currentTimeMillis() / 1000 + ".txt");
        }
    }

    public static void determinant(Scanner in) {
        int option;

        while (true) {
            System.out.println("1. Metode minor-kofaktor");
            System.out.println("2. Metode matriks eselon");

            option = in.nextInt();

            if (option != 1 && option != 2) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }

        int inputOption = fileOrInputChoice(in);

        double[][] contents;

        if (inputOption == 1) {
            System.out.println("Masukkan nilai n untuk matriks berukuran n x n: ");
            int size = in.nextInt();

            contents = new double[size][size];

            for (int i = 0; i < size; i++) {
                System.out.println("Masukkan persamaan: (format aij)");
                for (int j = 0; j < size; j++) {
                    contents[i][j] = in.nextDouble();
                }
            }
        } else {
            ArrayList<String> stringList = readFile(in);
            contents = new double[stringList.size()][stringList.size()];

            for (int i = 0; i < stringList.size(); i++) {
                String[] rows = stringList.get(i).replace("\n", "").split("\\s+");
                for (int j = 0; j < rows.length; j++) {
                    contents[i][j] = Double.parseDouble(rows[j]);
                }
            }
        }

        int outputOption = fileOutputChoice(in);

        Matrix matrix = new Matrix(contents);

        double determinant;

        if (option == 1) {
            determinant = -9999;
            try {
                determinant = matrix.getDeterminant();
            } catch (Exception e) {
                // guaranteed to be matrix square
            }
        } else {
            determinant = MatrixAlternative.determinant(matrix);
        }

        String output = "Determinan matriks adalah " + String.format("%.3f", determinant);

        System.out.println(output);

        ArrayList<String> outputFile = Parser.doubleToArrayOfString(contents);
        outputFile.add(output);

        if (outputOption == 1) {
            saveToFile(outputFile, "determinant" + System.currentTimeMillis() / 1000 + ".txt");
        }
    }

    public static void inverse(Scanner in) {
        int option;

        while (true) {
            System.out.println("1. Metode adjoin");
            System.out.println("2. Metode Gauss");

            option = in.nextInt();

            if (option != 1 && option != 2 && option != 3 && option != 4) {
                System.out.println("Input salah! Silakan ulangi");
            } else {
                break;
            }
        }

        int inputOption = fileOrInputChoice(in);

        double[][] contents;

        if (inputOption == 1) {
            System.out.println("Masukkan nilai n untuk matriks berukuran n x n: ");
            int size = in.nextInt();

            contents = new double[size][size];

            for (int i = 0; i < size; i++) {
                System.out.println("Masukkan persamaan: (format aij)");
                for (int j = 0; j < size; j++) {
                    contents[i][j] = in.nextDouble();
                }
            }
        } else {
            ArrayList<String> stringList = readFile(in);
            contents = new double[stringList.size()][stringList.size()];

            for (int i = 0; i < stringList.size(); i++) {
                String[] rows = stringList.get(i).replace("\n", "").split("\\s+");
                for (int j = 0; j < rows.length; j++) {
                    contents[i][j] = Double.parseDouble(rows[j]);
                }
            }
        }

        int outputOption = fileOutputChoice(in);

        Matrix matrix = new Matrix(contents);

        ArrayList<String> result = new ArrayList<>();

        if (option == 1) {
            try {
                Matrix inverse = matrix.getInverse();
                result.addAll(Parser.doubleToArrayOfString(inverse.getMatrix()));
            } catch (Exception e) {
                result.add(e.getMessage() + "\n");
            }
        } else {
            try {
                Matrix inverse = MatrixAlternative.inverse(matrix);
                result.addAll(Parser.doubleToArrayOfString(inverse.getMatrix()));
            } catch (ZeroDeterminantException e) {
                result.add(e.getMessage() + "\n");
            }
        }

        for (String line : result) {
            System.out.println(line);
        }

        if (outputOption == 1) {
            saveToFile(result, "inverse" + System.currentTimeMillis() / 1000 + ".txt");
        }
    }

    public static void polynomialInterpolation(Scanner in) {
        int inputOption = fileOrInputChoice(in);

        double[][] contents;
        double xPredict;

        if (inputOption == 1) {
            System.out.println("Masukkan banyaknya titik: ");
            int n = in.nextInt();

            contents = new double[n][2];

            for (int i = 0; i < n; i++) {
                System.out.println("Masukkan persamaan: (format x y tanpa tanda koma atau kurung)");
                for (int j = 0; j < 2; j++) {
                    contents[i][j] = in.nextDouble();
                }
            }

            System.out.println("Masukkan titik x yang ingin diprediksi");
            xPredict = in.nextDouble();
        } else {
            ArrayList<String> stringList = readFile(in);
            contents = new double[stringList.size() - 1][2];

            for (int i = 0; i < stringList.size() - 1; i++) {
                String[] rows = stringList.get(i).replace("\n", "").split("\\s+");
                for (int j = 0; j < rows.length; j++) {
                    contents[i][j] = Double.parseDouble(rows[j]);
                }
            }

            xPredict = Double.parseDouble(stringList.get(stringList.size() - 1).replace("\n", ""));
        }

        int outputOption = fileOutputChoice(in);

        ArrayList<String> result = new ArrayList<>();

        double[] constants = Polynom.solve(contents);

        result.add(Polynom.buildEquation(constants));
        result.add(String.format("Hasil prediksi f(%f) adalah %f", xPredict, Polynom.predict(constants, xPredict)));

        for (String line : result) {
            System.out.println(line);
        }

        if (outputOption == 1) {
            saveToFile(result, "polynom" + System.currentTimeMillis() / 1000 + ".txt");
        }
    }

    public static void bicubicInterpolation(Scanner in) {
        int inputOption = fileOrInputChoice(in);

        double[][] contents;
        double xPredict, yPredict;

        if (inputOption == 1) {
            contents = new double[4][4];

            System.out.println("Input matriks 4x4 dengan 4 kolom per barisnya");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    contents[i][j] = in.nextDouble();
                }
            }

            System.out.println("Masukkan titik prediksi x y dalam satu baris (x, y di antara 0 dan 1)");
            xPredict = in.nextDouble();
            yPredict = in.nextDouble();
        } else {
            ArrayList<String> stringList = readFile(in);
            contents = new double[4][4];

            for (int i = 0; i < 4; i++) {
                String[] rows = stringList.get(i).replace("\n", "").split("\\s+");
                for (int j = 0; j < rows.length; j++) {
                    contents[i][j] = Double.parseDouble(rows[j]);
                }
            }

            String[] lastRow = stringList.get(4).replace("\n", "").split("\\s+");
            xPredict = Double.parseDouble(lastRow[0]);
            yPredict = Double.parseDouble(lastRow[1]);
        }

        int outputOption = fileOutputChoice(in);

        ArrayList<String> result = new ArrayList<>();

        Bicubic interpolater = new Bicubic(new Matrix(contents));
        double predictVal = interpolater.interpolate(xPredict, yPredict);

        result.add(String.format("Hasil prediksi f(%f,%f) adalah %f", xPredict, yPredict, predictVal));

        for (String line : result) {
            System.out.println(line);
        }

        if (outputOption == 1) {
            saveToFile(result, "bicubic" + System.currentTimeMillis() / 1000 + ".txt");
        }
    }

    public static void multipleRegression(Scanner in) {
        int inputOption = fileOrInputChoice(in);

        double[][] contents;
        double[] xPredict;

        if (inputOption == 1) {
            System.out.println("Masukkan banyaknya titik: ");
            int n = in.nextInt();
            System.out.println("Masukkan banyaknya peubah x: ");
            int xn = in.nextInt();

            contents = new double[n][xn + 1];

            for (int i = 0; i < n; i++) {
                System.out.println("Masukkan baris data (xi1 ... xin yi");
                for (int j = 0; j < xn; j++) {
                    contents[i][j] = in.nextDouble();
                }
                contents[i][xn] = in.nextDouble();
            }

            System.out.println("Masukkan baris x yang ingin diprediksi (xi1 ... xin)");

            xPredict = new double[xn];

            for (int i = 0; i < xn; i++) {
                xPredict[i] = in.nextDouble();
            }
        } else {
            ArrayList<String> stringList = readFile(in);
            contents = new double[stringList.size() - 1][stringList.get(0).split("\\s+").length];

            for (int i = 0; i < stringList.size() - 1; i++) {
                String[] rows = stringList.get(i).replace("\n", "").split("\\s+");
                for (int j = 0; j < rows.length; j++) {
                    contents[i][j] = Double.parseDouble(rows[j]);
                }
            }

            String[] lastRow = stringList.get(0).replace("\n", "").split("\\s+");

            xPredict = new double[lastRow.length];

            for (int i = 0; i < lastRow.length; i++) {
                xPredict[i] = in.nextDouble();
            }
        }

        int outputOption = fileOutputChoice(in);

        ArrayList<String> result = new ArrayList<>();

        double[] constants = MultipleLinear.regCoefficients(new MatrixAugmented(contents));
        double prediction = MultipleLinear.predict(constants, xPredict);

        result.add(MultipleLinear.buildEquation(constants));

        StringBuilder resultString = new StringBuilder();
        resultString.append("f(");

        for (int i = 0; i < xPredict.length; i++) {
            if (i != xPredict.length - 1) {
                resultString.append(String.format("%.3f, ", xPredict[i]));
            } else {
                resultString.append(String.format("%.3f)", xPredict[i]));
            }
        }

        resultString.append(" = ").append(prediction);
        result.add(resultString.toString());

        for (String line : result) {
            System.out.println(line);
        }

        if (outputOption == 1) {
            saveToFile(result, "multiplelinear" + System.currentTimeMillis() / 1000 + ".txt");
        }
    }

    public static int fileOrInputChoice(Scanner in) {
        int option;

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

    public static int fileOutputChoice(Scanner in) {
        int option;

        while (true) {
            System.out.println("Apakah anda ingin menyimpan file hasil perhitungan?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");

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
        String path;
        FileReader reader;
        boolean fileFound = false;

        do {
            System.out.println("Masukkan path file relatif terhadap folder test: (contoh: file.txt)");
            path = in.nextLine().replace("\n", "");
            try {
                reader = new FileReader(System.getProperty("user.dir") + File.pathSeparator + "test" + File.pathSeparator + path);
                fileFound = true;

                BufferedReader br = new BufferedReader(reader);

                String line = br.readLine();
                while (line != null) {
                    result.add(line);
                    line = br.readLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File dengan path " + path + " tidak ditemukan");
            } catch (IOException e) {
                System.out.println("Unexpected error occur: " + e.getMessage());
                e.printStackTrace();
            }
        } while (!fileFound);

        return result;
    }

    static void saveToFile(ArrayList<String> output, String path) {
        String basePath = System.getProperty("user.dir") + File.pathSeparator + "test" + File.pathSeparator;
        try {
            Parser.writeFile(output, path);
            System.out.println("File berhasil disimpan pada path " + basePath + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
