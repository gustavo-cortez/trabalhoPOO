package interfaces;

import javax.swing.JOptionPane;
import menus.*;

/**
 * 
 * @author Gustavo
 */
public class VisualInterface implements UserInterface {
    private final Instancias instancia = new Instancias(this);
    
    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    @Override
    public String solicitarEntrada(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }
    
    @Override
    public void exibirMenuPrincipal() {
        instancia.exibirMenuPrincipal();
    }
}
