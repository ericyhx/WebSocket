package hello;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by dasun on 2017/7/15.
 */
public class ProductorMsg{
    private BlockingQueue shareBQ;
    public ProductorMsg(BlockingQueue shareBQ) {
        this.shareBQ=shareBQ;
    }
    public void startProduct(){
        Executors.newSingleThreadExecutor().execute(()->{
            int i=0;
            try{
                while (true){
                    System.out.println("produced :i="+i);
                    shareBQ.put("i="+i);
                    i++;
                    Thread.sleep(1000);
//                    if(i>10&&i%10==0){
//                        Thread.sleep(20000);
//                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
