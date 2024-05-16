//##########################################//
// # PRODUCER                               //
//##########################################//
// + An object wich sends TCP requests to   //
//   a Server                               //
// + The requests contain a string, which   //
//   will be added the the server structure //
//##########################################//

/*
  per i clienti produttore: dopo la connessione, il cliente manda un messaggio "producer\n" (le aspice non fanno parte
  dei messaggi) poi aspetta un messaggio dal server "okprod\n" e poi il cliente manda una string senza carattere '\n' in
  mezzo che finisce con '\n' e si scollega. La string prodotta viene aggiunta ad una struttura FIFO dal server.
*/

public class Producer  extends Client implements Runnable {
    public Producer(int server_port) {
      super(server_port,  new String[] {"producer", "oggetto"}, new String[] {"okprod"});
    }

    public void run() {
      System.out.println("Start producer thread");
      this.Client_Life();
    }
}