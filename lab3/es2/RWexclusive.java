public class RWexclusive extends RWbasic {
    
    // Update data
    @Override public void write(){
        try{
            synchronized(this) {
                int cache_data = data;
                cache_data++;

                // To test the concurrency 
                Thread.sleep((int)(Math.random()*10));

                data = cache_data;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
