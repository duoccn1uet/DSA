public class cla extends Stack implements inter, inter2 {

    static void test(inter2 a) {
        if (cla instanceof Stack) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
    public static void main(String[] args) {
        cla a = new cla();
        test(a);
    }
}
