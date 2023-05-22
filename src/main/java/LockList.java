import java.util.concurrent.locks.ReentrantLock;

public class LockList extends SortList {

    ReentrantLock lock = new ReentrantLock();

    public LockList() {
        super();
    }

    @Override
    public boolean add(Integer obj) {
        try {
            lock.lock();

            Entry prev = this.head;
            Entry curr = prev.next;
            while (curr.object.compareTo(obj) < 0) {
                prev = curr;
                curr = prev.next;
            }
            if (curr.object.equals(obj) || prev.object.equals(obj)) {
                return false;
            } else {
                Entry newEntry = new Entry(obj);
                newEntry.next = curr;
                prev.next = newEntry;
                this.listLengthAfterAdds++;
                this.listLengthAfterRemove = this.listLengthAfterAdds;
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(Integer obj) {
        try {
            lock.lock();
            Entry prev = this.head;
            Entry curr = prev.next;
            while (curr.object.compareTo(obj) < 0) {
                prev = curr;
                curr = prev.next;
            }
            if (curr.object.equals(obj)) {
                prev.next = curr.next;
                this.listLengthAfterRemove--;
                this.removeSuccessNumber++;
                return true;
            } else {
                this.removeFailuresNumber++;
                return false;
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public boolean contain(Integer obj) {
        try {
            lock.lock();
            Entry prev = this.head;
            Entry curr = prev.next;
            while (curr.object.compareTo(obj) < 0) {
                prev = curr;
                curr = prev.next;
            }
            if (curr.object.equals(obj) || prev.object.equals(obj)) {
                this.containSuccessNumber++;
                return true;
            } else {
                this.containFailuresNumber++;
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}
