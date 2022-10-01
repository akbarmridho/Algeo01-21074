package CaseStudy;

import main.interpolation.Bicubic;
import main.interpolation.Polynom;
import main.matrix.Matrix;

public class InterpolationCase {
    public static void main(String[] args) {
        System.out.println("CASE 3A");
        case3a();
        System.out.println("CASE 3B");
        case3b();
        System.out.println("CASE 3C");
        case3c();
        System.out.println("CASE 4");
        case4();
    }

    public static void case3a() {
        double[][] points = {
                {0.4, 0.043},
                {0.7, 0.005},
                {0.11, 0.058},
                {0.14, 0.072},
                {0.17, 0.1},
                {0.2, 0.13},
                {0.23, 0.147}
        };

        double[] constants = Polynom.solve(points);
        System.out.println(Polynom.buildEquation(constants));

        for (double x : new double[]{0.2, 0.55, 0.85, 1.28}) {
            System.out.printf("f(%.2f) = %f%n", x, Polynom.predict(constants, x));
        }
    }

    public static void case3b() {
        double[][] points = {
                {6.567, 12624},
                {7, 21807},
                {7.258, 38391},
                {7.451, 54517},
                {7.548, 51952},
                {7.839, 28228},
                {8.161, 35764},
                {8.484, 20813},
                {8.709, 12408},
                {9, 10534}
        };

        double[] constants = Polynom.solve(points);
        System.out.println(Polynom.buildEquation(constants));

        for (double x : new double[]{
                7 + 16d / 31d,
                8 + 10d / 31d,
                9 + 5d / 30d,
                7.548
        }) {
            System.out.printf("f(%.2f) = %d%n", x, (int) Polynom.predict(constants, x));
        }
    }

    public static void case3c() {

        for (int n : new int[]{5, 10}) {
            System.out.println("Saat n = " + n);
            double step = (2d - 0d) / n;
            double[][] points = new double[n + 1][2];

            for (int i = 0; i <= n; i++) {
                points[i][0] = step * i;
                points[i][1] = fx(step * i);
            }

            double[] constants = Polynom.solve(points);
            System.out.println(Polynom.buildEquation(constants));

            for (double x : new double[]{0.2, 0.55, 0.85, 1.28}) {
                System.out.printf("f(%.2f) = %f%n", x, Polynom.predict(constants, x));
            }
        }

    }

    public static double fx(double x) {
        return (Math.pow(x, 2) * Math.sqrt(x)) / (Math.exp(x) + x);
    }

    public static void case4() {
        double[][] contents = {
                {153, 59, 210, 96},
                {125, 161, 72, 81},
                {98, 101, 42, 12},
                {21, 51, 0, 16}
        };

        Bicubic solver = new Bicubic(new Matrix(contents));

        System.out.printf("Hasil prediksi f(%.2f,%.2f) adalah %f\n", 0.0, 0.0, solver.interpolate(0, 0));
        System.out.printf("Hasil prediksi f(%.2f,%.2f) adalah %f\n", 0.5, 0.5, solver.interpolate(0.5, 0.5));
        System.out.printf("Hasil prediksi f(%.2f,%.2f) adalah %f\n", 0.25, 0.75, solver.interpolate(0.25, 0.75));
        System.out.printf("Hasil prediksi f(%.2f,%.2f) adalah %f\n", 0.5, 0.5, solver.interpolate(0.1, 0.9));
    }
}
