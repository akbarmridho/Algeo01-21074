package matrix;

public class Matrix {
    protected double[][] mat;
    protected int row;
    protected int col;

    Matrix(int row, int col, double[][] contents) {
        this.mat = new double[row][col];
        this.row = row;
        this.col = col;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.mat[i][j] = contents[i][j];
            }
        }
    }

    public int getRowCount() {
        return this.row;
    }

    public int getColumnCount() {
        return this.col;
    }

    public double[][] getMatrix() {
        return this.mat;
    }

    public Matrix multiplyMatrix(Matrix other) {
        //
    }

    public Matrix multiplyCoef(double multiplier) {
        //
    }

    public Matrix addMatrix(Matrix other) {
        //
    }

    public Matrix subMatrix(Matrix other) {
        //
    }

    public Matrix addCoef(double val) {
        //
    }

    public void swapRow(int row1, int row2) {
        //
    }

    public void addRow(int rowTarget, int rowRef, int multipiler) {
        //
    }

    public void multiplyRow(int row, int multipiler) {
        //
    }

    public void transpose() {
        //
    }
}
