import java.util.Scanner;

public class Bai3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] queue = new int[100000];

        int l = 0;
        int r = 0;
        for (int t = input.nextInt(); t-- > 0;) {
            int query = input.nextInt();
            switch (query) {
                case 1:
                    int value = input.nextInt();
                    queue[r++] = value;
                    break;
                case 2:
                    l++;
                    break;
                case 3:
                    System.out.println(queue[l]);
                    break;
            }
            System.out.println(l + " " + r + "\n");
            for (int i = l; i < r; ++i)
                System.out.print(queue[i] + " ");
        }
    }
}
