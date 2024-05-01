public class RWext extends RWbasic {
    
    // Checks if it can be written
    private boolean ready_to_update; 
    
    // Return data
    @Override public int read() {
        try {
            synchronized(this) {
                while(this.ready_to_update == true) {
                    wait();
                }

                this.ready_to_update = true;
                notify();
                return data;
            }
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("Thread Interrupted");
            return -1;
        }
    }

    @Override public void write(){
        try {
            synchronized(this) {
                while(this.ready_to_update == false) {
                    wait();
                }
                
                int cache_data = data;
                cache_data++;
                this.ready_to_update = false;
                notify();
                data = cache_data;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("Thread Interrupted");
        }
    }
}
