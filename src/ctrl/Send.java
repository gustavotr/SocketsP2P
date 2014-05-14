/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1097075
 */
public class Send {
    
     private MulticastSocket socket;
    private MulticastSocketP2P socketP2P;

    public Send(MulticastSocketP2P s) {
        try {
            socketP2P = s;
            socket = socketP2P.getSocket();
            // Fill the buffer with some data
            byte buf[] = new byte[10];
            for (int i=0; i<buf.length; i++) buf[i] = (byte)i;
            // Create a DatagramPacket
            DatagramPacket pack;
            pack = new DatagramPacket(buf, buf.length,
                    socketP2P.getGroup(), socketP2P.getPORT());
            // Do a send. Note that send takes a byte for the ttl and not an int.
            socket.send(pack);
            // And when we have finished sending data close the socket
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
