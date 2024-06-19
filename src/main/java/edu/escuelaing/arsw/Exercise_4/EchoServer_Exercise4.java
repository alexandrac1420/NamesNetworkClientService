package edu.escuelaing.arsw.Exercise_4;

import java.io.*;
import java.net.*;

public class EchoServer_Exercise4 {
    private static String currentFunction = "cos";
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
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("fun:")) {
                String function = inputLine.substring(4).trim();
                if (function.equals("sin") || function.equals("cos") || function.equals("tan")) {
                    currentFunction = function;
                    System.out.println("Function changed to: " + currentFunction);
                    out.println("Function changed to: " + currentFunction);
                } else {
                    out.println("Unknown function: " + function);
                }
            } else {
                try {
                    double number = Double.parseDouble(inputLine);
                    double result = calculate(number);
                    System.out.println("Received number: " + number + ", " + currentFunction + ": " + result);
                    out.println(result);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input received: " + inputLine);
                    out.println("Please enter a valid number.");
                }
            }

            if (inputLine.equalsIgnoreCase("bye")) {
                break;
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    private static double calculate(double number) {
        switch (currentFunction) {
            case "sin":
                return Math.sin(number);
            case "cos":
                return Math.cos(number);
            case "tan":
                return Math.tan(number);
            default:
                return Double.NaN;
        }
    }
}
