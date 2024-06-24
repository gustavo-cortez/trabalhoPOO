package menus;

import enums.EnumUsoEstacionamento;
import classes.*;
import enums.EnumMenuEstacionamento;
import interfaces.*;
import java.util.List;

public class MenuEstacionamento implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        
        instancias.getTicketsIns().verificarTicketsMensalistas();
        int opcao;
        do {
            /*Imprimir o menu dependendo da interface escolhida*/
            List<EnumMenuEstacionamento> opcoesMenuEstacionamento = List.of(EnumMenuEstacionamento.values());
            
            opcao = Interface.exibirMenus("Submenu - Gerenciamento do Estacionamento", opcoesMenuEstacionamento);
            

            switch (opcao) {
                /*Caso 1 - Estacionar o veiculo com base na placa e vaga desejada*/
                case 1:
                    String placaEstacionar = Interface.solicitarEntrada("Digite a placa do veículo a ser estacionado"); 
                    int numVaga = (Interface.solicitarInt("Digite o número da vaga para estacionar o veículo"));
                    String tipoUso = (String) Interface.solicitarEntradaMaior("Selecione como deseja usar a vaga, por hora ou mensal.", "Tipo de Vaga", new String[]{"HORISTA", "MENSALISTA"}, "HORISTA");
                    if(placaEstacionar == null || tipoUso == null){
                        break;
                    }
                    if(instancias.getVagasIns().buscarTicketMensalistaValido(instancias.getClienteIns().consultarPlaca(placaEstacionar)) != null && EnumUsoEstacionamento.HORISTA.equals(tipoUso)){
                        Interface.exibirErro("Veículo já ligado há um Ticket Mensalista válido");
                    }
                    else{
                        instancias.getClienteIns().consultarPlaca(placaEstacionar).setUsoEstacionamento(EnumUsoEstacionamento.valueOf(tipoUso));
                        if(instancias.getClienteIns().consultarPlaca(placaEstacionar) != null){
                            if(instancias.getClienteIns().consultarPlaca(placaEstacionar).getTipoUso().equals(EnumUsoEstacionamento.HORISTA)){
                                instancias.getVagasIns().estacionarVeiculo(instancias.getClienteIns().consultarPlaca(placaEstacionar), numVaga);
                            }
                            else{
                                instancias.getVagasIns().estacionarVeiculoMensalista(instancias.getClienteIns().consultarPlaca(placaEstacionar), numVaga);
                            }
                        }
                        else{
                            Interface.exibirErro("Veículo não encontrado ou cliente inexistente.");//mensagem de erro
                        }
                    }
                    break;
                /*Caso 2 - Retirar o veículo conforme a vaga informada*/
                case 2:
                    numVaga = Interface.solicitarInt("Digite o número da vaga para retirar o veículo");
                    if(instancias.getTicketsIns().buscarTicketPorVaga(numVaga) != null){
                        
                        if(instancias.getTicketsIns().buscarTicketPorVaga(numVaga) instanceof TicketHorista){
                            instancias.getVagasIns().retirarVeiculo(numVaga);
                        }
                        else{
                            instancias.getVagasIns().retirarVeiculoMensalista(numVaga); 
                        }
                    }
                    else{
                        Interface.exibirErro("Vaga não ocupada ou inexistente.");
                    }
                    
                    break;
                /*Caso 3 - Listar vagas disponiveis para uso*/
                case 3:
                    List<Vagas> vagasDisponiveis = instancias.getVagasIns().listarVagasDisponiveis();
                    if (!vagasDisponiveis.isEmpty()) {
                        StringBuilder mensagem = new StringBuilder("Vagas disponíveis:");
                        for (Vagas vaga : vagasDisponiveis) {
                            mensagem.append("\nNúmero: ").append(vaga.getNumero()).append(", Rua: ").append(vaga.getRua()).append(", Tipo: ").append(vaga.getTipoVeiculo());
                        }
                        Interface.exibirMensagem(mensagem.toString());
                    } else {
                        Interface.exibirMensagem("Sem vagas disponíveis!");
                    }
                    break;
                /*Caso 4 - Listar vagas alugadas para o uso mensal de certos veículos*/
                case 4:
                    List<Vagas> vagasAlugadas = instancias.getVagasIns().listarVagasAlugadas();
                    if (!vagasAlugadas.isEmpty()) {
                        StringBuilder mensagem = new StringBuilder("Vagas alugadas:");
                        for (Vagas vaga : vagasAlugadas) {
                            mensagem.append("\nNúmero: ").append(vaga.getNumero()).append(", Rua: ").append(vaga.getRua()).append(", Tipo: ").append(vaga.getTipoVeiculo());
                        }
                        Interface.exibirMensagem(mensagem.toString());
                    } else {
                        Interface.exibirMensagem("Sem vagas alugadas!");
                    }
                    break;
                /*Caso 5 - Voltar para o menu principal*/
                case 5:
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    break;
                default:
                    if(opcao == 0){
                        opcao = 5;
                        break;
                    }
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 5);
    }
}
