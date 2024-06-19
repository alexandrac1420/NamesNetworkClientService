package edu.escuelaing.arsw.Exercise_1;

import java.io.*;
import java.net.*;

public class Exercise_1 {

    public static void main(String[] args) {
        try {
            URL google = new URL("http://ldbn.escuelaing.edu.co:80/index.html");

            // Imprimir los datos utilizando los métodos de URL
            System.out.println("Protocol: " + google.getProtocol());
            System.out.println("Authority: " + google.getAuthority());
            System.out.println("Host: " + google.getHost());
            System.out.println("Port: " + google.getPort());
            System.out.println("Path: " + google.getPath());
            System.out.println("Query: " + google.getQuery());
            System.out.println("File: " + google.getFile());
            System.out.println("Ref: " + google.getRef());

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }
}