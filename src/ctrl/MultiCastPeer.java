package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Processo;
//import model.ServerRecebedorChave;
//import util.Parameter;
//import util.Serializer;

public class MultiCastPeer extends Thread {

    private SocketP2P socketP2P;
    private MulticastSocket socket;
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
        this.processo = processo;
        socketP2P = new SocketP2P();
        socket = socketP2P.getSocket();
        this.start();
    }

    @Override
    /**
     * Método que tem 3 partes: 1 - Inserção dos jogadores numa lista ordenada
     * de cada 1 que após estar cheia, elege o servidor 2 - Caso o jogador seja
     * o servidor, inicializa o server 3 - Caso o jogador seja cliente,
     * inicializa o cliente
     */
    public void run() {
        while (!socket.isClosed()) {
            if (!processo.knowTracker()) {
                try {
                    /// Perguntar quem é o tracker
                    enviarMensagem("quem e o tracker?");
                    received = false;
                    while (!received) {
                        //aguarda resposta
                        byte buf[] = new byte[1024];
                        DatagramPacket pack = new DatagramPacket(buf, buf.length);                        
                        System.out.println("Esperando resposta");
                        socket.receive(pack);
                        String localIP = pack.getAddress().getHostAddress();
                        if (!(pack.getAddress().equals(localIP))) {
                            received = true;
                            // Finally, let us do something useful with the data we just received,
                            // like print it on stdout :-)
                            System.out.println("Received data from: " + pack.getAddress().toString()
                                    + ":" + pack.getPort() + " with length: "
                                    + pack.getLength());
                            System.out.write(pack.getData(), 0, pack.getLength());
                            System.out.println(pack.getAddress());
                            System.out.println("Local: "+localIP);
                            // And when we have finished receiving data leave the multicast group and
                            // close the socket
                            socket.leaveGroup(socketP2P.getGroup());
                            socket.close();
                        }
                    }
                    System.out.println("FIM");
                } catch (IOException ex) {
                    Logger.getLogger(MultiCastPeer.class.getName()).log(Level.SEVERE, null, ex);
                }
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
        DatagramPacket msgIn = new DatagramPacket(buffer, buffer.length, socketP2P.getGroup(), socketP2P.getPORT());
        try {
            // this.enviarMensagem(jogador.sendInfo());
            socket.receive(msgIn);            
            String mensagem = new String(msgIn.getData());

            if (mensagem.equals("hello")) {
                return true;
            }
            // sleep(50);
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Envia uma mensagem para o grupo multicast
     *
     * @param msg
     *
     */
    public void enviarMensagem(String msg) {
        byte[] byteMsg = msg.getBytes();
        DatagramPacket msgOut = new DatagramPacket(byteMsg, byteMsg.length, socketP2P.getGroup(), socketP2P.getPORT());
        try {
            socket.send(msgOut);
            socketP2P.setLocalHost(socket.getLocalAddress().getHostAddress());
            System.out.println("LocalHost: "+socketP2P.getLocalHost());
        } catch (IOException e) {
            System.out.println("Erro I/O: " + e.getLocalizedMessage());
        }
    }
}
