package menus;

import enums.EnumVagaStatus;
import enums.EnumTipoVeiculo;
import classes.*;
import funcoesVisual.*;
import interfaces.UserInterface;
import javax.swing.JOptionPane;

public class MenuVagas implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcaoVagas;
        do {
            String[] opcoesSubMenuVagas = {
                "Cadastrar vaga",
                "Consultar vaga por número",
                "Excluir vaga",
                "Editar vaga",
                "Alterar disponibilidade",
                "Voltar"
            };
            opcaoVagas = JOptionPane.showOptionDialog(null, "Submenu - Gerenciar vagas:", "Gerenciar vagas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesSubMenuVagas, opcoesSubMenuVagas[0]);

            switch (opcaoVagas) {
                case 0:
                    int numeroVaga = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da vaga"));
                    String ruaVaga = JOptionPane.showInputDialog("Digite a rua da vaga");
                    String tipoVeiculoStr = (String) JOptionPane.showInputDialog(null, "Selecione o tipo da vaga.", "Tipo de Vaga", JOptionPane.QUESTION_MESSAGE, null, new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");

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
                    int numVaga = Integer.parseInt(JOptionPane.showInputDialog("Informe o número da vaga que deseja ser consultada:"));
                    Vagas vagaConsulta = instancias.getVagasIns().buscarVaga(numVaga);
                    if (vagaConsulta != null) {
                        JOptionPane.showMessageDialog(null, "Número: " + vagaConsulta.getNumero() + "\nRua: " + vagaConsulta.getRua() + "\nStatus: " + vagaConsulta.getStatus() + "\nTipo: " + vagaConsulta.getTipoVeiculo(), "Consulta de Vaga", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vaga não encontrada.", "Consulta de Vaga", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    int numeroVagaExcluir = Integer.parseInt(JOptionPane.showInputDialog("Informe o número da vaga a ser excluída:"));
                    instancias.getVagasIns().excluirVaga(numeroVagaExcluir, instancias.getTicketsIns());
                    break;
                case 3:
                    int numeroVagaEditar = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da vaga que deseja editar"));
                    String ruaVagaEditar = JOptionPane.showInputDialog("Digite a nova rua da vaga");
                    String tipoVagaEditar = (String) JOptionPane.showInputDialog(null, "Selecione o novo tipo da vaga", "Tipo de Vaga", JOptionPane.QUESTION_MESSAGE, null, new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");

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
                    int numeroVagaDispo = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da vaga que deseja editar o status", JOptionPane.OK_OPTION));
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
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                    instancias.exibirMenuPrincipal();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcaoVagas != 5);
    }
}
