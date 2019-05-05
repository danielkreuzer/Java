/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER;

import java.io.*;
import java.net.Socket;

public class ServerHandler extends Thread {
    private Socket socket;
    private ServerDataHandler serverDataHandler = new ServerDataHandler();

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(    ObjectInput objectInput = new ObjectInputStream(socket.getInputStream());
                ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream())) {
            while (true) {
                Object object = objectInput.readObject();
                Object output = serverDataHandler.HandleInput(object);
                objectOutput.writeObject(output);
            }
        } catch (Exception e) {
            try {
                //client closed connection
                System.out.println("- : disconnected client: " + socket);
                socket.close();
            } catch (IOException e1) {
                System.out.println("ERROR: COMPLETE server problem, serverhandler failure");
            }
        }
    }
}
