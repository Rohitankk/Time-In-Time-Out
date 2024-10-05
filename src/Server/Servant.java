package Server;

import Server.ReadersAndWriters.Exceptions;
import Server.ReadersAndWriters.LogsRAWs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static Server.ReadersAndWriters.AccountRAWs.*;
import static Server.ReadersAndWriters.LogsRAWs.*;

public class Servant extends UnicastRemoteObject implements ClientFunctions {
    private HashMap<String, String> users;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    public Servant() throws RemoteException {
        super();
        users= new HashMap<>();
    }
    public boolean login(String name, String password) throws RemoteException , Exceptions{
        users = readUsers();
        if (!users.containsKey(name)){
           throw new Exceptions.UserNotFoundException();
        }
        if (!users.get(name).equals(password)){
            throw new Exceptions.IncorrectPasswordException();
        }else{
            for(String username: users.keySet()){
                if(Objects.equals(name, username) && Objects.equals(password, users.get(username))){
                    System.out.println("User " + name + " has logged in");

                }
            }
            return true;
        }
    }

    @Override
    public boolean register(String name, String password, String confirm) throws RemoteException, Exceptions {
        users = readUsers();
        if (!Objects.equals(password, confirm)){
           throw new Exceptions.PasswordMismatchException();
        }
        if (users.containsKey(name)){
            throw new Exceptions.UserAlreadyExistsExceptions();
        }else {
            try {
                addRequest(name, password);
                Server.refreshRequestList();

            } catch (RuntimeException e) {
                System.out.println("request received");
            }
            return true;
        }

    }
    public ArrayList<ArrayList<String>> getSummary(String username){
        return readLogs(username);
    }

    @Override
    public boolean getStatus(String username) throws RemoteException {
        HashMap<String, String> statusMap = Server.getUserStatus();
        if(Objects.equals(statusMap.get(username), "Working")){
            return true;
        }
        //return status
        //upon user login, set the homepage such that the timeIn and timeOut buttons correspond to the users
        //status
        return false;
    }

    public void timeIn(String username, String timeIn) throws  RemoteException{
        writeTimeIn(username, timeIn);
        Server.setUserStatus(LogsRAWs.checkLastTimeOut());

        Server.getHomepageInterface().fillTable(Server.toArray(Server.getUserStatus()));
        Server.getHomepageInterface().repaint();
        Server.getHomepageInterface().revalidate();
    }

    public void timeOut(String username, String timeOut) throws  RemoteException{
        writeTimeOut(username, timeOut);
        Server.setUserStatus(LogsRAWs.checkLastTimeOut());

        Server.getHomepageInterface().fillTable(Server.toArray(Server.getUserStatus()));
        Server.getHomepageInterface().repaint();
        Server.getHomepageInterface().revalidate();
    }

    public static void main(String[] args) throws RemoteException {
        Servant servant = new Servant();
        System.out.println(servant.getStatus("Melody"));
        System.out.println(servant.getStatus("Rohit"));
    }
}
