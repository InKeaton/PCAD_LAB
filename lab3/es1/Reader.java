public class Reader implements Runnable{
    private final RWbasic rw;
    public Reader(RWbasic rw){
        this.rw = rw;
    }

    public void run(){
        System.out.println("Reader: " + Thread.currentThread().getName() + "\tdata: " + rw.read());
    }
}
