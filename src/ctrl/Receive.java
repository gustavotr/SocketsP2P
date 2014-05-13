/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1097075
 */
public class Receive {
    
    private MulticastSocket socket;
    private MultiCastPeer multi;
    
    public Receive(MultiCastPeer s) {
        try {
            multi = s;
            socket = multi.getSocket();
            
            byte buf[] = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buf, buf.length);
            socket.receive(pack);
            // Finally, let us do something useful with the data we just received,
            // like print it on stdout :-)
            System.out.println("Received data from: " + pack.getAddress().toString()
                    + ":" + pack.getPort() + " with length: "
                    + pack.getLength());
            System.out.write(pack.getData(), 0, pack.getLength());
            System.out.println();
            // And when we have finished receiving data leave the multicast group and
            // close the socket
            socket.leaveGroup(multi.getGroup());
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
