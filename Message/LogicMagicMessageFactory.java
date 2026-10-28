package Message;

import Message.Keep.KeepAliveMessage;
import Message.Login.LoginMessage;

public class LogicMagicMessageFactory {

    public static PiranhaMessage CreateMessageByType(int type) {
        switch (type) {
            case 10101:
                return new LoginMessage();
            case 10108:
                return new KeepAliveMessage();
            default:
                return null;
        }

    }
}
