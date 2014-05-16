package view;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import listener.BaseListener;
import listener.GUITelaInicialListener;
import ctrl.Processo;
import util.GUIConstants;

public class GUITelaInicial extends BasePanel implements Runnable{

    private static final long serialVersionUID = 1L;
    private JLabel labelErro;
    private JButton buttonBuscar;
    private BaseListener listener;
    private JLabel label;
    private JTextField busca;
    private Processo processo;
    private JFrame mainJFrame;

    public GUITelaInicial(JFrame mainFrame) {
        super(mainFrame);
        this.mainFrame = mainFrame;
        processo = new Processo();
        listener = new GUITelaInicialListener(this, processo);
        this.setSize(GUIConstants.FRAME_WIDTH, GUIConstants.FRAME_HEIGHT);
        this.setVisible(true);
        this.inicializando();        
        this.mainFrame.getContentPane().add(this); 
        new Thread(this).start();
    }
    
    private void inicializando(){
        
        label = new JLabel("Aguardando a conexao dos peers");
        label.setBounds(GUIConstants.HORIZONAL_CENTER_POS, GUIConstants.VERTICAL_CENTER_POS, GUIConstants.BASE_COMPONENT_WIDTH, GUIConstants.BASE_COMPONENT_HEIGHT);
        this.add(label);
    }

    /**
     * Inicialização dos componentes da tela de início
     */
    public void initComponents() {
        
        label.setText("Peers conectados");
                
        busca = new JTextField();
        busca.setBounds(GUIConstants.HORIZONAL_CENTER_POS - GUIConstants.BASE_COMPONENT_WIDTH/2, GUIConstants.VERTICAL_CENTER_POS/2, GUIConstants.BASE_COMPONENT_WIDTH, GUIConstants.BASE_COMPONENT_HEIGHT);
        this.add(busca);
        
        buttonBuscar = new JButton("Buscar");        
        buttonBuscar.setBounds(GUIConstants.HORIZONAL_CENTER_POS + GUIConstants.BASE_COMPONENT_WIDTH/2, GUIConstants.VERTICAL_CENTER_POS/2, 100, GUIConstants.BASE_COMPONENT_HEIGHT);
        buttonBuscar.addActionListener(listener);
        this.add(buttonBuscar);

        labelErro = new JLabel();
        labelErro.setBounds(GUIConstants.HORIZONAL_CENTER_POS, 235, GUIConstants.BASE_COMPONENT_WIDTH, GUIConstants.BASE_COMPONENT_HEIGHT);
        labelErro.setFont(new Font(null, Font.PLAIN, 10));
        this.add(labelErro);        
    }

    public JButton getButtonStart() {
        return buttonBuscar;
    }

    public JLabel getLabelErro() {
        return labelErro;
    }
    
    public String getBusca(){
        return busca.getText();
    }

    @Override
    public void run() {
        while(!processo.knowTracker()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUITelaInicial.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
                
        initComponents();
        mainFrame.getContentPane().add(this);
        mainFrame.repaint();
    }

}
