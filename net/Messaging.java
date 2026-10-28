package net;

import Message.LogicMagicMessageFactory;
import Message.MessageManager;
import Message.PiranhaMessage;
import net.Stream.ByteStream;

import java.io.IOException;
import java.io.OutputStream;

public class Messaging {

    public int a2;
    public int a3;
    public int a4;



    public static void NextMessage(Messaging msg,byte[] payload) throws IOException {
     

        ReadNewMessage(msg.a2, payload);
    }

    public static void ReadNewMessage(int type,byte[] payload) throws IOException {
        System.out.println("new type=" + type + " payloadLen=" + payload.length);

        
        PiranhaMessage Packet = LogicMagicMessageFactory.CreateMessageByType(type);
        if (Packet == null) {
            System.out.println("Unhandled message type=" + type + ", skipping " + payload.length + " bytes");
            return;
        }
        Packet.stream = new ByteStream(payload, payload.length);
        Packet.decode();

        MessageManager.ReceiveMessage(Packet);
    }
    public byte[] readHeader(byte[] result) {

        a2 = ((result[0] & 0xFF) << 8) | (result[1] & 0xFF);
        a3 = ((result[2] & 0xFF) << 16) | ((result[3] & 0xFF) << 8) | (result[4] & 0xFF);
        a4 = ((result[5] & 0xFF) << 8) | (result[6] & 0xFF);

        return result;
    }

    public static byte[] writeHeader(PiranhaMessage message) {
        byte[] header = new byte[7];

        int type = message.getMessageType();
        int length = message.getEncodingLength();
        int version = message.getMessageVersion();
        header[0] = (byte) ((type >> 8) & 0xFF);
        header[1] = (byte) (type & 0xFF);
        header[2] = (byte) ((length >> 16) & 0xFF);
        header[3] = (byte) ((length >> 8) & 0xFF);
        header[4] = (byte) (length & 0xFF);
        header[5] = (byte) ((version >> 8) & 0xFF);
        header[6] = (byte) (version & 0xFF);

        return header;
    }

    public static void send(PiranhaMessage message) throws IOException {

        message.encode();
        byte[] payload = message.stream.toByteArray();
        byte[] header = writeHeader(message);

        OutputStream out = Tcp.Socket.getOutputStream();
        out.write(header);
        out.write(payload);
        out.flush();
    }

   

}
