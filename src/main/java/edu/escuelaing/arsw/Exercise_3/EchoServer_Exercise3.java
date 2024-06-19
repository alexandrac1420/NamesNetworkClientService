package edu.escuelaing.arsw.Exercise_3;

import java.io.*;
import java.net.*;

public class EchoServer_Exercise3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null) {
            try {
                int number = Integer.parseInt(inputLine);
                int squaredNumber = number * number;
                out.println("El cuadrado es "+ squaredNumber);
            } catch (NumberFormatException e) {
                out.println("Please enter a valid integer.");
            }
            if (inputLine.equals("Respuestas: Bye.")) {
                break;
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
