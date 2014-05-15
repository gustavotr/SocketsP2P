/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import ctrl.MultiCastPeer;
import ctrl.Tracker;
import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author a1097075
 */
public class Processo {
    
    private boolean tracker = false;
    private boolean client = false;
    private int id;
    private String folderPath;
    private Vector<String> arquivosDoProcesso;    
    private MultiCastPeer multi;
    private int trackerID;
    private Tracker myTracker;
    private boolean knowTracker = false;
    private ArrayList<Processo> processosNaRede;    
    private InetAddress trackerAddress;

    public Processo() {
        Random rnd = new Random();
        id = rnd.nextInt();
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

    public InetAddress getTrackerAddress() {
        return trackerAddress;
    }    
    
           
    public boolean knowTracker(){
        return knowTracker;
    }       
    
    public Vector<String> getArquivosDoProcesso() {
        return arquivosDoProcesso;
    } 

    public void setTheTracker(int trackerID, InetAddress trackerAddress){
        this.trackerID = trackerID;
        this.trackerAddress = trackerAddress;
        knowTracker = true;
        if(trackerID == id){
            myTracker = new Tracker(id);
        }
    }
    }