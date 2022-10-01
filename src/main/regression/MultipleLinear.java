package main.regression;

import main.SPL.Gauss;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

public class MultipleLinear {
    /* normalEstSystem menyusun MatrixAugmented yang berisi SPL normal estimation for multiple linear regression */
    public static MatrixAugmented normalEstSystem(MatrixAugmented data) {
        Matrix xi = data.getOriginal();
        Matrix yi = data.getAugmentation();

        // pembuatan SPL untuk normal estimation (pencarian B0...Bk)
        int xiCount = data.getOriginal().getColumnCount();      // banyak variabel independen
        int sampleCount = data.getOriginal().getRowCount();     // banyak sampel
        Matrix normalEstLHS = new Matrix(xiCount + 1, xiCount + 1);     // left hand side of normal est. system
        Matrix normalEstRHS = new Matrix(xiCount + 1, 1);             // right hand side of normal est. system

        normalEstLHS.getMatrix()[0][0] = sampleCount;
        for (int i = 1; i < xiCount + 1; i++) {
            for (int j = 0; j < sampleCount; j++) {
                normalEstLHS.getMatrix()[i][0] += xi.getMatrix()[j][i - 1];
                normalEstLHS.getMatrix()[0][i] += xi.getMatrix()[j][i - 1];
            }
        }
        for (int i = 1; i < xiCount + 1; i++) {
            for (int j = 1; j < xiCount + 1; j++) {
                for (int k = 0; k < sampleCount; k++) {
                    normalEstLHS.getMatrix()[i][j] += xi.getMatrix()[k][i - 1] * xi.getMatrix()[k][j - 1];
                }
            }
        }

        for (int i = 0; i < xiCount + 1; i++) {
            for (int j = 0; j < sampleCount; j++) {
                if (i == 0) {
                    normalEstRHS.getMatrix()[i][0] += yi.getMatrix()[j][0];
                } else {
                    normalEstRHS.getMatrix()[i][0] += xi.getMatrix()[j][i - 1] * yi.getMatrix()[j][0];
                }
            }
        }
        return new MatrixAugmented(normalEstLHS, normalEstRHS);
    }

    /* regCoefficients mengembalikan array yang berisi perkiraan nilai koefisien regresi
     * array yang dikembalikan adalah {B0, B1, B2, ..., Bk}
     * hasil diperoleh dengan melakukan metode eliminasi gauss terhadap SPL normal estimation*/
    public static double[] regCoefficients(MatrixAugmented data) {
        double[] result;
        try {
            result = Gauss.solve(normalEstSystem(data), true).transpose().getMatrix()[0];
        } catch (Exception e)
        {
            result = new double[]{0};
            // guarantee to success
            e.getMessage();
        }

        return result;
    }

    /*prosedur predict mencetak rumus fungsi regresi linear dari data dan mencetak nilai prediksi
     * bValues adalah array nilai-nilai koefisien regresi
     * xValues adalah array nilai-nilai xk yang akan ditaksir nilai fungsinya
     * rumus : y = B0 + B1.x1 + B2.x2 +...+ Bk.xk
     * */
    public static double predict(double[] bValues, double[] xValues) {
        double prediction = bValues[0];
        for (int i = 0; i < xValues.length; i++) {
            prediction += bValues[i + 1] * xValues[i];
        }
        return prediction;
    }

    public static String buildEquation(double[] bValues) {
        StringBuilder temp = new StringBuilder();
        // mencetak fungsi regresi linear
        temp.append("f(x) = " + bValues[0]);
        for (int i = 1; i < bValues.length; i++) {
            if (bValues[i] != 0) {     // hanya mencetak apabila koefisien tidak nol
                if (bValues[i] < 0) {
                    temp.append(" - " + (-1 * bValues[i]) + "x" + i);
                } else {
                    temp.append(" + " + bValues[i] + "x" + i);
                }
            }
        }

        return temp.toString();
    }
}
