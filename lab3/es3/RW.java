public class RW extends RWbasic {
    
    // Checks if it can be written
    private boolean ready_to_update; 
		private static int n_lettori = 0;
    // Return data
    @Override public int read() {
    	synchronized(this) {
				RW.n_lettori++;
			}

	  	synchronized(this) {
				RW.n_lettori--;
				if(RW.n_lettori <= 0) {
					notifyAll();
				}
			}			
			return data;
    }

    // Update data
    @Override public void write(){
        try {
            synchronized(this) {
                while(RW.n_lettori > 0) {
                    wait();
                }
                
                int cache_data = data;
                cache_data++;
                this.ready_to_update = false;
                notifyAll();
                data = cache_data;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.err.println("Thread Interrupted");
        }
    }
}
