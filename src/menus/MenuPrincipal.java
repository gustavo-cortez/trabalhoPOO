package menus;
import enums.EnumMenuPrincipal;
import interfaces.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author Gustavo
 */
public class MenuPrincipal implements MenuInterface{
    private Instancias instancias;
    
    @Override
    public void exibir(UserInterface Interface , Instancias instancias) {
        if(Interface instanceof VisualInterface){
            
            Interface.exibirMensagem("Palhaço Caçarola");
            
        }
        
        int opcao;
        do {
            StringBuilder menu = new StringBuilder("Menu Principal:\n");
            for (EnumMenuPrincipal option : EnumMenuPrincipal.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");
            
            opcao = Interface.solicitarInt(menu.toString());
           

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
                        boolean continua = false;
                        do{
                            try{
                                String dataInicioStr = Interface.solicitarEntrada("Digite a data de início (formato dd/MM/yyyy-HH:mm)");//verifica insercao
                                String dataFimStr = Interface.solicitarEntrada("Digite a data de fim (formato dd/MM/yyyy-HH:mm)");

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
                                LocalDateTime inicioPeriodo = LocalDateTime.parse(dataInicioStr, formatter);
                                LocalDateTime fimPeriodo = LocalDateTime.parse(dataFimStr, formatter);

                                double faturamentoPeriodo = instancias.getTicketsIns().consultarFaturamentoPeriodo(inicioPeriodo, fimPeriodo);
                                Interface.exibirMensagem("Total faturado no período: R$ " + faturamentoPeriodo);
                                continua = true;
                            }
                            catch(DateTimeParseException e){
                                Interface.exibirErro("FORMATO DE ENTRADA INCORRETO");
                            }
                        }while(continua == false);
                        break;
                    case SAIR:
                        Interface.exibirMensagem("Saindo do programa...");
                        instancias.getPersistenciaIns().salvarDados("DadosEstacionamento.json");
                        break;
                    default:
                        Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
                }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != 6);
    }
}
 