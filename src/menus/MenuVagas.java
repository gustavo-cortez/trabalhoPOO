package menus;

import enums.EnumVagaStatus;
import enums.EnumTipoVeiculo;
import classes.*;
import enums.EnumMenuVagas;
import funcoesVisual.*;
import interfaces.UserInterface;
import javax.swing.JOptionPane;

public class MenuVagas implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcao;
        do {
            StringBuilder menu = new StringBuilder("Menu Principal:\n");
            for (EnumMenuVagas option : EnumMenuVagas.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            String opcaoStr = Interface.solicitarEntrada(menu.toString());
            opcao = Integer.parseInt(opcaoStr);

            switch (opcao) {
                case 0:
                    int numeroVaga = Interface.solicitarInt("Digite o número da vaga");
                    String ruaVaga = Interface.solicitarEntrada("Digite a rua da vaga");
                    String tipoVeiculoStr = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");

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
                case 1:
                    int numVaga = Interface.solicitarInt("Informe o número da vaga que deseja ser consultada:");
                    Vagas vagaConsulta = instancias.getVagasIns().buscarVaga(numVaga);
                    if (vagaConsulta != null) {
                        Interface.exibirMensagemVaga("Número: " + vagaConsulta.getNumero() + "\nRua: " + vagaConsulta.getRua() + "\nStatus: " + vagaConsulta.getStatus() + "\nTipo: " + vagaConsulta.getTipoVeiculo(), "Consulta de Vaga");
                    } else {
                        Interface.exibirMensagem("Vaga não encontrada.", "Consulta de Vaga");
                    }
                    break;
                case 2:
                    int numeroVagaExcluir = Interface.solicitarInt("Informe o número da vaga a ser excluída:");
                    instancias.getVagasIns().excluirVaga(numeroVagaExcluir, instancias.getTicketsIns());
                    break;
                case 3:
                    int numeroVagaEditar = Interface.solicitarInt("Digite o número da vaga que deseja editar");
                    String ruaVagaEditar = Interface.solicitarEntrada("Digite a nova rua da vaga");
                    String tipoVagaEditar = (String) Interface.solicitarEntradaMaior("Selecione o novo tipo da vaga", "Tipo de Vaga",  new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");

                    if (tipoVagaEditar != null) {
                        EnumTipoVeiculo tipoVeiculoEditar;
                        switch (tipoVagaEditar.toUpperCase()) {
                            case "CARRO":
                                tipoVeiculoEditar = EnumTipoVeiculo.CARRO;
                                break;
                            case "MOTO":
                                tipoVeiculoEditar = EnumTipoVeiculo.MOTO;
                                break;
                            case "CAMINHAO":
                                tipoVeiculoEditar = EnumTipoVeiculo.ÔNIBUS;
                                break;
                            default:
                                tipoVeiculoEditar = EnumTipoVeiculo.CARRO;
                                break;
                        }
                        instancias.getVagasIns().editarVaga(numeroVagaEditar, ruaVagaEditar, tipoVeiculoEditar);
                    }
                    break;
                case 4:
                    int numeroVagaDispo = Interface.solicitarInt("Digite o número da vaga que deseja editar o status");
                    String statusVaga = (String) JOptionPane.showInputDialog(null, "Selecione o status que a vaga estará", "Status da Vaga", JOptionPane.QUESTION_MESSAGE, null, new String[]{"DISPONIVEL", "OCUPADA", "INDISPONIVEL"}, "DISPONIVEL");

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
                case 5:
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    instancias.exibirMenuPrincipal();
                    break;
                default:
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 5);
    }
}
