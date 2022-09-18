package main.matrix;

public class MatrixSquare extends Matrix {
    MatrixSquare(int size, double[][] contents) {
        super(size, size, contents);
    }

    /*
     * Memperoleh entri minor dari suatu matriks dengan baris dan kolom entri sebagai parameter 
     * prekondisi: minimal matriks 2x2
     */
    public MatrixSquare getMinor(int entryRow, int entryCol) {
        // pengisian nilai-nilai elemen dari entri minor pada array matOrder
        int matOrder = this.mat.length;
        double[][] minorVal = new double[matOrder-1][matOrder-1];
        int k, l;
        k=0;
        for (int i = 0; i < matOrder; i++) {
            if (i==entryRow) {
                continue;
            } else {
                l=0;
                for (int j = 0; j < matOrder; j++) {
                    if (j!=entryCol) {
                        minorVal[k][l] = this.mat[i][j];
                        l++;
                    }
                }
                k++;    
            }
            
        }
        // pembuatan MatrixSquare minor 
        MatrixSquare minor = new MatrixSquare(matOrder - 1, minorVal);
        return minor;
    }

    /*
     * Menghitung determinan suatu matriks dengan metode minor-kofaktor
     * Fungsi ini fungsi rekursif
     */
    public double getDeterminant() {
        // percabangan : cek ukuran atau orde matriks persegi
        int matrixOrder = this.mat.length;
        if (matrixOrder == 1) {
            return (this.mat[0][0]);
        }
        else if (matrixOrder == 2){
            return (this.mat[0][0]*this.mat[1][1] - this.mat[0][1]*this.mat[1][0]);
        }
        else{
            // mencari baris atau kolom dengan nol terbanyak
            int[][] zeroCount = new int[2][matrixOrder]; 
            int colZeroes, rowZeroes;
            for (int i=0; i<matrixOrder; i++){
                colZeroes=0;
                rowZeroes=0;
                for (int j=0; j<matrixOrder; j++){
                    if (this.mat[i][j]==0){
                        rowZeroes++;
                    }
                    if (this.mat[j][i]==0){
                        colZeroes++;
                    }
                }
                zeroCount[0][i]=rowZeroes;
                zeroCount[1][i]=colZeroes;
            }
            // mencari maxima (indeks baris atau kolom dengan '0' terbanyak)
            int rowMaxIdx=zeroCount[0][0];
            int colMaxIdx=zeroCount[1][0];
            for (int i=0; i<matrixOrder; i++) {
                if (zeroCount[0][i]>rowMaxIdx){
                    rowMaxIdx = i;
                }
                if (zeroCount[1][i]>colMaxIdx){
                    colMaxIdx = i;
                }
            }

            // percabangan : cek apakah ada baris atau kolom yang seluruh elemennya bernilai nol
            // jika ada, determinan = 0
            double det=0;
            if (zeroCount[0][rowMaxIdx] == matrixOrder || zeroCount[1][colMaxIdx] == matrixOrder){
                return det;
            }
            // jika tidak, lanjutkan perhitungan determinan
            else{
                double sign;
                // percabangan: menentukan untuk menggunakan baris atau kolom dengan nol terbanyak
                if (zeroCount[0][rowMaxIdx] >= zeroCount[1][colMaxIdx]){    // baris "maxima" memiliki lebih banyak '0' daripada kolom "maxima"
                    sign = Math.pow(-1, rowMaxIdx);
                    for (int j=0; j<matrixOrder; j++){
                        if (this.mat[rowMaxIdx][j]==0) {    // tidak memproses cofactor apabila elemen pada baris bernilai 0
                            continue;
                        }
                        else{
                            det += this.mat[rowMaxIdx][j] * sign * (this.getMinor(rowMaxIdx, j)).getDeterminant();
                        }
                    }
                }
                else{   // kolom "maxima" memiliki lebih banyak nilai '0'
                    sign = Math.pow(-1, colMaxIdx);
                    for (int i=0; i<matrixOrder; i++){
                        if (this.mat[i][colMaxIdx]==0) {    // tidak memproses cofactor apabila elemen pada kolom bernilai 0
                            continue;
                        }
                        else{
                            det += this.mat[i][colMaxIdx] * sign * (this.getMinor(i, colMaxIdx)).getDeterminant();
                        }
                    }
                }

                return det;
            }
        }
    }

    

    /**
     * menghitung kofaktor matriks dari posisi entri pada matriks
     */
    public double getCofactor(int entryRow, int entryCol){
        double sign = Math.pow(-1, entryRow+entryCol);
        return (sign * (this.getMinor(entryRow, entryCol)).getDeterminant());
    }

    /*
     * Mengembalikan adjoint dari suatu matriks
     * Menggunakan metode minor-kofaktor, lalu di-transpose
     */
    public MatrixSquare getAdjoint() {
        int order = this.mat.length;
        double[][] cofactorsVal = new double[order][order];
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                cofactorsVal[i][j] = this.getCofactor(i, j);
            }
        }
        MatrixSquare cofactorsMat = new MatrixSquare(order, cofactorsVal);
        MatrixSquare adj = (MatrixSquare) cofactorsMat.transpose();
        return adj;
    }

    /*
     * Mengembalikan invers matriks menggunakan metode minor-kofaktor
     * Prekondisi: determinan tidak 0
     */
    public MatrixSquare getInverse() {
        double det = this.getDeterminant();            
        MatrixSquare adj = this.getAdjoint();
        MatrixSquare inv = (MatrixSquare) adj.multiplyCoef(1/det);
        return inv;
    }
}
