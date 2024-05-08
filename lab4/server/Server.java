import java.io.*;
import java.net.*;

public class Server {
	private ServerSocket server;
	private final int default_port = 6969;

	Server() 				 { this.ServerRun(this.default_port); }
	Server(int port) { this.ServerRun(port); }

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
