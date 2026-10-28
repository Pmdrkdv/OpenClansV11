package Message.Login;

import Message.PiranhaMessage;

public class LoginMessage extends PiranhaMessage {
    public long AccountId;
    public String PassToken;
    public String DeviceId;
    public String Device;
    public boolean  isAndroid;
    public int RndKey;
    public String LanguageType;
    public int Major;
    public int Minor;
    public int Build;
    public String ResourceSha;
    public String ClientVersion;
    public String SupercellIdToken;
    
    @Override 
    public void decode(){
        AccountId = stream.ReadLong();

        PassToken = stream.ReadString();
        Major = stream.ReadInt();
        Minor = stream.ReadInt();
        Build = stream.ReadInt();

    }

    @Override
    public int getMessageType() {
        return 10101;
    }



}
