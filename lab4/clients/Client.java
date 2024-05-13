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
	
	private String[] snd_string;
	private String[] rcv_string;
	private int 	 snd_msg_index = 0;
	private int 	 rcv_msg_index  = 0;

	private int server_port;

	//Metodi
	public Client(int server_port, String[] snd_string, String[]rcv_string) {
		this.server_port = server_port;
		this.client_id = client_num++;
		this.snd_string = snd_string;
		this.rcv_string = rcv_string;
		System.out.println("Creato il nuovo client numero: " + this.client_id + " manderà le richieste al server alla porta: " + this.server_port);
	}

	public void Snd() {
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(this.client_socket.getOutputStream()));
			pw.println(this.snd_string[this.snd_msg_index]);
			pw.flush();
			pw.close();
		} catch (Exception e) {}
	}


	public void Rcv() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.client_socket.getInputStream()));
			String rcv;
			do {
				rcv = br.readLine();
			} while(rcv != this.rcv_string[this.rcv_msg_index]);
			br.close();
		} catch (Exception e) {}
	}

	public void Client_Life() {
		try {	
			this.client_socket = new Socket("localhost", this.server_port);
			while( this.rcv_msg_index  != (this.rcv_string.length - 1) || 
				   this.snd_msg_index  != (this.snd_string.length - 1) ){
				
				if(this.rcv_msg_index != (this.rcv_string.length - 1))  { 
					this.Snd(); 
					this.snd_msg_index++;
				}

				if(this.snd_msg_index != (this.snd_string.length - 1))  { 
					this.Rcv();  
					this.rcv_msg_index++;
				}
			}
			this.client_socket.close();
		}	 
		catch (Exception e) {}
	}
}

