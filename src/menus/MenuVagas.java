package menus;

import enums.EnumVagaStatus;
import enums.EnumTipoVeiculo;
import classes.*;
import enums.EnumMenuVagas;
import interfaces.UserInterface;
import java.util.List;

public class MenuVagas implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcao;
        do {
            /*Imprimir o menu principal dependendo da interface escolhida*/
            List<EnumMenuVagas> opcoesMenuVagas = List.of(EnumMenuVagas.values());
            
            opcao = Interface.exibirMenus("Submenu - Vagas", opcoesMenuVagas);

            switch (opcao) {
                /*Caso 1 - Cadastra uma vaga com o número da vaga, o nome da rua e o tipo de vaga*/
                case 1:
                    int numeroVaga = Interface.solicitarInt("Digite o número da vaga");
                    String ruaVaga = Interface.solicitarEntrada("Digite a rua da vaga");
                    String tipoVeiculoStr = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
                    if(ruaVaga == null){
                        break;
                    }
                    if (tipoVeiculoStr != null) {
                        EnumTipoVeiculo tipoVeiculo;
                        switch (tipoVeiculoStr.toUpperCase()) {
                            case "CARRO":
                                tipoVeiculo = EnumTipoVeiculo.CARRO;
                                break;
                            case "MOTO":
                                tipoVeiculo = EnumTipoVeiculo.MOTO;
                                break;
                            case "ÔNIBUS":
                                tipoVeiculo = EnumTipoVeiculo.ÔNIBUS;
                                break;
                            default:
                                tipoVeiculo = EnumTipoVeiculo.CARRO;
                                break;
                        }
                        instancias.getVagasIns().cadastrarVaga(numeroVaga, ruaVaga, tipoVeiculo);
                    }
                    break;
                /*Caso 2 - Consulta uma vaga através do número da vaga*/
                case 2:
                    int numVaga = Interface.solicitarInt("Informe o número da vaga que deseja ser consultada:");
                    Vagas vagaConsulta = instancias.getVagasIns().buscarVaga(numVaga);
                    if (vagaConsulta != null) {
                        String mensagemCompleta = String.format("Número: " + vagaConsulta.getNumero() + "\nRua: " + vagaConsulta.getRua() + "\nStatus: " + vagaConsulta.getStatus() + "\nTipo: " + vagaConsulta.getTipoVeiculo(), "Consulta de Vaga");
                        Interface.exibirMensagem(mensagemCompleta);
                    } else {
                        Interface.exibirMensagem("Vaga não encontrada.");
                    }
                    break;
                /*Caso 3 - Exclui uma vaga através do número da vaga, 
                apenas se ela não tiver tickets ocupados ou alugados relacionados a ela*/
                case 3:
                    int numeroVagaExcluir = Interface.solicitarInt("Informe o número da vaga a ser excluída:");
                    instancias.getVagasIns().excluirVaga(numeroVagaExcluir);
                    break;
                /*Caso 4 - Edita uma vaga através do número da vaga, podendo mudar a rua e o tipo da vaga*/
                case 4:
                    int numeroVagaEditar = Interface.solicitarInt("Digite o número da vaga que deseja editar");
                    String ruaVagaEditar = Interface.solicitarEntrada("Digite a nova rua da vaga");
                    String tipoVagaEditar = (String) Interface.solicitarEntradaMaior("Selecione o novo tipo da vaga", "Tipo de Vaga",  new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
                    if(ruaVagaEditar == null || tipoVagaEditar == null){
                        break;
                    }
                    instancias.getVagasIns().editarVaga(numeroVagaEditar, ruaVagaEditar, EnumTipoVeiculo.valueOf(tipoVagaEditar));
                    break;
                /*Caso 4 - Edita uma vaga através do número da vaga, podendo o status da vaga para indisponível ou disponível
                nunca podendo alterar se ela estiver ocupada ou alugada mensal*/
                case 5:
                    int numeroVagaDispo = Interface.solicitarInt("Digite o número da vaga que deseja editar o status");
                    String statusVaga = (String) Interface.solicitarEntradaMaior("Selecione o status que a vaga estará", "Status da Vaga", new String[]{"DISPONIVEL", "OCUPADA", "INDISPONIVEL"}, "DISPONIVEL");
                    if(statusVaga == null){
                        break;
                    }
                    if (statusVaga != null) {
                        EnumVagaStatus vagaStatus;
                        switch (statusVaga.toUpperCase()) {
                            case "DISPONIVEL":
                                vagaStatus = EnumVagaStatus.DISPONIVEL;
                                break;
                            case "OCUPADA":
                                vagaStatus = EnumVagaStatus.OCUPADA;
                                break;
                            case "INDISPONIVEL":
                                vagaStatus = EnumVagaStatus.INDISPONIVEL;
                                break;
                            default:
                                vagaStatus = EnumVagaStatus.DISPONIVEL;
                                break;
                        }
                        instancias.getVagasIns().alterarDisponibilidadeVaga(numeroVagaDispo, vagaStatus);
                    }
                    break;
                /*Caso 6 - Volta para o menu principal*/    
                case 6:
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    break;
                default:
                    if(opcao == 0){
                        opcao = 6;
                        break;
                    }
                    Interface.exibirErro("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 6);
    }
}
