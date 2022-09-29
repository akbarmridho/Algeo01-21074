package CaseStudy;

import main.interpolation.Bicubic;
import main.interpolation.Polynom;
import main.matrix.Matrix;

public class InterpolationCase {
    public static void main(String[] args) {
        case3a();
        case3b();
        case3c();
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

        try {
            double[] constants = Polynom.solve(points);

            double y1 = Polynom.predict(constants, 0.2);
            double y2 = Polynom.predict(constants, 0.55);
            double y3 = Polynom.predict(constants, 0.85);
            double y4 = Polynom.predict(constants, 1.28);
        } catch (Exception e) {

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

        try {
            double[] result = Polynom.solve(points);

            double firstTc = 7 + 16d / 31d;
            double secondTc = 8 + 10d / 31d;
            double thirdTc = 9 + 5d / 30d;
            double fourthTc = 7.548;

            System.out.println((int) Polynom.predict(result, firstTc));
            System.out.println((int) Polynom.predict(result, secondTc));
            System.out.println((int) Polynom.predict(result, thirdTc));
            System.out.println((int) Polynom.predict(result, fourthTc));
        } catch (Exception e) {
        }
    }

    public static void case3c() {

        for (int n : new int[]{5, 10}) {
            double step = (2d - 0d) / n;
            double[][] points = new double[n + 1][2];

            for (int i = 0; i <= n; i++) {
                points[i][0] = step * i;
                points[i][1] = fx(step * i);
            }

            try {
                double[] result = Polynom.solve(points);
            } catch (Exception e) {

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

        double result1 = solver.interpolate(0, 0);
        double result2 = solver.interpolate(0.5, 0.5);
        double result3 = solver.interpolate(0.25, 0.75);
        double result4 = solver.interpolate(0.1, 0.9);
    }
}
