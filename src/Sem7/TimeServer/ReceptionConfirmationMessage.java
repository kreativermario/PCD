package Sem7.TimeServer;

import java.io.Serializable;

public class ReceptionConfirmationMessage implements Serializable {

    private final boolean receivedStatus;

    public ReceptionConfirmationMessage(boolean receivedStatus){
        this.receivedStatus = receivedStatus;
    }

    public boolean isReceivedStatus() {
        return receivedStatus;
    }
}
