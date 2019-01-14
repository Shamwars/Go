/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {
  protected int x;
  protected int y;
  protected long[] arrayStack;

  public Stack(int size) {
    x = 0;
    y = 0;
    arrayStack = new long[size];
  }

  public Stack(Stack st) {
    x = st.x;
    y = st.y;
    arrayStack = Arrays.copyOf(st.arrayStack, st.arrayStack.length);
  }

  public boolean isEmpty() {
    return (x == y);
  }

  public boolean isFull() {
    return (x == arrayStack.length);
  }

  public int size() {
    return x;
  }

  public void push(long element) {
    if (isFull()) {
      arrayStack = Arrays.copyOf(arrayStack,
          (int) Math.floor(arrayStack.length * 1.2));
    }
    arrayStack[++x] = element;
  }

  public long pop() throws EmptyStackException {
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    return arrayStack[x--];
  }

  public long peek() throws EmptyStackException {
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    return arrayStack[x];
  }
}
