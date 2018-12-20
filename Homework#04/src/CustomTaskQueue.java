import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomTaskQueue{
    Node head;
    Node tail;
    Lock lock = new ReentrantLock();

    public void offer(Runnable task) {
        Node node = new Node(task);
        try{
            lock.lock();
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        finally {
            lock.unlock();
        }

    }

    public Runnable poll() {
        try {
            lock.lock();
            if (head != null) {
                Node tmp = head;
                head = head.next;
                if (tmp == tail) {
                    tail = null;
                }
                return tmp.task;
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    private class Node {
        Node next;
        Runnable task;

        public Node(Runnable task) {
            this.task = task;
        }
    }
}
