package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import util.Parameter;
import ctrl.MultiCastPeer;


/**
 * Classe responsável pelo Servidor UDP do jogo da Forca
 *
 */
public class Server {

    private ArrayList<Processo> processos = new ArrayList<Processo>();
    private int cont = 0, contPalavra = -1;
//    private Palavra palavrasController;
    private String palavraDaVez = "";
    private String palavraAdivinhada = "";
    private MultiCastPeer mCast;
    private ArrayList<String> palavraTentada = new ArrayList<String>();
    private ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
    // private ArrayList<PrivateKey> chavesPrivadas = new ArrayList<PrivateKey>();
    // private ArrayList<PublicKey> chavesPublicas = new ArrayList<PublicKey>();

//    private TreeMap<Integer, PublicKey> cPublicas = new TreeMap<Integer, PublicKey>(new IdComparator());
  //  private TreeMap<Integer, PrivateKey> cPrivadas = new TreeMap<Integer, PrivateKey>(new IdComparator());
    
    private HashMap<Integer, Integer> idContNaoJogou = new HashMap<Integer, Integer>();

    private boolean loopGetPrivateKey = true;
    private int contLoopStillAlive = 0;
    private int contJogadorVez = 0;

    /**
     * Construtora que recebe a lista de Jogadores que são os clientes do jogo da Forca e o MultiCast do Servidor
     * @param jogadores 
     * @param mCast
     */
    public Server(List<Processo> processos, MultiCastPeer mCast) {
        this.processos.addAll(processos);
        this.mCast = mCast;
        //criar lista de arquivos
      
        for(int p = 0; p < processos.size(); p++){
            ArrayList<String> arquivosDoProcesso = processos.get(p).getArquivos();
            for(int i = 0; i < arquivosDoProcesso.size(); i++){
                String nomeDoArquivo = arquivosDoProcesso.get(i);
                int index = existFile(nomeDoArquivo);
                if(index < 0){  //caso arquivo ainda não esteja na lista de arquivos ele é adicionado
                    arquivos.add(new Arquivo(nomeDoArquivo,p));
                }else{  //caso contrário o indice do processo que contém o arquivo é adicionado ao Arquivo
                    arquivos.get(index).addProcesso(p);
                }
            }
        }
    }

    

    public void addContLoop() {
        contLoopStillAlive += 88;
    }


    /**
     * Método que envia uma mensagem de Hello caso tenha dado estourado o tempo determinado para enviar a mensagem
     */
    private void mandarMensagemHello() {
        addContLoop();
        String hello = "hello";
        if (contLoopStillAlive >= Parameter.DELTA_T1_SERVER_MANDAR_HELLO) {
            mCast.enviarMensagem(hello.getBytes());
            contLoopStillAlive = 0;
        }

    }

   public String getAddress() {
        return Parameter.HOST_ADDRESS;
    }


    public ArrayList<Processo> getProcessos() {
        return processos;
    }

    //Retorna o indice do arquivo caso ele existe ou retorna -1 caso contrário
    private int existFile(String fileName) {
        for(int i = 0; i < arquivos.size(); i++){
            Arquivo temp = arquivos.get(i);
            if(temp.getNome() == fileName){
                return i;
            }
        }        
        return -1;
    }

}