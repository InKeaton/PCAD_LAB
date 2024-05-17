import java.util.*; 
import java.util.Queue; 

//##########################################// 
// # FIFO                                   //
//##########################################// 
// + A thread-safe FIFO data structure      //
//##########################################//  

public class FIFO {

    private ArrayList<String> data;          
    private int next_out;           

    public FIFO() { 
        data = new ArrayList<String>(); 
        next_out = 0; 
    } 

    public synchronized boolean insert(String x) { 
        data.add(x); 
        return true; 
    };     
    
    public synchronized boolean remove() { 
        if (is_empty() == true) { 
            return false; 
        } 
        next_out++; 
        return true; 
    } 
    
    public synchronized String get_next_out() { 
        return data.get(next_out); 
    } 

    public synchronized boolean is_empty() { 
        return next_out >= data.size(); 
    }      
}; 

