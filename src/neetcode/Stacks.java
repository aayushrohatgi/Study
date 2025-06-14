package neetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

    // CHECK THE MinStack class for 2nd question

    
}
