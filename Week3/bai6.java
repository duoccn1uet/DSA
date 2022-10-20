package Buoi3;

import java.util.*;

public class bai6 {

    public static void minimumBribes(List<Integer> q) {
        int n = q.size();
        int res = 0;
        for (int i = 0; i < n; ++i) {
            if (i + 1 < q.get(i)) {
                if (q.get(i) - i - 1 <= 2)
                    res += q.get(i) - i - 1;
                else {
                    System.out.println("Too chaotic");
                    return;
                }
            }
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        minimumBribes(Arrays.asList(1, 2, 5, 3, 7, 8, 6, 4));
    }
}
