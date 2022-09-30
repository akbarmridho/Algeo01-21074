package main.SPL;

import java.util.Arrays;

import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.matrix.MatrixAugmented;
import main.matrix.Matrix;
import main.matrix.errors.NotMatrixSquareException;

public class Gauss {
    public static Matrix solve(MatrixAugmented matrixCpy) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
        MatrixAugmented matrix = matrixCpy.copy();
        operation(matrix, true);
        matrix.trimEquation();
        Integer[] removedIdx = Transformers.removeUnnecesaryVariable(matrix);
        
        

        // selesaikan solusi SPL dari matriks eselon
        for (int i = matrix.getRowCount() - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                matrix.addRow(j, i, -1 * matrix.getOriginal().getMatrix()[j][i]);
            }
        }

        // buat array hasil
        return matrix.getAugmentation();
    }

    /*
     * lakukan operasi gauss sehingga terbentuk matriks eselon
     */
    public static void operation(MatrixAugmented matrix, boolean skipDeterminant) throws NotMatrixSquareException, NoSolutionException {
        if (!skipDeterminant)
        {
            double determinant = matrix.getOriginal().getDeterminant();

            if (determinant == 0) {
                throw new NoSolutionException("Tidak terdapat solusi SPL karena determinan nol");
            }
        }

        // Sort matrix agar kolom ke-n pada baris ke-n merupakan nilai terbesar di antara kolom ke-n pada semua baris
        for (int i = 0; i < matrix.getOriginal().getColumnCount() - 1; i++) {
            int maxRowIdx = matrix.getColMaxIndex(i, i, matrix.getRowCount() - 1);
            if (maxRowIdx != -1 && i != maxRowIdx) {
                matrix.swapRow(i, maxRowIdx);
            }
        }

        // Untuk baris ke-n, lakukan operasi pada baris sehingga kolom ke-n pada baris lain bernilai nol
        for (int i = 0; i < matrix.getRowCount(); i++) {
            // Sebelum baris matriksnya dipasangkan, terlebih dahulu bagi barisnya sehingga kolom ke-n pada baris ke-n
            // bernilai 1

            // terdapat rare case ketika elemen [i,i] merupakan nol akibat dari pengurangan yang dilakukan pada loop
            // sebelumnya, sehingga kita harus melakukan swapping lagi
            if (i < matrix.getOriginal().getColumnCount()) {
                if (matrix.getOriginal().getMatrix()[i][i] == 0.0)
                {
                    int maxRowIdx = matrix.getColMaxIndex(i, i, matrix.getRowCount() - 1);
                    if (maxRowIdx != -1 && i != maxRowIdx) {
                        matrix.swapRow(i, maxRowIdx);
                    } else if (i != matrix.getRowCount() - 1) {
                        // terdapat case juga saat nilai maksimum pada kolom i adalah 0
                        // mengingat divider tidak boleh nol, akhira kita loop dan swap agar nilai pada kolom [i,i] bernilai
                        // negatif

                        int searchIdx = i+1;
                        while(matrix.getOriginal().getMatrix()[searchIdx][i] == 0.0 && searchIdx < matrix.getRowCount())
                        {
                            searchIdx++;
                        }

                        matrix.swapRow(i, searchIdx);
                    }
                }

                double divider = matrix.getOriginal().getMatrix()[i][i];
                if(divider != 0.0){
                    matrix.multiplyRow(i, 1d / divider);
                }

                for (int j = i + 1; j < matrix.getRowCount(); j++) {
                    double multiplier = matrix.getOriginal().getMatrix()[j][i];

                    matrix.addRow(j, i, -1d * multiplier);
                }
            }
        }
    }

}
