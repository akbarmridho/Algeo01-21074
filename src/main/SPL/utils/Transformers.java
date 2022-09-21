package main.SPL.utils;

import main.SPL.errors.InfinitySolutionException;
import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

import java.util.ArrayList;
import java.util.Arrays;

public class Transformers {
    /*
     * Menghilangkan kolom yang rata dengan nol
     */
    public static Integer[] removeUnnecesaryVariable(MatrixAugmented matrix) throws InfinitySolutionException {
        ArrayList<Integer> maskedIdx = new ArrayList<Integer>();
        Matrix equation = matrix.getOriginal();

        for (int i = 0; i < equation.getColumnCount(); i++) {
            boolean allZero = true;

            for (int j = 0; j < equation.getRowCount() && allZero; j++) {
                if (equation.getMatrix()[j][i] != 0) {
                    allZero = false;
                }
            }

            if (allZero) {
                maskedIdx.add(i);
            }
        }

        if (equation.getColumnCount() - maskedIdx.size() < matrix.getAugmentation().getRowCount()) {
            throw new InfinitySolutionException("Terdapat tak-hingga banyaknya solusi");
        }

        Integer[] idxs = maskedIdx.toArray(new Integer[maskedIdx.size()]);

        equation.deleteCols(idxs);

        return idxs;
    }

    public static double[] fillResultWithZero(double[] input, Integer[] idxs) {
        double[] result = new double[input.length + idxs.length];

        int i = 0;

        for (int j = 0; j < input.length + idxs.length; j++) {
            int finalI = i;
            if (Arrays.stream(idxs).anyMatch(x -> x == finalI)) {
                result[j] = 0;
            } else {
                result[i] = input[i];
                i++;
            }
        }

        return result;
    }
}
