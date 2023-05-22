public class AddThread extends TestThread implements Runnable {

    public AddThread(RandomSequenceGenerator seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            this.list.add(nums[i]);
        }
    }
}
