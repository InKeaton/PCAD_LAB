public class Writer implements Runnable {

    // RWbasic object to manage
    private final RWbasic book;
    
    // Constructor
    public Writer(RWbasic new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Update book value
    public void run(){
        this.book.write();
    }
}
