public class App {

    private static int N = 50;
    public static void main(String[] args) throws Exception {
        RWbasic rw = new RWbasic();
        Thread[] Writers = new Thread[N];
        Thread[] Readers = new Thread[N];
        Writer wr = new Writer(rw);
        Reader rd = new Reader(rw);

        for(Integer i = 0; i < N; i++){
            Writers[i] = new Thread(wr);
            Writers[i].start();
            Readers[i] = new Thread(rd,i.toString());
            Readers[i].start();
        }
        
        for(Integer i = 0; i < N; i++){
            Writers[i].join();
            Readers[i].join();
        }
    }
}
