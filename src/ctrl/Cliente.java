/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo
 */
public class Cliente extends Thread {
    
    private DatagramSocket socketUDP; 
    private Processo processo;
    private MultiCastPeer multi;

    public Cliente(MultiCastPeer multi, Processo processo) {
        try {
            this.multi = multi;
            this.processo = processo;
            socketUDP = new DatagramSocket();
            this.start();
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override    
    /**
     * Mantem Comunicacao UNICAST com o tracker
     */
    public void run() {        
        while (true) {
            if (!processo.knowTracker()) {
                System.out.println(multi.eleicao());
            }else{  
                try {
                    //Sabe quem é o Tracker                    
                    String str = "Peer "+processo.getId()+" diz: oi tracker!"; 
                    byte[] buf = str.getBytes();
                    DatagramPacket pack = new DatagramPacket(buf, buf.length, processo.getTracker().getAddress(), Tracker.UDPPort);
                    socketUDP.send(pack);
                    buf = new byte[1024];
                    pack = new DatagramPacket(buf, buf.length);
                    socketUDP.receive(pack);
                    String resposta = new String(pack.getData());
                    System.out.println("UNICAST <- " + resposta);
                    System.out.println( new String("\tFrom: " + pack.getAddress().getHostAddress() + ":" + pack.getPort()) );
                    Random rnd = new Random();
                    this.sleep(1000 + rnd.nextInt(5000));
                } catch (SocketException ex) {
                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    

    
}
