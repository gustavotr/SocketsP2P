package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

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
    public void redimencionar(int width, int hegiht){
        mainFrame.setSize(width, hegiht);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - width / 2, dim.height / 2 - hegiht / 2);      
    }

    public void refreshView() {
        mainFrame.validate();
        mainFrame.repaint();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }
}
