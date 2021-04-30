import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomConcurrentStringList {
    Node head;
    Lock lock = new ReentrantLock();

    public void add(String str) {
        Node node = new Node(str);
        try {
            lock.lock();
            if (head == null) {
                head = node;
                return;
            }
        }
        finally {
            lock.unlock();
        }

        Node ptr = head;
        try {
            ptr.lock.lock();
            while (ptr.next != null) {
                Node tmp = ptr;
                try {
                    ptr.next.lock.lock();
                    ptr = ptr.next;
                }
                finally {
                    tmp.lock.unlock();
                }
            }
            ptr.next = node;
        }
        finally {
            ptr.lock.unlock();
        }
    }

    public boolean contains(String str) {
        Node ptr = head;
        while (ptr != null) {
            Node tmp = ptr;
            try {
                ptr.lock.lock();
                if (str.equals(ptr.string)) {
                    return true;
                }
                ptr = ptr.next;
            }
            finally {
                tmp.lock.unlock();
            }
        }
        return false;
    }

    private class Node {
        Node next;
        String string;
        Lock lock = new ReentrantLock();

        public Node(String string) {
            this.string = string;
        }
    }
}
