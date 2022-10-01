package main.bonus;

import main.interpolation.Bicubic;
import main.matrix.Matrix;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class ImageScaling {
    static int scale = 2;

    enum EDGEMODE {
        BOTTOM,
        RIGHT,
        BOTTOMRIGHT
    }

    public static BufferedImage scale(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        int[][][] pixels = convertRawPixels(input);

        int newHeight = height * scale;
        int newWidth = width * scale;
        int[][] newPixels = new int[newHeight][newWidth];

        // pixel pertama secara vertikal dan horizontal belum terinterpolasi
        // bila lebar pixel ganjil, maka akan ada dua pixel di sebelah kanan yang belum terinterpolasi
        // bila lebar pixel genap, maka akan ada satu pixel di sebelah kanan yang belum terinterpolasi
        // sama halnya dengan tinggi pixel

        // interpolasi edge pixel teratas dan terbawah (handle case pixel genap)
        // bila jumlah pixel ganjil, pixel pada posisi ganjil terakhir belum diinterpolasi
        for (int y : new int[]{0, height - 2}) {
            for (int x = 0; x <= width - 2; x += 2) {
                int[][][] subPixels = new int[2][2][4];

                for (int i = 0; i < 2; i++) {
                    System.arraycopy(pixels[y + i], x, subPixels[i], 0, 2);
                }

                int[][] result = interpolatePixels(subPixels, false);

                for (int i = 0; i < 4; i++) {
                    System.arraycopy(result[i], 0, newPixels[y * scale + i], x * scale, 4);
                }
            }
        }

        // interpolasi edge pixel paling kiri dan kanan (handle case pixel genap)
        // bila jumlah pixel ganjil, pixel pada posisi ganjil terakhir belum diinterpolasi
        for (int y = 1; y < (height / 2) * 2 - 1; y += 2) {
            for (int x : new int[]{0, (width / 2) * 2 - 2}) {
                int[][][] subPixels = new int[2][2][4];

                for (int i = 0; i < 2; i++) {
                    System.arraycopy(pixels[y + i], x, subPixels[i], 0, 2);
                }

                int[][] result = interpolatePixels(subPixels, false);

                for (int i = 0; i < 4; i++) {
                    System.arraycopy(result[i], 0, newPixels[y * scale + i], x * scale, 4);
                }
            }
        }

        // handle jika terdapat lebar yang ganjil
        if (width % 2 == 1) {
            for (int y = 0; y < (height / 2) * 2 - 1; y += 2) {
                int[][][] subPixels = new int[2][2][4];

                subPixels[0][0] = pixels[y][width - 2];
                subPixels[0][1] = pixels[y][width - 1];
                subPixels[1][0] = pixels[y + 1][width - 2];
                subPixels[1][1] = pixels[y + 1][width - 1];

                int[][] result = interpolateEdgePixels(subPixels, EDGEMODE.RIGHT);

                for (int i = 0; i < 4; i++) {
                    System.arraycopy(result[i], 0, newPixels[y * scale + i], (width - 1) * scale, 2);
                }
            }
        }

        // handle jika terdapat tinggi yang ganjil
        if (height % 2 == 1) {
            for (int x = 0; x < width - 2; x += 2) {
                int[][][] subPixels = new int[2][2][4];

                subPixels[0][0] = pixels[height - 2][x];
                subPixels[0][1] = pixels[height - 2][x + 1];
                subPixels[1][0] = pixels[height - 1][x];
                subPixels[1][1] = pixels[height - 1][x + 1];

                int[][] result = interpolateEdgePixels(subPixels, EDGEMODE.BOTTOM);

                for (int i = 0; i < 2; i++) {
                    System.arraycopy(result[i], 0, newPixels[(height - 1) * scale + i], x * scale, 4);
                }
            }
        }

        // jika tinggi dan lebar ganjil
        if (height % 2 == 1 && width % 2 == 1) {
            int[][][] subPixels = new int[2][2][4];

            subPixels[0][0] = pixels[height - 2][width - 2];
            subPixels[0][1] = pixels[height - 2][width - 1];
            subPixels[1][0] = pixels[height - 1][width - 2];
            subPixels[1][1] = pixels[height - 1][height - 2];

            int[][] result = interpolateEdgePixels(subPixels, EDGEMODE.BOTTOMRIGHT);

            for (int i = 0; i < 2; i++) {
                System.arraycopy(result[i], 0, newPixels[(height - 1) * scale + i], (width - 1) * scale, 2);
            }
        }

        // interpolate 2x2 pixels menjadi 4x4 pixels dari 4x4 pixels sekitar
        // menggunakan metode bicubic interpolation
        // from y = 1 and x = 2 to y < height/2 and x < width/2
        ArrayList<int[]> pair = new ArrayList<>();

        for (int y = 1; y < (height / 2) * 2 - 2; y += 2) {
            for (int x = 1; x < (width / 2) * 2 - 2; x += 2) {
                pair.add(new int[]{y, x});
            }
        }

        pair.parallelStream().forEach(pos -> {
            int y = pos[0];
            int x = pos[1];
            int[][][] subPixels = new int[4][4][4];

            for (int i = 0; i < 4; i++) {
                System.arraycopy(pixels[y + i - 1], x - 1, subPixels[i], 0, 4);
            }

            int[][] result = interpolatePixels(subPixels, true);

            for (int i = 0; i < 4; i++) {
                System.arraycopy(result[i], 0, newPixels[y * scale + i], x * scale, 4);
            }
        });

        BufferedImage resultImage = new BufferedImage(newWidth, newHeight, TYPE_INT_ARGB);

        for (int h = 0; h < newHeight; h++) {
            resultImage.setRGB(0, h, newWidth, 1, newPixels[h], 0, newWidth);
        }

        return resultImage;
    }

    /*
     * Lakukan interpolasi pada pixel berukuran 4x4 dengan 4 channel (RGBA) bila menggunakan bicubic
     * Ini menginterpolasi pusat pixel yang berukuran 2x2 (sisa pixel digunakan untuk acuan interpolasi)
     * Bila menggunakan interpolasi linear, maka ukuran input pixel adalah 2x2
     * yang menghasilkan hasil interpolasi pixel berukuran 4x4
     */
    public static int[][] interpolatePixels(int[][][] subPixels, boolean bicubic) {
        int[][][] result = new int[4][4][4];

        for (int i = 0; i < 4; i++) {
            int[][] subresult;

            if (bicubic) {
                double[][] color = new double[4][4];

                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        color[j][k] = subPixels[j][k][i];
                    }
                }

                subresult = interpolateChannelBicubic(color);
            } else {
                double[][] color = new double[2][2];

                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        color[j][k] = subPixels[j][k][i];
                    }
                }
                subresult = interpolateChannelLinear4x4(color);
            }


            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[j][k][i] = subresult[j][k];
                }
            }
        }

        int[][] convertedResult = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // langsung konversi RGBA ke int
                convertedResult[i][j] = toRGBA(result[i][j]);
            }
        }

        return convertedResult;
    }

    public static int[][] interpolateEdgePixels(int[][][] subPixels, EDGEMODE mode) {
        int row, col;

        if (mode == EDGEMODE.RIGHT) {
            row = 4;
            col = 2;
        } else if (mode == EDGEMODE.BOTTOM) {
            row = 2;
            col = 4;
        } else {
            row = 2;
            col = 2;
        }

        int[][][] result = new int[row][col][4];

        for (int i = 0; i < 4; i++) {
            double[][] color = new double[2][2];

            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    color[j][k] = subPixels[j][k][i];
                }
            }

            int[][] subresult;

            if (mode == EDGEMODE.RIGHT) {
                subresult = interpolateChannelLinear4x2(color);
            } else if (mode == EDGEMODE.BOTTOM) {
                subresult = interpolateChannelLinear2x4(color);
            } else {
                subresult = interpolateChannelLinear2x2(color);
            }


            for (int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    result[j][k][i] = subresult[j][k];
                }
            }
        }

        int[][] convertedResult = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // langsung konversi RGBA ke int
                convertedResult[i][j] = toRGBA(result[i][j]);
            }
        }

        return convertedResult;

    }

    /*
     * Interpolasi pusat matriks (2x2) menjadi matriks 4x4
     * dengan metode bicubic interpolation
     */
    public static int[][] interpolateChannelBicubic(double[][] color) {
        int[][] result = new int[4][4];

        Bicubic interpolater = new Bicubic(new Matrix(color));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = (int) interpolater.interpolate(i / 3d, j / 3d);
//                result[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        return result;
    }

    /*
     * Interpolasi matriks 2x2 menjadi matriks 4x4
     * dengan metode linear interpolation
     */
    public static int[][] interpolateChannelLinear4x4(double[][] color) {
        int[][] result = new int[4][4];

        // interpolate left and right column
        for (int i = 0; i < 4; i++) {
            for (int j : new int[]{0, 3}) {
                int val = (int) (color[0][j / 2] + i / 3d * (color[1][j / 2] - color[0][j / 2]));
                result[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        // interpolate row
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int val = (int) (color[i / 2][0] + j / 3d * (color[i / 2][1] - color[i / 2][0]));
                result[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        return result;
    }

    /*
     * Interpolasi matriks 2x2 menjadi matriks 4x2
     * dengan metode linear interpolation
     */
    public static int[][] interpolateChannelLinear4x2(double[][] color) {
        int[][] shadowResult = new int[4][3];

        // interpolate left and right column
        for (int i = 0; i < 4; i++) {
            for (int j : new int[]{0, 2}) {
                int val = (int) (color[0][j / 2] + i / 3d * (color[1][j / 2] - color[0][j / 2]));
                shadowResult[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        // interpolate row
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                int val = (int) (color[i / 2][0] + j / 2d * (color[i / 2][1] - color[i / 2][0]));
                shadowResult[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        int[][] result = new int[4][2];

        for (int i = 0; i < 4; i++) {
            System.arraycopy(shadowResult[i], 1, result[i], 0, 2);
        }

        return result;
    }

    /*
     * Interpolasi matriks 2x2 menjadi matriks 2x4
     * dengan metode linear interpolation
     */
    public static int[][] interpolateChannelLinear2x4(double[][] color) {
        int[][] shadowResult = new int[3][4];

        // interpolate left and right column
        for (int i = 0; i < 3; i++) {
            for (int j : new int[]{0, 3}) {
                int val = (int) (color[0][j / 2] + i / 2d * (color[1][j / 2] - color[0][j / 2]));
                shadowResult[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        // interpolate row
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int val = (int) (color[i / 2][0] + j / 3d * (color[i / 2][1] - color[i / 2][0]));
                shadowResult[i][j] = val < 0 ? 0 : Math.min(val, 255);
            }
        }

        int[][] result = new int[2][4];

        for (int i = 1; i < 3; i++) {
            System.arraycopy(shadowResult[i], 0, result[i - 1], 0, 4);
        }

        return result;
    }

    /*
     * Interpolasi matriks 2x2 menjadi matriks 2x2
     * dengan metode linear interpolation
     */
    public static int[][] interpolateChannelLinear2x2(double[][] color) {
        int[][] result = new int[2][2];

        result[0][0] = (int) ((color[0][0] + color[0][1] + color[1][0] + color[1][1]) / 4);
        result[0][1] = (int) ((color[0][1] + color[1][1]) / 2);
        result[1][0] = (int) ((color[1][0] + color[1][1]) / 2);
        result[1][1] = (int) color[1][1];

        return result;
    }

    /*
     * Ambil tiap pixel pada gambar lalu ubah warna dari int ARGB menuju array of int dan ubah menjadi array 2 dimensi
     * untuk posisi x dan y
     */
    public static int[][][] convertRawPixels(BufferedImage input) {
        int width = input.getWidth();
        int height = input.getHeight();
        int[] rawPixels = input.getRGB(0, 0, width, height, null, 0, width);

        int[][][] pixels = new int[input.getHeight()][input.getWidth()][4];

        int yPos = 0;
        int xPos = 0;

        while (yPos < height) {
            pixels[yPos][xPos] = toBGRA(rawPixels[yPos * width + xPos]);
            xPos++;
            if (xPos == width) {
                xPos = 0;
                yPos++;
            }
        }

        return pixels;
    }

    /*
     * Konversi pixel color dari int menuju array of color value BGRA
     * Konvensi int pixel
     * dibaca menggunakan biner
     * AAAAAAAARRRRRRRRGGGGGGGGBBBBBBBB
     * A adalah bit alpha
     * R adalah bit red
     * G adalah bit green
     * B adalah bit blue
     */
    public static int[] toBGRA(int color) {
        return new int[]{color & 0xff, // blue
                (color & 0xff00) >> 8, // green
                (color & 0xff0000) >> 16, // red
                (color & 0xff000000) >>> 24 // alpha
        };
    }

    /*
     * Konversi pixel color dari array value BGRA menuju integer
     * Konvensi int pixel
     * dibaca menggunakan biner
     * AAAAAAAARRRRRRRRGGGGGGGGBBBBBBBB
     * A adalah bit alpha
     * R adalah bit red
     * G adalah bit green
     * B adalah bit blue
     */
    public static int toRGBA(int[] color) {
        return (color[3] << 24) | (color[2] << 16) | (color[1]) << 8 | color[0];
    }
}
