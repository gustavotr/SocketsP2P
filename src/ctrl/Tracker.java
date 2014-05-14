/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Arquivo;

/**
 *
 * @author Gustavo
 */
public class Tracker extends Thread{
    
    private ArrayList<Arquivo> arquivosDoTracker;
    private MulticastSocketP2P socketP2P;

    public Tracker(MulticastSocketP2P s) {
        try {
            arquivosDoTracker = new ArrayList<Arquivo>();
            socketP2P = new MulticastSocketP2P(5050);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run() {        
        while (!socketP2P.isClosed()) { 
            try {
                System.out.println("");
                byte buf[] = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buf, buf.length);
                socketP2P.receive(pack);
                String pergunta = new String(pack.getData());
                System.out.println("TRACKER RECEBEU -----> "+pergunta);
//                System.out.println("Tracker recebeu de: " + pack.getAddress().toString()
//                                    + ":" + pack.getPort() + " with length: "
//                                    + pack.getLength());
//                            System.out.println("Mensagem: ");
//                            System.out.write(pack.getData(), 0, pack.getLength());
                //if (pergunta.equals("quem e o tracker?")){
                    System.out.println("Respondendo!!!!!!!");
                    String resposta = "eu sou o tracker";
                    byte[] byteMsg = resposta.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(byteMsg, byteMsg.length, pack.getAddress(), pack.getPort());                    
                    socketP2P.send(pack);
                //}               
                
                            
                
            } catch (IOException ex) {                
                Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
