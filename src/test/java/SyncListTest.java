import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SyncListTest extends TestCase {

    int randNumsLength = 50_000;
    int randNumsRange = 80_000;
    int setSeed = 0;

    public void testAddList() {

        SyncList syncList = new SyncList();

        syncList.add(1);
        syncList.add(2);
        syncList.add(3);
        syncList.add(Integer.MIN_VALUE);
        syncList.add(3);
        System.out.println(syncList.contain(5));
        System.out.println(syncList.contain(2));
        syncList.remove(3);

        syncList.printList();


    }

    public void testRandom() {
        RandomSequenceGenerator randomSequenceGenerator = new RandomSequenceGenerator(0, 1000);
        for (int i = 0; i < 10; i++) {
            System.out.print(randomSequenceGenerator.next() + " ");
        }
    }

    public void testHelp(SortList list, String label) {
        RandomSequenceGenerator randomSequenceGenerator = new RandomSequenceGenerator(setSeed, randNumsRange);
        List<Thread> addThreads = new ArrayList<>();
        List<Thread> removeThreads = new ArrayList<>();
        List<Thread> containThreads = new ArrayList<>();
        int processors = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < processors - 1; i++) {
            AddThread addThread = new AddThread(randomSequenceGenerator, randNumsLength / (processors - 1), list);
            RemoveThread removeThread = new RemoveThread(randomSequenceGenerator, randNumsLength / (processors - 1), list);
            ContainThread containThread = new ContainThread(randomSequenceGenerator, randNumsLength / (processors - 1), list);
            Thread thread_a = new Thread(addThread);
            Thread thread_r = new Thread(removeThread);
            Thread thread_c = new Thread(containThread);
            addThreads.add(thread_a);
            removeThreads.add(thread_r);
            containThreads.add(thread_c);
        }

        long start_a = System.currentTimeMillis();

        addThreads.stream().forEach(e -> e.start());
        addThreads.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        long end_a = System.currentTimeMillis() - start_a;

        System.out.printf("ADD %s execution takes: (%s ms) on %s processors.%n", label, end_a, processors);
        if (!list.isSorted()) {
            System.out.println("List isn't sorted after add.");
        }
        System.out.printf("List length after add: %s.%n", list.listLengthAfterAdds);

        long start_c = System.currentTimeMillis();

        containThreads.stream().forEach(e -> e.start());
        containThreads.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        long end_c = System.currentTimeMillis() - start_c;

        System.out.printf("Contain %s execution takes: (%s ms) on %s processors.%n", label, end_c, processors);
        System.out.printf("Total number of successes found: %s, failures found: %s.%n", list.containSuccessNumber, list.containFailuresNumber);

        long start_r = System.currentTimeMillis();

        removeThreads.stream().forEach(e -> e.start());
        removeThreads.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        long end_r = System.currentTimeMillis() - start_r;

        System.out.printf("Remove %s execution takes: (%s ms) on %s processors.%n", label, end_r, processors);
        if (!list.isSorted()) {
            System.out.println("List isn't sorted after remove.");
        }
        System.out.printf("List length after remove: %s.%n", list.listLengthAfterRemove);
        System.out.printf("Total number of successes removed: %s, failures removed: %s.%n", list.removeSuccessNumber, list.removeFailuresNumber);

    }

    public void testRun() {
        SyncList syncList = new SyncList();
        testHelp(syncList, "Synchronization");
        System.out.println("===============");
        RWLockList rwLockList = new RWLockList();
        testHelp(rwLockList, "RWLock");
        System.out.println("===============");
        LockList lockList = new LockList();
        testHelp(lockList, "Lock");
    }
}
