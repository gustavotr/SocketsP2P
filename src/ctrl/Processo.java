/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Arquivo;
import model.Peer;
import util.Funcoes;

/**
 *
 * @author a1097075
 */
public class Processo implements Runnable{
    
    private int id;
    private String folderPath;
    private static Vector<String> busca;
    private String stringBuscada;
    private Vector<String> arquivosDoProcesso;
    private MultiCastPeer multi;
    private Peer tracker;    
    private boolean knowTracker;
    private Tracker myTracker;
    private Cliente cliente;

    public Processo() {
        Random rnd = new Random();
        id = 10 + rnd.nextInt(89);
        knowTracker = false;        
        this.folderPath = "src/arquivos/processo"+(rnd.nextInt(4)+1);                       
        setArquivos();
        multi = new MultiCastPeer(this);        
    }
    
    /**
     * Procura por todos os arquivos que estão na pasta do processo
     * e os adiciona ao Vector arquivosDoProcesso
     */
    public void setArquivos(){
        arquivosDoProcesso = new Vector<String>();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        
            for(int i = 0; i < listOfFiles.length; i++){
                arquivosDoProcesso.add(listOfFiles[i].getName());
            }
    }
    
    
    public int getId() {
        return id;
    } 
           
    public boolean knowTracker(){
        return knowTracker;
    }       
    
    public Vector<String> getArquivosDoProcesso() {
        return arquivosDoProcesso;
    } 

    public Peer getTracker() {
        return tracker;
    }
    
    public void setTheTracker(Peer peer){
        this.tracker = peer;        
        knowTracker = true;
        if(tracker.getId() == id){
            myTracker = new Tracker(id);
        }
        
        cliente = new Cliente(multi, this);
    }   
    
    /**
     * Funcao chamada quando o traquer cai
     * irá desvincular o tracker que caiu para que haja uma nova eleicao
     */
    public void setNoTracker() {
        knowTracker = false;
    }

    public Vector<String> buscarAquivo(String search) {
        stringBuscada = search;
        new Thread(this).start();        
        return busca; 
    }

    public Vector<String> getBusca() {
        return busca;
    }
    

    @Override
    public void run() {
        busca = new Vector<>();
        try {
            System.out.println("Comecou Busca");
            String str = "Request: buscar("+stringBuscada+")";            
            byte[] buf = str.getBytes();
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket pack = new DatagramPacket(buf, buf.length, tracker.getAddress(), Tracker.UDPPort);
            socket.send(pack);            
            String statusDoPedido = "empty";
            String statusFinal = Funcoes.to1024String("fim dos arquivos");
            while(!statusDoPedido.equals(statusFinal)){                
                buf = new byte[1024];
                pack = new DatagramPacket(buf, buf.length);
                socket.receive(pack);
                //System.out.println("Recebeu resposta da busca");                
                statusDoPedido = new String(pack.getData());
                //System.out.println(statusDoPedido);
                if(!statusDoPedido.equals(statusFinal)){
                    //System.out.println("Novo Arquivo ------------------");
                    busca.add(statusDoPedido);
                }                        
            }
            socket.close();
            //System.out.println("Terminou Busca");
            
        } catch (SocketException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Processo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}