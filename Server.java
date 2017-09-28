package com.pirat_bolot.SimpleServer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server extends Thread {

    private static final int PORT = 8080;
    private static String TEMPLATE_ANSWER = "The client '%d' sent me message: \n\t";
    private static String TEMPLATE_CLOSE_CONNECTION = "The client '%d' closed the connection";

    private Socket socket;
    private int num;

    public Server() {}

    public void setSocket(int num, Socket socket) {
        this.num = num;
        this.socket = socket;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    @Override
    public void run() {
        try {

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String line;
            while (true) {
                line = dis.readUTF();
                System.out.println(String.format(TEMPLATE_ANSWER, num) + line);
                System.out.println("I'm sending it back...");

                dos.writeUTF("Server receive text: " + line);
                dos.flush();
                System.out.println();
                if (line.equalsIgnoreCase("quit")) {
                    socket.close();
                    System.out.println(String.format(TEMPLATE_CLOSE_CONNECTION, num));
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Exception: " + e);
        }
    }

    public static void main(String[] args) {
        try {
            int i = 0;
            InetAddress ia = InetAddress.getByName("localhost");
            try (ServerSocket serverSocket = new ServerSocket(PORT, 0, ia)) {
                System.out.println("Server started\n\n");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client accepted");
                    new Server().setSocket(i++, socket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
