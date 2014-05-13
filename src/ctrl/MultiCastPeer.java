package ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.Client;
import model.Processo;
import model.Server;
import util.Parameter;
import util.Serializer;
//import model.ServerRecebedorChave;
//import util.Parameter;
//import util.Serializer;

public class MultiCastPeer extends Thread {

    private static final long serialVersionUID = 1L;
    private final String HOST = "229.10.10.100";
    private final int PORT = 5050;
    private final int TIMEOUT = 20000;
    private MulticastSocket socket;
    private InetAddress group;
    private Processo processo;
//   private boolean isPrivateKeyReceived = false;
    private int contHelloEmMs = 0;
    private boolean isServerUp = true;
    private ArrayList<Processo> processos;

//    private boolean myTurn = false;
//    private boolean enviaOuRecebeChave = true;

    /**
     * Construtora do Multicast por processo. 
     * @param processo Recebe o processo como parâmetro.
     */
    public MultiCastPeer(Processo processo) {
        try {
            this.processo = processo;
            group = InetAddress.getByName(HOST);
            socket = new MulticastSocket(PORT);
            socket.setSoTimeout(TIMEOUT);
            socket.joinGroup(group);
            this.start();
        } catch (UnknownHostException e) {
            System.out.println("Erro no host: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Erro I/O: " + e.getLocalizedMessage());
        }
    }

    @Override
    /**
     * Método que tem 3 partes:
     * 1 - Inserção dos jogadores numa lista ordenada de cada 1 que após estar cheia, elege o servidor
     * 2 - Caso o jogador seja o servidor, inicializa o server
     * 3 - Caso o jogador seja cliente, inicializa o cliente
     */
    public void run() {
        while (!socket.isClosed()) {
            
            /// Perguntar quem é o tracker
            /// caso não haja resposta presumir que voce é o tracker           
            
        }
    }

    public InetAddress getGroup() {
        return group;
    }

    public MulticastSocket getSocket() {
        return socket;
    }

    public int getPORT() {
        return PORT;
    }
            
    /**
     * Método que inicializa o Cliente do jogo, criando inicialmente uma conexão unicast UDP para receber a chave Publica dele.
     * 
     */
    private synchronized void inicializarClienteUDP() {
        // loop do jogo
        Client c = processo.getClient();

        // verifica se o server estiver UP ainda após Delta T1.
        if ((contHelloEmMs >= Parameter.DELTA_T1_SERVER_MANDAR_HELLO)) {

            // verifica se o server está online
            isServerUp = isServerUP();
            if (!isServerUp) {
                // se não estiver, limpa a lista
                processo.getListaProcessos().clear();
            }
            contHelloEmMs = 0;
        } else {
            if (isServerUp) {
                //inicializa servidor UDP para receber as chaves do server do jogo
                

            }
        }

        try {
            sleep(50);
            contHelloEmMs += 50;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Método que verifica se recebeu a mensagem de hello via multicast
     * @return true caso tenha recebido a mensagem de hello e false caso contrário
     */
    private boolean isServerUP() {
        byte[] buffer = new byte[1024];
        DatagramPacket msgIn = new DatagramPacket(buffer, buffer.length, group, PORT);
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
     * Método que envia as chaves publicas aos clientes e após inicializa o servidor
     */
//    private void inicializarServidorUDP() {
//
//        if (enviaOuRecebeChave) {
//            try {
//                //	    		for(int i=0; i<20; i++)
//                {
//                    ClienteSenderChave csc = new ClienteSenderChave(jogador, jogador.getServer());
//                    sleep(1750);
//                    if (!jogador.getServer().getcPublicas().containsKey(jogador.getListaJogadores().get(0).getId())) {
//                        csc.enviaChavePublica(jogador.getListaJogadores().get(0).getPorta(), 0);
//                        sleep(500);
//                    }
//
//                    if (!jogador.getServer().getcPublicas().containsKey(jogador.getListaJogadores().get(1).getId())) {
//                        csc.enviaChavePublica(jogador.getListaJogadores().get(1).getPorta(), 1);
//                        sleep(500);
//                    }
//
//                    if (!jogador.getServer().getcPublicas().containsKey(jogador.getListaJogadores().get(2).getId())) {
//                        csc.enviaChavePublica(jogador.getListaJogadores().get(2).getPorta(), 2);
//                        sleep(500);
//                    }
//
//                    for (Jogador j : jogador.getListaJogadores()) {
//                        if (!jogador.getServer().getcPublicas().containsKey(j.getId())) {
//                            enviaOuRecebeChave = true;
//                            break;
//                        }
//                    }
//                    enviaOuRecebeChave = false;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        } else {
//
//            // começa o server udp
//            jogador.getServer().startJogo();
//
//        }

//    }

    /**
     * Envia uma mensagem para o grupo multicast
     * 
     * @param String
     *            msg
     */
    public void enviarMensagem(byte[] msg) {
        DatagramPacket msgOut = new DatagramPacket(msg, msg.length, group, PORT);
        try {
            socket.send(msgOut);
        } catch (IOException e) {
            System.out.println("Erro I/O: " + e.getLocalizedMessage());
        }
    }

    /**
     * Método responsável por limpar o buffer de dados
     * 
     * @param byte [] buffer
     */
    private void cleanBuffer(byte[] buffer) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 0;
        }
    }

   
    private void adicionarProcessos() {
        byte[] buffer = new byte[1024];
        DatagramPacket msgIn = new DatagramPacket(buffer, buffer.length, group, PORT);
        try {
            this.enviarMensagem(processo.sendId());
            socket.receive(msgIn);
            Object o = Serializer.deserialize(msgIn.getData());
            if (o instanceof Processo) {
                processo.addProcesso((Processo) o);
            }
            sleep(1250);
            // enviarMensagem("Recebido por " + usuario);
        } catch (IOException e) {
            System.out.println("Erro I/O: " + e.getLocalizedMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro Serializacao: " + ex.getLocalizedMessage());
        } catch (InterruptedException ex) {
            System.out.println("Erro sleep: " + ex.getLocalizedMessage());
        } finally {
            cleanBuffer(buffer);
        }
    }  
}