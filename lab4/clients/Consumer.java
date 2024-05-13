//##########################################//
// # PRODUCER                               //
//##########################################//
// + An object wich sends TCP requests to   //
//   a Server                               //
// + The requests contain a string, which   //
//   will be added the the server structure //
//##########################################//

import java.net.*;
import java.io.*;

/*
 	Per i clienti consumatore: dopo la connessione, il cliente manda un messaggio "consumer\n" poi aspetta un primo mes-
	saggio dal server "okcons\n" e un altro messaggio senza carattere '\n' in mezzo che finisce con '\n' e chi corrisponde
	alla string più vecchia contenuta nella struttura FIFO del server. La string consumata viene cancellata dalla struttura FIFO.
	Il client rimane connesso finche ottiene una string (se la struttura FIFO è vuota, aspetterà). Dopo avere ottenuto la string
	il cliente si scollega.
 */

public class Consumer  extends Client implements Runnable {

	public Consumer(int server_port) {
		super(server_port,  new String[] {"consumer\n"}, new String[] {"okcons\n", "\n"});
	}	

	public void run() {
		System.out.println("Start consumer thread");
		this.Client_Life();
	}

}
