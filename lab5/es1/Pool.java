import java.util.concurrent.*;

public class Pool {

    public static void main(String[] args) {
        try {
            int n_locker_rooms = 10;
            int n_lockers      = 50;
            int n_swimmers     = 100;

            Semaphore locker_keys      = new Semaphore(n_lockers);
            Semaphore locker_room_keys = new Semaphore(n_locker_rooms);

            Thread[] swimmers = new Thread[n_swimmers];

            for(int i = 0; i < swimmers.length; i++) {
                swimmers[i] = new Thread(new Swimmer(locker_keys, locker_room_keys, "Swim" + i + ""));
            };

            for(int i = 0; i < swimmers.length; i++) {
                swimmers[i].start();
            };

            for(int i = 0; i < swimmers.length; i++) {
                swimmers[i].join();
            };
            
            System.out.println("[MAIN]\tEsecuzione terminata :)");
        } catch(Exception e) {
			System.out.println("Errore nel lancio del thread: " + e.getMessage());
		}
    }
}
