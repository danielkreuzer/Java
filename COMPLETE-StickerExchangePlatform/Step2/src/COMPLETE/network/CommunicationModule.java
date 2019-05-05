package COMPLETE.network;

import COMPLETE.dataStructs.DataHandler;
import COMPLETE.dataStructs.Sticker;
import COMPLETE.dataStructs.User;
import COMPLETE_SERVER_RMI.DataAccessException;
import COMPLETE_SERVER_RMI.StickerCommunication;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.TreeMap;

public class CommunicationModule extends Thread {
    public boolean getBaseSticker = false;
    public boolean getUserTreeMap = false;
    public boolean addNewUser = false;
    public boolean addNewRequest = false;
    public boolean deleteRequest = false;
    public boolean addStickerToUser = false;
    public boolean deleteStickerFromUser = false;
    public boolean addDoubleStickerToUser = false;
    public boolean deleteDoubleStickerFromUser = false;
    public boolean updatePassword = false;

    StickerCommunication stickerCommunication;

    public User inputUser = null;

    public String inputRequestingUser = "";
    public String inputAskedUser = "";
    public Integer inputWantedSticker = 0;
    public Integer inputStickerGiven = 0;

    public String inputUserString = "";
    public Integer inputSticker = 0;
    public String inputNewPassword = "";

    public boolean returnBoolean = false;

    private DataHandler dataHandler = null;

    public CommunicationModule(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        try {
            stickerCommunication = (StickerCommunication)
                    java.rmi.Naming.lookup("rmi://localhost:1099/StickerServer");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("connection to server not possible");
        }
    }

    public CommunicationModule() {
        try {
            stickerCommunication = (StickerCommunication)
                    java.rmi.Naming.lookup("rmi://localhost:1099/StickerServer");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("connection to server not possible");
        }
    }

    @Override
    public void run() {
        try {
            if(getBaseSticker) {
                dataHandler.setBaseSticker(stickerCommunication.getBaseSticker());
            } else if(getUserTreeMap) {
                dataHandler.setUserTreeMap(stickerCommunication.getUserTreeMap());
            } else if(addNewUser) {
                returnBoolean = stickerCommunication.addNewUser(inputUser);
            } else if(addNewRequest) {
                returnBoolean = stickerCommunication.addNewRequest(
                        inputRequestingUser, inputAskedUser, inputWantedSticker, inputStickerGiven);
            } else if(deleteRequest) {
                returnBoolean = stickerCommunication.deleteRequest(
                        inputRequestingUser, inputAskedUser, inputWantedSticker, inputStickerGiven);
            } else if(addStickerToUser) {
                returnBoolean = stickerCommunication.addStickerToUser(inputUserString, inputSticker);
            } else if(deleteStickerFromUser) {
                returnBoolean = stickerCommunication.deleteStickerFromUser(inputUserString, inputSticker);
            } else if(addDoubleStickerToUser) {
                returnBoolean = stickerCommunication.addDoubleStickerToUser(inputUserString, inputSticker);
            } else if(deleteDoubleStickerFromUser) {
                returnBoolean = stickerCommunication.deleteDoubleStickerFromUser(inputUserString, inputSticker);
            } else if(updatePassword) {
                returnBoolean = stickerCommunication.updatePassword(inputUserString, inputNewPassword);
            }
        } catch (DataAccessException | RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
