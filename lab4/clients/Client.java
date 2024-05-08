import java.io.*;
import java.net.*;

public class Client {
	private static int client_num = 0;
	private int client_id;
	private Socket client_socket = null;

	Client(String msg) {
		this.client_id = client_num++;
		System.out.println("Creato il nuovo client numero: " + this.client_id);
		this.Send(msg);
	}

	public void Send(String msg) {
		System.out.println("Client: " + this.client_id + " invia al server questo msg: " + msg);
		try {
				Socket socket=new Socket("localhost",6969);
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				pw.println("Blabla");
				pw.flush();
				String mess=br.readLine();
				System.out.println("Message from the server:"+mess);
				pw.close();
				br.close();
				socket.close();
				this.client_socket = new Socket("localhost", 6969);
			} catch (Exception e) {
					
			}

	}

}

