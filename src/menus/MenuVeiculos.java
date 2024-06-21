package menus;

import enums.EnumTipoVeiculo;
import classes.*;
import enums.EnumUsoEstacionamento;
import funcoesVisual.*;
import interfaces.*;
import javax.swing.JOptionPane;

public class MenuVeiculos implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        String[] opcoesSubMenuVeiculos = {
            "Cadastrar veículo",
            "Consultar veículo por documento",
            "Excluir veículo do cliente",
            "Voltar"
        };

        int opcaoVeiculo;
        do {
            opcaoVeiculo = JOptionPane.showOptionDialog(null, "Submenu - Gerenciar veículos:", "Gerenciar veículos",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesSubMenuVeiculos, opcoesSubMenuVeiculos[0]);

            switch (opcaoVeiculo) {
                case 0:
                    // Cadastrar veículo
                    String documentoCli = JOptionPane.showInputDialog("Informe o documento:");
                    String veiculoPlaca = JOptionPane.showInputDialog("Informe a placa do veículo:");
                    String modeloVeiculo = JOptionPane.showInputDialog("Informe o modelo do veículo:");
                    String corVeiculo = JOptionPane.showInputDialog("Informe a cor do veículo:");
                    String tipoUso = (String) JOptionPane.showInputDialog(null, "Selecione o tipo da vaga.", "Tipo de Vaga", JOptionPane.QUESTION_MESSAGE, null, new String[]{"HORISTA", "MENSALISTA"}, "HORISTA");
                    String tipoVeiculoStr = (String) JOptionPane.showInputDialog(null, "Selecione o tipo da vaga.", "Tipo de Vaga", JOptionPane.QUESTION_MESSAGE, null, new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
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
                    documentoCli = JOptionPane.showInputDialog("Informe o documento:");
                    instancias.getClienteIns().consultarVeiculo(documentoCli);
                    break;

                case 2:
                    // Excluir veículo do cliente
                    documentoCli = JOptionPane.showInputDialog("Informe o documento do cliente:");
                    veiculoPlaca = JOptionPane.showInputDialog("Informe a placa do veículo que deseja excluir:"); 
                    if(instancias.getClienteIns().excluirVeiculo(documentoCli, veiculoPlaca, instancias.getTicketsIns()) == false){
                        JOptionPane.showMessageDialog(null, "Erro ao excluir o veículo do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Veículo excluido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case 3:
                    // Voltar
                    JOptionPane.showMessageDialog(null, "Voltando ao menu de clientes...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcaoVeiculo != 3);
    }
}
