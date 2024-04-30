public class RWbasic {

    // Data stored by the object
    // Changed to protected to be accessed by subclasses
    protected int data;

    // Constructor
    public RWbasic(){
        this.data = 0;
    }

    // Return data
    public int read(){
        return data;
    }

    // Update data
    public void write(){
        try{
            int cache_data = data;
            cache_data++;
            // To test the concurrency 
            Thread.sleep((int)(Math.random()*10));
            data = cache_data;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}