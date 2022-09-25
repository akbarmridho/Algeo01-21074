import main.matrix.Matrix;
import main.matrix.MatrixAlternative;
import main.matrix.errors.NotMatrixSquareException;
import main.matrix.errors.ZeroDeterminantException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    static final double tolerance = Math.pow(2, -23);
    @Test
    public void MatrixSquare() {
        double[][] matrixContent = {{1, 2, 3}, {4, 5, 6}};
        double[][] matrixSquareContent = {{1, 2}, {4, 5}};

        Matrix notSquare = new Matrix(matrixContent);
        Matrix square = new Matrix(matrixSquareContent);

        assertFalse(notSquare.isSquare());
        assertTrue(square.isSquare());
    }

    @Test
    public void MatrixMultiplication() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixBContent = {{6, 3, -1, 0}, {1, 1, 0, 4}, {-2, 5, 0, 2}};
        Matrix matrixB = new Matrix(matrixBContent);

        double[][] matrixResultContent = {{5, 27, -2, 12}, {-1, 6, 0, 6}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        Matrix result = matrixA.multiplyMatrix(matrixB);
        assertTrue(result.isEqual(matrixResult));
    }

    @Test
    public void MatrixMultiplicationCoef() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixResultContent = {{4, 2, 8}, {0, 2, 2}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        Matrix result = matrixA.multiplyCoef(2);
        assertTrue(result.isEqual(matrixResult));
    }

    @Test
    public void MatrixAddMatrix() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixBContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixB = new Matrix(matrixBContent);

        double[][] matrixResultContent = {{4, 2, 8}, {0, 2, 2}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        Matrix result = matrixA.addMatrix(matrixB);
        assertTrue(result.isEqual(matrixResult));
    }

    @Test
    public void MatrixSubMatrix() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixBContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixB = new Matrix(matrixBContent);

        double[][] matrixResultContent = {{0, 0, 0}, {0, 0, 0}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        Matrix result = matrixA.subMatrix(matrixB);
        assertTrue(result.isEqual(matrixResult));
    }

    @Test
    public void MatrixAddCoef() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixResultContent = {{4, 3, 6}, {2, 3, 3}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        Matrix result = matrixA.addCoef(2);
        assertTrue(result.isEqual(matrixResult));
    }

    @Test
    public void MatrixSwapRow() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixResultContent = {{0, 1, 1}, {2, 1, 4}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        matrixA.swapRow(0, 1);
        assertTrue(matrixA.isEqual(matrixResult));
    }

    @Test
    public void MatrixAddRow() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixResultContent = {{2, 0, 3}, {0, 1, 1}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        matrixA.addRow(0, 1, -1);
        assertTrue(matrixA.isEqual(matrixResult));
    }

    @Test
    public void MatrixMultiplyRow() {
        double[][] matrixAContent = {{2, 1, 4}, {0, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixResultContent = {{1, 0.5, 2}, {0, 1, 1}};
        Matrix matrixResult = new Matrix(matrixResultContent);

        matrixA.multiplyRow(0, 0.5);
        assertTrue(matrixA.isEqual(matrixResult));
    }

    @Test
    public void MatrixTranspose() {
        double[][] matrixAContent = {{1, 2, 3}, {4, 5, 6}};
        Matrix matrixA = new Matrix(matrixAContent);

        double[][] matrixBContent = {{1, 4}, {2, 5}, {3, 6}};
        Matrix matrixB = new Matrix(matrixBContent);

        Matrix result = matrixA.transpose();
        assertTrue(result.isEqual(matrixB));
    }

    @Test
    public void Matrix2x2Determinant() {
        double[][] matrixAContent = {{1, 2}, {3, 4}};
        Matrix matrixA = new Matrix(matrixAContent);
        double determinant = -2;

        try {
            assertEquals(determinant, matrixA.getDeterminant());
            assertEquals(determinant, MatrixAlternative.determinant(matrixA));
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void Matrix3x3Determinant() {
        double[][] matrixAContent = {
                {6, 1, 1},
                {4, -2, 5},
                {2, 8, 7}};
        Matrix matrixA = new Matrix(matrixAContent);
        double determinant = -306;

        try {
            assertEquals(determinant, matrixA.getDeterminant());
            assertTrue(Math.abs(determinant - MatrixAlternative.determinant(matrixA)) < tolerance);
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void Matrix4x4Determinant() {
        double[][] matrixAContent = {
                {4, 3, 2, 2},
                {0, 1, -3, 3},
                {0, -1, 3, 3},
                {0, 3, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);
        double determinant = -240;

        try {
            assertEquals(determinant, matrixA.getDeterminant());
            assertEquals(determinant, MatrixAlternative.determinant(matrixA));
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void Matrix4x4ZeroDeterminant1() {
        // terdapat baris yang nol
        double[][] matrixAContent = {{4, 3, 2, 2}, {0, 1, -3, 3}, {0, 0, 0, 0}, {0, 3, 1, 1}};
        Matrix matrixA = new Matrix(matrixAContent);
        double determinant = 0;

        try {
            assertEquals(determinant, matrixA.getDeterminant());
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void Matrix4x4ZeroDeterminant2() {
        // kolom pertama dan ketiga sama
        double[][] matrixAContent = {{2, 1, 2, 3}, {6, 7, 6, 9}, {0, 6, 0, 0}, {1, 2, 1, 4}};
        Matrix matrixA = new Matrix(matrixAContent);
        double determinant = 0;

        try {
            assertEquals(determinant, matrixA.getDeterminant());
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void Matrix4x4ZeroDeterminant3() {
        // baris ketiga merupakan kelipatan baris kedua
        double[][] matrixAContent = {{1, 2, 3, 4}, {2, 5, 7, 3}, {4, 10, 14, 6}, {3, 4, 2, 7}};
        Matrix matrixA = new Matrix(matrixAContent);
        double determinant = 0;

        try {
            assertEquals(determinant, matrixA.getDeterminant());
        } catch (Exception error) {
            fail(error.getMessage());
        }
    }

    @Test
    public void MatrixNotSquareInverse() {
        double[][] matrixContent = {{1, 2, 3}, {4, 5, 6}};

        Matrix notSquare = new Matrix(matrixContent);
        assertThrows(NotMatrixSquareException.class, notSquare::getInverse);
    }

    @Test
    public void Matrix2x2ZeroDeterminantInverse() {
        double[][] matrixContent = {{1, 1}, {1, 1}};

        Matrix matrix = new Matrix(matrixContent);

        assertThrows(ZeroDeterminantException.class, matrix::getInverse);
    }

    @Test
    public void Matrix3x3ZeroDeterminantInverse() {
        double[][] matrixContent = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

        Matrix matrix = new Matrix(matrixContent);

        assertThrows(ZeroDeterminantException.class, matrix::getInverse);
    }

    @Test
    public void MatrixInverse2x2() {
        double[][] matrixAContent = {{4, 7}, {2, 6}};
        double[][] matrixInverseContent = {{0.6, -0.7}, {-0.2, 0.4}};

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixInverse = new Matrix(matrixInverseContent);

        try {
            Matrix result = matrixA.getInverse();
            assertTrue(result.isEqual(matrixInverse));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void MatrixInverse3x3() {
        double[][] matrixAContent = {{1, 2, 3}, {0, 1, 4}, {5, 6, 0}};
        double[][] matrixInverseContent = {{-24, 18, 5}, {20, -15, -4}, {-5, 4, 1}};

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixInverse = new Matrix(matrixInverseContent);

        try {
            Matrix result = matrixA.getInverse();
            assertTrue(result.isEqual(matrixInverse));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void MatrixInverse4x4() {
        double[][] matrixAContent = {{1, 1, 1, -1}, {1, 1, -1, 1}, {1, -1, 1, 1}, {-1, 1, 1, 1}};
        double[][] matrixInverseContent = {{0.25, 0.25, 0.25, -0.25}, {0.25, 0.25, -0.25, 0.25}, {0.25, -0.25, 0.25, 0.25}, {-0.25, 0.25, 0.25, 0.25}};

        Matrix matrixA = new Matrix(matrixAContent);
        Matrix matrixInverse = new Matrix(matrixInverseContent);

        try {
            Matrix result = matrixA.getInverse();
            assertTrue(result.isEqual(matrixInverse));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
