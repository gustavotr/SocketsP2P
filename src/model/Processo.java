package model;

import java.io.IOException;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ctrl.MultiCastPeer;
import java.io.File;

public class Processo {

    private int id;
    private transient MultiCastPeer multicastConnection;
    private transient boolean isServer = false;
    private transient boolean isClient = false;
    private transient ArrayList<Integer> idProcessos;
    private transient ArrayList<Processo> listaProcessos;
    private transient ArrayList<String> arquivos;
    private transient int numeroDeProcessos = 0;
    private transient Server servidorUDP = null;
    private transient Client clienteUDP = null;
    private transient PrivateKey chavePrivada;
    private PublicKey chavePublica = null;
    private boolean chavePublicaRecebida = false;
    Random r = new Random();
    private int porta = 0;

    /**
     * Construtora para o Jogador
     *
     * @param nick recebe o nick do jogador
     */
    public Processo() {
        this.isServer = false;
        idProcessos = new ArrayList<Integer>();
        listaProcessos = new ArrayList<Processo>();
        id = r.nextInt(10000);
        addIdProcessos(id);
        multicastConnection = new MultiCastPeer(this);
        addProcesso(this);

        //gera uma porta para o Jogador caso necessite criar um server UDP (range entre 6000 à 7000)
        porta = 6000 + (int) (Math.random() * ((6999 - 6000) + 1));

    }

    /**
     * Gerador de ID
     *
     * @param id recebe o ID do jogador e insere na lista
     */
    public void addIdProcessos(int id) {
        boolean insereId = true;

        for (int idDoProcesso : idProcessos) {
            if (idDoProcesso == id) {
                insereId = false;
                break;
            }
        }

        if (insereId) {
            idProcessos.add(id);
            numeroDeProcessos++;
        }
    }
   

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

//	public byte[] getPrivateKey() {
//		try {
//			return Serializer.serialize(chavePrivada);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public byte[] getPublicKey() {
//		try {
//			return Serializer.serialize(chavePublica);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public PrivateKey getChavePrivada() {
//		return chavePrivada;
//	}
//
//	public PublicKey getChavePublica() {
//		return chavePublica;
//	}
    /**
     * Método que adiciona os outros jogadores a lista de jogadores deste
     * Jogador
     *
     * @param processo
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addProcesso(Processo processo) {
        boolean insere = true;

        for (Processo p : getListaProcessos()) {
            if (processo.getId() == (p.getId())) {
                insere = false;
                break;
            }
        }

        if (insere) {
            getListaProcessos().add(processo);
        }

        Collections.sort(listaProcessos, new Comparator() {

            @Override
            public int compare(Object obj1, Object obj2) {
                Processo p1 = (Processo) obj1;
                Processo p2 = (Processo) obj2;

                if (p1.getId() < p2.getId()) {
                    return -1;
                } else if (p1.getId() > p2.getId()) {
                    return +1;
                }
                return 0;

            }
        });
    }

    public boolean isDefinindoProcessos() {
        if (numeroDeProcessos < 4) {
            return true;
        } else {
            eleicao();
            return false;
        }
    }

    public void eleicao() {
        int idProcessoEleito = getListaProcessos().get(0).getId();

        for (int i = 1; i < this.getListaProcessos().size(); i++) {
            if (idProcessoEleito < getListaProcessos().get(i).getId()) {
                idProcessoEleito = getListaProcessos().get(i).getId();
            }
        }

        if (this.getId() == idProcessoEleito) {
            isServer = true;
            isClient = false;
        } else {
            isServer = false;
            isClient = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Processo> getListaProcessos() {
        return listaProcessos;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        this.isServer = server;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setClient(boolean client) {
        this.isClient = client;
    }

    public Server getServer() {
        if (isServer) {
            if (servidorUDP == null) {
                getListaProcessos().remove(this);
                servidorUDP = new Server(getListaProcessos(),
                        multicastConnection);
            }

            return servidorUDP;
        }
        return null;
    }

    public Client getClient() {
        if (isClient) {
            if (clienteUDP == null) {
                clienteUDP = new Client(getServidorAddress(), this);
            }

            return clienteUDP;
        }
        return null;
    }

    private String getServidorAddress() {
        String address = "";
        for (Processo j : getListaProcessos()) {
            if (j.isServer()) {
                address = j.getServer().getAddress();
                break;
            }
        }
        return address;
    }

    public ArrayList<String> getArquivos() {
        arquivos = new ArrayList<String>();
        File folder = new File("arquivos/processo"+(r.nextInt(4)+ 1));
        File[] listOfFiles = folder.listFiles();
        
            for(int i = 0; i < listOfFiles.length; i++){
                arquivos.add(listOfFiles[i].getName());
            }
            
        return arquivos;
    }

}
