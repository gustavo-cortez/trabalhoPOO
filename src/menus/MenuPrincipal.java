package menus;
import enums.EnumMenuPrincipal;
import interfaces.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo
 */
public class MenuPrincipal implements MenuInterface{
    private Instancias instancias;
    
    @Override
    public void exibir(UserInterface Interface , Instancias instancias) {
        if(Interface instanceof VisualInterface){
            
            System.out.println("Palhaço Caçarola");
            
        }
        
        int opcao;
        do {
            StringBuilder menu = new StringBuilder("Menu Principal:\n");
            for (EnumMenuPrincipal option : EnumMenuPrincipal.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            String opcaoStr = Interface.solicitarEntrada(menu.toString());
            opcao = Integer.parseInt(opcaoStr);

            try{
                EnumMenuPrincipal opcaoEscolhida = EnumMenuPrincipal.porNumero(opcao);

                switch (opcaoEscolhida) {
                    case CLIENTES:
                        instancias.getMenuClientes().exibir(Interface, instancias);
                        break;
                    case VAGAS:
                        instancias.getMenuVagas().exibir(Interface, instancias);
                        break;
                    case ESTACIONAMENTO:
                        instancias.getMenuEstacionamento().exibir(Interface, instancias);
                        break;
                    case FUNCOES_GERAIS:
                        instancias.getMenuFuncoesGerais().exibir(Interface, instancias);
                        break;
                    case CONSULTAR_FATURAMENTO:
                        String dataInicioStr = JOptionPane.showInputDialog("Digite a data de início (formato dd/MM/yyyy-HH:mm)");
                        String dataFimStr = JOptionPane.showInputDialog("Digite a data de fim (formato dd/MM/yyyy-HH:mm)");

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
                        LocalDateTime inicioPeriodo = LocalDateTime.parse(dataInicioStr, formatter);
                        LocalDateTime fimPeriodo = LocalDateTime.parse(dataFimStr, formatter);

                        double faturamentoPeriodo = instancias.getTicketsIns().consultarFaturamentoPeriodo(inicioPeriodo, fimPeriodo);
                        JOptionPane.showMessageDialog(null, "Total faturado no período: R$ " + faturamentoPeriodo);
                        break;
                    case SAIR:
                        JOptionPane.showMessageDialog(null, "Saindo do programa...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
                }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != 6);
    }
}
