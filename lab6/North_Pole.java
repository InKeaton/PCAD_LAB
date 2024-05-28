import java.util.concurrent.*; 

public class North_Pole {

    public static void main(String[] args) {
        try {
            int n_reindeers         = 9;
            int n_elfs              = 20;
            int n_elfs_to_be_helped = 3;

            // TO DO;
            //  + Passare le rispettive barriere agli elfi ed alle renne
            //  + Scrivere i due e babo natale
            //  + Scrivere le classi che rappresentano cosa fanno le barriere quando sono tutti raggiunti

            wait_for_santa_to_travel = new CyclicBarrier(n_reindeers,         new Wake_Santa_To_Travel());
            wait_for_santa_to_help   = new CyclicBarrier(n_elfs_to_be_helped, new Wake_Santa_To_Help()  );

            Thread   santa     = new Thread(new Santa());
            Thread[] reindeers = new Thread[n_reindeers];
            Thread[] elfs      = new Thread[n_elfs];

            for(int i = 0; i < reindeers.length; i++) {
                reindeers[i] = new Thread(new Reindeer("Rdr" + i + ""));
            };

            for(int i = 0; i < elfs.length; i++) {
                elfs[i]      = new Thread(new Elf(     "Elf" + i + ""));
            };

            // START ////////////////////////////////////////////////////

            santa.start()

            for(int i = 0; i < reindeers.length; i++) {
                reindeers[i].start();
            };

            for(int i = 0; i < elfs.length; i++) {
                elfs[i].start();
            };

            // JOIN  ////////////////////////////////////////////////////

            for(int i = 0; i < elfs.length; i++) {
                elfs[i].join();
            };

            for(int i = 0; i < reindeers.length; i++) {
                reindeers[i].join();
            };

            santa.join()
            
            System.out.println("[MAIN]\tEsecuzione terminata :)");

        } catch(Exception e) {
            System.out.println("Errore nel lancio del thread: " + e.getMessage());
        }
    }
}
