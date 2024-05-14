import java.io.*;
import java.net.*;

//##########################################// 
// # SERVER                                 //
//##########################################// 
// + An object containing a FIFO structure. //
// + Waits for TCP requests from Producers  //
//   and Consumers to add or remove         //
//   elements from the structure            //
//##########################################//  

public class Server {

	private ServerSocket server;
	private final int default_port = 6969;

	public Server()         { this.ServerRun(this.default_port);}
	public Server(int port) { this.ServerRun(port); }

	private void ServerRun(int run_port) {
		/*
		 * Deve semplicemente accettare le connessioni 
		 * con i client e avviare il thread del servizio 
		 * per potergli rispondere
		 */
		try {
			this.server = new ServerSocket(run_port);
			while(true) {
				Socket socket = this.server.accept();
				System.out.println("Hello");
				Thread t = new Thread(new Service(socket));
				t.start();
			}
		} catch(Exception e) {
		
		}
	}
}
