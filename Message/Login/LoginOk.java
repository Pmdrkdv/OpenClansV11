package Message.Login;

import Message.PiranhaMessage;

public class LoginOk extends PiranhaMessage {

    @Override
    public int getMessageType() {
       
        return 23654;
    }

    @Override
    public void encode() {
        stream.WriteInt(0);
        stream.WriteInt(1);
        stream.WriteInt(0);
        stream.WriteInt(1);
        stream.WriteString("abcdefghijklmnopqrstuvwxyz");
        stream.WriteString(null);
        stream.WriteString(null);
        stream.WriteInt(11);
        stream.WriteInt(651);
        stream.WriteInt(10);
        stream.WriteString("dev");
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteString(null);
        stream.WriteString(null);
        stream.WriteString(null);
        stream.WriteInt(0);
        stream.WriteString(null);
        stream.WriteString("RU");
        stream.WriteString(null);
        stream.WriteInt(1);
        stream.WriteString(null);
        stream.WriteString(null);
        stream.WriteString(null);
        stream.WriteInt(1);
        stream.WriteString("https://game-assets.clashofclans.com/");
        stream.WriteInt(1);
        stream.WriteString("https://event-assets.clashofclans.com");
        stream.WriteBytes(null, 0);
        stream.WriteBoolean(true);
        stream.WriteBoolean(false);
        stream.WriteVInt(0);
        stream.WriteString("");

    }

}
