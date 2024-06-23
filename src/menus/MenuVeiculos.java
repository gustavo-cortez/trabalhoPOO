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
        do {
            StringBuilder menu = new StringBuilder("Menu Principal:\n");
            for (EnumMenuVeiculos option : EnumMenuVeiculos.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            opcao = Interface.solicitarInt(menu.toString());

            switch (opcao) {
                case 1:
                    // Cadastrar veículo
                    String documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    String veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo:");
                    String modeloVeiculo = Interface.solicitarEntrada("Informe o modelo do veículo:");
                    String corVeiculo = Interface.solicitarEntrada("Informe a cor do veículo:");
                    String tipoUso = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"HORISTA", "MENSALISTA"}, "HORISTA");
                    String tipoVeiculoStr = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
                    if (tipoVeiculoStr != null) {
                        switch (tipoVeiculoStr.toUpperCase()) {
                            case "CARRO":
                                instancias.getClienteIns().adicionarVeiculoCliente(veiculoPlaca, EnumTipoVeiculo.valueOf(tipoVeiculoStr), documentoCli, modeloVeiculo, corVeiculo, EnumUsoEstacionamento.valueOf(tipoUso));
                                break;
                        }
                    }
                    
                    break;

                case 2:
                    // Consultar veículo por documento
                    documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    instancias.getClienteIns().consultarVeiculo(documentoCli);
                    break;

                case 3:
                    // Excluir veículo do cliente
                    documentoCli = Interface.solicitarEntrada("Informe o documento do cliente:");
                    veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo que deseja excluir:"); 
                    if(instancias.getClienteIns().excluirVeiculo(documentoCli, veiculoPlaca, instancias.getTicketsIns()) == false){
                        Interface.exibirErro("Erro ao excluir o veículo do cliente.");
                    }
                    else{
                        Interface.exibirSucesso("Veículo excluido com sucesso.");
                    }
                    break;

                case 4:
                    // Voltar
                    Interface.exibirMensagem("Voltando ao menu de clientes...");
                    break;

                default:
                    Interface.exibirErro("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 4);
    }
}
