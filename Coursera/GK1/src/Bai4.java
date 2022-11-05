public class Bai4 {
    static class Node {
        public int data;
        public Node parent;
        public Node left;
        public Node right;

        // Các biến tạm dưới đây có thể dùng để lưu vị trí, kiểm tra nếu sinh viên thấy cần thiết
        public boolean tempBool1; // Hai biến tạm kiểu boolean có thể dùng cho bất kỳ mục đích gì
        public boolean tempBool2;
        public int tempInt1; // Hai biến tạm kiểu int có thể dùng cho bất kỳ mục đích gì
        public int tempInt2;
        public Node tempNode1; // Hai biến tạm kiểu Node có thể dùng cho bất kỳ mục đích gì
        public Node tempNode2;

        public Node(int data, Node parent, Node left, Node right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

    static public Node push(Node root, int n, int newValue) {
        if (n == 0)
            return new Node(newValue, null, null, null);
        if (root.data < newValue) {
            /// right
            if (root.right == null) {
                root.right = new Node(newValue, root, null, null);
            } else {
                push(root.right, n, newValue);
            }
        } else {
            if (root.left == null) {
                root.left = new Node(newValue, root, null, null);
            } else {
                push(root.left, n, newValue);
            }
        }
        return root;
    }

    static public int getMax(Node root) {
        if (root == null)
            return -1;
        return root.right == null ? root.data : getMax(root.right);
    }

    public static void main(String[] args) {
        Node root = null;
        System.out.println(getMax(root));

        root = push(root, 0, 3);
        System.out.println(getMax(root));
        root = push(root, 1, 1);
        System.out.println(getMax(root));
        root = push(root, 2, 5);
        System.out.println(getMax(root));
        root = push(root, 3, 9);
        System.out.println(getMax(root));
    }
}
