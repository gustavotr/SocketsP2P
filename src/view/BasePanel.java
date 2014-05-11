package view;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;

import listener.BaseListener;

public class BasePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    protected JFrame mainFrame;
    protected BaseListener listener;

    protected BasePanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null);
    }

    /**
     * Método auxiliar responsável pelos eventos de troca de panel
     * 
     * @param newPanel
     *            referencia do panel que será inserido
     */
    public void changePanel(Component newPanel) {
        mainFrame.remove(this);
        mainFrame.add(newPanel);
        this.refreshView();
    }

    public void refreshView() {
        mainFrame.validate();
        mainFrame.repaint();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
}
