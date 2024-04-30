public class Reader implements Runnable {

    // RW object to manage
    private final RW book;

    // Constructor
    public Reader(RW new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Read book value
    public void run(){
        System.out.println("Reader: " + Thread.currentThread().getName() + "\tdata: " + this.book.read());
    }
}
