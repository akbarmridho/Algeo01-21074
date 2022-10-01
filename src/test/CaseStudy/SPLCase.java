package CaseStudy;

import main.SPL.GaussJordan;
import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.matrix.errors.NotMatrixSquareException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SPLCase {
    public static void main(String[] args) {
//        case1a();
//        case1b();
        case1c();
//        case1d();
//        case2a();
//        case2b();
//        case3a();
//        case3b();
    }

    public static void case1a() {
        double[][] matrixAContent = {
                {1, 1, -1, -1},
                {2, 5, -7, -5},
                {2, -1, 1, 3},
                {5, 2, -4, 2}
        };

        double[][] matrixBContent = {
                {1}, {-2}, {4}, {6}
        };

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixB = new Matrix(matrixBContent);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);
        writeEquation(mat);
        solve(mat);
    }

    public static void case1b() {
        double[][] matrixAContent = {
                {1, -1, 0, 0, 1},
                {1, 1, 0, -3, 0},
                {2, -1, 0, 1, -1},
                {-1, 2, 0, -2, -1}
        };

        double[][] matrixBContent = {
                {3}, {6}, {5}, {-1}
        };

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixB = new Matrix(matrixBContent);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

        writeEquation(mat);
        solve(mat);
    }

    public static void case1c() {
        double[][] matrixAContent = {
                {0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0},
                {0, 1, 0, 0, 0, 1}
        };

        double[][] matrixBContent = {
                {2}, {-1}, {1}
        };

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixB = new Matrix(matrixBContent);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

        writeEquation(mat);
        solve(mat);
    }

    public static void case1d() {
        for (int n : new int[]{6, 10}) {
            double[][] contentsA = new double[n][n];
            double[][] contentsB = new double[n][1];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    contentsA[i][j] = (1d) / (i + j + 1);
                }

                if (i == 0) {
                    contentsB[i][0] = 1;
                } else {
                    contentsB[i][0] = 0;
                }
            }

            Matrix matrixA = new Matrix(contentsA);
            Matrix matrixB = new Matrix(contentsB);

            MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

            try {
                GaussJordan.solve(mat);
            } catch (InfinitySolutionException | NotMatrixSquareException | NoSolutionException e) {

            }
        }

    }

    public static void case2a() {
        double[][] contents = {
                {1, -1, 2, -1, -1},
                {2, 1, -2, -2, -2},
                {-1, 2, -4, 1, 1},
                {3, 0, 0, -3, -3}
        };

        MatrixAugmented mat = new MatrixAugmented(contents);

        try {
            GaussJordan.solve(mat);
        } catch (InfinitySolutionException | NotMatrixSquareException | NoSolutionException e) {

        }
    }

    public static void case2b() {
        double[][] contents = {
                {2, 0, 8, 0, 8},
                {0, 1, 0, 4, 6},
                {-4, 0, 6, 0, 6},
                {0, -2, 0, 3, -1},
                {2, 0, -4, 0, -4},
                {0, 1, 0, -2, 0}
        };

        MatrixAugmented mat = new MatrixAugmented(contents);

        try {
            GaussJordan.solve(mat);
        } catch (InfinitySolutionException | NotMatrixSquareException | NoSolutionException e) {

        }
    }

    public static void case3a() {
        double[][] contentsA = {
                {8, 1, 3, 2},
                {2, 9, -1, -2},
                {1, 3, 2, -1},
                {1, 0, 6, 4}
        };

        double[][] contentsB = {
                {0}, {1}, {2}, {3}
        };

        Matrix matrixA = new Matrix(contentsA);
        Matrix matrixB = new Matrix(contentsB);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

        try {
            GaussJordan.solve(mat);
        } catch (InfinitySolutionException | NotMatrixSquareException | NoSolutionException e) {

        }
    }

    public static void case3b() {
        double[][] contentsA = {
                {0, 0, 0, 0, 0, 0, 1, 1, 1},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0.04289, 0, 0.04289, 0.75, 0.04289, 0.75, 0.61396},
                {0, 0.25, 0.91421, 0.25, 0.91421, 0.25, 0.91421, 0.25, 0},
                {0.61396, 0.75, 0.04289, 0.75, 0.04289, 0, 0.04289, 0, 0.61396},
                {0, 0, 1, 0, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1, 0, 0, 1, 0},
                {1, 0, 0, 1, 0, 0, 1, 0, 0},
                {0.04289, 0.75, 0.61396, 0, 0.04289, 0.75, 0, 0, 0.04289},
                {0.91421, 0.25, 0, 0.25, 0.91421, 0.25, 0, 0.25, 0.91421},
                {0.04289, 0, 0, 0.75, 0.04289, 0, 0.61396, 0.75, 0.04289}
        };

        double[][] contentsB = {
                {13}, {15}, {8}, {14.79}, {14.31}, {3.81}, {18.00}, {12}, {6}, {10.51}, {16.13}, {7.04}
        };

        Matrix matrixA = new Matrix(contentsA);
        Matrix matrixB = new Matrix(contentsB);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

        try {
            GaussJordan.solve(mat);
        } catch (InfinitySolutionException | NotMatrixSquareException | NoSolutionException e) {

        }
    }

    public static void writeEquation(MatrixAugmented mat) {
        for (int i = 0; i < mat.getRowCount(); i++) {
            for (int j = 0; j < mat.getOriginal().getColumnCount(); j++) {
                System.out.print(String.format("%.2f", Math.abs(mat.getOriginal().getMatrix()[i][j])) + "x" + Integer.toString(j + 1));

                if (j < mat.getOriginal().getColumnCount() - 1) {
                    if (mat.getOriginal().getMatrix()[i][j+1] < 0) {
                        System.out.print(" - ");
                    } else {
                        System.out.print(" + ");
                    }
                } else {
                    System.out.print(" = ");
                }
            }

            System.out.print(String.format("%.2f", mat.getAugmentation().getMatrix()[i][0]) + "\n");
        }
    }

    public static void solve(MatrixAugmented mat) {
        try {
            Integer[] removedIdx = Transformers.transformParametric(mat);

            Matrix result = GaussJordan.solve(mat);
            double[] resultFill = Transformers.fillResultWithZero(Transformers.singleColMatToArr(result), removedIdx);

            for (int i = 0; i < result.getRowCount(); i++) {
                System.out.print("x" + Integer.toString(i + 1) + " = " + String.format("%.2f", resultFill[i]) + "\n");
            }

        } catch (InfinitySolutionException | NotMatrixSquareException | NoSolutionException e) {
            System.out.println(e.getMessage());
        }
    }

}
