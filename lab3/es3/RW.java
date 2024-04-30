public class RW extends RWbasic {
    
    // Checks if it can be written
    private boolean ready_to_update; 

    // Return data
    @Override public int read() {
        try {
            while(this.ready_to_update == true) {
                synchronized(this) {
                    wait();
                }
            }

            this.ready_to_update = true;
            synchronized(this) {
                notify();
            }
            return data;
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("Thread Interrupted");
            return -1;
        }
    }

    // Update data
    @Override public void write(){
        try{
            while(this.ready_to_update == false) {
                synchronized(this) {
                    wait();
                }
            }
        
            synchronized(this) {
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
