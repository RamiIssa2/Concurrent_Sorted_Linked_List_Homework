import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockList extends SortList {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock localLock = new ReentrantReadWriteLock();

    public RWLockList() {
        super();
    }

    @Override
    public boolean add(Integer obj) {
        try {
            lock.writeLock().lock();

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
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean remove(Integer obj) {
        Entry prev = this.head;
        Entry curr = prev.next;
        while (curr.object.compareTo(obj) < 0) {
            prev = curr;
            curr = prev.next;
        }
        if (curr.object.equals(obj)) {
            try {
                prev.lock.writeLock().lock();
                prev.next = curr.next;
            } finally {
                prev.lock.writeLock().unlock();
            }
            this.listLengthAfterRemove--;
            this.removeSuccessNumber++;
            return true;
        } else {
            this.removeFailuresNumber++;
            return false;
        }

    }

    @Override
    public boolean contain(Integer obj) {
        try {
            lock.readLock().lock();
            Entry prev = this.head;
            Entry curr = prev.next;
            while (curr.object.compareTo(obj) < 0) {
                prev = curr;
                curr = prev.next;
            }
            try {
                localLock.writeLock().lock();
                if (curr.object.equals(obj) || prev.object.equals(obj)) {
                    this.containSuccessNumber++;
                    return true;
                } else {
                    this.containFailuresNumber++;
                    return false;
                }
            } finally {
                localLock.writeLock().unlock();
            }
        } finally {
            lock.readLock().unlock();
        }
    }

}
