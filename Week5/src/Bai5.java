//import java.io.*;
//import java.util.*;
//
//public class Solution {
//
//    public static class Stack <T> {
//        private List <T> stack = new ArrayList<>();
//
//        public void push(T s) {
//            stack.add(s);
//        }
//
//        public void pop() {
//            stack.remove(stack.size()-1);
//        }
//
//        public T top() {
//            return stack.get(stack.size() - 1);
//        }
//    }
//
////    public static void main(String[] args) {
////        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
////        ///bufferedReader.read
////        int t = input.nextInt();
////        int k;
////        input.nextLine();
////        String cur = "";
////        Stack<String> cache = new Stack<String>();
////        Stack<Integer> ope = new Stack<Integer>();
////
////        while (t-- > 0) {
////            int query = input.nextInt();
////            String value;
////            switch (query) {
////                case 1:
////                    value = input.nextLine().trim();
////                    cur += value;
////                    ///cache.push(value);
////                    ope.push(value.length());
////                    break;
////                case 2:
////                    k = input.nextInt();
////                    cache.push(cur.substring(cur.length()-k, cur.length()));
////                    ope.push(-k);
////                    cur = cur.substring(0, cur.length() - k);
////                    break;
////                case 3:
////                    k = input.nextInt();
////                    System.out.println(cur.charAt(k-1));
////                    break;
////                case 4:
////                    int o = ope.top();
////                    ope.pop();
////                    if (o > 0) {
////                        cur = cur.substring(0, cur.length() - o);
////                    } else {
////                        cur += cache.top();
////                        cache.pop();
////                    }
////                    break;
////            }
////        }
////    }
//}