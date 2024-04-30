public class Reader implements Runnable {

    // RWexclusive object to manage
    private final RWexclusive book;

    // Constructor
    public Reader(RWexclusive new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Read book value
    public void run(){
        System.out.println("Reader: " + Thread.currentThread().getName() + "\tdata: " + this.book.read());
    }
}
