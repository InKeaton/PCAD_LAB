public class Writer implements Runnable {

    // RWext object to manage
    private final RWext book;
    
    // Constructor
    public Writer(RWext new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Update book value
    public void run(){
        this.book.write();
    }
}
