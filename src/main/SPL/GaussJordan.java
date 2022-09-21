package main.SPL;

import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.matrix.MatrixAugmented;
import main.matrix.errors.NotMatrixSquareException;

public class GaussJordan {
    public static double[] solve(MatrixAugmented matrix) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
        Integer[] removedIdx = Transformers.removeUnnecesaryVariable(matrix);
        Gauss.operation(matrix);

        // lakukan operasi jordan (setelah operasi gauss) untuk membentuk solusi
        for (int i = matrix.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                matrix.addRow(j, i, matrix.getOriginal().getMatrix()[j][i]);
            }
        }

        // buat array hasil
        return Transformers.fillResultWithZero(matrix.getAugmentation().transpose().getMatrix()[0], removedIdx);
    }
}
