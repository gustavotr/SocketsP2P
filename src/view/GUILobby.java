package view;

import ctrl.Processo;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
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
        this.add(result);
    }
}