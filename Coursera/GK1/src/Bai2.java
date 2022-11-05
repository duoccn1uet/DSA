import java.util.Scanner;
import java.util.Stack;

public class Bai2 {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        String s = inp.nextLine();
        Stack<Character> st = new Stack<>();
        boolean ok = true;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                    st.push(c);
                    break;
                case '[':
                    if (st.isEmpty())
                        st.push(c);
                    else {
                        if (st.peek() == '(')
                            ok = false;
                        else
                            st.push(c);
                    }
                    break;
                case '{':
                    if (st.isEmpty())
                        st.push(c);
                    else {
                        if (st.peek() == '(' || st.peek() == '[')
                            ok = false;
                        else
                            st.push(c);
                    }
                    break;
                case ')':
                    if (st.isEmpty() || st.peek() != '(')
                        ok = false;
                    else
                        st.pop();
                    break;
                case ']':
                    if (st.isEmpty() || st.peek() != '[')
                        ok = false;
                    else
                        st.pop();
                    break;
                case '}':
                    if (st.isEmpty() || st.peek() != '{')
                        ok = false;
                    else
                        st.pop();
                    break;
            }
            if (!ok)
                break;
        }
        if (!ok || st.size() > 0)
            System.out.println("INVALID");
        else
            System.out.println("VALID");
    }
}
