package edu.escuelaing.arsw.Exercise_8;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessengerService extends Remote {
    void registerClient(String clientName, MessengerService client) throws RemoteException;
    void sendMessage(String clientName, String message) throws RemoteException;
    String receiveMessage(String clientName) throws RemoteException;
}