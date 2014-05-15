/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import ctrl.MultiCastPeer;
import ctrl.Tracker;
import java.io.File;
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
    private int theTracker;
    private Tracker myTracker;
    private boolean knowTracker = false;
    private ArrayList<Processo> processosNaRede;    

    public Processo() {
        Random rnd = new Random();
        id = rnd.nextInt();
        multi = new MultiCastPeer(this);        
        this.folderPath = "src/arquivos/processo"+(rnd.nextInt(4)+1);        
        setArquivos();       
    }
    
    public void setArquivos(){
        arquivosDoProcesso = new Vector<String>();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        
            for(int i = 0; i < listOfFiles.length; i++){
                arquivosDoProcesso.add(listOfFiles[i].getName());
            }
    }

    public void setIsTracker(boolean isTracker) {
        this.tracker = isTracker;
    }
    
   public boolean isTracker(){
       return tracker;
   }   

    public ArrayList<Processo> getProcessosNaRede() {
        return processosNaRede;
    }
    
    public void adicionarProcesso(Processo p){
        processosNaRede.add(p);
    }

    public boolean isClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

    public void setTracker(boolean tracker) {
        this.tracker = tracker;
    }    

    public int getId() {
        return id;
    }
           
    public boolean knowTracker(){
        return knowTracker;
    }       
    
    public void eleicao(){
        int idProcessoEleito = getProcessosNaRede().get(0).getId();

        for (int i = 1; i < this.getProcessosNaRede().size(); i++) {
            if (idProcessoEleito < getProcessosNaRede().get(i).getId()) {
                idProcessoEleito = getProcessosNaRede().get(i).getId();
            }
        }

        if (this.getId() == idProcessoEleito) {
            tracker = true;
            client = false;            
        } else {
            tracker = false;
            client = true;
        }
        
        knowTracker = true;
        theTracker = idProcessoEleito;
    }

    public Vector<String> getArquivosDoProcesso() {
        return arquivosDoProcesso;
    } 

    public void setTheTracker(int theTracker){
        this.theTracker = theTracker;
        if(theTracker == id){
            myTracker = new Tracker();
        }
    }
    }