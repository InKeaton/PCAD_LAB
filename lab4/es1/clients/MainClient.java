import java.util.*;


public class MainClient {
	
	public static void main(String[] args) {
		try {
			int numProd = 100, numCons = 101;
			Thread[] tP = new Thread[numProd];
			Thread[] tC = new Thread[numCons];
			for(int i = 0; i < tP.length; i++) {
				tP[i] = new Thread(new Producer(6969));
			}

			for(int i = 0; i < tC.length; i++) {
				tC[i] = new Thread(new Consumer(6969));
			}

			for(int i = 0; i < tP.length; i++) {
				tP[i].start();
				tP[i].join();
			}

			for(int i = 0; i < tC.length; i++) {
				tC[i].start();
				tC[i].join();
			}

			System.out.println("Thraed eseguiti: "    + (numCons + numProd));
			System.out.println("Thraed consumatori: " + numCons);
			System.out.println("Thraed produttori: "  + numProd);

		} catch(Exception e) {
			System.out.println("Errore nel lancio del thread: " + e.getMessage());
		}
	}
}


