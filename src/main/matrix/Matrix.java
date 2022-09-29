package main.matrix;

import main.matrix.errors.NotMatrixSquareException;
import main.matrix.errors.ZeroDeterminantException;

import java.util.Arrays;

public class Matrix {
    protected double[][] mat;
    protected int row;
    protected int col;

    static final double tolerance = Math.pow(2, -23);

    /*
     * Konstruktor:
     * isi konten berbentuk array of array of double
     */
    public Matrix(double[][] contents) {
        this.row = contents.length;
        this.col = contents[0].length;
        this.mat = new double[this.row][this.col];

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(contents[i], 0, this.mat[i], 0, this.col);
        }
    }

    //Empty constructor
    public Matrix(int row, int col) {
        this.mat = new double[row][col];
        //a default value of 0.0d for array of double is guranteed
        //source: docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5
        this.row = row;
        this.col = col;
    }

    /*
     * Membuat salinan class matrix baru
     * isi konten tidak perlu disalin karena konstruktor otomatis menyalin parameter ke dalam array baru
     */
    public Matrix copy() {
        return new Matrix(this.mat);
    }

    public void assign(Matrix other) {
        this.mat = other.mat;
        this.col = other.col;
        this.row = other.row;
    }

    /*
     * Mengembalikan banyaknya baris dalam suatu matriks
     */
    public int getRowCount() {
        return this.row;
    }

    /*
     * Mengembalikan banyaknya kolom dalam suatu matriks
     */
    public int getColumnCount() {
        return this.col;
    }

    /*
     * Mengembalikan matriks yang berupa array of array of double
     */
    public double[][] getMatrix() {
        return this.mat;
    }

    /*
     * Check if the matrix is matrix square
     */
    public boolean isSquare() {
        return this.getRowCount() == this.getColumnCount();
    }

    /*
     * Melakukan perkalian matriks dengan matriks.
     * Mengembalikan hasil perkalian matriks yang berupa matriks
     */
    public Matrix multiplyMatrix(Matrix other) {
        //Declaration
        Matrix result = new Matrix(this.row, other.col);

        //Multiply Matrix
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < other.col; j++) {
                for (int k = 0; k < this.col; k++) {
                    result.mat[i][j] += this.mat[i][k] * other.mat[k][j];
                    // Cij = Ai1 * B1j + ... + Ain * Bnj
                }
            }
        }

        //return
        return result;
    }

    /*
     * Melakukan perkalian matriks dengan koefisien.
     * Mengembalikan hasil perkalian yang berupa matriks
     */
    public Matrix multiplyCoef(double multiplier) {
        //Declaration
        Matrix result = new Matrix(this.row, this.col);

        //multiply constant
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.mat[i][j] = this.mat[i][j] * multiplier;
                //Aij = Aij * S
            }
        }

        //return
        return result;
    }

    /*
     * Melakukan pertambahan matriks dengan matriks
     * Mengembalikan haisl penjumlahan yang berupa matriks
     */
    public Matrix addMatrix(Matrix other) {
        //Declaration
        Matrix result = new Matrix(this.row, this.col);

        //add main.matrix elements
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.mat[i][j] = this.mat[i][j] + other.mat[i][j];
                //Cij = Aij + Bij
            }
        }

        //return
        return result;
    }

    /*
     * Melakukan pengurangan matriks dengan matriks
     * Mengembalikan hasil pengurangan yang berupa matriks
     */
    public Matrix subMatrix(Matrix other) {
        //Declaration
        Matrix result = new Matrix(this.row, this.col);

        //sub main.matrix elements
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.mat[i][j] = this.mat[i][j] - other.mat[i][j];
                //Cij = Aij - Bij
            }
        }

        //return
        return result;
    }

    /*
     * Melakukan penjumlahan matriks dengan koefisien
     * Mengembalikan hasil penjumlahan yang berupa matriks
     */
    public Matrix addCoef(double val) {
        //Declaration
        Matrix result = new Matrix(this.row, this.col);

        //add main.matrix elements with constant
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result.mat[i][j] = this.mat[i][j] + val;
                //Cij = Aij + Constant
            }
        }

        //return
        return result;
    }

    /*
     * Menukar dua baris
     */
    public void swapRow(int row1, int row2) {
        //Declaration
        double[] temp; //temporary storage register for swap

        //swap
        temp = this.mat[row1];
        this.mat[row1] = this.mat[row2];
        this.mat[row2] = temp;

        //return
    }

    /*
     * Melakukan operasi baris elementer berupa penjumlahan
     * rowTarget merupakan baris yang ingin dimanipulasi
     * rowRef merupakan baris yang akan dioperasikan
     * multiplier adalah pengali
     * multiplier = 1 jika rowTarget = rowTarget + rowRef
     * multiplier = -2 jika rowTarget = rowTarget + (-2)*rowRef
     */
    public void addRow(int rowTarget, int rowRef, double multipiler) {
        //Declaration

        //add targeted row with reference row times constant
        for (int i = 0; i < this.col; i++) {
            this.mat[rowTarget][i] += this.mat[rowRef][i] * multipiler;
            //Axi = Axi + Ayi * C
        }

        //return
    }

    /*
     * Melakukan perkalian suatu baris dengan suatu multiplier
     */
    public void multiplyRow(int row, double multipiler) {
        //Declaration

        //multiply row
        for (int i = 0; i < this.col; i++) {
            this.mat[row][i] *= multipiler;
            //Axi = Axi * C
        }

        //return
    }

    /*
     * Menghapus banyak baris sekaligus
     * Parameter adalah array of index baris yang ingin dihapus
     */
    public void deleteRows(Integer[] rows) {
        int newRow = this.row - rows.length;
        double[][] contents = new double[newRow][this.col];

        int rowCount = 0;

        for (int i = 0; i < newRow; i++) {
            int finalI = i;
            if (Arrays.stream(rows).anyMatch(x -> x == finalI)) {
                continue;
            }

            if (this.col >= 0) System.arraycopy(this.mat[i], 0, contents[rowCount], 0, this.col);

            rowCount++;
        }

        this.mat = contents;
        this.row = newRow;
    }

    /*
     * Menghapus banyak kolom sekaligus
     * Parameter adalah array of index kolom yang ingin dihapus
     */
    public void deleteCols(Integer[] cols) {
        int newCol = this.col - cols.length;
        double[][] contents = new double[this.row][newCol];

        for (int i = 0; i < this.row; i++) {

            int colCount = 0;

            for (int j = 0; j < this.col; j++) {
                int finalJ = j;
                if (Arrays.stream(cols).noneMatch(x -> x == finalJ)) {
                    contents[i][colCount] = this.mat[i][j];
                    colCount++;
                }
            }

        }

        this.mat = contents;
        this.col = newCol;
    }

    /*
     * Melakukan transpos matriks
     * Mengembalikan hasil transpose matriks
     */
    public Matrix transpose() {
        //Declaration
        Matrix result = new Matrix(this.col, this.row);
        //Axy^T = Ayx

        //transpose
        for (int i = 0; i < result.row; i++) {
            for (int j = 0; j < result.col; j++) {
                result.mat[i][j] = this.mat[j][i];
                //Tij = Aji
            }
        }

        //return
        return result;
    }

    /*
     * Memeriksa apakah dua buah matriks memiliki konten yang sama
     * (dalam batas toleransi 2^-23 untuk setiap elemennya)
     */
    public boolean isEqual(Matrix other) {
        //Declaration
        boolean equal = (this.col == other.col) && (this.row == other.row);
        //size A != size B -> not equal
        int i = 0;

        //compare each element
        while (equal && (i < this.row)) {
            int j = 0; //index

            while (equal && (j < this.col)) {
                equal = Math.abs(this.mat[i][j] - other.mat[i][j]) < tolerance; // Aij != Bij -> stop search
                j++;
            }
            i++;
        }

        //return
        return equal;
    }

    /*
     * Memperoleh entri minor dari suatu matriks dengan baris dan kolom entri sebagai parameter
     * prekondisi: minimal matriks 2x2
     */
    public Matrix getMinor(int entryRow, int entryCol) {
        // pengisian nilai-nilai elemen dari entri minor pada array matOrder
        int matOrder = this.mat.length;
        double[][] minorVal = new double[matOrder - 1][matOrder - 1];
        int k, l;
        k = 0;
        for (int i = 0; i < matOrder; i++) {
            if (i != entryRow) {
                l = 0;
                for (int j = 0; j < matOrder; j++) {
                    if (j != entryCol) {
                        minorVal[k][l] = this.mat[i][j];
                        l++;
                    }
                }
                k++;
            }

        }
        // pembuatan Matrix minor
        return new Matrix(minorVal);
    }

    /*
     * Mencari nilai determinan suatu matriks menggunakan metode minor-kofaktor
     */
    public double getDeterminant() throws NotMatrixSquareException {
        if (!this.isSquare()) {
            throw new NotMatrixSquareException("Matriks bukan persegi sehingga tidak bisa diperoleh determinan");
        }
        // percabangan : cek ukuran atau orde matriks persegi
        int matrixOrder = this.mat.length;
        if (matrixOrder == 1) {
            return (this.mat[0][0]);
        } else if (matrixOrder == 2) {
            return (this.mat[0][0] * this.mat[1][1] - this.mat[0][1] * this.mat[1][0]);
        } else {
            // lakukan pencarian baris dengan nol terbanyak
            int rowIdx = 0;
            int rowZeroCount = 0;

            for (int i = 0; i < this.getRowCount(); i++) {
                int currentZeroCount = 0;
                for (int j = 0; j < this.getColumnCount(); j++) {
                    if (this.mat[i][j] == 0) {
                        currentZeroCount++;
                    }
                }

                if (rowZeroCount < currentZeroCount) {
                    rowIdx = i;
                    rowZeroCount = currentZeroCount;
                }
            }

            // lakukan pencarian kolom dengan nol terbanyak
            int colIdx = 0;
            int colZeroCount = 0;

            for (int j = 0; j < this.getColumnCount(); j++) {
                int currentZeroCount = 0;
                for (int i = 0; i < this.getRowCount(); i++) {
                    if (this.mat[i][j] == 0) {
                        currentZeroCount++;
                    }
                }

                if (colZeroCount < currentZeroCount) {
                    colIdx = j;
                    colZeroCount = currentZeroCount;
                }
            }

            double det = 0;

            // pilih minor kofaktor dengan nol terbanyak untuk mengurangi banyaknya perhitungan
            // jika nol terbanyak berada pada suatu baris
            if (rowZeroCount > colZeroCount) {
                for (int j = 0; j < matrixOrder; j++) {
                    if (this.mat[rowIdx][j] != 0) {
                        det += Math.pow(-1, rowIdx + j) * this.mat[rowIdx][j] * this.getMinor(rowIdx, j).getDeterminant();
                    }
                }

            } else {
                // jika nol terbanyak berada pada suatu kolom
                for (int i = 0; i < matrixOrder; i++) {
                    if (this.mat[i][colIdx] != 0) {
                        det += Math.pow(-1, colIdx + i) * this.mat[i][colIdx] * this.getMinor(i, colIdx).getDeterminant();
                    }
                }
            }

            return det;
        }
    }

    /*
     * Memperoleh nilai kofaktor dari suatu matriks
     * prekondisi: minimal matriks 2x2
     */
    public double getCofactor(int entryRow, int entryCol) throws NotMatrixSquareException {
        if (!this.isSquare()) {
            throw new NotMatrixSquareException("Matriks bukan persegi sehingga tidak bisa diperoleh determinan");
        }

        double sign = Math.pow(-1, entryRow + entryCol);
        return (sign * (this.getMinor(entryRow, entryCol)).getDeterminant());
    }

    /*
     * Mengembalikan adjoin dari suatu matriks
     * Menggunakan metode minor-kofaktor, lalu di-transpose
     */
    public Matrix getAdjoin() throws NotMatrixSquareException {
        if (!this.isSquare()) {
            throw new NotMatrixSquareException("Matriks bukan persegi sehingga tidak bisa diperoleh adjoin");
        }

        int order = this.mat.length;
        double[][] cofactorsVal = new double[order][order];
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                cofactorsVal[i][j] = this.getCofactor(i, j);
            }
        }
        Matrix cofactorsMat = new Matrix(cofactorsVal);
        return cofactorsMat.transpose();
    }

    /*
     * Mengembalikan invers matriks menggunakan metode minor-kofaktor
     * Prekondisi: determinan tidak 0
     */
    public Matrix getInverse() throws NotMatrixSquareException, ZeroDeterminantException {
        if (!this.isSquare()) {
            throw new NotMatrixSquareException("Matriks bukan persegi sehingga tidak bisa diperoleh invers");
        }

        double det = this.getDeterminant();

        if (det == 0) {
            throw new ZeroDeterminantException("Matriks memiliki determinan nol sehingga tidak bisa diperoleh invers");
        }
        Matrix adj = this.getAdjoin();
        return adj.multiplyCoef(1 / det);
    }

    /*
     * Cari indeks kolom pada range baris dengan nilai maksimum pada kolom tersebut
     */
    public int getColMaxIndex(int col, int fromRow, int toRow) {
        if (toRow <= fromRow) {
            return -1;
        }

        int max = fromRow;

        for (int i = fromRow + 1; i <= toRow; i++) {
            if (this.mat[i][col] > this.mat[max][col]) {
                max = i;
            }
        }

        return max;
    }

    public static Matrix identity(int size) {
        double[][] contents = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    contents[i][j] = 1;
                } else {
                    contents[i][j] = 0;
                }
            }
        }

        return new Matrix(contents);
    }
}
