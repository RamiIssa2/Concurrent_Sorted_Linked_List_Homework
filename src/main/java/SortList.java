public abstract class SortList {

    public Entry head;
    public int listLengthAfterAdds;
    public int containSuccessNumber;
    public int containFailuresNumber;
    public int listLengthAfterRemove;
    public int removeSuccessNumber;
    public int removeFailuresNumber;

    public SortList() {
        this.head = new Entry(Integer.MIN_VALUE);
        this.head.next = new Entry(Integer.MAX_VALUE);
        this.listLengthAfterAdds = 0;
        this.containSuccessNumber = 0;
        this.containFailuresNumber = 0;
        this.listLengthAfterRemove = 0;
        this.removeSuccessNumber = 0;
        this.removeFailuresNumber = 0;
    }

    public abstract boolean add(Integer obj);

    public abstract boolean remove(Integer obj);

    public abstract boolean contain(Integer obj);

    public void printList() {
        Entry current = this.head;
        while (current != null) {
            System.out.println(current.object);
            current = current.next;

        }
    }
    
    public boolean isSorted(){
        Entry current = this.head;
        while (current.next != null) {
            if (current.object > current.next.object){
                return false;
            }
            current = current.next;
        }
        return true;
    }
}
