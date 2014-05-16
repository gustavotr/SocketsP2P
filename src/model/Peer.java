/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.net.InetAddress;

/**
 *
 * @author Gustavo
 */
public class Peer {
    
    private InetAddress address;
    private int id;
    private int port;

    public Peer(int id, InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.id = id;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }   

    public int getPort() {
        return port;
    }
    
    public String getSettings(){
        String settings = "ID: "+id+"; Address: "+address+":"+port;
        return settings;
    }
    
}
