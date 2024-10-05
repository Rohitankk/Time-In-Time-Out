package Server;

import Server.ReadersAndWriters.Exceptions;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ClientFunctions extends Remote {

    public boolean login(String name, String password) throws RemoteException, Exceptions;

    public boolean register(String name, String password, String confirm) throws RemoteException, Exceptions;

    public ArrayList<ArrayList<String>> getSummary(String username) throws RemoteException;

    public boolean getStatus(String username) throws RemoteException;

    public void timeIn(String username, String timeIn) throws RemoteException;
    public void timeOut(String username, String timeOut) throws RemoteException;
}
