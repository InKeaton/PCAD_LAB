public class RWbasic {

    private int data;

    public RWbasic(){
        this.data = 0;
    }

    public int read(){
        return data;
    }

    public void write(){
        try{
            int tmp = data;
            tmp++;
            Thread.sleep(100);
            data = tmp;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}