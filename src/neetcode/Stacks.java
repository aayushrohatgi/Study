package neetcode;

import java.util.*;
import java.util.stream.Collectors;

public class Stacks {

    public boolean isValid(String s) {
        boolean isValid = s.length() % 2 == 0;
        if (isValid) {
            Map<Character, Character> parenthesisMap = new HashMap<>();
            parenthesisMap.put(')', '(');
            parenthesisMap.put('}', '{');
            parenthesisMap.put(']', '[');
            Stack<Character> parenthesisStack = new Stack<>();
            for (char ch : s.toCharArray()) {
                if (!parenthesisMap.containsKey(ch)) {
                    parenthesisStack.push(ch);
                } else {
                    if (!parenthesisStack.empty() && parenthesisStack.peek() == parenthesisMap.get(ch)) {
                        parenthesisStack.pop();
                    } else {
                        isValid = false;
                        break;
                    }
                }
            }
            if (isValid) {
                isValid = parenthesisStack.empty();
            }
        }
        return isValid;
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> polishStack = new ArrayDeque<>();
        Set<String> operators = Set.of("+", "-", "/", "*");
        int finalResult = 0;

        for (String token : tokens) {
            if (operators.contains(token)) {
                int b = polishStack.pop();
                int a = polishStack.pop();
                polishStack.push(performOperation(a, b, token));
            } else {
                polishStack.push(Integer.parseInt(token));
            }
        }
        if (!polishStack.isEmpty()) {
            finalResult = polishStack.pop();
        }
        return finalResult;
    }

    private Integer performOperation(int a, int b, String token) {
        int result = 0;
        switch (token) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
        }
        return result;
    }

    public List<String> generateParenthesis(int n) {
        List<String> validParenthesis = new ArrayList<>();
        generateParenthesisRec(new StringBuilder(), n, n, validParenthesis);
        return validParenthesis;
    }

    private void generateParenthesisRec(StringBuilder current, int open, int close, List<String> validParenthesis) {
        if (open == 0 && close == 0) {
            validParenthesis.add(current.toString());
        } else {
            if (open > 0) {
                current.append('(');
                open--;
                generateParenthesisRec(current, open, close, validParenthesis);
                current.deleteCharAt(current.length() - 1);
                open++;
            }
            if (close > open) {
                current.append(')');
                close--;
                generateParenthesisRec(current, open, close, validParenthesis);
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int[] results = new int[temperatures.length];
        Deque<Integer> warmerDeque = new ArrayDeque<>();

        for (int i = temperatures.length - 1; i >= 0; i--) {
            // If stack is not empty pop till we find a hotter day
            while (!warmerDeque.isEmpty() && temperatures[i] >= temperatures[warmerDeque.peek()]) {
                warmerDeque.pop();
            }

            // hotter day found, compute result in accordance to current index
            // no need to compute for cases where stack is empty, as it indicates no hotter days ahead and default is 0
            if (!warmerDeque.isEmpty()) {
                results[i] = warmerDeque.peek() - i;
            }

            // push the current index for future comparisons
            warmerDeque.push(i);
        }

        return results;
    }

    public int carFleet(int target, int[] position, int[] speed) {
        int fleets = 0;
        Map<Integer, Double> positionToMinTimeMap = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < position.length; i++) {
            positionToMinTimeMap.put(position[i], (1.0 * target - position[i]) / speed[i]);
        }
        double currentMinTime = 0;
        for (var place : positionToMinTimeMap.keySet()) {
            if (positionToMinTimeMap.get(place) > currentMinTime) {
                fleets++;
                currentMinTime = positionToMinTimeMap.get(place);
            }
        }
        return fleets;
    }

    /*
        This needs explanation, we only want to store limiting heights in the stack for computation
        Limiting height will become left boundary only if there are other limitations
        If no limitations then consider left boundary as start, why?
        Left boundary can not reach start only if there is a 0 in the middle that breaks the continuity.
        Since 0 will become limiting even if we compute empty stack left boundary as -1
        it will cancel it so we do not need to worry about wrong computation
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            // Treat i==n as a bar of height 0 to flush remaining stack
            int currHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int top = stack.pop();
                int height = heights[top];

                // Left boundary: stack.peek() after pop or -1 if empty
                int left = stack.isEmpty() ? -1 : stack.peek();

                int width = i - left - 1;
                int area = height * width;
                maxArea = Math.max(maxArea, area);
            }

            stack.push(i);
        }

        return maxArea;
    }
}
