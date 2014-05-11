package listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import model.Processo;

import view.GUILobby;
import view.GUITelaInicial;

public class GUITelaInicialListener extends BaseListener {

    private GUITelaInicial panel;

    public GUITelaInicialListener(GUITelaInicial panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component c = (Component) e.getSource();
        if (c.equals(panel.getButtonStart())) {            
            Processo p = new Processo();
            panel.changePanel(new GUILobby(panel.getMainFrame(), p));            
        }
    }
}
