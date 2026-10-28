package net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tcp {

    public int port = 9339;
    public static Socket Socket;

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + serverSocket.getLocalPort() + " !");
        while (true) {
            final Socket client = serverSocket.accept();
            Socket = client;
            new Thread(() -> {
                try {
                    connected(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void connected(Socket client) throws IOException {
        InputStream InputStream = client.getInputStream();
        Messaging msg = new Messaging();
        System.out.println("Client connected: " + client.getRemoteSocketAddress());
        while (!client.isClosed()) {
            byte[] header = InputStream.readNBytes(7);
            msg.readHeader(header);
            byte[] payload = InputStream.readNBytes(msg.a3);
            Messaging.NextMessage(msg, payload);
        }

    }

}
