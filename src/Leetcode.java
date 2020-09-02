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



    public static void main(String[] args){
        Leetcode leetcode = new Leetcode();
        int[] nums = {3,2,6,5,0,3};  //
        System.out.println(leetcode.maxProfit(nums));
    }
}


