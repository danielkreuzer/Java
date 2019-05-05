/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER_RMI;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
    private static final int DEFAULT_RMI_PORT = 1099;
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost/StickerDB?autoReconnect=true&useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = null;


    public static void main(String[] args) {
        int registryPort = DEFAULT_RMI_PORT;
        if (args.length > 0) registryPort = Integer.valueOf(args[0]);
        try (RMI_ServerHandler rmi_serverHandler
                     = new RMI_ServerHandler(CONNECTION_STRING, USER_NAME, PASSWORD)) {
            LocateRegistry.createRegistry(registryPort);
            // weak reference is returned, kann passieren, dass Garbage Collector es freigibt
            // deshalb Zuweisung an richtige Referenz
            Remote bufferStub = UnicastRemoteObject.exportObject(rmi_serverHandler, 0);
            String  bufferUrl = String.format("rmi://localhost:%s/StickerServer", registryPort);
            Naming.rebind(bufferUrl, bufferStub);
            System.out.printf("Sticker Server available at: %s%n", bufferUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
