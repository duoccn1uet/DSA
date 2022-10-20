package Buoi3;

import java.util.*;

public class bai4 {
    public static List<Integer> closestNumbers(List<Integer> arr) {
        Collections.sort(arr);
        int n = arr.size();
        List<Integer> res = new ArrayList<Integer>();
        int min_diff = (int) 1e9;
        for (int i = 1; i < n; ++i)
            min_diff = Math.min(min_diff, arr.get(i) - arr.get(i - 1));
        for (int i = 1; i < n; ++i)
            if (arr.get(i) - arr.get(i - 1) == min_diff) {
                res.add(arr.get(i - 1));
                res.add(arr.get(i));
            }
        return res;
    }

    public static void main(String[] args) {
        int[] a = { 5, 4, 3, 2 };
        List<Integer> f = new ArrayList<Integer>();
        for (int x : a)
            f.add(x);
        List<Integer> g = closestNumbers(f);
        for (int x : g)
            System.out.println(x);
    }
}