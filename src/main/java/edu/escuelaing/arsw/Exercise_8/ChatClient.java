package edu.escuelaing.arsw.Exercise_8;

import java.rmi.Naming;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingrese la dirección IP del servidor RMI: ");
            String serverIP = scanner.nextLine();

            System.out.print("Ingrese el puerto del servidor RMI: ");
            int serverPort = Integer.parseInt(scanner.nextLine());

            System.out.print("Ingrese su nombre de usuario: ");
            String clientName = scanner.nextLine();

            MessengerService chatService = (MessengerService) Naming.lookup("rmi://" + serverIP + ":" + serverPort + "/ChatService");

            MessengerService client = new ChatClientImpl();
            chatService.registerClient(clientName, client);

            System.out.println("Conectado al servidor de chat.");

            while (true) {
                System.out.print("Escriba un mensaje (exit para salir): ");
                String message = scanner.nextLine();

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }

                chatService.sendMessage(clientName, message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
