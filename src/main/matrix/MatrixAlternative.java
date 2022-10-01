package main.matrix;

import main.SPL.GaussJordan;
import main.matrix.errors.ZeroDeterminantException;

public class MatrixAlternative {
    /*
     * Lakukan invers matriks dengan metode gauss-jordan
     */
    public static Matrix inverse(Matrix matrix, boolean skipDeterminant) throws ZeroDeterminantException {
        MatrixAugmented matrixPair = new MatrixAugmented(matrix.copy(), Matrix.identity(matrix.getRowCount()));

        if (!skipDeterminant) {
            if (Math.abs(determinant(matrix)) - Math.pow(2, 23) < 0) {
                throw new ZeroDeterminantException("Tidak dapat diperoleh invers matrix karena determinan nol");
            }
        }

        try {
            GaussJordan.operation(matrixPair);
        } catch (Exception e) {
            // operasi dianggap pasti berhasil
        }

        return matrixPair.getAugmentation();
    }

    /*
     * Lakukan perhitungan determinan dengan OBE dan perkalian diagonal matriks
     */
    public static double determinant(Matrix matrixCpy) {
        Matrix matrix = matrixCpy.copy();

        double determinantMultiplier = 1;

        // Sort matrix agar kolom ke-n pada baris ke-n merupakan nilai terbesar di antara kolom ke-n pada semua baris
        for (int i = 0; i < matrix.getColumnCount(); i++) {
            int maxRowIdx = matrix.getColAbsMaxIndex(i, i, matrix.getRowCount() - 1);
            if (maxRowIdx != -1 && i != maxRowIdx) {
                matrix.swapRow(i, maxRowIdx);
                determinantMultiplier *= -1;
            }
        }

        // Untuk baris ke-n, lakukan operasi pada baris sehingga kolom ke-n pada baris lain bernilai nol
        for (int i = 0; i < matrix.getRowCount(); i++) {
            // Sebelum baris matriksnya dipasangkan, terlebih dahulu bagi barisnya sehingga kolom ke-n pada baris ke-n
            // bernilai 1
            if (matrix.getMatrix()[i][i] == 0.0) {
                int maxRowIdx = matrix.getColAbsMaxIndex(i, i, matrix.getRowCount() - 1);
                if (maxRowIdx != -1 && i != maxRowIdx) {
                    matrix.swapRow(i, maxRowIdx);
                } else {
                    // terdapat case juga saat nilai maksimum pada kolom i adalah 0
                    // mengingat divider tidak boleh nol, akhira kita loop dan swap agar nilai pada kolom [i,i] bernilai
                    // negatif

                    int searchIdx = i + 1;
                    while (matrix.getMatrix()[searchIdx][i] == 0.0 && searchIdx < matrix.getRowCount()) {
                        searchIdx++;
                    }

                    matrix.swapRow(i, searchIdx);
                }
            }

            double divider = matrix.getMatrix()[i][i];
            matrix.multiplyRow(i, 1d / divider);
            determinantMultiplier *= divider;

            for (int j = i + 1; j < matrix.getRowCount(); j++) {
                double multiplier = matrix.getMatrix()[j][i];

                matrix.addRow(j, i, -1d * multiplier);
            }
        }

        double result = 1;

        for (int i = 0; i < matrix.getRowCount(); i++) {
            result *= matrix.getMatrix()[i][i];
        }

        return result * determinantMultiplier;
    }
}
