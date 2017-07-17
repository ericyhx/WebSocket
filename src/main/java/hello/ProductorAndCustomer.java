package hello;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dasun on 2017/7/15.
 */
public class ProductorAndCustomer {
    private BlockingQueue shareBQ=new LinkedBlockingQueue();
    public void start(){
        ProductorMsg p=new ProductorMsg(shareBQ);
        p.startProduct();
    }

    public BlockingQueue getShareBQ() {
        return shareBQ;
    }

    public void setShareBQ(BlockingQueue shareBQ) {
        this.shareBQ = shareBQ;
    }
    public static ProductorAndCustomer getInstance(){
        return new ProductorAndCustomer();
    }

    public void stop() {
        System.exit(0);
    }
}
