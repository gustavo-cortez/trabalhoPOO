package menus;

import enums.EnumTipoVeiculo;
import enums.EnumUsoEstacionamento;
import interfaces.*;
import javax.swing.JOptionPane;
import enums.EnumMenuVeiculos;

public class MenuVeiculos implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcao;
        StringBuilder menu = new StringBuilder("Menu Principal:\n");
            for (EnumMenuVeiculos option : EnumMenuVeiculos.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            String opcaoStr = Interface.solicitarEntrada(menu.toString());
            opcao = Integer.parseInt(opcaoStr);

        do {
            opcao = JOptionPane.showOptionDialog(null, "Submenu - Gerenciar veículos:", "Gerenciar veículos",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesSubMenuVeiculos, opcoesSubMenuVeiculos[0]);

            switch (opcao) {
                case 0:
                    // Cadastrar veículo
                    String documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    String veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo:");
                    String modeloVeiculo = Interface.solicitarEntrada("Informe o modelo do veículo:");
                    String corVeiculo = Interface.solicitarEntrada("Informe a cor do veículo:");
                    String tipoUso = (String) JOptionPane.showInputDialog(null, "Selecione o tipo da vaga.", "Tipo de Vaga", JOptionPane.QUESTION_MESSAGE, null, new String[]{"HORISTA", "MENSALISTA"}, "HORISTA");
                    String tipoVeiculoStr = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
                    if (tipoVeiculoStr != null) {
                        switch (tipoVeiculoStr.toUpperCase()) {
                            case "CARRO":
                                instancias.getClienteIns().adicionarVeiculoCliente(veiculoPlaca, EnumTipoVeiculo.valueOf(tipoVeiculoStr), documentoCli, modeloVeiculo, corVeiculo, EnumUsoEstacionamento.valueOf(tipoUso));
                                break;
                        }
                    }
                    
                    break;

                case 1:
                    // Consultar veículo por documento
                    documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    instancias.getClienteIns().consultarVeiculo(documentoCli);
                    break;

                case 2:
                    // Excluir veículo do cliente
                    documentoCli = Interface.solicitarEntrada("Informe o documento do cliente:");
                    veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo que deseja excluir:"); 
                    if(instancias.getClienteIns().excluirVeiculo(documentoCli, veiculoPlaca, instancias.getTicketsIns()) == false){
                        JOptionPane.showMessageDialog(null, "Erro ao excluir o veículo do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Veículo excluido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case 3:
                    // Voltar
                    Interface.exibirMensagem("Voltando ao menu de clientes...");
                    break;

                default:
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 3);
    }
}
