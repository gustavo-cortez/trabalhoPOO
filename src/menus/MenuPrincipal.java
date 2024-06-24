package menus;
import enums.EnumMenuPrincipal;
import interfaces.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

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
            List<EnumMenuPrincipal> opcoesMenuPrincipal = List.of(EnumMenuPrincipal.values());
            
            opcao = Interface.exibirMenus("Menu principal", opcoesMenuPrincipal);
           

            try{

                switch (opcao) {
                    case 1:
                        instancias.getMenuClientes().exibir(Interface, instancias);
                        break;
                    case 2:
                        instancias.getMenuVagas().exibir(Interface, instancias);
                        break;
                    case 3:
                        if(!instancias.getClienteIns().clientes.isEmpty() && !instancias.getVagasIns().vagas.isEmpty()){
                            instancias.getMenuEstacionamento().exibir(Interface, instancias);  
                        }
                        else{
                            Interface.exibirErro("Há cadastros vazios em clientes, vagas ou tarifas!");
                        }
                        break;
                    case 4:
                        instancias.getMenuFuncoesGerais().exibir(Interface, instancias);
                        break;
                    case 5:
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
                    case 6:
                        Interface.exibirMensagem("Saindo do programa...");
                        instancias.getPersistenciaIns().salvarDados("DadosEstacionamento.json");
                        break;
                    default:
                        if(opcao == 0){
                            opcao = 6;
                            break;
                        }
                        Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
                }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != 6);
    }
}
 