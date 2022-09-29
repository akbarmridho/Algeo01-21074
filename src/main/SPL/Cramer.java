package main.SPL;

import main.SPL.errors.InfinitySolutionException;
import main.SPL.errors.NoSolutionException;
import main.SPL.utils.Transformers;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.matrix.errors.NotMatrixSquareException;

public class Cramer {
    public static double[] solve(MatrixAugmented matrixCpy) throws NotMatrixSquareException, NoSolutionException, InfinitySolutionException {
        MatrixAugmented matrix = matrixCpy.copy();
        //Index variabel yang tidak terpakai pada SPL
        double determinant = matrix.getOriginal().getDeterminant();
        
        if (determinant == 0) {
            throw new NoSolutionException("Tidak terdapat solusi SPL karena determinan nol");
        }

        // 1/Determinan
        double oneOverDet = 1d/determinant;
        
        //container hasil SPL
        double[] result = new double[matrix.getOriginal().getColumnCount()];

        //Operasi Crammer
        for(int i = 0; i < matrix.getOriginal().getColumnCount(); i++) {
            //matrix temporary Dx1, Dx2, Dx3, ..., Dxn
            Matrix tempMatrix = matrix.getOriginal();

            //Mengubah kolom i dengan sisi kanan dari SPL
            for(int j = 0; j < matrix.getOriginal().getRowCount(); j++) {
                tempMatrix.getMatrix()[j][i] = matrixCpy.getAugmentation().getMatrix()[j][0];
            }

            //xn = Dxn / D
            result[i] = tempMatrix.getDeterminant() * oneOverDet;
        }

        //return
        return result;
    }
}
