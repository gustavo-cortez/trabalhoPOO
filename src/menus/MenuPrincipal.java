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
        int opcao;
        do {
            /*Imprimir o menu principal dependendo da interface escolhida*/
            List<EnumMenuPrincipal> opcoesMenuPrincipal = List.of(EnumMenuPrincipal.values());
            
            opcao = Interface.exibirMenus("Menu principal", opcoesMenuPrincipal);
           

            try{

                switch (opcao) {
                    /*Caso 1 - Exibir submenu Clientes*/
                    case 1:
                        instancias.getMenuClientes().exibir(Interface, instancias);
                        break;
                    /*Caso 2 - Exibir submenu Vagas*/
                    case 2:
                        instancias.getMenuVagas().exibir(Interface, instancias);
                        break;
                    /*Caso 3 - Exibir submenu Estacionamento, mas apenas se já tiver Clientes, vagas e tarifas cadastradas no sistema*/
                    case 3:
                        if(!instancias.getClienteIns().clientes.isEmpty() && !instancias.getVagasIns().vagas.isEmpty() && !instancias.getTarifasIns().tarifasVazias()){
                            instancias.getMenuEstacionamento().exibir(Interface, instancias);  
                        }
                        else{
                            Interface.exibirErro("Há cadastros vazios em clientes, vagas ou tarifas!");
                        }
                        break;
                    /*Caso 4 - Exibir submenu Funções gerais, onde está a listagem de tickets e o gerenciamento de tarifas*/
                    case 4:
                        instancias.getMenuFuncoesGerais().exibir(Interface, instancias);
                        break;
                    /*Caso 5 - Parte resposável para o consulta do faturamento por período, 
                    sendo informada o inicio e o fim com data e hora*/
                    case 5:
                        boolean continua = false;
                        do{
                            try{
                                String dataInicioStr = Interface.solicitarEntrada("Digite a data de início (formato dd/MM/yyyy-HH:mm)");//verifica insercao
                                String dataFimStr = Interface.solicitarEntrada("Digite a data de fim (formato dd/MM/yyyy-HH:mm)");
                                if(dataFimStr == null || dataInicioStr == null){
                                    break;
                                }
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
                                LocalDateTime inicioPeriodo = LocalDateTime.parse(dataInicioStr, formatter);
                                LocalDateTime fimPeriodo = LocalDateTime.parse(dataFimStr, formatter);

                                double faturamentoPeriodo = instancias.getFaturamentoIns().consultarFaturamentoPeriodo(inicioPeriodo, fimPeriodo);
                                Interface.exibirMensagem("Total faturado no período: R$ " + faturamentoPeriodo);
                                continua = true;
                            }
                            catch(DateTimeParseException e){
                                Interface.exibirErro("FORMATO DE ENTRADA INCORRETO");
                            }
                        }while(continua == false);
                        break;
                    /*Caso 6 - Parte resposável para o consulta do faturamento por veículo, sendo informado 
                    a placa do veículo desejado para a consulta*/ 
                    case 6:
                        if(!instancias.getClienteIns().clientes.isEmpty() && !instancias.getVagasIns().vagas.isEmpty() && !instancias.getTarifasIns().tarifasVazias()){
                           String veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo que consultar o faturamento:");
                           double faturamentoVeiculo = instancias.getFaturamentoIns().consultarFaturamentoVeiculo(veiculoPlaca);
                           Interface.exibirMensagem("Total faturado pelo o uso do veículo: R$ " + faturamentoVeiculo);
                        }
                        else{
                            Interface.exibirErro("Há cadastros vazios em clientes, vagas ou tarifas!"); 
                        }
                        break;
                    /*Caso 7 - Sair do programa e salvar os dados no DadosEstacionamento.json, 
                    optei pelo JSON por tem uma usabilidade simples e leve mesmo que ele não seja legível para conferir na mão o arquivo
                    até pensei em usar CSV mas não achei muito mais vantajoso do que JSON*/
                    case 7:
                        Interface.exibirMensagem("Saindo do programa...");
                        instancias.getPersistenciaIns().salvarDados("DadosEstacionamento.json");
                        break;
                    default:
                        if(opcao == 0){
                            opcao = 7;
                            break;
                        }
                        Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
                }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != 7);
    }
}
 