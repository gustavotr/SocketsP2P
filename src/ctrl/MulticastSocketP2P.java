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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1097075
 */
public class MulticastSocketP2P extends MulticastSocket{
    
    private final String HOST = "229.10.10.100";
    private final int TIMEOUT = 20000;
    private InetAddress group;
    private int PORT = 5050;
    
    public MulticastSocketP2P(int port) throws IOException {
        super(port);
        group = InetAddress.getByName(HOST);                    
        this.setSoTimeout(TIMEOUT);
        this.joinGroup(group);
    }  

    public InetAddress getGroup() {
        return group;
    }

   public String getHOST() {
        return HOST;
    } 

    public int getPORT() {
        return PORT;
    }
    
    /**
     * Envia uma mensagem para o grupo multicast
     *
     * @param msg
     *
     */
    public void enviarMensagem(String msg) {
        byte[] byteMsg = msg.getBytes();
        DatagramPacket msgOut = new DatagramPacket(byteMsg, byteMsg.length, group, PORT);
        try {            
            this.send(msgOut);    
        } catch (IOException e) {
            System.out.println("Erro I/O: " + e.getLocalizedMessage());
        }
    }
    
}
