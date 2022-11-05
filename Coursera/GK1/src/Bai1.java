import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Bai1 {
    private static int f(int x) {
        int res = 0;
        while (x > 0) {
            res += x % 10;
            x /= 10;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);

        int n = inp.nextInt();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            a.add(inp.nextInt());
        a.sort((o1, o2) -> {
            int cs1 = f(o1);
            int cs2 = f(o2);
            if (cs1 != cs2)
                return Integer.compare(cs1, cs2);
            return Integer.compare(o1, o2);
        });
        for (int x : a)
            System.out.println(x);
    }
}
