package main.matrix;

public class Matrix {
    protected double[][] mat;
    protected int row;
    protected int col;

    /*
     * Konstruktor:
     * banyaknya baris
     * banyaknya kolom
     * isi konten
     */
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

    //Empty constructor
    Matrix(int row, int col) {
        this.mat = new double[row][col];
        //a default value of 0.0d for array of double is guranteed
        //source: docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5
        this.row = row;
        this.col = col;
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
        double temp[]; //temporary storage register for swap
        
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
        int j = 0; //index

        //compare each element
        while (equal && (i < this.row)) {
            while (equal && (j < this.row)) {
                equal = this.mat[i][j] == other.mat[i][j]; // Aij != Bij -> stop search
                j++;
            }
            i++;
        }

        //return
        return equal;
    }
}
