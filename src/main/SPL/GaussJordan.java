package main.SPL;

import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.matrix.MatrixAlternative;
import main.matrix.MatrixAugmented;
import main.matrix.Matrix;
import main.matrix.errors.NotMatrixSquareException;

public class GaussJordan {
    public static Matrix solve(MatrixAugmented matrixCpy, boolean skipDeterminant) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
//        if (!skipDeterminant && matrixCpy.getRowCount() == matrixCpy.getOriginal().getColumnCount() &&
//                Math.abs(MatrixAlternative.determinant(matrixCpy.getOriginal())) < Math.pow(2, -46)
//        ) {
//            throw new NoSolutionException("Tidak terdapat solusi SPL karena determinan sama dengan nol");
//        }

        MatrixAugmented matrix = matrixCpy.copy();
        operation(matrix);
        matrix.trimEquation();
        Transformers.transformParametric(matrix);

        // buat array hasil
        return matrix.getAugmentation();
    }

    public static void operation(MatrixAugmented matrix) {
        Gauss.operation(matrix);

        // lakukan operasi jordan (setelah operasi gauss) untuk membentuk solusi
        for (int i = matrix.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                matrix.addRow(j, i, -1 * matrix.getOriginal().getMatrix()[j][i]);
            }
        }
    }
}
