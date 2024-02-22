import java.util.LinkedList;

public class TqsStack<T> {
    LinkedList<T> stack = new LinkedList<>();
    int capacity = 0;

    public TqsStack() {
    }

    public TqsStack(int capacity) {
        stack = new LinkedList<>();
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void push(T o) {
        if (capacity > 0 && stack.size() == capacity) {
            throw new IllegalStateException("Stack is full");
        }
        stack.add(o);
    }

    public T pop() {
        return stack.removeLast();
    }

    public int size() {
        return stack.size();
    }

    public T peek() {
        return stack.getLast();
    }
}
