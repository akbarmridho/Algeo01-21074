package main.matrix;

import main.matrix.errors.NotMatrixSquareException;
import main.matrix.errors.ZeroDeterminantException;

import java.util.Arrays;

public class Matrix {
    protected double[][] mat;
    protected int row;
    protected int col;

    static final double tolerance = 0.0000001;

    /*
     * Konstruktor:
     * banyaknya baris
     * banyaknya kolom
     * isi konten
     */
    public Matrix(int row, int col, double[][] contents) {
        this.mat = new double[row][col];
        this.row = row;
        this.col = col;

        for (int i = 0; i < row; i++) {
            System.arraycopy(contents[i], 0, this.mat[i], 0, col);
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

    public Matrix copy()
    {
        double[][] contents = new double[this.row][this.col];

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.mat[i], 0, contents[i], 0, this.col);
        }

        return new Matrix(this.row, this.col, contents);
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

    public void deleteRows(Integer[] rows) {
        int newRow = this.row - rows.length;
        double[][] contents = new double[newRow][this.col];

        int rowCount = 0;

        for (int i = 0; i < newRow; i++) {
            int finalI = i;
            if (Arrays.stream(rows).anyMatch(x -> x == finalI)) {
                continue;
            }

            for (int j = 0; j < this.col; j++) {
                contents[rowCount][j] = this.mat[i][j];
            }

            rowCount++;
        }

        this.mat = contents;
        this.row = newRow;
    }

    public void deleteCols(Integer[] cols) {
        int newCol = this.col - cols.length;
        double[][] contents = new double[this.row][newCol];

        for (int i = 0; i < this.row; i++) {

            int colCount = 0;

            for (int j = 0; j < this.col; j++) {
                int finalJ = j;
                if (!Arrays.stream(cols).anyMatch(x -> x == finalJ)) {
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

    public boolean isEqual(Matrix other) {
        //Declaration
        boolean equal = (this.col == other.col) && (this.row == other.row);
        //size A != size B -> not equal
        int i = 0;

        //compare each element
        while (equal && (i < this.row)) {
            int j = 0; //index

            while (equal && (j < this.col)) {
                equal = Math.abs(this.mat[i][j] - other.mat[i][j]) < tolerance ; // Aij != Bij -> stop search
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
            if (i == entryRow) {
                continue;
            } else {
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
        Matrix minor = new Matrix(matOrder - 1, matOrder - 1, minorVal);
        return minor;
    }

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
            double det = 0;
            int sign = 1;
            for (int j = 0; j < matrixOrder; j++) {
                det += sign * this.mat[0][j] * (this.getMinor(0, j)).getDeterminant();
                sign = -sign;
            }
            return det;
        }
    }
// EXPERIMENTAL : pencarian nol terbanyak pada baris/kolom untuk perhitungan determinan
//        else{
//
//            // mencari baris atau kolom dengan nol terbanyak
//            int[][] zeroCount = new int[2][matrixOrder];
//            int colZeroes, rowZeroes;
//            for (int i=0; i<matrixOrder; i++){
//                colZeroes=0;
//                rowZeroes=0;
//                for (int j=0; j<matrixOrder; j++){
//                    if (this.mat[i][j]==0){
//                        rowZeroes++;
//                    }
//                    if (this.mat[j][i]==0){
//                        colZeroes++;
//                    }
//                }
//                zeroCount[0][i]=rowZeroes;
//                zeroCount[1][i]=colZeroes;
//            }
//            // mencari maxima (indeks baris atau kolom dengan '0' terbanyak)
//            int rowMaxIdx=zeroCount[0][0];
//            int colMaxIdx=zeroCount[1][0];
//            for (int i=0; i<matrixOrder; i++) {
//                if (zeroCount[0][i]>rowMaxIdx){
//                    rowMaxIdx = i;
//                }
//                if (zeroCount[1][i]>colMaxIdx){
//                    colMaxIdx = i;
//                }
//            }
//
//            // percabangan : cek apakah ada baris atau kolom yang seluruh elemennya bernilai nol
//            // jika ada, determinan = 0
//            double det=0;
//            if (zeroCount[0][rowMaxIdx] == matrixOrder || zeroCount[1][colMaxIdx] == matrixOrder){
//                return det;
//            }
//            // jika tidak, lanjutkan perhitungan determinan
//            else{
//                double sign;
//                // percabangan: menentukan untuk menggunakan baris atau kolom dengan nol terbanyak
//                if (zeroCount[0][rowMaxIdx] >= zeroCount[1][colMaxIdx]){    // baris "maxima" memiliki lebih banyak '0' daripada kolom "maxima"
//                    sign = Math.pow(-1, rowMaxIdx);
//                    for (int j=0; j<matrixOrder; j++){
//                        if (this.mat[rowMaxIdx][j]==0) {    // tidak memproses cofactor apabila elemen pada baris bernilai 0
//                            continue;
//                        }
//                        else{
//                            det += this.mat[rowMaxIdx][j] * sign * (this.getMinor(rowMaxIdx, j)).getDeterminant();
//                        }
//                    }
//                }
//                else{   // kolom "maxima" memiliki lebih banyak nilai '0'
//                    sign = Math.pow(-1, colMaxIdx);
//                    for (int i=0; i<matrixOrder; i++){
//                        if (this.mat[i][colMaxIdx]==0) {    // tidak memproses cofactor apabila elemen pada kolom bernilai 0
//                            continue;
//                        }
//                        else{
//                            det += this.mat[i][colMaxIdx] * sign * (this.getMinor(i, colMaxIdx)).getDeterminant();
//                        }
//                    }
//                }
//
//                return det;
//            }
//        }


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
        Matrix cofactorsMat = new Matrix(order, order, cofactorsVal);
        Matrix adj = cofactorsMat.transpose();
        return adj;
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
        Matrix inv = adj.multiplyCoef(1 / det);
        return inv;
    }
}
