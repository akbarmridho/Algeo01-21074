package matrix;

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
        //
    }

    /*
     * Melakukan perkalian matriks dengan koefisien.
     * Mengembalikan hasil perkalian yang berupa matriks
     */
    public Matrix multiplyCoef(double multiplier) {
        //
    }

    /*
     * Melakukan pertambahan matriks dengan matriks
     * Mengembalikan haisl penjumlahan yang berupa matriks
     */
    public Matrix addMatrix(Matrix other) {
        //
    }

    /*
     * Melakukan pengurangan matriks dengan matriks
     * Mengembalikan hasil pengurangan yang berupa matriks
     */
    public Matrix subMatrix(Matrix other) {
        //
    }

    /*
     * Melakukan penjumlahan matriks dengan koefisien
     * Mengembalikan hasil penjumlahan yang berupa matriks
     */
    public Matrix addCoef(double val) {
        //
    }

    /*
     * Menukar dua baris
     */
    public void swapRow(int row1, int row2) {
        //
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
        //
    }

    /*
     * Melakukan perkalian suatu baris dengan suatu multiplier
     */
    public void multiplyRow(int row, double multipiler) {
        //
    }

    /*
     * Melakukan transpos matriks
     * Mengembalikan hasil transpose matriks
     */
    public Matrix transpose() {
        //
    }
}
