package com.pirat_bolot.SimpleServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static final int PORT = 8080;
    private static final String localhost = "127.0.0.1";

    public static void main(String[] args) {
        try{
            System.out.println("Welcome to Client side\n" +
                    "Connecting to the server\n\t" +
                    "(IP address " + localhost +
                    ", port " + PORT + ")");
            InetAddress ipAddress = InetAddress.getByName(localhost);
            try (Socket socket = new Socket(ipAddress, PORT)) {
                System.out.println("The connection is established.");
                System.out.println(
                        "\tLocalPort = " +
                                socket.getLocalPort() +
                                "\n\tInetAddress.HostAddress = " +
                                socket.getInetAddress().getHostAddress() +
                                "\n\tReceiveBufferSize (SO_RCVBUF) = " +
                                socket.getReceiveBufferSize());

                DataInputStream in  = new DataInputStream (socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                String line;
                System.out.println("Type in something and press enter\n");
                while (true) {
                    line = keyboard.readLine();
                    out.writeUTF(line);
                    out.flush();
                    line = in.readUTF();
                    if (line.endsWith("quit"))
                        break;
                    else {
                        System.out.println(
                                "The server sent me this line :\n\t" + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
