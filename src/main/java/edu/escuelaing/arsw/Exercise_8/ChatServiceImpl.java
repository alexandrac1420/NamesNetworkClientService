package edu.escuelaing.arsw.Exercise_8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ChatServiceImpl extends UnicastRemoteObject implements MessengerService {

    private Map<String, MessengerService> clients;
    private Map<String, String> messages;

    protected ChatServiceImpl() throws RemoteException {
        super();
        this.clients = new HashMap<>();
        this.messages = new HashMap<>();
    }

    @Override
    public void registerClient(String clientName, MessengerService client) throws RemoteException {
        clients.put(clientName, client);
        System.out.println("Cliente '" + clientName + "' se ha conectado.");
    }

    @Override
    public void sendMessage(String clientName, String message) throws RemoteException {
        String fullMessage = clientName + ": " + message;
        System.out.println("Mensaje recibido: " + fullMessage);
        messages.put(clientName, fullMessage);

        // Envía el mensaje a todos los clientes conectados, excepto al remitente
        for (String name : clients.keySet()) {
            if (!name.equals(clientName)) {
                clients.get(name).receiveMessage(clientName + ": " + message);
            }
        }
    }

    @Override
    public String receiveMessage(String clientName) throws RemoteException {
        return messages.getOrDefault(clientName, null);
    }

    public static void main(String[] args) {
        try {
            ChatServiceImpl chatService = new ChatServiceImpl();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("rmi://localhost:1099/ChatService", chatService);
            System.out.println("Servidor de chat listo en el puerto 1099.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
