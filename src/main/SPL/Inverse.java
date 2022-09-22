package main.SPL;

import main.matrix.Matrix;
import main.matrix.MatrixAugmented;
import main.SPL.errors.InfinitySolutionException;
import main.SPL.utils.Transformers;
import main.matrix.errors.NotMatrixSquareException;
import main.matrix.errors.ZeroDeterminantException;


public class Inverse {
    public static double[] solve(MatrixAugmented spl) throws InfinitySolutionException, NotMatrixSquareException {
        // bentuk umum SPL : Ax = b; dalam matrix augmented adalah [A | b]
        // A diperoleh dari spl.getOriginal(); b diperoleh dari spl.getAugmentation()

        // Langkah 1: penentuan SPL homogen atau heterogen
        boolean isHomogeneous = true;
        for (int i = 0; i < (spl.getAugmentation()).getRowCount(); i++) {
            if (((spl.getAugmentation()).getMatrix())[i][0] != 0) {
                isHomogeneous = false;
                break;
            }
        }

        // Langkah 2: x = (A^-1)b   ... A^-1 adalah invers dari A
        Matrix inversA;
        try {
            inversA = (spl.getOriginal()).getInverse();
        } catch (NotMatrixSquareException e){
            throw new NotMatrixSquareException("SPL tidak bisa diselesaikan dengan metode invers");
        } catch (ZeroDeterminantException e){
            if (isHomogeneous){
                throw new InfinitySolutionException("SPL homogen mempunyai solusi nontrivial");
            } else {
                throw new InfinitySolutionException("SPL tidak mempunyai solusi unik");
            }
        }

        Matrix resultMat = (inversA.multiplyMatrix(spl.getAugmentation()));
        return Transformers.singleColMatToArr(resultMat);
    }
}
