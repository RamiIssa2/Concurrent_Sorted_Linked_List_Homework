public class SyncList extends SortList {

    public SyncList() {
        super();
    }

    @Override
    public synchronized boolean add(Integer obj) {
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
    }

    @Override
    public synchronized boolean remove(Integer obj) {
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

    }

    @Override
    public synchronized boolean contain(Integer obj) {
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
    }
}
