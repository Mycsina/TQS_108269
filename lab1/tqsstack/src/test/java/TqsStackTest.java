import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TqsStackTest {
    private TqsStack<Object> stack;

    @BeforeEach
    void setup() {
        stack = new TqsStack<>();
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testIs0Size() {
        assertEquals(0, stack.size());
    }

    @Test
    public void testPush() {
        int n = (int) (Math.random() * 100);
        for (int i = 0; i < n; i++) {
            stack.push(new Object());
        }
        assertFalse(stack.isEmpty());
        assertEquals(n, stack.size());
    }

    @Test
    public void testPop() {
        Object o = new Object();
        stack.push(o);
        assertEquals(o, stack.pop());
    }

    @Test
    public void testPeek() {
        Object o = new Object();
        stack.push(o);
        int s = stack.size();
        assertEquals(o, stack.peek());
        assertEquals(s, stack.size());
    }

    @Test
    public void testEmptyTheStack() {
        int n = (int) (Math.random() * 100);
        for (int i = 0; i < n; i++) {
            stack.push(new Object());
        }
        for (int i = 0; i < n; i++) {
            stack.pop();
        }
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void testPopEmpty() {
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    public void testPeekEmpty() {
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    public void testPushFull() {
        int n = (int) (Math.random() * 100);
        stack = new TqsStack<>(n);
        for (int i = 0; i < n; i++) {
            stack.push(new Object());
        }
        assertThrows(IllegalStateException.class, () -> stack.push(new Object()));
    }

}
