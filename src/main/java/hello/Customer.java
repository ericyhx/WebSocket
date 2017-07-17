package hello;

import javax.websocket.Session;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by dasun on 2017/7/15.
 */
public class Customer{
    public static void sendMsg(Session currentSession,BlockingQueue shareBQ) {
        Executors.newSingleThreadExecutor().execute(()->{
            while (true){
                try{
                    currentSession.getBasicRemote().sendText(String.valueOf(shareBQ.take()));
                    Thread.sleep(200);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
