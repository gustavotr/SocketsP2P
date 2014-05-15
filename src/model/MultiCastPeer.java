package model;

import ctrl.Tracker;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastPeer extends Thread {

    private MulticastSocketP2P multicastSocket;
    private Processo processo;
    private boolean received;
    private ArrayList<Peer> peers;

    /**
     * Construtora do Multicast por processo.
     *
     * @param processo Recebe o processo como parâmetro.
     */
    public MultiCastPeer(Processo processo) {
        try {
            this.processo = processo;
            this.peers = new ArrayList<>();
            multicastSocket = new MulticastSocketP2P();
            String peer = "Peer id:"+processo.getId();
            multicastSocket.enviarMensagem(peer);
            while(peers.size() < 4){
                byte[] buff = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buff, buff.length);
                multicastSocket.receive(pack);
                String respostaEsperada = to1024String("Peer id:");
                String resposta = new String(pack.getData());  
                if(resposta.substring(0,7).equals(respostaEsperada.substring(0,7))){                   
                   int tempID = Integer.parseInt(resposta.substring(8,9));
                   if(!peersHasID(tempID)){
                       Peer newPeer = new Peer(tempID,pack.getAddress(),pack.getPort());
                       peers.add(newPeer);
                       multicastSocket.enviarMensagem(peer);
                       System.out.println("Novo peer: "+newPeer.getSettings());
                   }
                }
            }
            System.out.println("Peers adicionados");
            System.out.println(eleicao());
            //this.start();
        } catch (IOException ex) {
            Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override    
    public void run() {        
//        while (true) {
//            if (!processo.knowTracker()) {
//                try {
//                    /// Perguntar quem é o tracker
//                    //socketP2P.enviarMensagem("quem e o tracker?");
//                    received = false;
//                    while (!received) {
//                        //aguarda resposta
//                        byte buf[] = new byte[1024];
//                        DatagramPacket pack = new DatagramPacket(buf, buf.length);                         
//                        System.out.println("Esperando resposta");                        
//                        multicastSocket.setSoTimeout(5000);
//                        multicastSocket.receive(pack);
//                        String resposta = new String(pack.getData());                        
//                        String respostaEsperada = to1024String("eu sou o tracker!");
//                        if (respostaEsperada.equals(resposta)){
//                            received = true;
//                            // Finally, let us do something useful with the data we just received,
//                            // like print it on stdout :-)
//                            System.out.println("Peer recebeu de: " + pack.getAddress().toString()
//                                    + ":" + pack.getPort() + " with length: "
//                                    + pack.getLength());
//                            System.out.println("Mensagem: ");
//                            System.out.write(pack.getData(), 0, pack.getLength());
//                            System.out.println("");                             
//                        }
//                    }
//                } catch (SocketTimeoutException e) {
//                    try {
//                        // timeout exception.
//                        System.out.println("Nenhum tracker respondeu");
//                        System.out.println("Eu serei o tracker");
//                        processo.setTheTracker(processo.getId(), InetAddress.getByName("localhost"));                        
//                    } catch (UnknownHostException ex) {
//                        Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }catch (IOException ex) {
//                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{  
//                try {
//                    //Sabe quem é o Tracker
//                    DatagramSocket socketUDP = new DatagramSocket();
//                    byte[] buf = "Essa é minha chave pública".getBytes();
//                    DatagramPacket pack = new DatagramPacket(buf, buf.length, processo.getTrackerAddress(), Tracker.UDPPort);
//                    socketUDP.send(pack);
//                } catch (SocketException ex) {
//                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }

    public String eleicao(){
        Peer peerEleito = peers.get(0);
        int idProcessoEleito = peerEleito.getId(); 
        
        for (int i = 1; i < this.peers.size(); i++) {
            if (idProcessoEleito < peers.get(i).getId()) {
                peerEleito = peers.get(i);
                idProcessoEleito = peerEleito.getId();               
            }
        }
        
        processo.setTheTracker(peerEleito);
        String tracker = "Tracker("+peerEleito.getSettings()+")";
        return tracker;
    }
    
    /**
     * Converte uma String em uma String que ocupa 1024 bytes de um byte array
     *
     * @param str recebe uma String como parâmetro.
     * 
     * @return retorna a nova String
     */
    private String to1024String(String str) {
        byte[] buf = new byte[1024];
        byte[] temp = str.getBytes();
        System.arraycopy(temp, 0, buf, 0, temp.length);
        return new String(buf);        
    }

    private boolean peersHasID(int tempID) {
        for(int i = 0; i < peers.size(); i++){
            if(peers.get(i).getId() == tempID){
                return true;
            }
        }
        return false;
    }
}
