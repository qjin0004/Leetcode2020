import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Leetcode {

    //Q557
    public String reverseWords(String s) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != ' '){
                stringBuffer.append(s.charAt(i));
            }
            if(s.charAt(i) != ' ' || i == s.length()-1){
                result.append(stringBuffer.reverse());
                result.append(" ");
                stringBuffer.delete(0,stringBuffer.length());
            }
        }
        return result.toString();
    }

    //121
    public int maxProfit121(int[] prices) {
        if(prices.length==0) return 0;
        int min = prices[0]; int result = 0;
        for(int i = 1; i < prices.length; i++){
            result = (prices[i] > min && prices[i]-min > result)? prices[i]-min : result;
            min = (prices[i] > min) ? min : prices[i];
        }
        return result;
    }

    //122
    public int maxProfit(int[] prices) {
        if(prices.length == 0) return 0;
        int min = prices[0]; int sum = 0;
        for(int i = 1; i < prices.length; i++){
            if(prices[i] < min && min != -1 ){
                min = prices[i];
            }else if(min != -1 && i+1 < prices.length && prices[i] > prices[i+1]){
                sum += (prices[i]-min);
                min = -1;
            } else if(min == -1){
                min = prices[i];
            } else if(i == prices.length-1 && min != -1){
                sum += (prices[i]-min);
            }
        }
        return sum;
    }

    //offer 09
    class CQueue {
        Stack stack;
        Stack backStack;

        public CQueue() {
            stack = new Stack();
            backStack = new Stack();
        }

        public void appendTail(int value) {
            stack.add(value);
        }

        public int deleteHead() {
            while(!stack.isEmpty()){
                backStack.add(stack.pop());
            }
            int value = (backStack.size() > 0)? (int)backStack.pop() : -1;
            while(!backStack.isEmpty()){
                stack.add(backStack.pop());
            }
            return value;
        }
    }

    //offer 20
    public boolean isNumber(String s) {
        boolean result = true; boolean hasE = false; boolean hasDot = false; boolean hasNum = false;
        if(s.trim().length() == 0) return false;
        for(int i = 0; i < s.trim().length(); i++){
            if(hasNum == false && (s.trim().charAt(i) == '+' || s.trim().charAt(i) == '-') && i != s.trim().length()-1){
                continue;
            }else if(hasNum == false && Character.isDigit(s.trim().charAt(i))){
                hasNum = true;
            }else if(hasE == false && hasDot == false && s.trim().charAt(i) == '.'&& ((i-1 >=0 && Character.isDigit(s.trim().charAt(i-1))) || (i+1 < s.trim().length() && Character.isDigit(s.trim().charAt(i+1))))){
                hasDot = true;
            }else if(hasNum == true && hasE == false && s.trim().toLowerCase().charAt(i) == 'e'){
                hasE = true; hasNum = false;
            }else if(Character.isDigit(s.trim().charAt(i))){
                continue;
            } else{
                result = false; break;
            }
        }
        if(hasNum == false) return false;
        return result;
    }


    //112
    public boolean hasPathSum(TreeNode root, int sum) {
        Stack<TreeNode> stack = new Stack<>();
        boolean res = false;
        if(root == null) return false;
        stack.add(root);
        while(!stack.isEmpty()){
            TreeNode temp = stack.pop();
            if(temp.left == null && temp.right == null && temp.val == sum) {
                res = true; break;
            }
            if(temp.left != null){
                temp.left.val += temp.val;
                stack.add(temp.left);
            }
            if(temp.right != null){
                temp.right.val += temp.val;
                stack.add(temp.right);
            }
        }
        return res;
    }

    //113 回溯
    public List<List<Integer>> pathSum1(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null) return result;
        List<Integer> path = new ArrayList<>();
        path.add(root.val);
        backtrack113(root, path, sum, result);
        return result;
    }

    public void backtrack113(TreeNode node, List<Integer> path, int sum, List<List<Integer>> result){
        if(node.right == null && node.left == null){
            int temp = 0;
            for(Integer e: path){
                temp+=e;
            }
            if(temp == sum){
                result.add(new ArrayList<>(path));
            }
            return;
        }
        if(node.left != null || node.right != null){
            if(node.left != null){
                path.add(node.left.val);
                backtrack113(node.left,path,sum,result);
                path.remove(path.size()-1);
            }
            if(node.right != null){
                path.add(node.right.val);
                backtrack113(node.right, path, sum, result);
                path.remove(path.size()-1);
            }
        }
    }

    //113 DFS 比较特殊尝试使用全剧变量进行接受
    List<List<Integer>> res113 = new ArrayList<>();
    public List<List<Integer>> pathSum113(TreeNode root, int sum){
        dfsHelper(root, new ArrayList<>(),sum);
        return res113;
    }

    public void dfsHelper(TreeNode node, List<Integer> path, int sum){
        if(node == null){
            return;
        }
        path.add(node.val);
        if(node.left == null && node.right == null && node.val == sum){
            res113.add(new ArrayList<>(path));
        }
        dfsHelper(node.left, path, sum-node.val);
        dfsHelper(node.right, path, sum-node.val);
        path.remove(path.size()-1);
    }

    //437 路径总和3 --从节点向上遍历方式
    public int pathSum(TreeNode root, int sum) {
        return helper(root,sum, new ArrayList<>(), 0);
    }

    //cache 用来存储tree上的点位，index用来记录当前node的点位
    //相当于把tree变成了一个array，通过index知道哪些是在tree上层级的，可以进行从下至上的操作
    //较好题解： https://leetcode-cn.com/problems/path-sum-iii/solution/di-gui-jie-fa-by-tian-ye/
    public int helper(TreeNode node, int sum, List<Integer> cache, int index){
        if(node == null) return 0;
        cache.add(index,node.val);
        int temp = 0; int n = 0; //记录当前点到root的路径中符合条件的个数
        for(int i = index;i >= 0; i--){
            temp += cache.get(i);// 计算到目前为止路径总和，是否有符合条件
            if(temp == sum){
                n++;
            }
        }
        int left = helper(node.left, sum, cache, index+1);
        int right = helper(node.right, sum, cache, index+1);
        return n+left+right; //从node点开始 include自身 +左分支+右分支
    }





    public static void main(String[] args){
        Leetcode leetcode = new Leetcode();
        int[] nums = {3,2,6,5,0,3};  //
        TreeNode t10 = new TreeNode(10);
        TreeNode t5 = new TreeNode(5);
        TreeNode t3$ = new TreeNode(-3);
        TreeNode t3 = new TreeNode(3);
        TreeNode t2 = new TreeNode(2);
        TreeNode t32 = new TreeNode(3);
        TreeNode t2$ = new TreeNode(-2);
        TreeNode t1 = new TreeNode(1);
        TreeNode t11 = new TreeNode(11);

        t10.left = t5; t10.right = t3$; t5.left = t3; t5.right = t2; t3$.right = t11;
        t3.left = t32; t3.right = t2$; t2.right = t1;
        System.out.println(leetcode.pathSum(t10,8));
    }
}


