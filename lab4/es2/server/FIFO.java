import java.util.*; 

//##########################################// 
// # FIFO                                   //
//##########################################// 
// + A thread-safe FIFO data structure      //
//##########################################//  

public class FIFO {

    private ArrayList<String> data;          
    private int numel;

    public FIFO(int numel) { 
        this.numel = numel;
        this.data = new ArrayList<String>(); 
    } 

    public synchronized void Add(String x) { 
        try {
            while(this.data.size() == this.numel) { wait(); }
            this.data.add(x); 
            if(this.data.size() == 1) { notifyAll(); }
        } catch(Exception e) {}         
    }

    public synchronized String Pop() {
        String return_val = "";
        try {
            return_val = this.data.removeFirst();
            if(this.data.size() == this.numel-1) { notifyAll();}
            while(this.data.size() == 0) {  wait(); }
        }   catch(Exception e) { }
        return return_val;
    } 
      
}; 

