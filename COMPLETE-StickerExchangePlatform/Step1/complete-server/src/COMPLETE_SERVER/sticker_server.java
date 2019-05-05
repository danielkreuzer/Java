/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;


public class sticker_server {

    static final int PORT = 5001;

    private static void connect() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setReuseAddress(true);

        } catch (Exception e) {
            System.out.println("ERROR: COMPLETE server problem, serversocket failure");
            System.exit(-1);
        }

        //Wait for new sticker clients
        while (true) {
            try {
                socket = serverSocket.accept();
                System.out.println("+ : new client connected: " + socket);
            } catch (Exception e) {
                System.out.println("");
            }
            new ServerHandler(socket).start();
        }
    }

    //Aufbau:
    //https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

    public static void main(String[] args) {
        System.out.println("COMPLETE sticker collection system server starting...");
        System.out.println("ready for clients ...");
        connect();


    }
}
