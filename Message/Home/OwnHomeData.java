package Message.Home;

import Logic.LogicClientAvatar;
import Logic.LogicClientHome;
import Message.PiranhaMessage;

public class OwnHomeData extends PiranhaMessage {

   

    @Override
    public void encode() {
        stream.WriteInt(0);
        stream.WriteInt(0);


        new LogicClientHome().Encode(stream);
        new LogicClientAvatar().Encode(stream);

       
        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteLong(0);
        stream.WriteLong(0);
        stream.WriteLong(0);

        stream.WriteInt(0);
        stream.WriteInt(0); 

    }

    @Override
    public int getMessageType() {
        return 25195;
    }
}
