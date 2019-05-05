/*
 * Copyright (c) 2018 Daniel Kreuzer
 */

package COMPLETE_SERVER_RMI;

import COMPLETE.dataStructs.Sticker;
import COMPLETE.dataStructs.User;

import java.io.*;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.*;

public class RMI_ServerHandler implements StickerCommunication {
    private String CONNECTION_STRING;
    private String USER_NAME;
    private String PASSWORD;
    private Connection connection;

    public RMI_ServerHandler(String CONNECTION_STRING, String USER_NAME, String PASSWORD) {
        this.CONNECTION_STRING = CONNECTION_STRING;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
    }

    public Connection getConnection() throws DataAccessException {
        try {
            if (connection == null)
                connection =
                        DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
            return connection;
        }
        catch (SQLException ex) {
            throw new DataAccessException("Can't establish connection to database. SQLException: "
                    + ex.getMessage());
        }// try/catch
    } // getConnection

    @Override
    public TreeMap<Integer, Sticker> getBaseSticker() throws RemoteException, DataAccessException {
        System.out.println("+ request getBaseSticker");
        TreeMap<Integer, Sticker> treeMap = new TreeMap<>();

        try (PreparedStatement statement =
                     getConnection().prepareStatement("SELECT * FROM Sticker")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                   treeMap.put(resultSet.getInt("Number"),
                           new Sticker(resultSet.getInt("Number"),
                                   resultSet.getString("Sector"),
                                   resultSet.getString("Description")));
                }
            } // includes finally resultSet.close();
        }
        catch (SQLException ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close()
        System.out.println("- request getBaseSticker");
        return treeMap;
    }

    private Sticker getStickerToID(Integer id) {
        Sticker sticker = null;
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet =
                     statement.
             executeQuery("SELECT * FROM Sticker WHERE Number = "+id.toString())) {
            if (resultSet.next()) {
                sticker = new Sticker(resultSet.getInt("Number"),
                                        resultSet.getString("Sector"),
                                        resultSet.getString("Description"));
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } // includes finally resultSet.close(); statement.close();
        return sticker;
    }

    private TreeMap<Integer, Sticker> getMySticker(String Name) {
        TreeMap<Integer, Sticker> treeMap = new TreeMap<>();
        try (PreparedStatement statement =
                     getConnection().
                     prepareStatement("SELECT * FROM My_Sticker WHERE User = '"+Name+"'")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Sticker");
                    treeMap.put(id, getStickerToID(id));
                }
            } // includes finally resultSet.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } // includes finally statement.close()
        return treeMap;
    }

    private TreeMap<Integer, Sticker> getDoubleSticker(String Name) {
        TreeMap<Integer, Sticker> treeMap = new TreeMap<>();
        try (PreparedStatement statement =
                     getConnection().
                             prepareStatement("SELECT * FROM Double_Sticker WHERE User = '"+Name+"'")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Sticker");
                    treeMap.put(id, getStickerToID(id));
                }
            } // includes finally resultSet.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } // includes finally statement.close()
        return treeMap;
    }

    private List<Map.Entry<User, Map.Entry<Sticker, Sticker>>> getRequestListToUser(String user) {
        List<Map.Entry<User, Map.Entry<Sticker, Sticker>>> list = new ArrayList<>();
        try (PreparedStatement statement =
                     getConnection().
                             prepareStatement("SELECT * FROM Requests WHERE Asked_User = ?")) {

            statement.setString(1, user);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user_requesting = new User();
                    user_requesting.setName(resultSet.getString("Requesting_User"));
                    Sticker stkWanted = new Sticker();
                    Sticker stkGiven = new Sticker();
                    stkWanted.setNumber(resultSet.getInt("Sticker_Wanted"));
                    stkGiven.setNumber(resultSet.getInt("Sticker_Give"));
                    Map.Entry<Sticker, Sticker> stickers = new AbstractMap.SimpleEntry<Sticker, Sticker>(
                            stkWanted, stkGiven
                    );

                    Map.Entry<User, Map.Entry<Sticker, Sticker>> newRequest =
                            new AbstractMap.SimpleEntry<User, Map.Entry<Sticker, Sticker>>(
                                    user_requesting, stickers
                            );
                    list.add(newRequest);
                }
            } // includes finally resultSet.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } // includes finally statement.close()


        return list;
    }

    @Override
    public TreeMap<String, User> getUserTreeMap() throws RemoteException, DataAccessException {
        System.out.println("+ request getUserTreeMap");
        TreeMap<String, User> treeMap = new TreeMap<>();

        try (PreparedStatement statement =
                     getConnection().prepareStatement("SELECT * FROM User")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TreeMap<Integer, Sticker> mySticker = getMySticker(resultSet.getString("Name"));
                    TreeMap<Integer, Sticker> doubleSticker = getDoubleSticker(resultSet.getString("Name"));
                    User user = new User(resultSet.getString("Name"),
                            resultSet.getString("Password"),
                            resultSet.getString("Address"),
                            resultSet.getString("Country"),
                            resultSet.getString("Region"),
                            mySticker,
                            doubleSticker);
                    user.setRequestList(getRequestListToUser(resultSet.getString("Name")));
                    treeMap.put(resultSet.getString("Name"), user);
                }
            } // includes finally resultSet.close();
        }
        catch (SQLException ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close()
        System.out.println("- request getUserTreeMap");
        return treeMap;
    }

    @Override
    public boolean addNewUser(User user) throws RemoteException, DataAccessException {
        System.out.println("+ request addNewUser");
        try (PreparedStatement statement =
                     getConnection()
                     .prepareStatement("INSERT INTO User " +
                                     "(Name, Password, Address, Country, Region) VALUES" +
                                     "  (?, ?, ?, ?, ?)")) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getCountry());
            statement.setString(5, user.getRegion());
            // 1. insert the new entry
            statement.executeUpdate();

        }
        catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();


        for(Map.Entry<Integer, Sticker> entry: user.getMySticker().entrySet()) {
            try (PreparedStatement statement =
                         getConnection()
                 .prepareStatement("INSERT INTO My_Sticker (User, Sticker) VALUES" +
                                 " (?, ?)")) {
                statement.setString(1, user.getName());
                statement.setInt(2, entry.getKey());
                // 1. insert the new entry
                statement.executeUpdate();

            } catch (Exception ex) {
                throw new DataAccessException("SQLException: " + ex.getMessage());
            } // includes finally statement.close();
        }

        for(Map.Entry<Integer, Sticker> entry: user.getDoubleSticker().entrySet()) {
            try (PreparedStatement statement =
                         getConnection()
                                 .prepareStatement("INSERT INTO Double_Sticker (User, Sticker) VALUES" +
                                         " (?, ?)")) {
                statement.setString(1, user.getName());
                statement.setInt(2, entry.getKey());
                // 1. insert the new entry
                statement.executeUpdate();

            } catch (Exception ex) {
                throw new DataAccessException("SQLException: " + ex.getMessage());
            } // includes finally statement.close();
        }
        System.out.println("- request addNewUser");
        return true;
    }

    @Override
    public boolean addNewRequest(String requestingUser, String askedUser, Integer stickerWanted, Integer stickerGiven) throws RemoteException, DataAccessException {
        System.out.println("+ request addNewRequest");
        try (PreparedStatement statement =
                     getConnection()
                 .prepareStatement("INSERT INTO Requests " +
                         "(Requesting_User, Asked_User, Sticker_Give, Sticker_Wanted) " +
                         "VALUES (?, ?, ?, ?)")) {
            statement.setString(1, requestingUser);
            statement.setString(2, askedUser);
            statement.setInt(3, stickerGiven);
            statement.setInt(4, stickerWanted);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- request addNewRequest");
        return true;
    }

    @Override
    public boolean deleteRequest(String requestingUser, String askedUser, Integer stickerWanted, Integer stickerGiven) throws RemoteException, DataAccessException {
        System.out.println("+ request deleteRequest");
        try (PreparedStatement statement =
                     getConnection()
                             .prepareStatement("DELETE FROM Requests WHERE" +
                                     " Requesting_User = ? AND" +
                                     " Asked_User = ? AND" +
                                     " Sticker_Give = ? AND" +
                                     " Sticker_Wanted = ?")) {
            statement.setString(1, requestingUser);
            statement.setString(2, askedUser);
            statement.setInt(3, stickerGiven);
            statement.setInt(4, stickerWanted);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- request deleteRequest");
        return true;
    }

    @Override
    public boolean addStickerToUser(String user, Integer sticker) throws RemoteException, DataAccessException {
        System.out.println("+ request addStickerToUser");
        try (PreparedStatement statement =
                     getConnection()
                             .prepareStatement("INSERT INTO My_Sticker (User, Sticker) VALUES" +
                                     " (?, ?)")) {
            statement.setString(1, user);
            statement.setInt(2, sticker);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- request addStickerToUser");
        return true;
    }

    @Override
    public boolean deleteStickerFromUser(String user, Integer sticker) throws RemoteException, DataAccessException {
        System.out.println("+ request deleteStickerFromUser");
        try (PreparedStatement statement =
                     getConnection()
                             .prepareStatement("DELETE FROM My_Sticker WHERE " +
                                     "User = ? AND Sticker = ?")) {
            statement.setString(1, user);
            statement.setInt(2, sticker);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- request deleteStickerFromUser");
        return true;
    }

    @Override
    public boolean addDoubleStickerToUser(String user, Integer sticker) throws RemoteException, DataAccessException {
        System.out.println("+ addDoubleStickerToUser");
        try (PreparedStatement statement =
                     getConnection()
                             .prepareStatement("INSERT INTO Double_Sticker (User, Sticker) VALUES" +
                                     " (?, ?)")) {
            statement.setString(1, user);
            statement.setInt(2, sticker);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- addDoubleStickerToUser");
        return true;
    }

    @Override
    public boolean deleteDoubleStickerFromUser(String user, Integer sticker) throws RemoteException, DataAccessException {
        System.out.println("+ request deleteDoubleStickerFromUser");
        try (PreparedStatement statement =
                     getConnection()
                             .prepareStatement("DELETE FROM Double_Sticker WHERE " +
                                     "User = ? AND Sticker = ?")) {
            statement.setString(1, user);
            statement.setInt(2, sticker);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- request deleteDoubleStickerFromUser");
        return true;
    }

    @Override
    public boolean updatePassword(String user, String password) throws RemoteException, DataAccessException {
        System.out.println("+ request updatePassword");
        try (PreparedStatement statement =
                     getConnection()
                             .prepareStatement("UPDATE User SET Password = ? WHERE Name = ?")) {
            statement.setString(1, password);
            statement.setString(2, user);
            // 1. insert the new entry
            statement.executeUpdate();

        } catch (Exception ex) {
            throw new DataAccessException("SQLException: " + ex.getMessage());
        } // includes finally statement.close();
        System.out.println("- request updatePassword");
        return true;
    }

    @Override
    public void close() throws Exception {
        try {
            if (connection != null) connection.close();
            connection = null;
        }
        catch (SQLException ex) {
            throw new DataAccessException("Problems closing database connection: SQLException: " + ex.getMessage());
        } // catch
    }
}
