 package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import model.Processo;
import util.GUIConstants;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private Processo processo;
    
    public MainFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setResizable(false);
        this.setSize(GUIConstants.FRAME_WIDTH, GUIConstants.FRAME_HEIGHT);
        this.setLocation(dim.width / 2 - this.getWidth() / 2, dim.height / 2 - this.getHeight() / 2);
        this.setVisible(true);        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(GUIConstants.TITLE);
        //this.setLayout(null);
        this.setLookAndFeel();

        JPanel telaInicial = new GUITelaInicial(this);
        this.invalidate();
        this.repaint();
        
        initMulticast();
    }

    private void setLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    private void initMulticast() {
        processo = new Processo();
    }
}
