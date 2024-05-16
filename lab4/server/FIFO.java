import java.util.*; 

//##########################################// 
// # FIFO                                   //
//##########################################// 
// + A thread-safe FIFO data structure      //
//##########################################//  

public class FIFO {

    private ArrayList<String> data;          

    public FIFO() { 
        this.data = new ArrayList<String>(); 
    } 

    public synchronized void Add(String x) { 
        this.data.add(x); 
        if(this.data.size() == 1) { notifyAll(); }
    }

    public synchronized String Pop() {
        try {
            while(this.data.size() == 0) {  wait(); }
        }   catch(Exception e) { }
        return this.data.removeLast();
    } 
      
}; 

