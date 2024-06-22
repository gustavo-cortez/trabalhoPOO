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
    
    @Override
    public int solicitarInt(String mensagem){
        int numero = 0;
        boolean verificacao = false;
        while(verificacao == false){
            try{
                String auxiliar = JOptionPane.showInputDialog(mensagem);
                numero = Integer.parseInt(auxiliar);
                verificacao = true;
            }
            catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
        }
        return numero;
    }
    
    @Override
    public void exibirErro (String erro){
        
        // Exibe a mensagem de erro em um JOptionPane
        JOptionPane.showMessageDialog(null, erro, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void exibirSucesso (String sucesso){
        
        // Exibe mensagem de sucesso em um JOptionPane
        JOptionPane.showMessageDialog(null, sucesso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public double solicitarDouble (String mensagem){
        double numero = 0;
        boolean verificacao = false;
        while(verificacao == false){
            try{
                String auxiliar = JOptionPane.showInputDialog(mensagem);
                numero = Double.parseDouble(auxiliar);
                verificacao = true;
            }
            catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
        }
        return numero;
    }
    
    @Override
    public String solicitarEntradaMaior(String mensagem, String titulo, String[] opcoes, String opcaoPadrao) {
        return (String) JOptionPane.showInputDialog(null, mensagem, titulo, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcaoPadrao);
    }
}
