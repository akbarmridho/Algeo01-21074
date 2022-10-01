package main.SPL.utils;

import main.matrix.Matrix;
import main.matrix.MatrixAugmented;

import java.util.ArrayList;
import java.util.Arrays;

public class Transformers {
    /*
     * Menghilangkan kolom yang rata dengan nol, lalu membentuk persamaan parametrik bila diperlukan
     */
    public static Integer[] transformParametric(MatrixAugmented matrix) {
        ArrayList<Integer> maskedIdx = new ArrayList<>();
        Matrix equation = matrix.getOriginal();

        // cari untuk setiap kolom keberadaan kolom nol
        for (int i = 0; i < equation.getColumnCount(); i++) {
            boolean allZero = true;

            for (int j = 0; j < equation.getRowCount() && allZero; j++) {
                if (equation.getMatrix()[j][i] != 0) {
                    allZero = false;
                }
            }

            if (allZero) {
                // tambahkan id kolom yang dimask
                maskedIdx.add(i);

                // jika terdapat kolom nol, maka solusi menjadi persamaan parametrik
                Matrix parametricRHS = new Matrix(matrix.getRowCount(), matrix.getAugmentation().getColumnCount() + 1);

                for (int i1 = 0; i1 < matrix.getRowCount(); i1++) {
                    for (int j1 = 0; j1 < matrix.getAugmentation().getColumnCount(); j1++) {
                        // salin isi matriks augmentasi bagian kanan
                        parametricRHS.getMatrix()[i1][j1] = matrix.getAugmentation().getMatrix()[i1][j1];
                    }
                    // assign nol pada kolom paling kanan pada augmentation
                    parametricRHS.getMatrix()[i1][parametricRHS.getColumnCount() - 1] = equation.getMatrix()[i1][i];
                }

                // ubah matrix augmentation dari satu kolom menjadi beberapa kolom
                Matrix equationRHS = matrix.getAugmentation();
                equationRHS.assign(parametricRHS);
            }
        }

        // jika banyaknya kolom setelah penghapusan masih lebih banyak daripada banyaknya baris
        // (banyaknya persamaan masih kurang, sehingga menghasilkan persamaan parametrik)
        if (equation.getColumnCount() - maskedIdx.size() > matrix.getAugmentation().getRowCount()) {

            int parametricIndex = equation.getColumnCount() - 1;

            int i = 0;

            // banyaknya persamaan yang kurang
            while (i < (equation.getColumnCount() - maskedIdx.size() - matrix.getAugmentation().getRowCount())) {

                boolean isZeroColumn = false;

                for (int  j = 0; !isZeroColumn && j < (maskedIdx.size() - i); j++) {
                    isZeroColumn = (parametricIndex == maskedIdx.get(j));
                }

                if (!isZeroColumn) {
                    maskedIdx.add(parametricIndex);
                    i++;

                    Matrix parametricRHS = new Matrix(matrix.getRowCount(), matrix.getAugmentation().getColumnCount() + 1);

                    for (int i1 = 0; i1 < matrix.getRowCount(); i1++) {
                        for (int j1 = 0; j1 < matrix.getAugmentation().getColumnCount(); j1++) {
                            parametricRHS.getMatrix()[i1][j1] = matrix.getAugmentation().getMatrix()[i1][j1];
                        }

                        parametricRHS.getMatrix()[i1][parametricRHS.getColumnCount() - 1] = equation.getMatrix()[i1][parametricIndex];
                    }

                    Matrix equationRHS = matrix.getAugmentation();
                    equationRHS.assign(parametricRHS);
                }
                parametricIndex--;
            }
        }

        Integer[] idxs = maskedIdx.toArray(new Integer[0]);

        Matrix equationRHS = matrix.getAugmentation();
        equationRHS.assign(formatParam(matrix, idxs));
        
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

    // mengubah matrix yang terdiri dari satu kolom menjadi sebuah array satu dimensi
    public static double[] singleColMatToArr(Matrix input){
        return (input.transpose()).getMatrix()[0];
    }

    public static Matrix formatParam (MatrixAugmented equation, Integer[] idx) {
        Matrix matrix = equation.getAugmentation();
        Matrix result = new Matrix(equation.getRowCount()+idx.length, equation.getOriginal().getColumnCount() + 1);
        int indexAdd = 0;
        for (int i = 0; i < result.getRowCount(); i++) {
            boolean foundedIdxRow = false;
            for (Integer integer : idx) {
                if (i == integer) {
                    foundedIdxRow = true;
                    break;
                }
            }
            if (!foundedIdxRow) {
                result.getMatrix()[i][0] = matrix.getMatrix()[indexAdd][0];
                indexAdd++;
            }
        }

        for (int j = 0; j < result.getColumnCount()-1; j++) {
            boolean found = false;
            int foundedIdx = 0;
            for (int k = 0; k < idx.length; k++) {
                if (j == idx[k]) {
                    found = true;
                    foundedIdx = k+1;
                    break;
                }
            }
            if (found) {
                indexAdd=0;
                for (int i = 0; i < result.getRowCount(); i++) {
                    boolean foundedIdxRow = false;
                    for (Integer integer : idx) {
                        if (i == integer) {
                            foundedIdxRow = true;
                            break;
                        }
                    }
                    if (!foundedIdxRow) {
                        result.getMatrix()[i][j+1] = -1d*matrix.getMatrix()[indexAdd][foundedIdx];
                        indexAdd++;
                    }
                }
            } else {
                for (int i = 0; i < result.getRowCount(); i++) {
                    result.getMatrix()[i][j+1] = 0;
                }
            }
        }
        return result;
    }

    public static ArrayList<String> printParametric(Matrix solution) {

        ArrayList<String> output = new ArrayList<>();

        for (int i = 0; i < solution.getRowCount(); i++) {
            boolean parametricVariable = false;
            String RHS = "x" + (i + 1);
            StringBuilder LHS = new StringBuilder();
            for (int j = 0; j < solution.getColumnCount(); j++) {
                if (!parametricVariable){
                    parametricVariable = solution.getMatrix()[i][j]!=0;
                }
                if (j == 0){
                    LHS.append(solution.getMatrix()[i][j]);
                }
                else if (solution.getMatrix()[i][j]!=0) {
                    LHS.append(" + ").append(solution.getMatrix()[i][j]).append("(t").append(j).append(")");
                }
            }
            if (!parametricVariable) {
                LHS = new StringBuilder("(t" + (i + 1) + ")");
            }
            if (i == solution.getRowCount() - 1) {
                output.add(RHS + " = " + LHS + "\n");
            } else {
                output.add(RHS + " = " + LHS + ", ");
            }
        }
        return output;
    }

}
