/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import ctrl.Tracker;
import java.io.File;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author a1097075
 */
public class Processo {
    
    private int id;
    private String folderPath;
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
        multi = new MultiCastPeer(this);
        this.folderPath = "src/arquivos/processo"+(rnd.nextInt(4)+1);        
        setArquivos();       
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
}