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
            List<EnumMenuEstacionamento> opcoesMenuEstacionamento = List.of(EnumMenuEstacionamento.values());
            
            opcao = Interface.exibirMenus("Submenu - Gerenciamento do Estacionamento", opcoesMenuEstacionamento);
            

            switch (opcao) {
                case 1:
                    String placaEstacionar = Interface.solicitarEntrada("Digite a placa do veículo a ser estacionado"); 
                    int numVaga = (Interface.solicitarInt("Digite o número da vaga para estacionar o veículo"));
                    String tipoUso = (String) Interface.solicitarEntradaMaior("Selecione como deseja usar a vaga, por hora ou mensal.", "Tipo de Vaga", new String[]{"HORISTA", "MENSALISTA"}, "HORISTA");
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
                    break;
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
