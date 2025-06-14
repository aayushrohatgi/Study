package dtos;

import java.util.Stack;

// It will be ensured the pop, top and getMin will never be called on empty stack
public class MinStack {

    private final Stack<Pair> theStack;

    public MinStack() {
        theStack = new Stack<>();
    }

    public void push(int val) {
        int min;
        if (theStack.empty()) {
            min = val;
        } else {
            min = Math.min(val, theStack.peek().getY());
        }
        theStack.push(new Pair(val, min));
    }

    public void pop() {
        theStack.pop();
    }

    public int top() {
        return theStack.peek().getX();
    }

    public int getMin() {
        return theStack.peek().getY();
    }
}
