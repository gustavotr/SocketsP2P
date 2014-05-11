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
    private JButton buttonBuscar;
    private BaseListener listener;
    private JTextField busca;

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

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(GUIConstants.HORIZONAL_CENTER_POS, 205, GUIConstants.BASE_COMPONENT_WIDTH, GUIConstants.BASE_COMPONENT_HEIGHT);
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

}
