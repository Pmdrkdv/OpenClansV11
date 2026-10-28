package Message.Keep;

import Message.PiranhaMessage;


public class KeepAliveMessage extends PiranhaMessage {
    @Override
    public int getMessageType() {
        return 10108;
    }
}
