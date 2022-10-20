package Buoi3;

import java.util.*;

public class bai3 {
    public static String balancedSums(List<Integer> arr) {
        int n = arr.size();
        long[] s = new long[n + 1];
        s[n - 1] = arr.get(n - 1);
        for (int i = n - 2; i >= 0; --i)
            s[i] = s[i + 1] + arr.get(i);
        long prefix_sum = 0;
        for (int i = 0; i < n; ++i) {
            if (prefix_sum == s[i + 1])
                return "YES";
            prefix_sum += arr.get(i);
        }
        return "NO";
    }
}
