/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import model.MulticastSocketP2P;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Arquivo;

/**
 *
 * @author Gustavo
 */
public class Tracker extends Thread{
    
    private ArrayList<Arquivo> arquivosDoTracker;
    private MulticastSocketP2P multicastSocket;
    private DatagramSocket socketUDP;
    private int idTracker;

    /**
     * Numero da porta UDP do Tracker para receber requisicoes
     */
    public static final int UDPPort = 6066;
    
    /**
     * Construtor do Tracker
     * @param id recebe um int que e o id do processo que o gerou
     */
    public Tracker(int id) {
        try {            
            idTracker = id;
            arquivosDoTracker = new ArrayList<>();
            multicastSocket = new MulticastSocketP2P();
            socketUDP  = new DatagramSocket(UDPPort);
            TrackerHello trackerHello = new TrackerHello();
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
    @Override
    public void run() {        
        while (true) { 
            try {
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);                
                socketUDP.receive(pack);
                String resposta = new String(pack.getData());
                System.out.println("UNICAST <- " + resposta);  
                System.out.println( new String("\tFrom: " + pack.getAddress().getHostAddress() + ":" + pack.getPort()) );
                String saudacao = "oi peer " + resposta.substring(5,7);
                buf = saudacao.getBytes();
                pack = new DatagramPacket(buf, buf.length, pack.getAddress(), pack.getPort());
                socketUDP.send(pack);
            } catch (IOException ex) {
                Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
    } 
   
 
     public class TrackerHello extends Thread{

        public TrackerHello() {
            this.start();
        }
        
         @Override
        public void run() {        
            while (!multicastSocket.isClosed()) { 
                try {
                    System.out.println("\nTracker ativo!");
                    multicastSocket.enviarMensagem("eu sou o tracker! ID:"+idTracker);
                    this.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
                }
           }

        }
        
    }
}


