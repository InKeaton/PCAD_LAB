public class RWext extends RWbasic {
    
    @Override public int read() {
        // TO BE DONE
        return data
    }

    // Update data
    @Override public void write(){
        // TO BE DONE
        try{
            synchronized(this) {
                int cache_data = data;
                cache_data++;
                data = cache_data;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
