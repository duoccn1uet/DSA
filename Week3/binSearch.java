package Buoi3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class binSearch {

    public static int binarySearch(int[] a, int number) {
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = (l + r) >> 1;
            if (a[m] == number) {
                return m;
            }
            if (a[m] < number) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        Random rand = new Random();
        int upperA = (int) 1e9;
        int[] a = new int[n];
        for (int i = 0; i < n; ++i)
            a[i] = rand.nextInt(upperA);
        for (int i = 0; i < n; ++i)
            System.out.print(a[i] + " ");
        System.out.println();
        Arrays.sort(a);
        System.out.println(binarySearch(a, a[2]));
    }
}
