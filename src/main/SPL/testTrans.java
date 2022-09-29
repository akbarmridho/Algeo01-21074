package main.SPL;

import java.util.Arrays;

import main.SPL.utils.Transformers;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

public class testTrans {
    public static void main(String[] args) {
        double[][] a = {{0,0,1,4,8},
                        {0,0,4,6,6}};
        double[][] b = {{1},
                        {4}};
        Matrix x = new Matrix(a);
        Matrix y = new Matrix(b);
        MatrixAugmented equ = new MatrixAugmented(x, y);

        Integer[] or = Transformers.removeUnnecesaryVariable(equ);

        System.out.println(Arrays.deepToString(equ.getAugmentation().getMatrix()));
        System.out.println(Arrays.deepToString(or));
    }
}
