package Message;

import net.Stream.ByteStream;

public abstract class PiranhaMessage {

    public ByteStream stream = new ByteStream(1024);

    public void encode() {
    }

    public void decode() {
    }

    public abstract int getMessageType();

    public int getMessageVersion() {
        return 0;
    }

    public int getEncodingLength() {
        return stream.GetLength();
    }

}
