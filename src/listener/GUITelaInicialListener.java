package listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import ctrl.Processo;

import view.GUILobby;
import view.GUITelaInicial;

public class GUITelaInicialListener extends BaseListener {

    private GUITelaInicial panel;
    private Processo processo;

    public GUITelaInicialListener(GUITelaInicial panel, Processo processo) {
        this.panel = panel;
        this.processo = processo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component c = (Component) e.getSource();
        if (c.equals(panel.getButtonStart())) {
            String busca = panel.getBusca();
            processo.buscarAquivo(busca);
            panel.changePanel(new GUILobby(panel.getMainFrame(), processo, busca));
            panel.redimencionar(800, 600);
        }
    }
}
