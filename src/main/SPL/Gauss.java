package main.SPL;

import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

public class Gauss {
    public static Matrix solve(MatrixAugmented matrixCpy) throws NoSolutionException {
        MatrixAugmented matrix = matrixCpy.copy();

        operation(matrix);
        matrix.trimEquation();
        Integer[] removedIdx = Transformers.transformParametric(matrix);

        // selesaikan solusi SPL dari matriks eselon
        for (int i = matrix.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                matrix.addRow(j, i, -1 * matrix.getOriginal().getMatrix()[j][i]);
            }
        }

        Matrix equationRHS = matrix.getAugmentation();
        equationRHS.assign(Transformers.formatParam(matrix, removedIdx));

        // buat array hasil
        return matrix.getAugmentation();
    }

    public static void operation(MatrixAugmented matrix) {
        int i = 0; // pivot row
        int j = 0; // pivot col
        int m = matrix.getOriginal().getRowCount(); // row count
        int n = matrix.getOriginal().getColumnCount(); // col count

        while (i < m && j < n) {
            int iMax = matrix.getColAbsMaxIndex(j, i, m - 1);

            if (matrix.getOriginal().getMatrix()[iMax][j] == 0.0) {
                j++;
            } else {
                if (iMax != i) {
                    matrix.swapRow(i, iMax);
                }

                double divider = matrix.getOriginal().getMatrix()[i][j];
                if (Math.abs(divider) < Math.pow(2, -46)) {
                    matrix.getOriginal().getMatrix()[i][j] = 0;
                } else {
                    matrix.multiplyRow(i, 1d / divider);

                    for (int x = i + 1; x < m; x++) {
                        double multiplier = matrix.getOriginal().getMatrix()[x][j];
                        matrix.addRow(x, i, (-1) * multiplier);
                    }
                    i++;
                }
                j++;
            }
        }
    }
}
