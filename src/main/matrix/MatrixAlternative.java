package main.matrix;

import main.SPL.GaussJordan;
import main.matrix.errors.ZeroDeterminantException;

public class MatrixAlternative {
    /*
     * Lakukan invers matriks dengan metode gauss-jordan
     */
    public static Matrix inverse(Matrix matrix) throws ZeroDeterminantException {
        MatrixAugmented matrixPair = new MatrixAugmented(matrix.copy(), Matrix.identity(matrix.getRowCount()));

        if (Math.abs(determinant(matrix)) - Math.pow(2, 23) < 0) {
            throw new ZeroDeterminantException("Tidak dapat diperoleh invers matrix karena determinan nol");
        }

        GaussJordan.operation(matrixPair);

        return matrixPair.getAugmentation();
    }

    /*
     * Lakukan perhitungan determinan dengan OBE dan perkalian diagonal matriks
     */
    public static double determinant(Matrix matrixCpy) {
        Matrix matrix = matrixCpy.copy();

        double determinantMultiplier = 1;

        int i = 0; // pivot row
        int j = 0; // pivot col
        int m = matrix.getRowCount(); // row count
        int n = matrix.getColumnCount(); // col count

        while (i < m && j < n) {
            int iMax = matrix.getColAbsMaxIndex(j, i, m - 1);

            if (matrix.getMatrix()[iMax][j] == 0.0) {
                j++;
            } else {
                if (iMax != i) {
                    matrix.swapRow(i, iMax);
                    determinantMultiplier *= -1;
                }

                double divider = matrix.getMatrix()[i][j];
                if (Math.abs(divider) < Math.pow(2, -46)) {
                    matrix.getMatrix()[i][j] = 0;
                    j++;
                } else {
                    matrix.multiplyRow(i, 1d / divider);
                    determinantMultiplier *= divider;

                    for (int x = i + 1; x < m; x++) {
                        double multiplier = matrix.getMatrix()[x][j];
                        matrix.addRow(x, i, (-1) * multiplier);
                    }
                    i++;
                    j++;
                }
            }
        }

        double result = 1;

        for (int l = 0; l < matrix.getRowCount(); l++) {
            result *= matrix.getMatrix()[l][l];
        }

        return result * determinantMultiplier;
    }
}
