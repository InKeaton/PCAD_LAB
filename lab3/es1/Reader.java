public class Reader implements Runnable {

    // RWbasic object to manage
    private final RWbasic book;

    // Constructor
    public Reader(RWbasic new_book){
        this.book = new_book;
    }

    // Function ran by the thread
    // Read book value
    public void run(){
        System.out.println("Reader: " + Thread.currentThread().getName() + "\tdata: " + this.book.read());
    }
}
