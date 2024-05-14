import java.io.*;
import java.net.*;

public class Service implements Runnable {
    private Socket socket     = null;
    private PrintWriter pw    = null;
    private BufferedReader br = null;

    Service(Socket socket) { 
        try {
            this.socket = socket; 
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.pw = new PrintWriter(this.socket.getOutputStream());
        } catch(Exception exc) {}
    }

    private void Consumer() {
        try {
            System.out.println("Contatto da un consumer");
            pw.println("okcons");
            pw.flush();
            System.out.println("Messaggio inviato");
        }   catch(Exception e) {

        }
    }   

    private void Producer() {
        try {
            System.out.println("Contatto da un consumer");
            pw.println("okcons\n");
            pw.flush();
        }   catch(Exception e) {
        }
    }

    public void run() {
        try{
            // Leggo il messaggio inviato dal socket
            System.out.println("Run service 5.0");
            String msg = br.readLine();
            System.out.println(msg);
            if (msg.equals("producer"))      { this.Producer(); }
            else if(msg.equals("consumer"))  { this.Consumer(); }
            br.close();
            this.socket.close();
        } catch(Exception exc) {

        }
    }

}
