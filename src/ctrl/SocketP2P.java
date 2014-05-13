/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a1097075
 */
public class SocketP2P {
    
    private final String HOST = "229.10.10.100";
    private final int PORT = 5050;
    private final int TIMEOUT = 20000;
    private InetAddress group;
    private MulticastSocket socket;
    private String localHost;

    public SocketP2P() {
        try {
            
            group = InetAddress.getByName(HOST);            
            socket = new MulticastSocket(PORT);            
            socket.setSoTimeout(TIMEOUT);
            socket.joinGroup(group);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketP2P.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MulticastSocket getSocket() {
        return socket;
    }

    public InetAddress getGroup() {
        return group;
    }

    public int getPORT() {
        return PORT;
    }

    public String getHOST() {
        return HOST;
    } 

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }

    public String getLocalHost() {
        return localHost;
    }   
    
}
