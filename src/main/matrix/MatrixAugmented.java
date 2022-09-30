package main.matrix;

import java.util.ArrayList;

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
    public MatrixAugmented(double[][] contents)
    {
        double[][] original = new double[contents.length][contents[0].length-1];
        double[][] augmentation = new double[contents.length][1];

        for (int i = 0; i < contents.length; i++)
        {
            for (int j = 0 ; j < contents[i].length-1; j++)
            {
                original[i][j] = contents[i][j];
            }

            augmentation[i][0] = contents[i][contents[i].length-1];
        }

        this.matOriginal = new Matrix(original);
        this.matAugmentation = new Matrix(augmentation);
    }

    public MatrixAugmented copy()
    {
        return new MatrixAugmented(this.matOriginal.copy(), this.matAugmentation.copy());
    }

    public Matrix getOriginal() {
        return this.matOriginal;
    }

    public Matrix getAugmentation() {
        return this.matAugmentation;
    }

    public int getRowCount() {
        return this.matOriginal.getRowCount();
    }

    public int getColCount() {
        return this.matAugmentation.getColumnCount() + this.matOriginal.getColumnCount();
    }

    public int getColMaxIndex(int col, int fromRow, int toRow)
    {
        return this.matOriginal.getColMaxIndex(col, fromRow, toRow);
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

    public void deleteRow(Integer[] rows) {
        this.matOriginal.deleteRows(rows);
        this.matAugmentation.deleteRows(rows);
    }

    public void trimEquation() {
        ArrayList<Integer> idxs = new ArrayList<Integer>();
        if(this.getRowCount() > this.getOriginal().getColumnCount()) {
            for (int i = this.getOriginal().getColumnCount(); i < this.getRowCount(); i++){
                idxs.add(i);
            }
            this.deleteRow(idxs.toArray(new Integer[idxs.size()]));
        }
    }
}
