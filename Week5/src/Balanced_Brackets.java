import java.util.ArrayList;
import java.util.List;

public class Balanced_Brackets {
    public static String isBalanced(String s) {
        char[] stack = new char[s.length()];
        int n = 0;

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack[n++] = c;
                    break;
                case ']':
                    if (n == 0 || stack[n-1] != '[')
                        return "NO";
                    --n;
                    break;
                case ')':
                    if (n == 0 || stack[n-1] != '(')
                        return "NO";
                    --n;
                    break;
                case '}':
                    if (n == 0 || stack[n-1] != '{')
                        return "NO";
                    --n;
                    break;
            }
        }
        return n == 0 ? "YES" : "NO";
    }

    public static void main(String[] args) {
        System.out.println(isBalanced("[](){()}"));
    }
}
