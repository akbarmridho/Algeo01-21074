package CaseStudy;

import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.regression.MultipleLinear;

import java.util.Arrays;

public class MultipleLinearCase {
    public static void main(String[] args) {
        double[][] xi = {
                {72.4, 76.3, 29.18},
                {41.6, 70.3, 29.35},
                {34.3, 77.1, 29.24},
                {35.1, 68, 29.27},
                {10.7, 79, 29.78},
                {12.9, 67.4, 29.39},
                {8.3, 66.8, 29.69},
                {20.1, 76.9, 29.48},
                {72.2, 77.7, 29.09},
                {24, 67.7, 29.6},
                {23.2, 76.8, 29.38},
                {47.4, 86.6, 29.35},
                {31.5, 76.9, 29.63},
                {10.6, 86.3, 29.56},
                {11.2, 86, 29.48},
                {73.3, 76.3, 29.4},
                {75.4, 77.9, 29.28},
                {96.6, 78.7, 29.29},
                {107.4, 86.8, 29.03},
                {54.9, 70.9, 29.37}
        };
        double[][] yi = {
                {0.9},
                {0.91},
                {0.96},
                {0.89},
                {1},
                {1.1},
                {1.15},
                {1.03},
                {0.77},
                {1.07},
                {1.07},
                {0.94},
                {1.1},
                {1.1},
                {1.1},
                {0.91},
                {0.87},
                {0.78},
                {0.82},
                {0.95}
        };
        MatrixAugmented data = new MatrixAugmented(new Matrix(xi), new Matrix(yi));

        // demonstrasi normalEstSystem
        MatrixAugmented normal = MultipleLinear.normalEstSystem(data);
        System.out.println("SPL estimasi normal");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    System.out.printf("%.3f", normal.getOriginal().getMatrix()[i][j]);
                } else {
                    if (normal.getOriginal().getMatrix()[i][j] < 0) {
                        System.out.print(" - ");
                    } else {
                        System.out.print(" + ");
                    }
                    System.out.printf("%.3fx%d", Math.abs(normal.getOriginal().getMatrix()[i][j]), j);
                }

            }

            System.out.printf(" = %.3f\n", normal.getAugmentation().getMatrix()[i][0]);
        }


        // demonstrasi pencarian koef regresi
        System.out.println();
        try {
            double[] bVal = MultipleLinear.regCoefficients(data);
            System.out.println(MultipleLinear.buildEquation(bVal));

            // demonstrasi prediksi regresi
            double[] xVal = {50, 76, 29.3};
            double result = MultipleLinear.predict(bVal, xVal);

            StringBuilder resultString = new StringBuilder();
            resultString.append("f(");

            for (int i = 0; i < xVal.length; i++) {
                if (i != xVal.length - 1) {
                    resultString.append(String.format("%.3f, ", xVal[i]));
                } else {
                    resultString.append(String.format("%.3f)", xVal[i]));
                }
            }

            resultString.append(" = ").append(result);
            System.out.println(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
