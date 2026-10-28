package Message;

import java.io.IOException;

import Message.Home.OwnHomeData;
import Message.Keep.KeepAliveServerMessage;
import Message.Login.LoginOk;
import net.Messaging;

public class MessageManager {

    public static void ReceiveMessage(PiranhaMessage msg) throws IOException {
        switch (msg.getMessageType()) {
            case 10101:
                LoginMessageReceived(msg);
                break;
            case 10108:
                KeepAliveReceived();
                break;

        }
    }

    public static void KeepAliveReceived()throws IOException{
        Messaging.send(new KeepAliveServerMessage());
    }

    public static void LoginMessageReceived(PiranhaMessage msg) throws IOException {
        Messaging.send(new LoginOk());
        Messaging.send(new OwnHomeData());
    }
}
