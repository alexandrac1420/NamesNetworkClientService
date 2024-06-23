package edu.escuelaing.arsw.Exercise_8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatClientImpl extends UnicastRemoteObject implements MessengerService {

    protected ChatClientImpl() throws RemoteException {
        super();
    }

    @Override
    public void registerClient(String clientName, MessengerService client) throws RemoteException {
    }

    @Override
    public void sendMessage(String clientName, String message) throws RemoteException {
    }

    @Override
    public String receiveMessage(String message) throws RemoteException {
        System.out.println();
        System.out.println("Nuevo mensaje recibido: " + message);
        return message;
    }
}
