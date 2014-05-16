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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Funcoes;

/**
 *
 * @author Gustavo
 */
public class Cliente extends Thread {
    
    private DatagramSocket socketUnicast; 
    private Processo processo;
    private MultiCastPeer multi;

    public Cliente(MultiCastPeer multi, Processo processo) {
        try {
            this.multi = multi;
            this.processo = processo;
            socketUnicast = new DatagramSocket();
            this.start();
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override    
    /**
     * Envia a lista atualizada de arquivos para o Tracke a cada 2 segundos
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
                    socketUnicast.send(pack);
                    buf = new byte[1024];
                    pack = new DatagramPacket(buf, buf.length);
                    socketUnicast.receive(pack);
                    String resposta = new String(pack.getData());
                    String respostaEsperada = Funcoes.to1024String("Request: getArquivos;");
//                    System.out.println("UNICAST <- " + resposta);
//                    System.out.println( new String("\tFrom: " + pack.getAddress().getHostAddress() + ":" + pack.getPort()) );
                    if(resposta.equals(respostaEsperada)){
                        Vector<String> arquivos = processo.getArquivosDoProcesso();
                        for(int i = 0; i < arquivos.size(); i++){
                            buf = arquivos.get(i).getBytes();
                            pack = new DatagramPacket(buf, buf.length, pack.getAddress(), pack.getPort());
                            socketUnicast.send(pack);
                        }
                    }
                    buf = "fim dos arquivos".getBytes();
                    pack = new DatagramPacket(buf, buf.length, pack.getAddress(), pack.getPort());
                    socketUnicast.send(pack);
                    this.sleep(2000);
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
