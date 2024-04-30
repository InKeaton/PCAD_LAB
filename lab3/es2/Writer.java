public class Writer implements Runnable {

    // RWexclusive object to manage
    private final RWexclusive book;
    
    // Constructor
    public Writer(RWexclusive new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Update book value
    public void run(){
        this.book.write();
    }
}
