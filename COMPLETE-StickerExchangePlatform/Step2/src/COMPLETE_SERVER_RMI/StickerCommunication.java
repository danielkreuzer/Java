/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER_RMI;

import COMPLETE.dataStructs.Sticker;
import COMPLETE.dataStructs.User;

import javax.xml.crypto.Data;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.TreeMap;

public interface StickerCommunication extends Remote, AutoCloseable {
    TreeMap<Integer, Sticker> getBaseSticker()      throws RemoteException, DataAccessException;
    TreeMap<String, User> getUserTreeMap()          throws RemoteException, DataAccessException;
    boolean addNewUser(User user)                   throws RemoteException, DataAccessException;
    boolean addNewRequest(String requestingUser,
                          String askedUser,
                          Integer stickerWanted,
                          Integer stickerGiven)     throws RemoteException, DataAccessException;
    boolean deleteRequest(String requestingUser,
                          String askedUser,
                          Integer stickerWanted,
                          Integer stickerGiven)     throws RemoteException, DataAccessException;
    boolean addStickerToUser(String user,
                             Integer sticker)       throws RemoteException, DataAccessException;
    boolean deleteStickerFromUser(String user,
                                  Integer sticker)  throws RemoteException, DataAccessException;
    boolean addDoubleStickerToUser(String user,
                             Integer sticker)       throws RemoteException, DataAccessException;
    boolean deleteDoubleStickerFromUser(String user,
                                  Integer sticker)  throws RemoteException, DataAccessException;
    boolean updatePassword(String user, String password) throws RemoteException, DataAccessException;


}
