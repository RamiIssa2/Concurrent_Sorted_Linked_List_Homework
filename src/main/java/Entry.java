import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Entry {

    public Integer object;

    public Entry next;

    public ReentrantReadWriteLock lock;

    public Entry(Integer object) {
        this.object = object;
        this.lock = new ReentrantReadWriteLock();
    }
}
