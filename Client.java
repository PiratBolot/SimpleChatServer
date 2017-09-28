package com.pirat_bolot.SimpleServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static final int PORT = 8080;
    private static final String localhost = "127.0.0.1";

    public static void main(String[] args) {
        Socket socket = null;
        try{
            try {
                System.out.println("Welcome to Client side\n" +
                        "Connecting to the server\n\t" +
                        "(IP address " + localhost +
                        ", port " + PORT + ")");
                InetAddress ipAddress = InetAddress.getByName(localhost);
                socket = new Socket(ipAddress, PORT);
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

                // Создаем поток для чтения с клавиатуры.
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader keyboard = new BufferedReader(isr);
                String line = null;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
