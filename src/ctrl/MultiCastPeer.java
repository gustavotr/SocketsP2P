package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Processo;
//import model.ServerRecebedorChave;
//import util.Parameter;
//import util.Serializer;

public class MultiCastPeer extends Thread {

    private MulticastSocketP2P socketP2P;
    private int PORT = 5050;
    private Processo processo;
    private boolean received;
//   private boolean isPrivateKeyReceived = false;
    private int contHelloEmMs = 0;
    private boolean isServerUp = true;
    private ArrayList<Processo> processos;

//    private boolean myTurn = false;
//    private boolean enviaOuRecebeChave = true;
    /**
     * Construtora do Multicast por processo.
     *
     * @param processo Recebe o processo como parâmetro.
     */
    public MultiCastPeer(Processo processo) {
        try {
            this.processo = processo;
            socketP2P = new MulticastSocketP2P(PORT);
            this.start();
            new Tracker(socketP2P);
        } catch (IOException ex) {
            Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    /**
     * Método que tem 3 partes: 1 - Inserção dos jogadores numa lista ordenada
     * de cada 1 que após estar cheia, elege o servidor 2 - Caso o jogador seja
     * o servidor, inicializa o server 3 - Caso o jogador seja cliente,
     * inicializa o cliente
     */
    public void run() {        
        while (!socketP2P.isClosed()) {
            if (!processo.knowTracker()) {
                try {
                    /// Perguntar quem é o tracker
                    socketP2P.enviarMensagem("quem e o tracker?");
                    received = false;
                    while (!received) {
                        //aguarda resposta
                        byte buf[] = new byte[1024];
                        DatagramPacket pack = new DatagramPacket(buf, buf.length);                         
                        System.out.println("Esperando resposta");                        
                        socketP2P.setSoTimeout(2000);
                        socketP2P.receive(pack);
                        String resposta = new String(pack.getData());
                        System.out.println("PEER RECEBEU -----> " + resposta);
                        if (resposta.equals("eu sou o tracker")){
                            received = true;
                            // Finally, let us do something useful with the data we just received,
                            // like print it on stdout :-)
                            System.out.println("Peer recebeu de: " + pack.getAddress().toString()
                                    + ":" + pack.getPort() + " with length: "
                                    + pack.getLength());
                            System.out.println("Mensagem: ");
                            System.out.write(pack.getData(), 0, pack.getLength());
                            // And when we have finished receiving data leave the multicast group and
                            // close the socket
                            socketP2P.leaveGroup(socketP2P.getGroup());
                            socketP2P.close();
                        }
                    }
                    System.out.println("FIM");
                } catch (SocketTimeoutException e) {
                    // timeout exception.
                    System.out.println("Nenhum tracker respondeu");
                    System.out.println("Eu serei o tracker");
                    //processo.setTheTracker(processo.getId());                                        
                    //socketP2P.close();
                }catch (IOException ex) {
                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{  //Sabe quem é o Tracker
                
            }
        }
    }

    /**
     * Método que verifica se recebeu a mensagem de hello via multicast
     *
     * @return true caso tenha recebido a mensagem de hello e false caso
     * contrário
     */
    private boolean isServerUP() {
        byte[] buffer = new byte[1024];
        DatagramPacket msgIn = new DatagramPacket(buffer, buffer.length, socketP2P.getGroup(), socketP2P.getPort());
        try {
            socketP2P.receive(msgIn);            
            String mensagem = new String(msgIn.getData());

            if (mensagem.equals("hello")) {
                return true;
            }
            // sleep(50);
        } catch (Exception e) {

        }
        return false;
    }    
}
