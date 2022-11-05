import java.util.Scanner;

public class Bai3 {
    private static int[] a;
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        int n = inp.nextInt();
        a = new int[n];
        boolean ok = true;
        for (int i = 0; i < n-1; ++i) {
            int x = inp.nextInt();
            int y = inp.nextInt();
            ++a[x];
            if (a[x] > 2) {
                ok = false;
                break;
            }
        }
        System.out.println(ok ? "yes" : "no");
    }
}
