//##########################################// 
// # CLIENT                                 //
//##########################################// 
// + An object wich sends TCP requests to   //
//   a Server                               //
//##########################################//  


import java.io.*;
import java.net.*;

public class Client implements Actor {
	//Attributi
	private static int client_num = 0;
	
	private int client_id;
	private Socket client_socket = null;
	
	private String[] send_string;
	private String[] rcv_string;
	private int 	 send_msg_index = 0;
	private int 	 rcv_msg_index  = 0;

	private int server_port;

	//Metodi
	public Client(int server_port, String[] send_string, String[]rcv_string) {
		this.server_port = server_port;
		this.client_id = client_num++;
		this.send_string = send_string;
		this.rcv_string = rcv_string;
		System.out.println("Creato il nuovo client numero: " + this.client_id + " manderà le richieste al server alla porta: " + this.server_port);
	}

	private void Send() {
		try {
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(this.client_socket.getOutputStream()));
			pw.println(this.send_string[this.send_msg_index]);
			pw.flush();
			pw.close();
		} catch (Exception e) {
				
		}
	}

	private void Rcv() {
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(this.client_socket.getInputStream()));
			String rcv;
			do {
				rcv = br.readLine();
			} while(rcv != this.rcv_string[this.rcv_msg_index]);

		} catch (Exception e) {

		}
	}

	public void Client_Life() {
		try {	this.client_socket = new Socket("localhost", this.server_port);	} 
		catch (Exception e) {}
		System.out.println("Il client " + this.client_id + " invia una stringa al server");
		this.Send();
	}
}

