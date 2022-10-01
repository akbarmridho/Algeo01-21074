package main.matrix;

import java.util.ArrayList;

import main.SPL.errors.NoSolutionException;

public class MatrixAugmented {
    protected Matrix matOriginal;
    protected Matrix matAugmentation;

    public MatrixAugmented(Matrix original, Matrix augmentation) {
        this.matOriginal = original;
        this.matAugmentation = augmentation;
    }

    /*
     * Buat matrix augmented dari array of arraf of double
     * yang menjadi augmentasi adalah kolom paling kanan
     */
    public MatrixAugmented(double[][] contents) {
        double[][] original = new double[contents.length][contents[0].length - 1];
        double[][] augmentation = new double[contents.length][1];

        for (int i = 0; i < contents.length; i++) {
            if (contents[i].length - 1 >= 0) System.arraycopy(contents[i], 0, original[i], 0, contents[i].length - 1);

            augmentation[i][0] = contents[i][contents[i].length - 1];
        }

        this.matOriginal = new Matrix(original);
        this.matAugmentation = new Matrix(augmentation);
    }

    /*
     * Buat salinan matriks augmented
     */
    public MatrixAugmented copy() {
        return new MatrixAugmented(this.matOriginal.copy(), this.matAugmentation.copy());
    }

    /*
     * Mengembalikan matriks kiri dari matrix augmented
     */
    public Matrix getOriginal() {
        return this.matOriginal;
    }

    /*
     * Mengembalikan matriks kanan dari matrix augmented
     */
    public Matrix getAugmentation() {
        return this.matAugmentation;
    }

    /*
     * Mengembalikan banyakna kolom suatu matrix augmented
     */
    public int getRowCount() {
        return this.matOriginal.getRowCount();
    }

    /*
     * Mengembalikan indeks dari keberadaan nilai absolute makimal dari suatu baris ke baris tertentu
     * pada suatu kolom (digunakan untuk operasi gauss)
     */
    public int getColAbsMaxIndex(int col, int fromRow, int toRow) {
        return this.matOriginal.getColAbsMaxIndex(col, fromRow, toRow);
    }

    /*
     * Menukar dua baris
     */
    public void swapRow(int row1, int row2) {
        this.matOriginal.swapRow(row1, row2);
        this.matAugmentation.swapRow(row1, row2);
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
        this.matOriginal.addRow(rowTarget, rowRef, multipiler);
        this.matAugmentation.addRow(rowTarget, rowRef, multipiler);
    }

    /*
     * Melakukan perkalian suatu baris dengan suatu multiplier
     */
    public void multiplyRow(int row, double multipiler) {
        this.matOriginal.multiplyRow(row, multipiler);
        this.matAugmentation.multiplyRow(row, multipiler);
    }

    /*
     * Hapus beberapa baris sekaligus
     * input adalah indeks dari baris yang ingin dihapus
     */
    public void deleteRow(Integer[] rows) {
        this.matOriginal.deleteRows(rows);
        this.matAugmentation.deleteRows(rows);
    }

    /*
     * Untuk metode gauss
     * hapus beberapa kolom apabila:
     * 1. Banyaknya persamaan melebihi dari banyaknya persamaaan yang dibutuhkan
     * 2. Banyaknya persamaan sesuai, tetapi terdapat baris yang nilainya semua nol
     */
    public void trimEquation() throws NoSolutionException {
        ArrayList<Integer> idxs = new ArrayList<>();

        // Jika banyaknya baris lebih banyak daripada banyaknya kolom pada matrix original
        // pada operasi gauss, ini berarti terdapat lebih banyak persamaan daripada yang dibutuhkan
        if (this.getRowCount() > this.getOriginal().getColumnCount()) {
            for (int i = this.getOriginal().getColumnCount(); i < this.getRowCount(); i++) {
                idxs.add(i);
            }

        } else {
            // periksa apakah ada baris yang nilainya semua nol
            for (int i = 0; i < this.getRowCount(); i++) {

                // periksa jika baris semua nol
                boolean isZeroRow = true;
                for (int j = 0; j < this.getOriginal().getColumnCount() & isZeroRow; j++) {
                    if (this.getOriginal().getMatrix()[i][j] != 0) {
                        isZeroRow = false;
                    }
                }

                // baris nol tetapi nilai pada augmentation tidak nol
                // tidak ada solusi
                if (isZeroRow && this.getAugmentation().getMatrix()[i][0] != 0) {
                    throw new NoSolutionException("Tidak terdapat solusi SPL karena LHS 0 menghasilkan RHS tidak 0.");
                }

                // jika baris nol, tambahkan ke ids untuk dihapus
                if (isZeroRow) {
                    idxs.add(i);
                }
            }
        }

        if (idxs.size() > 0) {
            this.deleteRow(idxs.toArray(new Integer[0]));
        }
    }
}
