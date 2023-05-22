public class ContainThread extends TestThread implements Runnable {
    public ContainThread(RandomSequenceGenerator seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        for (int i = 0; i < nums.length; i++) {
            this.list.contain(nums[i]);
        }
    }
}
