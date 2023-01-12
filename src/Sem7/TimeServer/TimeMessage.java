package Sem7.TimeServer;

import java.io.Serializable;

public class TimeMessage implements Serializable {

    private long time;

    public TimeMessage(long time){
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
