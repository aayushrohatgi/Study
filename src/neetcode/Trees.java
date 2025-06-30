package neetcode;

import dtos.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Trees {

    public TreeNode invertTree(TreeNode root) {
        if (null != root) {
            var left = root.left;
            root.left = root.right;
            root.right = left;
            invertTree(root.left);
            invertTree(root.right);
        }
        return root;
    }

    public int maxDepth(TreeNode root) {
        return calculateDepthOfTree(root, 0);
    }

    private int calculateDepthOfTree(TreeNode root, int currentDepth) {
        if (null == root) {
            return currentDepth;
        } else {
            currentDepth++;
            return Math.max(
                    calculateDepthOfTree(root.left, currentDepth),
                    calculateDepthOfTree(root.right, currentDepth)
            );
        }
    }

    /*
        To find the max diameter we need to check diameters from bottom to up
        If current node can be used in max diameter than the best path will have
        most depth path from left + right subtree,
        so while calculating max depths return depths till now to upper node for computing its max path
        along with the maxDiameter found till now
     */
    public int diameterOfBinaryTree(TreeNode root) {
        return calculateMaxDiameterAndDepth(root).maxDiameter;
    }

    private static class ResultMaxDiameter {
        int depth;
        int maxDiameter;

        ResultMaxDiameter(int depth, int maxDiameter) {
            this.depth = depth;
            this.maxDiameter = maxDiameter;
        }
    }

    private ResultMaxDiameter calculateMaxDiameterAndDepth(TreeNode root) {
        if (root == null) {
            return new ResultMaxDiameter(0, 0);
        }
        ResultMaxDiameter left = calculateMaxDiameterAndDepth(root.left);
        ResultMaxDiameter right = calculateMaxDiameterAndDepth(root.right);
        int depth = Math.max(left.depth, right.depth) + 1;
        int diameterThroughNode = left.depth + right.depth;
        int maxDiameter = Math.max(diameterThroughNode, Math.max(left.maxDiameter, right.maxDiameter));
        return new ResultMaxDiameter(depth, maxDiameter);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        boolean isSame;
        if (null == p && null == q) {
            isSame = true;
        } else if (null != p && null != q && p.val == q.val) {
            isSame = isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            isSame = false;
        }
        return isSame;
    }

    public boolean isBalanced(TreeNode root) {
        return computeBalanceAndDepth(root).isBalanced;
    }

    private ResultIsBalanced computeBalanceAndDepth(TreeNode root) {
        if (root == null) {
            return new ResultIsBalanced(true, 0);
        }
        var leftResult = computeBalanceAndDepth(root.left);
        var rightResult = computeBalanceAndDepth(root.right);
        var isBalanced = leftResult.isBalanced && rightResult.isBalanced && Math.abs(leftResult.depth - rightResult.depth) <= 1;
        return new ResultIsBalanced(isBalanced, Math.max(leftResult.depth, rightResult.depth) + 1);
    }

    private static class ResultIsBalanced {
        boolean isBalanced;
        int depth;

        ResultIsBalanced(boolean isBalanced, int depth) {
            this.isBalanced = isBalanced;
            this.depth = depth;
        }
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }
        if (isSameTree(root, subRoot)) {
            return true;
        }
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    /*
        dfs, return node if p aur q is encountered else null,
        if left and right both return value the common ancestor found, i
        f one side return null pass p aur q,
        if ancestor found return ancestor
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == p) {
            return p;
        }
        if (root == q) {
            return q;
        }
        var inLeft = lowestCommonAncestor(root.left, p, q);
        var inRight = lowestCommonAncestor(root.right, p, q);
        if (null != inLeft && null != inRight) {
            return root;
        } else if (null != inLeft) {
            return inLeft;
        } else return inRight; // if this is null then return null as that was the default case
    }

    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else if (p.val < root.val && q.val < root.val) {
                root = root.left;
            } else {
                return root;
            }
        }
        return root;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root != null) {
            Deque<TreeNode> queue = new ArrayDeque<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                List<Integer> level = new ArrayList<>();
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    level.add(node.val); // ArrayDeque cant hold null values
                    if (null != node.left) {
                        queue.add(node.left);
                    }
                    if (null != node.right) {
                        queue.add(node.right);
                    }
                }
                levels.add(level);
            }
        }
        return levels;
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> rightViewNodes = new ArrayList<>();
        if (root != null) {
            Deque<TreeNode> queue = new ArrayDeque<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    if (i == levelSize - 1) {
                        rightViewNodes.add(node.val); // ArrayDeque cant hold null values
                    }
                    if (null != node.left) {
                        queue.add(node.left);
                    }
                    if (null != node.right) {
                        queue.add(node.right);
                    }
                }
            }
        }
        return rightViewNodes;
    }

    public int goodNodes(TreeNode root) {
        return collectGoodNodes(root, root.val);
    }

    private int collectGoodNodes(TreeNode root, int maxTillNow) {
        if (root == null) {
            return 0;
        }
        if (maxTillNow <= root.val) {
            maxTillNow = root.val;
            return collectGoodNodes(root.left, maxTillNow) + collectGoodNodes(root.right, maxTillNow) + 1;
        } else {
            return collectGoodNodes(root.left, maxTillNow) + collectGoodNodes(root.right, maxTillNow);
        }
    }

}
