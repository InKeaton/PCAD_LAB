import java.util.concurrent.*; 

public class Swimmer implements Runnable {

    private Semaphore locker_key;
    private Semaphore locker_room_key;
    private String name;

    public Swimmer(Semaphore locker_key, Semaphore locker_room_key, String name) { 
        this.name = name; 
        this.locker_key = locker_key; 
        this.locker_room_key = locker_room_key; 
    };

    @Override public void run() {
        try {
            /*
             * Una soluzione possibile potrebbe essere quella che il nuotatore nel momento 
             * in cui lascia la chiave dello spogliatoio lascia anche quella dell'armadietto.
             * Oppure che il nuotatore non lascia la chiave dello spogliatoio finchè non lascia 
             * anche quella dell'armadietto.
             * Oppure che il nuotatore lascia la chiave dell'armadietto ma non dello spogliatoio
             * sapendo che un'altro nuotatore non può accedere alla armadietto senza poter
             * accedere allo spogliatoio.
             */
            System.out.println("[" + this.name + "]\tEntro in piscina");
            // prende la chiave dello spogliatoio
            System.out.println("[" + this.name + "]\tRichiedo la chiave dello spogliatoio");

            locker_room_key.acquire();
            System.out.println("[" + this.name + "]\tRicevo la chiave dello spogliatoio");
            // prende la chiave dell"armadietto
            System.out.println("[" + this.name + "]\tRichiedo la chiave dell\'armadietto");
            locker_key.acquire();
            System.out.println("[" + this.name + "]\tRicevo la chiave dell\'armadietto");
            // si cambia nello spogliatoio
            System.out.println("[" + this.name + "]\tMi metto il costume");
            Thread.sleep(10);
            // libera lo spogliatoio
            System.out.println("[" + this.name + "]\tLibero lo spogliatoio");
            Thread.sleep(10);
            // mette i vestiti nell"armadietto
            System.out.println("[" + this.name + "]\tMetto i vestiti nell\'armadietto");
            Thread.sleep(10);
            System.out.println("[" + this.name + "]\tLascio la chiave dell\'armadietto");
            locker_key.release();
            // lascia la chiave dello spogliatoio
            System.out.println("[" + this.name + "]\tLascio la chiave dello spogliatoio");
            locker_room_key.release();

            // nuota
            System.out.println("[" + this.name + "]\tNuoto per un po\"...");
            Thread.sleep(100);
            // prende la chiave dello spogliatoio
            System.out.println("[" + this.name + "]\tRichiedo la chiave dello spogliatoio");

            locker_room_key.acquire();
            System.out.println("[" + this.name + "]\tRicevo la chiave dello spogliatoio");
            //  riprendo la chiave dell'armadietto
            System.out.println("[" + this.name + "]\tRichiedo la chiave dell\'armadietto");
            locker_key.acquire();
            // prende i vestiti
            System.out.println("[" + this.name + "]\tPrendo i vestiti");
            Thread.sleep(10);
            // si veste
            System.out.println("[" + this.name + "]\tMi rimetto i vestiti");
            Thread.sleep(10);
            // libera lo spogliatoio
            System.out.println("[" + this.name + "]\tLibero lo spogliatoio");
            Thread.sleep(10);
            // lascia la chiave dell"armadietto
            System.out.println("[" + this.name + "]\tLascio la chiave dell\'armadietto");
            locker_key.release();
            // lascia la chiave dello spogliatoio
            System.out.println("[" + this.name + "]\tLascio la chiave dello spogliatoio");
            locker_room_key.release();

            System.out.println("[" + this.name + "]\tTorno a casa");
            System.out.println("[" + this.name + "]\tF I N E");
        } catch(Exception e) {
			System.out.println("Errore nel lancio del thread: " + e.getMessage());
		}
    };
}
