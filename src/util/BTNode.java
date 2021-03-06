package util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BTNode {
    public int val;
    public BTNode left;
    public BTNode right;

    public BTNode() {
    }

    public BTNode(int x) {
        val = x;
    }

    public void printPostorder(BTNode node, StringBuilder sb) {
        if (node == null) return;
        // first recur on left subtree
        printPostorder(node.left, sb);

        // then recur on right subtree
        printPostorder(node.right, sb);

        // now deal with the node
        sb.append(node.val).append(",");
    }

    @Override
    public String toString() {
        return "BTNode{" +
                "val=" + val +
                '}';
    }

    public void printPreorder(BTNode node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.val).append(",");
        printPostorder(node.left, sb);
        printPostorder(node.right, sb);
    }

    public void printInOrder(BTNode node, StringBuilder sb) {
        if (node == null) return;
        printPostorder(node.left, sb);
        sb.append(node.val).append(",");
        printPostorder(node.right, sb);
    }

    public void inOrder(BTNode node, List<Integer> inOrders) {
        if (node == null) return;
        inOrder(node.left, inOrders);
        inOrders.add(node.val);
        inOrder(node.right, inOrders);
    }

    public void printBFSOrder(BTNode node, StringBuilder sb) {
        int height = height(node);
        for (int i = 1; i <= height; i++)
            printLevelOrder(node, i, sb);
    }

    private int height(BTNode node) {
        if (node == null) return 0;
        int lh = height(node.left);
        int rh = height(node.right);
        return lh > rh ? lh + 1 : rh + 1;
    }

    public void printLevelOrder(BTNode node, int level, StringBuilder sb) {
        if (node == null) {
            sb.append("null").append(",");
            return;
        }
        if (level == 1)
            sb.append(node.val).append(",");
        else if (level > 1)
        {
            printLevelOrder(node.left, level-1, sb);
            printLevelOrder(node.right, level-1, sb);
        }
    }

    public static void printLevelOrderIgnoreNull(BTNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append(node.val).append(",");
        printLevelOrderIgnoreNull(node.left, sb);
        printLevelOrderIgnoreNull(node.right, sb);
    }

    /*
    LeetCode 889
    https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/description/
Input: pre = [1,2,4,5,3,6,7], post = [4,5,2,6,7,3,1]
Output: [1,2,3,4,5,6,7]
preorder = [root] + [preorder of left] + [preorder of right]
postorder = [postorder of left] + [post order of right] + [root]

pre[1] = root of the left,  post[i] == pre[1],  0-i == postorder of left,  i+1-len-1 == post order of right
step 1: check if has child: compare post pointer with root value.
step 2: If no child, return children length 0.
step 3: create left child value, move pre pointer.
step 4: recursive to resolve left child, get left child's children length.
step 5: move pre pointe and move pre pointer with left child node length(left child's children length + 1)
step 6: check if has right child: compare post pointer with root value.
step 7: if no right child, return length.
step 8: create right child value, move pre pointer.
step 9: recursive to resolve righ child, get right child's children length.
step 10: return left child node length + right child node length
     */
    BTNode buildTreeFromArray(int[] preOrder, int[] postOrder) {
        //first of preOrder is the root | last of the post Order is the root
        BTNode root = new BTNode(preOrder[0]);
        addChildren(root, preOrder, 1, postOrder, 0);
        return root;
    }

    private int addChildren(BTNode root, int[] preOrder, int i, int[] postOrder, int j) {
        if (postOrder[j] == root.val) return 0;
        BTNode left = new BTNode(preOrder[i]);
        root.left = left;
        int len = 1 + addChildren(left, preOrder, i+1, postOrder, j);
        i += len;
        j += len;
        if (postOrder[j] != root.val) {
            BTNode right = new BTNode(preOrder[i]);
            root.right = right;
            len += 1 + addChildren(right, preOrder, i+1, postOrder, j);
        }
        return len;
    }

    // return length of child
    int addChild(BTNode root, int[] pre, int p1, int[] post, int p2) {
        // no more child
        if (post[p2]==root.val) {
            return 0;
        }
        // child is pre[s1]
        BTNode child=new BTNode(pre[p1]);

        /*
        if (root.left==null) {
            root.left=child;
        } else {
            root.right=child;
        }
        */
        // add to right just for fun
        if (root.right==null) {
            root.right=child;
        } else {
            root.left=root.right;
            root.right=child;
        }

        // add children on the child
        return 1+addChildren(child,pre,p1+1,post,p2);
    }
    // return length of children
    int addChildren2(BTNode root, int[] pre, int p1, int[] post, int p2) {
        int len=0;
        for (int i=0;i<2;i++) {
            // try twice for left and right child
            len+=addChild(root,pre,p1+len,post,p2+len);
        }
        return len;
    }

    /*
    use O(n^2) time, O(n) space
    use Map will be easier to find the end of the left
     */
     BTNode constructFromPrePost(int[] pre, int[] post) {
        if (pre == null || pre.length == 0 || post == null || post.length == 0 || pre.length != post.length) {
            return null;
        }

        Map<Integer, Integer> postMap = new HashMap<>();

        for (int i = 0; i < post.length; i++) {
            postMap.put(post[i], i);
        }

        return buildTreeHelper(pre, 0, pre.length - 1, post, 0, postMap);
    }

     BTNode buildTreeHelper(int[] pre, int preStart, int preEnd, int[] post, int postStart, Map<Integer, Integer> postMap) {
        if (preStart > preEnd) {
            return null;
        }

        BTNode node = new BTNode(pre[preStart]);

        if (preStart == preEnd) {
            return node;
        }

        int leftChildPostIndex = postMap.get(pre[preStart + 1]); // ****** find the end of the left tree *********
        int numLeft = leftChildPostIndex - postStart + 1;

        node.left = buildTreeHelper(pre, preStart + 1, preStart + numLeft, post, postStart, postMap);
        node.right = buildTreeHelper(pre, preStart + numLeft + 1, preEnd, post, leftChildPostIndex + 1, postMap);
        return node;
    }
}
