/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER;

import java.io.*;
import java.util.TreeMap;

public class ServerDataHandler {
    public Object HandleInput(Object inputObject) {
        Object returnObject = new Object();
        try {
            try {
                TreeMap<String, User> s = (TreeMap<String, User>) inputObject;

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Userdata.ser"));
                objectOutputStream.writeObject(s);
                boolean ret = true;
                returnObject = ret;
            } catch (Exception e) {
                //No tree to synchronise sent
                try {
                    ObjectInput is = new ObjectInputStream(new FileInputStream("Userdata.ser"));
                    returnObject = is.readObject();
                } catch (IOException | ClassNotFoundException e1) {
                    System.out.println("ERROR: COMPLETE server problem: " +
                            "serverdatahandler existing data read failure");
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR: COMPLETE server problem: serverdatahandler failure");
        }

        return returnObject;
    }


}
