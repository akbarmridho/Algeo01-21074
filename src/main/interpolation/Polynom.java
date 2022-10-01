package main.interpolation;

import main.SPL.Gauss;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

public class Polynom {
    /*
     * Selesaikan persamaan polinom berdasarkan array of point [x, y]
     * Mengembalikan array of konstanta dari a0 ... an
     */
    public static double[] solve(double[][] points) {
        int equations = points.length;
        Matrix xPolynomExpansion = new Matrix(equations, equations);
        Matrix yPolynomResult = new Matrix(equations, 1);

        // ubah ke dalam bentuk pangkat
        for (int i = 0; i < equations; i++) {
            for (int j = 0; j < equations; j++) {
                xPolynomExpansion.getMatrix()[i][j] = Math.pow(points[i][0], j);
            }
        }

        for (int i = 0; i < equations; i++) {
            yPolynomResult.getMatrix()[i][0] = points[i][1];
        }

        MatrixAugmented Polynomials = new MatrixAugmented(xPolynomExpansion, yPolynomResult);

        double[] result;

        try {
            result = Gauss.solve(Polynomials).transpose().getMatrix()[0];
        } catch (Exception e) {
            result = new double[]{0.0};
        }

        return result;
    }


    /*
     * Prediksikan nilai polynom berdasarkan nilai konstanta a0 .. an
     */
    public static double predict(double[] constants, double x)
    {
        double sum = 0;

        for (int i = 0; i < constants.length; i++)
        {
            sum += constants[i] * Math.pow(x, i);
        }

        return sum;
    }

    public static String buildEquation(double[] constants) {
        StringBuilder temp = new StringBuilder();
        temp.append("f(x) = ");

        for (int i = 0; i < constants.length; i++) {
            if (i == 0) {
                temp.append(String.format("%.3f", constants[i]));
            }  else {
                temp.append(String.format("%.3f", constants[i])).append("x^").append(i);
            }

            if (i != constants.length - 1){
                temp.append(" ");
            }
        }

        return temp.toString();
    }
}
