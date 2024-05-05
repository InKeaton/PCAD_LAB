public class Main {

    // Number of threads of each type
    private static int NUMBER_OF_THREADS = 50;

    public static void main(String[] args) throws Exception {

        // RWext object to be shared
        RWext BOOK = new RWext();

        // Threads arrays
        Thread[] Writers = new Thread[NUMBER_OF_THREADS];
        Thread[] Readers = new Thread[NUMBER_OF_THREADS];

        // Book's writer and reader objects
        Writer BOOK_WRITER = new Writer(BOOK);
        Reader BOOK_READER = new Reader(BOOK);

        // Initialize and start all of the threads
        for(int i = 0; i < NUMBER_OF_THREADS; i++){
            Writers[i] = new Thread(BOOK_WRITER, "W" + ((Integer)(i+1)).toString());
            Writers[i].start();

            Readers[i] = new Thread(BOOK_READER, "R" + ((Integer)(i+1)).toString());
            Readers[i].start();
        }
        
        // Join all threads
        for(Integer i = 0; i < NUMBER_OF_THREADS; i++){
            Writers[i].join();
            Readers[i].join();
        }

    }
}
