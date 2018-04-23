package com.rocky.thread.syncutil.cyclicbarrier;

import java.util.Random;

public class MatrixMock {

    private int data[][];

    public MatrixMock(int size, int length, int number) {
        int counter = 0;
        data = new int[size][length];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < length; j++) {
                data[i][j] = random.nextInt(10);
                if (data[i][j] == number) {
                    counter++;
                }
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("There are " + counter + " ocurrences of number in generatord data.");
    }

    public int[] getRow(int row) {
        if (row >= 0 && row < data.length) {
            return data[row];
        }
        return null;
    }

}
