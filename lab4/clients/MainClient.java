import java.util.*;

public class MainClient {
	
	public static void main(String[] args) {
		try {
			Thread t = new Thread(new Consumer(6969));
			t.start();
			t.join();
		} catch(Exception e) {
			System.out.println("Errore nel lancio del thread: " + e.getMessage());
		}
	}
}


