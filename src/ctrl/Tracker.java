/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.io.IOException;
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
    private MulticastSocketP2P socketP2P;
    private int PORT = 5050;

    public Tracker() {
        try {
            arquivosDoTracker = new ArrayList<Arquivo>();
            socketP2P = new MulticastSocketP2P(PORT);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void run() {        
        while (!socketP2P.isClosed()) { 
            try {
                System.out.println("\nTracker ativo!");
                socketP2P.enviarMensagem("eu sou o tracker!");
                this.sleep(5000);
//                byte buf[] = new byte[1024];
//                DatagramPacket pack = new DatagramPacket(buf, buf.length);
//                socketP2P.receive(pack);
//                String pergunta = new String(pack.getData());
//                System.out.println("TRACKER RECEBEU -----> "+pergunta);
////                System.out.println("Tracker recebeu de: " + pack.getAddress().toString()
////                                    + ":" + pack.getPort() + " with length: "
////                                    + pack.getLength());
////                            System.out.println("Mensagem: ");
////                            System.out.write(pack.getData(), 0, pack.getLength());
//                //if (pergunta.equals("quem e o tracker?")){
//                    System.out.println("Respondendo!!!!!!!");
//                    String resposta = "eu sou o tracker";
//                    byte[] byteMsg = resposta.getBytes();
//                    DatagramPacket sendPacket = new DatagramPacket(byteMsg, byteMsg.length, socketP2P.getGroup(), socketP2P.getPORT());                    
//                    socketP2P.send(pack);
//                    System.out.println("ENVIOU!!!!!!!");
//                }
//            } catch (IOException ex) {                
//                Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
//            }
            } catch (InterruptedException ex) {
                Logger.getLogger(Tracker.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
    }
    
}
