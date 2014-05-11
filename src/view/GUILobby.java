package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import listener.BaseListener;
import listener.GUILobbyListener;
import model.Processo;
import util.GUIConstants;

public class GUILobby extends BasePanel {

    private static final long serialVersionUID = 1L;
    private BaseListener listener;
    private JLabel labelPalavra;
    private JLabel labelProcessoAtual;
    private JLabel labelTituloLetrasChutadas;
    private JLabel labelLetrasChutadas;
    private JTextField textFieldPalavra;
    private JTextField textFieldMsgChat;
    private JButton buttonChutar;
    private JButton buttonEnviar;
    private JTextArea textAreaChat;
    private Processo processo;

    public GUILobby(JFrame mainFrame, Processo processo) {
        super(mainFrame);
        this.processo = processo;
        listener = new GUILobbyListener(this);
        this.setSize(GUIConstants.FRAME_WIDTH, GUIConstants.FRAME_HEIGHT);
        this.setVisible(true);
        this.initComponents();
        mainFrame.add(this);
    }

    private void initComponents() {
        labelPalavra = new JLabel("T E S T E");
        labelPalavra.setBounds(10, 20, GUIConstants.FRAME_WIDTH - 20, 40);
        labelPalavra.setHorizontalAlignment(SwingConstants.CENTER);
        labelPalavra.setFont(new Font(null, Font.BOLD, 18));
        this.add(labelPalavra);

        labelProcessoAtual = new JLabel("Processo: "+processo.getId());
        labelProcessoAtual.setBounds(10, 70, GUIConstants.FRAME_WIDTH / 2, GUIConstants.BASE_COMPONENT_HEIGHT);
        labelProcessoAtual.setFont(new Font(null, Font.PLAIN, 10));
        this.add(labelProcessoAtual);

        labelTituloLetrasChutadas = new JLabel("Letras já chutadas: ");
        labelTituloLetrasChutadas.setBounds(10, 100, GUIConstants.FRAME_WIDTH / 2, GUIConstants.BASE_COMPONENT_HEIGHT);
        this.add(labelTituloLetrasChutadas);

        labelLetrasChutadas = new JLabel("Letras chutadas e formatadas aqui");
        labelLetrasChutadas.setFont(new Font(null, Font.PLAIN, 12));
        labelLetrasChutadas.setBounds(10, 120, GUIConstants.FRAME_WIDTH / 2, GUIConstants.BASE_COMPONENT_HEIGHT);
        this.add(labelLetrasChutadas);

        textFieldPalavra = new JTextField();
        textFieldPalavra.setBounds(10, GUIConstants.FRAME_HEIGHT - 80, 150, GUIConstants.BASE_COMPONENT_HEIGHT);
        this.add(textFieldPalavra);

        buttonChutar = new JButton("Chutar Letra/Palavra");
        buttonChutar.setFont(new Font(null, Font.PLAIN, 10));
        buttonChutar.setBounds(10, GUIConstants.FRAME_HEIGHT - 60, 150, GUIConstants.BASE_COMPONENT_HEIGHT);
        buttonChutar.addActionListener(listener);
        this.add(buttonChutar);

    }

    public JLabel getLabelPalavra() {
        return labelPalavra;
    }

    public JLabel getLabelProcessoAtual() {
        return labelProcessoAtual;
    }

    public JLabel getLabelTituloLetrasChutadas() {
        return labelTituloLetrasChutadas;
    }

    public JLabel getLabelLetrasChutadas() {
        return labelLetrasChutadas;
    }

    public JTextField getTextFieldPalavra() {
        return textFieldPalavra;
    }

    public JTextField getTextFieldMsgChat() {
        return textFieldMsgChat;
    }

    public JButton getButtonChutar() {
        return buttonChutar;
    }

    public JButton getButtonEnviar() {
        return buttonEnviar;
    }

    public JTextArea getTextAreaChat() {
        return textAreaChat;
    }

    public Processo getProcesso() {
        return processo;
    }

}