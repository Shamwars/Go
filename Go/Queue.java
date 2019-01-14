/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;
import java.util.Arrays;

public class Queue{
	int x;
	int y;
	long [] arrayQueue;
	int maxCapacity;

	public Queue(int maxCapacity) {
		x = 0;
		y = 0;
		arrayQueue = new long[maxCapacity];
		this.maxCapacity = maxCapacity;
	}

    public Queue(Queue q) {
        x = 0;
        y = 0;
        this.maxCapacity = q.maxCapacity;
        arrayQueue = Arrays.copyOf(q.arrayQueue, q.arrayQueue.length);
    }

	public boolean isEmpty() {
		return (x == y);
	}

	public boolean isFull() {
		return (y == maxCapacity);
	}

	public void enqueue(long element) {
		if(x == y) {
			arrayQueue[y++] = element;
		} else {
			if(isFull()) {
				System.out.println("sorry we're full");
			} else {
				arrayQueue[y++] = element;
			}
		}
	}

	public long dequeue() {
		if(isEmpty()) {
			System.out.println("Sorry its empty");
			return -1;
		}else {
			return arrayQueue[x++];
		}
	}

	public long tail() {
		if(isEmpty()) {
			System.out.println("Sorry its empty");
			return -1;
		} else {
			return arrayQueue[y - 1];
		}
	}

	public long front() {
		if(isEmpty()) {
			System.out.println("Sorry its empty");
			return -1;
		} else {
			return arrayQueue[x];
		}
	}

	public int size() {
		return (y - x);
	}
}

