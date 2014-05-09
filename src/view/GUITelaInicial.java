package view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.BaseListener;
import listener.GUITelaInicialListener;
import util.GUIConstants;

public class GUITelaInicial extends BasePanel {

    private static final long serialVersionUID = 1L;
    private JLabel labelErro;
    private JButton buttonStart;
    private BaseListener listener;

    public GUITelaInicial(JFrame mainFrame) {
        super(mainFrame);
        listener = new GUITelaInicialListener(this);
        this.setSize(GUIConstants.FRAME_WIDTH, GUIConstants.FRAME_HEIGHT);
        this.setVisible(true);
        this.initComponents();
        mainFrame.getContentPane().add(this);
    }

    /**
     * Inicialização dos componentes da tela de início
     */
    private void initComponents() {

        buttonStart = new JButton("Ligar PeerToPeer");
        buttonStart.setBounds(GUIConstants.HORIZONAL_CENTER_POS, 205, GUIConstants.BASE_COMPONENT_WIDTH, GUIConstants.BASE_COMPONENT_HEIGHT);
        buttonStart.addActionListener(listener);
        this.add(buttonStart);

        labelErro = new JLabel();
        labelErro.setBounds(GUIConstants.HORIZONAL_CENTER_POS, 235, GUIConstants.BASE_COMPONENT_WIDTH, GUIConstants.BASE_COMPONENT_HEIGHT);
        labelErro.setFont(new Font(null, Font.PLAIN, 10));
        this.add(labelErro);
    }

    public JButton getButtonJogar() {
        return buttonStart;
    }

    public JLabel getLabelErro() {
        return labelErro;
    }

}
