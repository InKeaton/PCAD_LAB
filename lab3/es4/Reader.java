public class Reader implements Runnable {

    // RWext object to manage
    private final RWext book;

    // Constructor
    public Reader(RWext new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Read book value
    public void run(){
        System.out.println("Reader: " + Thread.currentThread().getName() + "\tdata: " + this.book.read());
    }
}
