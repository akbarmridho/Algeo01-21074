package matrix;

public class MatrixSquare extends Matrix {
    MatrixSquare(int size, double[][] contents) {
        super(size, size, contents);
    }

    /*
     * Menghitung determinan suatu matriks dengan metode minor-kofaktor
     * Fungsi ini fungsi rekursif
     */
    public double getDeterminant() {
        // cek baris/ kolom dengan nol terbanyak dan lakukan perhitungan dari sana
        // cek jika ada baris/ kolom yang full 0, jika ya, langsung kembalikan nol
    }

    /*
     * Memperoleh nilai kofaktor dari suatu matriks (minimal matriks 2x2)
     */
    public double getCofactor(MatrixSquare matrix) {

    }

    /*
     * Mengembalikan adjoint dari suatu matriks
     * Menggunakan metode minor-kofaktor, lalu di-transpose
     * 
     * Berdakan case saat matriks 2x2 dengan matriks ukuran nxn dengan n>=3
     */
    public MatrixSquare getAdjoint() {
        //
    }

    /*
     * Mengembalikan invers matriks menggunakan metode minor-kofaktor
     */
    public MatrixSquare getInverse() {
        //
    }
}
