package Message.Keep;

import Message.PiranhaMessage;

public class KeepAliveServerMessage extends PiranhaMessage {
    @Override
    public int getMessageType() {
        return 20108;
    }
}
