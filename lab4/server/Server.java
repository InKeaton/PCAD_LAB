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

public class Server implements Actor {
	private ServerSocket server;
	private final int default_port = 6969;

	public Server()         { this.ServerRun(this.default_port); }
	public Server(int port) { this.ServerRun(port); }

	private void ServerRun(int run_port) {
		try {
			this.server = new ServerSocket(run_port);
			while(true) {
				Socket socket = this.server.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				String mess=br.readLine();
				System.out.println("Receive message:" + mess);
				pw.println("ECHO "+mess);
				pw.flush();
				br.close();
				pw.close();
				socket.close(); 
			}
		} catch(Exception e) {
		
		}
	}
}
