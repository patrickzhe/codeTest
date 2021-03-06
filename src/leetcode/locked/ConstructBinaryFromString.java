package leetcode.locked;

import util.BTNode;

import java.util.Stack;

/*
leetcode 536
http://www.cnblogs.com/grandyang/p/6793904.html

You need to construct a binary tree from a string consisting of parenthesis and integers.

The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis.
The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.

You always start to construct the left child node of the parent first if it exists.

Example:

Input: "4(2(3)(1))(6(5))"
Output: return the tree root node representing the following tree:

       4
     /   \
    2     6
   / \   /
  3   1 5
Note:

There will only be '(', ')', '-' and '0' ~ '9' in the input string.
An empty tree is represented by "" instead of "()".

@solved

关键在于递归的理解，每层只解决自己的左子树右子树
然后退出条件在于，每层又剥掉最外面的字符，不用关心-

 */
public class ConstructBinaryFromString {

    public static BTNode constructTreeFromString(String s) {
        if (s.indexOf('(') < 0) {
            return new BTNode(Integer.valueOf(s));
        }
        int index_l = s.indexOf('(');
        BTNode node = new BTNode(Integer.valueOf(s.substring(0, index_l)));
        int count = 1;
        int i = index_l + 1;
        for (; i < s.length(); i++) {
            if (s.charAt(i) == ')') count--;
            else if (s.charAt(i) == '(') count++;
            if (count == 0) break;
        }
        node.left = constructTreeFromString(s.substring(index_l + 1, i));
        if (i + 2 < s.length() - 1)
            node.right = constructTreeFromString(s.substring(i + 2, s.length() - 1));
        return node;
    }

    /*
    O(n) space, O(n logn) time ?
     */
    public static BTNode str2tree(String s) {
        Stack<BTNode> stack = new Stack<>();
        for(int i = 0, j = i; i < s.length(); i++, j = i){
            char c = s.charAt(i);
            if(c == ')')    stack.pop();
            else if(c >= '0' && c <= '9' || c == '-'){
                while(i + 1 < s.length() && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') i++;
                BTNode currentNode = new BTNode(Integer.valueOf(s.substring(j, i + 1)));
                if(!stack.isEmpty()){
                    BTNode parent = stack.peek();
                    if(parent.left != null)    parent.right = currentNode;
                    else parent.left = currentNode;
                }
                stack.push(currentNode);
            }
        }
        return stack.isEmpty() ? null : stack.peek();
    }

    public static void main(String[] args) {
        String s = "4(2(3)(1))(6(5))";
        BTNode node = constructTreeFromString(s);
        StringBuilder sb = new StringBuilder();
        node.printInOrder(node, sb);
        System.out.println(sb.toString());
        node = str2tree(s);
        sb.delete(0, sb.length());
        node.printInOrder(node, sb);
        System.out.println(sb.toString());
    }
}
