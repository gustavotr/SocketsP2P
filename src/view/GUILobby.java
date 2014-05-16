package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import listener.BaseListener;
import listener.GUILobbyListener;
import model.Processo;

public class GUILobby extends BasePanel {

    private static final long serialVersionUID = 1L;
    private BaseListener listener;
    private static JLabel buscaRealizada;
    private JList result;
    private Processo processo;
    private int panelWidth;
    private int panelHeight;

    public GUILobby(JFrame mainFrame, Processo processo) {
        super(mainFrame);
        this.processo = processo;
        listener = new GUILobbyListener(this);
        panelWidth = 800;
        panelHeight = 600;
        this.setSize(panelWidth, panelHeight);
        this.setVisible(true);
        this.initComponents();
        mainFrame.add(this);
    }

    public static void setBuscaRealizada(String busca) {
        buscaRealizada.setText(busca);
    }
    
    

    private void initComponents() {
        buscaRealizada = new JLabel("");
        buscaRealizada.setBounds(10, 20, getWidth() - 20, 40);
        buscaRealizada.setHorizontalAlignment(SwingConstants.CENTER);
        buscaRealizada.setFont(new Font(null, Font.BOLD, 18));
        this.add(buscaRealizada);
        
        result = new JList<String>(processo.getArquivosDoProcesso());
        result.setBounds(20, 100, getWidth() - 50, getHeight() - 150);
        this.add(result);
    }
}