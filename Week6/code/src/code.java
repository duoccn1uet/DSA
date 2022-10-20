import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class code {
    public static void insertionSort1(int n, List<Integer> arr) {
         for (int i = 1; i < n; ++i) {
            for (int j = i; j > 0; --j) {
                if (arr.get(j-1) > arr.get(j)) {
                    int tmp = arr.get(j);
                    arr.set(j, arr.get(j-1));
                    arr.set(j-1, tmp);
                }
            }
            for (int x : arr) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] a = {2, 4, 6, 8, 3};
        insertionSort1(6, Arrays.asList(1, 4, 3, 5, 6, 2));
    }
}
