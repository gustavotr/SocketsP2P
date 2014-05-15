package model;

import ctrl.Tracker;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastPeer extends Thread {

    private MulticastSocketP2P multicastSocket;    
    private Processo processo;
    private ArrayList<Peer> peers;

    /**
     * Construtora do Multicast por processo.
     *
     * @param processo Recebe o processo como parâmetro.
     */
    public MultiCastPeer(Processo processo) {
        try {
            this.processo = processo;
            multicastSocket = new MulticastSocketP2P(); 
            System.out.println(eleicao());
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override    
    public void run() {        
        while (true) {
            if (!processo.knowTracker()) {
                eleicao();
            }else{  
                try {                                        
                    byte[] buf = new byte[1024];
                    DatagramPacket pack = new DatagramPacket(buf, buf.length);
                    multicastSocket.receive(pack);
                    String resposta = new String(pack.getData());
                    System.out.println("MULTICAST <- "+resposta);
                    this.sleep(1000);
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

    public Processo getProcesso() {
        return processo;
    }
    
    public String eleicao(){
        
        peers = new ArrayList<>();
        
        String peer = "Peer id:"+processo.getId();
        multicastSocket.enviarMensagem(peer);
        
        while(peers.size() < 4){
            
            byte[] buff = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buff, buff.length);
            
            try {
                multicastSocket.receive(pack);
            } catch (IOException ex) {
                Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String respostaEsperada = to1024String("Peer id:");
            String resposta = new String(pack.getData());
            
            if(resposta.substring(0,7).equals(respostaEsperada.substring(0,7))){                   
               int tempID = Integer.parseInt(resposta.substring(8,10));               
               if(!peersHasID(tempID)){
                   Peer newPeer = new Peer(tempID,pack.getAddress(),pack.getPort());
                   peers.add(newPeer);
                   multicastSocket.enviarMensagem(peer);
                   System.out.println("Novo peer: "+newPeer.getSettings());
               }
            }
        }
        
        System.out.println("Peers adicionados");

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
