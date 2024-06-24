import javax.swing.JOptionPane;
import interfaces.VisualInterface;
import interfaces.TerminalInterface;
/**
 * Trabalho POO Estacionamento
 * @author Gustavo e Gabriel
 */
public class Estacionamento {

    /* Método principal para executar o programa */
    public static void main(String[] args) {

        String[] opcoes = {"Terminal de comandos", "Janelas gráficas"};
        int opcao = JOptionPane.showOptionDialog(
            null,
            "Como executar o programa?",
            "Escolha de Interface",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opcoes,
            opcoes[0]
        );

        switch(opcao){
            case 0:
                TerminalInterface terminalIns = new TerminalInterface();
                terminalIns.exibirMenuPrincipal();
                break;
            case 1:
                VisualInterface visualIns = new VisualInterface();
                visualIns.exibirMenuPrincipal();
                break;
            default:
                break;
        }
        
    }   
}
