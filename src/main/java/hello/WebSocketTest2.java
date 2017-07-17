package hello;


import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dasun on 2017/7/15.
 */
@ServerEndpoint("/websocket2")
public class WebSocketTest2 {
    /**
     * 存储当前有效的session对象
     */
    private static Queue<Session> sessionSet = new ConcurrentLinkedQueue<Session>();
    private static BlockingQueue shareBQ=new LinkedBlockingQueue();
    @OnMessage
    public void onMessage(String message, Session currentSession) {
        System.out.println("Server say：Received: " + message);
        try {
            final Set<Session> sessions = currentSession.getOpenSessions();
            // 客户端互相发送消息
            for (Session session : sessions) {
                session.getBasicRemote().sendText(message);
            }
            /************ 启动定时公告 **************/
            //SystemTimer.getInstance();
            /**************************/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnOpen
    public void onOpen(Session currentSession) {
        if (sessionSet.contains(currentSession) == false) {
            sessionSet.add(currentSession);
            System.out.println("WebSocketTest.onOpen()================Add=" + sessionSet.size());
        }

        ProductorMsg p=new ProductorMsg(shareBQ);
        p.startProduct();
        Customer.sendMsg(currentSession,shareBQ);
        String jas="[{\"id\":\"29\",\"x\":\"85\",\"y\":\"47\"},{\"id\":\"29\",\"x\":\"88\",\"y\":\"47\"}]";
        try {
            currentSession.getBasicRemote().sendText(jas);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server say：Client connected");

    }

    @OnClose
    public void onClose(Session currentSession) {
        if (sessionSet.contains(currentSession)) {
            sessionSet.remove(currentSession);
        }
        System.out.println("Server say：Connection closed============Close=" + sessionSet.size());
    }

    public static Queue<Session> getSessionSet() {
        return sessionSet;
    }


}
