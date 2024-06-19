package edu.escuelaing.arsw.Exercise_7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeClient_Exercise7 {

    public static void main(String[] args) {
        byte[] sendBuf = new byte[256];
        DatagramSocket socket = null;
        InetAddress address = null;
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("127.0.0.1");
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(DatagramTimeClient_Exercise7.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        String lastReceived = "No time received yet";

        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 4445);
                socket.send(packet);

                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.setSoTimeout(5000);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                lastReceived = "Date: " + received;
                System.out.println(lastReceived);
            } catch (SocketException ex) {
                Logger.getLogger(DatagramTimeClient_Exercise7.class.getName()).log(Level.SEVERE, "Socket error", ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(DatagramTimeClient_Exercise7.class.getName()).log(Level.SEVERE, "Unknown host", ex);
            } catch (IOException ex) {
                System.err.println("Server not available, maintaining last received time: " + lastReceived);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DatagramTimeClient_Exercise7.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
