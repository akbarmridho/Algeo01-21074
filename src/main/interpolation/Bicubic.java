package main.interpolation;

import main.matrix.Matrix;
import main.matrix.MatrixAlternative;

class CoefMatrix {
    private static CoefMatrix instance = null;

    public Matrix original;
    public Matrix inverse;

    private CoefMatrix() {
        double[][] contents = new double[16][16];
        int rowNum = 0;

        // buat matriks koefisien untuk proses bicubic interpolation
        // matrix original mengikuti persamaan
        // f(x, y) = sigma from i = 0 to 3 of sigma from j = 0 to 3 of a_ij*x^i*y^j
        // x, y = -1, 0, 1, 2
        for (int y = -1; y <= 2; y++) {
            for (int x = -1; x <= 2; x++) {
                double[] rowContent = new double[16];
                int idx = 0;

                for (int j = 0; j <= 3; j++) {
                    for (int i = 0; i <= 3; i++) {
                        rowContent[idx++] = Math.pow(x, i) * Math.pow(y, j);
                    }
                }

                contents[rowNum++] = rowContent;
            }
        }

        this.original = new Matrix(contents);
        try {
            this.inverse = MatrixAlternative.inverse(this.original);
        } catch (Exception e) {
            // pasti berhasil
        }

    }

    /*
     * Singleton instance matriks koefisien untuk perhitungan bicubic
     */
    public static CoefMatrix getInstance() {
        if (instance == null) {
            instance = new CoefMatrix();
        }

        return instance;
    }
}

public class Bicubic {
    private final Matrix constant;

    public Bicubic(Matrix matrix) {
        CoefMatrix coef = CoefMatrix.getInstance();

        // ubah matriks 4x4 menjadi matriks 16x1
        Matrix flattedMatrix = new Matrix(16, 1);
        int idx = 0;

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                flattedMatrix.getMatrix()[idx++][0] = matrix.getMatrix()[i][j];
            }
        }

        // hasil perkalian dengan koefisien bicubic
        Matrix flatConstant = coef.inverse.multiplyMatrix(flattedMatrix);

        // ubah hasil perkalian menjadi bentuk 4x4
        this.constant = new Matrix(4, 4);

        int rowIdx = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                this.constant.getMatrix()[i][j] = flatConstant.getMatrix()[rowIdx++][0];
            }
        }
    }

    /*
     * Lakukan interpolasi berdasarkan input matriks
     * nilai x dan y berada pada range 0.0 dan 1.0
     */
    public double interpolate(double x, double y) {
        double sum = 0.0;

        for (int j = 0; j <= 3; j++) {
            for (int i = 0; i <= 3; i++) {
                sum += this.constant.getMatrix()[i][j] * Math.pow(x, i) * Math.pow(y, j);
            }
        }

        return sum;
    }
}
