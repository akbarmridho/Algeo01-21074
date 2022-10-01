import main.SPL.Gauss;
import main.SPL.GaussJordan;
import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GaussJordanTest {
    @Test
    public void GaussFirstExample() {
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

        assertThrows(NoSolutionException.class, () -> Gauss.solve(mat, false));
        assertThrows(NoSolutionException.class, () -> GaussJordan.solve(mat, false));
    }

    @Test
    public void GaussSecondExample() {
        double[][] matrixAContent = {
                {1, 1, 1},
                {2, 1, 0},
                {1, 1, 3}
        };

        double[][] matrixBContent = {
                {6}, {8}, {8}
        };

        double[] expectedResult = {
                3.0, 2.0, 1.0
        };

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixB = new Matrix(matrixBContent);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

        try {
            double[] result1 = Gauss.solve(mat, false).transpose().getMatrix()[0];
            double[] result2 = GaussJordan.solve(mat, false).transpose().getMatrix()[0];

            assertArrayEquals(expectedResult, result1);
            assertArrayEquals(expectedResult, result2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void GaussThirdExample() {
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

        assertThrows(NoSolutionException.class, () -> Gauss.solve(mat, false));
        assertThrows(NoSolutionException.class, () -> GaussJordan.solve(mat, false));
    }

    @Test
    public void GaussFourthExample() {
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

        assertThrows(InfinitySolutionException.class, () -> Gauss.solve(mat, false));
        assertThrows(InfinitySolutionException.class, () -> GaussJordan.solve(mat, false));
    }

    @Test
    public void GaussFifthExample() {
        double[][] matrixAContent = {
                {1, 1, 1, 0},
                {2, 1, 0, 0},
                {1, 1, 3, 0}
        };

        double[][] matrixBContent = {
                {6}, {8}, {8}
        };

        double[] expectedResult = {
                3.0, 2.0, 1.0, 0.0
        };

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixB = new Matrix(matrixBContent);

        MatrixAugmented mat = new MatrixAugmented(matrixA, matrixB);

        try {
            double[] result1 = Gauss.solve(mat, false).transpose().getMatrix()[0];
            double[] result2 = GaussJordan.solve(mat, false).transpose().getMatrix()[0];

            assertArrayEquals(expectedResult, result1);
            assertArrayEquals(expectedResult, result2);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
