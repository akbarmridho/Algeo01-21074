package matrix;

public class MatrixAugmented {
    protected Matrix matOriginal;
    protected Matrix matAugmentation;

    MatrixAugmented(Matrix original, Matrix augmentation) {
        this.matOriginal = original;
        this.matAugmentation = augmentation;
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
}
