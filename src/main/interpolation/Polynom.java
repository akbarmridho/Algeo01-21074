package main.interpolation;

import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.SPL.Gauss;
import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.matrix.errors.NotMatrixSquareException;

public class Polynom {
    public static double[] solve(double[][] points) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
        int equations = points.length;
        Matrix xPolynomExpansion = new Matrix(equations, equations);
        Matrix yPolynomResult = new Matrix(equations, 1);

        for (int i = 0; i < equations; i++) {
            for (int j = 0; j < equations; j++) {
                xPolynomExpansion.getMatrix()[i][j] = Math.pow(points[i][0], j);
            }
        }

        for (int i = 0; i < equations; i++) {
            yPolynomResult.getMatrix()[i][0] = points[i][1];
        }

        MatrixAugmented Polynomials = new MatrixAugmented(xPolynomExpansion, yPolynomResult);

        return Gauss.solve(Polynomials);
    }
}
