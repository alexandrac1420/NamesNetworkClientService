package edu.escuelaing.arsw.Exercise_6;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer_Exercise6 {
    private static final String BASE_DIRECTORY = "src/main/java/edu/escuelaing/arsw/Exercise_6/";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        System.out.println("Listo para recibir ...");

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        StringBuilder request = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.isEmpty()) {
                break;
            }
            request.append(inputLine + "\n");
        }

        System.out.println("Received: \n" + request.toString());

        String[] requestLines = request.toString().split("\n");
        String getLine = requestLines[0];
        String[] getParts = getLine.split(" ");
        String filePath = getParts[1].substring(1);

        if (filePath.isEmpty()) {
            filePath = "index.html"; 
        }

        filePath = BASE_DIRECTORY + filePath; // Prepend the base directory to the file path
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            String contentType = Files.probeContentType(file.toPath());
            byte[] fileContent = Files.readAllBytes(file.toPath());

            out.print("HTTP/1.1 200 OK\r\n");
            out.print("Content-Type: " + contentType + "\r\n");
            out.print("Content-Length: " + fileContent.length + "\r\n");
            out.print("\r\n");
            out.flush();

            OutputStream dataOut = clientSocket.getOutputStream();
            dataOut.write(fileContent, 0, fileContent.length);
            dataOut.flush();
        } else {
            String outputLine = "HTTP/1.1 404 Not Found\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<title>404 Not Found</title>\n" + "</head>"
                    + "<body>"
                    + "File Not Found"
                    + "</body>"
                    + "</html>";
            out.println(outputLine);
        }

        out.close();
        in.close();
        clientSocket.close();
    }
}
