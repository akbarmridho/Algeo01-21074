import main.SPL.Inverse;
import main.SPL.errors.InfinitySolutionException;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.matrix.errors.NotMatrixSquareException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InversSPLTest {
    @Test
    public void InverseFirst() {
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

        assertThrows(InfinitySolutionException.class, () -> Inverse.solve(mat));
    }

    @Test
    public void InverseSecond() {
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
            double[] result = Inverse.solve(mat);
            assertArrayEquals(expectedResult, result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void InverseThird() {
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

        assertThrows(NotMatrixSquareException.class, () -> Inverse.solve(mat));
    }

    @Test
    public void InverseFourth() {
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

        assertThrows(NotMatrixSquareException.class, () -> Inverse.solve(mat));
    }

    @Test
    public void InverseFifth() {
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
            double[] result = Inverse.solve(mat);
            assertArrayEquals(expectedResult, result);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
