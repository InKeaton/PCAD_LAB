//##########################################// 
// # CLIENT                                 //
//##########################################// 
// + An object wich sends TCP requests to   //
//   a Server                               //
//##########################################//  

public class Client implements Actor {
    private static int client_num = 0;                                                                                                                                                      
    private int client_id;
    private Socket client_socket = null;

    Client() {
        Socket socket=new Socket("localhost",4242);
        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        pw.println("Blabla");
        pw.flush();
        String mess=br.readLine();
        System.out.println("Message from the server:"+mess);
        pw.close();
        br.close();
        socket.close();
    }
}
