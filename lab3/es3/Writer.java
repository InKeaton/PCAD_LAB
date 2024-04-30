public class Writer implements Runnable {

    // RW object to manage
    private final RW book;
    
    // Constructor
    public Writer(RW new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Update book value
    public void run(){
        this.book.write();
    }
}
