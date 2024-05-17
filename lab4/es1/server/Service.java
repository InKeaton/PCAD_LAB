import java.io.*;
import java.net.*;

public class Service implements Runnable {
    private Socket socket     = null;
    private PrintWriter pw    = null;
    private BufferedReader br = null;
    private static FIFO  stack = null;

    Service(Socket socket) { 
        try {
            this.socket = socket; 
            if(stack == null) { Service.stack = new FIFO(); }
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.pw = new PrintWriter(this.socket.getOutputStream());
        } catch(Exception exc) {}
    }

    private void Consumer() {
        try {
            System.out.println("C--> Accettata nuova richiesta da un consumer");
            System.out.println("C--> Contatto da un consumer");
            pw.println("okcons");
            pw.flush();
            System.out.println("C--> Messaggio inviato okcons");
            
            pw.println(Service.stack.Pop());
            pw.flush();
            System.out.println("C--> 1 Messaggio inviato oggetto");

            pw.println("");
            pw.flush();
            System.out.println("C--> 2 Messaggio inviato oggetto");
            System.out.println("C--> Ciao Consumer :(");
        }   catch(Exception e) {

        }
    }   

    private void Producer() {
        try {
            String rcv;
            System.out.println("P--> Accettata nuova richiesta da un producer");
            pw.println("okprod");
            pw.flush();
            System.out.println("P--> Messaggio inviato okprod");
            while (((rcv = br.readLine()) == null) || !(rcv.equals("oggetto"))){}
            Service.stack.Add(rcv);
            System.out.println("P--> Ricevuto il 1 msg dal producer :)");

            while (((rcv = br.readLine()) == null) || !(rcv.equals(""))){}            
            System.out.println("P--> Ricevuto il 2 msg dal producer :)"); 
            System.out.println("P--> Ciao producer alla prossima :(");
        }   catch(Exception e) {
        }
    }

    public void run() {
        try{
            // Leggo il messaggio inviato dal socket
            String msg = br.readLine();
            if (msg.equals("producer"))      { this.Producer(); }
            else if(msg.equals("consumer"))  { this.Consumer(); }
            // Close
            this.br.close();
            this.pw.close();
            this.socket.close();
        } catch(Exception exc) {

        }
    }

}
