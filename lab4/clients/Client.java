//##########################################// 
// # CLIENT                                 //
//##########################################// 
// + An object wich sends TCP requests to   //
//   a Server                               //
//##########################################//  


import java.io.*;
import java.net.*;

public class Client  {
	//Attributi
	private static int client_num = 0;
	
	private int client_id;
	private Socket client_socket  = null;
	private PrintWriter pw        = null;
	private BufferedReader br     = null;
	private String[] snd_string;
	private String[] rcv_string;
	private int 	 snd_msg_index  = 0;
	private int 	 rcv_msg_index  = 0;

	private int server_port;

	//Metodi
	public Client(int server_port, String[] snd_string, String[]rcv_string) {
		this.server_port = server_port;
		this.client_id = client_num++;
		this.snd_string = snd_string;
		this.rcv_string = rcv_string;
		try {
			this.client_socket = new Socket("localhost", this.server_port);
			this.pw = new PrintWriter(new OutputStreamWriter(this.client_socket.getOutputStream()));
			this.br = new BufferedReader(new InputStreamReader(this.client_socket.getInputStream()));
		} catch(Exception e) {}
		System.out.println("Creato il nuovo client numero: " + this.client_id + " manderà le richieste al server alla porta: " + this.server_port);
	}

	public void Snd() {
		try {
			System.out.println("Client: " + this.client_id + " send");
			pw.println(this.snd_string[this.snd_msg_index]);
			pw.flush();
			System.out.println("inviato il seguente msg: " + this.snd_string[this.snd_msg_index]);
		} catch (Exception e) {}
	}


	public void Rcv() {
		try {
			String rcv;
			System.out.println("Client: " + this.client_id + " recv");
			while(((rcv = br.readLine()) == null) || !(rcv.equals(this.rcv_string[this.rcv_msg_index]))) {}
			System.out.println("Ricevuto msg: " + rcv);
		} catch (Exception e) {
			System.out.println("Client RCV Exception: " + e.getMessage());
		}
	}

	public void Client_Life() {
		try {	
			while(this.snd_msg_index < this.snd_string.length || this.rcv_msg_index < this.rcv_string.length) {
				if(this.snd_msg_index < this.snd_string.length) {
					this.Snd(); 
					this.snd_msg_index++;
				}
				if(this.rcv_msg_index < this.rcv_string.length) {
					this.Rcv();  
					this.rcv_msg_index++;
				}
			}
			this.client_socket.close();
		}	 
		catch (Exception e) {
			System.out.println("Presa eccezione: " + e.getMessage());

		}
	}
}

