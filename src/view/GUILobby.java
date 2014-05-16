package view;

import ctrl.Processo;
import ctrl.Tracker;
import java.awt.Font;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import listener.BaseListener;
import listener.GUILobbyListener;

public class GUILobby extends BasePanel {

    private static final long serialVersionUID = 1L;
    private BaseListener listener;
    private JLabel buscaRealizada;
    private JList result;
    private String busca;
    private Processo processo;
    private int panelWidth;
    private int panelHeight;
    private JFrame mainFrame;

    public GUILobby(JFrame mainFrame, Processo processo, String busca) {
        super(mainFrame);
        this.mainFrame = mainFrame;
        this.processo = processo;
        this.busca = busca;
        listener = new GUILobbyListener(this);
        panelWidth = 800;
        panelHeight = 600;
        this.setSize(panelWidth, panelHeight);
        this.setVisible(true);
        this.initComponents();
        this.mainFrame.add(this);
    }
    

    private void initComponents() {
        buscaRealizada = new JLabel(busca);
        buscaRealizada.setBounds(10, 20, getWidth() - 20, 40);
        buscaRealizada.setHorizontalAlignment(SwingConstants.CENTER);
        buscaRealizada.setFont(new Font(null, Font.BOLD, 18));
        this.add(buscaRealizada);
        
        result = new JList<>(processo.getBusca());
        result.setBounds(20, 100, getWidth() - 50, getHeight() - 150);
        result.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    try {
                        String value = result.getSelectedValue().toString();
                        value = value.substring(0, 4+value.lastIndexOf(".") );
                        String str = "Request: arquivo(".concat(value).concat(")");                        
                        byte[] buf = str.getBytes();
                        DatagramSocket socket = new DatagramSocket();
                        DatagramPacket pack = new DatagramPacket(buf, buf.length, processo.getTracker().getAddress(), Tracker.UDPPort);
                        socket.send(pack);
                        buf = new byte[1024];
                        pack = new DatagramPacket(buf, buf.length);
                        socket.receive(pack);
                        //Imprime a localizacao do arquivo requerido
                        System.out.println(new String(pack.getData()));
                    } catch (SocketException ex) {
                        Logger.getLogger(GUILobby.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GUILobby.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        this.add(result);
    }
}