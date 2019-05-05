/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE.network;

import COMPLETE.dataStructs.User;

import java.io.*;
import java.net.Socket;
import java.util.TreeMap;

public class CommunicationModule {
    public boolean sendToServer(Object object) {
        try (
            Socket socket = new Socket("localhost", 5001);
            ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());) {

            socket.setReuseAddress(true);

            objectOutput.writeObject(object);

        } catch (Exception e) {
            return  false;
        }
        return true;
    }

    public TreeMap<String, User> getUserDataFromServer() {
        TreeMap<String, User> treeMap = null;
        try (
            Socket socket = new Socket("localhost", 5001);
            ObjectOutput objectOutput = new ObjectOutputStream(socket.getOutputStream());
            ObjectInput objectInput = new ObjectInputStream(socket.getInputStream())) {

            socket.setReuseAddress(true);
            String sendMessage = "get";
            objectOutput.writeObject(sendMessage);
            treeMap = (TreeMap<String, User>) objectInput.readObject();

        } catch (IOException | ClassNotFoundException e) {

        }
        return treeMap;
    }

}
