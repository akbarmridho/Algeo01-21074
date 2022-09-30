package main.SPL;

import java.util.Arrays;

import main.SPL.utils.Transformers;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

public class testTrans {
    public static void main(String[] args) {
        double[][] a = {{4,1,5,2,8},
                        {2,3,4,3,6}};
        double[][] b = {{1},
                        {4}};
        Matrix x = new Matrix(a);
        Matrix y = new Matrix(b);
        MatrixAugmented equ = new MatrixAugmented(x, y);

        Integer[] or = Transformers.removeUnnecesaryVariable(equ);

        System.out.println(Arrays.deepToString(equ.getAugmentation().getMatrix()));
        System.out.println(Arrays.deepToString(or));
        Transformers.printParametric(equ.getAugmentation());
    }
}
