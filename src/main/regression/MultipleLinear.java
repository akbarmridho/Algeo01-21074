package main.regression;

import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.matrix.errors.NotMatrixSquareException;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.SPL.Gauss;

import java.util.Arrays;

public class MultipleLinear {
    /* normalEstSystem menyusun MatrixAugmented yang berisi SPL normal estimation for multiple linear regression */
    public static MatrixAugmented normalEstSystem(MatrixAugmented data){
        Matrix xi = data.getOriginal();
        Matrix yi = data.getAugmentation();
        
        // pembuatan SPL untuk normal estimation (pencarian B0...Bk)
        int xiCount = data.getOriginal().getColumnCount();      // banyak variabel independen
        int sampleCount = data.getOriginal().getRowCount();     // banyak sampel
        Matrix normalEstLHS = new Matrix(xiCount+1, xiCount+1);     // left hand side of normal est. system
        Matrix normalEstRHS = new Matrix(xiCount+1, 1);             // right hand side of normal est. system
        
        normalEstLHS.getMatrix()[0][0] = sampleCount;
        for (int i = 1; i < xiCount+1; i++) {
            for (int j = 0; j < sampleCount; j++) {
                normalEstLHS.getMatrix()[i][0] += xi.getMatrix()[j][i-1];
                normalEstLHS.getMatrix()[0][i] += xi.getMatrix()[j][i-1];
            }
        }
        for (int i = 1; i < xiCount+1; i++) {
            for (int j = 1; j < xiCount+1; j++) {
                for (int k = 0; k < sampleCount; k++) {
                    normalEstLHS.getMatrix()[i][j] += xi.getMatrix()[k][i-1] * xi.getMatrix()[k][j-1];
                }
            }
        }

        for (int i = 0; i < xiCount+1; i++) {
            for (int j = 0; j < sampleCount; j++) {
                if(i==0){
                    normalEstRHS.getMatrix()[i][0] += yi.getMatrix()[j][0];
                }
                else{
                    normalEstRHS.getMatrix()[i][0] += xi.getMatrix()[j][i-1] * yi.getMatrix()[j][0];
                }
            }
        }
        return new MatrixAugmented(normalEstLHS,normalEstRHS);
    }

    /* regCoefficients mengembalikan array yang berisi perkiraan nilai koefisien regresi
    * array yang dikembalikan adalah {B0, B1, B2, ..., Bk}
    * hasil diperoleh dengan melakukan metode eliminasi gauss terhadap SPL normal estimation*/
    public static double[] regCoefficients(MatrixAugmented data) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
        return Gauss.solve(normalEstSystem(data)).transpose().getMatrix()[0];
    }

    /*prosedur predict mencetak rumus fungsi regresi linear dari data dan mencetak nilai prediksi
    * bValues adalah array nilai-nilai koefisien regresi
    * xValues adalah array nilai-nilai xk yang akan ditaksir nilai fungsinya
    * rumus : y = B0 + B1.x1 + B2.x2 +...+ Bk.xk
    * */
    public static void predict(double[] bValues, double[] xValues){
        // mencetak fungsi regresi linear
        System.out.print("f(x) = " + bValues[0]);
        for (int i = 1; i < bValues.length; i++) {
            if (bValues[i]!=0){     // hanya mencetak apabila koefisien tidak nol
                if (bValues[i]<0) {
                    System.out.print(" - " + (-1*bValues[i]) + "x" + i);
                }
                else {
                    System.out.print(" + " + bValues[i] + "x" + i);
                }
            }
        }
        System.out.print(", ");

        // mencetak hasil prediksi (regresi linear)
        double prediction = bValues[0];
        for (int i = 0; i < xValues.length; i++) {
            prediction += bValues[i+1] * xValues[i];
        }
        System.out.print("f(xk) = " + prediction + "\n");
    };

    /* Demonstrasi
    public static void main(String[] args) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
        // berikut adalah data
        double[][] xi = {
                {72.4, 76.3, 29.18},
                {41.6, 70.3, 29.35},
                {34.3, 77.1, 29.24},
                {35.1, 68, 29.27},
                {10.7, 79, 29.78},
                {12.9, 67.4, 29.39},
                {8.3, 66.8, 29.69},
                {20.1, 76.9, 29.48},
                {72.2, 77.7, 29.09},
                {24, 67.7, 29.6},
                {23.2, 76.8, 29.38},
                {47.4, 86.6, 29.35},
                {31.5, 76.9, 29.63},
                {10.6, 86.3, 29.56},
                {11.2, 86, 29.48},
                {73.3, 76.3, 29.4},
                {75.4, 77.9, 29.28},
                {96.6, 78.7, 29.29},
                {107.4, 86.8, 29.03},
                {54.9, 70.9, 29.37}
        };
        double[][] yi = {
                {0.9},
                {0.91},
                {0.96},
                {0.89},
                {1},
                {1.1},
                {1.15},
                {1.03},
                {0.77},
                {1.07},
                {1.07},
                {0.94},
                {1.1},
                {1.1},
                {1.1},
                {0.91},
                {0.87},
                {0.78},
                {0.82},
                {0.95}
        };
        MatrixAugmented data = new MatrixAugmented(new Matrix(20, 3, xi), new Matrix(20,1,yi));

        // demonstrasi normalEstSystem
        MatrixAugmented normal = normalEstSystem(data);
        System.out.println("SPL estimasi normal");
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(normal.getOriginal().getMatrix()[i]) + " " + Arrays.toString(normal.getAugmentation().getMatrix()[i]));
        }

        // demonstrasi pencarian koef regresi
        System.out.println();
        double[] bVal = regCoefficients(data);
        System.out.println("koefisien regresi: " + Arrays.toString(bVal));

        // demonstrasi prediksi regresi
        double[] xVal1 = {2,3,4};
        double[] xVal2 = {0,0,0};
        double[] xVal3 = {1,0,1};
        predict(bVal, xVal1);
        predict(bVal, xVal2);
        predict(bVal, xVal3);
    }
     */
}
